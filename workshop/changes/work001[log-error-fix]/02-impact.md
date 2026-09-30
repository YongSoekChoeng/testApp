# 영향도 분석서

## 1. 진입점 (화면/API/배치) 목록
- **화면 (JSP)**
  - `/logs/loginHist.go` (로그인이력 조회 화면)
  - `/logs/actionLog.go` (액션로그 조회 화면)
  - `/logs/errorLog.go` (에러로그 조회 화면)
- **API (Ajax)**
  - `/logs/getLoginHistList.ajax`
  - `/logs/getActionLogList.ajax`
  - `/logs/getErrorLogList.ajax`

## 2. 변경 대상
| 파일 경로 | 변경 유형 | 이유 |
| :--- | :---: | :--- |
| `src/main/resources/sqlmap/mapper/system/logs-mapper.xml` | 수정 | `selectActionLogListSql` 및 `selectErrorLogListSql` 내 존재하지 않는 `seq` 컬럼 정렬 조건 제거 또는 유효한 컬럼(`in_dtm`)으로 변경 (FR-01, FR-02, FR-03) |

## 3. 영향 받는 호출자·연계 시스템·DB 객체
- **호출자 (Java Class)**
  - `kr.co.gnx.logs.LogsController`: 각 로그 리스트 조회 요청 처리
  - `kr.co.gnx.logs.LogsService`: DAO 호출을 통한 데이터 조회 로직 수행
  - `kr.co.gnx.logs.LogsDAO`: MyBatis를 통한 SQL 실행
- **DB 객체**
  - `tbsy_error_log`: 에러 로그 테이블 (조회 쿼리 영향)
  - `tbsy_action_log`: 액션 로그 테이블 (조회 쿼리 영향)
  - `tbsy_login_hist`: 로그인 이력 테이블 (참조용)
  - `tbin_empmst`: 사원 마스터 테이블 (조인 대상)
  - `tbcm_common_code`: 공통 코드 테이블 (조인 대상)

## 4. 재사용 가능한 기존 컴포넌트
- `kr.co.gnx.base.BaseDAO`, `BaseService`, `BaseController`: 공통 기능 활용
- `kr.co.gnx.comm.util.CommUtil`: 파라미터 유효성 검사 및 페이징 처리 활용
- `Comm.PagingStart`, `Comm.PagingEnd`: MyBatis 공통 페이징 SQL 조각 활용

## 5. 위험 요소와 회귀 테스트가 필요한 기존 기능
- **위험 요소**
  - 정렬 기준 컬럼 변경 시, 기존 사용자가 기대하던 정렬 순서(예: 특정 ID 순)가 달라질 수 있음.
  - `in_dtm` 컬럼에 인덱스가 없을 경우, 데이터 양 증가에 따른 조회 성능 저하 가능성 (비기능 요구사항 관련).
- **회귀 테스트 필요 기능**
  - 로그인이력, 액션로그, 에러로그의 전체 리스트 조회 기능.
  - 검색 조건(회사코드, 기간, 사원명 등) 적용 시 데이터 필터링 및 페이징 정상 동작 여부.
  - 정렬 기능이 화면 UI에서 정상적으로 작동하는지 확인.
