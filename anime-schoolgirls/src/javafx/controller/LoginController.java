package javafx.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import api.Config;
import api.clientHttp;
import javafx.SceneController;
import javafx.model.LoginModel;
import javafx.model.MainWindowModel;
import javafx.model.UserInfo;
import javafx.view.LoginView;
import javafx.view.MainWindowView;

public class LoginController {
	private LoginView view;
	private LoginModel model;
	private final static String USER_HOME = System.getProperty("user.home") + System.getProperty("file.separator") + "anime-hentai" + System.getProperty("file.separator");
	
	public LoginController(LoginModel model, LoginView view) {
		this.model = model;
		this.view = view;
		view.setController(this);
		
		loadUserSettings();
	}
	
	public void loginButtonClicked(String username, String password, boolean toggled) {
		boolean loginSuccess = false;
		try {
			loginSuccess = clientHttp.Login(username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(loginSuccess) {
			if(toggled)
				saveUser(username, password);
			else
				saveToggleInfo("FALSE");
			MainWindowModel model = new MainWindowModel();
			SceneController.setScene(new MainWindowView(model.getList()));
		}else{
			view.showError("Error: Wrong username or password!");
		}
	}

	public LoginView getView() {
		return view;
	}
	
	private void saveUser(String username, String password) {
		UserInfo user = new UserInfo(username, password, Config.userToken);
		saveToggleInfo("TRUE");
		writeFile(USER_HOME, "user.info", user.toString());
	}
	
	private void saveToggleInfo(String toggle) {
		writeFile(USER_HOME, "user.settings", toggle);
	}
	
	private void writeFile(String fileLocation, String fileName, String text) {
		File f = new File(fileLocation);
		try{
			f.mkdirs();
		}catch(SecurityException e) {
			e.printStackTrace();
		}
		try( BufferedWriter w = new BufferedWriter(new FileWriter(f.getAbsolutePath() + System.getProperty("file.separator") + fileName)) ) {
			w.write(text);
		} catch( Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadUserSettings() {
		if(readToggleState()) {
			UserInfo user = readUser();
			view.setRememberMeToggle(true);
			view.setUsername(user.getUsername());
			view.setPassword(user.getPassword());
			Config.userToken(user.getToken());
		}
	}
	
	private boolean readToggleState() {
		ArrayList<String> text = readFile(USER_HOME + "user.settings");
		
		for(String s : text) {
			switch(s) {
			case "TRUE":
				return true;
			case "FALSE":
				return false;
			}
		}
		
		return false;
	}
	
	private UserInfo readUser() {
		ArrayList<String> text = readFile(USER_HOME + "user.info");
		
		String username = null, password = null, token = null;
		
		for(String s : text) {
			String[] split = s.split(" ");
			switch(split[0]) {
			case "USERNAME:":
				username = split[1];
				break;
			case "PASSWORD:":
				password = split[1];
				break;
			case "TOKEN:":
				token = split[1];
				break;
			}
		}
		return new UserInfo(username, password, token);
	}
	
	private ArrayList<String> readFile(String fileLocation) {
		ArrayList<String> text = new ArrayList<>();
		
		try(BufferedReader r = new BufferedReader(new FileReader(new File(fileLocation)))) {
			String line;
			while((line = r.readLine()) != null)
				text.add(line);
		} catch(Exception e) {
//			e.printStackTrace();
		}
		
		return text;
	}
}