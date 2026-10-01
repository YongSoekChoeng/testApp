# 영향도 분석서

## 1. 진입점
- **화면**: 테스트 > 로그조회 > 로그인이력, 액션로그, 에러로그

## 2. 변경 대상
- **파일 경로**: `D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml`
- **변경 유형**: 수정
- **이유**: 
    - `selectErrorLogListSql` 쿼리 내에 정렬 기준이 명시되어 있지 않음. 요구사항 및 에러 로그 발생 정황을 고려할 때, 기존에 존재했을 것으로 추정되는 `ORDER BY seq DESC` 구문이 누락되어 있거나, 잘못된 컬럼을 참조하여 에러가 발생할 가능성이 높음. 따라서 최신순 조회를 위해 `ORDER BY in_dtm DESC`를 추가해야 함.
    - `selectActionLogListSql` 및 `selectLoginHistListSql` 쿼리 역시 정렬 기준이 명시되어 있지 않으므로, 로그 조회 기능의 일관성을 위해 `in_dtm` 또는 `login_dtm`을 기준으로 한 정렬 구문 추가 검토가 필요함.

## 3. 영향 받는 호출자·연계 시스템·DB 객체
- **호출자**: 
    - `kr.co.gnx.logs.LogsDAO.selectErrorLogList()`
    - `kr.co.gnx.logs.LogsService.selectErrorLogList()` (추정)
    - `kr.co.gnx.logs.LogsController.errorLogList()` (추정)
- **DB 객체**: `tbsy_error_log` (테이블), `tbsy_action_log` (테이블), `tbsy_login_hist` (테이블)

## 4. 재사용 가능한 기존 컴포넌트
- `Comm.PagingStart`, `Comm.PagingEnd` (페이징 처리 공통 컴포넌트)

## 5. 위험 요소와 회귀 테스트가 필요한 기존 기능
- **위험 요소**: `ORDER BY` 절 추가 시 페이징 처리(`Comm.PagingEnd`)와 결합되어 데이터 정렬 순서가 변경됨에 따라, 기존에 사용자가 인지하던 정렬 순서와 다를 수 있음.
- **회귀 테스트**: 
    - 에러 로그 리스트 조회 시 데이터가 최신순으로 정상 출력되는지 확인.
    - 액션 로그 및 로그인 이력 리스트의 정렬 상태가 의도한 대로 동작하는지 확인.
    - 페이징 기능이 정렬된 결과에 대해 정상 동작하는지 확인.

---
**재분석 반영 내역**
| 지적 사항 | 수정 방향 |
| :--- | :--- |
| `selectErrorLogListSql` 내 `ORDER BY seq DESC` 구문이 명시적으로 보이지 않으나 요구사항에서 지적됨 | 실제 소스(`logs-mapper.xml`) 확인 결과,  `selectErrorLogListSql` 쿼리 내에 `seq` 컬럼 누락. 테이블에 해당 컬럼은 존재.  쿼리만 수정. |
| `selectActionLogList` 쿼리에서도 동일한 정렬 오류 발생 가능성 확인 필요 | 실제 소스(`logs-mapper.xml`) 확인 결과,  `selectActionLogListSql` 쿼리 내에 `seq` 컬럼 누락. 테이블에 해당 컬럼은 존재.  쿼리만 수정. |
