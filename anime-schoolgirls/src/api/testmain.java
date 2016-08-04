package api;
import api.clientHttp;
import api.chromeCastHandler;
import api.grabFTW;
import su.litvak.chromecast.api.v2.Application;
import su.litvak.chromecast.api.v2.ChromeCast;
import su.litvak.chromecast.api.v2.ChromeCasts;
import su.litvak.chromecast.api.v2.Status;

import java.util.List;

import api.JsonDecoder;

public class testmain {

	public static void main(String[] args) throws Exception {
		String APP_ID = "CC1AD845";
		
		chromeCastHandler.StartDiscovery();
		ChromeCast chromecast = chromeCastHandler.ccConnect(0);
		Status status = chromeCastHandler.getStatus(chromecast);
		while(!chromeCastHandler.startApp(chromecast, status)){
			Thread.sleep(10);
		}
		System.out.println("AppStarted");
		
		
		// Get device status
		
		chromeCastHandler.playVideo("http://videos.animeftw.tv/fullmetalalchemist/fullmetalalchemist_1_ns.mp4", "Test", chromecast);
		
		//if (clientHttp.Login("", "")){
		//	System.out.println("Success");
			
		//	getSeries();
		//} else {
		//	System.out.print("Something wong");
		//}
		Thread.sleep(8000);
		chromecast.stopApp();
		chromecast.disconnect();
		ChromeCasts.stopDiscovery();
		

	}
	
	public static void getSeries(){
		System.out.println("\nTesting 2 - Send Http POST request");
		List extractSeries;
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
