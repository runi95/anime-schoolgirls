package javafx.view;

import javafx.Resources;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.controller.LoginController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class LoginView extends BorderPane {

	@FXML
	Button loginButton;
	@FXML
	TextField username;
	@FXML
	PasswordField password;
	@FXML
	Label errorLabel;

	private FadeTransition fadeTransition;

	private LoginController controller;

	public LoginView() {
		Resources.loadFXML(this);
	}

	public void showError(String errorMessage) {
		errorLabel.setText(errorMessage);
		errorFading();
	}

	public void loginButtonClicked() {
		controller.loginButtonClicked(username.getText(), password.getText());
	}

	public void setController(LoginController controller) {
		this.controller = controller;
	}

	private void errorFading() {
		if (fadeTransition == null) {
			fadeTransition = new FadeTransition(Duration.millis(4000), errorLabel);
			fadeTransition.setFromValue(1.0);
			fadeTransition.setToValue(0.1);
			fadeTransition.stop();
			fadeTransition.play();
			startTimer();
		}
	}

	private void startTimer() {
		final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				errorLabel.setText("");
				fadeTransition = null;
			}
		}));
		timeline.playFromStart();
	}

}
