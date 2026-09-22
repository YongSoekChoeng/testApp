#!/bin/bash
# testApp이 떠 있는 로컬 Tomcat을 정지한다.
# startApp.sh가 catalina.sh run을 직접 띄우고 PID를 tomcat.pid에 직접 적어두므로,
# 여기서도 shutdown.sh(포트 8005 프로토콜)가 아니라 그 PID를 직접 kill한다.
TOMCAT_HOME="${TOMCAT_HOME:-/opt/tomcat9}"

PID_FILE="$TOMCAT_HOME/tomcat.pid"
if [ ! -f "$PID_FILE" ]; then
    echo "PID 파일이 없습니다 - Tomcat이 구동 중이 아닌 것으로 봅니다."
    exit 0
fi

PID="$(cat "$PID_FILE")"

# startApp.sh가 jenkins 계정이면 jysn007@localhost로 SSH를 떠서(별도 로그인 세션) Tomcat을
# 띄우므로, 그 프로세스는 jysn007 소유다. jenkins는 다른 사용자 소유 프로세스를 kill할 권한이
# 없으니(그룹 쓰기 권한과는 별개의 리눅스 규칙), 정지도 같은 방식으로 jysn007@localhost를 통해서 한다.
run_as_owner() {
    if [ "$(whoami)" = "jenkins" ]; then
        ssh -i /var/lib/jenkins/.ssh/localhost_deploy -o StrictHostKeyChecking=accept-new -o BatchMode=yes \
            jysn007@localhost "$1"
    else
        eval "$1"
    fi
}

if ! run_as_owner "kill -0 $PID 2>/dev/null"; then
    echo "PID $PID 프로세스가 없습니다 - 이미 정지된 것으로 봅니다."
    rm -f "$PID_FILE"
    exit 0
fi

run_as_owner "kill $PID"
for i in $(seq 1 15); do
    run_as_owner "kill -0 $PID 2>/dev/null" || break
    sleep 1
done
run_as_owner "kill -0 $PID 2>/dev/null" && run_as_owner "kill -9 $PID 2>/dev/null"
rm -f "$PID_FILE"
echo "정지 완료."
