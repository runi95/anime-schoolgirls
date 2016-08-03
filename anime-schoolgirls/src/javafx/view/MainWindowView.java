package javafx.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.Resources;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.DescriptionTextArea;
import javafx.model.Series;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainWindowView extends SplitPane implements Initializable {

	private ObservableList<Series> list;
	
	@SuppressWarnings("rawtypes")
	@FXML TableView seriesTable;
	@FXML ScrollPane scrollPane;
	@FXML DescriptionTextArea description;

	public MainWindowView(ObservableList<Series> list) {
		this.list = list;
		Resources.loadFXML(this);
	}
	
	public int listSize() { return list.size(); }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
        /* initialize and specify table column */
        TableColumn tcC1 = new TableColumn<>("Name");
        tcC1.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcC1.setEditable(false);
        tcC1.setPrefWidth(400);
        TableColumn tcC2 = new TableColumn<>("Rating");
        tcC2.setCellValueFactory(new PropertyValueFactory<>("rating"));
        tcC2.setEditable(false);
        tcC2.setMaxWidth(200);

        /* add column to the tableview and set its items */
        seriesTable.getColumns().add(tcC1);
        seriesTable.getColumns().add(tcC2);
        seriesTable.setItems(list);
        
        seriesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	System.out.println(seriesTable.getSelectionModel().getSelectedItem());
            }
        });
	}
}
