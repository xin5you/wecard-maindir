package com.cn.thinkx.oms.module.common.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cn.thinkx.oms.module.common.model.ImageManager;

/**
 * 图片文件
 * @author zqy
 *
 */
public interface ImageManagerService {

	ImageManager getImageManagerById(String id);

	int insertImageManager(ImageManager entity);
	int updateImageManager(ImageManager entity);
	int deleteImageManager(String id);
	
	/**
	 * 文件上传
	 * @param mchntCode
	 * @param application
	 * @param applicationId
	 * @param type
	 * @return
	 */
	void addUploadImange(String mchntCode,String application,String applicationId,String type,MultipartFile[] files);
	
	String addUploadImange(String mchntCode,String application, String type,MultipartFile files);
	
	/**
	 * 文件上传 修改图片
	 * @param mchntCode
	 * @param application
	 * @param applicationId
	 * @param type
	 * @param files
	 */
	void updateUploadImange(String mchntCode,String application,String applicationId,String type,MultipartFile[] files);
	
	/**
	 * 查询图片列表
	 * @param entity
	 * @return
	 */
	List<ImageManager> getImageManagerPathList(ImageManager entity);

}
