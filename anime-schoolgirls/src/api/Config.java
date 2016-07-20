/**
 * @author Henning Berge
 */

package api;

public class Config {
	public static String userToken;
	public static String USER_AGENT = "HenningCast/javaCast";
	public static String DEV_KEY = "qruE-204Y-ZXgr-GLRZ";
	public static String BASE_URL = "https://www.animeftw.tv/api/v2/";

	public static void userToken(String token) {
		userToken = token;
		
	}
	
}
