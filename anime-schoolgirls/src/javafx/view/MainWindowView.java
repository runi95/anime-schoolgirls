package javafx.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.Resources;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.MainWindowModel;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainWindowView extends SplitPane implements Initializable {

	private MainWindowModel model;
	
	@SuppressWarnings("rawtypes")
	@FXML TableView videoTable;

	public MainWindowView(MainWindowModel model) {
		this.model = model;
		Resources.loadFXML(this);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
        /* initialize and specify table column */
        TableColumn tcC1 = new TableColumn<>("Icon");
        tcC1.setCellValueFactory(new PropertyValueFactory<>("icon"));
        tcC1.setEditable(false);
        tcC1.setMaxWidth(140);
        tcC1.setMinWidth(140);
        tcC1.setPrefWidth(140);

        TableColumn tcC2 = new TableColumn<>("Name");
        tcC2.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcC2.setEditable(false);
//        tcC2.setMaxWidth(500);
        tcC2.setMinWidth(40);
        tcC2.setPrefWidth(100);
        
        TableColumn tcC3 = new TableColumn<>("Description");
        tcC3.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcC3.setEditable(false);
//        tcC2.setMaxWidth(500);
        tcC2.setMinWidth(100);
        
        /* add column to the tableview and set its items */
        videoTable.getColumns().add(tcC1);
        videoTable.getColumns().add(tcC2);
        videoTable.getColumns().add(tcC3);
        videoTable.setItems(model.getList());
//        videoTable.setItems(model.getList());  
	}
}
