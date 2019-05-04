package com.cn.thinkx.oms.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtil<T> {
	
	/**
	 *  导出Excel 表 
	 * @param title         表格标题名  
	 * @param headers   表格属性列名数组
	 * @param dataset    需要显示的数据集合
	 * @param out          与输出设备关联的流对象
	 */
	public void exportExcel(String title, String[] headers,Collection<T> dataset,OutputStream out){
		HSSFWorkbook workbook = new HSSFWorkbook();  
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title);  
        // 设置表格默认列宽度为15个字节  
        sheet.setDefaultColumnWidth((short) 15); 
        // 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式  
//        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
//        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        // 生成一个字体  
        HSSFFont font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setColor(HSSFColor.RED.index);  
        font.setFontHeightInPoints((short) 12);  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        // 把字体应用到当前的样式  
        style.setFont(font);  
        // 生成并设置另一个样式  
        HSSFCellStyle style2 = workbook.createCellStyle();  
//        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);  
//        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        // 生成另一个字体  
        HSSFFont font2 = workbook.createFont();  
        font2.setFontName("微软雅黑");
        font2.setColor(HSSFColor.BLACK.index);
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
        // 把字体应用到当前的样式  
        style2.setFont(font2);   
  
        // 产生表格标题行  
        HSSFRow row = sheet.createRow(0);  
        for (int i = 0; i < headers.length; i++){  
            HSSFCell cell = row.createCell(i);  
            cell.setCellStyle(style);  
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
            cell.setCellValue(text);  
        } 
        
        Iterator<T> it = dataset.iterator();  
        int index = 0;  
        while (it.hasNext()){
        	index++;  
            row = sheet.createRow(index);  
            T t = (T) it.next();  
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
            Field[] fields = t.getClass().getDeclaredFields();  
            for (int i = 0; i < fields.length; i++){
            	HSSFCell cell = row.createCell(i);  
                cell.setCellStyle(style2);  
                Field field = fields[i];  
                String fieldName = field.getName();  
                String getMethodName = "get"  
                        + fieldName.substring(0, 1).toUpperCase()  
                        + fieldName.substring(1); 
                try{
	                Class tCls = t.getClass();  
	                Method getMethod = tCls.getMethod(getMethodName, new Class[]{});  
	                Object value = getMethod.invoke(t, new Object[]{});  
	                // 判断值的类型后进行强制类型转换  
	                String textValue = value.toString();
	                HSSFRichTextString richString = new HSSFRichTextString( textValue);
                    cell.setCellValue(richString);
                } catch(Exception e){
                	e.printStackTrace();
                }finally {
					
				}
            }
        }
        try {
        	out.flush();
			workbook.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public HSSFWorkbook exportExcel(String title,String titlerow,String startTime,String endTime, String[] titlehead, String[] titleheadnum,
            String[] head,Collection<T> dataset,Class c,Object obj){
		HSSFWorkbook workbook = new HSSFWorkbook();  
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title);  
        // 设置表格默认列宽度为15个字节  
        sheet.setDefaultColumnWidth((short) 15); 
        // 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式  
        style.setWrapText(true);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  //垂直居中
        // 生成一个字体  
        HSSFFont font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setColor(HSSFColor.BLACK.index);  
        font.setFontHeightInPoints((short) 12);  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        // 把字体应用到当前的样式  
        style.setFont(font);  
        // 生成并设置另一个样式  
        HSSFCellStyle style2 = workbook.createCellStyle();  
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  //下边框
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);       //左边框
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);     //右边框
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);       //上边框
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中 
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  //垂直居中
        // 生成另一个字体  
        HSSFFont font2 = workbook.createFont();  
        font2.setFontName("微软雅黑");                // 字体样式
        font2.setColor(HSSFColor.BLACK.index);   //  字体颜色
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  //  粗体
        // 把字体应用到当前的样式  
        style2.setFont(font2);   
        
        int rowno =0;       
        int endColl = c.getDeclaredFields().length-1;
        
        HSSFRow titleRow=sheet.createRow(rowno);  
	    HSSFCell titleCell =titleRow.createCell(0);  
	    titleCell.setCellValue(new HSSFRichTextString(titlerow));
	    sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,0,endColl));  
	    setRegionStyle(sheet, new CellRangeAddress(rowno,rowno,0,endColl), style);
	    rowno++;
	    if(endColl<3){
		    HSSFRow startTimeRow=sheet.createRow(rowno);
		    HSSFCell startTimeCell=startTimeRow.createCell(0);  
		    startTimeCell.setCellStyle(style);  
		    startTimeCell.setCellValue(new HSSFRichTextString("开始时间"));
		    HSSFCell startTimeContent=startTimeRow.createCell(1);  
		    startTimeContent.setCellStyle(style);
		    startTimeContent.setCellValue(new HSSFRichTextString(startTime));
		    if(endColl>1){
		    	sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,1,endColl)); 
		    	setRegionStyle(sheet, new CellRangeAddress(rowno,rowno,1,endColl), style);
		    }
		    rowno++;
		    HSSFRow endTimeRow=sheet.createRow(rowno);  
		    HSSFCell endTimeCell=endTimeRow.createCell(0);  
		    endTimeCell.setCellStyle(style); 
		    endTimeCell.setCellValue(new HSSFRichTextString("结束时间"));
		    HSSFCell endTimeContent=endTimeRow.createCell(1);  
		    endTimeContent.setCellStyle(style);
		    endTimeContent.setCellValue(new HSSFRichTextString(endTime));
		    if(endColl>1){
		    	sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,1,endColl)); 
		    	setRegionStyle(sheet, new CellRangeAddress(rowno,rowno,1,endColl), style);
		    }
		    rowno++;
	    }else{
	    	HSSFRow startTimeRow=sheet.createRow(rowno);  
		    HSSFCell startTimeCell=startTimeRow.createCell(0);  
		    startTimeCell.setCellStyle(style);  
		    startTimeCell.setCellValue(new HSSFRichTextString("开始时间"));
		    HSSFCell startTimeContent=startTimeRow.createCell(1);  
//		    startTimeContent.setCellStyle(style);  
		    startTimeContent.setCellValue(new HSSFRichTextString(startTime));
		    sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,1,endColl/2)); 
		    setRegionStyle(sheet, new CellRangeAddress(rowno,rowno,1,endColl/2), style);
//		    startTimeContent.setCellStyle(style);  
		    HSSFCell endTimeCell=startTimeRow.createCell(endColl/2+1);  
		    endTimeCell.setCellStyle(style);  
		    endTimeCell.setCellValue(new HSSFRichTextString("结束时间"));
		    HSSFCell endTimeContent=startTimeRow.createCell(endColl/2+2);  
//		    endTimeContent.setCellStyle(style);  
		    endTimeContent.setCellValue(new HSSFRichTextString(endTime));
		    sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,endColl/2+2,endColl)); 
		    setRegionStyle(sheet, new CellRangeAddress(rowno,rowno,endColl/2+2,endColl), style);
		    rowno++;
	    }
        // 产生表格标题行  
        HSSFRow row = sheet.createRow(rowno);  
        for (int i = 0; i < titlehead.length; i++){  
            HSSFCell cell = row.createCell(i);  
            cell.setCellStyle(style);  
            HSSFRichTextString text = new HSSFRichTextString(titlehead[i]);  
            cell.setCellValue(text);  
        } 
        rowno++;
        if(titleheadnum!=null){
	        for (int i = 0; i < titleheadnum.length; i++) {
	            String[] temp = titleheadnum[i].split(",");
	            Integer startrow = Integer.parseInt(temp[0]);
	            Integer overrow = Integer.parseInt(temp[1]);
	            Integer startcol = Integer.parseInt(temp[2]);
	            Integer overcol = Integer.parseInt(temp[3]);
	            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow,
	                    startcol, overcol));
	        }
        }
        if(head!=null){
	        row = sheet.createRow(rowno); 
	        for (int i = 0; i < titlehead.length; i++){  
	            HSSFCell cell = row.createCell(i);  
	            cell.setCellStyle(style);  
	            for (int j = 0; j < head.length; j++) {
	                cell = row.createCell(j + 1);
	                cell.setCellValue(head[j]);
	                cell.setCellStyle(style);      
	            } 
	            
	        } 
	        rowno++;
        }
        if(dataset!=null){
	        Iterator<T> it = dataset.iterator();  
	        int index = rowno;  
	        while (it.hasNext()){
	            row = sheet.createRow(index);  
	            index++;  
	            T t = (T) it.next();  
	            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
	            Field[] fields = t.getClass().getDeclaredFields();  
	            for (int i = 0; i < fields.length; i++){
	            	HSSFCell cell = row.createCell(i);  
	        		cell.setCellStyle(style2);  
	                Field field = fields[i];  
	                String fieldName = field.getName();  
	                String getMethodName = "get"  
	                        + fieldName.substring(0, 1).toUpperCase()  
	                        + fieldName.substring(1); 
	                try{
		                Class tCls = t.getClass();  
		                Method getMethod = tCls.getMethod(getMethodName, new Class[]{});  
		                Object value = getMethod.invoke(t, new Object[]{});  
		                // 判断值的类型后进行强制类型转换 
		                String textValue = "";
		                if(value != null){
		                	textValue = value.toString();
		                }
		                HSSFRichTextString richString = new HSSFRichTextString( textValue);
	                    cell.setCellValue(richString);
	                } catch(Exception e){
	                	e.printStackTrace();
	                }finally {
						
					}
	            }
	        }
	        
	        if(obj!=null){
	        	HSSFRow rowAmount = sheet.createRow(index);  
	        	 Field[] fields = obj.getClass().getDeclaredFields();  
	        	 for (int i = 0; i < fields.length; i++){
	             	HSSFCell cellAmount = rowAmount.createCell(i);  
	             	if(i==0){
	             		cellAmount.setCellStyle(style);  
	             	}else{
	             		cellAmount.setCellStyle(style2);  
	             	}
	             	Field field = fields[i];  
	                 String fieldName = field.getName();  
	                 String getMethodName = "get"  
	                         + fieldName.substring(0, 1).toUpperCase()  
	                         + fieldName.substring(1); 
	                 try{
	 	                Class tCls = obj.getClass();  
	 	                Method getMethod = tCls.getMethod(getMethodName, new Class[]{});  
	 	                Object value = getMethod.invoke(obj, new Object[]{});  
	 	                // 判断值的类型后进行强制类型转换  
	 	                String textValue = value.toString();
	 	                HSSFRichTextString richString = new HSSFRichTextString( textValue);
	 	               cellAmount.setCellValue(richString);
	                 } catch(Exception e){
	                 	e.printStackTrace();
	                 }finally {
	 					
	 				}
	             }
	        }
        }
        return workbook;
	}

	public static void setRegionStyle(HSSFSheet sheet, CellRangeAddress region,
            HSSFCellStyle cs) {
        for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null)
                row = sheet.createRow(i);
            for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
                HSSFCell cell = row.getCell(j);
                if (cell == null) {
                    cell = row.createCell(j);
                    cell.setCellValue("");
                }
                cell.setCellStyle(cs);
            }
        }
    }
}
