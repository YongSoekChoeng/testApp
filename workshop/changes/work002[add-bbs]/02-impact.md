# 영향도 분석서 - 신규 FAQ 기능 개발 (work002[add-bbs])

- 분석 대상: `D:/AppHome/testApp` (프로젝트ID: testApp)
- 요구사항: `D:/AppHome/testApp/workshop/changes/work002[add-bbs]/01-requirements.md`
- FR 목록: FR-01, FR-02, FR-03, FR-04 (4개, 전부 다룸)

## 요구사항과 다른 점

없음. 요구사항 정의서 3절의 서술(FAQ 관련 코드 0건, tbts_sample_board 정의 위치, sampleBoard.jsp 구조, tbsy_resource/seed.sql 메뉴 데이터, mapper 자동 로드 설정)은 모두 소스에서 확인한 사실과 일치한다.

## FR별 대응 표

| FR 번호 | 원인 한 줄 | 변경 대상 파일 | 변경 내용 한 줄 |
|---------|-----------|----------------|-----------------|
| FR-01 | FAQ를 저장할 테이블이 DB에 존재하지 않음(schema.sql 294줄 전체에 FAQ 테이블 없음) | D:/AppHome/testApp/db/schema.sql | tbts_sample_board(282-294줄)와 동일한 구조의 신규 테이블 `tbts_faq` DDL을 파일 끝에 추가한다 |
| FR-02 | tbsy_resource에 '테스트'(TS_000) 하위 FAQ 메뉴 행이 없어 getMenuList가 FAQ를 반환하지 못함(seed.sql 67-76줄에 FAQ 없음) | D:/AppHome/testApp/db/seed.sql | 76줄의 TS_100 행 뒤에 `('PRD','TS_200','TS_000','FAQ','/faq/faqList.go',20,'Y','ADMIN001',NOW())` 행을 추가한다 |
| FR-03 | FAQ 화면/Controller/쿼리가 존재하지 않아 FAQ 테이블 내용을 조회해 표시할 경로가 없음 | D:/AppHome/testApp/src/main/webapp/WEB-INF/views/faq/faqList.jsp, D:/AppHome/testApp/src/main/java/kr/co/gnx/faq/FaqController.java, D:/AppHome/testApp/src/main/java/kr/co/gnx/faq/FaqService.java, D:/AppHome/testApp/src/main/java/kr/co/gnx/faq/FaqDAO.java, D:/AppHome/testApp/src/main/java/kr/co/gnx/faq/FaqVO.java, D:/AppHome/testApp/src/main/resources/sqlmap/mapper/faq/faq-mapper.xml | sampleBoard 기반 조회 전용 모듈을 신규 생성한다: JSP(검색박스+Kendo 그리드, read만), Controller(/faq/faqList.go 화면이동, /faq/getFaqList.ajax 목록조회, 로그인 세션 mb_id 설정), Service/DAO, VO, 매퍼(selectFaqList: tbts_faq에서 use_yn='Y' + search_word 조건, seq DESC) |
| FR-04 | 신규 FAQ 화면에 추가/수정/삭제 UI와 URL을 두지 않으면 조회 전용이 되므로, sampleBoard.jsp의 toolbar '추가'(44줄)·command컬럼 '수정/삭제'(52-59줄)·update/destroy/create transport(74-76줄)를 그대로 복사하지 않아야 함 | D:/AppHome/testApp/src/main/webapp/WEB-INF/views/faq/faqList.jsp, D:/AppHome/testApp/src/main/java/kr/co/gnx/faq/FaqController.java, D:/AppHome/testApp/src/main/resources/sqlmap/mapper/faq/faq-mapper.xml | faqList.jsp에는 toolbar/command컬럼/editable/update·destroy·create transport를 두지 않고 read transport만 두고, FaqController에는 insert/update/delete 메서드를 만들지 않으며, faq-mapper.xml에는 insert/update 쿼리를 만들지 않는다 |

## 원인

### FR-01 FAQ 테이블 설계
FAQ 내용을 저장할 테이블이 DB에 존재하지 않는다. 테이블 생성 스크립트 `D:/AppHome/testApp/db/schema.sql` 전체(294줄)를 확인한 결과 FAQ 테이블 DDL이 없으며(282-294줄은 `tbts_sample_board`가 마지막 테이블), `D:/AppHome/testApp` 전체에서 'faq' 문자열 검색 결과도 0건이다. 참고 대상 `tbts_sample_board`는 `D:/AppHome/testApp/db/schema.sql:282-294`에 정의되어 있고 컬럼은 seq(BIGINT PK AUTO_INCREMENT), mb_id, title(VARCHAR 200), content(VARCHAR 4000), writer_nm(VARCHAR 50), use_yn(기본 'Y'), in_emp_cd, in_dtm, up_emp_cd, up_dtm이다. "테이블 정의에 있는지": FAQ 테이블은 정의에 없다(신규 생성 대상). "조회 결과(SELECT 목록)에 있는지": 테이블 자체가 없으므로 조회 대상도 없다.

### FR-02 FAQ 메뉴 등록 (테스트 > FAQ)
메뉴는 `tbsy_resource` 테이블로 관리된다(`D:/AppHome/testApp/db/schema.sql:266-279`, PK는 mb_id+resource_id). index.jsp가 `/menu/getMenuList.ajax`를 호출하고(`D:/AppHome/testApp/src/main/webapp/WEB-INF/views/main/index.jsp:146`), `MenuController#getMenuList`(`D:/AppHome/testApp/src/main/java/kr/co/gnx/system/menu/MenuController.java:35-51`)가 로그인 세션의 mb_id로 `MenuService#getMenuList` → `MenuDAO#selectMenuList` → 쿼리ID `Menu.selectMenuList`(`D:/AppHome/testApp/src/main/resources/sqlmap/mapper/system/menu-mapper.xml:11-47`, 재귀 CTE로 prnt_resource_id 트리 전개, use_yn='Y'만)를 실행한다. 시드 데이터 `D:/AppHome/testApp/db/seed.sql:67-76`에 '테스트' 최상위 메뉴 TS_000(75줄)과 하위 '샘플게시판' TS_100(76줄)만 있고 FAQ 행이 없으므로, selectMenuList 결과에 FAQ가 포함되지 않아 메뉴가 표시되지 않는다. "테이블 정의에 있는지": tbsy_resource 테이블 정의에는 FAQ 관련 컬럼이 별도로 필요하지 않다(기존 컬럼 resource_id/prnt_resource_id/resource_name/resource_url/sort_no/use_yn로 충분). "조회 결과(SELECT 목록)에 있는지": selectMenuList의 SELECT 목록(menu-mapper.xml 36-46줄)은 resource_id, prnt_resource_id, resource_name, resource_url, lv, isleaf, menu_path이며, FAQ 행이 INSERT되면 이 SELECT에 자동으로 포함된다(행 데이터 문제이지 SELECT 목록 누락 문제가 아님).

### FR-03 FAQ 목록 조회 화면
FAQ 화면/Controller/쿼리가 존재하지 않는다('faq' 검색 0건). 참고 모델인 샘플게시판의 흐름은: `sampleBoard.jsp`의 Kendo 그리드 read URL `/sample/board/getSampleBoardList.ajax`(`D:/AppHome/testApp/src/main/webapp/WEB-INF/views/sample/board/sampleBoard.jsp:73`) → `SampleBoardController#getSampleBoardList`(`D:/AppHome/testApp/src/main/java/kr/co/gnx/sample/board/SampleBoardController.java:48-61`, 57줄에서 로그인 세션 mb_id를 VO에 설정) → `SampleBoardService#getSampleBoardList`(`D:/AppHome/testApp/src/main/java/kr/co/gnx/sample/board/SampleBoardService.java:22-24`) → `SampleBoardDAO#selectSampleBoardList`(`D:/AppHome/testApp/src/main/java/kr/co/gnx/sample/board/SampleBoardDAO.java:15-17`) → 쿼리ID `SampleBoard.selectSampleBoardList`(`D:/AppHome/testApp/src/main/resources/sqlmap/mapper/sample/sampleboard-mapper.xml:8-26`, tbts_sample_board에서 mb_id+use_yn='Y'+search_word 조건, seq DESC). 신규 매퍼 XML은 `D:/AppHome/testApp/src/main/resources/spring/context-sqlMap.xml:24`의 `mapperLocations=classpath*:/sqlmap/mapper/**/*-mapper.xml`에 의해 자동 로드되므로 별도 등록 불필요. "테이블 정의에 있는지": FAQ 테이블은 정의에 없다(FR-01에서 신규 생성). "조회 결과(SELECT 목록)에 있는지": 신규 쿼리 selectFaqList를 작성할 때 SELECT 목록에 seq/title/content/writer_nm/in_dtm을 포함시키면 되며, FR-01의 DDL과 컬럼명이 일치해야 한다.

### FR-04 수정/삭제 기능 없음
샘플게시판 화면에는 추가/수정/삭제 경로가 3곳에 있다: ① JSP의 toolbar '추가' 버튼(`sampleBoard.jsp:44`)과 그리드 command컬럼 '수정/삭제'(52-59줄), editable 설정(60-64줄), ② JSP transport의 update/destroy/create URL(74-76줄), ③ Controller의 insert/update/delete 메서드(`SampleBoardController.java:78-85, 90-97, 102-109`)와 매퍼의 insert/update 쿼리(`sampleboard-mapper.xml:43-89`). FAQ 모듈은 이 3곳을 모두 만들지 않으면 조회 전용이 된다. "테이블 정의에 있는지": FAQ 테이블 DDL은 up_emp_cd/up_dtm 컬럼을 포함하되(샘플게시판과 동일 구조, FR-01) 이를 쓰는 UPDATE 쿼리는 만들지 않는다. "조회 결과(SELECT 목록)에 있는지": selectFaqList의 SELECT 목록에는 up_dtm을 포함하지 않는다(수정 기능이 없으므로 '수정일시' 컬럼은 표시 대상에서 제외).

## 진입점 (화면/API/배치) 목록

신규 FAQ 기능의 진입점(변경 후):

| 메뉴 | 화면 파일 | URL | Controller.메서드 | 쿼리ID |
|------|-----------|-----|-------------------|--------|
| 테스트 > FAQ (신규) | D:/AppHome/testApp/src/main/webapp/WEB-INF/views/faq/faqList.jsp (신규) | /faq/faqList.go (신규) | kr.co.gnx.faq.FaqController.faqList (신규) | - |
| 테스트 > FAQ 목록조회 (신규) | (위 JSP의 그리드 read) | /faq/getFaqList.ajax (신규) | kr.co.gnx.faq.FaqController.getFaqList (신규) | Faq.selectFaqList (신규, faq-mapper.xml) |

참고용 기존 진입점(변경 없음):

| 메뉴 | 화면 파일 | URL | Controller.메서드 | 쿼리ID |
|------|-----------|-----|-------------------|--------|
| 테스트 > 샘플게시판 | D:/AppHome/testApp/src/main/webapp/WEB-INF/views/sample/board/sampleBoard.jsp | /sample/board/sampleBoard.go | SampleBoardController.sampleBoard (40-43줄) | - |
| 샘플게시판 목록조회 | (위 JSP) | /sample/board/getSampleBoardList.ajax | SampleBoardController.getSampleBoardList (48-61줄) | SampleBoard.selectSampleBoardList |
| 상단/좌측 메뉴 | D:/AppHome/testApp/src/main/webapp/WEB-INF/views/main/index.jsp | /menu/getMenuList.ajax | MenuController.getMenuList (35-51줄) | Menu.selectMenuList |

## 변경 대상

| 파일 절대경로 | 변경 유형 | 변경 내용 | 해결하는 FR 번호 |
|---------------|-----------|-----------|-----------------|
| D:/AppHome/testApp/db/schema.sql | 수정 | 파일 끝(294줄 이후)에 `CREATE TABLE tbts_faq (seq BIGINT NOT NULL AUTO_INCREMENT, mb_id VARCHAR(20) NOT NULL, title VARCHAR(200) NOT NULL, content VARCHAR(4000) NULL, writer_nm VARCHAR(50) NULL, use_yn VARCHAR(1) NOT NULL DEFAULT 'Y', in_emp_cd VARCHAR(20) NULL, in_dtm DATETIME NULL, up_emp_cd VARCHAR(20) NULL, up_dtm DATETIME NULL, PRIMARY KEY (seq))` DDL을 추가한다(컬럼은 tbts_sample_board 282-294줄과 동일) | FR-01 |
| D:/AppHome/testApp/db/seed.sql | 수정 | 76줄의 TS_100 VALUES 행 뒤에 `('PRD', 'TS_200', 'TS_000', 'FAQ', '/faq/faqList.go', 20, 'Y', 'ADMIN001', NOW())` 행을 추가한다(테이블 tbts_faq의 초기 데이터 INSERT도 함께 추가) | FR-02 |
| D:/AppHome/testApp/src/main/webapp/WEB-INF/views/faq/faqList.jsp | 신규 | sampleBoard.jsp를 본뜬 조회 전용 JSP: 검색박스(search_word, 제목/내용/작성자) + Kendo 그리드(번호/제목/내용/작성자/등록일시 컬럼), transport는 read=/faq/getFaqList.ajax 하나만, toolbar/command컬럼/editable/update/destroy/create 없음 | FR-03, FR-04 |
| D:/AppHome/testApp/src/main/java/kr/co/gnx/faq/FaqController.java | 신규 | @Controller: /faq/faqList.go → ModelAndView("/faq/faqList"), /faq/getFaqList.ajax → json_string 파싱 후 vo.setMb_id(로그인 세션) → faqService.getFaqList(vo)를 'results'로 반환. insert/update/delete 메서드 없음 | FR-03, FR-04 |
| D:/AppHome/testApp/src/main/java/kr/co/gnx/faq/FaqService.java | 신규 | @Service: getFaqList(FaqVO)가 faqDAO.selectFaqList(vo) 호출. insert/update/delete 없음 | FR-03 |
| D:/AppHome/testApp/src/main/java/kr/co/gnx/faq/FaqDAO.java | 신규 | @Repository(BaseDAO 상속): selectFaqList가 getSqlSession().selectList("Faq.selectFaqList", vo) 실행 | FR-03 |
| D:/AppHome/testApp/src/main/java/kr/co/gnx/faq/FaqVO.java | 신규 | BaseVO 상속, title/content/writer_nm 필드(SampleBoardVO와 동일 구성) | FR-03 |
| D:/AppHome/testApp/src/main/resources/sqlmap/mapper/faq/faq-mapper.xml | 신규 | namespace="Faq", <select id="selectFaqList">: SELECT seq, title, content, writer_nm, DATE_FORMAT(in_dtm,...) AS in_dtm FROM tbts_faq WHERE mb_id=#{mb_id} AND use_yn='Y' [+ search_word로 title/content/writer_nm LIKE] ORDER BY seq DESC. insert/update 쿼리 없음. context-sqlMap.xml:24의 mapperLocations에 의해 자동 로드 | FR-03, FR-04 |

## 영향 받는 호출자·연계 시스템·DB 객체

- 신규 파일(FAQ 모듈 6개)은 기존 어떤 코드에서도 참조되지 않으므로 기존 기능에 대한 호출자 영향은 없다.
- seed.sql 수정: tbsy_resource에 1행 추가. 이 테이블을 읽는 곳은 `Menu.selectMenuList`(menu-mapper.xml:11-47)뿐이며, 실행 경로 index.jsp → /menu/getMenuList.ajax → MenuController#getMenuList → MenuService#getMenuList → MenuDAO#selectMenuList(knowledgeImpact 결과, HIGH 신뢰도)이다. 영향: 로그인 사용자의 '테스트' 메뉴에 FAQ 하위메뉴가 1개 추가 표시되는 것뿐. 기존 메뉴 행은 건드리지 않는다.
- schema.sql 수정: DDL 1개 추가만이라 기존 테이블/쿼리에 영향 없음.
- DB 객체:
  - 변경/조회 대상: `tbts_faq`(신규, selectFaqList가 SELECT), `tbsy_resource`(seed.sql에 1행 INSERT, selectMenuList가 SELECT)
  - 조인만 하는 대상: 없음(selectFaqList는 단일 테이블 조회, selectMenuList의 재귀 CTE도 tbsy_resource 자기 자신 조인만)

## 검토한 대안

| 대안 | 고르지 않은 이유 |
|------|-----------------|
| FAQ 테이블을 tbts_sample_board를 재사용(신규 테이블 없이) | 요청서가 "추가할 테이블"을 명시했고, FAQ 데이터와 샘플게시판 테스트 데이터가 섞이며 샘플게시판 화면에서 FAQ 데이터가 함께 조회되어 기존 기능 동작이 바뀜 |
| FAQ 메뉴를 seed.sql이 아니라 tbsy_resource에 직접 INSERT만(운영 DB 스크립트) | 이 프로젝트의 DB 초기화 방식이 schema.sql+seed.sql이며, 재설치 시 메뉴가 사라짐. seed.sql에 넣어 재현 가능하게 함 |
| FAQ 화면을 sampleBoard.jsp를 그대로 복사하고 JSP에서 버튼만 제거 | JSP의 transport에 update/destroy/create URL이 남아 서버 경로가 노출되고, Controller/매퍼의 insert/update 쿼리까지 삭제해야 완전해짐. 처음부터 조회 전용으로 작성하는 것이 영향 범위 최소 |
| 검색 조건을 제목만(내용/작성자 제외) | 요구사항 Q4가 미확정이나 FR-03은 "검색어로 검색해 목록을 걸러 볼 수 있다"고만 했고, 참고 모델(샘플게시판)과 동일하게 제목/내용/작성자로 두는 것이 요청서 "기존의 샘플게시판과 유사"에 가장 가까움 |

## 재사용 가능한 기존 컴포넌트

- `kr.co.gnx.base.BaseVO`(seq/mb_id/use_yn/in_emp_cd/in_dtm/up_emp_cd/up_dtm/search_word/json_string 공통 필드) - FaqVO가 상속 (`D:/AppHome/testApp/src/main/java/kr/co/gnx/sample/board/SampleBoardVO.java:10`의 BaseVO 상속 패턴)
- `kr.co.gnx.base.BaseDAO`(getSqlSession()) - FaqDAO가 상속 (SampleBoardDAO.java:10)
- `kr.co.gnx.base.BaseService` - FaqService가 상속 (SampleBoardService.java:15)
- `kr.co.gnx.comm.util.session.SessionUtil.getSessionVO(...).getUser()` - 로그인 사용자 mb_id 취득 (SampleBoardController.java:113-115)
- `kr.co.gnx.comm.util.CommUtil.isNotEmpty` - json_string/search_word 공백 체크 (SampleBoardController.java:54, sampleboard-mapper.xml:21)
- `genexon.initKendoUI_grid_inlineEdit` / `genexon.getSearchParameterToJsonString` - Kendo 그리드 초기화·검색 파라미터 (sampleBoard.jsp:20, 103)
- MyBatis 매퍼 자동 로드: `classpath*:/sqlmap/mapper/**/*-mapper.xml` (context-sqlMap.xml:24) - faq-mapper.xml을 해당 경로에 두면 등록 불필요

## 위험 요소와 회귀 테스트가 필요한 기존 기능

- seed.sql의 tbsy_resource INSERT 문 수정: VALUES 목록에 행을 추가할 때 기존 8행(68-76줄)의 문법(콤마 위치)을 깨면 전체 메뉴 INSERT가 실패하고 모든 메뉴가 사라진다. 회귀 테스트: 로그인 후 상단 메뉴(시스템관리/로그조회/테스트)와 하위메뉴가 기존과 동일하게 표시되는지.
- selectMenuList의 재귀 CTE는 lv<3까지만 전개(menu-mapper.xml:33) - FAQ는 lv2(TS_000>FAQ)라 문제없으나, sort_no=20으로 TS_100(10) 뒤에 정렬되도록 해야 '샘플게시판' 순서가 바뀌지 않는다. 회귀 테스트: '테스트' 메뉴의 하위 순서가 샘플게시판 → FAQ인지.
- 신규 매퍼 XML의 namespace("Faq")가 기존 namespace(SampleBoard, Menu 등)와 충돌하면 애플리케이션 기동이 실패한다. 회귀 테스트: 애플리케이션 기동 후 샘플게시판/메뉴/로그 등 기존 화면 1회 조회.
- FAQ 화면은 InterceptorAdapter의 로그인 세션 검사 대상이 된다(InterceptorAdapter.java:109 - 메뉴별 상세 권한은 제외, 로그인 여부만 검사). /faq/faqList.go와 /faq/getFaqList.ajax는 예외 URL 목록(196-216, 224-240줄)에 없으므로 로그인 전 접근 시 기존 .go/.ajax URL과 동일하게 처리된다. 회귀 테스트: 미로그인 상태에서 /faq/faqList.go 접근 시 로그인 화면으로 이동하는지.
- tbts_faq DDL은 기존 테이블과 무관하지만, schema.sql이 처음부터 실행되는 초기화 스크립트인지 확인해야 한다(파일 전체를 실행하면 기존 테이블은 DROP 없이 CREATE만이라 재실행 시 중복 오류 가능 - 기존 스크립트 특성과 동일하므로 신규 변경으로 인한 회귀는 아님).

## 확인 필요 사항 (요구사항 7절 질문과 관련)

- Q1(컬럼 구성), Q2(회사별 분리), Q4(검색 필드)는 본 분석에서 샘플게시판과 동일 구성(제목/내용/작성자, mb_id별 분리, 제목/내용/작성자 검색)으로 결정했다(검토한 대안 참조). 승인 시 이 전제가 바뀌면 tbts_faq DDL과 selectFaqList, FaqVO가 함께 바뀐다.
- Q3(데이터 입력 방식): 본 분석은 seed.sql에 FAQ 초기 데이터 INSERT를 포함하는 것으로 했다. 별도 관리 화면이 필요하면 범위 외 추가 작업.
- Q5(메뉴 권한): InterceptorAdapter.java:109에 "메뉴별 상세 권한 조회는 이 테스트 프로젝트 범위에서 제외 - 로그인 여부만으로 접근을 허용"이라, 로그인한 모든 회사(PRD) 사용자에게 표시되는 것으로 했다.
