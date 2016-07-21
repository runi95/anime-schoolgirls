package javafx.model;

import javafx.scene.image.ImageView;

public class Series {
	
	private String name, description, rating, image;
	
	public Series(String name, String description, String rating, String image) {
		this.name = name;
		this.description = description;
		this.rating = rating;
		this.image = image;
	}
	
	public String getName() { return name; }
	public String getDescription() { return description; }
	public String getRating() { return rating; }
	public String getImage() { return image; }
	
}
