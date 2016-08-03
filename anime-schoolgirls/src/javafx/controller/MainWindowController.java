package javafx.controller;

import java.util.ArrayList;
import java.util.List;

import api.JsonDecoder;
import api.JsonDecoder.extractSeries;
import api.grabFTW;
import javafx.model.MainWindowModel;
import javafx.model.Series;
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
	
	public void addAllSeries(List<Series> videoList) {
		model.getList().addAll(videoList);
	}
	
	public void addSeries(String name, String description, String rating, String image, String id) {
		model.getList().add(new Series(name, description, rating, image, id));
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
	
	/*
	private Image getRatingImage(String rating) { 
		switch(rating) {
		
		
		default:
			return new Image("javafx/view/image/noimage.jpg");
		}
	}
	*/
	
	private void addVideosFromAnimeFTW() {
		List<extractSeries> extractSeries;
		grabFTW ftwdaemon = new grabFTW();
		
		try {
			extractSeries = JsonDecoder.getSeries(ftwdaemon.getListing("display-series", 3));
			addAllSeries(convertFromExtractSeriesToSeries(extractSeries));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		
		}
	}
	
	private List<Series> convertFromExtractSeriesToSeries(List<extractSeries> list) {
		List<Series> retList = new ArrayList<>();
		
		System.out.println("extractSeries = " + list.size());
		
		for(extractSeries series : list) {
			String name = series.getfullSeriesName(), description = series.getdescription(), image = series.getimage(), rating = series.getratingString(), id = series.getid();
			retList.add(new Series(name, description, rating, image, id));
		}
		
		return retList;
	}
}
