package com.cn.iboot.diy.api.base.utils;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class UploadUtil {

	public static void upLoad(HSSFWorkbook workBook,String title,HttpServletResponse response) throws IOException{
		OutputStream out = response.getOutputStream();
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename = "+ new String((title + ".xls").getBytes(), "iso-8859-1"));
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		workBook.write(out);
		out.close();
	}
}
