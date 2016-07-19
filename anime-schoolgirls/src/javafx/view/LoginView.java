package javafx.view;

import javafx.Resources;
import javafx.controller.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class LoginView extends BorderPane {
	
	@FXML Button loginButton;
	@FXML TextField username;
	@FXML PasswordField password;
	
	private LoginController controller;
	
	public LoginView() {
		Resources.loadFXML(this);
	}
	
	public void loginButtonClicked() {
		controller.loginButtonClicked(username.getText(), password.getText());
	}
}
