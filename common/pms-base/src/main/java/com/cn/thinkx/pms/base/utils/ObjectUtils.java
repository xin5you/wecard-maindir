package com.cn.thinkx.pms.base.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

/**
 * Object工具类
 * @author zqy
 *
 */
public class ObjectUtils
extends org.apache.commons.lang3.ObjectUtils
{
  public static void annotationToObject(Object annotation, Object object)
  {
    if (annotation != null)
    {
      Class<?> annotationClass = annotation.getClass();
      Class<?> objectClass = object.getClass();
      for (Method m : objectClass.getMethods()) {
        if (StringUtils.startsWith(m.getName(), "set")) {
          try
          {
            String s = StringUtils.uncapitalize(StringUtils.substring(m.getName(), 3));
            Object obj = annotationClass.getMethod(s, new Class[0]).invoke(annotation, new Object[0]);
            if ((obj != null) && (!"".equals(obj.toString())))
            {
              if (object == null) {
                object = objectClass.newInstance();
              }
              m.invoke(object, new Object[] { obj });
            }
          }
          catch (Exception localException) {}
        }
      }
    }
  }
  
  public static byte[] serialize(Object object)
  {
    ObjectOutputStream oos = null;
    ByteArrayOutputStream baos = null;
    try
    {
      if (object != null)
      {
        baos = new ByteArrayOutputStream();
        oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        return baos.toByteArray();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static Object unserialize(byte[] bytes)
  {
    ByteArrayInputStream bais = null;
    try
    {
      if ((bytes != null) && (bytes.length > 0))
      {
        bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
