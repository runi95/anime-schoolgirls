package javafx.model;

public class LoginModel {

	private String username, password, token, tokenDate;
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public void setTokenDate(String tokenDate) {
		this.tokenDate = tokenDate;
	}
	
	public String getUsername() { return username; }
	public String getPassword() { return password; }
	public String getToken() { return token; }
	public String getTokenDate() { return tokenDate; }
}
