# 요구사항 정의서 (Requirements Specification)

## 1. 배경 및 목적
- 로그 조회 메뉴(로그인이력, 액션로그, 에러로그)에서 SQL 문법 오류(`Unknown column 'seq'`)로 인해 데이터 조회가 실패하는 현상이 발생함.
- 해당 오류를 수정하여 사용자가 로그 데이터를 정상적으로 조회할 수 있도록 함.

## 2. 기능 요구사항 (Functional Requirements)

### FR-01: 에러 로그 리스트 조회 기능 수정
- **Given**: 사용자가 에러 로그 조회 메뉴를 호출하고 검색 조건(회사코드, 사원번호, 기간 등)을 입력했을 때
- **When**: `getErrorLogList.ajax` 요청이 서버로 전달되어 `selectErrorLogList` 쿼리가 실행될 때
- **Then**: `ORDER BY seq desc` 구문에서 발생하는 `Unknown column 'seq'` 오류를 해결하여 에러 로그 리스트가 정상적으로 반환되어야 한다.

### FR-02: 로그인 이력 리스트 조회 기능 수정
- **Given**: 사용자가 로그인 이력 조회 메뉴를 호출하고 검색 조건을 입력했을 때
- **When**: `getLoginHistList.ajax` 요청이 서버로 전달되어 `selectLoginHistList` 쿼리가 실행될 때
- **Then**: `ORDER BY seq desc` 구문에서 발생하는 `Unknown column 'seq'` 오류를 해결하여 로그인 이력 리스트가 정상적으로 반환되어야 한다.

### FR-03: 액션 로그 리스트 조회 기능 수정
- **Given**: 사용자가 액션 로그 조회 메뉴를 호출했을 때
- **When**: 액션 로그 조회 관련 쿼리가 실행될 때
- **Then**: 로그 파일의 에러 패턴과 동일하게 `seq` 컬럼 관련 SQL 문법 오류를 해결하여 액션 로그 리스트가 정상적으로 반환되어야 한다.

## 3. 비기능 요구사항 (Non-Functional Requirements)
- **감사로그**: 에러 발생 시 기존의 `ExceptionHandler`를 통한 로그 기록 메커즘을 유지한다.

## 4. 범위 제외 항목 (Out of Scope)
- 로그 데이터의 내용(데이터 자체)에 대한 정합성 검증 및 수정.
- 로그 조회 화면의 UI/UX 디자인 변경.

## 5. 확인 필요 질문
1. `ORDER BY seq`에서 사용하고자 했던 `seq` 컬럼이 실제 DB 테이블에 존재하는 컬럼입니까? 아니면 특정 기준을 의미하는 별칭(Alias)입니까?
2. `seq` 컬럼을 사용할 수 없는 경우, 대체할 정렬 기준 컬럼(예: `in_dtm` 등)이 무엇인지 확인 부탁드립니다.
