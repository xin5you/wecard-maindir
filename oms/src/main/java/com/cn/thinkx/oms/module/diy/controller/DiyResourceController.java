package com.cn.thinkx.oms.module.diy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.diy.model.DiyResource;
import com.cn.thinkx.oms.module.diy.service.DiyResourceService;
import com.cn.thinkx.oms.module.map.controller.MapController;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.StringUtils;

@Controller
@RequestMapping(value="diy/diyResource")
public class DiyResourceController extends BaseController{
	
	Logger logger = LoggerFactory.getLogger(DiyResourceController.class);
	
	@Autowired
	@Qualifier("diyResourceService")
	private DiyResourceService diyResourceService;

	/**
	 * 查询资源列表
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listDiyResource")
	public ModelAndView listDiyResource(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("diy/diyResource/listDiyResource");
		String operStatus=StringUtils.nullToString(req.getParameter("operStatus"));
		
		List<DiyResource> pageList = null;
		try {
			pageList=diyResourceService.getDiyResourceList(new DiyResource());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表信息出错", e);
		}
		mv.addObject("pageInfo", pageList);
		mv.addObject("operStatus", operStatus);
		return mv;
	}
	
	/**
	 * 进入到新增资源
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoAddDiyResource")
	public ModelAndView intoAddDiyResource(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("diy/diyResource/addDiyResource");
		DiyResource DiyResource=new DiyResource();
		DiyResource.setResourceType("0");	//0是菜单   1是功能
		List<DiyResource>  diyResourceList=diyResourceService.getDiyResourceList(DiyResource);
		mv.addObject("diyResourceList", diyResourceList);
		
		String resourceId=StringUtils.nullToString(req.getParameter("resourceId"));
		DiyResource parantRes=diyResourceService.getDiyResourceById(resourceId);
		mv.addObject("parantRes", parantRes);
		
		return mv;
	}
	
	/**
	 * 资源添加提交
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addDiyResourceCommit")
	@ResponseBody
	public Map<String, Object> addDiyResourceCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("status", Boolean.TRUE);
		try {
			User user = getCurrUser(req);
			DiyResource diyResource=getDiyResourceInfo(req,user);
			diyResource.setCreateUser(user.getId().toString());
			diyResource.setDataStat("0");
			DiyResource checkResource=diyResourceService.getDiyResourceByKey(diyResource.getKey());
			if(checkResource !=null){
				resultMap.put("status", Boolean.FALSE);
				resultMap.put("msg", checkResource.getKey()+"已经存在，请重新输入");
				return resultMap;
			}
			diyResourceService.insertDiyResource(diyResource);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "新增资源失败，请重新添加");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}
	
	/**
	 * 进入编辑资源页面
	 * 
	 * @param req
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/intoEditDiyResource")
	public ModelAndView intoEditDiyResource(HttpServletRequest req, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("diy/diyResource/editDiyResource");
		String resourceId=req.getParameter("resourceId");
		DiyResource diyResource=diyResourceService.getDiyResourceById(resourceId);
		
		//查找上级菜单列表
		DiyResource diyResource1=new DiyResource();
		diyResource1.setResourceType("0");
		List<DiyResource>  diyResourceList=diyResourceService.getDiyResourceList(diyResource1);
		
		mv.addObject("diyResourceList", diyResourceList);
		mv.addObject("diyResource", diyResource);
		return mv;
	}
	
	
	/**
	 * 资源编辑 提交
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editDiyResourceCommit")
	@ResponseBody
	public Map<String, Object> editDiyResourceCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		try {
			User user = getCurrUser(req);
			DiyResource diyResource=getDiyResourceInfo(req,user);
			DiyResource checkDiyResource=diyResourceService.getDiyResourceByKey(diyResource.getKey());
			if(checkDiyResource !=null){
				if(!diyResource.getId().equals(checkDiyResource.getId())){
					resultMap.put("status", Boolean.FALSE);
					resultMap.put("msg", checkDiyResource.getKey()+"已经存在，请重新输入");
					return resultMap;
				}
			}
			diyResourceService.updateDiyResource(diyResource);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "编辑资源失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}
	
	/**
	 * 对资源表进行封装
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	private DiyResource getDiyResourceInfo(HttpServletRequest req,User user) throws Exception {
		DiyResource diyResource=null;
		String resourceId=StringUtils.nullToString(req.getParameter("resourceId"));
		if(!StringUtils.isNullOrEmpty(resourceId)){
			diyResource=diyResourceService.getDiyResourceById(resourceId);
		}else{
			diyResource=new DiyResource();
		}
		diyResource.setId(resourceId);
		diyResource.setResourceName(StringUtils.nullToString(req.getParameter("resourceName")));
		diyResource.setKey(StringUtils.nullToString(req.getParameter("key")).toUpperCase());
		diyResource.setUrl(StringUtils.nullToString(req.getParameter("url")));
		diyResource.setDescription(StringUtils.nullToString(req.getParameter("description")));
		diyResource.setIcon(StringUtils.nullToString(req.getParameter("icon")));
		diyResource.setSeq(Integer.parseInt(StringUtils.nullToString(req.getParameter("seq"))));
		diyResource.setResourceType(StringUtils.nullToString(req.getParameter("resourceType")));
		diyResource.setDataStat(StringUtils.nullToString(req.getParameter("dataStat")));
		diyResource.setPid(StringUtils.nullToString(req.getParameter("pid")));
		diyResource.setUpdateUser(user.getId().toString());
		return diyResource;
	}
	
	/**
	 * 删除资源 commit
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteDiyResourceCommit")
	@ResponseBody
	public Map<String, Object> deleteDiyResourceCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		String resourceId=req.getParameter("resourceId");
		try {
			diyResourceService.deleteDiyResource(resourceId);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "删除资源失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}
}
