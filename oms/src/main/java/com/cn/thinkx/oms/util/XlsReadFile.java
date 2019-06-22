package com.cn.thinkx.oms.util;

import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrderList;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrderDetail;
import com.cn.thinkx.oms.module.sys.model.SMSMessage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;

public class XlsReadFile {
	
	private Logger logger = LoggerFactory.getLogger(XlsReadFile.class);

	/**
	 * 读取Excel数据
	 * 
	 * @param file
	 */
	public void readExcel(InputStream inputStream, String fileName, List<SMSMessage> pageList) {
		Workbook wb = null;
		try {
			if (fileName.endsWith("xls")) {
				wb = new HSSFWorkbook(inputStream);// 解析xls格式
			} else if (fileName.endsWith("xlsx")) {
				wb = new XSSFWorkbook(inputStream);// 解析xlsx格式
			}
			Sheet sheet = wb.getSheetAt(0);// 第一个工作表

			int firstRowIndex = sheet.getFirstRowNum();
			int lastRowIndex = sheet.getLastRowNum();
			SMSMessage smsMessage = new SMSMessage();
			for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
				Row row = sheet.getRow(rIndex);
				if (row != null) {
					smsMessage = new SMSMessage();
					smsMessage.setPhone(getValue(row.getCell(0)));
					smsMessage.setContent(getValue(row.getCell(1)));
					pageList.add(smsMessage);
				}
			}
			wb.close();
			inputStream.close();
		} catch (Exception e) {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e1) {
					logger.error("批量操作读取Excel发生异常：", e1);
				}
			}
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e1) {
					logger.error("批量操作读取Excel发生异常：", e1);
				}
			}
		}
	}

	/**
	 * 读取导入的订单Excel
	 * 
	 * @param inputStream
	 * @param fileName
	 * @param orderMap
	 */
	public ModelMap readOrderExcel(InputStream inputStream, String fileName, Map<String, BatchOrderList> orderMap, String batchType) {
		Workbook wb = null;
		ModelMap map = new ModelMap();
		map.addAttribute("status", Boolean.TRUE);
		try {
			if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
				map.addAttribute("status", Boolean.FALSE);
				map.addAttribute("msg", "上传文件格式不正确");
				return map;
			}
			if (fileName.endsWith("xls")) {
				wb = new HSSFWorkbook(inputStream);// 解析xls格式
			} else if (fileName.endsWith("xlsx")) {
				wb = new XSSFWorkbook(inputStream);// 解析xlsx格式
			}
			Sheet sheet = wb.getSheetAt(0);// 第一个工作表

			int lastRowIndex = sheet.getLastRowNum();
			BatchOrderList order = new BatchOrderList();
			for (int rIndex = 1; rIndex <= lastRowIndex; rIndex++) {
				Row row = sheet.getRow(rIndex);
				if (row != null) {
					order = new BatchOrderList();
					order.setPuid(UUID.randomUUID().toString().replace("-", ""));
					if (row.getCell(0) == null) {
						orderMap = null;
						map.addAttribute("status", Boolean.FALSE);
						map.addAttribute("msg", "上传文件格式不正确!!!");
						return map;
					}
					if (!getValue(row.getCell(0)).matches("^[a-zA-Z\u4e00-\u9fa5]+$")) {
						orderMap = null;
						map.addAttribute("status", Boolean.FALSE);
						map.addAttribute("msg", "第" + (rIndex + 1) + "行姓名错误!!!");
						return map;
					}

					order.setUserName(getValue(row.getCell(0)));
					if (!"".equals(getValue(row.getCell(1))) && getValue(row.getCell(1)) != null) {
						// if( getValue(row.getCell(1)).length()!=18 &&
						// getValue(row.getCell(1)).length()!=15){
						if (!"该身份证有效".equals(IDCardValidateUtils.IDCardValidate(getValue(row.getCell(1))).toUpperCase())) {
							orderMap = null;
							map.addAttribute("status", Boolean.FALSE);
							map.addAttribute("msg", "第" + (rIndex + 1) + "行身份证错误！"
									+ IDCardValidateUtils.IDCardValidate(getValue(row.getCell(1))).toUpperCase());
							return map;
						}
						// }
					}
					order.setUserCardNo(getValue(row.getCell(1)));

					if (row.getCell(2) == null) {
						orderMap = null;
						map.addAttribute("status", Boolean.FALSE);
						map.addAttribute("msg", "上传文件格式不正确!!!");
						return map;
					}
					if (!Pattern.compile("^1\\d{10}$").matcher(getValue(row.getCell(2))).find()) {
						orderMap = null;
						map.addAttribute("status", Boolean.FALSE);
						map.addAttribute("msg", "第" + (rIndex + 1) + "行手机号错误!!!");
						return map;
					}

					for (Iterator<String> it = orderMap.keySet().iterator(); it.hasNext();) {
						if (it.next().equals(getValue(row.getCell(2)))) {
							orderMap = null;
							map.addAttribute("status", Boolean.FALSE);
							map.addAttribute("msg", "第" + (rIndex + 1) + "行手机号重复!!!");
							return map;
						}
					}

					order.setPhoneNo(getValue(row.getCell(2)));
					if (batchType.equals("recharge")) {
						if (row.getCell(3) == null) {
							orderMap = null;
							map.addAttribute("status", Boolean.FALSE);
							map.addAttribute("msg", "上传文件格式不正确!!!");
							return map;
						}
						if (!Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$")
								.matcher(getValue(row.getCell(3))).find()) {
							// orderList = null;
							orderMap = null;
							map.addAttribute("status", Boolean.FALSE);
							map.addAttribute("msg", "第" + (rIndex + 1) + "行金额错误!!!");
							return map;
						}
						// order.setAmount(""+NumberUtils.formatMoney(NumberUtils.RMBYuanToCent(getValue(row.getCell(3)))));
						order.setAmount(getValue(row.getCell(3)));
					}
					// orderList.addLast(order);
					orderMap.put(order.getPhoneNo(), order);
				}
			}
			wb.close();
			inputStream.close();
		} catch (Exception e) {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e1) {
					logger.error("读取导入订单Excel发生异常：", e1);
				}
			}
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e1) {
					logger.error("读取导入订单Excel发生异常：", e1);
				}
			}
		}
		return map;
	}

	@SuppressWarnings("static-access")
	private static String getValue(Cell hssfCell) {
		DecimalFormat df = new DecimalFormat("#");
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			if (String.valueOf(hssfCell.getNumericCellValue()).matches("\\d+\\.\\d+$|-\\d+\\.\\d+$")) // 小数
				return String.valueOf(hssfCell.getNumericCellValue());
			return df.format(hssfCell.getNumericCellValue());
		} else {
			return String.valueOf(hssfCell.getStringCellValue()).trim();
		}
	}

	/**
	 * 读取导入的订单Excel
	 *
	 * @param inputStream
	 * @param fileName
	 * @param orderList
	 */
	public ModelMap readBatchWithDrawOrderDetailsExcel(InputStream inputStream, String fileName, List<BatchWithdrawOrderDetail> orderList) {

		Workbook wb = null;
		ModelMap map = new ModelMap();
		map.addAttribute("status", Boolean.TRUE);
		try {
			if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
				map.addAttribute("status", Boolean.FALSE);
				map.addAttribute("msg", "上传文件格式不正确");
				return map;
			}
			if (fileName.endsWith("xls")) {
				wb = new HSSFWorkbook(inputStream);// 解析xls格式
			} else if (fileName.endsWith("xlsx")) {
				wb = new XSSFWorkbook(inputStream);// 解析xlsx格式
			}
			Sheet sheet = wb.getSheetAt(0);// 第一个工作表

			int lastRowIndex = sheet.getLastRowNum();
			BatchWithdrawOrderDetail order = new BatchWithdrawOrderDetail();
			for (int rIndex = 1; rIndex <= lastRowIndex; rIndex++) {
				Row row = sheet.getRow(rIndex);
				if (row != null) {
					order = new BatchWithdrawOrderDetail();
					order.setDetailId(UUID.randomUUID().toString().replace("-", ""));
					//收款人姓名
					order.setReceiverName(getValue(row.getCell(0)));
					//收款人银行卡号
					order.setReceiverCardNo(getValue(row.getCell(1)));
					//银行名称
					order.setBankName(getValue(row.getCell(2)));
					//充值金额
					order.setAmount(new BigDecimal(getValue(row.getCell(3))));

					order.setBankProvince(getValue(row.getCell(4)));
					order.setBankCity(getValue(row.getCell(5)));
					order.setRemarks(getValue(row.getCell(6)));
					order.setPayeeBankLinesNo(getValue(row.getCell(7)));
					orderList.add(order);
				}
			}
			wb.close();
			inputStream.close();
		} catch (Exception e) {
			map.addAttribute("status", Boolean.FALSE);
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e1) {
					logger.error("读取代付订单Excel发生异常：", e1);
				}
			}
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e1) {

					logger.error("读取代付订单Excel发生异常：", e1);
				}
			}
		}
		return map;
	}
	public static void main(String[] args) {

	}
}