package javafx.model;

import api.Config;

public class UserInfo {

	private String username, password, token, tokenDate;
	
	public UserInfo(String username, String password, String token, String tokenDate) {
		this.username = username;
		this.password = password;
		this.token = token;
		this.tokenDate = tokenDate;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getToken() {
		return token;
	}

	public String getTokenDate() {
		return tokenDate;
	}
	
	@Override
	public String toString() {
		return "USERNAME: " + username + "\n\rPASSWORD: " + password + "\n\rTOKEN: " + token + "\n\rTOKENDATE: " + tokenDate;
	}
}
