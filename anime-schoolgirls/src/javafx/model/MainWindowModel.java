package javafx.model;

import java.util.List;

import api.JsonDecoder.extractEpisodes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainWindowModel {
	
	private ObservableList<Series> topseriesList = FXCollections.observableArrayList();
	private ObservableList<Series> seriesList = FXCollections.observableArrayList();
	private ObservableList<Episodes> episodesList = FXCollections.observableArrayList();
	private ObservableList<Episodes> movieList = FXCollections.observableArrayList();
	
	public ObservableList<Series> getSeriesList() {
		return seriesList;
	}
	
	public ObservableList<Episodes> getEpisodesList() {
		return episodesList;
	}
	
	public ObservableList<Episodes> getMovieList() {
		return movieList;
	}

	public ObservableList<Series> getTopSeriesList() {
		return topseriesList;
	}
}
