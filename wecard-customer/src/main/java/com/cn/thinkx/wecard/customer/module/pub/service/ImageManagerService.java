package com.cn.thinkx.wecard.customer.module.pub.service;

import java.util.List;

import com.cn.thinkx.common.wecard.domain.image.ImageManager;

public interface ImageManagerService {

	
	/**
	 * 根据条件获取有效状态图片地址
	 * @param req
	 * @return
	 */
	List<String> getImagesUrl(ImageManager req);
}
