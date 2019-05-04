package com.cn.thinkx.oms.module.enterpriseorder.util;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.common.redis.core.JedisClusterUtils;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrderList;

public class PagePersonUtil {
	public static List<BatchOrderList> getPersonInfPageList(int startNum,int pageSize,LinkedList<BatchOrderList> personInfList){
		List<BatchOrderList> list=new LinkedList<BatchOrderList>();
		if(personInfList !=null && personInfList.size()>0){
			int startLimit=(startNum-1)*pageSize;
			int endLimit=startNum*pageSize-1;
			int total=personInfList.size();
			
			if(total<=endLimit){
				endLimit=total-1;
			}
			for(int i=startLimit;i<=endLimit;i++){
				list.add(personInfList.get(i));
			}
		}
		return list;
	}
	
	public static LinkedList<BatchOrderList> getRedisBatchOrderList(String bathOpen){
		String getData = JedisClusterUtils.getInstance().get(bathOpen); // 从缓存钟获取信息
		LinkedList<BatchOrderList> orderList = null;
		if (getData != null) {
			orderList = new LinkedList(JSONObject.parseArray(getData, BatchOrderList.class));
		}
		return orderList;
	}
}
