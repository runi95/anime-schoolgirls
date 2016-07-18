package javafx.model;

import javafx.scene.image.ImageView;

public class Video {
	
	     private ImageView icon;
	     private String name, description;

	     Video(ImageView icon, String name, String description) 
	     {
	         this.icon = icon;
	         this.name = name;
	         this.description = description;
	     }

	     public void setIcon(ImageView value) 
	     {
	    	 icon = value;
	     }

	     public ImageView getIcon() 
	     {
	         return icon;
	     }
	     
	     public void setName(String name) {
	    	 this.name = name;
	     }
	     
	     public String getName() {
	    	 return this.name;
	     }

	     public void setDescription(String description) 
	     {
	         this.description = description;
	     }

	     public String getDescription() 
	     {
	         return this.description;
	     }
	 }