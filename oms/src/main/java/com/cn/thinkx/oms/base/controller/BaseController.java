package com.cn.thinkx.oms.base.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.NumberUtils;

public class BaseController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected String getParam(HttpServletRequest request, String name) {
		return request.getParameter(name);
	}

	protected int getPageNum(HttpServletRequest request) {
		int startNum = NumberUtils.parseInt(request.getParameter("iDisplayStart"), 1);
		int pageSize = getPageSize(request);
		int pageNum = (startNum >= pageSize) ? (startNum / pageSize + 1) : 1;
		return pageNum;
	}

	protected int getPageSize(HttpServletRequest request) {
		int pageSize = NumberUtils.parseInt(request.getParameter("iDisplayLength"), 1);
		return pageSize;
	}
	
	protected User getCurrUser(HttpServletRequest request){
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute(Constants.SESSION_USER);
		return user;
	}

	protected void writerJSON(HttpServletRequest request, HttpServletResponse response, Map<String, Object> dataMap) {
		try {
			response.setContentType("text/plain");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			PrintWriter out = response.getWriter();
			JSONObject resultJSON = new JSONObject(); // 根据需要拼装json
			resultJSON.putAll(dataMap);
			String jsonpCallback = request.getParameter("jsonpCallback");// 客户端请求参数
			out.println(jsonpCallback + "(" + resultJSON.toString() + ")");// 返回jsonp格式数据
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage(), e);
		}
	}

	protected int parseInt(String str, int defaultNum) {
		int num = 0;
		try {
			num = Integer.parseInt(str);
		} catch (Exception e) {
			// e.printStackTrace();
			num = defaultNum;
		}
		return num;
	}

	protected int parseInt(String str) {
		return parseInt(str, 0);
	}

	protected String parseString(String str) {
		if (str == null)
			return "";
		return str;
	}

	protected long parseLong(String str) {
		if (str == null)
			return 0;
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return 0;
		}
	}
}
