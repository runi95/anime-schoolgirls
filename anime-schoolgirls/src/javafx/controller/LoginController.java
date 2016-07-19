package javafx.controller;

import api.clientHttp;
import javafx.SceneController;
import javafx.model.LoginModel;
import javafx.model.MainWindowModel;
import javafx.view.LoginView;
import javafx.view.MainWindowView;

public class LoginController {
	private LoginView view;
	private LoginModel model;
	
	public LoginController(LoginModel model, LoginView view) {
		this.model = model;
		this.view = view;
		view.setController(this);
	}
	
	public void loginButtonClicked(String username, String password) {
		boolean loginSuccess = false;
		try {
			loginSuccess = clientHttp.Login(username, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(loginSuccess) {
			MainWindowModel model = new MainWindowModel();
			SceneController.setScene(new MainWindowView(model.getList()));
		}else{
			view.showError("Error: Wrong username or password!");
		}
//		System.out.println("Username: " + username + "\nPassword: " + password);
	}

	public LoginView getView() {
		return view;
	}
}