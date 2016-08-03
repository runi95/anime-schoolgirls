package javafx.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import api.JsonDecoder;
import api.JsonDecoder.extractEpisodes;
import api.JsonDecoder.extractSeries;
import api.grabFTW;
import javafx.model.Episodes;
import javafx.model.MainWindowModel;
import javafx.model.Series;
import javafx.model.Video;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.view.MainWindowView;

public class MainWindowController {

	private MainWindowModel model;;
	private MainWindowView view;

	public MainWindowController(MainWindowModel model, MainWindowView view) {
		this.model = model;
		this.view = view;
		setListeners(view.getSeriesTable(), view.getEpisodesTable());
		addSeriesFromAnimeFTW();
	}

	@SuppressWarnings("unchecked")
	private void setListeners(TableView seriesTable, TableView episodesTable) {
		seriesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				Series series = (Series) seriesTable.getSelectionModel().getSelectedItem();
				if (series != null) {
					view.setSeriesDescription(series.getDescription());
					view.setSeriesImage(series.getImage());
					addEpisodesFromFTW(series.getId());
				}
			}
		});
	}

	public void addEpisodesFromFTW(String id) {
		removeAllEpisodes();

		grabFTW ftwdaemon = new grabFTW();

		new Thread() {
			@Override
			public void run() {
				try {
					List<extractEpisodes> extractSeries;
					extractSeries = JsonDecoder.getEpisodes(ftwdaemon.getEpisodesByid("display-episodes", id));

					addAllEpisodes(convertFromExtractEpisodesToEpisodes(extractSeries));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				}
			}
		}.start();
	}

	public void playEpisode() {
		Episodes episode = (Episodes)view.getEpisodesTable().getSelectionModel().getSelectedItem();
		
		runEpisode(episode.getEpLink());
	}
	
	private void runEpisode(String link) {
		System.err.println("GOT THIS FAR");
		String[] arguments = new String[] {"mpv", "--user-agent", "\"HenningCast/mpv\"", link};
		try {
			Process proc = new ProcessBuilder(arguments).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addAllEpisodes(List<Episodes> episodeList) {
		model.getEpisodesList().addAll(episodeList);
	}

	public void addEpisodes(int epNumber, String name, String epLink) {
		model.getEpisodesList().add(new Episodes(epNumber, name, epLink));
	}

	public void removeAllEpisodes() {
		model.getEpisodesList().clear();
	}

	public void removeEpisode(int index) {
		model.getEpisodesList().remove(index);
	}

	public void addAllSeries(List<Series> seriesList) {
		model.getSeriesList().addAll(seriesList);
	}

	public void addSeries(String name, String description, String rating, String image, String id) {
		model.getSeriesList().add(new Series(name, description, rating, image, id));
	}

	public void removeAllVideos() {
		model.getSeriesList().clear();
	}

	public void removeVideo(int index) {
		model.getSeriesList().remove(index);
	}

	public Image getImageFromURL(String imageURL) {
		Image img = null;
		try {
			img = new Image(imageURL);
		} catch (NullPointerException | IllegalArgumentException e) {
			/* If fetching image fails, use stock image */

			img = new Image("javafx/view/image/noimage.jpg");
		}

		return img;
	}

	public Node getView() {
		return view;
	}

	/*
	 * private Image getRatingImage(String rating) { switch(rating) {
	 * 
	 * 
	 * default: return new Image("javafx/view/image/noimage.jpg"); } }
	 */

	private void addSeriesFromAnimeFTW() {
		grabFTW ftwdaemon = new grabFTW();

		new Thread() {
			@Override
			public void run() {
				try {
					List<extractSeries> extractSeries;

					extractSeries = JsonDecoder.getSeries(ftwdaemon.getListing("display-series", 1600));
					addAllSeries(convertFromExtractSeriesToSeries(extractSeries));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				}
			}
		}.start();
	}

	private List<Episodes> convertFromExtractEpisodesToEpisodes(List<extractEpisodes> list) {
		List<Episodes> retList = new ArrayList<>();

		for (extractEpisodes episodes : list) {
			int epNumber = parseInt(episodes.getepnumber());
			String name = episodes.getepname(), epLink = episodes.getvideo();
			if (parseInt(episodes.Movie()) != 1)
				retList.add(new Episodes(epNumber, name, epLink));
		}

		return retList;
	}

	private int parseInt(String integer) {
		int parsedInt = -1;

		try {
			parsedInt = Integer.parseInt(integer);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return parsedInt;
	}

	private List<Series> convertFromExtractSeriesToSeries(List<extractSeries> list) {
		List<Series> retList = new ArrayList<>();

		for (extractSeries series : list) {
			String name = series.getfullSeriesName(), description = series.getdescription(), image = series.getimage(),
					rating = series.getratingString(), id = series.getid();
			retList.add(new Series(name, description, rating, image, id));
		}

		return retList;
	}
}
