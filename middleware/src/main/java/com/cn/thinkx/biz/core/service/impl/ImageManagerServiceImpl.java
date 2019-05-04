package com.cn.thinkx.biz.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.biz.core.mapper.ImageManagerMapper;
import com.cn.thinkx.biz.core.model.ImageManager;
import com.cn.thinkx.biz.core.service.ImageManagerService;

@Service("imageManagerService")
public class ImageManagerServiceImpl implements ImageManagerService {

	@Autowired
	@Qualifier("imageManagerMapper")
	private ImageManagerMapper imageManagerMapper;



	@Override
	public List<String> getImagesUrl(ImageManager req) {
		return imageManagerMapper.getImagesUrl(req);
	}

}
