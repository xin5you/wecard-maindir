package com.cn.thinkx.oms.module.sys.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.sys.model.SMSMessage;
import com.cn.thinkx.oms.util.XlsReadFile;
import com.cn.thinkx.pms.base.service.MessageService;


@Controller
@RequestMapping(value = "sys/sms")
public class SMSMessageController extends BaseController {
	
	Logger logger = LoggerFactory.getLogger(SMSMessageController.class);
	
	@Autowired
	@Qualifier("messageService")
	private  MessageService messageService;
	
	@RequestMapping(value = "/listSmsMessage")
	public ModelAndView listUser(HttpServletRequest req, HttpServletResponse response,
			@RequestParam(value = "file", required = false)MultipartFile multipartFile) {
		ModelAndView mv = new ModelAndView("sys/sms/listSmsMessage");
		
		List<SMSMessage> pageList = new ArrayList<SMSMessage>();
		try {
			CommonsMultipartFile cf=(CommonsMultipartFile) multipartFile;
			if(cf !=null && cf.getSize()>0){
				DiskFileItem fi = (DiskFileItem) cf.getFileItem();
				File file=fi.getStoreLocation();
				XlsReadFile xls=new XlsReadFile();
				InputStream inputStream = new FileInputStream(file);
				xls.readExcel(inputStream,multipartFile.getOriginalFilename(),pageList); //讀取xls
				
				if(pageList !=null && pageList.size()>0){
					for(int i=0;i<pageList.size();i++){
						boolean flag=messageService.sendMessage(pageList.get(i).getPhone(), pageList.get(i).getContent(), "");
						pageList.get(i).setFlag(flag);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发送短信模板出现", e);
		}
		mv.addObject("pageInfo", pageList);
	
		return mv;
	}
	
	
}
