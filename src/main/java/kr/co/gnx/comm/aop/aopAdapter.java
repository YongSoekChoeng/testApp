package kr.co.gnx.comm.aop;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.sitemesh.webapp.contentfilter.HttpServletRequestFilterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import kr.co.gnx.config.Constants;
import kr.co.gnx.config.Constants.LOGGING;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * InterceptorAdapter 후 진입점
 * @author kihong
 * 
 */
@Aspect
public class aopAdapter {
	  
	private static final Logger logger = LoggerFactory.getLogger(aopAdapter.class);
	
	/**
	 * @description 메소드 실행전 실행
	 * @param joinPoint
	 * @throws UnsupportedEncodingException
	 */
	@Before("execution(public * kr.co.gnx.*.*.*(..))")
	public void aopcheck(JoinPoint joinPoint) throws UnsupportedEncodingException , Exception {	

	    LOGGING qlog = Constants.QueryLogging;
	    Constants.QueryLogging = LOGGING.LOGGING; 
//	    Constants.QueryLogging = LOGGING.NOLOGGING;
		
		logger.debug("========AOP=========");
		logger.debug("joinPoint.toString()");
		logger.debug(joinPoint.toString());
		logger.debug("## 시작 " + joinPoint.getTarget().getClass().getSimpleName() + " "+ joinPoint.getSignature().getName() + " ##");

		if (0 != joinPoint.getArgs().length) {
			// DefaultMultipartHttpServletRequest
			if ((joinPoint.getArgs()[0] instanceof HttpServletRequestFilterable)
					|| (joinPoint.getArgs()[0] instanceof DefaultMultipartHttpServletRequest)) {
				HttpServletRequest request = (HttpServletRequest) joinPoint
						.getArgs()[0];
				String url = request.getRequestURI();
				logger.debug("aopcheck url="+url);

			} // end if
		} // end if
		
		Constants.QueryLogging = qlog;
		logger.debug("");
	}
		
	
    /**
     * @description 메소드 실행후 실행 
     * @param joinPoint
     */
	@After("execution(public * kr.co.gnx.*.*.*(..))")
	public void afterLogging(JoinPoint joinPoint) {
		logger.debug("## After!!!! {} {} ##", joinPoint.getTarget().getClass().getSimpleName(),
				joinPoint.getSignature().getName());
	}

	
	/**
	 * @description method 정상 실행후 
	 * @param joinPoint
	 * @param ret
	 */
    @AfterReturning(pointcut = "execution(public * kr.co.gnx.*.*.*(..))", returning = "ret")
	public void returningLogging(JoinPoint joinPoint, Object ret) {
		logger.debug("## AfterReturning!!!! {}", joinPoint.getSignature().getName());

	}
		
    
    /**
     * @description Exception 발생시 실행
     * @param joinPoint
     * @param ex
     * @throws Throwable 
     */
	@AfterThrowing(pointcut = "execution(public * kr.co.gnx.*.*.*(..))", throwing = "ex")
	public void throwingLogging(JoinPoint joinPoint, Throwable ex) throws Throwable {
		logger.error("");
		logger.error("");
		logger.error("## 에러 {} {} ##", joinPoint.getTarget().getClass().getSimpleName(),
				joinPoint.getSignature().getName());
		logger.error("## 메시지 : {}", ex.getMessage());
		logger.error("");
		ex.printStackTrace();

		throw ex;
	}

}