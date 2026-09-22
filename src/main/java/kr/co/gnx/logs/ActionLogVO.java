package kr.co.gnx.logs;

import kr.co.gnx.base.BaseVO;

public class ActionLogVO extends BaseVO{
	private String action_type;				/* 행위 유형 varchar(40) */
	private String action_url;				/* 접근 URL varchar(200)*/
	private String action_query_string;		/* URL에 전달된 파라미터 값 varchar(1000)*/
	private String action_ip;				/* 행위자 IP varchar(100)*/
	
	public String getAction_type() {
		return action_type;
	}
	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}
	public String getAction_url() {
		return action_url;
	}
	public void setAction_url(String action_url) {
		this.action_url = action_url;
	}
	public String getAction_query_string() {
		return action_query_string;
	}
	public void setAction_query_string(String action_query_string) {
		this.action_query_string = action_query_string;
	}
	public String getAction_ip() {
		return action_ip;
	}
	public void setAction_ip(String action_ip) {
		this.action_ip = action_ip;
	}
	
	@Override
	public String toString() {
		return "ActionLogVO [action_type=" + action_type + ", action_url=" + action_url + ", action_query_string="
				+ action_query_string + ", action_ip=" + action_ip + "]";
	}
		
}
