package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {

	public static boolean isPhoneNumber(String from_num) {
		if(from_num.startsWith("0")){
			from_num = from_num.substring(1);
		}
		if(from_num.length() != 11){
			return false;
		}
		Pattern p = null;
		Matcher m = null;
		p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(from_num);
		return m.matches();
	}
	
	public static boolean isYidong(String from_num) {
		if(from_num.startsWith("0")){
			from_num = from_num.substring(1);
		}
		if(from_num.length() != 11){
			return false;
		}
		if (from_num.startsWith("134") || from_num.startsWith("135")
				|| from_num.startsWith("136") || from_num.startsWith("137")
				|| from_num.startsWith("138") || from_num.startsWith("139")
				|| from_num.startsWith("159") || from_num.startsWith("158")
				|| from_num.startsWith("150") || from_num.startsWith("157")
				|| from_num.startsWith("151") || from_num.startsWith("188")
				|| from_num.startsWith("187")|| from_num.startsWith("189")) {
			return true;
		}
		return false;
	}

	
}
