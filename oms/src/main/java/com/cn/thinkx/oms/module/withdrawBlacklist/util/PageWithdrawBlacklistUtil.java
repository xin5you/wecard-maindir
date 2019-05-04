package com.cn.thinkx.oms.module.withdrawBlacklist.util;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.common.redis.core.JedisClusterUtils;
import com.cn.thinkx.oms.module.withdrawBlacklist.model.WithdrawBlacklist;

public class PageWithdrawBlacklistUtil {
	public static List<WithdrawBlacklist> getWithdrawBlacklistInfPageList(int startNum, int pageSize,
			LinkedList<WithdrawBlacklist> wbInfList) {
		List<WithdrawBlacklist> list = new LinkedList<WithdrawBlacklist>();
		if (wbInfList != null && wbInfList.size() > 0) {
			int startLimit = (startNum - 1) * pageSize;
			int endLimit = startNum * pageSize - 1;
			int total = wbInfList.size();

			if (total <= endLimit) {
				endLimit = total - 1;
			}
			for (int i = startLimit; i <= endLimit; i++) {
				list.add(wbInfList.get(i));
			}
		}
		return list;
	}

	public static LinkedList<WithdrawBlacklist> getRedisWithdrawBlacklist(String bathOpen) {
		String getData = JedisClusterUtils.getInstance().get(bathOpen); // 从缓存中获取信息
		LinkedList<WithdrawBlacklist> wbList = null;
		if (getData != null) {
			wbList = new LinkedList(JSONObject.parseArray(getData, WithdrawBlacklist.class));
		}
		return wbList;
	}
}
