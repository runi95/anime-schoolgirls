/**
 * @author Henning Berge
 */
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


import api.Config;




@SuppressWarnings("deprecation")
public class clientHttp {

	

	public static boolean Login(String userName, String userPass) throws Exception {

		clientHttp http = new clientHttp();
		



		return http.sendLoginRequest(userName, userPass);
		

		

	}


	// HTTP POST request
	private boolean sendLoginRequest(String userName, String userPass) throws Exception {
		
		String url = Config.BASE_URL;

		@SuppressWarnings("resource")
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("User-Agent", Config.USER_AGENT);

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("devkey", Config.DEV_KEY));
		urlParameters.add(new BasicNameValuePair("username", userName));
		urlParameters.add(new BasicNameValuePair("password", userPass));
		urlParameters.add(new BasicNameValuePair("remember", "true"));
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
		
		rd.close();

		//System.out.println();
		return JsonDecoder.handleJson(result.toString(), "token");

	}
	
	
	
	
	
	

	
}

