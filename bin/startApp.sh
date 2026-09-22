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

echo "[1/3] mvn package"
cd "$PROJECT_DIR"
mvn -q package -DskipTests

echo "[2/3] Tomcat 정지(구동 중이면) 및 이전 배포본 정리"
CATALINA_PID="$TOMCAT_HOME/tomcat.pid" "$TOMCAT_HOME/bin/shutdown.sh" 2>/dev/null || true
sleep 3
rm -rf "$TOMCAT_HOME/webapps/ROOT" "$TOMCAT_HOME/webapps/ROOT.war" "$TOMCAT_HOME/work"/*
cp "$PROJECT_DIR/target/cms4_prd.war" "$TOMCAT_HOME/webapps/ROOT.war"

echo "[3/3] Tomcat 기동"
CATALINA_PID="$TOMCAT_HOME/tomcat.pid" "$TOMCAT_HOME/bin/startup.sh"

echo "완료. http://localhost:7080/ 로 접속하세요 (기동까지 몇 초 걸릴 수 있습니다)."
