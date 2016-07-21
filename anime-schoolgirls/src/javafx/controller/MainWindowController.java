package javafx.controller;

import java.util.ArrayList;
import java.util.List;

import api.JsonDecoder;
import api.JsonDecoder.extractSeries;
import api.grabFTW;
import javafx.model.MainWindowModel;
import javafx.model.Video;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.view.MainWindowView;

public class MainWindowController {

	private MainWindowModel model;;
	private MainWindowView view;
	
	public MainWindowController(MainWindowModel model, MainWindowView view) {
		this.model = model;
		this.view = view;
		addVideosFromAnimeFTW();
	}
	
	public void addAllVideos(List<Video> videoList) {
		model.getList().addAll(videoList);
	}
	
	public void addVideo(String imageURL, String name, String description) {
		model.getList().add(new Video(new ImageView(getImageFromURL(imageURL)), name, description));
	}
	
	public void removeAllVideos() {
		model.getList().clear();
	}
	
	public void removeVideo(int index) {
		model.getList().remove(index);
	}
	
	public Image getImageFromURL(String imageURL) {
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
	
	private void addVideosFromAnimeFTW() {
		List<extractSeries> extractSeries;
		grabFTW ftwdaemon = new grabFTW();
		
		try {
			extractSeries = JsonDecoder.getSeries(ftwdaemon.getListing("display-series", 3));
			addAllVideos(convertFromExtractSeriesToVideos(extractSeries));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		
		}
	}
	
	private List<Video> convertFromExtractSeriesToVideos(List<extractSeries> list) {
		List<Video> retList = new ArrayList<>();
		
		System.out.println("extractSeries = " + list.size());
		
		for(extractSeries series : list) {
			String image = series.getimage(), name = series.getfullSeriesName(), description = series.getdescription();
			retList.add(new Video(new ImageView(getImageFromURL(image)), name, description));
		}
		
		return retList;
	}
}
