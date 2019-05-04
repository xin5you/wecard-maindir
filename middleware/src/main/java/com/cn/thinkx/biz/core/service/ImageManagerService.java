package com.cn.thinkx.biz.core.service;

import java.util.List;

import com.cn.thinkx.biz.core.model.ImageManager;

public interface ImageManagerService {

	/**
	 * 根据条件获取有效状态图片地址
	 * @param req
	 * @return
	 */
	List<String> getImagesUrl(ImageManager req);
}
