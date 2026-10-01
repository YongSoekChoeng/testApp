# 영향도 분석 리뷰 결과

## FR별 판정
| FR 번호 | 충족/미충족/확인불가 | 근거 |
| :--- | :--- | :--- |
| FR-01 | 미충족 | `selectErrorLogListSql` 쿼리에 `ORDER BY seq DESC` 구문이 존재하지 않으며, 테이블 구조상 `seq` 컬럼이 존재하더라도 요구사항에서 언급된 에러의 원인이 명확히 소스상에 나타나지 않음. 또한, 영향도 분석서의 재분석 내용에서 `seq` 컬럼이 존재한다고 했으나, 실제 쿼리에는 정렬 구문 자체가 누락되어 있어 에러 발생 원인과 수정 방향이 불분명함. |

## 지적 사항
| 심각도 | 파일:라인 | 문제 | 제안 |
| :--- | :--- | :--- | :--- |
| 주요 | logs-mapper.xml:155 | 요구사항에서 언급된 `ORDER BY seq DESC` 구문이 실제 소스(`selectErrorLogListSql`)에는 존재하지 않음. | 에러 발생 원인이 되는 정확한 구문을 확인하거나, 요구사항에 따라 `ORDER BY in_dtm DESC` 등 유효한 컬럼으로 정렬 구문을 추가해야 함. |
| 주요 | logs-mapper.xml:117 | `selectActionLogListSql` 쿼리에 정렬 구문이 누락되어 있어, 로그 조회 기능의 일관성 및 최신순 조회를 위해 정렬 기준 추가가 필요함. | `ORDER BY in_dtm DESC` 구문을 추가하여 일관된 정렬을 보장할 것. |
| 경미 | logs-mapper.xml:117 | `selectLoginHistListSql` 쿼리에도 정렬 구문이 누락되어 있음. | `ORDER BY login_dtm DESC` 구문을 추가하여 일관성을 유지할 것. |
