package kr.co.gnx.security.model;

import io.swagger.annotations.ApiModelProperty;

public abstract class ResponseDefault {

	@ApiModelProperty(value="성공여부",allowEmptyValue=false,hidden=false)
	private boolean success;
	
	@ApiModelProperty(value="메세지",allowEmptyValue=false,hidden=false)
	private String message;
	@ApiModelProperty(value="상태코드",allowEmptyValue=false,hidden=false)
	private String statuscode;
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}

	
}
