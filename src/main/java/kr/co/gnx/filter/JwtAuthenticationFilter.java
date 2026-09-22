package kr.co.gnx.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kr.co.gnx.comm.jwt.JwtTokenUtil;

@Component
public class JwtAuthenticationFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	private static final String HEADER_AUTH = "Authorization";
	private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
	private Pattern allowedMethods = Pattern.compile("^(GET|POST|DELETE|PUT)$");
	private Pattern allowedExceptionUrl = Pattern.compile("(\\/api/test/me)"
														+ "|(\\/api/auth/sign-in)"
														//+ "|(\\/api/auth/sign-out)"
														+ "");
	private Pattern allowedUrl = Pattern.compile("(\\/api/.*)"+ "");

	@Override
	public void init(FilterConfig filterConfig) throws ServletException{}

	@Override
	public void destroy(){}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String url = httpRequest.getRequestURI();
		String Method = httpRequest.getMethod();
		String token = httpRequest.getHeader(HEADER_AUTH);
		logger.debug("url="+url);
		logger.debug("Method="+Method);
		logger.debug("token="+token);
		try {
			
		if(allowedUrl.matcher(url).find()){//API 관련만 token 체크한다.
			logger.debug("");
			if(!allowedExceptionUrl.matcher(url).find()) {//예외 URL이 아닐경우
				if(allowedMethods.matcher(Method).matches()){
					Boolean isTokenExpired = jwtTokenUtil.isTokenExpired(token);
					if(token != null && !isTokenExpired){// 토큰이 있고 만료되지 않은 경우 (토큰 발급 이력 DB 대조는 이 테스트 프로젝트 범위에서 제외)
						logger.debug("else OK");
						Map _jwt = new HashMap<String, String>();
						_jwt.put("parameterName", HEADER_AUTH);
						_jwt.put("token", token);
						request.setAttribute("_jwt", _jwt);

						chain.doFilter(request, response);
						//return;
					}else{
						logger.debug("SC_UNAUTHORIZED");
						httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
						return;
					}
					
				}else{
					logger.debug("SC_FORBIDDEN");
					httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
					return;
				}
			}else {
				chain.doFilter(request, response);
			}
		}else {
			chain.doFilter(request, response);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
