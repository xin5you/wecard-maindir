package com.cn.thinkx.pms.base.utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.IOUtils;
/**
 * Properties 工具类
 * @author bai
 *
 */

public class PropertiesLoader
{
	private static Logger log = LoggerFactory.getLogger(PropertiesLoader.class);
  private final Properties properties;
  
  public PropertiesLoader(String... resourcesPaths)
  {
    this.properties = loadProperties(resourcesPaths);
  }
  
  public Properties getProperties()
  {
    return this.properties;
  }
  
  public void updateProperties(String key, String value)
  {
    if ((getValue(key) == null) || (getValue(key) == "")) {
      return;
    }
    this.properties.setProperty(key, value);
  }
  
  private String getValue(String key)
  {
    String systemProperty = System.getProperty(key);
    if (systemProperty != null) {
      return systemProperty;
    }
    if (this.properties.containsKey(key)) {
      return this.properties.getProperty(key);
    }
    return "";
  }
  
  public String getProperty(String key)
  {
    String value = getValue(key);
    if (value == null) {
      throw new NoSuchElementException();
    }
    return value;
  }
  
  public String getProperty(String key, String defaultValue)
  {
    String value = getValue(key);
    return value != null ? value : defaultValue;
  }
  
  public Integer getInteger(String key)
  {
    String value = getValue(key);
    if (value == null) {
      throw new NoSuchElementException();
    }
    return Integer.valueOf(value);
  }
  
  public Integer getInteger(String key, Integer defaultValue)
  {
    String value = getValue(key);
    return value != null ? Integer.valueOf(value) : defaultValue;
  }
  
  public Double getDouble(String key)
  {
    String value = getValue(key);
    if (value == null) {
      throw new NoSuchElementException();
    }
    return Double.valueOf(value);
  }
  
  public Double getDouble(String key, Integer defaultValue)
  {
    String value = getValue(key);
    return Double.valueOf(value != null ? Double.valueOf(value).doubleValue() : defaultValue.intValue());
  }
  
  public Boolean getBoolean(String key)
  {
    String value = getValue(key);
    if (value == null) {
      throw new NoSuchElementException();
    }
    return Boolean.valueOf(value);
  }
  
  public Boolean getBoolean(String key, boolean defaultValue)
  {
    String value = getValue(key);
    return Boolean.valueOf(value != null ? Boolean.valueOf(value).booleanValue() : defaultValue);
  }
  
  private Properties loadProperties(String... resourcesPaths)
  {
    Properties props = new Properties();
    for (String location : resourcesPaths)
    {
      InputStream is = null;
      try
      {
        is = new FileInputStream(location);
        props.load(is);
      }
      catch (IOException ex)
      {
        log.error("Could not load properties from path:" + location + ", " + ex.getMessage());
      }
      finally
      {
        IOUtils.closeQuietly(is);
      }
    }
    return props;
  }
}
