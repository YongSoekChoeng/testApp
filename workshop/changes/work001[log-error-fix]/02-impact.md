# 영향도 분석서 (work001 - log-error-fix)

## 요구사항과 다른 점
- 없음. 요구사항 정의서의 서술(오류 URL 3종, 오류 문구, 공통 조각 위치, 화면 sort_column/그리드 seq 위치, 테이블 seq 컬럼 존재)은 소스에서 확인한 사실과 일치한다.

## FR별 대응 표
| FR 번호 | 원인 한 줄 | 변경 대상 파일 | 변경 내용 한 줄 |
|---------|-----------|----------------|-----------------|
| FR-01 | 로그인이력 조회 쿼리 SELECT 목록에 seq가 없어 바깥 ORDER BY seq가 컬럼을 못 찾음 | D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml | selectLoginHistListSql의 SELECT 목록에 `tb1.seq`를 추가한다 |
| FR-02 | 액션로그 조회 쿼리 SELECT 목록에 seq가 없어 바깥 ORDER BY seq가 컬럼을 못 찾음 | D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml | selectActionLogListSql의 SELECT 목록에 `tb1.seq`를 추가한다 |
| FR-03 | 에러로그 조회 쿼리 SELECT 목록에 seq가 없어 바깥 ORDER BY seq가 컬럼을 못 찾음 | D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml | selectErrorLogListSql의 SELECT 목록에 `tb1.seq`를 추가한다 |

## 원인

### FR-01 로그인이력 조회
화면이 정렬값 `seq desc`를 고정 전송하는 것이 → 공통 페이징 조각의 ORDER BY로 이어지고 → 그 ORDER BY가 가리키는 seq가 조회 결과에 없어서 오류가 난다.
- 화면 `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/loginHist.jsp:63`에 `<input type="hidden" name="sort_column" id="sort_column" value="seq desc"/>`가 있어 `sort_column=seq desc`가 전달된다. 그리드에 `field:"seq"`(번호) 컬럼이 있다(`loginHist.jsp:132`).
- 호출 URL `/logs/getLoginHistList.ajax`(`loginHist.jsp:92`) → Controller `D:/AppHome/testApp/src/main/java/kr/co/gnx/logs/LogsController.java:49`~`:50` `getLoginHistList` → Service `D:/AppHome/testApp/src/main/java/kr/co/gnx/logs/LogsService.java:54`~`:57` → DAO `D:/AppHome/testApp/src/main/java/kr/co/gnx/logs/LogsDAO.java:45`~`:46` → 쿼리ID `logs.selectLoginHistList`.
- `selectLoginHistList`는 `Comm.PagingStart` + `selectLoginHistListSql` + `Comm.PagingEnd` 조합이다(Comm.PagingEnd include는 `logs-mapper.xml:108`). 공통 조각 `Comm.PagingEnd`는 `D:/AppHome/testApp/src/main/resources/sqlmap/mapper/comm/comm-mapper.xml:17`~`:18`에서 `<if ...sort_column...> ORDER BY ${sort_column}`로 정렬값을 그대로 ORDER BY에 붙인다. 실행 SQL은 `... ) A ORDER BY seq desc ) AA LIMIT ...` 형태가 된다.
- 문제가 된 컬럼 seq의 테이블 정의 여부: `D:/AppHome/testApp/db/schema.sql:86` `CREATE TABLE tbsy_login_hist`, `:87` `seq BIGINT NOT NULL AUTO_INCREMENT`(PRIMARY KEY) — 테이블에 있다.
- 조회 결과(SELECT 목록) 포함 여부: `selectLoginHistListSql`(`logs-mapper.xml:84`)의 SELECT 목록 `logs-mapper.xml:86`~`:91`(mb_id, emp_cd, tb2.emp_nm, login_ip, login_dtm, memo)에 seq가 없다. 즉 "테이블에 컬럼이 없다"가 아니라 "테이블에는 seq가 있는데 조회 결과(SELECT 목록)에 seq가 빠져 있다". 바깥 ORDER BY가 서브쿼리 A의 출력 컬럼에서 seq를 못 찾아 `Unknown column 'seq' in 'order clause'`가 난다(`error.log:467`, `:472`).

### FR-02 액션로그 조회
화면이 정렬값 `seq desc`를 고정 전송하는 것이 → 공통 페이징 조각의 ORDER BY로 이어지고 → 그 ORDER BY가 가리키는 seq가 조회 결과에 없어서 오류가 난다.
- 화면 `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/actionLog.jsp:63`에 `value="seq desc"` hidden input이 있고, 그리드에 `field:"seq"`(번호) 컬럼이 있다(`actionLog.jsp:129`).
- 호출 URL `/logs/getActionLogList.ajax`(`actionLog.jsp:92`) → Controller `LogsController.java:95`~`:96` `getActionLogList` → Service `LogsService.java:94`~`:97` → DAO `LogsDAO.java:79`~`:80` → 쿼리ID `logs.selectActionLogList`.
- `selectActionLogList`는 `Comm.PagingStart` + `selectActionLogListSql` + `Comm.PagingEnd` 조합이다(Comm.PagingEnd include는 `logs-mapper.xml:152`). `Comm.PagingEnd`의 `ORDER BY ${sort_column}`(`comm-mapper.xml:17`~`:18`)에 `seq desc`가 그대로 들어간다.
- 문제가 된 컬럼 seq의 테이블 정의 여부: `D:/AppHome/testApp/db/schema.sql:223` `CREATE TABLE tbsy_action_log`, `:224` `seq BIGINT NOT NULL AUTO_INCREMENT`(PRIMARY KEY) — 테이블에 있다.
- 조회 결과(SELECT 목록) 포함 여부: `selectActionLogListSql`(`logs-mapper.xml:122`)의 SELECT 목록 `logs-mapper.xml:124`~`:132`(mb_id, emp_cd, tb2.emp_nm, action_type, action_type_nm, action_url, action_query_string, action_ip, in_dtm)에 seq가 없다. 즉 "테이블에는 seq가 있는데 조회 결과(SELECT 목록)에 seq가 빠져 있다". 바깥 ORDER BY가 seq를 못 찾아 `Unknown column 'seq' in 'order clause'`가 난다(`error.log:1173`, `:1178`).

### FR-03 에러로그 조회
화면이 정렬값 `seq desc`를 고정 전송하는 것이 → 공통 페이징 조각의 ORDER BY로 이어지고 → 그 ORDER BY가 가리키는 seq가 조회 결과에 없어서 오류가 난다.
- 화면 `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/errorLog.jsp:63`에 `value="seq desc"` hidden input이 있고, 그리드에 `field:"seq"`(번호) 컬럼이 있다(`errorLog.jsp:132`).
- 호출 URL `/logs/getErrorLogList.ajax`(`errorLog.jsp:92`) → Controller `LogsController.java:141`~`:142` `getActionLogList`(에러로그용 오버로드, 메서드명 동일) → Service `LogsService.java:107`~`:110` → DAO `LogsDAO.java:90`~`:91` → 쿼리ID `logs.selectErrorLogList`.
- `selectErrorLogList`는 `Comm.PagingStart` + `selectErrorLogListSql` + `Comm.PagingEnd` 조합이다(Comm.PagingEnd include는 `logs-mapper.xml:189`). `Comm.PagingEnd`의 `ORDER BY ${sort_column}`(`comm-mapper.xml:17`~`:18`)에 `seq desc`가 그대로 들어간다.
- 문제가 된 컬럼 seq의 테이블 정의 여부: `D:/AppHome/testApp/db/schema.sql:237` `CREATE TABLE tbsy_error_log`, `:238` `seq BIGINT NOT NULL AUTO_INCREMENT`(PRIMARY KEY) — 테이블에 있다.
- 조회 결과(SELECT 목록) 포함 여부: `selectErrorLogListSql`(`logs-mapper.xml:155`)의 SELECT 목록 `logs-mapper.xml:157`~`:169`(mb_id, emp_cd, tb2.emp_nm, action_type, action_type_nm, error_url, error_query_string, error_status, error_class, error_msg, error_trace, error_ip, in_dtm)에 seq가 없다. 즉 "테이블에는 seq가 있는데 조회 결과(SELECT 목록)에 seq가 빠져 있다". 바깥 ORDER BY가 seq를 못 찾아 `Unknown column 'seq' in 'order clause'`가 난다(`error.log:1883`, `:1888`).

## 진입점 (화면/API/배치) 목록
| 메뉴 | 화면 파일 | URL | Controller.메서드 | 쿼리ID |
|------|-----------|-----|-------------------|--------|
| 테스트>로그조회>로그인이력 | D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/loginHist.jsp | /logs/getLoginHistList.ajax | kr.co.gnx.logs.LogsController.getLoginHistList (LogsController.java:49) | logs.selectLoginHistList |
| 테스트>로그조회>액션로그 | D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/actionLog.jsp | /logs/getActionLogList.ajax | kr.co.gnx.logs.LogsController.getActionLogList (LogsController.java:95) | logs.selectActionLogList |
| 테스트>로그조회>에러로그 | D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/errorLog.jsp | /logs/getErrorLogList.ajax | kr.co.gnx.logs.LogsController.getActionLogList (LogsController.java:141, 에러로그용 오버로드) | logs.selectErrorLogList |

## 변경 대상
| 파일 절대경로 | 변경 유형 | 변경 내용 | 해결하는 FR 번호 |
|---------------|-----------|-----------|------------------|
| D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml | 수정 | `selectLoginHistListSql`(84~102행)의 SELECT 목록 첫 컬럼으로 `tb1.seq`를 추가한다. | FR-01 |
| D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml | 수정 | `selectActionLogListSql`(122~146행)의 SELECT 목록 첫 컬럼으로 `tb1.seq`를 추가한다. | FR-02 |
| D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml | 수정 | `selectErrorLogListSql`(155~183행)의 SELECT 목록 첫 컬럼으로 `tb1.seq`를 추가한다. | FR-03 |

## 영향 받는 호출자·연계 시스템·DB 객체
- 호출자(화면): loginHist.jsp, actionLog.jsp, errorLog.jsp — 세 화면 모두 그리드에 `seq`(번호) 필드가 이미 있어(각 132/129/132행) SELECT 목록에 seq가 추가되면 값이 채워져 표시된다. 화면 수정 불필요.
- 호출 경로: LogsController → LogsService → LogsDAO → logs-mapper.xml. Controller/Service/DAO는 파라미터 전달만 하며 수정 불필요.
- 공통 코드 사용처: `Comm.PagingEnd`는 `logs-mapper.xml:108,152,189`(세 로그 쿼리)와 `D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/member-mapper.xml:119`(회원사관리 `selectMemberListPaging`)에서 사용된다. 이번 변경은 공통 조각(`comm-mapper.xml`)을 건드리지 않고 세 로그 쿼리의 SELECT 목록만 고치므로 member-mapper.xml:119 회원사관리 조회에는 영향이 없다(회원사 쿼리는 SELECT 목록에 `tb1.seq`가 이미 있고 `member-mapper.xml:118`에서 자체 `ORDER BY IFNULL(tb1.up_dtm, tb1.in_dtm) DESC`를 쓴다).
- DB 객체(변경 대상): 없음(테이블/컬럼 변경 없음).
- DB 객체(조회 대상): tbsy_login_hist, tbsy_action_log, tbsy_error_log (각각 seq 컬럼 보유: schema.sql:86/223/237).
- DB 객체(조인만 하는 대상): tbin_empmst(사원명, logs-mapper.xml:93/135/172), tbcm_common_code(액션유형명, logs-mapper.xml:137/174).

## 검토한 대안
| 대안 | 고르지 않은 이유 |
|------|------------------|
| 공통 조각 `Comm.PagingEnd`(comm-mapper.xml:17~18)의 ORDER BY 처리를 바꾼다 | 공통 코드를 쓰는 member-mapper.xml:119 회원사관리 조회까지 영향이 번진다. 영향 범위가 가장 작지 않다. |
| 화면(loginHist/actionLog/errorLog.jsp:63)의 `sort_column` 값을 SELECT 목록에 있는 컬럼(login_dtm/in_dtm)으로 바꾼다 | FR-01~03의 Then이 "각 행에 번호(seq) 값이 채워져 보인다"이므로 seq가 조회 결과에 있어야 한다. 정렬값만 바꾸면 seq 표시 요구를 못 채운다. |
| 그리드의 "번호" 컬럼을 제거한다 | FR의 Then(번호 값 표시)과 요구사항 범위에 어긋난다. |

## 재사용 가능한 기존 컴포넌트
- 공통 페이징 조각 `Comm.PagingStart`/`Comm.PagingEnd`(comm-mapper.xml:8~24) — 그대로 재사용.
- 그리드 초기화 공통 함수 `genexon.initKendoUI_grid_inlineEdit`(화면 3종 사용) — 그대로 재사용.
- 그리드 seq 필드 정의(각 화면 schema model 및 columns) — 이미 존재하므로 재사용.

## 위험 요소와 회귀 테스트가 필요한 기존 기능
- 위험 요소: 세 쿼리는 `SELECT AA.* FROM (SELECT COUNT(*) OVER() AS total, A.* FROM (...) A ORDER BY ${sort_column}) AA ...` 구조다. SELECT 목록에 `tb1.seq`를 추가하면 서브쿼리 A의 출력 컬럼이 하나 늘지만 `AA.*`로 그대로 전달되므로 그리드 seq 표시 외 부작용은 없다. ORDER BY가 서브쿼리 A의 출력 컬럼명을 참조하므로 추가 컬럼의 별칭이 `seq`가 되도록 `tb1.seq`(별칭 없이 컬럼명 seq)로 추가한다.
- 회귀 테스트 필요 기능:
  1. 로그인이력/액션로그/에러로그 세 화면의 조회 및 서버페이징(page/pageSize) 정상 동작.
  2. 세 화면 그리드의 "번호"(seq) 컬럼 값 표시.
  3. `Comm.PagingEnd`를 함께 쓰는 회원사관리 조회(`member-mapper.xml:119`, `selectMemberListPaging`)가 이번 변경으로 영향받지 않는지 확인.
  4. 로그 저장(insertErrorLog/insertActionLog/insertJoinSendLog) 동작은 변경하지 않았으므로 그대로인지 확인.
- 범위 밖(요구사항 Out of scope): 세 화면 그리드의 "소속"(snmpath) 컬럼은 조회 쿼리 SELECT 목록에 없어 값이 비어 보이지만 요청서에 없으므로 이번 변경 대상이 아니다.
