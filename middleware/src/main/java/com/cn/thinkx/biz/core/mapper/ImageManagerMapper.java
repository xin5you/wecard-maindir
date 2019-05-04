package com.cn.thinkx.biz.core.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.biz.core.model.ImageManager;

@Repository("imageManagerMapper")
public interface ImageManagerMapper {
	
	
	/**
	 * 根据条件获取有效状态图片地址
	 * @param req
	 * @return
	 */
	List<String> getImagesUrl(ImageManager req);
}
