#!/bin/bash
# testApp을 로컬 Tomcat에 빌드+배포+기동한다.
# dstone 프로젝트의 다른 모듈들(dstone-batch/batchadmin)과 같은 "systemd 없이 스크립트로 기동" 방식을 따른다.
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
TOMCAT_HOME="${TOMCAT_HOME:-/opt/tomcat9}"
export JAVA_HOME="${JAVA_HOME:-/usr/lib/jvm/java-21-openjdk-amd64}"

if [ ! -d "$TOMCAT_HOME" ]; then
    echo "TOMCAT_HOME($TOMCAT_HOME)이 없습니다. 표준 Tomcat 9를 그 경로에 설치하거나 TOMCAT_HOME 환경변수를 지정하세요."
    exit 1
fi

echo "[1/3] mvn clean package"
cd "$PROJECT_DIR"
# clean이 꼭 필요하다 - 이 프로젝트는 로컬(jysn007)에서도, Jenkins(jenkins 계정)에서도 번갈아
# 빌드된다. target/ 안의 파일을 "이미 있는 파일 위에 덮어쓰기"하려 하면, 그 파일을 이전에 만든
# 계정이 아닌 쪽에서는 (그룹 쓰기 권한은 있어도) 메타데이터 변경 권한이 없어 실패한다
# (소유자가 아니면 mtime/권한을 못 바꾸는 건 ACL로도 못 풀리는 리눅스의 별도 규칙).
# clean으로 먼저 지우면 새로 만드는 쪽이 그대로 소유자가 되니 문제가 없다.
mvn -q clean package -DskipTests

echo "[2/3] Tomcat 정지(구동 중이면) 및 이전 배포본 정리"
"$SCRIPT_DIR/stopApp.sh" || true
sleep 2
rm -rf "$TOMCAT_HOME/webapps/ROOT" "$TOMCAT_HOME/webapps/ROOT.war" "$TOMCAT_HOME/work"/*
cp "$PROJECT_DIR/target/cms4_prd.war" "$TOMCAT_HOME/webapps/ROOT.war"

echo "[3/3] Tomcat 기동"
mkdir -p "$TOMCAT_HOME/logs"
START_CMD="export JAVA_HOME='$JAVA_HOME'; echo \$\$ > '$TOMCAT_HOME/tomcat.pid'; exec '$TOMCAT_HOME/bin/catalina.sh' run >> '$TOMCAT_HOME/logs/catalina.out' 2>&1"

if [ "$(whoami)" = "jenkins" ]; then
    # Jenkins(jenkins 계정)가 이 스크립트를 부르면 이야기가 다르다 - startup.sh 자체 백그라운드,
    # nohup+setsid+disown, at(1)/atd 전부 몇 초 뒤 Tomcat이 깔끔하게(SIGTERM을 받아 자체 shutdown
    # hook이 정상 종료 절차를 밟는 형태로) 죽는 걸 실제로 겪었다 - 심지어 at에 넘기기 전 관련
    # 환경변수(JENKINS_SERVER_COOKIE 등)를 다 지워도 마찬가지였다. Jenkins의 Durable Task
    # 프로세스 정리가 이 WSL 환경에서 정확히 어떤 기준으로 스캔하는지는 끝내 특정하지 못했지만,
    # localhost로 SSH를 뜨면(PAM이 완전히 새 로그인 세션을 만든다) 그 추적 메커니즘이 뭐가 됐든
    # 아예 무관한 별개 세션이 되어 확실하게 벗어난다 - 이 문제의 가장 확실한 해법이었다.
    ssh -i /var/lib/jenkins/.ssh/localhost_deploy -o StrictHostKeyChecking=accept-new -o BatchMode=yes \
        jysn007@localhost "$START_CMD"
else
    # 직접(jysn007) 실행할 때는 at(1)/atd 큐에 넘기는 것으로 충분하다 - 이 스크립트를 불러온 셸이
    # 끝나도 옆에서 계속 떠 있어야 하니, 이 셸에 딸린 백그라운드 잡으로 두지 않는다는 점은 동일하다.
    unset JENKINS_SERVER_COOKIE HUDSON_SERVER_COOKIE BUILD_ID BUILD_NUMBER BUILD_TAG BUILD_URL \
          JOB_NAME JOB_BASE_NAME EXECUTOR_NUMBER NODE_NAME WORKSPACE JENKINS_URL 2>/dev/null || true
    echo "$START_CMD" | at now
fi
sleep 2

echo "완료. http://localhost:7080/ 로 접속하세요 (기동까지 몇 초 걸릴 수 있습니다)."
