set JAVA_HOME="C:\DEV\JDK\21.0"
set PATH=%PATH%;%JAVA_HOME%\bin

java -version

java -jar diffy.jar ^
  --candidate=localhost:6080 ^
  --master.primary=localhost:6050 ^
  --master.secondary=localhost:6060 ^
  --service.protocol=http ^
  --serviceName=My-Service ^
  --proxy.port=8880 ^
  --admin.port=8881 ^
  --http.port=8888 ^
  --rootUrl=localhost:8888
  