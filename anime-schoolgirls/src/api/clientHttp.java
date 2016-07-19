package api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import api.JsonDecoder.userInfo;
import api.Config;
import api.grabFTW;




@SuppressWarnings("deprecation")
public class clientHttp {

	private final String USER_NAME = "hennber";
	private final String USER_PASS = "Henning569348";
	

	public static boolean Login(String[] args) throws Exception {

		clientHttp http = new clientHttp();
		


		//System.out.println("\nTesting 2 - Send Http POST request");
		
		//grabFTW ftwdaemon = new grabFTW();
		//System.out.println(ftwdaemon.getListing("display-series", 3));
		return http.sendLoginRequest();
		

		

	}


	// HTTP POST request
	private boolean sendLoginRequest() throws Exception {
		
		String url = Config.BASE_URL;

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("User-Agent", Config.USER_AGENT);

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("devkey", Config.DEV_KEY));
		urlParameters.add(new BasicNameValuePair("username", USER_NAME));
		urlParameters.add(new BasicNameValuePair("password", USER_PASS));
//		urlParameters.add(new BasicNameValuePair("caller", ""));
//		urlParameters.add(new BasicNameValuePair("num", "12345"));

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + post.getEntity());
		System.out.println("Response Code : " + 
                                    response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		//System.out.println();
		return JsonDecoder.handleJson(result.toString(), "token");

	}
	
	
	
	
	
	

	
}

