// dstone의 다른 모듈들(dstone-batch/batchadmin)과 같은
// "Checkout -> Build -> Sync -> Stop -> Deploy -> Health Check" 흐름을 따른다.
// Jenkins는 자기 워크스페이스(기본 경로)에 체크아웃해서 먼저 한 번 빌드해보고(실패를 빨리 알기 위함),
// 성공하면 그 소스를 실제 배포 경로(/app/testApp, 로컬 개발 때부터 써 온 경로)로 동기화한 다음
// 그 경로의 bin/stopApp.sh + bin/startApp.sh를 그대로 호출한다(두 스크립트는 이미 로컬에서
// 검증된 것을 그대로 재사용 - Jenkins 전용 배포 로직을 새로 만들지 않는다).
pipeline {
    agent any

    environment {
        APP_NAME = 'testApp'
        DEPLOY_DIR = '/app/testApp'
    }

    stages {
        stage('Checkout') {
            steps {
                echo '====== Git Checkout ======'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '====== Maven Build (fail-fast 검증용) ======'
                sh 'mvn -q -DskipTests clean package'
            }
        }

        stage('Sync to Deploy Dir') {
            steps {
                echo '====== 배포 경로로 동기화 ======'
                sh '''
                    mkdir -p ${DEPLOY_DIR}
                    # --no-owner --no-group: Jenkins(jenkins 계정)는 /app/testApp(jysn007 소유)의
                    # 기존 디렉토리 소유권을 바꿀 권한이 없다(그룹에 써넣기 권한만 있음) - 소유권 동기화는
                    # 생략하고 내용만 맞춘다.
                    rsync -rlptD --no-owner --no-group --omit-dir-times --delete --exclude=.git --exclude=target ./ ${DEPLOY_DIR}/
                    chmod +x ${DEPLOY_DIR}/bin/*.sh
                '''
            }
        }

        stage('Stop Existing Process') {
            steps {
                echo '====== Stop Existing Process ======'
                sh '${DEPLOY_DIR}/bin/stopApp.sh || true'
            }
        }

        stage('Deploy') {
            steps {
                echo '====== Deploy(Build+Start) Application ======'
                sh '${DEPLOY_DIR}/bin/startApp.sh'
            }
        }

        stage('Health Check') {
            steps {
                echo '====== Health Check ======'
                sh '''
                    sleep 15
                    ${DEPLOY_DIR}/bin/statusApp.sh
                    ${DEPLOY_DIR}/bin/statusApp.sh | grep -q RUNNING
                '''
            }
        }
    }

    post {
        success {
            echo '====== Deployment Successful ======'
        }
        failure {
            echo '====== Deployment Failed ======'
            sh 'tail -100 /opt/tomcat9/logs/catalina.out || true'
        }
    }
}
