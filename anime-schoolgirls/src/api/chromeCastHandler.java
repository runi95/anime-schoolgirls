package api;

import java.io.IOException;
import java.security.GeneralSecurityException;

import su.litvak.chromecast.api.v2.Application;
import su.litvak.chromecast.api.v2.ChromeCast;
import su.litvak.chromecast.api.v2.ChromeCasts;
import su.litvak.chromecast.api.v2.Status;

public class chromeCastHandler {
	public static boolean ccDiscovered = false;
	static String APP_ID = "CC1AD845";

	
	public static void set_ccDiscovered(boolean Discovered) {
		ccDiscovered = Discovered;
		
	}
	public static void StartDiscovery() throws IOException, InterruptedException{
		ChromeCasts.startDiscovery();
		
		while(ChromeCasts.get().isEmpty()){
			Thread.sleep(100);
			System.out.println("Sleeping");
		}
		
		set_ccDiscovered(true);
	}
	
	public static ChromeCast ccConnect(int id) throws IOException, GeneralSecurityException{
		ChromeCast chromecast = ChromeCasts.get().get(id);
		// Connect
		chromecast.connect();
		
		return chromecast;
		
	}
	
	public static Status getStatus(ChromeCast chromecast) throws IOException{
		Status status = chromecast.getStatus();
		return status;
	}
	
	public static boolean startApp(ChromeCast chromecast, Status status) throws IOException, InterruptedException{
		if (chromecast.isAppAvailable(APP_ID) && !status.isAppRunning(APP_ID)) {
			  Application app = chromecast.launchApp(APP_ID);
			}
		
		while(!status.isAppRunning(APP_ID) && !status.isAppRunning("E8C28D3C")){
			Thread.sleep(100);
			String running = status.getRunningApp().id;
			System.out.println("STUCK");
			System.out.print("Current App: " + running);
		}
		return true;
	}
	
	public static void playVideo(String url, String name, ChromeCast chromecast) throws IOException{
		chromecast.load(name,           // Media title
                "",  // URL to thumbnail based on media URL
                url, // media URL
                "video/mp4" // media content type
                );
	}
}
