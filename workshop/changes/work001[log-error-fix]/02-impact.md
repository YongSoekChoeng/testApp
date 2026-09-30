# 영향도 분석 보고서 (Impact Analysis Report)

## 1. 개요
본 문서는 '테스트 > 로그조회' 메뉴(로그인이력, 액션로그, 에러로그)에서 발생하는 SQL 문법 오류(`Unknown column 'seq' in 'order clause'`)를 해결하기 위한 코드 영향 범위를 조사한 결과입니다.

## 2. 진입점 (Entry Points)
- **화면 (JSP)**
    - `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/errorLog.jsp` (에러 로그 조회)
    - `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/actionLog.jsp` (액션 로그 조회)
    - `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/loginHist.jsp` (로그인 이력 조회)
- **API (Ajax)**
    - `/logs/getErrorLogList.ajax`
    - `/logs/getActionLogList.ajax`
    - `/logs/getLoginHistList.ajax`

## 3. 변경 대상
| 파일 경로 | 변경 유형 | 이유 |
| :--- | :---: | :--- |
| `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/errorLog.jsp` | 수정 | `<input type=\"hidden\" name=\"sort_column\" id=\"sort_column\" value=\"seq desc\"/>` 부분의 `seq`를 실제 존재하는 컬럼(예: `in_dtm`)으로 변경 필요 |
| `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/actionLog.jsp` | 수정 | `<input type=\"hidden\" name=\"sort_column\" id=\"sort_column\" value=\"seq desc\"/>` 부분의 `seq`를 실제 존재하는 컬럼(예: `in_dtm`)으로 변경 필요 |
| `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/loginHist.jsp` | 수정 | `<input type=\"hidden\" name=\"sort_column\" id=\"sort_column\" value=\"seq desc\"/>` 부분의 `seq`를 실제 존재하는 컬럼(예: `login_dtm`)으로 변경 필요 |
| `D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml` | 수정 | (필요 시) 쿼리 내 정렬 조건이 동적 파라미터에 의존할 경우, `seq` 대신 올바른 컬럼명이 전달되도록 확인 또는 쿼리 수정 |

## 4. 영향 받는 요소
- **호출자**: `LogsController`의 각 메서드(`getErrorLogList`, `getActionLogList`, `getLoginHistList`)가 프론트엔드에서 전달된 `sort_column` 값을 사용하여 MyBatis 쿼리를 호출함.
- **연계 시스템**: 없음 (내부 로그 시스템)
- **DB 객체**: 
    - `tbsy_error_log` (에러 로그 테이블)
    - `tbsy_action_log` (액션 로그 테이블)
    - `tbsy_login_hist` (로그인 이력 테이블)
    - *참고: 위 테이블들에는 `seq` 컬럼이 존재하지 않으므로, `in_dtm` 또는 `id` 등의 컬럼을 사용해야 함.*

## 5. 재사용 가능한 기존 컴포넌트
- `kr.co.gnx.base.BaseController` 및 `BaseService`
- `kr.co.gnx.comm.util.CommUtil` (파라미터 유효성 검사)
- `genexon.getSearchParameterToJsonString()` (프론트엔드 검색 파라미터 직렬화)

## 6. 위험 요소 및 회귀 테스트 필요 기능
- **위험 요소**: 
    - 정렬 기준 컬럼을 `seq`에서 다른 컬럼으로 변경할 경우, 사용자가 기대하는 정렬 순서(최신순 등)가 달라질 수 있음.
    - 프론트엔드 Kendo UI Grid의 정렬 헤더 클릭 시 `sort_column` 값이 어떻게 변경되는지 확인 필요.
- **회귀 테스트**:
    - 각 로그 조회 메뉴에서 날짜 범위 검색, 사용자(이름/사번) 검색 기능이 정상 작동하는지 확인.
    - 그리드 정렬(Asc/Desc) 기능이 변경된 컬럼 기준으로 정상 작동하는지 확인.
    - 페이징 처리가 정렬 변경 후에도 올바르게 수행되는지 확인.
