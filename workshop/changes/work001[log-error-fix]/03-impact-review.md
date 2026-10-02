# 영향도 분석 검수 결과 (work001 - log-error-fix)

## 판정
- 판정: 통과 (pass=true)
- 고쳐야 할 문서: 없음

## 요청서 범위 확인
| 요청서의 메뉴/기능 | 다룬 FR 번호 |
|--------------------|--------------|
| 테스트>로그조회>로그인이력 | FR-01 |
| 테스트>로그조회>액션로그 | FR-02 |
| 테스트>로그조회>에러로그 | FR-03 |

- 요청서의 메뉴 3건 모두 FR로 빠짐없이 들어 있음(누락 없음).

## FR별 판정
| FR 번호 | 충족/미충족/확인불가 | 근거 |
|---------|----------------------|------|
| FR-01 | 충족 | 영향도 분석서 변경 대상에 FR-01이 매핑됨. selectLoginHistListSql(logs-mapper.xml:84~102)의 SELECT 목록(86~91행)에 seq가 없고, 바깥 ORDER BY seq desc(comm-mapper.xml:17~18, 실행 SQL은 error.log:471)가 컬럼을 못 찾아 오류가 남. SELECT 목록에 tb1.seq를 추가하면 ORDER BY가 참조할 컬럼이 생기고 그리드 seq 필드(loginHist.jsp:132)에 값이 채워져 FR-01 Then을 만족함. |
| FR-02 | 충족 | 변경 대상에 FR-02 매핑됨. selectActionLogListSql(logs-mapper.xml:122~146) SELECT 목록(124~132행)에 seq 없음, 실행 SQL ORDER BY seq desc(error.log:1177 부근). tb1.seq 추가로 해결. 그리드 seq 필드 actionLog.jsp:129. |
| FR-03 | 충족 | 변경 대상에 FR-03 매핑됨. selectErrorLogListSql(logs-mapper.xml:155~183) SELECT 목록(157~169행)에 seq 없음, 실행 SQL ORDER BY seq desc(error.log:1887). tb1.seq 추가로 해결. 그리드 seq 필드 errorLog.jsp:132. |

## 사실 확인 (문서가 적은 위치를 직접 열어 대조)
- 화면 sort_column=seq desc: loginHist.jsp:63, actionLog.jsp:63, errorLog.jsp:63 — 모두 `value="seq desc"` 확인.
- 그리드 seq 필드: loginHist.jsp:132, actionLog.jsp:129, errorLog.jsp:132 — 모두 `field:"seq"` 확인.
- 호출 URL: loginHist.jsp:92 `/logs/getLoginHistList.ajax`, actionLog.jsp:92 `/logs/getActionLogList.ajax`, errorLog.jsp:92 `/logs/getErrorLogList.ajax` 확인.
- Controller: LogsController.java:49~50(getLoginHistList), :95~96(getActionLogList), :141~142(getErrorLogList — 에러로그용 오버로드, 메서드명 getActionLogList 동일) 확인.
- Service: LogsService.java:54~57, :94~97, :107~110 확인.
- DAO: LogsDAO.java:45~46, :79~80, :90~91 확인.
- Mapper: logs-mapper.xml:84(selectLoginHistListSql), :92(FROM tbsy_login_hist tb1), :104~108(selectLoginHistList = PagingStart+Sql+PagingEnd), :122/:133/:148~152, :155/:170/:185~189 확인.
- 공통 조각: comm-mapper.xml:17~18 `<if ...sort_column...> ORDER BY ${sort_column}` 확인.
- DB 정의: schema.sql:86~87(tbsy_login_hist.seq), :223~224(tbsy_action_log.seq), :237~238(tbsy_error_log.seq) 확인 — 테이블에 seq 존재.
- 오류 로그: error.log:463(getLoginHistList.ajax), :1169(getActionLogList.ajax), :1879(getErrorLogList.ajax) URL 3종 확인. 오류 문구 `Unknown column 'seq' in 'order clause'`(error.log:70, :467, :1883 등) 확인. request params에 `"sort_column":"seq desc"`(error.log:1908) 확인. 실행 SQL이 `... ) A ORDER BY seq desc ) AA LIMIT 20 OFFSET 0`(error.log:471, :1887) 형태임을 확인.
- 영향 범위: Comm.PagingEnd 사용처 member-mapper.xml:119(selectMemberListPaging) 확인. selectMemberSql(member-mapper.xml:5~6)에 `SELECT tb1.seq`가 이미 있어 영향 없음 서술도 사실과 일치. member-mapper.xml:118 자체 ORDER BY 확인.

## 지적 사항
| 심각도 | 대상 문서 | 파일 절대경로:줄 번호 | 문제 | 소스에서 확인한 사실 | 어떻게 고쳐야 하는지 |
|--------|-----------|----------------------|------|---------------------|---------------------|
| (없음) | - | - | 차단/주요/경미 지적 없음 | - | - |

## 앞선 지적 반영 확인
- 앞선 리뷰 파일(03-impact-review.md)이 존재하지 않아 해당 없음.

## 종합
- 요청서 범위 3건 모두 FR로 매핑, FR-01~03 모두 변경 대상에 매핑됨(미충족 FR 없음).
- 문서가 적은 파일/줄 위치와 오류 로그·소스 사실이 모두 일치.
- 원인이 "화면이 seq desc 전송 → 공통 페이징 ORDER BY → 조회 결과에 seq 없음 → Unknown column"으로 끊김 없이 이어짐.
- 변경 내용(tb1.seq를 SELECT 목록에 추가)대로 고치면 세 FR의 인수 조건(SQL 오류 없이 목록 표시 + seq 값 표시)이 만족됨.
- 공통 코드(Comm.PagingEnd) 사용처(회원사관리)가 영향 범위에 명시되어 있고, 변경이 공통 조각을 건드리지 않아 영향 없음.
- 차단 지적 없음 → 통과.
