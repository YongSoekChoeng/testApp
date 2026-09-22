package kr.co.gnx.comm;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.gnx.base.BaseService;
import kr.co.gnx.comm.jwt.JwtTokenUtil;
import kr.co.gnx.comm.util.CommUtil;
import kr.co.gnx.exception.MessageCommonCode;
import kr.co.gnx.security.model.Role;
import kr.co.gnx.security.model.User;
import kr.co.gnx.system.login.LoginVO;
import kr.co.gnx.system.member.MemberVO;

@Service
public class CommService extends BaseService{
	private static final Logger logger = LoggerFactory.getLogger(CommService.class);
	
	@Autowired
	private StandardPasswordEncoder sandardPasswordEncoder;
		
	/**
	 * @Description  : 로그인 정보 및 권한 체크
	 * @author       : lakhyun.kim
	 * @since        : 2019. 04. 19
	 * @param        : LoginVO
	 * @return       : User
	 */
	public User getLoginAuthCheck(LoginVO loginVO) {
		User user = new User();
		String login_id = loginVO.getLogin_id();	// 로그인아이디
		String login_pw = loginVO.getLogin_pw();	// 로그인패스워드
		String mb_id = loginVO.getMb_id();
		String project_type = loginVO.getProject_type();
		String access_token = loginVO.getAccess_token();
		List<Role> roles = new ArrayList<Role>();
		
		Map<String, String> UserView = new HashMap<String, String>();	//로그인 정보 조회 시 필수 정보 담음
		Map<String, String> RoleMap = new HashMap<String, String>();	//권한 정보 조회 시 필수 정보 담음

		// 로그인아이디와 회사정보를 등록
		UserView.put("mb_id", mb_id);
		UserView.put("login_id", login_id);
		UserView.put("concurrent_idx", "1");	// 메인권한
		UserView.put("project_type", project_type);
		
		//user = getUserDAO().SelectUserView(UserView);
		user = getUserDAO().selectErpUserView(UserView);	// 회사 및 설계사 정보 ERP에서 조회
		
		MessageCommonCode errorElement = null;
		String exmsg = "";
		
		if(null == user) {	//유저가 없을 경우
			errorElement = MessageCommonCode.ERR0012;
			exmsg = errorElement.getMessage();
			throw new UsernameNotFoundException(exmsg);
		}else {				//유저가 있을경우
			if(CommUtil.isNotEmpty(access_token)) {
				//액세스 토큰이 존재하는 경우
				JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
				
				//토큰 검증
				boolean isValidate = jwtTokenUtil.validateToken(access_token);
				
				if(!isValidate) {
					//토큰이 유효하지 않은 경우
					errorElement = MessageCommonCode.ERR0012;
					exmsg = errorElement.getMessage();
					throw new UsernameNotFoundException(exmsg);
				}
			}else {
				if (!sandardPasswordEncoder.matches(login_pw, user.getPassword())){//비밀번호가 틀렸을경우
					errorElement = MessageCommonCode.ERR0014;
					exmsg = errorElement.getMessage();
					throw new BadCredentialsException(exmsg);
				}
			}
			
			//위에서 에러가 나지 않은 경우 아래로
			RoleMap.put("mb_id",user.getMb_id());
			RoleMap.put("emp_cd",user.getEmp_cd());
			RoleMap.put("role_id",user.getRole_id());
			RoleMap.put("concurrent_idx", "1");// 메인권한
			
			// 권한세팅
			roles = getUserDAO().selectErpRolesHierarchyList(RoleMap);
			
			if(null == roles){//권한이 없을경우
				errorElement = MessageCommonCode.ERR0014;
				exmsg = errorElement.getMessage();
				throw new BadCredentialsException(exmsg);
			}else{
				user.setAuthorities(roles);
			}
			
			// 계정잠김여부
			if(null != user.getAcct_lock_type() && !"".equals(user.getAcct_lock_type())) {
				if("Y".equals(user.getAcct_lock_type())) {
					errorElement = MessageCommonCode.ERR0017;
					exmsg = errorElement.getMessage();
					throw new BadCredentialsException(exmsg);
				}
			}
			
			//회원사 정보 중 수수료 시스템의 회원사 정보를 쓰기 위해 회원사 정보 조회 후 세션에 등록
			MemberVO memberVO = new MemberVO();
			memberVO.setMb_id(user.getMb_id());
			Map<String, String> memberMap = getMemberDAO().selectMemberView(memberVO);
			
			if(memberMap != null) {
				user.setLogo_url(memberMap.get("logo_url"));		//좌측 상단 logo_url
				user.setSession_time(Integer.parseInt(String.valueOf(memberMap.get("session_time"))));	//세션 시간
			}
		}
		
		return user;
	}

	
	/**
	 * @Description  : SMS 인증번호 전송
	 * @author       : lakhyun.kim
	 * @since        : 2019. 04. 19
	 * @param        : LoginVO
	 * @return       : User
	 */
	public int insertSmsAuth(LoginVO loginVO) {
		int resultInt = 0;

		if(loginVO != null) {
			String auth_num = "";

			// 인증번호 생성
			SecureRandom random = new SecureRandom();
			random.setSeed(new Date().getTime());

			for (int i = 0; i < 6; i++) {
				auth_num += random.nextInt(10);
			}
			
			// 기존 인증번호 제거
			getCommDao().deleteAuthNum(loginVO);
			
			// 인증번호 저장 (실제 SMS 발송 모듈은 이 테스트 프로젝트 범위에서 제외 - 인증번호는 DB에만 저장됨)
			loginVO.setAuth_num(auth_num);
			getCommDao().insertAuthNum(loginVO);
		}
		return resultInt;
	}

	
	/**
	 * @Description  : 토큰 등록
	 * @author       : minho.kim
	 * @since        : 2020. 03. 02
	 * @param        : User
	 * @return       : int
	 */
	public int regiToken(User user) {
		
		try {
			/* 토큰 등록 */
			JwtTokenUtil jtu = new JwtTokenUtil();
			
			Map<String, Object> claims = new HashMap<>();
			
			claims.put("http://sample.genexon.co.kr/jwt_claims/mb_id", user.getMb_id());
			claims.put("http://sample.genexon.co.kr/jwt_claims/project_type", user.getProject_type());
			
			final long JWT_TOKEN_VALIDITY = 1 * 60 * 60;
			
			// 토큰 생성만 수행 (토큰 발급 이력 저장은 이 테스트 프로젝트 범위에서 제외)
			jtu.generateToken(claims, user.getUsername(), JWT_TOKEN_VALIDITY);

		} catch (Exception e) {
			// logger.error(ExceptionUtils.getFullStackTrace(e));
			return 0;
		}
		
		return 1;
	}
	/**
	 * @Description  : SMS 인증번호 체크
	 * @author       : lakhyun.kim
	 * @since        : 2019. 04. 19
	 * @return       : ModelAndView
	 */
	public boolean getAuthNumCheck(LoginVO loginVO) throws Exception {
		
    	boolean check = false;
    	// 사원아이디에 해당하는 인증번호 조회
    	Map auth_num = getCommDao().selectAuthNumCheck(loginVO);

    	// 인증번호가 있으면 비교
        if(auth_num != null && auth_num.size() > 0) {
        	if(loginVO.getAuth_num().equals(auth_num.get("auth_num"))) {
        		check = true;
        	}
        }
        
		return check;
	}

	/**
	 * @Description  : SMS 인증번호 삭제
	 * @author       : lakhyun.kim
	 * @since        : 2019. 04. 19
	 * @return       : ModelAndView
	 */
	public int deleteAuthNum(LoginVO loginVO) {
		int resultInt = 0;
		resultInt = getCommDao().deleteAuthNum(loginVO);
		return resultInt;
	}

	// CMS -> 정보계(Relay) DB 이관 배치 기능은 이 테스트 프로젝트 범위에서 제외되었다(원본: CommDAO의 Cms./Relay. 매퍼 호출).

}
