package com.java8.practices.advance.common;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Props 
{
	private static Properties properties = null;

	static String PROJECT_LOC = System.getenv("APPSERVER_CONF_PATH") + "/jobengineconf";
	private Props() {}	

	public static String get(String key) 
	{
		if(properties == null)
			loadProperties();
		return properties.getProperty(key);
	}

	private static synchronized void loadProperties()
	{
		System.out.println("Entry in property loading");
		File file = null;
		try 
		{
			properties = new Properties();
			System.out.println("Props File :: " + getProjectLoc() + "/common.properties");
			file = new File(getProjectLoc() + "/common.properties");
			properties.load(new FileInputStream(file));
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			file = null;
			System.out.println("Exit from property loading");
		}
	}

	public static String getProjectLoc() 
	{
		return PROJECT_LOC;
	}

	public static synchronized void setProjectLoc(String path)
	{
		if(path != null)
			PROJECT_LOC = path.trim();
	}
}
