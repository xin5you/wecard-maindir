package com.cn.thinkx.oms.module.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.common.service.ImageManagerService;
import com.cn.thinkx.oms.util.StringUtils;



@Controller
@RequestMapping(value = "common/image")
public class ImageManageController extends BaseController {
	
	@Autowired
	@Qualifier("imageManagerService")
	private ImageManagerService imageManagerService;
	
	/**
	 * 删除图片
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteImgById")
	@ResponseBody
	public Map<String, Object> deleteImgById(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		try {
			String imgId=StringUtils.nullToString(req.getParameter("imgId"));
			imageManagerService.deleteImageManager(imgId);
			resultMap.put("status", Boolean.TRUE);
		} catch (Exception e) {
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "文件删除失败,请联系管理员");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}

}
