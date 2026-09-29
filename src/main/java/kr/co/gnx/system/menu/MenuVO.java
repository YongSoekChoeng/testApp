package kr.co.gnx.system.menu;

import kr.co.gnx.base.BaseVO;

public class MenuVO extends BaseVO {

	private String resource_id;			/* 메뉴ID */
	private String prnt_resource_id;	/* 상위 메뉴ID (최상위면 null) */
	private String resource_name;		/* 메뉴명 */
	private String resource_url;		/* 화면 URL (하위메뉴가 있는 메뉴는 NONE) */

	public String getResource_id() {
		return resource_id;
	}
	public void setResource_id(String resource_id) {
		this.resource_id = resource_id;
	}
	public String getPrnt_resource_id() {
		return prnt_resource_id;
	}
	public void setPrnt_resource_id(String prnt_resource_id) {
		this.prnt_resource_id = prnt_resource_id;
	}
	public String getResource_name() {
		return resource_name;
	}
	public void setResource_name(String resource_name) {
		this.resource_name = resource_name;
	}
	public String getResource_url() {
		return resource_url;
	}
	public void setResource_url(String resource_url) {
		this.resource_url = resource_url;
	}
}
