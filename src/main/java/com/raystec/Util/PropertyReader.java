package com.raystec.Util;
import java.util.ResourceBundle;

/**
 * Read the property values from application properties file using Resource
 * Bundle
 *
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 *
 */

public class PropertyReader {

	
	public static void main(String[] args) {
		ResourceBundle rb = ResourceBundle
	            .getBundle("System");
		System.out.println(rb.getString("url"));
	}
	
	
	  private static ResourceBundle rb = ResourceBundle
	  .getBundle("System");
	 
    /**
     * Return value of key
     *
     * @param key get the key
     * @return returns value
     */

    public static String getValue(String key) {

        String val = null;

        try {
            val = rb.getString(key);
        } catch (Exception e) {
            val = key;
        }

        return val;

    }

    /**
     * Gets String after placing param values
     *
     * @param key 
     * @param param
     * @return String
     */
    public static String getValue(String key, String param) {
        String msg = getValue(key);
        msg = msg.replace("{0}", param);
        return msg;
    }

    /**
     * Gets String after placing params values
     *
     * @param key
     * @param params
     * @return
     */
    public static String getValue(String key, String[] params) {
        String msg = getValue(key);
        for (int i = 0; i < params.length; i++) {
            msg = msg.replace("{" + i + "}", params[i]);
        }
        return msg;
    }

    /**
     * Test method
     *
     * @param args
     */

   

}


