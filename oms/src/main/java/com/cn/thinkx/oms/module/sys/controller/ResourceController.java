package com.cn.thinkx.oms.module.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.sys.model.Resource;
import com.cn.thinkx.oms.module.sys.service.ResourceService;
import com.cn.thinkx.oms.util.StringUtils;



@Controller
@RequestMapping(value = "sys/resource")
public class ResourceController extends BaseController {
	
	@Autowired
	@Qualifier("resourceService")
	private ResourceService resourceService;

	
	@RequestMapping(value = "/listResource")
	public ModelAndView listResource(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("sys/resource/listResource");
		String operStatus=StringUtils.nullToString(req.getParameter("operStatus"));
		
		List<Resource> pageList = null;
		try {
			pageList=resourceService.getResourceList(new Resource());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表信息出错", e);
		}
		mv.addObject("pageInfo", pageList);
		mv.addObject("operStatus", operStatus);
		return mv;
	}
	
	
	@RequestMapping(value = "/intoAddResource")
	public ModelAndView intoAddResource(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("sys/resource/addResource");
		Resource resource=new Resource();
		resource.setResourcetype("0");
		List<Resource>  resourceList=resourceService.getResourceList(resource);
		mv.addObject("resourceList", resourceList);
		
		
		String resourceId=StringUtils.nullToString(req.getParameter("resourceId"));
		Resource parantRes=resourceService.getResourceById(resourceId);
		mv.addObject("parantRes", parantRes);
		
		return mv;
	}
	
	/**
	 * 资源添加提交
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addResourceCommit")
	@ResponseBody
	public Map<String, Object> addResourceCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("status", Boolean.TRUE);
		try {
			Resource resource=getResourceInfo(req);
			Resource checkResource=resourceService.getResourceByKey(resource.getKey());
			if(checkResource !=null){
				resultMap.put("status", Boolean.FALSE);
				resultMap.put("msg", checkResource.getKey()+"已经存在，请重新输入");
				return resultMap;
			}
			resourceService.insertResource(resource);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "新增资源失败，请重新添加");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}
	
	
	/**
	 * @param req
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/intoEditResource")
	public ModelAndView intoEditResource(HttpServletRequest req, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("sys/resource/editResource");
		String resourceId=req.getParameter("resourceId");
		Resource resource=resourceService.getResourceById(resourceId);
		
		//查找上级菜单列表
		Resource resource1=new Resource();
		resource1.setResourcetype("0");
		List<Resource>  resourceList=resourceService.getResourceList(resource1);
		
		mv.addObject("resourceList", resourceList);
		mv.addObject("resource", resource);
		return mv;
	}
	
	
	/**
	 * 资源编辑 提交
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editResourceCommit")
	@ResponseBody
	public Map<String, Object> editResourceCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		try {
			Resource resource=getResourceInfo(req);
			Resource checkResource=resourceService.getResourceByKey(resource.getKey());
			if(checkResource !=null){
				if(!resource.getId().equals(checkResource.getId())){
					resultMap.put("status", Boolean.FALSE);
					resultMap.put("msg", checkResource.getKey()+"已经存在，请重新输入");
					return resultMap;
				}
			}
			resourceService.updateResource(resource);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "编辑资源失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}

	
	/**
	* @Title: getResourceInfo
	* @Description: 资源表封装
	* @param @param req
	* @param @return
	* @param @throws Exception
	* @return resource    返回类型
	* @throws
	*/ 
	private Resource getResourceInfo(HttpServletRequest req) throws Exception {
		Resource resource=null;
		String resourceId=StringUtils.nullToString(req.getParameter("resourceId"));
		if(!StringUtils.isNullOrEmpty(resourceId)){
			resource=resourceService.getResourceById(resourceId);
		}else{
			resource=new Resource();
		}
		resource.setId(resourceId);
		resource.setName(StringUtils.nullToString(req.getParameter("name")));
		resource.setKey(StringUtils.nullToString(req.getParameter("key")).toUpperCase());
		resource.setUrl(StringUtils.nullToString(req.getParameter("url")));
		resource.setDescription(StringUtils.nullToString(req.getParameter("description")));
		resource.setIcon(StringUtils.nullToString(req.getParameter("icon")));
		resource.setSeq(Integer.parseInt(StringUtils.nullToString(req.getParameter("seq"))));
		resource.setResourcetype(StringUtils.nullToString(req.getParameter("resourcetype")));
		resource.setState(StringUtils.nullToString(req.getParameter("state")));
		resource.setPid(StringUtils.nullToString(req.getParameter("pid")));
		return resource;
	}

	
	/**
	 * 删除资源 commit
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteResourceCommit")
	@ResponseBody
	public Map<String, Object> deleteResourceCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		String resourceId=req.getParameter("resourceId");
		try {
			resourceService.deleteResource(resourceId);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "删除资源失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}
	
}
