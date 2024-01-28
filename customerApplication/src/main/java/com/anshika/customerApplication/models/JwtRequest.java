package com.anshika.customerApplication.models;

public class JwtRequest {
	String loginId;
	String password;
	
	public JwtRequest() {
		
		// TODO Auto-generated constructor stub
	}

	public JwtRequest(String loginId, String password) {
		super();
		this.loginId = loginId;
		this.password = password;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String username) {
		this.loginId = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "JwtRequest [loginId=" + loginId + ", password=" + password + "]";
	}
}
