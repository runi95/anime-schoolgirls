package api;
import api.clientHttp;
import api.grabFTW;

import java.util.List;

import api.JsonDecoder;
import api.JsonDecoder.extractSeries;

public class testmain {

	public static void main(String[] args) throws Exception {

		if (clientHttp.Login("", "")){
			System.out.println("Success");
			
			getSeries();
		} else {
			System.out.print("Something wong");
		}
		

	}
	
	public static void getSeries(){
		System.out.println("\nTesting 2 - Send Http POST request");
		List<extractSeries> extractSeries;
		grabFTW ftwdaemon = new grabFTW();
		try {
			extractSeries = JsonDecoder.getSeries(ftwdaemon.getListing("display-series", 3));
			System.out.println(extractSeries.get(1).toString());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		
		}
		
	}

}
