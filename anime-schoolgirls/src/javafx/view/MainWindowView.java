package javafx.view;

import java.net.URL;
import java.util.ResourceBundle;

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

	private ObservableList<Series> seriesList;
	private ObservableList<Episodes> episodeList;
	private ObservableList<Episodes> movieList;
	
	private MainWindowController controller;
	
	@SuppressWarnings("rawtypes")
	@FXML TabPane episodeTabs;
	@FXML TableView seriesTable;
	@FXML TableView epTable;
	@FXML TableView movieTable;
	@FXML ScrollPane scrollPane;
	@FXML ImageView seriesImage;
	@FXML DescriptionTextArea seriesDescription;

	public MainWindowView(ObservableList<Series> seriesList, ObservableList<Episodes> episodesList, ObservableList<Episodes> movieList) {
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
	
	// TODO: Add thread pool, fix multithreading issue with images.
	public void setSeriesImage(String url) {
		seriesImage.setImage(new Image("/javafx/view/image/noimage.png"));
		Thread thread = new Thread() {
			@Override
			public void run() {
				seriesImage.setImage(new Image(url));
			}
		};
		thread.start();
	}
	
	public TableView getSeriesTable() { return seriesTable; }
	public TableView getEpisodesTable() { return epTable; }
	public TableView getMovieTable() { return movieTable; }
	
	public TabPane getEpisodeTabs() { return episodeTabs; }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
        /* initialize and specify table column for series */
		TableColumn tcCS1 = new TableColumn<>("Watchlist");
		tcCS1.setCellValueFactory(new PropertyValueFactory<>("watchlistIcon"));
		tcCS1.setEditable(false);
		tcCS1.setMinWidth(64);
        tcCS1.setMaxWidth(64);
        tcCS1.setPrefWidth(64);
        TableColumn tcCS2 = new TableColumn<>("Name");
        tcCS2.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcCS2.setEditable(false);
        tcCS2.setPrefWidth(400);
        TableColumn tcCS3 = new TableColumn<>("Rating");
        tcCS3.setCellValueFactory(new PropertyValueFactory<>("rating"));
        tcCS3.setEditable(false);
        tcCS3.setMinWidth(75);
        tcCS3.setMaxWidth(75);
        tcCS3.setPrefWidth(75);
        
        /* initialize and specify table column for episodes */
        TableColumn tcCE1 = new TableColumn<>("Episode");
        tcCE1.setCellValueFactory(new PropertyValueFactory<>("epnumber"));
        tcCE1.setEditable(false);
        tcCE1.setPrefWidth(75);
        tcCE1.setMaxWidth(75);
        tcCE1.setMinWidth(75);
        TableColumn tcCE2 = new TableColumn<>("Name");
        tcCE2.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcCE2.setEditable(false);
        
        /* initialize and specify table column for movies */
        TableColumn tcCM1 = new TableColumn<>("Movie");
        tcCM1.setCellValueFactory(new PropertyValueFactory<>("epnumber"));
        tcCM1.setEditable(false);
        tcCM1.setPrefWidth(10);
        TableColumn tcCM2 = new TableColumn<>("Name");
        tcCM2.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcCM2.setEditable(false);

        /* add column to the tableview and set its items */
        seriesTable.getColumns().add(tcCS1);
        seriesTable.getColumns().add(tcCS2);
        seriesTable.getColumns().add(tcCS3);
        seriesTable.setItems(seriesList);
        
        epTable.getColumns().add(tcCE1);
        epTable.getColumns().add(tcCE2);
        epTable.setItems(episodeList);
        
        movieTable.getColumns().add(tcCM1);
        movieTable.getColumns().add(tcCM2);
        movieTable.setItems(movieList);
	}
}
