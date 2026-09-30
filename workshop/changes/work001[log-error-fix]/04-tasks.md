# 구현 태스크 목록

## TASK-01: SQL 매퍼 파일 수정 (에러 로그)
- **목적**: `tbsy_error_log` 조회 시 발생하는 `seq` 컬럼 오류 수정
- **대상 파일**: `src/main/resources/sqlmap/mapper/system/logs-mapper.xml`
- **선행 태스크**: 없음
- **완료 기준**: `selectErrorLogListSql` 쿼리에서 `ORDER BY seq`가 `ORDER BY in_dtm DESC`로 변경되었는지 확인

## TASK-02: SQL 매퍼 파일 수정 (액션 로그)
- **목적**: `tbsy_action_log` 조회 시 발생하는 `seq` 컬럼 오류 수정
- **대상 파일**: `src/main/resources/sqlmap/mapper/system/logs-mapper.xml`
- **선행 태스크**: 없음
- **완료 기준**: `selectActionLogListSql` 쿼리에서 `ORDER BY seq`가 `ORDER BY in_dtm DESC`로 변경되었는지 확인

## TASK-03: SQL 매퍼 파일 수정 (로그인 이력)
- **목적**: `tbsy_login_hist` 조회 시 발생 가능한 `seq` 컬럼 오류 선제적 수정
- **대상 파일**: `src/main/resources/sqlmap/mapper/system/logs-mapper.xml`
- **선행 태스크**: 없음
- **완료 기준**: `selectLoginHistListSql` 쿼리에서 `ORDER BY seq`가 `ORDER BY in_dtm DESC`로 변경되었는지 확인

## TASK-04: 통합 기능 테스트
- **목적**: 수정된 쿼리가 정상 작동하며 페이징 및 검색 조건이 유지되는지 검증
- **대상 파일**: (UI/API 테스트)
- **선행 태스크**: TASK-01, TASK-02, TASK-03
- **완료 기준**: 
    - '에러로그', '액션로그', '로그인이력' 메뉴 접속 시 SQL 에러 없이 리스트가 출력됨
    - 최신 데이터가 상단에 정렬됨 (`in_dtm DESC`)
    - 검색 조건 적용 및 페이징 동작 정상 확인
