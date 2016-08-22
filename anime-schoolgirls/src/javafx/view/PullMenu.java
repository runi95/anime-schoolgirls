package javafx.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class PullMenu extends TitledPane {

	@FXML
	ImageView playButton;
	
	@FXML
	ComboBox<String> comboBoxDevices;
	
	@FXML
	Button startButton, stopButton;
	
	public PullMenu() {
		setExpanded(true);

		expandedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					new Timeline(new KeyFrame(Duration.seconds(0), new KeyValue(maxWidthProperty(), getWidth())),
							new KeyFrame(Duration.seconds(1), new KeyValue(maxWidthProperty(), 200.0))).play();
				} else {
					new Timeline(new KeyFrame(Duration.seconds(0), new KeyValue(maxWidthProperty(), getWidth())),
							new KeyFrame(Duration.seconds(1), new KeyValue(maxWidthProperty(), 0.0))).play();
				}
			}
		});
	}
	
	public void buttonPullMenu(ImageView pullMenuIcon) {
		setExpanded(!isExpanded());
		if (isExpanded())
			pullMenuIcon.setImage(new Image("javafx/view/image/Arrow_Right.png"));
		else
			pullMenuIcon.setImage(new Image("javafx/view/image/Arrow_Left.png"));
	}
	
	public void buttonStop() {
		
	}
	
	public void buttonStart() {
		
	}
}
