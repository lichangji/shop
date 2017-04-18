package util;

import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertieUtil {
	public static String loadPropertie(String name) throws Exception{
		Properties props = PropertiesLoaderUtils.loadAllProperties("config.properties"); 
		return props.getProperty(name);
	}
}
