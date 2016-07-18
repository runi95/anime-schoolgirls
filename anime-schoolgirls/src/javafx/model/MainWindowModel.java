package javafx.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainWindowModel {
	
	private ObservableList<Video> videoList = FXCollections.observableArrayList();
	
	public ObservableList<Video> getList() {
		return videoList;
	}
}
