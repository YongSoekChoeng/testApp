package kr.co.gnx.system.member;

import java.io.Serializable;

import kr.co.gnx.base.BaseVO;

public class MemberVO extends BaseVO implements Serializable {

	private static final long serialVersionUID = 958463618823803230L;
	
	// 회원사 정보
    private String mb_login_id    ; /* 회원사로그인ID     VARCHAR(20)   */
    private String mb_type        ; /* 회사타입           VARCHAR(4)    */
    private String cont_frymd     ; /* 계약시작일         VARCHAR(10)   */
    private String cont_toymd     ; /* 계약종료일         VARCHAR(10)   */
    private String corp_reg_num   ; /* 법인등록번호       VARCHAR(12)   */
    private String tax_reg_num	  ; /* 사업자등록번호  VARCHAR(20) */
    private String corp_email	  ; /* 회사이메일  VARCHAR(20) */
    private String represent_nm   ; /* 대표명             VARCHAR(30)   */
    private String telno          ; /* 전화번호           VARCHAR(14)   */
    private String faxno          ; /* 팩스번호           VARCHAR(14)   */
    private String domain_url     ; /* 도메인URL          VARCHAR(100)  */
    private String domain_mng     ; /* 도메인관리         VARCHAR(100)  */
    private String domain_id      ; /* 도메인ID           VARCHAR(100)  */
    private String domain_pw      ; /* 도메인비번         VARCHAR(100)  */
    private String ci_url         ; /* CI URL             VARCHAR(200)  */
    private String logo_url       ; /* 로고 URL           VARCHAR(200)  */
    private String manager_nm     ; /* 관리자명           VARCHAR(30)   */
    private String mtelno         ; /* 관리자연락처       VARCHAR(14)   */
    private String pkg_name       ; /* PKG명              VARCHAR(20)   */
    private String copy_right     ; /* COPYRIGHT          VARCHAR(2000) */
    private String certlogo_url   ; /* 증명서사용로고 URL VARCHAR(200)  */
    private String seal_url       ; /* 직인 URL           VARCHAR(200)  */
    private String certificate_mb_nm;	/* 증명서용 회사명 */
    private String session_time;	/* 세션 유지 시간(분) */

    private String ci_no;
    private String ci_file_nm;
    private String ci_file_size;
    private String logo_no;
    private String logo_file_nm;
    private String logo_file_size;
    private String certlogo_no;
    private String certlogo_file_nm;
    private String certlogo_file_size;
    private String seal_no;
    private String seal_file_nm;
    private String seal_file_size;
    private String copy_mb_id;
    private String mb_api_id;
    private String mb_api_pwd;
    
    
    public String getMb_login_id() {
		return mb_login_id;
	}

	public void setMb_login_id(String mb_login_id) {
		this.mb_login_id = mb_login_id;
	}

	public String getMb_type() {
		return mb_type;
	}

	public void setMb_type(String mb_type) {
		this.mb_type = mb_type;
	}

	public String getCont_frymd() {
		return cont_frymd;
	}

	public void setCont_frymd(String cont_frymd) {
		this.cont_frymd = cont_frymd;
	}

	public String getCont_toymd() {
		return cont_toymd;
	}

	public void setCont_toymd(String cont_toymd) {
		this.cont_toymd = cont_toymd;
	}

	public String getCorp_reg_num() {
		return corp_reg_num;
	}

	public void setCorp_reg_num(String corp_reg_num) {
		this.corp_reg_num = corp_reg_num;
	}

	public String getRepresent_nm() {
		return represent_nm;
	}

	public void setRepresent_nm(String represent_nm) {
		this.represent_nm = represent_nm;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getFaxno() {
		return faxno;
	}

	public void setFaxno(String faxno) {
		this.faxno = faxno;
	}

	public String getDomain_url() {
		return domain_url;
	}

	public void setDomain_url(String domain_url) {
		this.domain_url = domain_url;
	}

	public String getDomain_mng() {
		return domain_mng;
	}

	public void setDomain_mng(String domain_mng) {
		this.domain_mng = domain_mng;
	}

	public String getDomain_id() {
		return domain_id;
	}

	public void setDomain_id(String domain_id) {
		this.domain_id = domain_id;
	}

	public String getDomain_pw() {
		return domain_pw;
	}

	public void setDomain_pw(String domain_pw) {
		this.domain_pw = domain_pw;
	}

	public String getCi_url() {
		return ci_url;
	}

	public void setCi_url(String ci_url) {
		this.ci_url = ci_url;
	}

	public String getLogo_url() {
		return logo_url;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}

	public String getManager_nm() {
		return manager_nm;
	}

	public void setManager_nm(String manager_nm) {
		this.manager_nm = manager_nm;
	}

	public String getMtelno() {
		return mtelno;
	}

	public void setMtelno(String mtelno) {
		this.mtelno = mtelno;
	}

	public String getPkg_name() {
		return pkg_name;
	}

	public void setPkg_name(String pkg_name) {
		this.pkg_name = pkg_name;
	}

	public String getCopy_right() {
		return copy_right;
	}

	public void setCopy_right(String copy_right) {
		this.copy_right = copy_right;
	}

	public String getCertlogo_url() {
		return certlogo_url;
	}

	public void setCertlogo_url(String certlogo_url) {
		this.certlogo_url = certlogo_url;
	}

	public String getSeal_url() {
		return seal_url;
	}

	public void setSeal_url(String seal_url) {
		this.seal_url = seal_url;
	}
	
	public String getCertificate_mb_nm() {
		return certificate_mb_nm;
	}

	public void setCertificate_mb_nm(String certificate_mb_nm) {
		this.certificate_mb_nm = certificate_mb_nm;
	}
	
	public String getSession_time() {
		return session_time;
	}

	public void setSession_time(String session_time) {
		this.session_time = session_time;
	}

	public String getCi_no() {
		return ci_no;
	}

	public void setCi_no(String ci_no) {
		this.ci_no = ci_no;
	}

	public String getCi_file_nm() {
		return ci_file_nm;
	}

	public void setCi_file_nm(String ci_file_nm) {
		this.ci_file_nm = ci_file_nm;
	}

	public String getCi_file_size() {
		return ci_file_size;
	}

	public void setCi_file_size(String ci_file_size) {
		this.ci_file_size = ci_file_size;
	}

	public String getLogo_no() {
		return logo_no;
	}

	public void setLogo_no(String logo_no) {
		this.logo_no = logo_no;
	}

	public String getLogo_file_nm() {
		return logo_file_nm;
	}

	public void setLogo_file_nm(String logo_file_nm) {
		this.logo_file_nm = logo_file_nm;
	}

	public String getLogo_file_size() {
		return logo_file_size;
	}

	public void setLogo_file_size(String logo_file_size) {
		this.logo_file_size = logo_file_size;
	}

	public String getCertlogo_no() {
		return certlogo_no;
	}

	public void setCertlogo_no(String certlogo_no) {
		this.certlogo_no = certlogo_no;
	}

	public String getCertlogo_file_nm() {
		return certlogo_file_nm;
	}

	public void setCertlogo_file_nm(String certlogo_file_nm) {
		this.certlogo_file_nm = certlogo_file_nm;
	}

	public String getCertlogo_file_size() {
		return certlogo_file_size;
	}

	public void setCertlogo_file_size(String certlogo_file_size) {
		this.certlogo_file_size = certlogo_file_size;
	}

	public String getSeal_no() {
		return seal_no;
	}

	public void setSeal_no(String seal_no) {
		this.seal_no = seal_no;
	}

	public String getSeal_file_nm() {
		return seal_file_nm;
	}

	public void setSeal_file_nm(String seal_file_nm) {
		this.seal_file_nm = seal_file_nm;
	}

	public String getSeal_file_size() {
		return seal_file_size;
	}

	public void setSeal_file_size(String seal_file_size) {
		this.seal_file_size = seal_file_size;
	}

	public String getCopy_mb_id() {
		return copy_mb_id;
	}

	public void setCopy_mb_id(String copy_mb_id) {
		this.copy_mb_id = copy_mb_id;
	}

	public String getTax_reg_num() {
		return tax_reg_num;
	}

	public void setTax_reg_num(String tax_reg_num) {
		this.tax_reg_num = tax_reg_num;
	}

	public String getCorp_email() {
		return corp_email;
	}

	public void setCorp_email(String corp_email) {
		this.corp_email = corp_email;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "MemberVO [mb_login_id=" + mb_login_id + ", mb_type=" + mb_type + ", cont_frymd=" + cont_frymd
				+ ", cont_toymd=" + cont_toymd + ", corp_reg_num=" + corp_reg_num + ", tax_reg_num=" + tax_reg_num
				+ ", corp_email=" + corp_email + ", represent_nm=" + represent_nm + ", telno=" + telno + ", faxno="
				+ faxno + ", domain_url=" + domain_url + ", domain_mng=" + domain_mng + ", domain_id=" + domain_id
				+ ", domain_pw=" + domain_pw + ", ci_url=" + ci_url + ", logo_url=" + logo_url + ", manager_nm="
				+ manager_nm + ", mtelno=" + mtelno + ", pkg_name=" + pkg_name + ", copy_right=" + copy_right
				+ ", certlogo_url=" + certlogo_url + ", seal_url=" + seal_url + ", certificate_mb_nm="
				+ certificate_mb_nm + ", session_time=" + session_time + ", ci_no=" + ci_no + ", ci_file_nm="
				+ ci_file_nm + ", ci_file_size=" + ci_file_size + ", logo_no=" + logo_no + ", logo_file_nm="
				+ logo_file_nm + ", logo_file_size=" + logo_file_size + ", certlogo_no=" + certlogo_no
				+ ", certlogo_file_nm=" + certlogo_file_nm + ", certlogo_file_size=" + certlogo_file_size + ", seal_no="
				+ seal_no + ", seal_file_nm=" + seal_file_nm + ", seal_file_size=" + seal_file_size + ", copy_mb_id="
				+ copy_mb_id + ", mb_api_id=" + mb_api_id + ", mb_api_pwd=" + mb_api_pwd + "]";
	}
	
}