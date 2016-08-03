package javafx.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainWindowModel {
	
	private ObservableList<Series> seriesList = FXCollections.observableArrayList();
	private ObservableList<Episodes> episodesList = FXCollections.observableArrayList();
	
	public ObservableList<Series> getSeriesList() {
		return seriesList;
	}
	
	public ObservableList<Episodes> getEpisodesList() {
		return episodesList;
	}
}
