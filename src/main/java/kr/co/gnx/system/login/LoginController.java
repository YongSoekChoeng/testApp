package kr.co.gnx.system.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.gnx.base.BaseController;
import kr.co.gnx.comm.excel.excelHandler2;
import kr.co.gnx.comm.util.CommUtil;
import kr.co.gnx.comm.util.DecryptMap;
import kr.co.gnx.comm.util.crypto.CryptoUtil;
import kr.co.gnx.comm.util.session.SessionUtil;

import kr.co.gnx.logs.LoginHistVO;
import kr.co.gnx.security.model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("LoginController")
public class LoginController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private StandardPasswordEncoder sandardPasswordEncoder;
	
	/**
	 * @Description  : 사용자계정관리 페이지 이동
	 * @author       : lakhyun.kim
	 * @since        : 2019. 03. 11
	 * @return       : ModelAndView
	 */
	@RequestMapping(value = "/userAccountManage/userAccountManage.go")
	public ModelAndView role(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView("/system/userAccountManage/userAccountManage");
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		Map<String, String> tempmap = new HashMap<String, String>();
		tempmap.put("ROLE_ID", UserSession.getRole_id());
		mv.addObject("User", tempmap);
		return mv;
	}

	/**
	 * @Description  : 로그인 정보 조회(사용자계정관리)
	 * @author       : lakhyun.kim
	 * @since        : 2019. 03. 05
	 * @return       : ModelAndView
	 */
	@RequestMapping(value = "/login/getLoginList.ajax")
	public ModelAndView getLoginList(HttpServletRequest request, HttpServletResponse response, LoginVO loginVO) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		loginVO.setMb_id(UserSession.getMb_id());
		mv.addObject("results", getLoginService().getLoginList(loginVO));
		return mv;
	}
	

	/**
	 * @Description  : 로그인 정보 수정(비밀번호 초기화, 계정잠금/해제, 2factor인증/해제)
	 * @author       : lakhyun.kim
	 * @since        : 2019. 03. 05
	 * @return       : ModelAndView
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/login/updateLogin.ajax")
	public ModelAndView updateLogin(HttpServletRequest request, @RequestBody JSONObject paramJObj) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		ArrayList<LoginVO> models = (ArrayList<LoginVO>) JSONArray.toCollection(paramJObj.getJSONArray("models"), LoginVO.class);
		getLoginService().setUserSession(UserSession);
		getLoginService().updateLogin(models);
		return mv;
	}

	/**
	 * @Description  : 사용자계정관리 엑셀다운로드
	 * @author       : lakhyun.kim
	 * @since        : 2019. 03. 05
	 * @return       : void
	 */
	@RequestMapping(value = "/login/getLoginListExcel.ajax")
	public void getLoginListExcel(HttpServletRequest request, HttpServletResponse response, LoginVO loginVO) throws Exception{
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		loginVO.setMb_id(UserSession.getMb_id());
		loginVO.setIn_emp_cd(UserSession.getEmp_cd());
		loginVO.setUp_emp_cd(UserSession.getEmp_cd());
		excelHandler2 eh =  getLoginService().getLoginListExcel(loginVO);

		if (eh.getRowindex() == 0) {
			CommUtil.sendGenexonAlert(response, "info", "사용자계정관리 - 엑셀다운로드", "조회 데이타가 없습니다.");
		} else {
			String filename = "사용자계정관리_" + CommUtil.getCurrentDateTime() + ".xlsx";
			eh.sendResponse(response, filename);
		}
	}
	
	/**
	 * @Description  : 비밀번호변경 화면 이동
	 * @author       : KIMDONGUK
	 * @since        : 2019. 06. 02
	 * @return       : ModelAndView
	 */
	@RequestMapping(value="/system/userAccountManage/passwordMng.go")
	public ModelAndView passwordMng(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/system/userAccountManage/passwordMng");
		
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		
		mv.addObject("User", UserSession);
		
		return mv;
	}
	
	/**
	 * @Description  : 비밀번호변경
	 * @author       : KIMDONGUK
	 * @since        : 2019. 06. 02
	 * @return       : ModelAndView
	 */
	@RequestMapping(value="/system/userAccountManage/passwordChange.ajax")
	public ModelAndView passwordChange(HttpServletRequest request, HttpServletResponse response, LoginVO loginVO) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		getLoginService().setUserSession(UserSession);
		
		getLoginService().passwordChange(loginVO);
		
		return mv;
	}

}
