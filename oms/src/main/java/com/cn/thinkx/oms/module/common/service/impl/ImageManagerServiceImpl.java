package com.cn.thinkx.oms.module.common.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cn.thinkx.common.redis.util.RedisDictProperties;
import com.cn.thinkx.oms.module.common.mapper.ImageManagerMapper;
import com.cn.thinkx.oms.module.common.model.ImageManager;
import com.cn.thinkx.oms.module.common.service.ImageManagerService;
import com.cn.thinkx.oms.util.FTPUtils;
import com.cn.thinkx.oms.util.FileUtils;
import com.cn.thinkx.oms.util.StringUtils;



@Service("imageManagerService")
public class ImageManagerServiceImpl implements ImageManagerService {

	@Autowired
	@Qualifier("imageManagerMapper")
	private ImageManagerMapper imageManagerMapper;

	public 	ImageManager getImageManagerById(String id){
		return imageManagerMapper.getImageManagerById(id);
	}
	@Override
	public int insertImageManager(ImageManager entity) {
		
		return imageManagerMapper.insertImageManager(entity);
	}

	@Override
	public int updateImageManager(ImageManager entity) {
		return imageManagerMapper.updateImageManager(entity);
	}

	@Override
	public int deleteImageManager(String id) {
		
		int oper=0;
		String imgrootPath=RedisDictProperties.getInstance().getdictValueByCode("OMS_FILE_UPLAOD_PATH");
		ImageManager img=this.getImageManagerById(id);
		
		 FTPUtils ftpUtil=new FTPUtils();
		 FTPClient ftpClient=ftpUtil.getFTPClient();
		 //ftpClient.enterLocalActiveMode();    //主动模式
	     ftpClient.enterLocalPassiveMode();     //被动模式
		 boolean flag=ftpUtil.deleteFile(ftpClient,imgrootPath+img.getImageUrl());
		 ftpUtil.ftpCloseConnect(ftpClient);
		 if(flag){
			 oper=imageManagerMapper.deleteImageManager(id);
		 }
	    return oper;
	}

	
	/**
	 * 文件上传
	 * @param mchntCode
	 * @param application
	 * @param applicationId
	 * @param type
	 * @return
	 */
	public void addUploadImange(String mchntCode,String application,String applicationId,String type,MultipartFile[] files){
		if(files !=null){
			String imgrootPath=RedisDictProperties.getInstance().getdictValueByCode("OMS_FILE_UPLAOD_PATH");

			String separator=File.separator;
			StringBuffer sbf=new StringBuffer(File.separator);
			if(!StringUtils.isNullOrEmpty(mchntCode)){
				sbf.append(mchntCode).append(separator);
			}
			if(!StringUtils.isNullOrEmpty(application)){
				sbf.append(application).append(separator);
			}
			if(!StringUtils.isNullOrEmpty(applicationId) && ! "10".equals(application)){
				sbf.append(applicationId).append(separator);
			}
			if(!StringUtils.isNullOrEmpty(type)){
				sbf.append(type).append(separator);
			}
			
			String realpath=imgrootPath+sbf.toString();
	
			ImageManager imgManager=null;
			try{
				CommonsMultipartFile cf=null;
				DiskFileItem fi=null;
				File f=null;
				String newFilename;
				 //打开ftp连接
				 FTPUtils ftpUtil=new FTPUtils();
				 FTPClient ftpClient=ftpUtil.getFTPClient();
				 //ftpClient.enterLocalActiveMode();    //主动模式
				 ftpClient.enterLocalPassiveMode();     //被动模式
				for(int i=0;i<files.length;i++){
					cf = (CommonsMultipartFile) files[i];
					if(cf.getSize()>0){
					fi = (DiskFileItem) cf.getFileItem();
					f = fi.getStoreLocation();
					InputStream inputStream = new FileInputStream(f);
					newFilename=FileUtils.getNewFileName(files[i].getOriginalFilename());
					boolean flag=ftpUtil.uploadFile(ftpClient, realpath, newFilename, inputStream);
					if(flag){
						imgManager=new ImageManager();
						imgManager.setApplication(application);
						imgManager.setApplicationId(applicationId);
						imgManager.setApplicationType(type);
						imgManager.setImageUrl(sbf.toString()+newFilename);
						imgManager.setDataStat("0");
						this.insertImageManager(imgManager);
					}
					}
				}
				ftpUtil.ftpCloseConnect(ftpClient); //关闭ftp连接
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * 上传文件 返回 文件名
	 * @param mchntCode
	 * @param application
	 * @param applicationId
	 * @param type
	 * @param files
	 * @return
	 */
	public String addUploadImange(String mchntCode,String application,String type,MultipartFile files){
		if(files !=null){
			String imgrootPath=RedisDictProperties.getInstance().getdictValueByCode("OMS_FILE_UPLAOD_PATH");

			String separator=File.separator;
			StringBuffer sbf=new StringBuffer(separator);
			if(!StringUtils.isNullOrEmpty(mchntCode)){
				sbf.append(mchntCode).append(separator);
			}
			if(!StringUtils.isNullOrEmpty(application)){
				sbf.append(application).append(separator);
			}
			if(!StringUtils.isNullOrEmpty(type)){
				sbf.append(type).append(separator);
			}
			
			String realpath=imgrootPath+sbf.toString();
	
			try{
				CommonsMultipartFile cf=null;
				DiskFileItem fi=null;
				File f=null;
				String newFilename;
				 //打开ftp连接
				 FTPUtils ftpUtil=new FTPUtils();
				 FTPClient ftpClient=ftpUtil.getFTPClient();
				 //ftpClient.enterLocalActiveMode();    //主动模式
				 ftpClient.enterLocalPassiveMode();     //被动模式
				 cf = (CommonsMultipartFile) files;
				 if(cf.getSize()>0){
					fi = (DiskFileItem) cf.getFileItem();
					f = fi.getStoreLocation();
					InputStream inputStream = new FileInputStream(f);
					newFilename=FileUtils.getNewFileName(files.getOriginalFilename());
					boolean flag=ftpUtil.uploadFile(ftpClient, realpath, newFilename, inputStream);
					
					if(flag){
						return sbf.toString()+newFilename;
					}
				 }
				ftpUtil.ftpCloseConnect(ftpClient); //关闭ftp连接
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	

	/**
	 * 删除商户下图片
	 */
	public void updateUploadImange(String mchntCode, String application, String applicationId, String applicationType,
			MultipartFile[] files) {
		if(files!=null && files.length>0 && !StringUtils.isNullOrEmpty(application) && !StringUtils.isNullOrEmpty(applicationId) && !StringUtils.isNullOrEmpty(applicationType)){
			String imgrootPath=RedisDictProperties.getInstance().getdictValueByCode("OMS_FILE_UPLAOD_PATH");
			if(files !=null && files[0].getSize()>0){
				FTPUtils ftpUtil=new FTPUtils();
				FTPClient ftpClient=ftpUtil.getFTPClient();
				//ftpClient.enterLocalActiveMode();    //主动模式
				ftpClient.enterLocalPassiveMode();     //被动模式
				ImageManager imgManager=new ImageManager();
				imgManager.setApplication(application);
				imgManager.setApplicationType(applicationType);
				imgManager.setApplicationId(applicationId);
				List<ImageManager> list=imageManagerMapper.getImageManagerList(imgManager);
				if(list !=null && list.size()>0){
					for(int i=0;i<list.size();i++){
						ftpUtil.deleteFile(ftpClient,imgrootPath+list.get(i).getImageUrl());
					}
				}
				 ftpUtil.ftpCloseConnect(ftpClient);
				//删除数据库数据
				imageManagerMapper.deleteImageManagerByType(imgManager);
				//重新上传文件
				this.addUploadImange(mchntCode, application, applicationId, applicationType, files);
			}
		}
	}
	
	/**
	 * 查询图片列表
	 * @param entity
	 * @return
	 */
	public List<ImageManager> getImageManagerPathList(ImageManager imgManager){
		List<ImageManager> list=imageManagerMapper.getImageManagerList(imgManager);
		
		if(list !=null){
			String imgrootPath="";
			imgrootPath=RedisDictProperties.getInstance().getdictValueByCode("HKB_URL_IMG");
			for(int i=0;i<list.size();i++){
				if(list.get(i) !=null){
					list.get(i).setImageUrl(imgrootPath+list.get(i).getImageUrl());
				}
			}
		}
		return list;
	}
}
