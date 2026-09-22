package kr.co.gnx.interceptor;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.gnx.comm.util.CommUtil;
import kr.co.gnx.comm.util.propertiesVO;
import kr.co.gnx.comm.util.session.SessionUtil;
import kr.co.gnx.comm.util.session.SessionVO;
import kr.co.gnx.config.Constants;
import kr.co.gnx.config.Startup;
import kr.co.gnx.config.Constants.LOGGING;
import kr.co.gnx.logs.ActionLogVO;
import kr.co.gnx.logs.LogsService;
import kr.co.gnx.security.model.User;


/**
 * 
 * 최상위 진입점
 * Interceptor
 * @author kihon
 *
 */
public class InterceptorAdapter extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LogsService logsService;
	
	private propertiesVO vo = new propertiesVO();
	
	private String logerYN = vo.getLogerYN();
	
	@Override
	public boolean preHandle(HttpServletRequest request
	                          ,HttpServletResponse response
	                          ,Object handler){
		
		LOGGING queryLogging = Constants.QueryLogging;
    	
    	Constants.QueryLogging = LOGGING.NOLOGGING;
		
	    try {
	    	String url = request.getRequestURI();
	    	if(url.contains("/css/")
	    			|| url.contains("/js/")
	    			|| url.contains("/images/")
	    			|| url.contains("/kendoui/")
	    			|| url.contains(".jpg")
	    			|| url.contains(".png")
	    			|| url.contains(".gif")
	    			|| url.contains(".ico")
	    			|| url.contains("/UbiServer/")
	    			){
	    		return true;
	    	}
	    		    	
	    	Enumeration<String> paramsss = request.getParameterNames();
	    	logger.debug("---------- requests @ interceptor ----------");
	    	String params = "";
	    	while (paramsss.hasMoreElements()){
	    		String name = (String)paramsss.nextElement();
	    		params += ( name + "=" + request.getParameter(name) + "&");
	    	}
	    	logger.debug(params);
	    	logger.debug("--------------------------------------------");
	    	
	    	logger.debug("---------- request to jsonData ----------");
	    	//request에 들어있는 JSON 데이터 꺼내기
//	    	JSONObject jsonObj = CommUtil.readJSONStringFromRequestBody(request);
//	    	
//	    	if(jsonObj != null) {
//	    		String reqPostDataString = jsonObj.toString();
//	    		
//	    		params = params + "&jsonObject=" + reqPostDataString;
//	    	}
	    	logger.debug("---------- request to jsonData ----------");
	    		    	
	    	//genexon.js에서 ddcode 조회 유무
	    	String action_log_yn = request.getParameter("ACTION_LOG_YN");
	    		    	
	    	//인덱스에서 메뉴조회, 세션유지, index.go 이동, ddcode 공통코드 조회를 제외한 나머지 액션
	    	HttpSession session = request.getSession(true);
    		SessionVO sessionVO = SessionUtil.getSessionVO(session);
    		User user = new User();
			ActionLogVO actionLogVO = new ActionLogVO();

			// session검사
			//세션 정보가 존재하는 경우 액션로그 행위자 사번 및 회사코드 존재하면 설정
			if(sessionVO != null) {
				user = SessionUtil.getSessionVO(session).getUser();

    			actionLogVO.setMb_id(user.getMb_id());		//행위자 회사코드
		    	actionLogVO.setEmp_cd(user.getEmp_cd());	//행위자 사번
			}

	    	// 메뉴별 상세 권한(조회/수정/삭제 등) 조회는 이 테스트 프로젝트 범위에서 제외 - 로그인 여부만으로 접근을 허용한다.

	    	//액션 로그
	    	if(exceptionActionLogUrl(url, action_log_yn)) {
				try {
					//액션 로그 생성
					String lower_case_url = url.toLowerCase();
					
					if(lower_case_url.indexOf(".go") > 0) {
						actionLogVO.setAction_type("GO");	//페이지이동
						
					}else if(lower_case_url.indexOf(".pop") > 0) {
						actionLogVO.setAction_type("POPUP");	//팝업이동
						
					}else if(lower_case_url.indexOf(".ajax") > 0) {
						//ajax url 호출 시
						if(lower_case_url.indexOf("get") > 0) {

							if(lower_case_url.indexOf("excel") > 0) {
								actionLogVO.setAction_type("EXCELDOWN");	//엑셀다운로드
								
							} else {
								actionLogVO.setAction_type("READ");		//조회
								
							}
						}else if(lower_case_url.indexOf("insert") > 0) {
							if(lower_case_url.indexOf("insertUploadExcelDataMonth") > 0) {
								actionLogVO.setAction_type("EXCELUPLOAD");	//엑셀업로드
								
							}else if(lower_case_url.indexOf("call") > 0 || lower_case_url.indexOf("copymonth") > 0) {
								actionLogVO.setAction_type("CALL_PROC");	//프로시저 호출
								
							}else {
								actionLogVO.setAction_type("INSERT");		//입력

							}
						}else if(lower_case_url.indexOf("update") > 0) {
							actionLogVO.setAction_type("UPDATE");	//수정
							
						}else if(lower_case_url.indexOf("delete") > 0) {
							actionLogVO.setAction_type("DELETE");	//삭제
							
						}else if(lower_case_url.indexOf("call") > 0 || lower_case_url.indexOf("uploadexcelsavedata") > 0) {
							actionLogVO.setAction_type("CALL_PROC");	//프로시저 호출
						}else {
							actionLogVO.setAction_type("UNKNOWN");
						}
						
					}else if(lower_case_url.indexOf("logout.do") > 0) {
						actionLogVO.setAction_type("LOGOUT");	//로그아웃
						
					}else if(lower_case_url.indexOf("login.do") > 0) {
						actionLogVO.setAction_type("LOGIN");	//로그인
					}else {
						actionLogVO.setAction_type("UNKNOWN");
					}
					
			    	actionLogVO.setAction_url(url);
			    	actionLogVO.setAction_ip(CommUtil.getClientIp(request));
			    	actionLogVO.setAction_query_string(params != null && params.length() > 1000 ? params.substring(0, 1000) : "");
		    	
		    		logsService.insertActionLog(actionLogVO);
		    	}catch(Exception e) {
		    		logger.error("액션 로그 기록 중 에러발생");
		    		logger.error(e.getMessage());
		    	}
				
				logger.info("interceptor url=["+url+"]");
				logger.info("회사코드 : " + actionLogVO.getMb_id() + ", 사원번호 : " + actionLogVO.getEmp_cd());
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	    
	    Constants.QueryLogging = queryLogging;
        
	    return true;
	}

	
	/**
	 * 메뉴 권한 조회 url 예외처리
	 * @param String
	 * @param String
	 * @return boolean
	 */
	private boolean exceptionMenuRoleUrl(String url) {
		boolean exceptionUrlYN = false;
		
		exceptionUrlYN =   url.indexOf("sessionExtension.ajax") == -1
						&& url.indexOf("getMenuList.ajax") == -1
						&& url.indexOf("/board/board/getBoardTreeView.ajax") == -1
						&& url.indexOf("index.go") == -1
				    	&& url.indexOf("login.go") == -1
				    	&& url.indexOf(".ico") == -1
				    	&& url.indexOf(".jpg") == -1
				    	&& url.indexOf(".gif") == -1
				    	&& url.indexOf(".png") == -1
				    	&& url.indexOf(".xlsx") == -1
				    	&& url.indexOf(".pdf") == -1
				    	&& url.indexOf(".xls") == -1
				    	&& url.indexOf("/beforeAction.do") == -1
				    	&& !"/".equals(url)
				    	&& !"/main/sessionMaintenance.ajax".equals(url);
		
		return exceptionUrlYN;
	}
	
	/**
	 * 액션로그 url 예외처리
	 * @param String
	 * @param String
	 * @return boolean
	 */
	private boolean exceptionActionLogUrl(String url, String action_log_yn) {
		boolean exceptionUrlYN = false;
		
		exceptionUrlYN =   url.indexOf("sessionExtension.ajax") == -1			//세션 연장
						&& url.indexOf("getMenuList.ajax") == -1				//메뉴리스트
						&& url.indexOf("/insa/org/getOrgTreeView.ajax") == -1	//조직 트리뷰
						&& url.indexOf("index.go") == -1						//초기화면
				    	&& url.indexOf("login.go") == -1						//로그인화면
				    	&& url.indexOf(".ico") == -1							//ico 확장자
				    	&& url.indexOf(".jpg") == -1							//jpg 확장자
				    	&& url.indexOf(".gif") == -1							//gif 확장자
				    	&& url.indexOf(".png") == -1							//png 확장자
				    	&& url.indexOf(".xls") == -1							//xls 확장자
				    	&& url.indexOf(".xlsx") == -1							//xlsx 확장자
				    	&& url.indexOf(".pdf") == -1							//pdf 확장자
				    	&& !"N".equals(action_log_yn)							//파라미터 중 action_log_yn이 N이 아닌 경우(genexon.js의 ddcode 조회시)
				    	&& url.indexOf("/beforeAction.do") == -1				//로그인 전 액션
				    	&& !"/".equals(url)										//루트
				    	&& !"/main/sessionMaintenance.ajax".equals(url)			//?
				    	&& url.indexOf("/board/board/getBoardTreeView.ajax") == -1	//게시판 트리뷰
				    	&& url.indexOf("/insa/org/getMonthOrgTreeView.ajax") == -1;	//월별 조직 트리뷰
		
		return exceptionUrlYN;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		//운영서버 쿼리로깅 OFF
		if(logerYN.equals("N") || (!Startup.isWindows() && "real".equals(vo.getDBGubun())) ) {
			Constants.QueryLogging = LOGGING.NOLOGGING;
		}else{
			Constants.QueryLogging = LOGGING.LOGGING;
		}
	}
}