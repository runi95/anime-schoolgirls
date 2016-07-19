package javafx.model;

import api.Config;

public class UserInfo {

	private String username, password, token;
	
	public UserInfo(String username, String password, String token) {
		this.username = username;
		this.password = password;
		this.token = token;
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

	@Override
	public String toString() {
		return "USERNAME: " + username + "\n\rPASSWORD: " + password + "\n\rTOKEN: " + token;
	}
}
