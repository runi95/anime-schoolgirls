package api;

import com.google.gson.Gson;
import api.Config;


public class JsonDecoder {

	public static void handleJson(String ftwJson, String Method) throws Exception{
		Gson gson = new Gson();
		//String testline = "{'status':'200','message':'7231690b-2f3c-4c35-b9e6-4aa48ae84ddd'}"; 
		switch (Method){
		case "token":
			userInfo info = gson.fromJson(ftwJson, userInfo.class);
			// Like this, right? ^
			// And then retrieve info like...
			Config.userToken =  info.getToken(); //?
			//System.out.println(Config.userToken);
			break;
			
		case "Series":
			
			break;
		
		case "Episodes":
			
			break;
		}

	}
	
	public class userInfo
	{
	   private String message;
	    
	   public userInfo(){      
	   }
	    
	   public userInfo(String message){
	      
	      this.message = message;
	      
	   }
	    
	   public String getUserToken()
	   {
	      return message;
	   }
	   public void setUserToken(String message)
	   {
	      this.message = message;
	   }
	   
	    
	   
	   public String getToken()
	   {
	      return message;
	   }
	}

}
