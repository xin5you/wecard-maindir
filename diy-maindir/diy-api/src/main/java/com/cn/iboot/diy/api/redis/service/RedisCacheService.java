package com.cn.iboot.diy.api.redis.service;

/** 
 * 缓存存储接口 
 * @param <K> key 
 * @param <V> value 
 * @author 
 */  
public interface RedisCacheService<String,V>{
    
    /** 
     * 设置哈希类型数据到redis 数据库 
     * @param cacheKey  map.key
     * @param key   表字段 
     * @param value   
     * @return 
     */  
	void hset(String cacheKey,String key,V value);
	
	
    /** 
     * 获取哈希表数据类型的值 <V>
     * @param cacheKey 
     * @param key 
     * @return 
     */  
	V hgetVO(String cacheKey,String key ,Class<V> classv);
	
	/** 
     * 设置哈希类型数据到redis 数据库 
     * @param cacheKey  map.key
     * @param key   
     * @param value   
     * @return 
     */  
	void hsetString(String cacheKey,String key,String value);

    
    /** 
     * 获取哈希表数据类型的值 
     * @param cacheKey 
     * @param key 
     * @return 
     */  
	String hget(String cacheKey,String key);
	
	
	/**
	 * 删除哈希表数据类型的值 
	 * @param cacheKey
	 * @param key
	 */
	void hdel(String cacheKey,String key);
}