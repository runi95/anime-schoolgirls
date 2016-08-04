package backend;

public class IntegerParser {

	public static int parseInt(String integer) {
		int parsedInt = -1;

		try {
			parsedInt = Integer.parseInt(integer);
		} catch (NumberFormatException e) {
//			e.printStackTrace();
		}

		return parsedInt;
	}
}
