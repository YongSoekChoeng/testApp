# 구현 태스크 목록 (Implementation Task List)

## Task 1: MyBatis Mapper 쿼리 수정
- **목적**: 존재하지 않는 `seq` 컬럼으로 인한 SQL 에러 해결 및 가상 `seq` 생성
- **대상 파일**: `D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml`
- **선행 태스크**: 없음
- **완료 기준**: 
  - `selectErrorLogList`, `selectActionLogList`, `selectLoginHistList` 쿼리에서 `ORDER BY seq`가 실제 날짜 컬럼으로 변경됨.
  - 각 쿼리의 서브쿼리 내에 `ROW_NUMBER() OVER (ORDER BY [날짜컬럼] DESC) AS seq`가 추가되어 화면의 '번호' 컬럼이 정상 출력됨.

## Task 2: 로그인 이력 화면 수정
- **목적**: 정렬 기준 변경에 따른 클라이언트 측 동작 보정
- **대상 파일**: `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/loginHist.jsp`
- **선행 태스크**: Task 1
- **완료 기준**: 
  - 그리드의 `sort_column` 기본값이 `login_dtm`으로 설정됨.
  - 정렬 시 에러 없이 데이터가 정상적으로 재정렬됨.

## Task 3: 액션 로그 화면 수정
- **목적**: 정렬 기준 변경에 따른 클라이언트 측 동작 보정
- **대상 파일**: `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/actionLog.jsp`
- **선행 태스크**: Task 1
- **완료 기준**: 
  - 그리드의 `sort_column` 기본값이 `in_dtm`으로 설정됨.
  - 정렬 시 에러 없이 데이터가 정상적으로 재정렬됨.

## Task 4: 에러 로그 화면 수정
- **목적**: 정렬 기준 변경에 따른 클라이언트 측 동작 보정
- **대상 파일**: `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/errorLog.jsp`
- **선행 태스크**: Task 1
- **완료 기준**: 
  - 그리드의 `sort_column` 기본값이 `in_dtm`으로 설정됨.
  - 정렬 시 에러 없이 데이터가 정상적으로 재정렬됨.
