package com.cn.thinkx.oms.module.common.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties({"bytes"}) 
public class FileMeta  {


	private static final long serialVersionUID = 1L;
	
	
	private String fileName;
	private long fileSize;
	private String fileType;
	private String errorInfo;  
	private byte[] bytes;
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public long getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	public String getFileType() {
		return fileType;
	}
	
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public byte[] getBytes() {
		return bytes;
	}
	
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
	
	public String getErrorInfo() {
		return errorInfo;
	}
	
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	  
          
}