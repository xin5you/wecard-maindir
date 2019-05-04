package com.cn.thinkx.oms.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileUtils {
	
	/**
	 * 获取指定路径下文件名称
	 * @param realpath
	 * @return
	 */
	public static String getNewFileName(String fileName){
		String newPathName ="";
		
		String[] typechoose = fileName.split("\\.");
		int ichoose = typechoose.length;
		String type = ichoose > 1 ? typechoose[ichoose - 1] : "";
		String t=type.toLowerCase();
		if (t.equals("jpg") || t.equals("gif")
				|| t.equals("jpeg") || t.equals("png")) {
			String newfilname = FileUtils.getUUID() + "." + type.toLowerCase();
			newPathName= newfilname;
		}
		return newPathName;
	}
	/**
	 * 删除文件
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(String filePath){
		boolean res = false;
		File f = new File(filePath); // 输入要删除的文件位置
		if(f.exists()){
			res=f.delete();
		}
		return res;
	}
	
	/**
	 * 文件上传
	 * @param savePath
	 * @param myfile
	 * @return
	 */
	public static int saveFile(String savePath, File myfile) {
		int res = 0;
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(myfile);
			out = new FileOutputStream(savePath);
			int readed = 0;
			byte[] buffer = new byte[1024];

			while ((readed = in.read(buffer, 0, 1024)) != -1) {
				out.write(buffer, 0, readed);
			}
			out.flush();
			out.close();
			in.close();
			res = 1;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(out !=null){
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			if(in !=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		return res;
	}

	/**
	 * 图片转换为流对象
	 * @param file
	 * @return
	 */
	public static byte[] image2byte(File file) {
		byte[] data = null;
		FileImageInputStream input = null;
		try {
			input = new FileImageInputStream(file);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int numBytesRead = 0;
			while ((numBytesRead = input.read(buf)) != -1) {
				output.write(buf, 0, numBytesRead);
			}
			data = output.toByteArray();
			output.close();
			input.close();
		} catch (FileNotFoundException ex1) {
			ex1.printStackTrace();
		} catch (IOException ex1) {
			ex1.printStackTrace();
		}
		return data;
	}

	/**
	 * 判断当前文件是图片
	 * @param file
	 * @return
	 */
	public static boolean isImage(MultipartFile myfile) {
		boolean flag = false;
		ImageInputStream iis;
		
		CommonsMultipartFile cf = (CommonsMultipartFile) myfile;
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();
		File file = fi.getStoreLocation();
		
		try {
			iis = ImageIO.createImageInputStream(file);
			if(iis !=null){
				Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
				if (iter.hasNext()) {//文件不是图片
					flag= true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	
	public static boolean checkFileType(MultipartFile mpf){
		boolean flag=false;
		String[] typechoose = mpf.getOriginalFilename().split("\\.");
		int ichoose = typechoose.length;
		String type = ichoose > 1 ? typechoose[ichoose - 1] : "";
		if (type.toLowerCase().equals("jpg") || type.toLowerCase().equals("gif")
				|| type.toLowerCase().equals("jpeg") || type.toLowerCase().equals("png")|| type.toLowerCase().equals("bmp")) {
			flag=true;
		}
		
		return flag;
	}
	

	public static void main(String[] args) throws Exception {
		System.out.println("===========os.name:"+System.getProperties().getProperty("os.name"));  
//		boolean st=FileUtils.deleteFile("C:\\360安全浏览器下载\\Oracle Sdo_geometry坐标转换.docx");
//		System.out.println(st);
//		String realpath="c:\\img\\test\\mechantcode\\shop";
//		String newFilename=FileUtils.getNewFileName(realpath,file.getName());
//		System.out.println(newFilename.substring(realpath.length()+1));
//		System.out.println(newFilename);
//		
//		if(!StringUtils.isNullOrEmpty(newFilename)){
//			int a=FileUtils.saveFile(newFilename,file);
//			System.out.println(a);
//		}else{
//			System.out.println("不能上传非图片格式的文件");
//		}
		
	}
}
