/**
 * @author Henning Berge
 */


package api;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import api.Config;


public class JsonDecoder {

	//private static ArrayList<SeriesList> SeriesList;
	public static <T> List<extractSeries> getSeries(String ftwJson){
		SeriesList serieslist = new Gson().fromJson(ftwJson, SeriesList.class);
		
		return serieslist.getlist();
		
	}
	
	public static <T> List<extractEpisodes> getEpisodes(String ftwJson){
		EpisodesList episodeslist = new Gson().fromJson(ftwJson, EpisodesList.class);
		
		return episodeslist.getlist();
	}
	
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
	 * 
	 * 
	 * Series Grabber
	 * 
	 */
	public class SeriesList {
	    @SerializedName("results")
	    public List<extractSeries> results;
	    public List<extractSeries> getlist(){
			return results;
	    	
	    }
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
	   private String watchlist;

	    
	   public extractSeries(){      
	   }
	    
	   public extractSeries(String id, String watchlist, String fullSeriesName, String description, String ratingLink, String image/*, String image-320x280*/, String romaji){
	      
	      this.id = id;
	      this.fullSeriesName = fullSeriesName;
	      this.description = description;
	      this.ratingLink = ratingLink;
	      this.image = image;
	      //this.image-320x280 = image-320x280;
	      this.romaji = romaji;
	      this.watchlist= watchlist;

	   }
	   public void setromaji(String romaji)
	   {
	      this.romaji = romaji;
	   }	   
	   public String getromaji()
	   {
	      return romaji;
	   }
	   public void setwatchlist(String watchlist)
	   {
	      this.watchlist = watchlist;
	   }	   
	   public String getwatchlist()
	   {
	      return watchlist;
	   }
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
	   public String getratingString()
	   {
		   switch (ratingLink){
		   case "https://img03.animeftw.tv/ratings/15+.jpg":
			   return "15";
		   case "https://img03.animeftw.tv/ratings/12+.jpg":
			   return "12";
		   case "https://img03.animeftw.tv/ratings/18+.jpg":
			   return "18";
		   default:
			   return "e";
		   }
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

	   public boolean valid(){
		   return fullSeriesName != null;
	   }
	}
	
	/**
	 * 
	 * 
	 * Episode Grabber
	 * 
	 */
	public class EpisodesList {
	    @SerializedName("results")
	    public List<extractEpisodes> results;
	    public List<extractEpisodes> getlist(){
			return results;
	    	
	    }
	}
	public class extractEpisodes
	{
	   private String id;
	   private String epname;
	   private String video;
	   private String epnumber;
	   private String image;
	   private String Movie;
	   private String romaji;

	    
	   public extractEpisodes(){      
	   }
	    
	   public extractEpisodes(String id, String epname, String video, String epnumber, String image, String Movie, String romaji){
	      
	      this.id = id;
	      this.epname = epname;
	      this.video = video;
	      this.epnumber = epnumber;
	      this.image = image;
	      this.Movie = Movie;
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
	   public void Movie(String Movie)
	   {
	      this.Movie = Movie;
	   }	   
	   public String Movie()
	   {
	      return Movie;
	   }
	   public void setimage(String image)
	   {
	      this.image = image;
	   }	   
	   public String getimage()
	   {
	      return image;
	   }
	   public void setepnumber(String epnumber)
	   {
	      this.epnumber = epnumber;
	   }	   
	   public String getepnumber()
	   {
	      return epnumber;
	   }
	   public void setvideo(String video)
	   {
	      this.video = video;
	   }	   
	   public String getvideo()
	   {
	      return video;
	   }
	   public void setepname(String epname)
	   {
	      this.epname = epname;
	   }	   
	   public String getepname()
	   {
	      return epname;
	   }
	   public void setid(String id)
	   {
	      this.id = id;
	   }	   
	   public String getid()
	   {
	      return id;
	   }

	   public boolean valid(){
		   return epname != null;
	   }
	}
	
	
	

}
