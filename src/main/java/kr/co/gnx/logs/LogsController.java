package kr.co.gnx.logs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import kr.co.gnx.base.BaseController;
import kr.co.gnx.comm.util.CommUtil;
import kr.co.gnx.comm.util.session.SessionUtil;
import kr.co.gnx.security.model.User;
import net.sf.json.JSONObject;


/**
 * 증권정보 입력/분석/관리
 * @author user
 *
 */
@Controller(value="LogsController")
public class LogsController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(LogsController.class);
	
	/**
	 * 로그인 기록 조회 페이지 이동
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/logs/loginHist.go")
	public ModelAndView loginHistory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/logs/loginHist");
		
		
		return mv;
	}
	
	/**
	 * 로그인 기록 리스트 조회
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @param LoginHistVO
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/logs/getLoginHistList.ajax")
	public ModelAndView getLoginHistList(HttpServletRequest request, HttpServletResponse response, LoginHistVO paramVO) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		User userSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		getLogsService().setUserSession(userSession);
		
		LoginHistVO loginHistVO = new LoginHistVO();
		
		if(CommUtil.isNotEmpty(paramVO.getJson_string())) {
			JSONObject paramJObj = JSONObject.fromObject(paramVO.getJson_string());
			loginHistVO = (LoginHistVO) JSONObject.toBean(paramJObj, LoginHistVO.class);
		}
		
		//서버페이징 시 JSON_STRING으로 파라미터를 넘길때 page정보를 어떻게 할 지 생각해보자
		loginHistVO.setPage(paramVO.getPage());
		loginHistVO.setPageSize(paramVO.getPageSize());
		
		mv.addObject("results", getLogsService().getLoginHistList(loginHistVO));
		
		return mv;
	}
	
	/**
	 * 액션 로그 조회 페이지 이동
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/logs/actionLog.go")
	public ModelAndView actionLog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/logs/actionLog");
		
		
		return mv;
	}
	
	/**
	 * 액션 로그 리스트 조회
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @param ActionLogVO
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/logs/getActionLogList.ajax")
	public ModelAndView getActionLogList(HttpServletRequest request, HttpServletResponse response, ActionLogVO paramVO) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		User userSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		getLogsService().setUserSession(userSession);
		
		ActionLogVO actionLogVO = new ActionLogVO();
		
		if(CommUtil.isNotEmpty(paramVO.getJson_string())) {
			JSONObject paramJObj = JSONObject.fromObject(paramVO.getJson_string());
			actionLogVO = (ActionLogVO) JSONObject.toBean(paramJObj, ActionLogVO.class);
		}
		
		//서버페이징 시 JSON_STRING으로 파라미터를 넘길때 page정보를 어떻게 할 지 생각해보자
		actionLogVO.setPage(paramVO.getPage());
		actionLogVO.setPageSize(paramVO.getPageSize());
		
		mv.addObject("results", getLogsService().getActionLogList(actionLogVO));
		
		return mv;
	}
	
	/**
	 * 에러 로그 조회 페이지 이동
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/logs/errorLog.go")
	public ModelAndView errorLog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/logs/errorLog");
		
		
		return mv;
	}
	
	/**
	 * 에러 로그 리스트 조회
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @param ActionLogVO
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/logs/getErrorLogList.ajax")
	public ModelAndView getActionLogList(HttpServletRequest request, HttpServletResponse response, ErrorLogVO paramVO) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		User userSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		getLogsService().setUserSession(userSession);
		
		ErrorLogVO errorLogVO = new ErrorLogVO();
		
		if(CommUtil.isNotEmpty(paramVO.getJson_string())) {
			JSONObject paramJObj = JSONObject.fromObject(paramVO.getJson_string());
			errorLogVO = (ErrorLogVO) JSONObject.toBean(paramJObj, ErrorLogVO.class);
		}
		
		//서버페이징 시 JSON_STRING으로 파라미터를 넘길때 page정보를 어떻게 할 지 생각해보자
		errorLogVO.setPage(paramVO.getPage());
		errorLogVO.setPageSize(paramVO.getPageSize());
		
		mv.addObject("results", getLogsService().getErrorLogList(errorLogVO));
		
		return mv;
	}
}
