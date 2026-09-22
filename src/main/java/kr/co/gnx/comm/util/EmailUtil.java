package kr.co.gnx.comm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import kr.co.gnx.system.file.FileVO;

public class EmailUtil {
	
private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);
	
//	private final String fromEmail = "tesoro319@gmail.com";
//	private final String password = "tesoro_password"; 
	private String fromEmail;
	private String password;
	private String toEmail;
	
	
	public String getFromEmail() {
		return fromEmail;
	}
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToEmail() {
		return toEmail;
	}
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public Properties getProperties() {
		Properties props = new Properties();
		
        /*props.put("mail.smtp.host", "localhost"); //SMTP Host
        props.put("mail.smtp.auth", "false"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "25"); //SMTP Port
        props.put("mail.debug","true");*/
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465"); //SMTP Port
        props.put("mail.debug","true");
        
        return props;
	}
	
	public Authenticator getAuthenticator() {
		Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            @Override
			protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("changewind94@gmail.com", "twpxtrxkcrckkisi");
                //return new PasswordAuthentication("tesoro.insure@gmail.com", "t@5615447");
            }
        };
        
        return auth;
	}
	
	/**
     * Utility method to send simple HTML email
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    public void sendEmail(Session session, String toEmail, String subject, String body){
    	try {
    		MimeMessage msg = new MimeMessage(session);
    		//set message headers
			//msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
 
			msg.setFrom(new InternetAddress(fromEmail));
			msg.setReplyTo(InternetAddress.parse("toEmail", false));
			msg.setSubject(subject, "UTF-8");
			msg.setContent(body,"text/html; charset=UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			
			
			// Multi Part 
	        Multipart multipart = new MimeMultipart();
	        BodyPart messageBodyPart = new MimeBodyPart();

	        // 메일 내용 설정.
	        messageBodyPart.setText(body);     
	        multipart.addBodyPart(messageBodyPart);
	        
			BodyPart fileBodyPart = new MimeBodyPart();
			
			String filepath ="F:\\test\\";
			String file_nm ="Chrysanthemum";
			
	    	//DataSource source = new FileDataSource(filepath + file_nm + ".jpg"); 
	    	//fileBodyPart.setDataHandler(new DataHandler(source)); 
	    	//String filename = file_nm + ".jpg";
			File file = new File(filepath + file_nm + ".jpg");
			FileInputStream input = null;
			input = new FileInputStream(file);
			
			FileOutputStream output = new FileOutputStream(file_nm);
			
			 byte[] buffer = new byte[4096];
		        int bytesRead = -1;
		        while ((bytesRead = input.read(buffer)) != -1) {
		        	output.write(buffer, 0, bytesRead);
		        }
		        
	    	
			/* while(true) {
		            int data = input.read();
		            //System.out.println(data);
		            if(data == -1) {
		                    System.out.println("파일 끝");
		                    break;
		             }
		   
		            output.write(data);
		   
		    }*/

    	
        	fileBodyPart.setFileName(MimeUtility.encodeText(file_nm));
	 
        	multipart.addBodyPart(fileBodyPart);
	        
			logger.info("Message is ready");
			
			//msg.writeTo(output);
			//multipart.writeTo(output);
			msg.setContent(multipart);
			Transport.send(msg);  
			logger.info("EMail Sent Successfully!!");
			input.close();
		    output.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
	/**
     * Utility method to send simple HTML email
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    public void sendEmail(Session session, String toEmail, String subject, String body,HttpServletResponse response){
    	try {
    		MimeMessage msg = new MimeMessage(session);
    		//set message headers
			//msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
 
			msg.setFrom(new InternetAddress(fromEmail));
			msg.setReplyTo(InternetAddress.parse("toEmail", false));
			msg.setSubject(subject, "UTF-8");
			msg.setContent(body,"text/html; charset=UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			
			
			// Multi Part 
	        Multipart multipart = new MimeMultipart();
	        BodyPart messageBodyPart = new MimeBodyPart();

	        // 메일 내용 설정.
	        messageBodyPart.setText(body);     
	        multipart.addBodyPart(messageBodyPart);
	        
			//BodyPart fileBodyPart = new MimeBodyPart();
			
			//String filepath ="F:\\test\\";
			//String file_nm ="Chrysanthemum";
			
	    	//DataSource source = new FileDataSource(filepath + file_nm + ".jpg"); 
	    	//fileBodyPart.setDataHandler(new DataHandler(source)); 
	    	//String filename = file_nm + ".jpg";
			//File file = new File(filepath + file_nm + ".jpg");
			//FileInputStream input = null;
			//input = new FileInputStream(file);
			
			ServletOutputStream output = response.getOutputStream();

	    	
			/* while(true) {
		            int data = input.read();
		            //System.out.println(data);
		            if(data == -1) {
		                    System.out.println("파일 끝");
		                    break;
		             }
		   
		            output.write(data);
		   
		    }*/

    	
        	//fileBodyPart.setFileName(MimeUtility.encodeText(file_nm));
	 
        	//multipart.addBodyPart(fileBodyPart);
	        
			logger.info("Message is ready");
			
			File file = new File("F:\\\\test\\\\test.zip");
			FileInputStream input = new FileInputStream(file);
			
			FileCopyUtils.copy(input, output);
			//msg.writeTo(output);
			//multipart.writeTo(output);
			//msg.setContent(multipart);
			Transport.send(msg);  
			logger.info("EMail Sent Successfully!!");
		    output.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
	/**
     * Utility method to send simple HTML email
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    public void sendEmail1(Session session, String toEmail, String subject, String body){
    	try {
    		MimeMessage msg = new MimeMessage(session);
    		//set message headers
			//msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
 
			msg.setFrom(new InternetAddress(fromEmail));
			msg.setReplyTo(InternetAddress.parse("toEmail", false));
			msg.setSubject(subject, "UTF-8");
			msg.setContent(body,"text/html; charset=UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			
			
			// Multi Part 
	        Multipart multipart = new MimeMultipart();
	        BodyPart messageBodyPart = new MimeBodyPart();

	        // 메일 내용 설정.
	        messageBodyPart.setText(body);     
	        multipart.addBodyPart(messageBodyPart);
	        
			BodyPart fileBodyPart = new MimeBodyPart();
			
			String filepath ="F:\\test\\";
			String file_nm ="Chrysanthemum";
			
	    	DataSource source = new FileDataSource(filepath + file_nm + ".jpg"); 
	    	fileBodyPart.setDataHandler(new DataHandler(source)); 
	    	String filename = file_nm + ".jpg";
			

    	
        	fileBodyPart.setFileName(MimeUtility.encodeText(file_nm));
	 
        	multipart.addBodyPart(fileBodyPart);
	        
			logger.info("Message is ready");
			
			msg.setContent(multipart);
			Transport.send(msg);  
			logger.info("EMail Sent Successfully!!");
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
	/**
     * Utility method to send simple HTML email
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    public void sendEmail(Session session, String toEmail, String subject, String body, List<FileVO> filePath){
    	try {
    		/*MimeMessage msg = new MimeMessage(session);
    		
    		InternetAddress from = new InternetAddress("factfindergenexon@gmail.com","Fact Finder","UTF-8");
    		msg.setFrom(from);
    		
    		InternetAddress to = new InternetAddress(toEmail);
    		msg.setRecipient(Message.RecipientType.TO, to);
    		msg.setSubject(subject, "UTF-8");
    		
    		// Multi Part 
	        Multipart multipart = new MimeMultipart();
	        BodyPart messageBodyPart = new MimeBodyPart();

	        // 메일 내용 설정.
	        messageBodyPart.setText(body);     
	        multipart.addBodyPart(messageBodyPart);
	        
	        if (filePath != null) {
	        	if (filePath.size() > 0) {
		        	for (int i = 0; i < filePath.size(); i++) {
		        		BodyPart fileBodyPart = new MimeBodyPart();
			        	DataSource source = new FileDataSource(filePath.get(i).getFILE_PATH() + filePath.get(i).getORIGINAL_NAME() + ".jpg"); 
			        	fileBodyPart.setDataHandler(new DataHandler(source)); 
			        	String filename = filePath.get(i).getFILE_NAME() + ".jpg";
			        	fileBodyPart.setFileName(MimeUtility.encodeText(filename));
			 
			        	multipart.addBodyPart(fileBodyPart);
		        	}
		        }
	        }
	         
	        msg.setContent(multipart);*/
			
    		MimeMessage msg = new MimeMessage(session);
    		//set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
 
			msg.setFrom(new InternetAddress(fromEmail));
			msg.setReplyTo(InternetAddress.parse("toEmail", false));
			msg.setSubject(subject, "UTF-8");
			//msg.setText(body, "UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			
			// Multi Part 
	        Multipart multipart = new MimeMultipart();
	        BodyPart messageBodyPart = new MimeBodyPart();

	        // 메일 내용 설정.
	        messageBodyPart.setText(body);     
	        multipart.addBodyPart(messageBodyPart);
	        
	        if (filePath != null) {
	        	if (filePath.size() > 0) {
		        	for (int i = 0; i < filePath.size(); i++) {
		        		BodyPart fileBodyPart = new MimeBodyPart();
			        	DataSource source = new FileDataSource(filePath.get(i).getFile_path() + filePath.get(i).getFile_nm() + ".jpg"); 
			        	fileBodyPart.setDataHandler(new DataHandler(source)); 
			        	String filename = filePath.get(i).getFile_nm() + ".jpg";
			        	fileBodyPart.setFileName(MimeUtility.encodeText(filename));
			 
			        	multipart.addBodyPart(fileBodyPart);
		        	}
		        }
	        }
	         
	        msg.setContent(multipart);
    		
			logger.info("Message is ready");
			Transport.send(msg);  
			logger.info("EMail Sent Successfully!!");
			
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
    public void sendEmailWithFile(Session session, String toEmail, String subject, String body, List<Map> filePath){
    	try {
    		MimeMessage msg = new MimeMessage(session);
    		MimeMultipart multipart = new MimeMultipart();
		
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(body,"text/html; charset=UTF-8");
			messageBodyPart.setHeader("Content-Transfer-Encoding", "base64");
			multipart.addBodyPart(messageBodyPart);

			for(int i=0 ; i<filePath.size() ; i++) {	
				Map file = filePath.get(i);
				String filename = (String) file.get("FILE_NM");
				String filepath = (String) file.get("FILE_PATH");

				try {
					DataSource source = new FileDataSource(filepath);
					MimeBodyPart messageBodyPartFile = new MimeBodyPart();
					messageBodyPartFile.setDataHandler(new DataHandler(source));
					messageBodyPartFile.setFileName(MimeUtility.encodeText(filename, "UTF-8","B"));
					multipart.addBodyPart(messageBodyPartFile);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
			
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			
			msg.setFrom(new InternetAddress(fromEmail));
			msg.setReplyTo(InternetAddress.parse("toEmail", false));
			msg.setSubject(subject, "UTF-8");
			msg.setContent(multipart);
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			logger.info("Message is ready");
			logger.info("## EMAIL LOG ## FROM : "+fromEmail+" / TO : "+toEmail + " / SUBJECT : "+subject);
			Transport.send(msg); 
			logger.info("EMail Sent Successfully!!");
			
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}
