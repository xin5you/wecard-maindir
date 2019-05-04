package com.cn.thinkx.wecard.customer.module.pub.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.domain.image.ImageManager;
import com.cn.thinkx.common.wecard.module.image.mapper.ImageManagerMapper;
import com.cn.thinkx.wecard.customer.module.pub.service.ImageManagerService;

@Service("imageManagerService")
public class ImageManagerServiceImpl implements ImageManagerService {

	@Autowired
	@Qualifier("imageManagerMapper")
	private  ImageManagerMapper imageManagerMapper;
	
	/**
	 * 根据条件获取有效状态图片地址
	 * @param req
	 * @return
	 */
	public List<String> getImagesUrl(ImageManager req){
		return imageManagerMapper.getImagesUrl(req);
	}
}
