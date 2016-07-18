package javafx.controller;

import javafx.model.MainWindowModel;
import javafx.model.Video;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.view.MainWindowView;

public class MainWindowController {

	private MainWindowModel model = new MainWindowModel();
	private MainWindowView view = new MainWindowView(model);
	
	/* MUST BE REMOVED AT SOME POINT */
	public MainWindowController() {
		addVideo("https://img03.animeftw.tv/seriesimages/3.jpg", "Hentai", "This is some really kinky Hentai, don't watch it, it's miiiiine!");
	}
	
	public void addVideo(String imageURL, String name, String description) {
		ImageView imgView = new ImageView(getImageFromURL(imageURL));
		imgView.setPreserveRatio(true);
		imgView.setFitWidth(200);
		imgView.setFitHeight(200);
		model.getList().add(new Video(imgView, name, description));
	}
	
	public void removeAllVideos() {
		model.getList().clear();
	}
	
	public void removeVideo(int index) {
		model.getList().remove(index);
	}
	
	private Image getImageFromURL(String imageURL) {
		Image img = null;
		try{
			img = new Image(imageURL);
		}catch(NullPointerException | IllegalArgumentException e) {
			/* If fetching image fails, use stock image */
			
			img = new Image("javafx/view/image/noimage.jpg");
		}
		
		return img;
	}

	public Node getView() {
		return view;
	}
}
