package javafx.view;

import java.net.URL;
import java.util.ResourceBundle;

import backend.WaitingThread;
import javafx.Resources;
import javafx.collections.ObservableList;
import javafx.controller.MainWindowController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.DescriptionTextArea;
import javafx.model.Episodes;
import javafx.model.Series;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainWindowView extends SplitPane implements Initializable {
	
	private ObservableList<Series> topseriesList;
	private ObservableList<Series> seriesList;
	private ObservableList<Episodes> episodeList;
	private ObservableList<Episodes> movieList;
	
	private MainWindowController controller;
	
	private Thread thread = null;
	private WaitingThread waitingThread = null;
	
	@SuppressWarnings("rawtypes")
	@FXML TabPane episodeTabs;
	@FXML TableView topseriesTable;
	@FXML TableView seriesTable;
	@FXML TableView epTable;
	@FXML TableView movieTable;
	@FXML ScrollPane scrollPane;
	@FXML ImageView seriesImage;
	@FXML DescriptionTextArea seriesDescription;

	public MainWindowView(ObservableList<Series> topseriesList, ObservableList<Series> seriesList, ObservableList<Episodes> episodesList, ObservableList<Episodes> movieList) {
		this.topseriesList = topseriesList;
		this.seriesList = seriesList;
		this.episodeList = episodesList;
		this.movieList = movieList;
		Resources.loadFXML(this);
	}
	
	public void setController(MainWindowController controller) {
		this.controller = controller;
	}
	
	public void playEpisode() {
		controller.playEpisode();
	}
	
	public void setSeriesDescription(String description) {
		seriesDescription.setText(description);
	}
	
	//TODO: Fix multi-threading as this has a slight chance to crash!
	public void setSeriesImage(String url) {
		seriesImage.setImage(new Image("/javafx/view/image/noimage.png"));
		if(thread != null) {
			thread.interrupt();
			
			if(waitingThread != null)
				waitingThread.setSeriesId(url);
			else{
				waitingThread = new WaitingThread(url) {
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

						setImageThread(getSeriesId());
						waitingThread = null;
					}
				};

				waitingThread.start();
			}
		}else
			setImageThread(url);
	}
	
	private synchronized void setImageThread(String url) {
		thread = new Thread() {
			@Override
			public void run() {
				seriesImage.setImage(new Image(url));
				
				thread = null;
			}
		};
		thread.start();
	}
	
	public synchronized TableView getTopSeriesTable() { return topseriesTable; }
	public synchronized TableView getSeriesTable() { return seriesTable; }
	public synchronized TableView getEpisodesTable() { return epTable; }
	public synchronized TableView getMovieTable() { return movieTable; }
	
	public TabPane getEpisodeTabs() { return episodeTabs; }
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
        /* add column to the tableview and set its items */
        topseriesTable.getColumns().add(createNewTableColumn("Name", "name", -1, 400, -1));
        topseriesTable.getColumns().add(createNewTableColumn("Rating", "rating", 75, 75, 75));
        topseriesTable.setItems(topseriesList);
        
        seriesTable.getColumns().add(createNewTableColumn("Watchlist", "watchlistIcon", 64, 64, 64));
        seriesTable.getColumns().add(createNewTableColumn("Name", "name", -1, 400, -1));
        seriesTable.getColumns().add(createNewTableColumn("Rating", "rating", 75, 75, 75));
        seriesTable.setItems(seriesList);
        
        epTable.getColumns().add(createNewTableColumn("Episode", "epnumber", 75, 75, 75));
        epTable.getColumns().add(createNewTableColumn("Name", "name", -1, -1, -1));
        epTable.setItems(episodeList);
        
        movieTable.getColumns().add(createNewTableColumn("Movie", "epnumber", -1, 10, -1));
        movieTable.getColumns().add(createNewTableColumn("Name", "name", -1, -1, -1));
        movieTable.setItems(movieList);
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	private TableColumn createNewTableColumn(String columnName, String variableName, int minWidth, int prefWidth, int maxWidth) {
		TableColumn column = new TableColumn<>(columnName);
		column.setCellValueFactory(new PropertyValueFactory<>(variableName));
		column.setEditable(false);
		if(minWidth != -1)
			column.setMinWidth(minWidth);
		if(prefWidth != -1)
			column.setPrefWidth(prefWidth);
		if(maxWidth != -1)
			column.setMaxWidth(maxWidth);
        
        return column;
	}
}
