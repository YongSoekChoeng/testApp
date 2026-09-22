package kr.co.gnx.security.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.swagger.annotations.ApiModelProperty;


public class User implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	private List<Role> authorities;
	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	@ApiModelProperty(allowEmptyValue=false,hidden=true)//enabled 숨김처리
	private boolean enabled = true;
	@ApiModelProperty(allowEmptyValue=false,hidden=true)//username 숨김처리
	private String username;
	
	@ApiModelProperty(allowEmptyValue=false,hidden=true)//project_type 숨김처리
	private String project_type;
	
	@ApiModelProperty(value="회원사ID",allowEmptyValue=false,hidden=false)
	private String mb_id;
	@ApiModelProperty(value="회원사명",allowEmptyValue=false,hidden=false)
	private String mb_nm;
	@ApiModelProperty(value="회원사로그인ID",allowEmptyValue=false,hidden=false)
	private String mb_login_id;
	@ApiModelProperty(value="로그인ID",allowEmptyValue=false,hidden=false)
	private String user_id;
	@ApiModelProperty(value="사원코드",allowEmptyValue=false,hidden=false)
	private String emp_cd;
	@ApiModelProperty(value="사원명",allowEmptyValue=false,hidden=false)
	private String emp_nm;
	@ApiModelProperty(value="비밀번호",allowEmptyValue=false,hidden=true)//비밀번호는 숨김처리
	private String password;
	@ApiModelProperty(value="권한",allowEmptyValue=false,hidden=false)
	private String role_id;
	@ApiModelProperty(value="직급",allowEmptyValue=false,hidden=false)
	private String jikgub;
	@ApiModelProperty(value="직책",allowEmptyValue=false,hidden=false)
	private String jikchk;
	@ApiModelProperty(value="조직코드",allowEmptyValue=false,hidden=false)
	private String scd;
	@ApiModelProperty(value="조직명",allowEmptyValue=false,hidden=false)
	private String snm;
	@ApiModelProperty(value="핸드폰번호",allowEmptyValue=false,hidden=false)
	private String hpno;
	@ApiModelProperty(value="집전화",allowEmptyValue=false,hidden=false)
	private String telno;
	@ApiModelProperty(value="내선번호",allowEmptyValue=false,hidden=false)
	private String extno;
	@ApiModelProperty(value="이메일",allowEmptyValue=false,hidden=false)
	private String email;
	@ApiModelProperty(value="재직여부",allowEmptyValue=false,hidden=false)
	private String empsta;
	@ApiModelProperty(value="계정잠김여부",allowEmptyValue=false,hidden=false)
	private String acct_lock_type;
	@ApiModelProperty(value="패키지명",allowEmptyValue=false,hidden=false)
	private String pkg_name;
	@ApiModelProperty(value="권한복구용 아이디",allowEmptyValue=false,hidden=false)
	private String demo_user;
	@ApiModelProperty(value="권한변경 에러메시지",allowEmptyValue=false,hidden=false)
	private String error_message;
	@ApiModelProperty(value="겸직구분",allowEmptyValue=false,hidden=false)
	private String concurrent_idx;
	@ApiModelProperty(value="겸직여부",allowEmptyValue=false,hidden=false)
	private String concurrent_gubun;
	@ApiModelProperty(value="2factor인증여부",allowEmptyValue=false,hidden=false)
	private String login_autr_type;
	@ApiModelProperty(value="최종로그인시간",allowEmptyValue=false,hidden=false)
	private String login_time;
	@ApiModelProperty(value="회사로고",allowEmptyValue=false,hidden=false)
	private String logo_url;
	@ApiModelProperty(value="Copyright",allowEmptyValue=false,hidden=false)
	private String copy_right;
	@ApiModelProperty(value="세션시간",allowEmptyValue=false,hidden=false)
	private int session_time;
	@ApiModelProperty(value="회원사 api 로그인 ID",allowEmptyValue=false,hidden=false)
	private String mb_api_id;
	@ApiModelProperty(value="회원사 api 로그인 비밀번호",allowEmptyValue=false,hidden=false)
	private String mb_api_pwd;
	
	public Collection<? extends GrantedAuthority> getAuthorities() { return this.authorities; } 
	
	public void setAuthorities(List<Role> authorities) { this.authorities = authorities; } 
	
	public boolean isAccountNonExpired() { return this.accountNonExpired; } 
	
	public void setAccountNonExpired(boolean accountNonExpired) { this.accountNonExpired = accountNonExpired; } 
	
	public boolean isAccountNonLocked() { return this.accountNonLocked; } 
	
	public void setAccountNonLocked(boolean accountNonLocked) { this.accountNonLocked = accountNonLocked; }
	
	public boolean isCredentialsNonExpired() { return this.credentialsNonExpired; } 
	
	public void setCredentialsNonExpired(boolean credentialsNonExpired) { this.credentialsNonExpired = credentialsNonExpired; } 
	
	public boolean isEnabled() { return this.enabled; } 
	
	public void setEnabled(boolean enabled) { this.enabled = enabled; }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProject_type() {
		return project_type;
	}

	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}

	public String getMb_id() {
		return mb_id;
	}

	public void setMb_id(String mb_id) {
		this.mb_id = mb_id;
	}

	public String getMb_nm() {
		return mb_nm;
	}

	public void setMb_nm(String mb_nm) {
		this.mb_nm = mb_nm;
	}

	public String getMb_login_id() {
		return mb_login_id;
	}

	public void setMb_login_id(String mb_login_id) {
		this.mb_login_id = mb_login_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getEmp_cd() {
		return emp_cd;
	}

	public void setEmp_cd(String emp_cd) {
		this.emp_cd = emp_cd;
	}

	public String getEmp_nm() {
		return emp_nm;
	}

	public void setEmp_nm(String emp_nm) {
		this.emp_nm = emp_nm;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getJikgub() {
		return jikgub;
	}

	public void setJikgub(String jikgub) {
		this.jikgub = jikgub;
	}

	public String getJikchk() {
		return jikchk;
	}

	public void setJikchk(String jikchk) {
		this.jikchk = jikchk;
	}

	public String getScd() {
		return scd;
	}

	public void setScd(String scd) {
		this.scd = scd;
	}

	public String getSnm() {
		return snm;
	}

	public void setSnm(String snm) {
		this.snm = snm;
	}

	public String getHpno() {
		return hpno;
	}

	public void setHpno(String hpno) {
		this.hpno = hpno;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getExtno() {
		return extno;
	}

	public void setExtno(String extno) {
		this.extno = extno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmpsta() {
		return empsta;
	}

	public void setEmpsta(String empsta) {
		this.empsta = empsta;
	}

	public String getAcct_lock_type() {
		return acct_lock_type;
	}

	public void setAcct_lock_type(String acct_lock_type) {
		this.acct_lock_type = acct_lock_type;
	}

	public String getPkg_name() {
		return pkg_name;
	}

	public void setPkg_name(String pkg_name) {
		this.pkg_name = pkg_name;
	}

	public String getDemo_user() {
		return demo_user;
	}

	public void setDemo_user(String demo_user) {
		this.demo_user = demo_user;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public String getConcurrent_idx() {
		return concurrent_idx;
	}

	public void setConcurrent_idx(String concurrent_idx) {
		this.concurrent_idx = concurrent_idx;
	}

	public String getConcurrent_gubun() {
		return concurrent_gubun;
	}

	public void setConcurrent_gubun(String concurrent_gubun) {
		this.concurrent_gubun = concurrent_gubun;
	}

	public String getLogin_autr_type() {
		return login_autr_type;
	}

	public void setLogin_autr_type(String login_autr_type) {
		this.login_autr_type = login_autr_type;
	}

	public String getLogin_time() {
		return login_time;
	}

	public void setLogin_time(String login_time) {
		this.login_time = login_time;
	}

	public String getLogo_url() {
		return logo_url;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}

	public String getCopy_right() {
		return copy_right;
	}

	public void setCopy_right(String copy_right) {
		this.copy_right = copy_right;
	}

	public int getSession_time() {
		return session_time;
	}

	public void setSession_time(int session_time) {
		this.session_time = session_time;
	}

	public String getMb_api_id() {
		return mb_api_id;
	}

	public void setMb_api_id(String mb_api_id) {
		this.mb_api_id = mb_api_id;
	}

	public String getMb_api_pwd() {
		return mb_api_pwd;
	}

	public void setMb_api_pwd(String mb_api_pwd) {
		this.mb_api_pwd = mb_api_pwd;
	}


}
