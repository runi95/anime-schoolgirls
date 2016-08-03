package javafx.model;

import javafx.scene.image.ImageView;

public class Series {
	
	private String name, description, rating, image, id;
	
	public Series(String name, String description, String rating, String image, String id) {
		this.name = name;
		this.description = description;
		this.rating = rating;
		this.image = image;
		this.id = id;
	}
	
	public String getName() { return name; }
	public String getDescription() { return description; }
	public String getRating() { return rating; }
	public String getImage() { return image; }
	public String getId() { return id; }
	
}
