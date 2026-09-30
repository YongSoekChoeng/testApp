# 검수 결과 보고서 (Impact Review Report)

## 1. 요구사항 충족 여부 (FR Verification)

| ID | 요구사항 명칭 | 판정 | 검토 의견 |
| :--- | :--- | :---: | :--- |
| FR-01 | 에러 로그 리스트 조회 기능 수정 | 충족 | `errorLog.jsp`에서 `seq desc`를 `in_dtm desc`로 변경하여 SQL 에러 원인을 제거함. |
| FR-02 | 액션 로그 리스트 조회 기능 정상화 | 충족 | `actionLog.jsp`에서 `seq desc`를 `in_dtm desc`로 변경하여 잠재적 에러를 방지함. |
| FR-03 | 로그인 이력 리스트 조회 기능 정상화 | 충족 | `loginHist.jsp`에서 `seq desc`를 `login_dtm desc`로 변경하여 잠재적 에러를 방지함. |

## 2. 상세 검토 의견

### [심각도: 주요] | 파일: `errorLog.jsp`, `actionLog.jsp`, `loginHist.jsp` | 문제: 정렬 컬럼 하드코딩 및 SQL Injection 위험 | 제안
- **현황**: JSP 파일 내 `<input type="hidden" name="sort_column" ... value="seq desc"/>`와 같이 정렬 조건을 하드코딩하여 전달하고 있음.
- **위험**: 영향도 분석서에 명시된 바와 같이, `Comm.PagingEnd`가 이 값을 그대로 `ORDER BY` 절에 삽입한다면, 향후 사용자가 UI를 통해 정렬 컬럼을 변경할 때 DB에 없는 컬럼명을 입력할 경우 동일한 SQL 에러가 재발함. 또한, 파라미터가 검증 없이 SQL에 직접 삽입되므로 SQL Injection 공격에 취약할 수 있음.
- **제안**: 서버 측(Controller 또는 Service)에서 전달받은 `sort_column` 값이 허용된 컬럼 목록(Whitelist)에 포함되는지 반드시 검증하는 로직을 추가해야 함.

### [심각도: 경미] | 파일: `logs-mapper.xml` | 문제: 동적 SQL 정렬 조건의 안정성 | 제안
- **현황**: MyBatis 매퍼에서 `${sort_column}`을 사용하여 동적 정렬을 수행함.
- **위험**: `${}` 문법은 SQL Injection에 취약하므로, 반드시 앞서 언급한 서버 측의 화이트리스트 검증이 선행되어야 함.
- **제안**: `sort_column` 파라미터에 대해 `if test` 등을 사용하여 유효한 컬럼명인지 체크하거나, 서버에서 정제된 값을 전달하도록 설계 변경 권고.

## 3. 종합 판정

**결과: PASS (조건부 통과)**

*본 검수는 요구사항 정의서에 명시된 기능적 오류(SQL 에러)의 해결 여부에 집중하였으며, 설계상 존재하는 보안 및 확장성 이슈(SQL Injection, 정렬 컬럼 검증 미비)는 별도의 개선 과제로 식별함. 기능적 요구사항은 모두 충족함.*
