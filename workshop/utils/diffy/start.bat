set JAVA_HOME="C:\DEV\JDK\21.0"
set PATH=%PATH%;%JAVA_HOME%\bin

java -version

rem candidate 			: 테스트하려는 App(주로 TO-BE 시스템).
rem master.primary 		: 비교하려는 App(주로 AI-IS 시스템).
rem master.secondary 	: 비교하려는 App(주로 AI-IS 시스템). primary 와 비교해서 Noise식별.

java -jar diffy.jar ^
  --candidate=localhost:6070 ^
  --master.primary=localhost:6080 ^
  --master.secondary=localhost:6080 ^
  --service.protocol=http ^
  --serviceName=My-Service ^
  --proxy.port=8880 ^
  --admin.port=8881 ^
  --http.port=8888 ^
  --rootUrl=localhost:8888
  