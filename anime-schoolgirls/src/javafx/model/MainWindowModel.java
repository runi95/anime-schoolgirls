package javafx.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainWindowModel {
	
	private ObservableList<Series> videoList = FXCollections.observableArrayList();
	
	public ObservableList<Series> getList() {
		return videoList;
	}
}
