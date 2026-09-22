// 전제: Jenkins Job의 워크스페이스는 이 저장소(testApp) 루트로 고정한다(Job 설정의 Custom Workspace = /app/testApp).
// dstone의 다른 모듈들(dstone-batch/batchadmin)과 같은 "Checkout -> Build -> Stop -> Deploy -> Health Check" 흐름을 따른다.
// bin/startApp.sh가 build+deploy+start를 한 번에 하지만, 빌드 실패와 배포 실패를 Jenkins 화면에서
// 구분해서 볼 수 있도록 Build 단계를 따로 둔다(어차피 startApp.sh 안에서 한 번 더 mvn package를 돌리는데,
// 의존성이 캐시돼 있어 오래 걸리지 않는다).
pipeline {
    agent any

    environment {
        APP_NAME = 'testApp'
        WORKSPACE_DIR = '/app/testApp'
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
                echo '====== Maven Build ======'
                sh 'mvn -q -DskipTests clean package'
            }
        }

        stage('Stop Existing Process') {
            steps {
                echo '====== Stop Existing Process ======'
                sh '${WORKSPACE_DIR}/bin/stopApp.sh || true'
            }
        }

        stage('Deploy') {
            steps {
                echo '====== Deploy(Build+Start) Application ======'
                sh '${WORKSPACE_DIR}/bin/startApp.sh'
            }
        }

        stage('Health Check') {
            steps {
                echo '====== Health Check ======'
                sh '''
                    sleep 15
                    ${WORKSPACE_DIR}/bin/statusApp.sh
                    ${WORKSPACE_DIR}/bin/statusApp.sh | grep -q RUNNING
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
