package api;

import com.google.gson.Gson;
import api.Config;


public class JsonDecoder {

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
			
			break;
		
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
	
	
	

}
