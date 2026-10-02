# 요구사항 정의서 (work001 - log-error-fix)

## 배경/목적
현업 요청에 따라 테스트>로그조회 하위 3개 메뉴(로그인이력/액션로그/에러로그)의 조회 오류를 조사한다.
세 메뉴 모두 조회 시 동일한 SQL 문법 오류(Unknown column 'seq' in 'order clause')로 목록이 표시되지 않는다.
본 문서는 무엇이 되어야 하는지(요구사항)를 정의하며, 어떻게 고칠지는 다루지 않는다.

## 요청 범위 표
| 번호 | 요청서의 메뉴/기능 | 다루는 FR 번호 |
|------|--------------------|----------------|
| 1 | 테스트>로그조회>로그인이력 | FR-01 |
| 2 | 테스트>로그조회>액션로그 | FR-02 |
| 3 | 테스트>로그조회>에러로그 | FR-03 |

## 현상과 확인한 사실

### 공통 (세 메뉴 공통 구조)
- 오류 로그 파일: `D:/AppHome/testApp/workshop/changes/work001[log-error-fix]/error.log`
- 오류가 난 호출 URL은 3종류다(로그에서 확인):
  - `/logs/getLoginHistList.ajax` — error.log:463
  - `/logs/getActionLogList.ajax` — error.log:1169
  - `/logs/getErrorLogList.ajax` — error.log:1879
- 세 건 모두 동일한 오류다: `java.sql.SQLSyntaxErrorException: Unknown column 'seq' in 'order clause'` (error.log:70, 1169 부근, 1879 부근).
- 실행된 SQL의 ORDER BY는 화면이 넘긴 정렬값으로 만들어진다. 요청 파라미터에 `"sort_column":"seq desc"`가 들어 있다(error.log:1879 부근 request params).
- 정렬값을 그대로 ORDER BY에 붙이는 공통 조각은 `D:/AppHome/testApp/src/main/resources/sqlmap/mapper/comm/comm-mapper.xml:17`~`:18` (`<if ...sort_column...> ORDER BY ${sort_column}`), 이 조각은 `Comm.PagingEnd`이며 세 조회 쿼리가 모두 `<include refid="Comm.PagingEnd"/>`로 사용한다.
- 화면 3개 모두 정렬값을 `seq desc`로 고정 전송한다:
  - `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/loginHist.jsp:63`
  - `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/actionLog.jsp:63`
  - `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/errorLog.jsp:63`
- 화면 그리드에는 "번호" 컬럼이 `seq` 필드로 정의되어 있다:
  - `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/loginHist.jsp:132`
  - `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/actionLog.jsp:129`
  - `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/errorLog.jsp:132`
- DB 정의 확인(테이블에는 seq 컬럼이 있다):
  - `D:/AppHome/testApp/db/schema.sql:86` `CREATE TABLE tbsy_login_hist` — `seq BIGINT NOT NULL AUTO_INCREMENT`(PRIMARY KEY)
  - `D:/AppHome/testApp/db/schema.sql:223` `CREATE TABLE tbsy_action_log` — `seq BIGINT NOT NULL AUTO_INCREMENT`(PRIMARY KEY)
  - `D:/AppHome/testApp/db/schema.sql:237` `CREATE TABLE tbsy_error_log` — `seq BIGINT NOT NULL AUTO_INCREMENT`(PRIMARY KEY)
- 원인 구분: "테이블에 컬럼이 없다"가 아니라 "테이블에는 seq가 있는데 조회 결과(SELECT 목록)에 seq가 빠져 있다". 세 조회 쿼리의 SELECT 목록에 seq가 없어, 바깥 ORDER BY seq가 가리킬 컬럼을 찾지 못한다.

### 1) 로그인이력
- 현상: 화면 진입 시 자동 조회에서 오류가 나 목록이 표시되지 않는다.
- 오류 메시지: `Unknown column 'seq' in 'order clause'` (error.log:463 부근, `logs.selectLoginHistList`).
- 소스에서 확인한 관련 위치:
  - 화면: `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/loginHist.jsp:63`(sort_column=seq desc), `:132`(그리드 seq 필드)
  - Controller: `D:/AppHome/testApp/src/main/java/kr/co/gnx/logs/LogsController.java:49`~`:50` (`/logs/getLoginHistList.ajax`), `:67`(서비스 호출)
  - Service: `D:/AppHome/testApp/src/main/java/kr/co/gnx/logs/LogsService.java:54`~`:57`
  - DAO: `D:/AppHome/testApp/src/main/java/kr/co/gnx/logs/LogsDAO.java:45`~`:46`
  - Mapper: `D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml:84`(`selectLoginHistListSql`), `:92`(`FROM tbsy_login_hist tb1`), `:105`~`:108`(`selectLoginHistList`가 PagingStart/Sql/PagingEnd 조합)
  - 공통 페이징: `D:/AppHome/testApp/src/main/resources/sqlmap/mapper/comm/comm-mapper.xml:17`~`:18`

### 2) 액션로그
- 현상: 화면 진입 시 자동 조회에서 오류가 나 목록이 표시되지 않는다.
- 오류 메시지: `Unknown column 'seq' in 'order clause'` (error.log:1169 부근, `logs.selectActionLogList`).
- 소스에서 확인한 관련 위치:
  - 화면: `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/actionLog.jsp:63`(sort_column=seq desc), `:129`(그리드 seq 필드)
  - Controller: `D:/AppHome/testApp/src/main/java/kr/co/gnx/logs/LogsController.java:95`~`:96` (`/logs/getActionLogList.ajax`), `:113`(서비스 호출)
  - Service: `D:/AppHome/testApp/src/main/java/kr/co/gnx/logs/LogsService.java:94`~`:97`
  - DAO: `D:/AppHome/testApp/src/main/java/kr/co/gnx/logs/LogsDAO.java:79`~`:80`
  - Mapper: `D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml:122`(`selectActionLogListSql`), `:133`(`FROM tbsy_action_log tb1`), `:149`~`:152`(`selectActionLogList`가 PagingStart/Sql/PagingEnd 조합)
  - 공통 페이징: `D:/AppHome/testApp/src/main/resources/sqlmap/mapper/comm/comm-mapper.xml:17`~`:18`

### 3) 에러로그
- 현상: 화면 진입 시 자동 조회에서 오류가 나 목록이 표시되지 않는다.
- 오류 메시지: `Unknown column 'seq' in 'order clause'` (error.log:1879 부근, `logs.selectErrorLogList`).
- 소스에서 확인한 관련 위치:
  - 화면: `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/errorLog.jsp:63`(sort_column=seq desc), `:132`(그리드 seq 필드)
  - Controller: `D:/AppHome/testApp/src/main/java/kr/co/gnx/logs/LogsController.java:141`~`:142` (`/logs/getErrorLogList.ajax`), `:159`(서비스 호출)
  - Service: `D:/AppHome/testApp/src/main/java/kr/co/gnx/logs/LogsService.java:107`~`:110`
  - DAO: `D:/AppHome/testApp/src/main/java/kr/co/gnx/logs/LogsDAO.java:90`~`:91`
  - Mapper: `D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml:155`(`selectErrorLogListSql`), `:170`(`FROM tbsy_error_log tb1`), `:185`~`:189`(`selectErrorLogList`가 PagingStart/Sql/PagingEnd 조합)
  - 공통 페이징: `D:/AppHome/testApp/src/main/resources/sqlmap/mapper/comm/comm-mapper.xml:17`~`:18`

## 기능 요구사항 (FR)

### FR-01 로그인이력 조회
- 설명: 테스트>로그조회>로그인이력 화면에서 조건으로 조회하면 로그인 이력 목록이 오류 없이 표시되어야 한다.
- Given: 사용자가 로그인이력 화면에 진입한 상태에서
- When: 조회 조건(이름/사번, 기간)으로 조회하면
- Then: SQL 오류 없이 로그인 이력 목록이 표시되고, 각 행에 번호(seq) 값이 채워져 보인다.

### FR-02 액션로그 조회
- 설명: 테스트>로그조회>액션로그 화면에서 조건으로 조회하면 액션 로그 목록이 오류 없이 표시되어야 한다.
- Given: 사용자가 액션로그 화면에 진입한 상태에서
- When: 조회 조건(이름/사번, 기간)으로 조회하면
- Then: SQL 오류 없이 액션 로그 목록이 표시되고, 각 행에 번호(seq) 값이 채워져 보인다.

### FR-03 에러로그 조회
- 설명: 테스트>로그조회>에러로그 화면에서 조건으로 조회하면 에러 로그 목록이 오류 없이 표시되어야 한다.
- Given: 사용자가 에러로그 화면에 진입한 상태에서
- When: 조회 조건(이름/사번, 기간)으로 조회하면
- Then: SQL 오류 없이 에러 로그 목록이 표시되고, 각 행에 번호(seq) 값이 채워져 보인다.

## 비기능 요구사항
- 해당 없음 (요청서에 성능/보안/감사로그 관련 요구가 없음).

## 범위 제외 항목 (Out of scope)
- 소스 코드 수정 및 배포 (본 문서는 요구사항 정의만 다룸).
- 세 화면의 소속(조직명) 컬럼 복원 (요청서에 없음).
- 로그조회 이외 메뉴의 오류.
- 로그 저장(insert) 동작 변경.

## 확인 필요 질문
1. 세 화면의 "번호" 컬럼(seq)을 계속 표시해야 하는가, 아니면 정렬 기준을 다른 값(예: 발생/입력 시간)으로 바꿔도 되는가?
2. 로그 목록의 기본 정렬 순서는 최신순(내림차순)이어야 하는가?
