package kr.co.gnx.comm;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.gnx.base.BaseController;
import kr.co.gnx.comm.jwt.JwtTokenUtil;
import kr.co.gnx.comm.util.CommUtil;
import kr.co.gnx.comm.util.InetUtil;
import kr.co.gnx.comm.util.propertiesVO;
import kr.co.gnx.comm.util.crypto.CryptoUtil;
import kr.co.gnx.comm.util.session.SessionUtil;
import kr.co.gnx.comm.util.session.SessionVO;
import kr.co.gnx.security.model.Role;
import kr.co.gnx.security.model.User;
import kr.co.gnx.system.login.LoginVO;
import kr.co.gnx.system.member.MemberVO;
import net.sf.json.JSONObject;

@Controller(value="CommController")
public class CommController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(CommController.class);
	
	@RequestMapping(value = "/login.go")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response,MemberVO memberVO) throws Exception{
		// 원본은 사내 공인/사설 IP가 아니면 외부 ERP 도메인으로 리다이렉트시키는 접근제한이 있었다.
		// 이 테스트 프로젝트는 로컬 접속만 하므로 그 제한은 빼고 항상 로그인 화면을 보여준다.
		return new ModelAndView("comm/login");
	}
	
	@RequestMapping(value={"beforeAction.do"})
	public String beforeAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("beforeAction.do");
		SessionVO sessionvo = SessionUtil.getSessionVO(request.getSession());
		User user = sessionvo.getUser();
				
		try {
			if(null != user) {
				// 로그인 히스토리 등록
				LoginVO loginVO = new LoginVO();
				
				String ip = InetUtil.getClientIP(request);

				if(ip != null && !ip.equals("")) {
					if(ip.equals("0:0:0:0:0:0:0:1")) {
						ip = InetUtil.getCurrentEnvironmentNetworkIp();
					}
					
					loginVO.setMb_id(user.getMb_id());
					loginVO.setEmp_cd(user.getEmp_cd());
					loginVO.setLogin_ip(ip);
					loginVO.setAcct_lock_type("N");	// 계정잠김여부
					loginVO.setPwd_err_nbtm("0");	// 비밀번호 오류 횟수
					getLoginService().setUserSession(user);
					
					ArrayList<LoginVO> list = new ArrayList<LoginVO>();
					list.add(loginVO);
					
					// 계정잠금 해제
					getLoginService().updateLogin(list);
					
					getLoginService().insertLoginHist(loginVO);
					
					/* 토큰 등록 */
					getCommService().regiToken(user);
				}
			}
			
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		
		return "redirect:index.go";
	}
	
	@RequestMapping(value="/logout.do")
	public String logout(HttpServletRequest req, HttpServletResponse res) {
		logger.debug("logout.do");
		logoutAction(req, res);
		return "redirect:/login.go";
	}
	
	public void logoutAction(HttpServletRequest req, HttpServletResponse res){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(req, res, auth);
		}
	}
	
	/**
	 * @Description  : 로그인 정보 및 권한 체크
	 * @author       : lakhyun.kim
	 * @since        : 2019. 04. 19
	 * @return       : ModelAndView
	 */
	@RequestMapping(value = "/comm/getAuthCheck.ajax")
	public ModelAndView getAuthCheck(HttpServletRequest request, HttpServletResponse response, LoginVO loginVO) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		try {
			loginVO.setProject_type("CMS4");
			mv.addObject("results", getCommService().getLoginAuthCheck(loginVO));
		} catch (Exception exception) {
			MemberVO memberVO = new MemberVO();
			memberVO.setMb_id(loginVO.getMb_id());
			
			getLoginService().updatePwdErrNbtm(loginVO);
			
			mv.addObject("memberView", getMemberService().getMemberView(memberVO));
			mv.addObject("SPRING_SECURITY_LAST_EXCEPTION", exception.getMessage());
		}
		
		return mv;
	}
	
	/**
	 * @Description  : SMS인증 팝업
	 * @author       : lakhyun.kim
	 * @since        : 2019. 04. 19
	 * @return       : ModelAndView
	 */

	@RequestMapping(value = "/comm/smsAuthPop.pop")
	public ModelAndView smsAuthPop(HttpServletRequest request, HttpServletResponse response, LoginVO loginVO) throws Exception {
		ModelAndView mv = new ModelAndView("/comm/smsAuthPop");
		JSONObject paramJObj = JSONObject.fromObject(loginVO.getJson_string());
		loginVO = (LoginVO) JSONObject.toBean(paramJObj, LoginVO.class);
		getCommService().insertSmsAuth(loginVO);
		mv.addObject("LoginVO", loginVO);
		return mv;
	}
	
	/**
	 * @Description  : SMS 인증번호 체크
	 * @author       : lakhyun.kim
	 * @since        : 2019. 04. 19
	 * @return       : ModelAndView
	 */
	@RequestMapping(value = "/comm/getAuthNumCheck.ajax")
	public ModelAndView getAuthNumCheck(HttpServletRequest request, HttpServletResponse response, LoginVO loginVO) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");

		if(getCommService().getAuthNumCheck(loginVO)) {
			mv.addObject("result", "success");
		} else {
			mv.addObject("result", "error");
		}
		
		mv.addObject("LoginVO", loginVO);
		return mv;
	}

	/**
	 * @Description  : SMS 인증번호 삭제
	 * @author       : lakhyun.kim
	 * @since        : 2019. 04. 19
	 * @return       : ModelAndView
	 */
	@RequestMapping(value = "/comm/deleteAuthNum.ajax")
	public ModelAndView deleteAuthNum(HttpServletRequest request, HttpServletResponse response, LoginVO loginVO) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		mv.addObject("results", getCommService().deleteAuthNum(loginVO));
		return mv;
	}
	
	/**
	 * @Description  : SSO 로그인 페이지 이동
	 * @author       : KIMDONGUK
	 * @since        : 2020. 12. 28
	 * @return       : ModelAndView
	 */
	@RequestMapping(value = "/comm/ssoLoginPage.go")
	public ModelAndView ssoLoginPage(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		ModelAndView mv = new ModelAndView("comm/ssoLogin");
		
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
		
		//토큰 가져옴
		String access_token = request.getParameter("access_token");
		
		//토큰 검증
		boolean isValidate = jwtTokenUtil.validateToken(access_token);
		
		if(!isValidate) {
			//실패 시 로그인 화면
			MemberVO memberVO = new MemberVO();
			
			String servername = request.getServerName();
			memberVO.setDomain_url(servername);
			Map<String, String> resultMap = getMemberService().getMemberUrlView(memberVO);
			
			try {
				if(resultMap != null) {
					if(resultMap.get("mb_id") != null && !resultMap.get("mb_id").equals("") && resultMap.get("mb_id").equals("PA")) {
						request.setAttribute("Browser", InetUtil.getBrowser(request));
					}
				}else{
					resultMap = new HashMap<String, String>();
					resultMap.put("mb_id", "GNX");
				}
				
				request.setAttribute("Domaininfo", resultMap);
				
			} catch (Exception e) {
				logger.error("context", e.getMessage());
			}
			
			mv.setViewName("comm/login");
		}else {
			String mb_id = jwtTokenUtil.getClaimsDataFromToken("mb_id", access_token);
			String login_id = jwtTokenUtil.getUsernameFromToken(access_token);
			
			mv.addObject("mb_id", mb_id);
			mv.addObject("login_id", login_id);
			mv.addObject("access_token", access_token);
		}
		
		return mv;
	}
	
	public static void main(String[] args) {
		try {
		String KEY = "genexon20202!";
		Map<String,String> map = new HashMap<String, String>();
		map.put("url", "jdbc:mariadb://211.215.18.133:3306/SAMPLE?autoReconnect=true&characterEncoding=utf8&useUnicode=true");
		map.put("username", "SAMPLE");
		map.put("password", "Zd-#8AKGm6bY#er*");
		System.out.println(CommUtil.XmlEncrypt(KEY,map));
		//System.out.println(CommUtil.generateToken());
		
		Map<String,String> smsmap = new HashMap<String, String>();
		smsmap.put("url", "jdbc:mariadb://222.239.252.157:3306/sms?autoReconnect=true&characterEncoding=utf8&useUnicode=true");
		smsmap.put("username", "sms");
		smsmap.put("password", "sms@)!*");
		System.out.println(CommUtil.XmlEncrypt(KEY,smsmap));

			
		CryptoUtil cutil = new CryptoUtil();
		String aaa= cutil.encrypt("abc");
		
		String bbb= cutil.decrypt(aaa);
		
		//System.out.println(aaa);
		//System.out.println(bbb);
		
		
		JwtTokenUtil jtu = new JwtTokenUtil();
		kr.co.gnx.security.model.User us = new kr.co.gnx.security.model.User();
		us.setUsername("sum");
		us.setPassword("sum");
		us.setMb_id("GNX");
		List<Role> authorities = new ArrayList<Role>();
		Role role = new Role();
		role.setName("SUPER");
		authorities.add(role);
		us.setAuthorities(authorities);
		
		Map<String, Object> claims = new HashMap<>();
		
		claims.put("http://www.genexon.co.kr/jwt_claims/mb_id", us.getMb_id());
		claims.put("http://www.genexon.co.kr/jwt_claims/project_type", "CMS4");
		

		final long JWT_TOKEN_VALIDITY = 1 * 60 * 60;
		System.out.println(jtu.generateToken(claims,us.getUsername(),JWT_TOKEN_VALIDITY));
		
		
		//System.out.println(jtu.generateToken(us,claims));
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdW0iLCJleHAiOjE1Nzk2MTM2NzQsImlhdCI6MTU3OTU5NTY3NH0.8OBjwxdHiBC5yaYwFMRVsRGHoXoncRBhqpO6KE653kl3Vqs9x8QCuht97qu83kla-AALBKjFvcbGTzWxTsR_3A";
		String token1 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdW0iLCJtYl9pZCI6IkdOWCIsImV4cCI6MTU3OTYxNTc3OSwiaWF0IjoxNTc5NTk3Nzc5fQ.etbccbA24GblPOcuRNoN4g3Xdaj3KgW2e6fN2U7zTYSZv3hwueyPvd7yLscgduS9cclZjew-rBJoRuS0p2zW0g";
		String token2 = "eyJhbGciOiJIUzUxMiJ9.eyJwcm9qZWN0X3R5cGUiOiJFUlAiLCJzdWIiOiJzdW0iLCJtYl9pZCI6IkdOWCIsImV4cCI6MTU3OTY3MDM5OCwiaWF0IjoxNTc5NjUyMzk4fQ.nyQZInd0sW1I3DDXM4maHsEyrFDfOPiVvjBoQ7OxkkqNHuL9NvwjT1s95ClnRZ9xjsHTThAJ2hget-bYq9S3WA";
		String token3 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdW0iLCJodHRwOi8vZ2VuZXhvbi5jby5rci9qd3RfY2xhaW1zL21iX2lkIjoiR05YIiwiZXhwIjoxNTc5NjcwNzg1LCJpYXQiOjE1Nzk2NTI3ODUsImh0dHA6Ly9nZW5leG9uLmNvLmtyL2p3dF9jbGFpbXMvcHJvamVjdF90eXBlIjoiRVJQIn0.2IWJJfN7L4TOMoy7zN8uM641Kk298_zXGEIpou3n622fClSvalugt2_3nV7iCW_LRxRoMcUvaDq1ZIWx2pj1qQ";
		String token4 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdW0iLCJodHRwOi8vd3d3LmdlbmV4b24uY28ua3Ivand0X2NsYWltcy9tYl9pZCI6IkdOWCIsImh0dHA6Ly93d3cuZ2VuZXhvbi5jby5rci9qd3RfY2xhaW1zL3Byb2plY3RfdHlwZSI6IkVSUCIsImV4cCI6MTU3OTY3MDg1MCwiaWF0IjoxNTc5NjUyODUwfQ.SI-cWE_4an3pcnBbpxxKFZfLXTR1iNlZWmFt6E1QuAO3fwWgEvYF5pRbfAj1h5aeFF89beKv9d21WWfbN1NY-g";
		String token5 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdW0iLCJodHRwOi8vd3d3LmdlbmV4b24uY28ua3Ivand0X2NsYWltcy9tYl9pZCI6IkdOWCIsImh0dHA6Ly93d3cuZ2VuZXhvbi5jby5rci9qd3RfY2xhaW1zL3Byb2plY3RfdHlwZSI6IkVSUCIsImV4cCI6MTU3OTY3MTU4MCwiaWF0IjoxNTc5NjUzNTgwfQ.m8yNx46JZ-tMw9BWt9mw9xjXbZY30XU9g0OQ3zRC_LiClTpyUhrUXpNe8kTiizAfV0LuKFkgdBj6WvpgURlDtA";
		String token6 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdW0iLCJodHRwOi8vd3d3LmdlbmV4b24uY28ua3Ivand0X2NsYWltcy9tYl9pZCI6IkdOWCIsImh0dHA6Ly93d3cuZ2VuZXhvbi5jby5rci9qd3RfY2xhaW1zL3Byb2plY3RfdHlwZSI6IkVSUCIsImV4cCI6MTU3OTY1ODMzMywiaWF0IjoxNTc5NjU0NzMzfQ.ULX0MgMNIysdWrvQAG99A4lAdrfkLSVKOHZSBf-qZcXqn6sretg_ma6dg0DM44oIUVdoHKmLdS_GohNvC2LZEg";
		String token7 = "ab";
		String token8 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdW0iLCJodHRwOi8vd3d3LmdlbmV4b24uY28ua3Ivand0X2NsYWltcy9tYl9pZCI6IkdOWCIsImh0dHA6Ly93d3cuZ2VuZXhvbi5jby5rci9qd3RfY2xhaW1zL3Byb2plY3RfdHlwZSI6IkVSUCIsImV4cCI6MTU3OTY4OTQ2OCwiaWF0IjoxNTc5Njg1ODY4fQ.fl3ghjvyM8YvtPgHxbL5QDSTBJoKbVpaVT5oD4zJJ8U9GIxSrAeJugeIrYTJwDcAUbfRh1NeS1pIBQOwGsoafQ";
		String token9 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdW0iLCJodHRwOi8vd3d3LmdlbmV4b24uY28ua3Ivand0X2NsYWltcy9tYl9pZCI6IkdOWCIsImh0dHA6Ly93d3cuZ2VuZXhvbi5jby5rci9qd3RfY2xhaW1zL3Byb2plY3RfdHlwZSI6IkVSUCIsImV4cCI6MTU4MDE5MTEwMCwiaWF0IjoxNTgwMTg3NTAwfQ.o0d-10U_P2J27Erl72sPNnR3Qekv0no9PVOWxcHE2Uo6-qg2zo9qqXQTRyFem5nU3YDS3aPMR2oZwrDoTpCVjQ";
		String token10 = "eyJhbGciOiJSUzI1NiJ9.eyJ1c2VyLmlkIjoidXNlci10aGViZXN0LTAwIiwidXNlci5uYW1lIjoi7YWM7Iqk7YSwIiwidXNlci51c2VybmFtZSI6Iu2FjOyKpO2EsCIsInVzZXIuZW1haWwiOiIiLCJ1c2VyLnBhc3N3b3JkIjoiIiwidXNlci5yb2xlcyI6IlVTRVIiLCJ1c2VyLmNvbXBhbnlJZCI6ImNvbXBhbnktdGVzdC10aGViZXN0IiwidG9rZW4uaWQiOiJlNjliMjY2Ni04MjMwLTQ2Y2EtYjU4NS02ZDEyNDZjODI3MTkiLCJ0b2tlbi52YWxpZGF0aW9uS2V5IjoiM2IxZmEyYWQtMzI5Zi00NDFhLWJhNDAtYWVlZTI1NzNlNWVjIiwiaWF0IjoxNTgwMzYxNDE2LCJleHAiOjE1ODAzODMwMTZ9.bshuCt0Ko8dxPhq4uWLfBW9s4QrRaqNeFXvbq7ges_hw2CItoUNgmeJEIUV48mH7pGFhAsYPbA774CqAnX03TlELWr5Yqj_6l3zthPWU0tpe-7ll2FEWdHiZXUcgM7O_hx9nx2jx06OZ_bH4ghUbNLsryydgyxU60W1LaugX6P-QtUCyUy_8muW548V2f4cBsCblTyYgpMx9YUYMD4YmzYEuZp_iOaDyDb1wDAtLBV9-IywCAbwR9FW3XtY_kqKlzKLBaqSMCbFIHH2AuItP3P2RsZRAXyzlIXL9THtzK0DfhPMD8EYjxi99smfMZMFKJkky9oZY8__WFI7VWR13wcNFkYpddUlJtB7PAQcHtyKou9tXRhqLI20qcg4xyq54JMNKAxREa4ClOfnKNtLftIASZB18_8CqVuoI4c7-Ia_u-uy3p4eGAwefY1Wb_Yyk11jptOFw85ok4DN5xXJS9qCXcMlal3-Wp63AN60JQ8CklUrd_T--4nxk5qLAOofBYS2oO0hItOew1eW96oukc6qJEw0esA_U3TQfo7Ke8-74_BjmT0rBXdGeQH2bNhuOwvRiJWlphjAE9tueM45mL4y1ztNJG8XF7ZEFq07hwD26dY5QgOmQ6-98LKs4pBDDGNX8uyV-cPR3xkN0a5qQteL33KTUKXn2C5d5G950EsE";
		String token11= "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdW0iLCJodHRwOi8vd3d3LmdlbmV4b24uY28ua3Ivand0X2NsYWltcy9tYl9pZCI6IkdOWCIsImh0dHA6Ly93d3cuZ2VuZXhvbi5jby5rci9qd3RfY2xhaW1zL3Byb2plY3RfdHlwZSI6IkVSUCIsImV4cCI6MTU4MDcwNzA0NiwiaWF0IjoxNTgwNzAzNDQ2fQ.t0aRnLMAfgHg57vWAzO_F2872TZZq-UhNnlFw4YeQ5ddH2m8sNt_PzN6mEoFLp-WesuM9Lw1jlwCL6pUH8Z4rg";
		System.out.println(jtu.getExpirationDateFromToken(token11));
		
		//System.out.println(jtu.getClaimsDataFromToken("http://www.genexon.co.kr/jwt_claims/project_type",token9));
		//System.out.println(jtu.getClaimsDataFromToken("mb_id",token7));
		//System.out.println(jtu.getExpirationDateFromToken(token9));
		
		//System.out.println(jtu.getExpirationDateFromToken(token10));
			//System.out.println(jtu.getClaimsDataFromToken("mb_id",token1));
		/*System.out.println(jtu.getClaimsDataFromToken("mb_id",token2));
		System.out.println(jtu.getClaimsDataFromToken("project_type",token2));*/
		/*System.out.println(jtu.getClaimsDataFromToken("http://www.genexon.co.kr/jwt_claims/project_type",token3));
		System.out.println(jtu.getClaimsDataFromToken("http://www.genexon.co.kr/jwt_claims/mb_id",token3));*/
		
		//System.out.println(jtu.getClaimsDataFromToken("http://www.genexon.co.kr/jwt_claims/project_type",token5));
		//System.out.println(jtu.getClaimsDataFromToken("http://www.genexon.co.kr/jwt_claims/mb_id",token5));
		Calendar cal = Calendar.getInstance();
		Date date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar time = Calendar.getInstance(Locale.KOREA);
		//System.out.println(jtu.getExpirationDateFromToken(token8));
		
		//System.out.println(transFormat.format(jtu.getExpirationDateFromToken(token6)));
		//System.out.println(jtu.isTokenExpired(token7));
		//System.out.println(sdf.format(time.getTime()));
		/*System.out.println(jtu.getClaimsDataFromToken("http://www.genexon.co.kr/jwt_claims/project_type",token4));
		System.out.println(jtu.getClaimsDataFromToken("http://www.genexon.co.kr/jwt_claims/mb_id",token4));*/
		/*System.out.println(jtu.getUsernameFromToken(token));
		System.out.println(jtu.getExpirationDateFromToken(token));
		System.out.println(jtu.getUsernameFromToken(token1));*/
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
