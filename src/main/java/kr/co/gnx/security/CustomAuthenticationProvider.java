package kr.co.gnx.security;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kr.co.gnx.comm.CommService;
import kr.co.gnx.security.model.User;
import kr.co.gnx.system.login.LoginVO;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	private CommService commService;
	
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		User user = new User();
		String LOGIN_ID = authentication.getName();// 로그인아이디
		String LOGIN_PW = (String) authentication.getCredentials();//로그인패스워드
		String ACCESS_TOKEN = "";
		String LOGIN_MB_ID = "";
		Collection<? extends GrantedAuthority> authorities = null;
		Map<String, String> loginData = new HashMap<String, String>();//로그인데이터정보 담음
		
		// 기타정보 획득
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Enumeration<?> param = request.getParameterNames();
		
		while (param.hasMoreElements()){
			String name = (String)param.nextElement();
			
			// 비밀번호의 경우 여기에 등록하지 않음
			if(name.toLowerCase().equals("password")) continue;
			
			loginData.put(name.toLowerCase(), request.getParameter(name));
		}
		
		if(null!=loginData) {
			LOGIN_MB_ID = null == loginData.get("mb_id")?"":loginData.get("mb_id").toUpperCase();
			ACCESS_TOKEN = null == loginData.get("access_token") ? "" : loginData.get("access_token");
		}
		
		LoginVO loginVO = new LoginVO();
		loginVO.setLogin_id(LOGIN_ID);
		loginVO.setLogin_pw(LOGIN_PW);
		loginVO.setMb_id(LOGIN_MB_ID);
		loginVO.setAccess_token(ACCESS_TOKEN);
		
		user = commService.getLoginAuthCheck(loginVO);
		authorities = user.getAuthorities();
		return new UsernamePasswordAuthenticationToken(user, LOGIN_PW, authorities);
	}

	
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
