package com.cn.thinkx.common.wecard.module.image.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.common.wecard.domain.image.ImageManager;

@Repository("imageManagerMapper")
public interface ImageManagerMapper {

	/**
	 * 根据条件获取有效状态图片地址
	 * @param req
	 * @return
	 */
	List<String> getImagesUrl(ImageManager req);

}