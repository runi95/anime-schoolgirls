package api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import api.Config;


public class JsonDecoder {

	//private static ArrayList<SeriesList> SeriesList;
	public static boolean handleJson(String ftwJson, String Method) throws Exception{
		Gson gson = new Gson();
		
		
		//String testline = "{'status':'200','message':'7231690b-2f3c-4c35-b9e6-4aa48ae84ddd'}"; 
		switch (Method){
		case "token":
			userInfo info = gson.fromJson(ftwJson, userInfo.class);
			// Like this, right? ^
			// And then retrieve info like...
			Config.userToken =  info.getToken(); //?
			System.out.println(info.getToken());
			if(Integer.parseInt(info.getUserLoginState()) == 200){
				return true;
				
			}
				
				
			break;
			
		case "Series":

			GsonBuilder builder = new GsonBuilder();
			Gson gsonjson = builder.create();
			String server_JSONResponse = ftwJson; // the string in which you are getting your JSON Response after hitting URL
			String myCustom_JSONResponse="";// in which we will keep our response after adding object element to it
			//extractSeries series = gsonjson.fromJson(ftwJson, extractSeries.class);
			//List<SeriesList> SeriesList;
			//SeriesList = new ArrayList<SeriesList>();
			//SeriesList = Arrays.asList(gson.fromJson(ftwJson, SeriesList[].class));
			//System.out.println(series.getid());
			SeriesList serieslist = new Gson().fromJson(ftwJson, SeriesList.class);
			System.out.println(gson.toJson(serieslist.results)); 
			myCustom_JSONResponse="{\"master\":"+serieslist.results+"}";
			extractSeries series = gson.fromJson(myCustom_JSONResponse, extractSeries.class);
			System.out.println(series.valid()); 
			//System.out.println(serieslist.results); 
			/**
			 * runar sjå hær:
			 * serieslist.results den returnerer list
			 */
			return true;
		
		case "Episodes":
			
			break;
			
		
			
			
		}
		return false;

	}
	
	public class userInfo
	{
	   private String message;
	   private String status;
	    
	   public userInfo(){      
	   }
	    
	   public userInfo(String message, String status){
	      
	      this.message = message;
	      this.status = status;
	      
	   }
	    
	   public String getUserToken()
	   {
	      return message;
	   }
	   public String getUserLoginState()
	   {
	      return status;
	   }
	   public void setUserToken(String message)
	   {
	      this.message = message;
	   }
	   public void setUserLoginState(String status)
	   {
	      this.status = status;
	   }
	   
	    
	   
	   public String getToken()
	   {
	      return message;
	   }
	}
	/**
	 * hær
	 * Eg forstår ikkje så møje av dette då. :p
	 * Trur det har med object og lister som ikkje går øveens
	 * 
	 */
	public class SeriesList {
	    @SerializedName("results")
	    public List<extractSeries> results;
	    //public String toString(){
		//	return null;
	    	
	    //}
	}
	public class extractSeries
	{
	   private String id;
	   private String fullSeriesName;
	   private String description;
	   private String ratingLink;
	   private String image;
	   //private String image-320x280;
	   private String romaji;

	    
	   public extractSeries(){      
	   }
	    
	   public extractSeries(String id, String fullSeriesName, String description, String ratingLink, String image/*, String image-320x280*/, String romaji){
	      
	      this.id = id;
	      this.fullSeriesName = fullSeriesName;
	      this.description = description;
	      this.ratingLink = ratingLink;
	      this.image = image;
	      //this.image-320x280 = image-320x280;
	      this.romaji = romaji;

	   }
	   public void setromaji(String romaji)
	   {
	      this.romaji = romaji;
	   }	   
	   public String getromaji()
	   {
	      return romaji;
	   }
//	   public void setimage-320x280(String image-320x280)
//	   {
//	      this.image-320x280 = image-320x280;
//	   }	   
//	   public String getimage-320x280()
//	   {
//	      return image-320x280;
//	   }
	   public void setimage(String image)
	   {
	      this.image = image;
	   }	   
	   public String getimage()
	   {
	      return image;
	   }
	   public void setratingLink(String ratingLink)
	   {
	      this.ratingLink = ratingLink;
	   }	   
	   public String getratingLink()
	   {
	      return ratingLink;
	   }
	   public void setdescription(String description)
	   {
	      this.description = description;
	   }	   
	   public String getdescription()
	   {
	      return description;
	   }
	   public void setfullSeriesName(String fullSeriesName)
	   {
	      this.fullSeriesName = fullSeriesName;
	   }	   
	   public String getfullSeriesName()
	   {
	      return fullSeriesName;
	   }
	   public void setid(String id)
	   {
	      this.id = id;
	   }	   
	   public String getid()
	   {
	      return id;
	   }
<<<<<<< HEAD
	   public boolean valid()
	   {
		   return id != null;
=======
	   public boolean valid(){
		   return fullSeriesName != null;
>>>>>>> 6108155b732ebc7623197312fb930bd7c4cb1dd5
	   }
	}
	
	
	

}
