# 영향도 분석 보고서 (Impact Analysis Report)

## 1. 진입점 (Entry Points)
- **화면 (JSP)**
  - `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/errorLog.jsp` (에러 로그 조회)
  - `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/actionLog.jsp` (액션 로그 조회)
  - `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/loginHist.jsp` (로그인 이력 조회)
- **API (Controller)**
  - `kr.co.gnx.logs.LogsController`
    - `/logs/getErrorLogList.ajax`
    - `/logs/getActionLogList.ajax`
    - `/logs/getLoginHistList.ajax`

## 2. 변경 대상
| 파일 경로 | 변경 유형 | 이유 |
| :--- | :---: | :--- |
| `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/errorLog.jsp` | 수정 | `<input type="hidden" name="sort_column" id="sort_column" value="seq desc"/>`에서 존재하지 않는 `seq` 컬럼을 `in_dtm desc`로 변경 (FR-01) |
| `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/actionLog.jsp` | 수정 | `<input type="hidden" name="sort_column" id="sort_column" value="seq desc"/>`에서 존재하지 않는 `seq` 컬럼을 `in_dtm desc`로 변경 (FR-02) |
| `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/loginHist.jsp` | 수정 | `<input type="hidden" name="sort_column" id="sort_column" value="seq desc"/>`에서 존재하지 않는 `seq` 컬럼을 `login_dtm desc`로 변경 (FR-03) |
| `D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml` | 확인 | `selectErrorLogList`, `selectActionLogList`, `selectLoginHistList` 쿼리 내 정렬 조건이 `sort_column` 파라미터에 의해 동적으로 결정되므로, JSP에서 전달하는 컬럼명이 DB 테이블(`tbsy_error_log`, `tbsy_action_log`, `tbsy_login_hist`)에 존재하는지 검증 필요. |

## 3. 영향 받는 호출자 · 연계 시스템 · DB 객체
- **호출자 (Service/DAO)**
  - `kr.co.gnx.logs.LogsService` (조회 메서드들)
  - `kr.co.gnx.logs.LogsDAO` (MyBatis 호출 메서드들)
- **DB 객체 (Table)**
  - `tbsy_error_log` (정렬 컬럼: `in_dtm`)
  - `tbsy_action_log` (정렬 컬럼: `in_dtm`)
  - `tbsy_login_hist` (정렬 컬럼: `login_dtm`)

## 4. 재사용 가능한 기존 컴포넌트
- `Comm.PagingStart`, `Comm.PagingEnd` (MyBatis 공통 페이징 SQL)
- `kr.co.gnx.comm.util.CommUtil` (파라미터 유효성 검사)
- `genexon.getSearchParameterToJsonString()` (JSP 내 검색 파라미터 직렬화)

## 5. 위험 요소와 회귀 테스트가 필요한 기존 기능
- **위험 요소**:
  - JSP에서 `sort_column` 값을 하드코딩하여 전달하고 있으므로, 만약 사용자가 그리드 헤더를 클릭하여 정렬을 변경할 경우, 변경된 컬럼명이 DB에 존재하지 않으면 동일한 SQL 에러가 재발할 수 있음.
  - `Comm.PagingEnd`에서 `${sort_column}`을 그대로 `ORDER BY` 절에 삽입하므로 SQL Injection 위험이 존재함 (단, 현재는 내부 관리용 메뉴이므로 위험도는 낮음).
- **회귀 테스트 필요 기능**:
  - 각 로그 조회 화면의 **페이징 처리** 기능 (정렬 조건 변경 후 페이지 이동이 정상적인지 확인).
  - 각 로그 조회 화면의 **검색 기능** (정렬 조건과 검색 조건이 결합되었을 때 정상 동작 확인).
  - **로그 데이터의 정렬 순서**가 의도한 대로(최신순) 표시되는지 확인.
