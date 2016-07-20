package javafx.controller;

import java.util.ArrayList;
import java.util.List;

import api.Config;
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
	
	/* MUST BE REMOVED AT SOME POINT */
	public MainWindowController(MainWindowModel model, MainWindowView view) {
		this.model = model;
		this.view = view;
//		addVideo("https://img03.animeftw.tv/seriesimages/3.jpg", "Hentai", "This is some really kinky Hentai, don't watch it, it's miiiiine!");
		addVideosFromAnimeFTW();
		System.out.println("mode list = " + model.getList().size());
		System.out.println("view list = " + view.listSize());
	}
	
	public void addAllVideos(List<Video> videoList) {
		System.out.println("Adding " + videoList.size() + " to model list!");
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
//			System.out.println("token = " + Config.userToken);
			extractSeries = JsonDecoder.getSeries(ftwdaemon.getListing("display-series", 3));
//			System.out.println("extractSeries = " + extractSeries);
			addAllVideos(convertFromExtractSeriesToVideos(extractSeries));
//			convertFromExtractSeriesToVideos(extractSeries);
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
//			System.out.println("image: " + image + "\nname: " + name + "\ndescription: " + description);
			retList.add(new Video(new ImageView(getImageFromURL(image)), name, description));
		}
		
		return retList;
	}
}
