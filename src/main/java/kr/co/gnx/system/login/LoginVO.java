package kr.co.gnx.system.login;

import kr.co.gnx.base.BaseVO;

public class LoginVO extends BaseVO {

	private String login_id;
	private String login_pw;
	private String pwd_intzn_type;//비밀번호초기화여부
	private String acct_lock_type;//계정잠김여부
	private String login_autr_type;//2tactor 인증여부
	private String user_pwd;//사용자 비밀번호
	private String trns_user_pwd;//임시 비밀번호
	private String pwd_err_nbtm;//비밀번호 오류횟수
	private String pwd_chg_dt;//비밀번호 변경일자
	
	private String login_ip;     /* 로그인IP   VARCHAR(40)  */
    private String login_dtm;    /* 로그인일시 DATETIME     */
	
	private String login_autr_type_nm;

	private String auth_num;
	
	private String sso_key;
	private String newPwd;
	private String pageUri;
	
	private String login_type;
	
	private String access_token;
	
	public String getLogin_type() {
		return login_type;
	}
	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}
	
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	
	public String getLogin_pw() {
		return login_pw;
	}
	public void setLogin_pw(String login_pw) {
		this.login_pw = login_pw;
	}
	
	public String getPwd_intzn_type() {
		return pwd_intzn_type;
	}
	public void setPwd_intzn_type(String pwd_intzn_type) {
		this.pwd_intzn_type = pwd_intzn_type;
	}
	
	public String getAcct_lock_type() {
		return acct_lock_type;
	}
	public void setAcct_lock_type(String acct_lock_type) {
		this.acct_lock_type = acct_lock_type;
	}
	
	public String getLogin_autr_type() {
		return login_autr_type;
	}
	public void setLogin_autr_type(String login_autr_type) {
		this.login_autr_type = login_autr_type;
	}
	
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	
	public String getTrns_user_pwd() {
		return trns_user_pwd;
	}
	public void setTrns_user_pwd(String trns_user_pwd) {
		this.trns_user_pwd = trns_user_pwd;
	}
	
	public String getPwd_err_nbtm() {
		return pwd_err_nbtm;
	}
	public void setPwd_err_nbtm(String pwd_err_nbtm) {
		this.pwd_err_nbtm = pwd_err_nbtm;
	}
	
	public String getPwd_chg_dt() {
		return pwd_chg_dt;
	}
	public void setPwd_chg_dt(String pwd_chg_dt) {
		this.pwd_chg_dt = pwd_chg_dt;
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
	
	public String getLogin_autr_type_nm() {
		return login_autr_type_nm;
	}
	public void setLogin_autr_type_nm(String login_autr_type_nm) {
		this.login_autr_type_nm = login_autr_type_nm;
	}
	
	public String getAuth_num() {
		return auth_num;
	}
	public void setAuth_num(String auth_num) {
		this.auth_num = auth_num;
	}
	
	public String getSso_key() {
		return sso_key;
	}
	public void setSso_key(String sso_key) {
		this.sso_key = sso_key;
	}
	
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	
	public String getPageUri() {
		return pageUri;
	}
	public void setPageUri(String pageUri) {
		this.pageUri = pageUri;
	}
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	@Override
	public String toString() {
		return "LoginVO [login_id=" + login_id + ", login_pw=" + login_pw + ", pwd_intzn_type=" + pwd_intzn_type
				+ ", acct_lock_type=" + acct_lock_type + ", login_autr_type=" + login_autr_type + ", user_pwd="
				+ user_pwd + ", trns_user_pwd=" + trns_user_pwd + ", pwd_err_nbtm=" + pwd_err_nbtm + ", pwd_chg_dt="
				+ pwd_chg_dt + ", login_ip=" + login_ip + ", login_dtm=" + login_dtm + ", login_autr_type_nm="
				+ login_autr_type_nm + ", auth_num=" + auth_num + ", sso_key=" + sso_key + ", newPwd=" + newPwd
				+ ", pageUri=" + pageUri + ", login_type=" + login_type + "]";
	}	
	
	
}
