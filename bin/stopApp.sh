#!/bin/bash
# testApp이 떠 있는 로컬 Tomcat을 정지한다.
# startApp.sh가 catalina.sh run을 nohup+disown으로 직접 띄우고 PID를 tomcat.pid에 직접 적어두므로,
# 여기서도 shutdown.sh(포트 8005 프로토콜)가 아니라 그 PID를 직접 kill한다.
TOMCAT_HOME="${TOMCAT_HOME:-/opt/tomcat9}"

PID_FILE="$TOMCAT_HOME/tomcat.pid"
if [ ! -f "$PID_FILE" ]; then
    echo "PID 파일이 없습니다 - Tomcat이 구동 중이 아닌 것으로 봅니다."
    exit 0
fi

PID="$(cat "$PID_FILE")"
if ! kill -0 "$PID" 2>/dev/null; then
    echo "PID $PID 프로세스가 없습니다 - 이미 정지된 것으로 봅니다."
    rm -f "$PID_FILE"
    exit 0
fi

kill "$PID"
for i in $(seq 1 15); do
    kill -0 "$PID" 2>/dev/null || break
    sleep 1
done
kill -0 "$PID" 2>/dev/null && kill -9 "$PID" 2>/dev/null || true
rm -f "$PID_FILE"
echo "정지 완료."
