package com.cn.thinkx.oms.module.common.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.common.model.ImageManager;

@Repository("imageManagerMapper")
public interface ImageManagerMapper {


	ImageManager getImageManagerById(String id);
	int insertImageManager(ImageManager entity);
	int updateImageManager(ImageManager entity);
	int deleteImageManager(String id);
	
	List<ImageManager> getImageManagerList(ImageManager entity);
	
	/**
	 * 删除某个种类的图片
	 * @param entity
	 * @return
	 */
	int deleteImageManagerByType(ImageManager entity);
}