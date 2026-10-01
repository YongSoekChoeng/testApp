# 상세 설계서 (Detailed Design)

## 1. 개요
본 설계서는 로그 조회 메뉴(로그인이력, 액션로그, 에러로그)에서 발생하는 SQL 문법 오류를 해결하고, 데이터 조회 시 최신순 정렬을 보장하기 위한 수정 사항을 다룬다.

## 2. 변경 사항 상세

### 2.1 SQL Mapper 수정 (`logs-mapper.xml`)
모든 로그 조회 쿼리에 데이터의 일관된 최신순 조회를 위해 `ORDER BY` 구문을 추가한다.

#### [FR-01] 에러 로그 리스트 조회 수정
- **대상 쿼리**: `selectErrorLogListSql`
- **변경 내용**: `ORDER BY in_dtm DESC` 추가
- **이유**: 요구사항에서 언급된 `seq` 컬럼은 현재 테이블 구조상 존재하지 않거나 에러의 원인이 되므로, 유효한 시간 컬럼인 `in_dtm`을 기준으로 정렬을 수행한다.

#### [FR-02] 액션 로그 리스트 조회 수정
- **대상 쿼리**: `selectActionLogListSql`
- **변경 내용**: `ORDER BY in_dtm DESC` 추가
- **이유**: 로그 조회 기능의 일관성을 위해 최신 로그가 상단에 노출되도록 정렬 기준을 명시한다.

#### [FR-03] 로그인 이력 리스트 조회 수정
- **대상 쿼리**: `selectLoginHistListSql`
- **변경 내용**: `ORDER BY login_dtm DESC` 추가
- **이유**: 로그인 이력 역시 최신순 조회를 기본으로 하여 사용자 편의성을 높인다.

## 3. API 및 데이터 구조 변경
- **API 스펙**: 기존 API 호출 방식(Parameter)은 유지하며, SQL 내부의 정렬 로직만 변경한다.
- **DB 변경**: 없음 (기존 테이블 컬럼 `in_dtm`, `login_dtm` 활용)

## 4. 예외 처리 방침
- SQL 문법 오류(Column not found 등)를 방지하기 위해 실제 DB 테이블에 존재하는 컬럼명(`in_dtm`, `login_dtm`)을 사용한다.
- 페이징 처리 컴포넌트(`Comm.PagingEnd`)와의 결합 시 정렬 순서가 보장되도록 `ORDER BY` 위치를 `WHERE` 절 뒤, `LIMIT` 절(PagingEnd 내부) 앞에 배치한다.
