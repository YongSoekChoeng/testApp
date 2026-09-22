package kr.co.gnx.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.gnx.comm.util.CommUtil;
import kr.co.gnx.comm.util.session.SessionUtil;
import kr.co.gnx.comm.util.session.SessionVO;
import kr.co.gnx.erp.ErpService;
import kr.co.gnx.erp.ErpVO;
import kr.co.gnx.exception.MessageCommonCode;
import kr.co.gnx.security.UserService;
import kr.co.gnx.security.model.Role;
import kr.co.gnx.security.model.User;
import kr.co.gnx.system.SystemVO;
import kr.co.gnx.system.member.MemberService;
import kr.co.gnx.system.member.MemberVO;

@Controller(value="MainController")
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Value("#{globals['Globals.DBGubun']}")
	private String DBGubun;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ErpService erpService;
	
	@RequestMapping(value={"index.go"})
	public ModelAndView main(HttpServletRequest req, HttpServletResponse res, SystemVO vo) throws Exception{
		logger.debug("index.go");
		ModelAndView mav = new ModelAndView("/main/index");
		
		/*==================================================
		 *  웹 WEB 메인
		 ===================================================*/
		SessionVO sessionVo = null;
		String error = null;
		MessageCommonCode messageElement = null;
		
		int concurrent_gubun = 0;
		
		try {
			
			sessionVo = SessionUtil.getSessionVO(req.getSession());
			
			
			// 권한사원변경
			if(vo.getUser_id() != null && !vo.getUser_id().equals("")) {
				
				User user = new User();
				Map<String, String> UserView = new HashMap<String, String>();//로그인 정보 조회 시 필수 정보 담음
				Map<String, String> RoleMap = new HashMap<String, String>();//권한 정보 조회 시 필수 정보 담음
				
				List<Role> roles = new ArrayList<Role>();
				
				// 변경할 권한사원 아이디와 회사정보를 등록
				UserView.put("mb_id", vo.getMb_id());
				UserView.put("login_id", vo.getUser_id());
				UserView.put("concurrent_idx", vo.getConcurrent_idx());// 겸직구분
				// 회사정보 + 사원계정 정보 획득
				//user = userService.getUserView(UserView);
				user = userService.selectErpUserView(UserView);	// 회사 및 설계사 정보 ERP에서 조회
				
				if(null == user) {//유저가 없을 경우
					messageElement = MessageCommonCode.ERR0016;
					error = messageElement.getMessage();
				} else {
					RoleMap.put("mb_id",user.getMb_id());
					RoleMap.put("emp_cd",user.getEmp_cd());
					RoleMap.put("role_id",user.getRole_id());
					RoleMap.put("concurrent_idx", vo.getConcurrent_idx());// 겸직구분
					//roles = userService.getRolesHierarchyList(RoleList);
					roles = userService.selectErpRolesHierarchyList(RoleMap);	//권한 정보 ERP에서 가져오기
					
					if(null == roles){ // 권한이 없을경우
						messageElement = MessageCommonCode.ERR0015;
						error = messageElement.getMessage();
					}else{
						user.setAuthorities(roles);
						
						//logo_url은 수수료시스템의 member 테이블에서 가져오도록
						MemberVO memberVO = new MemberVO();
						memberVO.setMb_id(user.getMb_id());
						Map<String, String> memberMap = memberService.getMemberView(memberVO);
						
						if(memberMap != null) {
							user.setLogo_url(memberMap.get("logo_url"));
						}

						// 겸직여부 조회
						//UserRoleVO userRoleVO = new UserRoleVO();
						//userRoleVO.setMb_id(user.getMb_id());
						//userRoleVO.setEmp_cd(user.getEmp_cd());
						//concurrent_gubun = userRoleService.getUserRoleListCount(userRoleVO);
						
						// 겸직 여부 권한 조회 - ERP에서 가져오도록
						ErpVO erpVO = new ErpVO();
						erpVO.setMb_id(user.getMb_id());
						erpVO.setEmp_cd(user.getEmp_cd());						
						concurrent_gubun = erpService.getErpUserRoleListCount(erpVO);
						
						if(concurrent_gubun > 1) {
							user.setConcurrent_gubun(String.valueOf(concurrent_gubun));
						} else {
							if(vo.getDemo_gubun() != null && !vo.getDemo_gubun().equals("")) {
								if(vo.getDemo_gubun().equals("Y")) {
									// 기존 로그인 유저 정보
									user.setDemo_user(sessionVo.getUser().getUser_id());
								}
							}
						}
						
						sessionVo.setUser(user);
					}
				}
				
				if(error != null) {
					sessionVo.getUser().setError_message(error);
				}
			}

			mav.addObject("type", "index");
			mav.addObject("session", sessionVo.getUser());
			mav.addObject("DBGubun", vo.getPropertiesvo().getDBGubun());
			
			mav.addObject("isMobile", CommUtil.isMobile(req));
		} catch (Exception e) {
			logger.error(e.getMessage());
			mav.setViewName("redirect:/login.go");
		}
		
		return mav;
	}
	
	/**
	 * @desc   : 메인페이지 이동
	 * @param  : HttpServletRequest, HttpServletResponse
	 * @return : String
	 * @throws : Exception
	 */
	@RequestMapping(value = "/main.go")
	public ModelAndView goMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/main/main");
		
		return mv;
	}
	
	/**
	 * @Description  : 세션 연장
	 * @author       : lakhyun.kim
	 * @since        : 2019. 04. 15
	 * @return       : ModelAndView
	 */
	@RequestMapping(value = "/main/sessionExtension.ajax")
	public ModelAndView sessionExtension(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		
		User userSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		int sessionTime = userSession.getSession_time();
		
		session.setMaxInactiveInterval(sessionTime*60);
		
		mav.addObject("SESSION_TIME", sessionTime);
		
		return mav;
	}
}
