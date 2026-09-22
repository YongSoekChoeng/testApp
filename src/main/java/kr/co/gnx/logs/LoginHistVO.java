package kr.co.gnx.logs;

import kr.co.gnx.base.BaseVO;

public class LoginHistVO extends BaseVO {
	private String login_ip;		/* 로그인IP varchar(40)*/
	private String login_dtm;		/* 로그인일시 datetime*/
	
	private String send_type;    /* 구분       VARCHAR(20)  */
	private String sender;       /* 보내는사람 VARCHAR(50)  */
	private String reciver;      /* 받는사람   VARCHAR(50)  */
	private String message;      /* 메세지     VARCHAR(300) */
	
	
	
	public String getSend_type() {
		return send_type;
	}

	public void setSend_type(String send_type) {
		this.send_type = send_type;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReciver() {
		return reciver;
	}

	public void setReciver(String reciver) {
		this.reciver = reciver;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLogin_ip() {
		return login_ip;
	}
	
	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}
	
	public String getLogin_dtm() {
		return login_dtm;
	}
	
	public void setLogin_dtm(String login_dtm) {
		this.login_dtm = login_dtm;
	}

	@Override
	public String toString() {
		return "LoginHistVO [login_ip=" + login_ip + ", login_dtm=" + login_dtm + ", send_type=" + send_type
				+ ", sender=" + sender + ", reciver=" + reciver + ", message=" + message + "]";
	}
}
