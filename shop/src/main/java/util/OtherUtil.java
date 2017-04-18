package util;

import java.util.Arrays;

public class OtherUtil {

	public static String parseArrToString(String[] cat_ids) {
		String str = Arrays.toString(cat_ids);
		return str.substring(1, str.length()-1);
	}
	
}
