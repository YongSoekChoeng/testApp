#!/bin/bash
# testApp이 올라가 있는 로컬 Tomcat을 정지한다.
set -e

TOMCAT_HOME="${TOMCAT_HOME:-/opt/tomcat9}"
export JAVA_HOME="${JAVA_HOME:-/usr/lib/jvm/java-21-openjdk-amd64}"

# shutdown.sh는 8005 셧다운 포트로 정지 명령을 보낸다 - 이미 꺼져 있으면 접속 실패 메시지만 남기고 끝난다.
CATALINA_PID="$TOMCAT_HOME/tomcat.pid" "$TOMCAT_HOME/bin/shutdown.sh"
echo "정지 요청 완료."
