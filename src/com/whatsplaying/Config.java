package com.whatsplaying;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Config
{
	Properties prop = new Properties();
	public Config(String path)
	{
		try {
			InputStream input = new FileInputStream(path);
			prop.load(input);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public String getProperty(String key)
	{
		String value = this.prop.getProperty(key);
		return value;
	}
}
