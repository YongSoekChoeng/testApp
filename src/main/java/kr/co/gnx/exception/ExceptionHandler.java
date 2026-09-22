package kr.co.gnx.exception;

import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import kr.co.gnx.comm.util.CommUtil;
import kr.co.gnx.comm.util.session.SessionUtil;
import kr.co.gnx.comm.util.session.SessionVO;
import kr.co.gnx.config.Constants;
import kr.co.gnx.config.Constants.LOGGING;
import kr.co.gnx.logs.ErrorLogVO;
import kr.co.gnx.logs.LogsService;
import kr.co.gnx.security.model.User;
import net.sf.json.JSONObject;
/**
 * ExceptionHandler 
 * 예외처리  ExceptionResolver
 * @author kihon 
 */
public class ExceptionHandler extends SimpleMappingExceptionResolver  {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private LogsService logsService;
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.SimpleMappingExceptionResolver#doResolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){

		CommUtil comutil = new CommUtil();

		StackTraceElement[] st = ex.getStackTrace();
		StringBuilder sb = new StringBuilder();
		
		LOGGING QueryLogging = Constants.QueryLogging; 
		
		Constants.QueryLogging = LOGGING.NOLOGGING;

		for(int i=0;i<st.length;i++) {
			sb.append(st[i]);
			sb.append("\n");
			
			if (i==4)break;
		}

		logger.error("");
		logger.error("##########################");
		logger.error("#call  doResolveException#");
		logger.error("request.getRequestURI = {}",request.getRequestURI());
		logger.error("response.getStatus() = {}",response.getStatus() );
		logger.error("ex.getClass()        = {}",ex.getClass()        );
		logger.error("ex.getMessage()      = {}",ex.getMessage()      );
		logger.error("ex.toString()        = {}",ex.toString()        );
		logger.error("StackTrace  = {}",sb.toString());
		logger.error("##########################");
		logger.error("");
			
		//에러 로그 저장
		try {
	        				
			Enumeration<String> paramsss = request.getParameterNames();
			
			logger.info("---------- request params @ exception handler ----------");
			
			String params = "";
			
			while (paramsss.hasMoreElements()) {
			    String name = (String)paramsss.nextElement();
			    params += ( name + "=" + request.getParameter(name) + "&");
			    logger.info(name + " : " +request.getParameter(name));
			}
							
			String url = request.getRequestURI();
			String lower_case_url = url.toLowerCase();

			if(exceptionUrl(url)) {
				HttpSession session = request.getSession(true);
				SessionVO sessionVO = SessionUtil.getSessionVO(session);
				User user = new User();
				ErrorLogVO errorLogVO = new ErrorLogVO();
				
				if(sessionVO != null) {
					user = SessionUtil.getSessionVO(session).getUser();
					errorLogVO.setMb_id(user.getMb_id());
					errorLogVO.setEmp_cd(user.getEmp_cd());	//에러 발생자 사번
				}
				
				if(lower_case_url.indexOf(".go") > 0) {
					errorLogVO.setAction_type("GO");	//페이지이동
					
				}else if(lower_case_url.indexOf(".pop") > 0) {
					errorLogVO.setAction_type("POPUP");	//팝업이동
					
				}else if(lower_case_url.indexOf(".ajax") > 0) {
					//ajax url 호출 시
					if(lower_case_url.indexOf("get") > 0) {

						if(lower_case_url.indexOf("excel") > 0) {
							errorLogVO.setAction_type("EXCELDOWN");	//엑셀다운로드
							
						} else {
							errorLogVO.setAction_type("READ");		//조회
							
						}
					}else if(lower_case_url.indexOf("insert") > 0) {
						if(lower_case_url.indexOf("insertUploadExcelDataMonth") > 0) {
							errorLogVO.setAction_type("EXCELUPLOAD");	//엑셀업로드
							
						}else if(lower_case_url.indexOf("call") > 0 || lower_case_url.indexOf("copymonth") > 0) {
							errorLogVO.setAction_type("CALL_PROC");	//프로시저 호출
							
						}else {
							errorLogVO.setAction_type("INSERT");		//입력

						}
					}else if(lower_case_url.indexOf("update") > 0) {
						errorLogVO.setAction_type("UPDATE");	//수정
						
					}else if(lower_case_url.indexOf("delete") > 0) {
						errorLogVO.setAction_type("DELETE");	//삭제
					}else if(lower_case_url.indexOf("call") > 0 || lower_case_url.indexOf("uploadexcelsavedata") > 0) {
						errorLogVO.setAction_type("CALL_PROC");	//프로시저 호출
					}else {
						errorLogVO.setAction_type("UNKNOWN");
					}
					
				}else if(lower_case_url.indexOf("logout.do") > 0) {
					errorLogVO.setAction_type("LOGOUT");	//로그아웃
					
				}else if(lower_case_url.indexOf("login.do") > 0) {
					errorLogVO.setAction_type("LOGIN");	//로그인
				}else {
					errorLogVO.setAction_type("UNKNOWN");
				}
				
				errorLogVO.setError_url(url);							//에러 발생 url
				errorLogVO.setError_class(ex.getClass().toString());	//에러 발생 클래스
				errorLogVO.setError_status(Integer.toString(response.getStatus()));		//에러 상태 코드
				errorLogVO.setError_msg(ex.getMessage());				//에러 메세지
				errorLogVO.setError_string(ex.toString());				//에러 전체 메시지
		        errorLogVO.setError_trace(sb.toString());
		        errorLogVO.setError_ip(CommUtil.getClientIp(request));
		        errorLogVO.setError_query_string(params);
	        
		        logsService.insertErrorLog(errorLogVO);
			}

			Constants.QueryLogging = QueryLogging;
		
		} catch (Exception e1) {
			logger.error("에러 로그 기록중 에러가 발생하였습니다.");
			logger.error(e1.getMessage());
			Constants.QueryLogging = QueryLogging;
		}
		
		String exmsg = "";
		String errCode = "";
		MessageCommonCode errorElement = null;
		
		//TODO: 익센셥에 대한 메시지 정의를 구현한다.
		if(ex instanceof DuplicateKeyException){
			errorElement = MessageCommonCode.ERR0004;
			errCode = errorElement.getMessageCode();
			exmsg = errorElement.getMessage();
		}
		else if(ex instanceof DataIntegrityViolationException){
		    DataIntegrityViolationException bEx = ((DataIntegrityViolationException) ex);
            
            logger.error("bEx.getErrorCode() = {}",bEx.getLocalizedMessage());
            errorElement = MessageCommonCode.ERR0005;
            // 개발편의를 위해서 에러 메세지 출력
            errCode = errorElement.getMessageCode();
            exmsg = bEx.getLocalizedMessage();
            exmsg = exmsg.replaceAll("ORA", "<br/>ORA");
		}
		else if(ex instanceof UncategorizedSQLException){
		    SQLException bEx = ((UncategorizedSQLException) ex).getSQLException();
	            
            logger.error("bEx.getErrorCode() = {}",bEx.getErrorCode());
            errorElement = MessageCommonCode.ERR0006;
            errCode = errorElement.getMessageCode();
            
            if(bEx.getErrorCode() > 20000)
            {
                exmsg = bEx.getMessage();
                exmsg = exmsg.substring(exmsg.indexOf(":")+1, exmsg.indexOf("\n"));
            }
            else
            {
                // 개발편의를 위해서 에러 메세지 출력
                exmsg = bEx.getMessage();
                exmsg = exmsg.substring(exmsg.indexOf(":")+1, exmsg.indexOf("\n"));
            }
		}
		else if(ex instanceof SQLException  ){
		    SQLException sqlEx = (SQLException) ex;
		    logger.error("sqlEx.getErrorCode() = {}",sqlEx.getErrorCode());
		    errorElement = MessageCommonCode.ERR0007;
		    errCode = errorElement.getMessageCode();
            if(sqlEx.getErrorCode() > 20000)
            {
                exmsg = sqlEx.getMessage();
                exmsg = exmsg.substring(exmsg.indexOf(":")+1, exmsg.indexOf("\n"));
            }
            else
            {
                // 개발편의를 위해서 에러 메세지 출력
                exmsg = sqlEx.getMessage();
                exmsg = exmsg.substring(exmsg.indexOf(":")+1, exmsg.indexOf("\n"));
            }
		    
			
		}else if(ex instanceof DataAccessException){
			errorElement = MessageCommonCode.ERR0008;
		    errCode = errorElement.getMessageCode();
			exmsg = errorElement.getMessage();
		}else if(ex instanceof NullPointerException ){
			errorElement = MessageCommonCode.ERR0009;
		    errCode = errorElement.getMessageCode();
			exmsg = errorElement.getMessage();
		}else if(ex instanceof org.apache.poi.openxml4j.exceptions.InvalidFormatException){
			errorElement = MessageCommonCode.ERR0010;
		    errCode = errorElement.getMessageCode();
			exmsg = errorElement.getMessage();
		}else{
			errorElement = MessageCommonCode.ERR0011;
			exmsg = ex.getMessage();
		}
		
		//ajax url은 json으로 에러 헨들링
		if(request.getRequestURI().toLowerCase().indexOf(".ajax") > 0 ) {
		
			JSONObject rtn = new JSONObject();
			rtn.element("errCode",  errCode);
			rtn.element("errmsg",  exmsg);
			
			try{
				comutil.sendjson(response, rtn , 500);
				
				return null;
			}catch (Exception e) {
				logger.error(exmsg);
				Constants.QueryLogging = QueryLogging;
			}
			
		}else{
			
			ModelAndView model = new ModelAndView();
			model.setViewName("comm/error");
			return model;
		}
		
		Constants.QueryLogging = QueryLogging;
		
		return null;
	}
	
	//에러로그를 남기질 않을 url 예외처리
	private boolean exceptionUrl(String url) {
		boolean yn = !(url.indexOf(".jsp") > 0) && !(url.indexOf(".js") > 0) && !(url.indexOf(".css") > 0) && !(url.indexOf(".ico") > 0)
					&& !(url.indexOf(".jpg") > 0) && !(url.indexOf(".eot") > 0) && !(url.indexOf(".woff") > 0) && !(url.indexOf(".ttf") > 0);
		
		return yn;
	}
	
}