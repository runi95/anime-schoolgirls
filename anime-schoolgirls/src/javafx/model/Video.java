package javafx.model;

import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

@Deprecated
public class Video {
	
	     private ImageView icon;
	     private String name;
	     private TextArea description;

	     public Video(ImageView imageView, String name, String description) 
	     {
	         this.icon = imageView;
	         setIconSize(icon);
	         this.name = name;
	         this.description = new TextArea(description) {
	        	 @Override
	        	 public void requestFocus() { 
	        		 // DO NOTHING? Probably...
	        	 }
	         };
	         this.description.setEditable(false);
	         this.description.setFocusTraversable(false);
	         this.description.setWrapText(true);
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
	    	 this.description = new TextArea(description) {
	        	 @Override
	        	 public void requestFocus() { 
	        		 // DO NOTHING? Probably...
	        	 }
	         };
	         this.description.setEditable(false);
	         this.description.setFocusTraversable(false);
	         this.description.setWrapText(true);
	     }

	     public TextArea getDescription() 
	     {
	         return this.description;
	     }
	     
	     private void setIconSize(ImageView icon) {
	    	 icon.setPreserveRatio(true);
	    	 icon.setFitWidth(200);
	    	 icon.setFitHeight(200);
	     }
	 }