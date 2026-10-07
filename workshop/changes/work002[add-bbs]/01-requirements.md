# 요구사항 정의서 - 신규 FAQ 기능 개발 (work002[add-bbs])

## 1. 배경/목적
고객은 신규 FAQ 기능을 개발해 줄 것을 요청했다. 화면은 기존 샘플게시판과 유사하게 만들고, 수정/삭제 기능은 두지 않고 추가할 테이블의 내용을 보여주는 조회 기능만 제공한다. 메뉴는 '테스트 > FAQ' 위치에 둔다.

## 2. 요청 범위
| 번호 | 요청서의 메뉴/기능 | 다루는 FR 번호 |
|------|-------------------|----------------|
| 1 | FAQ 테이블 설계 (신규 테이블) | FR-01 |
| 2 | FAQ 화면 (기존 샘플게시판과 유사, 수정/삭제 없이 내용 조회만) | FR-03, FR-04 |
| 3 | 메뉴 위치: 테스트 > FAQ (메뉴 등록) | FR-02 |

## 3. 현상과 확인한 사실
신규 기능 요청이므로 오류 현상은 없으며, 각 항목의 현재 상태와 참고할 기존 소스 위치를 확인했다.

### 3-1. FAQ 테이블 설계 (요청 범위 1)
- 현재 소스 어디에도 FAQ 관련 테이블/코드가 없다. `D:/AppHome/testApp` 전체(144개 파일)에서 'faq' 문자열 검색 결과 0건이다.
- 참고 대상 테이블(샘플게시판): `tbts_sample_board` - `D:/AppHome/testApp/db/schema.sql:282-294`
  - 컬럼: seq(BIGINT, PK, AUTO_INCREMENT), mb_id, title(VARCHAR 200), content(VARCHAR 4000), writer_nm(VARCHAR 50), use_yn(기본 'Y'), in_emp_cd, in_dtm, up_emp_cd, up_dtm
- 스키마 파일: `D:/AppHome/testApp/db/schema.sql` (전체 294줄, FAQ 테이블 없음)

### 3-2. FAQ 화면 (요청 범위 2)
- 현재 FAQ 화면은 존재하지 않는다(위 검색 결과 참조).
- 참고 대상 화면(샘플게시판): `D:/AppHome/testApp/src/main/webapp/WEB-INF/views/sample/board/sampleBoard.jsp`
  - 검색박스(제목/내용/작성자 검색어 `search_word`): 25-33줄
  - Kendo 그리드 컬럼(번호/제목/내용/작성자/등록일시/수정일시): 45-51줄
  - 툴바 '추가' 버튼: 44줄, 그리드 명령컬럼 '수정/삭제': 52-59줄 (FAQ에서는 제외 대상)
  - 데이터 소스 URL(read/update/destroy/create): 71-77줄
- 참고 대상 컨트롤러: `D:/AppHome/testApp/src/main/java/kr/co/gnx/sample/board/SampleBoardController.java`
  - 화면 이동 `/sample/board/sampleBoard.go`: 40-43줄
  - 목록 조회 `/sample/board/getSampleBoardList.ajax`: 48-61줄 (57줄에서 로그인 세션의 mb_id를 조회 조건으로 설정)
  - 단건 조회 `/sample/board/getSampleBoardView.ajax`: 66-73줄
- 참고 대상 SQL: `D:/AppHome/testApp/src/main/resources/sqlmap/mapper/sample/sampleboard-mapper.xml`
  - 목록 조회 `selectSampleBoardList`: 8-26줄 (use_yn='Y' 필터, search_word로 title/content/writer_nm 검색, seq DESC 정렬)
  - 단건 조회 `selectSampleBoardView`: 28-41줄
- 신규 매퍼 XML은 `D:/AppHome/testApp/src/main/resources/spring/context-sqlMap.xml:24`의 `mapperLocations=classpath*:/sqlmap/mapper/**/*-mapper.xml` 설정에 의해 자동 로드된다.
- 참고 대상 DAO/VO: `D:/AppHome/testApp/src/main/java/kr/co/gnx/sample/board/SampleBoardDAO.java` (전체 31줄), `D:/AppHome/testApp/src/main/java/kr/co/gnx/sample/board/SampleBoardVO.java` (전체 33줄, title/content/writer_nm + BaseVO 공통 필드)

### 3-3. 메뉴 위치: 테스트 > FAQ (요청 범위 3)
- 메뉴는 `tbsy_resource` 테이블로 관리한다: `D:/AppHome/testApp/db/schema.sql:266-279` (mb_id, resource_id, prnt_resource_id, resource_name, resource_url, sort_no, use_yn 등)
- '테스트' 최상위 메뉴는 이미 존재: resource_id=TS_000 - `D:/AppHome/testApp/db/seed.sql:75`
- 그 아래 '샘플게시판' 하위메뉴 존재: resource_id=TS_100, resource_url=/sample/board/sampleBoard.go - `D:/AppHome/testApp/db/seed.sql:76`
- FAQ 메뉴는 현재 존재하지 않는다(seed.sql 67-76줄의 메뉴 데이터에 FAQ 없음).

## 4. 기능 요구사항

### FR-01 FAQ 테이블 설계
신규 FAQ 내용을 저장할 테이블을 설계한다.
- Given: FAQ 기능을 위해 내용을 저장할 테이블이 아직 DB에 없는 상태
- When: 신규 FAQ 테이블의 DDL을 DB에 적용하면
- Then: FAQ 내용(제목, 내용 등)을 저장할 수 있는 테이블이 DB에 존재하고, FAQ 화면에서 이 테이블의 내용을 조회해 표시할 수 있다
- 참고: 테이블의 구체적 컬럼 구성은 미정(확인 필요 질문 Q1)

### FR-02 FAQ 메뉴 등록 (테스트 > FAQ)
'테스트' 최상위 메뉴 아래 'FAQ' 하위메뉴를 등록한다.
- Given: 메뉴가 tbsy_resource 테이블로 관리되고 '테스트' 최상위 메뉴(TS_000)가 존재하는 상태
- When: 사용자가 로그인 후 메뉴를 보면
- Then: '테스트' 메뉴 아래 'FAQ' 메뉴가 표시되고, 클릭하면 FAQ 화면이 열린다

### FR-03 FAQ 목록 조회 화면
샘플게시판과 유사한 화면으로 FAQ 테이블의 내용을 표시한다.
- Given: FAQ 테이블에 FAQ 데이터가 존재하는 상태
- When: 사용자가 '테스트 > FAQ' 메뉴를 열면
- Then: 샘플게시판 화면과 유사한 그리드로 FAQ 목록(제목, 내용 등)이 표시되고, 검색어로 검색해 목록을 걸러 볼 수 있다
- 참고: 검색 대상 필드는 미정(확인 필요 질문 Q4)

### FR-04 수정/삭제 기능 없음
FAQ 화면에는 수정/삭제(및 추가) 기능이 제공되지 않는다.
- Given: FAQ 화면이 열린 상태
- When: 사용자가 화면을 보면
- Then: '추가/수정/삭제' 버튼이나 그리드 명령컬럼이 없고, FAQ 데이터를 수정하거나 삭제할 수 있는 경로가 화면에 없다

## 5. 비기능 요구사항
- 보안: FAQ 화면은 로그인한 사용자만 접근할 수 있어야 한다(참고: 샘플게시판은 로그인 세션으로 조회를 제어 - `D:/AppHome/testApp/src/main/java/kr/co/gnx/sample/board/SampleBoardController.java:57`). 데이터 표시 범위(회사별 분리 여부)는 확인 필요(Q2).
- 성능: 해당 없음(요청서에 명시 없음)
- 감사로그: 해당 없음(조회 전용으로 수정/삭제 작업이 없음)

## 6. 범위 제외 항목 (Out of scope)
- FAQ 수정/삭제 기능 (요청서에 "수정/삭제 기능은 필요없고" 명시)
- FAQ 등록(추가) 기능 (요청서에 "추가할 테이블의 내용을 보여주는 기능이면 됨" - 조회 전용)
- 기존 샘플게시판 기능의 변경

## 7. 확인 필요 질문
1. FAQ 테이블에 어떤 컬럼이 필요한가? (샘플게시판과 동일하게 제목/내용/작성자만인지, 카테고리/조회수 등 추가 필드가 필요한지)
2. FAQ 데이터는 회사(mb_id)별로 분리해서 표시할 것인가(샘플게시판과 동일), 아니면 모든 회사가 공유하는 단일 데이터인가?
3. 등록/수정/삭제 기능이 없으므로 FAQ 데이터는 어떻게 입력하는가? (DB 직접 등록/초기 데이터, 또는 별도 관리 화면이 별도로 필요한가)
4. FAQ 검색 조건은 샘플게시판과 동일하게 제목/내용/작성자로 할 것인가?
5. FAQ 메뉴는 모든 사용자에게 표시할 것인가, 일부 권한(역할)만 볼 수 있게 할 것인가?
