# 구현 태스크 목록 (work001 - log-error-fix)

## TASK-01
- 목적: 로그인이력 조회 쿼리 SELECT 목록에 seq를 추가해 FR-01을 해결한다.
- 대상 파일: D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml
- 변경 내용: 설계서 "변경 1" — `selectLoginHistListSql`(84행)의 SELECT 목록 첫 컬럼(86행)으로 `tb1.seq`를 추가한다.
- 선행 태스크: 없음
- 완료 기준: `selectLoginHistListSql`의 SELECT 목록에 `tb1.seq`가 첫 컬럼으로 포함되어 있는가? (예/아니오)
- 상태: 대기

## TASK-02
- 목적: 액션로그 조회 쿼리 SELECT 목록에 seq를 추가해 FR-02를 해결한다.
- 대상 파일: D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml
- 변경 내용: 설계서 "변경 2" — `selectActionLogListSql`(122행)의 SELECT 목록 첫 컬럼(124행)으로 `tb1.seq`를 추가한다.
- 선행 태스크: 없음
- 완료 기준: `selectActionLogListSql`의 SELECT 목록에 `tb1.seq`가 첫 컬럼으로 포함되어 있는가? (예/아니오)
- 상태: 대기

## TASK-03
- 목적: 에러로그 조회 쿼리 SELECT 목록에 seq를 추가해 FR-03을 해결한다.
- 대상 파일: D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml
- 변경 내용: 설계서 "변경 3" — `selectErrorLogListSql`(155행)의 SELECT 목록 첫 컬럼(157행)으로 `tb1.seq`를 추가한다.
- 선행 태스크: 없음
- 완료 기준: `selectErrorLogListSql`의 SELECT 목록에 `tb1.seq`가 첫 컬럼으로 포함되어 있는가? (예/아니오)
- 상태: 대기

## TASK-04
- 목적: 세 로그조회 화면(로그인이력/액션로그/에러로그)의 조회와 서버페이징이 오류 없이 동작하고 각 행 "번호"(seq)가 표시되는지 확인한다(FR-01~03 통합 확인).
- 대상 파일: D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/loginHist.jsp, D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/actionLog.jsp, D:/AppHome/testApp/src/main/webapp/WEB-INF/views/logs/errorLog.jsp (확인만, 수정 없음)
- 변경 내용: 설계서 "FR별 확인 방법" — 세 화면에서 조건 조회 및 페이지 이동을 수행한다.
- 선행 태스크: TASK-01, TASK-02, TASK-03
- 완료 기준: 세 화면 모두 SQL 오류 없이 목록이 표시되고 각 행 "번호" 컬럼에 seq 값이 채워져 보이는가? (예/아니오)
- 상태: 대기

## TASK-05
- 목적: 공통 페이징 조각을 함께 쓰는 회원사관리 조회가 이번 변경으로 영향받지 않았는지 확인한다(회귀).
- 대상 파일: D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/member-mapper.xml (확인만, 수정 없음)
- 변경 내용: 설계서 "회귀 테스트 대상" 3번 — 회원사관리 조회(`selectMemberListPaging`)가 정상 동작하는지 확인한다.
- 선행 태스크: TASK-01, TASK-02, TASK-03
- 완료 기준: 회원사관리 조회가 변경 전과 동일하게 정상 동작하는가? (예/아니오)
- 상태: 대기

## FR ↔ TASK 매핑
| FR 번호 | 해결 태스크 | 확인 태스크 |
|---------|-------------|-------------|
| FR-01 | TASK-01 | TASK-04 |
| FR-02 | TASK-02 | TASK-04 |
| FR-03 | TASK-03 | TASK-04 |
