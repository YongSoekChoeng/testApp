package kr.co.gnx.comm.util;
/* 내부 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InetUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(InetUtil.class);

	/* 공인 및 프록시 */
	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		 
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {                    
			//Proxy 서버인 경우
			ip = request.getHeader("Proxy-Client-IP");
		}
		 
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			//Weblogic 서버인 경우
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		 
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		 
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		 
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		
		return ip;
	}
	 
	/* 만약 공인 IP없으면 내부 IP 가져오도록 처리 */
	@SuppressWarnings("rawtypes")
	public static String getCurrentEnvironmentNetworkIp(){
	    Enumeration netInterfaces = null;
	 
	    try {
	        netInterfaces = NetworkInterface.getNetworkInterfaces();
	    } catch (SocketException e) {
	        return getLocalIp();
	    }
	 
	    while (netInterfaces.hasMoreElements()) {
	        NetworkInterface ni = (NetworkInterface)netInterfaces.nextElement();
	        Enumeration address = ni.getInetAddresses();
	 
	        if (address == null) {
	            return getLocalIp();
	        }
	 
	        while (address.hasMoreElements()) {
	            InetAddress addr = (InetAddress)address.nextElement();
	            
	            if (!addr.isLoopbackAddress() && !addr.isSiteLocalAddress() && !addr.isAnyLocalAddress() ) {
	                String ip = addr.getHostAddress();
	                
	                if( ip.indexOf(".") != -1 && ip.indexOf(":") == -1 ){
	                    return ip;
	                }
	            }
	        }
	    }
	 
	    return getLocalIp();
	}
	 
	public static String getLocalIp(){
	    try {
	        return InetAddress.getLocalHost().getHostAddress();
	    } catch (UnknownHostException e) {
	        return null;
	    }
	}
	
	/* 브라우저 구분 */
	public static String getBrowser(HttpServletRequest request) {
		String brower = request.getHeader("User-Agent").toLowerCase();
		
		if(brower.indexOf("msie") > -1) {
			return "MSIE";
		} else if(brower.indexOf("chrome") > -1) {
			return "Chrome";
		} else if(brower.indexOf("opera") > -1) {
			return "Opera";
		} else if(brower.indexOf("firefox") > -1) {
			return "Firefox";
		} else if(brower.indexOf("rv:") > -1) {
			return "MSIE";
		}
		
		return "OTHER";
	}
	
	public static byte[] getHttpURLConnectionRadioChart(String savingFileWithPath, String urlPath
			, String WidthxHeight, List<String> g1Data, List<String> g2Data, List<String> gRemarks) {
		// google 차트를 이용해 방사형 차트의 이미지를 받아온다.
		
		byte[] buffer = new byte[2048];
		FileOutputStream outputStream = null;

		try {
		
//		String strchs = "300x300";
//		String encodeCHS = URLEncoder.encode(strchs, "UTF-8");
//		logger.info("encode encodeCHS=["+encodeCHS+"]");

//		String strCHD = "t:100,80,60,50,70,50,100,100,100|90,70,50,40,60,90,90,90,90";
//		String encodeCHD = URLEncoder.encode(strCHD, "UTF-8");
//		logger.info("encode strCHD=["+encodeCHD+"]");
//		
//		String strCHXL = "1:|질병|45|90|135|180|225|270|315";
//		String encodeCHXL = URLEncoder.encode(strCHXL, "UTF-8");
//		logger.info("encode strCHXL=["+encodeCHXL+"]");
		
			String encodeCHS = URLEncoder.encode(WidthxHeight, "UTF-8");
			String strCHD = "t:";
			String encodeCHD = "";
			if(g1Data != null && g1Data.size()>0) {
				for(int i=0; i<g1Data.size(); i++) {
					strCHD += g1Data.get(i);
					if( i == g1Data.size()-1 ){ strCHD += ","+g1Data.get(0);}
					else{ strCHD += ",";}					
					
				}
				//strCHD += ","+g1Data.get(0);
				if( g2Data != null && g2Data.size()>0 ) {
					strCHD += "|";
					for(int i=0; i<g2Data.size(); i++) {
						strCHD += g2Data.get(i);
						if( i == g1Data.size()-1 ){ strCHD += ","+g2Data.get(0);}
						else{ strCHD += ",";}
						
					}
					//strCHD += ","+g2Data.get(0);
				}
				encodeCHD = URLEncoder.encode(strCHD, "UTF-8");
			} else {
				return null;
			}
			String strCHXL = "1:";
			String encodeCHXL = "";
			if( gRemarks != null && gRemarks.size()>0) {
				for(int i=0; i<gRemarks.size(); i++) {
					strCHXL += "|"+gRemarks.get(i);
				}
				encodeCHXL = URLEncoder.encode(strCHXL, "UTF-8");
			}
			
//		String url = "http://chart.googleapis.com/chart?cht=r&chs=500x300&chdlp=r&chco=00FF00%2CFF00FF&chxt=y%2Cx&chxr=0%2C0%2C100%2C25&chd=e%3A..zMmZf.syf.......%2CAAAAAAAAAAAAAAAAAA&chg=25.0%2C25.0%2C4.0%2C4.0&chm=B%2CFF000080%2C0%2C1.0%2C5.0%7CB%2CFF990080%2C1%2C1.0%2C5.0&chxl=1%3A%7C0%7C45%7C90%7C135%7C180%7C225%7C270%7C315";
//		String url = "http://chart.googleapis.com/chart?"
//					+"cht=r"
//					+"&chs=300x300"
//					+"&chdlp=r&chco=FFC69F%2C8DE855&chxt=y%2Cx&chxr=0%2C0%2C100%2C20"
//					+"&chd=t:100,80,60,50,70,50,100,100,100|90,70,50,40,60,90,90,90,90"
//					+"&chg=25.0%2C25.0%2C4.0%2C4.0"
//					+"&chm=B%2CFFC69F80%2C0%2C1.0%2C5.0%7CB%2C8DE85580%2C1%2C1.0%2C5.0"
//					+"&chxl=1%3A%7C%EC%A7%88%EB%B3%91%7C45%7C90%7C135%7C180%7C225%7C270%7C315";		// 동심원 이름들, 질병(%EC%A7%88%EB%B3%91)
			
			
			// urlPath = http://chart.googleapis.com
			String url 	= urlPath+"/chart?"
						+ "cht=r"
						+ "&chs="+encodeCHS
						+ "&chdlp=r&chco=5ACBF5%2CF390BB"		// 방사형 컬러지정
						+ "&chxt=y%2Cx&chxr=0%2C0%2C100%2C20"	// 방사선 간격
						+ "&chd="+encodeCHD
						//+ "&chg=25.0%2C25.0%2C4.0%2C4.0"		// 바탕 모눈 눈금
						+ "&chm=B%2C5ACBF590%2C0%2C1.0%2C5.0%7CB%2CF390BB90%2C1%2C1.0%2C5.0"
						+ "&chxl="+encodeCHXL				// 범례
						;
			
			//logger.info("====================chart url = ["+url+"]");
		
		
			//logger.info("===================== start HTTPURLCONNECTION ============");
			
			HttpURLConnection conn;
			conn = (HttpURLConnection) new URL( url ).openConnection();
			
			conn.setConnectTimeout( 5000 );
			conn.setReadTimeout( 5000 );
			// oops! conn.setDoOutput(true);
			conn.setUseCaches(true);
			conn.setRequestMethod( "GET" );
	
			conn.setRequestProperty("Connection", "Keep-Alive" );
			//conn.setRequestProperty("Content-Type", "image/png");
			conn.setRequestProperty("Content-Type","application/x-www-form-urlendcoded");
	
			conn.connect();
			  
			int response = conn.getResponseCode();
			//logger.info("=================");
			//logger.info(String.format("HTTP_RESPONSE:%s CODE:%d TYPE:%s SIZE:%d", url, response, conn.getContentType(), conn.getContentLength()));
			//logger.info("=================");
			
			if( response == HttpURLConnection.HTTP_OK ){
				//logger.info("HTTP_OK");
				InputStream is = conn.getInputStream();
				outputStream = new FileOutputStream(savingFileWithPath);

				//logger.info("png file savingFileWithPath=["+savingFileWithPath+"]");
				
				if ("gzip".equals(conn.getContentEncoding())){
					//logger.info("zipped image");
					is = new GZIPInputStream(is);
				}

				// opens an output stream to save into file
				int bytesRead = -1;
				
				while ((bytesRead = is.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				
				is.close();
				//outputStream.close();
				
				conn.disconnect();
				
				//logger.info("download completed!");
				
				
			}
			
		} catch(Exception e) {
			logger.info(e.getMessage());
			
		} finally {
			if(outputStream != null){
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.info(e.getMessage());
				}
			}
		}
		
		return buffer;
		
	}
	
	public static byte[] chage100(String savingFileWithPath, String urlPath
			, String WidthxHeight, String g1Data, String g2Data, String g3Data, String g4Data, String g5Data) {
		// google 차트를 이용해 방사형 차트의 이미지를 받아온다.

		String url = "https://chart.googleapis.com/chart?cht=lc&chs=200x125&chd=t:" + g1Data;
    	for(int i=0; i<50;i++){url += ("," + g1Data);}
    	for(int i=0; i<51;i++){url += ("," + g2Data);}
    	for(int i=0; i<51;i++){url += ("," + g3Data);}
    	for(int i=0; i<51;i++){url += ("," + g4Data);}
    	for(int i=0; i<51;i++){url += ("," + g5Data);}
    	url += "&chdlp=r&chco=5ACBF5" // %2C 기준으로 색상 지정
				+"&chm=B%2C5ACBF580%2C0%2C1.0%2C5.0%7CB%2C000080%2C1%2C1.0%2C5.0"; // 범위 색상 및 색상 알파값들..알파값이 뭔지는 정확히 모름..
																					 // 똑같이 %2C 기준으로 색상값을 넣으면 됨
    	byte[] buffer = new byte[2048];
    	FileOutputStream outputStream = null;
    	
    	try {
			HttpURLConnection conn;
			conn = (HttpURLConnection) new URL( url ).openConnection();
			
			conn.setConnectTimeout( 5000 );
			conn.setReadTimeout( 5000 );
			// oops! conn.setDoOutput(true);
			conn.setUseCaches(true);
			conn.setRequestMethod( "GET" );
	
			conn.setRequestProperty("Connection", "Keep-Alive" );
			//conn.setRequestProperty("Content-Type", "image/png");
			conn.setRequestProperty("Content-Type","application/x-www-form-urlendcoded");
	
			conn.connect();
			  
			int response = conn.getResponseCode();
			//logger.info("=================");
			//logger.info(String.format("HTTP_RESPONSE:%s CODE:%d TYPE:%s SIZE:%d", url, response, conn.getContentType(), conn.getContentLength()));
			//logger.info("=================");
			
			if( response == HttpURLConnection.HTTP_OK ){
				//logger.info("HTTP_OK");
				InputStream is = conn.getInputStream();
				outputStream = new FileOutputStream(savingFileWithPath);
	
				//logger.info("png file savingFileWithPath=["+savingFileWithPath+"]");
				
				if ("gzip".equals(conn.getContentEncoding())){
					//logger.info("zipped image");
					is = new GZIPInputStream(is);
				}
	
				// opens an output stream to save into file
				int bytesRead = -1;
				while ((bytesRead = is.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				
				is.close();
				//outputStream.close();
				
				conn.disconnect();
				
				//logger.info("download completed!");
			}
    	} catch(Exception e) {
			logger.info(e.getMessage());
			
		} finally {
			if(outputStream != null){
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.info(e.getMessage());
				}
			}
		}
    	return buffer;
		
	}
	
	/* 전/후 그래프 */
	public static byte[] chage200(String savingFileWithPath, String urlPath
			, String WidthxHeight, String g1Data, String g2Data, String g3Data, String g4Data, String g5Data
			, String g6Data, String g7Data, String g8Data, String g9Data, String g10Data) {
		// google 차트를 이용해 방사형 차트의 이미지를 받아온다.
		
		String url = "https://chart.googleapis.com/chart?cht=lc&chs=200x125&chd=t:" + g1Data;
		for(int i=0; i<40;i++){url += ("," + g1Data);}
		for(int i=0; i<41;i++){url += ("," + g2Data);}
		for(int i=0; i<41;i++){url += ("," + g3Data);}
		for(int i=0; i<41;i++){url += ("," + g4Data);}
		for(int i=0; i<41;i++){url += ("," + g5Data);}
		url +="|"+g6Data;
		for(int i=0; i<40;i++){url += ("," + g6Data);}
		for(int i=0; i<41;i++){url += ("," + g7Data);}
		for(int i=0; i<41;i++){url += ("," + g8Data);}
		for(int i=0; i<41;i++){url += ("," + g9Data);}
		for(int i=0; i<41;i++){url += ("," + g10Data);}
		url += "&chdlp=r&chco=F390BB%2C5ACBF5" // %2C 기준으로 색상 지정
				+"&chm=B%2CF390BB80%2C0%2C1.0%2C5.0%7CB%2C5ACBF580%2C1%2C1.0%2C5.0"; // 범위 색상 및 색상 알파값들..알파값이 뭔지는 정확히 모름..
																					 // 똑같이 %2C 기준으로 색상값을 넣으면 됨
		
		byte[] buffer = new byte[2048];
		FileOutputStream outputStream = null;
		
		try {
			HttpURLConnection conn;
			conn = (HttpURLConnection) new URL( url ).openConnection();
			
			conn.setConnectTimeout( 5000 );
			conn.setReadTimeout( 5000 );
			// oops! conn.setDoOutput(true);
			conn.setUseCaches(true);
			conn.setRequestMethod( "GET" );

			conn.setRequestProperty("Connection", "Keep-Alive" );
			//conn.setRequestProperty("Content-Type", "image/png");
			conn.setRequestProperty("Content-Type","application/x-www-form-urlendcoded");

			conn.connect();
			  
			int response = conn.getResponseCode();
			//logger.info("=================");
			//logger.info(String.format("HTTP_RESPONSE:%s CODE:%d TYPE:%s SIZE:%d", url, response, conn.getContentType(), conn.getContentLength()));
			//logger.info("=================");
			
			if( response == HttpURLConnection.HTTP_OK ){
				//logger.info("HTTP_OK");
				InputStream is = conn.getInputStream();
				outputStream = new FileOutputStream(savingFileWithPath);

				//logger.info("png file savingFileWithPath=["+savingFileWithPath+"]");
				
				if ("gzip".equals(conn.getContentEncoding())){
					//logger.info("zipped image");
					is = new GZIPInputStream(is);
				}

				// opens an output stream to save into file
				int bytesRead = -1;
				
				while ((bytesRead = is.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				
				is.close();
				//outputStream.close();
				
				conn.disconnect();
				
				//logger.info("download completed!");
			}
		} catch(Exception e) {
			logger.info(e.getMessage());
			
		} finally {
			if(outputStream != null){
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.info(e.getMessage());
				}
			}
		}
		return buffer;
		
	}
}
