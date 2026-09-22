package kr.co.gnx.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Config {

	@Autowired 
	Properties globals;
	
    public String val(String key){
        return globals.getProperty(key);
    }
}
