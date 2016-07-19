package javafx.controller;

import javafx.model.LoginModel;
import javafx.view.LoginView;

public class LoginController {
	private LoginView view;
	private LoginModel model;
	
	public LoginController(LoginModel model, LoginView view) {
		this.model = model;
		this.view = view;
	}
	
	public void loginButtonClicked(String username, String password) {
		System.out.println("Username: " + username + "\nPassword: " + password);
	}

	public LoginView getView() {
		return view;
	}
}