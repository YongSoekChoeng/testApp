package kr.co.gnx.comm.util;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Session;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailManager {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailManager.class);
	
	public int sendEmailToUser(String fromEMail, String toEmail,String subject,String content, List<Map> filePath) {
		logger.info("sendEmailToUser");

		EmailUtil emailUtil = new EmailUtil();
		logger.info("SSL Email Start");
		emailUtil.setFromEmail(fromEMail);
		Properties props = emailUtil.getProperties();
		Authenticator auth = emailUtil.getAuthenticator();

		Session sessionMail = Session.getDefaultInstance(props, auth);
		logger.info("Session created");

		StringBuffer bodyBuf = new StringBuffer();
		bodyBuf.append(content);

		emailUtil.sendEmailWithFile(sessionMail, toEmail, subject, bodyBuf.toString(), filePath);

		return 1;

	}

}
