package kr.co.gnx.logs;

import kr.co.gnx.base.BaseVO;

public class ErrorLogVO extends BaseVO{
	private String action_type;
	private String error_url;
	private String error_query_string;
	private String error_class;
	private String error_status;
	private String error_msg;
	private String error_string;
	private String error_trace;
	private String error_ip;
	
	
	public String getAction_type() {
		return action_type;
	}

	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}

	public String getError_url() {
		return error_url;
	}
	
	public void setError_url(String error_url) {
		this.error_url = error_url;
	}
	
	public String getError_class() {
		return error_class;
	}
	
	public void setError_class(String error_class) {
		this.error_class = error_class;
	}
	
	public String getError_status() {
		return error_status;
	}
	
	public void setError_status(String error_status) {
		this.error_status = error_status;
	}
	
	public String getError_msg() {
		return error_msg;
	}
	
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	
	public String getError_string() {
		return error_string;
	}
	
	public void setError_string(String error_string) {
		this.error_string = error_string;
	}
	
	public String getError_trace() {
		return error_trace;
	}
	
	public void setError_trace(String error_trace) {
		this.error_trace = error_trace;
	}
	
	public String getError_ip() {
		return error_ip;
	}
	
	public void setError_ip(String error_ip) {
		this.error_ip = error_ip;
	}

	public String getError_query_string() {
		return error_query_string;
	}

	public void setError_query_string(String error_query_string) {
		this.error_query_string = error_query_string;
	}

	@Override
	public String toString() {
		return "ErrorLogVO [action_type=" + action_type + ", error_url=" + error_url + ", error_query_string="
				+ error_query_string + ", error_class=" + error_class + ", error_status=" + error_status
				+ ", error_msg=" + error_msg + ", error_string=" + error_string + ", error_trace=" + error_trace
				+ ", error_ip=" + error_ip + "]";
	}
}
