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
# startup.sh/shutdown.sh(포트 8005로 SHUTDOWN 문자열을 보내는 방식) 대신 catalina.sh run(포그라운드
# 실행)을 직접 nohup+disown으로 떼어낸다 - Jenkins에서 이 스크립트를 부르면, Jenkins는 빌드가 끝날 때
# 자기가 띄운 프로세스를 정리(kill)하는데, startup.sh가 내부적으로 만드는 백그라운드 프로세스는
# (setsid로 세션을 분리해도, Jenkins 쪽 환경변수를 지워도) 여전히 SIGTERM을 받아 Tomcat 자체
# shutdown hook이 깔끔하게 내려버리는 걸 실제로 겪었다. nohup+disown으로 셸의 잡 테이블에서
# 완전히 떼어내는 조합이 이런 CI 환경에서 데몬을 살려두는 가장 확실한 방법이다.
mkdir -p "$TOMCAT_HOME/logs"
nohup setsid "$TOMCAT_HOME/bin/catalina.sh" run < /dev/null >> "$TOMCAT_HOME/logs/catalina.out" 2>&1 &
disown
echo $! > "$TOMCAT_HOME/tomcat.pid"

echo "완료. http://localhost:7080/ 로 접속하세요 (기동까지 몇 초 걸릴 수 있습니다)."
