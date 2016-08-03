package javafx.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import api.JsonDecoder;
import api.JsonDecoder.extractEpisodes;
import api.JsonDecoder.extractSeries;
import api.grabFTW;
import backend.IntegerParser;
import backend.WaitingThread;
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

	private Thread thread = null;
	private WaitingThread waitingThread = null;

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

	//TODO: Fix multi-threading as this has a slight chance to crash!
	public void addEpisodesFromFTW(String id) {
		if (thread != null) {
			thread.interrupt();

			if (waitingThread != null)
				waitingThread.setSeriesId(id);
			else {
				waitingThread = new WaitingThread(id) {
					@Override
					public void run() {
						while (thread != null) {
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						createNewEpisodesThread(getSeriesId());
						waitingThread = null;
					}
				};

				waitingThread.start();
			}

		} else
			createNewEpisodesThread(id);
	}

	private void createNewEpisodesThread(String id) {
		removeAllEpisodes();
		removeAllMovies();
		grabFTW ftwdaemon = new grabFTW();

		thread = new Thread() {
			@Override
			public void run() {
				try {
					List<extractEpisodes> extractEpisodes;
					extractEpisodes = JsonDecoder.getEpisodes(ftwdaemon.getEpisodesByid("display-episodes", id));

					addAllEpisodes(convertFromExtractEpisodesToEpisodes(extractEpisodes));
					addAllMovies(convertFromExtractEpisodesToMovies(extractEpisodes));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					thread = null;
				}
			}
		};

		thread.start();
	}

	public void playEpisode() {
		Episodes episode = null;

		switch (view.getEpisodeTabs().getSelectionModel().getSelectedIndex()) {
		case 0:
			episode = (Episodes) view.getEpisodesTable().getSelectionModel().getSelectedItem();
			break;
		case 1:
			episode = (Episodes) view.getMovieTable().getSelectionModel().getSelectedItem();
			break;
		}

		if (episode != null)
			runEpisode(episode.getEpLink());
	}

	private void runEpisode(String link) {
		String[] arguments = new String[] { "mpv", "--user-agent", "\"HenningCast/mpv\"", link };
		try {
			Process proc = new ProcessBuilder(arguments).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addAllMovies(List<Episodes> movieList) {
		model.getMovieList().addAll(movieList);
	}

	public void addMovies(int movieNumber, String name, String movieLink) {
		model.getMovieList().add(new Episodes(movieNumber, name, movieLink));
	}

	public void removeAllMovies() {
		model.getMovieList().clear();
	}

	public void removeMovie(int index) {
		model.getMovieList().remove(index);
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

	public void addSeries(String name, String description, String rating, String image, String id, String watchlist) {
		model.getSeriesList().add(new Series(name, description, rating, image, id, watchlist));
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

	private List<Episodes> convertFromExtractEpisodesToMovies(List<extractEpisodes> list) {
		List<Episodes> retList = new ArrayList<>();

		for (extractEpisodes episodes : list) {
			int epNumber = IntegerParser.parseInt(episodes.getepnumber());
			String name = episodes.getepname(), epLink = episodes.getvideo();
			if (IntegerParser.parseInt(episodes.Movie()) == 1)
				retList.add(new Episodes(epNumber, name, epLink));
		}

		return retList;
	}

	private List<Episodes> convertFromExtractEpisodesToEpisodes(List<extractEpisodes> list) {
		List<Episodes> retList = new ArrayList<>();

		for (extractEpisodes episodes : list) {
			int epNumber = IntegerParser.parseInt(episodes.getepnumber());
			String name = episodes.getepname(), epLink = episodes.getvideo();
			if (IntegerParser.parseInt(episodes.Movie()) != 1)
				retList.add(new Episodes(epNumber, name, epLink));
		}

		return retList;
	}

	private List<Series> convertFromExtractSeriesToSeries(List<extractSeries> list) {
		List<Series> retList = new ArrayList<>();

		for (extractSeries series : list) {
			String name = series.getfullSeriesName(), description = series.getdescription(), image = series.getimage(),
					rating = series.getratingString(), id = series.getid(), watchlist = series.getwatchlist();
			retList.add(new Series(name, description, rating, image, id, watchlist));
		}

		return retList;
	}
}
