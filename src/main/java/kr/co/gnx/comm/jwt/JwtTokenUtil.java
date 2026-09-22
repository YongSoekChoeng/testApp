package kr.co.gnx.comm.jwt;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
	private static final long serialVersionUID = -2550185165626007488L;
	private String secretKey = "pinestreehasoakseeds#genexonshared";
	
	private Date nowDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
	
	@PostConstruct
	public void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	/*
	 * TOKEN으로 유저명 찾아옴!!
	 * */
	public String getUsernameFromToken(String token) {
		if(validateToken(token)) {
			return getClaimFromToken(token, Claims::getSubject);
		}
		
		return "";
	}
	
	/*
	 * TOKEN으로 데이터 찾아옴!!
	 * */
	public String getClaimsDataFromToken(String name, String token) {
		if(validateToken(token)) {
			Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
			String realname = claims.get(name, String.class);
			return realname;
		}
		
		return "";
	}
	
	/*
	 * TOKEN으로 유효시간 찾아옴!!
	 * */
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		if(validateToken(token)) {
			final Claims claims = getAllClaimsFromToken(token);
			return claimsResolver.apply(claims);
		}
		return null;
	}
	
	private Claims getAllClaimsFromToken(String token) {
		Claims claims= Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		return claims;
	}
	
	public Boolean isTokenExpired(String token) {
		Boolean validateToken = validateToken(token);
		
		if(validateToken) {
			Date expiration = getExpirationDateFromToken(token);
			
			int check = expiration.compareTo(nowDate);
			
			if(0==check) {
				return false;
			}else if(1==check) {
				return true;
			}else if(-1==check) {
				return false;
			}else {
				return false;
			}
		}
		
		return validateToken;
	}
	
	/*
	 * 토큰생성
	 * */
	public String generateToken(Map<String, Object> claims, String emp_cd, long JWT_TOKEN_VALIDITY) {
		return doGenerateToken(claims, emp_cd, JWT_TOKEN_VALIDITY);
	}
	
	private String doGenerateToken(Map<String, Object> claims, String subject, long JWT_TOKEN_VALIDITY) {
		Map<String, Object> jwtHeader = new HashMap<String, Object>();
		jwtHeader.put("typ", "JWT");
		jwtHeader.put("alg", "HS512");
		
		return Jwts.builder()
				.setHeader(jwtHeader)
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(nowDate)
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}
	
	public Boolean validateToken(String token) {
		try{
			getAllClaimsFromToken(token);
			return true;
		} catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }

    	return false;
	}
	
	//Request에서 ACCESS 토큰 가져올때 사용
	public String resolveAccessToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
	
	//Request에서 Refresh 토큰 가져올때 사용
	public String resolveRefreshToken(HttpServletRequest request) {
        return request.getHeader("Authorization_Refresh");
    }
}