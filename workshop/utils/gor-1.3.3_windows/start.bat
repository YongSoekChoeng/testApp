rem <goreplay 사용법>

rem 1. 트래픽을 출력하기
rem gor --input-raw :6080 --output-stdout

rem 2. 트래픽을 파일로 저장하기	
rem gor --input-raw :6080 --output-file ./logs/6080-20261022.gor

rem 3. 트래픽을 출력하기(reponse도 함께 출력하기)
rem gor --input-raw :6080 --output-stdout --input-raw-track-response

rem 4. 저장된 파일에서 트래픽 재생하기
rem gor --input-file ./logs/6080-20261022_0.gor --output-http "http://localhost:8888"

rem 5. 특정포트 패킷을 diffy 프락시서버로 보내기.
gor --input-raw :6080 --output-http "http://localhost:8888"