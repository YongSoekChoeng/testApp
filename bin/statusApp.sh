#!/bin/bash
# testApp이 떠 있는 로컬 Tomcat의 상태를 확인한다.
# PID가 jysn007 소유든 jenkins 소유든(누가 startApp.sh를 불렀는지에 따라 다르다) 상관없이 확인할 수
# 있도록 kill -0(다른 사용자 소유 프로세스면 권한 오류로 항상 실패한다) 대신 포트 응답으로 판단한다.
TOMCAT_HOME="${TOMCAT_HOME:-/opt/tomcat9}"

if [ -f "$TOMCAT_HOME/tomcat.pid" ]; then
    if curl -sf -o /dev/null -w '' http://localhost:7080/ 2>/dev/null; then
        echo "RUNNING"
        exit 0
    fi
    echo "STARTING (PID 파일은 있으나 아직 응답 없음)"
    exit 1
fi

echo "STOPPED"
exit 1
