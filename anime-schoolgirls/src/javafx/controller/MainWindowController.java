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
import javafx.application.Platform;
import javafx.model.Episodes;
import javafx.model.MainWindowModel;
import javafx.model.Series;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
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
		setListeners(view.getTopSeriesTable(), view.getEpisodesTable());
		addTopSeriesFromAnimeFTW();
		addAllSeriesFromAnimeFTW();
	}

	@SuppressWarnings("unchecked")
	private void setListeners(@SuppressWarnings("rawtypes") TableView seriesTable, @SuppressWarnings("rawtypes") TableView episodesTable) {
		seriesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				Series series = (Series) seriesTable.getSelectionModel().getSelectedItem();
				if (series != null) {
					view.setSeriesTitle(series.getName());
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

			if (getWaitThread() != null)
				getWaitThread().setSeriesId(id);
			else {
				setWaitingThread(new WaitingThread(id) {
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
						setWaitingThread(null);
					}
				});

				getWaitThread().start();
			}

		} else
			createNewEpisodesThread(id);
	}

	private synchronized void setWaitingThread(WaitingThread waitingThread) { this.waitingThread = waitingThread; } 
	private synchronized WaitingThread getWaitThread() { return waitingThread; }
	
	private synchronized void createNewEpisodesThread(String id) {
		removeAllEpisodes();
		removeAllMovies();
		grabFTW ftwdaemon = new grabFTW();
		
		Node oldSeriesPlaceHolder = new Label("No content in table");
		Node oldMoviesPlaceHolder = new Label("No content in table");
		
		ProgressIndicator seriesProgress = new ProgressIndicator();
		ProgressIndicator moviesProgress = new ProgressIndicator();
		seriesProgress.setMaxSize(90, 90);
		moviesProgress.setMaxSize(90, 90);
		setPlaceHolders(new StackPane(seriesProgress), new StackPane(moviesProgress));

		thread = new Thread() {
			@Override
			public void run() {
				try {
					List<extractEpisodes> extractEpisodes;
					extractEpisodes = JsonDecoder.getEpisodes(ftwdaemon.getEpisodesByid("display-episodes", id));
					
					if(getWaitThread() == null) {
						addAllEpisodes(convertFromExtractEpisodesToEpisodes(extractEpisodes));
						addAllMovies(convertFromExtractEpisodesToMovies(extractEpisodes));
					}
					} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					if(getWaitThread() == null) {
						setPlaceHolders(oldSeriesPlaceHolder, oldMoviesPlaceHolder);
					}
					thread = null;
				}
			}
		};

		thread.start();
	}
	
	private void setPlaceHolders(Node newSeriesPlaceHolder, Node newMoviesPlaceHolder) {
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				view.getEpisodesTable().setPlaceholder(newSeriesPlaceHolder);
				view.getMovieTable().setPlaceholder(newMoviesPlaceHolder);
			}
		});
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
			new ProcessBuilder(arguments).start();
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

	public void addAllTopSeries(List<Series> topseriesList) {
		model.getTopSeriesList().addAll(topseriesList);
	}
	
	public void addTopSeries(String name, String description, String rating, String image, String id, String watchlist) {
		model.getTopSeriesList().add(new Series(name, description, rating, image, id, watchlist));
	}
	
	public void removeAllTopSeries() {
		model.getTopSeriesList().clear();
	}
	
	public void removeTopSeries(int index) {
		model.getTopSeriesList().remove(index);
	}
	
	public void addAllSeries(List<Series> seriesList) {
		model.getSeriesList().addAll(seriesList);
	}

	public void addSeries(String name, String description, String rating, String image, String id, String watchlist) {
		model.getSeriesList().add(new Series(name, description, rating, image, id, watchlist));
	}

	public void removeAllSeries() {
		model.getSeriesList().clear();
	}

	public void removeSeries(int index) {
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

	private void addTopSeriesFromAnimeFTW() {
		grabFTW ftwdaemon = new grabFTW();
		
		Node oldPlaceHolder = new Label("No content in table");
		
		ProgressIndicator progress = new ProgressIndicator();
		progress.setMaxSize(90, 90);
		view.getTopSeriesTable().setPlaceholder(new StackPane(progress));
		
		new Thread() {
			@Override
			public void run() {
				try {
					List<extractSeries> extractSeries;

					extractSeries = JsonDecoder.getSeries(ftwdaemon.getListing("top-series", 10));
					addAllTopSeries(convertFromExtractSeriesToSeries(extractSeries));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}finally{
					view.getTopSeriesTable().setPlaceholder(oldPlaceHolder);
				}
			}
		}.start();
	}
	
	private void addAllSeriesFromAnimeFTW() {
		grabFTW ftwdaemon = new grabFTW();
		
		Node oldPlaceHolder = view.getSeriesTable().getPlaceholder();
		
		ProgressIndicator progress = new ProgressIndicator();
		progress.setMaxSize(90, 90);
		view.getSeriesTable().setPlaceholder(new StackPane(progress));
		
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
				}finally{
					view.getSeriesTable().setPlaceholder(oldPlaceHolder);
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
		System.out.println(retList.size());
		
		return retList;
	}
}
