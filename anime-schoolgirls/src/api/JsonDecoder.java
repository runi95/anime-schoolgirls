package api;

import com.google.gson.Gson;


public class JsonDecoder {

	public static void handleJson(String ftwJson) throws Exception{
		Gson gson = new Gson();
		//String testline = "{'status':'200','message':'7231690b-2f3c-4c35-b9e6-4aa48ae84ddd'}"; 
		userInfo info = gson.fromJson(ftwJson, userInfo.class);
		// Like this, right? ^
		// And then retrieve info like...
		System.out.println(info.getUserToken()); //?
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
	      return "Token=" + message;
	   }
	}

}
