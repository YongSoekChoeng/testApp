# 상세 설계서 (work001 - log-error-fix)

## 설계 요약
- 세 로그조회 화면(로그인이력/액션로그/에러로그)은 정렬값 `seq desc`를 고정 전송하고, 공통 페이징 조각 `Comm.PagingEnd`가 이를 바깥 `ORDER BY seq desc`로 붙인다.
- 그런데 세 조회 쿼리의 SELECT 목록에 `seq`가 없어 바깥 ORDER BY가 참조할 컬럼을 못 찾아 `Unknown column 'seq' in 'order clause'`가 난다.
- 해결 방향: 공통 조각이나 화면을 건드리지 않고, 세 조회 쿼리(`selectLoginHistListSql`/`selectActionLogListSql`/`selectErrorLogListSql`)의 SELECT 목록 첫 컬럼으로 `tb1.seq`를 추가한다.
- `tb1.seq`를 별칭 없이 추가하면 서브쿼리 A의 출력 컬럼명이 `seq`가 되어 바깥 ORDER BY와 그리드 `field:"seq"`가 그대로 동작한다.
- 변경 파일은 `logs-mapper.xml` 1개, 변경 지점 3곳이며 Controller/Service/DAO/화면/DB는 수정하지 않는다.

## 영향도 분석서·검수 리뷰와 소스 대조
- 영향도 분석서(02-impact.md)의 변경 대상 파일·줄 위치·원인 서술은 실제 소스와 일치함을 확인했다(로그인 84~102행, 액션 122~146행, 에러 155~183행).
- 검수 리뷰(03-impact-review.md)는 판정 "통과"이며 차단/주요/경미 지적이 없다. 따라서 설계에 반영할 지적 사항은 없다.
- 요구사항 정의서의 "확인 필요 질문" 2건(번호 컬럼 유지 여부, 최신순 정렬 여부)은 아래 questions에 담았다. 본 설계는 "번호(seq) 컬럼을 유지하고 최신순(seq desc) 정렬을 그대로 둔다"는 전제로 작성했다(요구사항 FR의 Then이 "각 행에 번호(seq) 값이 채워져 보인다"이므로 seq를 조회 결과에 포함하는 방향이 요구에 부합).

## 변경 상세

### 변경 1 (FR-01 로그인이력)
- 파일: D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml
- 위치: `selectLoginHistListSql` (84행), SELECT 목록 첫 컬럼 (86행)
- 변경 전 (86행):
```
			SELECT tb1.mb_id         /* 회사코드 VARCHAR(20)*/
```
- 변경 후 (86행):
```
			SELECT tb1.seq           /* 번호 BIGINT*/
			     , tb1.mb_id         /* 회사코드 VARCHAR(20)*/
```
- 이유: SELECT 목록에 seq가 없어 바깥 `ORDER BY seq desc`(comm-mapper.xml:17~18)가 참조할 컬럼을 못 찾는다. `tb1.seq`를 별칭 없이 추가해 서브쿼리 A의 출력 컬럼명을 `seq`로 만든다.
- 해결하는 FR: FR-01

### 변경 2 (FR-02 액션로그)
- 파일: D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml
- 위치: `selectActionLogListSql` (122행), SELECT 목록 첫 컬럼 (124행)
- 변경 전 (124행):
```
			SELECT tb1.mb_id                     /* 회사코드 VARCHAR(20)*/
```
- 변경 후 (124행):
```
			SELECT tb1.seq                       /* 번호 BIGINT*/
			     , tb1.mb_id                     /* 회사코드 VARCHAR(20)*/
```
- 이유: 변경 1과 동일(액션로그 쿼리 SELECT 목록에 seq 누락).
- 해결하는 FR: FR-02

### 변경 3 (FR-03 에러로그)
- 파일: D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml
- 위치: `selectErrorLogListSql` (155행), SELECT 목록 첫 컬럼 (157행)
- 변경 전 (157행):
```
			SELECT tb1.mb_id                /* 회사코드 VARCHAR(20)*/
```
- 변경 후 (157행):
```
			SELECT tb1.seq                  /* 번호 BIGINT*/
			     , tb1.mb_id                /* 회사코드 VARCHAR(20)*/
```
- 이유: 변경 1과 동일(에러로그 쿼리 SELECT 목록에 seq 누락).
- 해결하는 FR: FR-03

### 작성 규칙 준수
- 기존 파일의 들여쓰기(탭), 컬럼 나열 방식(`SELECT 첫컬럼` 다음 줄부터 `, 컬럼`), 컬럼 뒤 `/* ... */` 주석 방식을 그대로 따른다.
- `tb1.seq`는 별칭을 붙이지 않는다(별칭을 붙이면 출력 컬럼명이 달라져 바깥 ORDER BY `seq`가 다시 못 찾는다).

## API 스펙 변경
- 해당 없음. 요청/응답 파라미터와 오류 응답 형식은 바뀌지 않는다(조회 결과 컬럼이 하나 늘 뿐이며 그리드가 이미 `seq` 필드를 사용).

## DB 변경 (DDL)
- 해당 없음. 테이블/컬럼 추가·삭제 없음(세 테이블 모두 seq 컬럼 보유: schema.sql:86/223/237).

## 예외 처리 방침
- 해당 없음. SQL 문법 오류 자체를 없애는 변경이며, 별도 예외 처리 코드를 추가하지 않는다.

## FR별 확인 방법
| FR 번호 | 확인 방법 (화면 조작 순서와 기대 결과) |
|---------|----------------------------------------|
| FR-01 | 1) 테스트>로그조회>로그인이력 화면에 진입한다. 2) 이름/사번·기간 조건을 입력하고 조회한다. 3) SQL 오류 메시지 없이 목록이 표시되고, 각 행 "번호" 컬럼에 seq 값이 채워져 보이면 통과. 4) 페이지를 2페이지로 이동해도 목록이 정상 표시되면 통과. |
| FR-02 | 1) 테스트>로그조회>액션로그 화면에 진입한다. 2) 조건으로 조회한다. 3) SQL 오류 없이 목록이 표시되고 각 행 "번호"에 seq 값이 보이면 통과. 4) 서버페이징 이동 시에도 정상 표시되면 통과. |
| FR-03 | 1) 테스트>로그조회>에러로그 화면에 진입한다. 2) 조건으로 조회한다. 3) SQL 오류 없이 목록이 표시되고 각 행 "번호"에 seq 값이 보이면 통과. 4) 서버페이징 이동 시에도 정상 표시되면 통과. |

## 회귀 테스트 대상
1. 로그인이력/액션로그/에러로그 세 화면의 조회 및 서버페이징(page/pageSize) 정상 동작.
2. 세 화면 그리드 "번호"(seq) 컬럼 값 표시(정렬 `seq desc` 최신순 유지).
3. `Comm.PagingEnd`를 함께 쓰는 회원사관리 조회(`member-mapper.xml:119`, `selectMemberListPaging`)가 이번 변경으로 영향받지 않는지 확인(공통 조각을 건드리지 않으므로 영향 없어야 함).
4. 로그 저장(insertErrorLog/insertActionLog 등) 동작이 그대로인지 확인(변경 대상 아님).

## 범위 밖 (요구사항 Out of scope)
- 세 화면 그리드의 "소속"(snmpath) 컬럼은 조회 쿼리 SELECT 목록에 없어 값이 비어 보이지만 요청서에 없으므로 이번 변경 대상이 아니다.
- 로그조회 이외 메뉴의 오류, 로그 저장(insert) 동작 변경은 다루지 않는다.
