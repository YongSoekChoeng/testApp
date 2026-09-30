# 상세 설계서: 로그 조회 메뉴 SQL 오류 수정

## 1. 개요
본 설계는 '테스트 > 로그조회' 메뉴(로그인이력, 액션로그, 에러로그)에서 발생하는 SQL 문법 오류(`Unknown column 'seq' in 'order clause'`)를 해결하기 위한 것이다. 존재하지 않는 `seq` 컬럼을 정렬 기준으로 사용하는 MyBatis 매퍼 파일을 수정한다.

## 2. 클래스 및 인터페이스 변경
기존 Java 클래스(Controller, Service, DAO)의 로직 변경은 없으며, SQL 매퍼 파일의 쿼리문만 수정한다.

- **대상 파일**: `src/main/resources/sqlmap/mapper/system/logs-mapper.xml`
- **변경 내용**: 
    - `selectErrorLogListSql`: `ORDER BY seq DESC` -> `ORDER BY in_dtm DESC`
    - `selectActionLogListSql`: `ORDER BY seq DESC` -> `ORDER BY in_dtm DESC`
    - `selectLoginHistListSql`: `ORDER BY seq DESC` -> `ORDER BY in_dtm DESC` (영향도 분석 기반 추가 검토)

## 3. API 스펙 (변경 없음)
기존 API 스펙을 유지하며, 응답 데이터의 순서만 정렬 기준 변경에 따라 달라질 수 있다.

| API 명 | Method | Endpoint | 설명 |
| :--- | :---: | :--- | :--- |
| 에러로그 조회 | GET | `/logs/getErrorLogList.ajax` | 에러 로그 리스트 반환 |
| 액션로그 조회 | GET | `/logs/getActionLogList.ajax` | 액션 로그 리스트 반환 |
| 로그인이력 조회 | GET | `/logs/getLoginHistList.ajax` | 로그인 이력 리스트 반환 |

## 4. DB 변경 (DDL)
- **변경 사항 없음**: 기존 테이블의 컬럼을 수정하거나 추가하지 않고, 기존에 존재하는 `in_dtm` 컬럼을 활용한다.

## 5. 시퀀스 (Sequence)
1. `logs-mapper.xml` 내의 오류 쿼리 식별
2. `seq` 컬럼을 `in_dtm` (또는 테이블별 유효한 PK/날짜 컬럼)으로 교체
3. MyBatis 매퍼 파일 저장 및 애플리케이션 재시작/Hot-swap
4. 로그 조회 메뉴별 기능 테스트

## 6. 예외 처리 방침
- SQL 문법 오류가 발생하지 않도록 쿼리 검증을 철저히 한다.
- 데이터가 없는 경우 빈 리스트를 반환하도록 기존 페이징 로직을 유지한다.
- 정렬 기준 변경으로 인한 성능 저하를 방지하기 위해 `in_dtm` 컬럼에 인덱스가 생성되어 있는지 확인한다 (필요 시 인덱스 생성 제안).
