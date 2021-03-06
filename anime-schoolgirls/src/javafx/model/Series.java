package javafx.model;

import backend.IntegerParser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Series implements Comparable<Series> {

	private ImageView watchlistIcon = new ImageView();
	private int watchlistState = 0;
	private String name, description, rating, image, id, watchlist;

	public Series(String name, String description, String rating, String image, String id, String watchlist) {
		this.name = name;
		this.description = description;
		this.rating = rating;
		this.image = image;
		this.id = id;
		this.watchlist = watchlist;
		watchlistState = IntegerParser.parseInt(watchlist);
		
		watchlistIcon.setFitHeight(16);
		watchlistIcon.setFitWidth(16);
		setWatchlistIcon(watchlist);
	}

	private void setWatchlistIcon(String watchlist) {
		switch (watchlistState) {
		case 0:
			 watchlistIcon.setImage(new
			 Image("/javafx/view/image/empty-star.png"));
			 break;
		case 1:
			 watchlistIcon.setImage(new
			 Image("/javafx/view/image/full-star.png"));
			 break;
			 
		default:
			watchlistIcon.setImage(new Image("/javafx/view/image/half-full-star.png"));
			break;
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

	@Override
	public int compareTo(Series s) {
		return this.getName().compareTo(s.getName());
	}

}
