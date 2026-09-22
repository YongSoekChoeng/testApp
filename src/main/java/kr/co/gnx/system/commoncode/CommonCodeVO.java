package kr.co.gnx.system.commoncode;

import java.util.Arrays;

import kr.co.gnx.base.BaseVO;

public class CommonCodeVO extends BaseVO{
	
    private String grp_cmm_cd;
    private String cd_vl_ky;
    private String bz_nm;
    private String grp_cmm_cd_nm;
    private String grp_cmm_cd_desc;
    private String[] cd_vl_arr;
    private String[] grp_cmm_cd_arr;
    private String system_gubun;
    private String con;

    // 상품구분2
	private String prod_kind1;	/* 상품구분1 */
	private String prod_kind2;	/* 상품구분2 */

	private String prod_kind2_gubun;
	private String prod_kind2_nm;
	private String performance_group_gubun;
    
	public String getGrp_cmm_cd() {
		return grp_cmm_cd;
	}
	public void setGrp_cmm_cd(String grp_cmm_cd) {
		this.grp_cmm_cd = grp_cmm_cd;
	}
	public String getCd_vl_ky() {
		return cd_vl_ky;
	}
	public void setCd_vl_ky(String cd_vl_ky) {
		this.cd_vl_ky = cd_vl_ky;
	}
	public String getBz_nm() {
		return bz_nm;
	}
	public void setBz_nm(String bz_nm) {
		this.bz_nm = bz_nm;
	}
	public String getGrp_cmm_cd_nm() {
		return grp_cmm_cd_nm;
	}
	public void setGrp_cmm_cd_nm(String grp_cmm_cd_nm) {
		this.grp_cmm_cd_nm = grp_cmm_cd_nm;
	}
	public String getGrp_cmm_cd_desc() {
		return grp_cmm_cd_desc;
	}
	public void setGrp_cmm_cd_desc(String grp_cmm_cd_desc) {
		this.grp_cmm_cd_desc = grp_cmm_cd_desc;
	}
	public String[] getCd_vl_arr() {
		return cd_vl_arr;
	}
	public void setCd_vl_arr(String[] cd_vl_arr) {
		this.cd_vl_arr = cd_vl_arr;
	}
	public String[] getGrp_cmm_cd_arr() {
		return grp_cmm_cd_arr;
	}
	public void setGrp_cmm_cd_arr(String[] grp_cmm_cd_arr) {
		this.grp_cmm_cd_arr = grp_cmm_cd_arr;
	}
	public String getSystem_gubun() {
		return system_gubun;
	}
	public void setSystem_gubun(String system_gubun) {
		this.system_gubun = system_gubun;
	}
	public String getCon() {
		return con;
	}
	public void setCon(String con) {
		this.con = con;
	}

	public String getProd_kind1() {
		return prod_kind1;
	}

	public void setProd_kind1(String prod_kind1) {
		this.prod_kind1 = prod_kind1;
	}

	public String getProd_kind2() {
		return prod_kind2;
	}

	public void setProd_kind2(String prod_kind2) {
		this.prod_kind2 = prod_kind2;
	}

	public String getProd_kind2_gubun() {
		return prod_kind2_gubun;
	}

	public void setProd_kind2_gubun(String prod_kind2_gubun) {
		this.prod_kind2_gubun = prod_kind2_gubun;
	}

	public String getProd_kind2_nm() {
		return prod_kind2_nm;
	}

	public void setProd_kind2_nm(String prod_kind2_nm) {
		this.prod_kind2_nm = prod_kind2_nm;
	}

	public String getPerformance_group_gubun() {
		return performance_group_gubun;
	}

	public void setPerformance_group_gubun(String performance_group_gubun) {
		this.performance_group_gubun = performance_group_gubun;
	}

	@Override
	public String toString() {
		return "CommonCodeVO [grp_cmm_cd=" + grp_cmm_cd + ", cd_vl_ky=" + cd_vl_ky + ", bz_nm=" + bz_nm
				+ ", grp_cmm_cd_nm=" + grp_cmm_cd_nm + ", grp_cmm_cd_desc=" + grp_cmm_cd_desc + ", cd_vl_arr="
				+ Arrays.toString(cd_vl_arr) + ", grp_cmm_cd_arr=" + Arrays.toString(grp_cmm_cd_arr) + ", system_gubun="
				+ system_gubun + ", con=" + con + "]";
	}
}
