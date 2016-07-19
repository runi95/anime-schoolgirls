package javafx;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class SceneController {

		public static void setScene(Node node) {
			MyApplication.getRoot().getChildren().set(0, node);
		}
		
		public static void setModal(Node node) {
			StackPane stage = MyApplication.getRoot();
			if(stage.getChildren().size() == 1) {
				stage.getChildren().add(node);
				stage.getChildren().get(0).disableProperty().setValue(true);
			}else
				stage.getChildren().set(1, node);
		}
		
		public static void removeModal(Node node) {
			StackPane stage = MyApplication.getRoot();
			
			stage.getChildren().removeAll(node);
			stage.getChildren().get(0).disableProperty().setValue(true);
		}
}
