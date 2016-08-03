package backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileManager {
	
	public final static String USER_HOME = System.getProperty("user.home") + System.getProperty("file.separator")
	+ "anime-hentai" + System.getProperty("file.separator");

	public static ArrayList<String> readFile(String fileLocation) {
		ArrayList<String> text = new ArrayList<>();

		try (BufferedReader r = new BufferedReader(new FileReader(new File(fileLocation)))) {
			String line;
			while ((line = r.readLine()) != null)
				text.add(line);
		} catch (Exception e) {
			// e.printStackTrace();
		}

		return text;
	}

	public static void writeFile(String fileLocation, String fileName, String text) {
		File f = new File(fileLocation);
		try {
			f.mkdirs();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		try (BufferedWriter w = new BufferedWriter(
				new FileWriter(f.getAbsolutePath() + System.getProperty("file.separator") + fileName))) {
			w.write(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
