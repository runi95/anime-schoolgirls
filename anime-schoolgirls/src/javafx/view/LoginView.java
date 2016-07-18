package javafx.view;

import javafx.Resources;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class LoginView extends BorderPane {
	
	@FXML Button loginButton;
	@FXML TextField username;
	@FXML PasswordField password;
	
	public LoginView() {
		Resources.loadFXML(this);
	}
	
	public void loginButtonClicked() {
		System.out.println("Ponies in socks!");
	}
}
