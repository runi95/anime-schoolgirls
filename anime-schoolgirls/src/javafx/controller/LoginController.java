package javafx.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import api.Config;
import api.clientHttp;
import backend.FileManager;
import javafx.SceneController;
import javafx.model.LoginModel;
import javafx.model.MainWindowModel;
import javafx.model.UserInfo;
import javafx.view.LoginView;
import javafx.view.MainWindowView;

public class LoginController {
	private LoginView view;
	private LoginModel model;
	private final static String USER_HOME = System.getProperty("user.home") + System.getProperty("file.separator")
			+ "anime-hentai" + System.getProperty("file.separator");

	public LoginController(LoginModel model, LoginView view) {
		this.model = model;
		this.view = view;
		view.setController(this);

		loadUserSettings();
	}

	public void loginButtonClicked(String username, String password, boolean toggled) {
		boolean loginSuccess = false, tokenHasValidDate = checkDate(model.getTokenDate());
		if (tokenHasValidDate) {
			Config.userToken(model.getToken());
			loadMainScreen();
		} else {
			try {
				loginSuccess = clientHttp.Login(username, password);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (loginSuccess) {
				if (toggled)
					saveUser(username, password);
				else
					saveToggleInfo("FALSE");
				loadMainScreen();
			} else {
				view.showError("Error: Wrong username or password!");
			}
		}
	}

	public LoginView getView() {
		return view;
	}

	private void saveUser(String username, String password) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		String date = dateFormat.format(new Date());
		UserInfo user = new UserInfo(username, password, Config.userToken, date);
		saveToggleInfo("TRUE");
		FileManager.writeFile(USER_HOME, "user.info", user.toString());
	}

	private void saveToggleInfo(String toggle) {
		FileManager.writeFile(USER_HOME, "user.settings", toggle);
	}

	private void loadUserSettings() {
		if (readToggleState()) {
			UserInfo user = readUser();
			view.setRememberMeToggle(true);
			setUsername(user.getUsername());
			setPassword(user.getPassword());
			setToken(user.getToken());
			setTokenDate(user.getTokenDate());
		}
	}

	public void setUsername(String username) {
		model.setUsername(username);
		view.setUsername(username);
	}

	public void setPassword(String password) {
		model.setPassword(password);
		view.setPassword(password);
	}

	public void setToken(String token) {
		model.setToken(token);
	}

	public void setTokenDate(String tokenDate) {
		model.setTokenDate(tokenDate);
	}

	private boolean checkDate(String tokenDate) {
		if(tokenDate == null)
			return false;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		String date = dateFormat.format(new Date());

		long tokenDays = compareDate(tokenDate, date);

		if (tokenDays > 26)
			return false;
		else
			return true;
	}

	private long compareDate(String oldDate, String newDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");

		try {
			Date parsedOldDate = dateFormat.parse(oldDate);
			Date parsedNewDate = dateFormat.parse(newDate);

			long diff = parsedNewDate.getTime() - parsedOldDate.getTime();

			return TimeUnit.DAYS.convert(diff, TimeUnit.MICROSECONDS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 30;
	}

	private boolean readToggleState() {
		ArrayList<String> text = FileManager.readFile(USER_HOME + "user.settings");

		for (String s : text) {
			switch (s) {
			case "TRUE":
				return true;
			case "FALSE":
				return false;
			}
		}

		return false;
	}

	private UserInfo readUser() {
		ArrayList<String> text = FileManager.readFile(USER_HOME + "user.info");

		String username = null, password = null, token = null, date = null;

		for (String s : text) {
			String[] split = s.split(" ");
			switch (split[0]) {
			case "USERNAME:":
				username = split[1];
				break;
			case "PASSWORD:":
				password = split[1];
				break;
			case "TOKEN:":
				token = split[1];
				break;
			case "TOKENDATE:":
				date = split[1];
				break;
			}
		}
		return new UserInfo(username, password, token, date);
	}

	private void loadMainScreen() {
		MainWindowModel model = new MainWindowModel();
		SceneController.setScene(new MainWindowView(model.getList()));
	}
}