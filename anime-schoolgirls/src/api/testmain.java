package api;
import api.clientHttp;
import api.grabFTW;
import api.JsonDecoder;

public class testmain {

	public static void main(String[] args) throws Exception {
		if (clientHttp.Login("hennber", "SrB8ORWtsNyn")){
			System.out.println("Success");
			
			getSeries();
		} else {
			System.out.print("Something wong");
		}
		

	}
	
	public static void getSeries(){
		System.out.println("\nTesting 2 - Send Http POST request");
		
		grabFTW ftwdaemon = new grabFTW();
		try {
			if(JsonDecoder.handleJson(ftwdaemon.getListing("display-series", 3), "Series")){
				System.out.println("SUCCESS");
			}
				
			//System.out.println(ftwdaemon.getListing("display-series", 3));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
