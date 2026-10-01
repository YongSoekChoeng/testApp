# 구현 태스크 목록 (Implementation Tasks)

| 번호 | 목적 | 대상 파일 | 선행 태스크 | 완료 기준 |
| :--- | :--- | :--- | :--- | :--- |
| 1 | 에러 로그 리스트 조회 쿼리 정렬 오류 수정 | `D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml` | - | `selectErrorLogListSql`에 `ORDER BY in_dtm DESC`가 추가되고, 에러 없이 데이터가 최신순으로 조회됨 |
| 2 | 액션 로그 리스트 조회 쿼리 정렬 구문 추가 | `D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml` | 1 | `selectActionLogListSql`에 `ORDER BY in_dtm DESC`가 추가되고, 데이터가 최신순으로 조회됨 |
| 3 | 로그인 이력 리스트 조회 쿼리 정렬 구문 추가 | `D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/logs-mapper.xml` | 2 | `selectLoginHistListSql`에 `ORDER BY login_dtm DESC`가 추가되고, 데이터가 최신순으로 조회됨 |
