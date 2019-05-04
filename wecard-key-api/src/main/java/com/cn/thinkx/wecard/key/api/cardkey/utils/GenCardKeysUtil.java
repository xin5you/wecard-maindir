package com.cn.thinkx.wecard.key.api.cardkey.utils;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cn.thinkx.pms.base.utils.RandomUtils;

/**
 * 卡密生成器
 * 
 * @author pucker
 *
 */
public class GenCardKeysUtil {

	public static final Set<String> genCardKeys(final String machineId, final int nums) {
		Set<String> keyList = Stream.iterate(0, i -> i + 1).limit(nums).map(a -> {
			return RandomUtils.getOrderIdByUUId(machineId);
		}).collect(Collectors.toSet());
		return keyList;
	}
	
	public static void main(String[] args) {
		System.out.println(GenCardKeysUtil.genCardKeys("M", 5));
	}
}
