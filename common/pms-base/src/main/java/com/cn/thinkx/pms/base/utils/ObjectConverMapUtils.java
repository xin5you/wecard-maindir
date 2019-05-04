package com.cn.thinkx.pms.base.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 转换成map对象工具
 * @author zqy
 *
 */
public class ObjectConverMapUtils {
	
	/**
	 * 根据传入对象转换成Map对象
	 * 
	 * @param obj
	 * @return
	 */
	public  Map<String, String> getMapValue(Object obj) {
		Map<String, String> map = new HashMap<String, String>();
		if (obj != null) {
			getProperty(map, obj, obj.getClass());// 将当前类属性及其值放入map
			if (obj.getClass().getSuperclass() !=null && obj.getClass().getGenericSuperclass() != null) {
				getProperty(map, obj, obj.getClass().getSuperclass());// 将当前类父类属性及其值放入map
				if ( obj.getClass().getSuperclass().getSuperclass() !=null && obj.getClass().getGenericSuperclass().getClass().getGenericSuperclass() != null)
					getProperty(map, obj, obj.getClass().getSuperclass().getSuperclass());// 将当前类父类属性及其值放入map
			}
		}
		return map;
	}

	public void getProperty(Map<String, String> map, Object obj, Class<?> clazz) {
		// 获取f对象对应类中的所有属性域 
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();
			// 过滤签名字段
			if ("serialVersionUID".equals(varName))
				continue;
			try {
				// 获取原来的访问控制权限
				boolean accessFlag = fields[i].isAccessible();
				// 修改访问控制权限
				fields[i].setAccessible(true);
				// 获取在对象f中属性fields[i]对应的对象中的变量
				Object o = fields[i].get(obj);
				if (o != null && !StringUtil.isNullOrEmpty(o.toString()))
					map.put(varName, o.toString());
				// 恢复访问控制权限
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
	}

}
