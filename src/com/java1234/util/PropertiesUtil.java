package com.java1234.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/*
 * ¶ÁÈ¡diary.propertiesÎÄ¼þ
 * 
* @author chenyanwei
* @date 2 Dec 2018 10:42:16
* @version 1.0
*/
public class PropertiesUtil {
	public static String getValue(String key) {
		Properties prop=new Properties();
		InputStream inputStream=new PropertiesUtil().getClass().getResourceAsStream("/diary.properties");
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (String) prop.get(key);
	}

}
