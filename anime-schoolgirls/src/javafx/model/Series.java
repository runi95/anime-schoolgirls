package javafx.model;

import backend.FileManager;
import backend.IntegerParser;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Series {

	private ImageView watchlistIcon = new ImageView();
	private String name, description, rating, image, id, watchlist;

	public Series(String name, String description, String rating, String image, String id, String watchlist) {
		this.name = name;
		this.description = description;
		this.rating = rating;
		this.image = image;
		this.id = id;
		this.watchlist = watchlist;
		
		watchlistIcon.setFitHeight(64);
		watchlistIcon.setFitWidth(64);
		setWatchlistIcon(watchlist);
	}

	private void setWatchlistIcon(String watchlist) {
		switch (IntegerParser.parseInt(watchlist)) {

		default:
			 watchlistIcon.setImage(new
			 Image("/javafx/view/image/noimage.png"));
		}
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getRating() {
		return rating;
	}

	public String getImage() {
		return image;
	}

	public String getId() {
		return id;
	}

	public String getWatchlist() {
		return watchlist;
	}
	 public ImageView getWatchlistIcon() { return watchlistIcon; }

}
