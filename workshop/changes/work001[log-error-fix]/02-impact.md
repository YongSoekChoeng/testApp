# 영향도 분석 보고서 (Impact Analysis Report)

## 1. 진입점 (Entry Points)
- **화면 (JSP)**
  - `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/loginHist.jsp` (로그인이력 조회)
  - `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/actionLog.jsp` (액션로그 조회)
  - `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/errorLog.jsp` (에러로그 조회)
- **API (Ajax)**
  - `/logs/getLoginHistList.ajax`
  - `/logs/getActionLogList.ajax`
  - `/logs/getErrorLogList.ajax`

## 2. 변경 대상
| 파일 경로 | 변경 유형 | 이유 |
| :--- | :---: | :--- |
| `D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml` | 수정 | FR-01: `selectLoginHistListSql` 내 존재하지 않는 `seq` 컬럼 참조 제거 및 올바른 정렬 컬럼(예: `login_dtm`)으로 수정. FR-02/03: 액션/에러 로그 쿼리 내 정렬 기준 검토 및 필요시 수정. |
| `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/loginHist.jsp` | 수정 | `sort_column` hidden 필드 값 변경 (`seq desc` $\rightarrow$ `login_dtm desc` 등) |
| `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/actionLog.jsp` | 수정 | `sort_column` hidden 필드 값 검토 및 정렬 기준 일치화 |
| `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/errorLog.jsp` | 수정 | `sort_column` hidden 필드 값 검토 및 정렬 기준 일치화 |

## 3. 영향 받는 호출자·연계 시스템·DB 객체
- **호출자 (Java Service/DAO)**
  - `kr.co.gnx.logs.LogsService.getLoginHistList`
  - `kr.co.gnx.logs.LogsService.getActionLogList`
  - `kr.co.gnx.logs.LogsService.getErrorLogList`
  - `kr.co.gnx.logs.LogsDAO` (각 select 메서드)
- **DB 객체**
  - `tbsy_login_hist` (로그인이력 테이블)
  - `tbsy_action_log` (액션로그 테이블)
  - `tbsy_error_log` (에러로그 테이블)
  - `tbin_empmst` (사원 마스터 테이블 - 조인 대상)
  - `tbcm_common_code` (공통 코드 테이블 - 액션 유형 조인 대상)

## 4. 재사용 가능한 기존 컴포넌트
- `kr.co.gnx.base.BaseController`, `BaseService`, `BaseDAO` (기본 CRUD 및 페이징 구조)
- `kr.co.gnx.comm.util.CommUtil` (문자열 유효성 검사 등)
- `kr.co.gnx.comm.util.session.SessionUtil` (사용자 세션 관리)
- `genexon.initKendoUI_grid_inlineEdit` (Kendo UI 그리드 초기화 공통 함수)

## 5. 위험 요소와 회귀 테스트가 필요한 기존 기능
- **위험 요소**
  - `seq` 컬럼을 사용하던 정렬 로직을 변경할 경우, 사용자가 기대하던 정렬 순서(예: 최신순)가 달라질 수 있음.
  - SQL 쿼리 수정 시 `ORDER BY` 절의 컬럼이 실제 DB 테이블에 존재하는지 반드시 확인 필요.
- **회귀 테스트 필요 기능**
  - 로그 조회 메뉴의 모든 페이징 처리 기능 (정렬 변경 후 페이징이 정상 작동하는지).
  - 검색 조건(날짜, 사번/이름) 적용 시 데이터 필터링 및 정렬 결과의 정합성.
  - 로그 데이터가 대량일 경우 정렬 성능 저하 여부.
