package kr.co.gnx.security.model;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import io.swagger.annotations.ApiModelProperty;

public class Role implements GrantedAuthority{

	private static final long serialVersionUID = 7077755420106314539L;
	
	@ApiModelProperty(value="권한명",allowEmptyValue=false,hidden=false)
	private String name;
	private List<Privilege> privileges;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Privilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

	//@Override
	public String getAuthority() {
		return this.name;
	}
	@Override
	public String toString() {
		return "Role [name=" + name + ", privileges=" + privileges + "]";
	}
}
