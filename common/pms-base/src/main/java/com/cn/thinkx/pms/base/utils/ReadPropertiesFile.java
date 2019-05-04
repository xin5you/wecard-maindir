package com.cn.thinkx.pms.base.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropertiesFile {
	// 配置文件存放的地址
	private static final String CONFIG_ADDRESS = "CONFIG_ADDRESS";
	// 连加密机文件存放的地址
	private static final String INI_ADDRESS = "INI_ADDRESS";
	
	private static final ReadPropertiesFile FILE = new ReadPropertiesFile();
	private ReadPropertiesFile() {}
	public static ReadPropertiesFile getInstance() {
		return FILE;
	}
	
	/**
	 * 根据配置文件类型取得对应文件属性值
	 * @param key 属性键值
	 * @param type 文件类型(properties为config配置文件、ini为hsm连加密机配置文件)
	 * @return 属性值
	 */
	public String getProperty(String key, String type) {
		Properties props = new Properties();
		InputStream in = null;
        try {
            in = this.getClass().getResourceAsStream("/config.properties");
            props.load(in);
            String value = null;
            if ("properties".equals(type) || type == null) {
            	value = props.getProperty(CONFIG_ADDRESS);
        	} else if ("ini".equals(type)) {
        		value = props.getProperty(INI_ADDRESS);
        	} else {
        		value = null;
        	}
            
            Properties p = new Properties();
            in = new BufferedInputStream(new FileInputStream(value));
			p.load(in);
			
            return p.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
        	if (in != null) {
        		try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
	}
	
	public static void main(String[] args) {
		ReadPropertiesFile rpf = new ReadPropertiesFile();
		System.out.println(rpf.getProperty("test", "ini")); 
	}
}
