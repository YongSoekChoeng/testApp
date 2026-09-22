#!/bin/bash
# testApp이 떠 있는 로컬 Tomcat의 상태를 확인한다.
TOMCAT_HOME="${TOMCAT_HOME:-/opt/tomcat9}"

if [ -f "$TOMCAT_HOME/tomcat.pid" ] && kill -0 "$(cat "$TOMCAT_HOME/tomcat.pid")" 2>/dev/null; then
    if curl -sf -o /dev/null -w '' http://localhost:7080/ 2>/dev/null; then
        echo "RUNNING"
        exit 0
    fi
    echo "STARTING (프로세스는 떠 있으나 아직 응답 없음)"
    exit 1
fi

echo "STOPPED"
exit 1
