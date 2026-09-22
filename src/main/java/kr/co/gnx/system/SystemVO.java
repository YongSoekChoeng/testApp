package kr.co.gnx.system;

import java.io.Serializable;

import kr.co.gnx.base.BaseVO;
/**
 * @desc SystemVO
 * @author yoonsik
 */
public class SystemVO extends BaseVO implements Serializable {

	private static final long serialVersionUID = 958463618823803230L;
	private String ACTION_URL;
	private String CATEGORY;
	
	// 회원사 정보
	private String MB_NM          ; /* 회사명            VARCHAR2(100)  */
	private String ZIPCD          ; /* 우편번호          VARCHAR2(7)    */
	private String ADDR1          ; /* 주소              VARCHAR2(100)  */
	private String ADDR2          ; /* 상세주소          VARCHAR2(100)  */
	private String MB_TYPE        ; /* 회사타입          VARCHAR2(4)    */
	private String CONT_FRYMD     ; /* 계약시작일        VARCHAR2(10)   */
	private String CONT_TOYMD     ; /* 계약종료일        VARCHAR2(10)   */
	private String CORP_REG_NUM   ; /* 법인등록번호      VARCHAR2(12)   */
	private String REPRESENT_NM   ; /* 대표명            VARCHAR2(30)   */
	private String TELNO          ; /* 전화번호          VARCHAR2(14)   */
	private String FAXNO          ; /* 팩스번호          VARCHAR2(14)   */
	private String DOMAIN_URL     ; /* 도메인URL         VARCHAR2(100)  */
	private String DOMAIN_MNG     ; /* 도메인관리        VARCHAR2(100)  */
	private String DOMAIN_ID      ; /* 도메인ID          VARCHAR2(100)  */
	private String DOMAIN_PW      ; /* 도메인비번        VARCHAR2(100)  */
	private String MEMO           ; /* 메모              VARCHAR2(100)  */
	private String BIGO           ; /* 비고              VARCHAR2(100)  */
	private String CI_URL         ; /* CI URL            VARCHAR2(200)  */
	private String MB_LOGIN_ID    ; /* 회원사로그인ID    VARCHAR2(20)   */
	private String LOGO_URL       ; /* 로고 URL          VARCHAR2(200)  */
	private String MANAGER_NM     ; /* 관리자이름        VARCHAR2(30)   */
	private String MTELNO         ; /* 관리자연락처      VARCHAR2(14)   */
	private String PKG_NAME       ; /* PKG명             VARCHAR2(20)   */
	private String COPY_RIGHT     ; /* COPYRIGHT         VARCHAR2(2000) */
	private String CERTLOGO_URL   ; /* 증명서사용로고URL VARCHAR2(200)  */
	private String SEAL_URL       ; /* 직인URL           VARCHAR2(200)  */
	
	public String getACTION_URL() {
		return ACTION_URL;
	}
	public void setACTION_URL(String aCTION_URL) {
		ACTION_URL = aCTION_URL;
	}
	public String getCATEGORY() {
		return CATEGORY;
	}
	public void setCATEGORY(String cATEGORY) {
		CATEGORY = cATEGORY;
	}
	public String getMB_NM() {
		return MB_NM;
	}
	public void setMB_NM(String mB_NM) {
		MB_NM = mB_NM;
	}
	public String getZIPCD() {
		return ZIPCD;
	}
	public void setZIPCD(String zIPCD) {
		ZIPCD = zIPCD;
	}
	public String getADDR1() {
		return ADDR1;
	}
	public void setADDR1(String aDDR1) {
		ADDR1 = aDDR1;
	}
	public String getADDR2() {
		return ADDR2;
	}
	public void setADDR2(String aDDR2) {
		ADDR2 = aDDR2;
	}
	public String getMB_TYPE() {
		return MB_TYPE;
	}
	public void setMB_TYPE(String mB_TYPE) {
		MB_TYPE = mB_TYPE;
	}
	public String getCONT_FRYMD() {
		return CONT_FRYMD;
	}
	public void setCONT_FRYMD(String cONT_FRYMD) {
		CONT_FRYMD = cONT_FRYMD;
	}
	public String getCONT_TOYMD() {
		return CONT_TOYMD;
	}
	public void setCONT_TOYMD(String cONT_TOYMD) {
		CONT_TOYMD = cONT_TOYMD;
	}
	public String getCORP_REG_NUM() {
		return CORP_REG_NUM;
	}
	public void setCORP_REG_NUM(String cORP_REG_NUM) {
		CORP_REG_NUM = cORP_REG_NUM;
	}
	public String getREPRESENT_NM() {
		return REPRESENT_NM;
	}
	public void setREPRESENT_NM(String rEPRESENT_NM) {
		REPRESENT_NM = rEPRESENT_NM;
	}
	public String getTELNO() {
		return TELNO;
	}
	public void setTELNO(String tELNO) {
		TELNO = tELNO;
	}
	public String getFAXNO() {
		return FAXNO;
	}
	public void setFAXNO(String fAXNO) {
		FAXNO = fAXNO;
	}
	public String getDOMAIN_URL() {
		return DOMAIN_URL;
	}
	public void setDOMAIN_URL(String dOMAIN_URL) {
		DOMAIN_URL = dOMAIN_URL;
	}
	public String getDOMAIN_MNG() {
		return DOMAIN_MNG;
	}
	public void setDOMAIN_MNG(String dOMAIN_MNG) {
		DOMAIN_MNG = dOMAIN_MNG;
	}
	public String getDOMAIN_ID() {
		return DOMAIN_ID;
	}
	public void setDOMAIN_ID(String dOMAIN_ID) {
		DOMAIN_ID = dOMAIN_ID;
	}
	public String getDOMAIN_PW() {
		return DOMAIN_PW;
	}
	public void setDOMAIN_PW(String dOMAIN_PW) {
		DOMAIN_PW = dOMAIN_PW;
	}
	public String getMEMO() {
		return MEMO;
	}
	public void setMEMO(String mEMO) {
		MEMO = mEMO;
	}
	public String getBIGO() {
		return BIGO;
	}
	public void setBIGO(String bIGO) {
		BIGO = bIGO;
	}
	public String getCI_URL() {
		return CI_URL;
	}
	public void setCI_URL(String cI_URL) {
		CI_URL = cI_URL;
	}
	public String getMB_LOGIN_ID() {
		return MB_LOGIN_ID;
	}
	public void setMB_LOGIN_ID(String mB_LOGIN_ID) {
		MB_LOGIN_ID = mB_LOGIN_ID;
	}
	public String getLOGO_URL() {
		return LOGO_URL;
	}
	public void setLOGO_URL(String lOGO_URL) {
		LOGO_URL = lOGO_URL;
	}
	public String getMANAGER_NM() {
		return MANAGER_NM;
	}
	public void setMANAGER_NM(String mANAGER_NM) {
		MANAGER_NM = mANAGER_NM;
	}
	public String getMTELNO() {
		return MTELNO;
	}
	public void setMTELNO(String mTELNO) {
		MTELNO = mTELNO;
	}
	public String getPKG_NAME() {
		return PKG_NAME;
	}
	public void setPKG_NAME(String pKG_NAME) {
		PKG_NAME = pKG_NAME;
	}
	public String getCOPY_RIGHT() {
		return COPY_RIGHT;
	}
	public void setCOPY_RIGHT(String cOPY_RIGHT) {
		COPY_RIGHT = cOPY_RIGHT;
	}
	public String getCERTLOGO_URL() {
		return CERTLOGO_URL;
	}
	public void setCERTLOGO_URL(String cERTLOGO_URL) {
		CERTLOGO_URL = cERTLOGO_URL;
	}
	public String getSEAL_URL() {
		return SEAL_URL;
	}
	public void setSEAL_URL(String sEAL_URL) {
		SEAL_URL = sEAL_URL;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "SystemVO [ACTION_URL=" + ACTION_URL + ", CATEGORY=" + CATEGORY + ", MB_NM=" + MB_NM + ", ZIPCD=" + ZIPCD
				+ ", ADDR1=" + ADDR1 + ", ADDR2=" + ADDR2 + ", MB_TYPE=" + MB_TYPE + ", CONT_FRYMD=" + CONT_FRYMD
				+ ", CONT_TOYMD=" + CONT_TOYMD + ", CORP_REG_NUM=" + CORP_REG_NUM + ", REPRESENT_NM=" + REPRESENT_NM
				+ ", TELNO=" + TELNO + ", FAXNO=" + FAXNO + ", DOMAIN_URL=" + DOMAIN_URL + ", DOMAIN_MNG=" + DOMAIN_MNG
				+ ", DOMAIN_ID=" + DOMAIN_ID + ", DOMAIN_PW=" + DOMAIN_PW + ", MEMO=" + MEMO + ", BIGO=" + BIGO
				+ ", CI_URL=" + CI_URL + ", MB_LOGIN_ID=" + MB_LOGIN_ID + ", LOGO_URL=" + LOGO_URL + ", MANAGER_NM="
				+ MANAGER_NM + ", MTELNO=" + MTELNO + ", PKG_NAME=" + PKG_NAME + ", COPY_RIGHT=" + COPY_RIGHT
				+ ", CERTLOGO_URL=" + CERTLOGO_URL + ", SEAL_URL=" + SEAL_URL + "]";
	}
	
}