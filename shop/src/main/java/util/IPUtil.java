package util;

import javax.servlet.http.HttpServletRequest;

public class IPUtil {

	public static String getIP(HttpServletRequest request) {
	    if (request.getHeader("x-forwarded-for") == null) {  
	        return request.getRemoteAddr();  
	    }  
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip != null){
	    	String[] ips = ip.split(",");
	    	if(ips != null && ips.length != 0){
	    		return ips[0];
	    	}
	    }
	    return ip;
	}
	
}
