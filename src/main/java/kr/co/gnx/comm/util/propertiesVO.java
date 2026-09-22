package kr.co.gnx.comm.util;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class propertiesVO {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public String readProperties(String w) {
		Properties properties = new Properties();
		try {

	    	String propFileName = "/properties/globals.xml";  // properties파일명
	    	        
	    	InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
	    	properties.loadFromXML(inputStream);
	    	/*for(String key : properties.stringPropertyNames()) {
	    	     String value = properties.getProperty(key);
	    	     System.out.println(key + " => " + value);
	    	}*/
	    	} catch (Exception e) {
	    		// TODO: handle exception
	    	}
		
		return properties.getProperty(w);
	}
	
	public String getDBGubun() {
		return readProperties("Globals.DBGubun");
	}
	public String getLogerYN() {
		return readProperties(getDBGubun()+"."+"Globals.LogerYN");
	}
	public String getHttpdomain() {
		return readProperties(getDBGubun()+"."+"Globals.httpdomain");
	}
	
	public String getErpUrl() {
		return readProperties(getDBGubun()+"."+"Globals.ANYBIZ_ErpUrl");
	}
	
}
