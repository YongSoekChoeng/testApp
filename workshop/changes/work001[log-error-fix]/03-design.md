# 상세 설계서 (Design Specification)

## 1. 개요
본 설계는 로그 조회 메뉴(로그인이력, 액션로그, 에러로그)에서 발생하는 `Unknown column 'seq' in 'order clause'` SQL 에러를 해결하기 위한 설계이다. 기존 쿼리에서 존재하지 않는 `seq` 컬럼을 기준으로 정렬하던 방식을 실제 존재하는 날짜 컬럼으로 변경하고, 화면의 정렬 및 번호 표시 로직을 조정한다.

## 2. 클래스 및 인터페이스 변경

### 2.1 MyBatis Mapper (`logs-mapper.xml`)
- **변경 목적**: 존재하지 않는 `seq` 컬럼을 제거하고, 실제 데이터 기반의 정렬 컬럼으로 교체.
- **변경 내용**:
  - `selectErrorLogList`: `ORDER BY seq DESC` $\rightarrow$ `ORDER BY in_dtm DESC`
  - `selectActionLogList`: `ORDER BY seq DESC` $\rightarrow$ `ORDER BY in_dtm DESC`
  - `selectLoginHistList`: `ORDER BY seq DESC` $\rightarrow$ `ORDER BY login_dtm DESC`
  - **[추가]** 화면의 '번호' 컬럼(seq) 표시를 위해 각 쿼리의 서브쿼리(A) 내에 `ROW_NUMBER() OVER (ORDER BY [날짜컬럼] DESC) AS seq`를 추가하여 가상 `seq`를 생성함.

### 2.2 JSP 화면 (`loginHist.jsp`, `actionLog.jsp`, `errorLog.jsp`)
- **변경 목적**: 정렬 기준 변경에 따른 클라이언트 측 동작 보정.
- **변경 내용**:
  - `sort_column` 기본값 변경 (예: `seq` $\rightarrow$ `login_dtm` 또는 `in_dtm`).
  - Kendo Grid의 정렬 이벤트 발생 시, 서버로 전달되는 컬럼명이 DB 컬럼명과 일치하도록 조정.

## 3. API 스펙 (기존 유지)
- 모든 API는 기존의 요청/응답 형식을 유지하며, 내부 SQL 실행 로직만 변경됨.
- **Endpoint**:
  - `POST /logs/getLoginHistList.ajax`
  - `POST /logs/getActionLogList.ajax`
  - `POST /logs/getErrorLogList.ajax`

## 4. DB 변경 (DDL)
- **없음**: 기존 테이블 구조(`tbsy_error_log`, `tbsy_action_log`, `tbsy_login_hist`)를 유지함.

## 5. 시퀀스 (Sequence)
1. 사용자가 로그 조회 메뉴 접속 및 조건 입력.
2. AJAX 요청 발생.
3. `LogsController`가 요청 수신.
4. `LogsService` 호출.
5. `LogsDAO`를 통해 `logs-mapper.xml` 실행.
   - 쿼리 내에서 `ROW_NUMBER()`를 통해 가상 `seq` 생성.
   - 실제 날짜 컬럼(`in_dtm`, `login_dtm`)으로 정렬 수행.
6. 결과 데이터 반환 및 화면 그리드 렌더링.

## 6. 예외 처리 방침
- SQL 에러 발생 시 기존의 `BadSqlGrammarException` 처리 로직을 유지하되, 설계된 쿼리가 문법적으로 올바른지 사전 검증함.
- 데이터가 없는 경우 빈 리스트를 반환하여 화면 에러를 방지함.

## 7. 설계 대안 (Architecture Alternatives)

### 대안 1: 쿼리 내 가상 `seq` 생성 (채택)
- **설명**: `ROW_NUMBER() OVER(...)`를 사용하여 쿼리 결과에 `seq` 컬럼을 포함시킴.
- **장점**: 
  - 기존 JSP의 그리드 설정(번호 컬럼)을 거의 수정하지 않아도 됨.
  - DB 스키마 변경 없이 즉시 적용 가능.
- **단점**: 
  - 페이징 처리 시 `ROW_NUMBER()`의 위치에 따라 성능 영향이 있을 수 있음 (현재 구조에서는 서브쿼리 내에서 처리하므로 영향 미비).

### 대안 2: DB 스키마에 `seq` 컬럼 추가
- **설명**: 모든 로그 테이블에 `BIGINT` 타입의 `seq` 컬럼을 추가하고 데이터 마이그레이션 수행.
- **장점**: 
  - 정렬 성능이 가장 최적화됨.
  - 쿼리가 단순해짐.
- **단점**: 
  - 운영 중인 DB 스키마 변경에 따른 리스크 존재.
  - 데이터 마이그레이션 비용 발생.
  - 요구사항 범위(Out of scope)를 벗어남.

**결정**: **대안 1**을 적용한다.
