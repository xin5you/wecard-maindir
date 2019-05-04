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
import com.cn.thinkx.oms.module.sys.model.Organization;
import com.cn.thinkx.oms.module.sys.service.OrganizationService;
import com.cn.thinkx.oms.util.StringUtils;



@Controller
@RequestMapping(value = "sys/organization")
public class OrganizationController extends BaseController {
	
	@Autowired
	@Qualifier("organizationService")
	private OrganizationService organizationService;

	
	@RequestMapping(value = "/listOrganization")
	public ModelAndView listorganization(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("sys/organization/listOrganization");
		String operStatus=StringUtils.nullToString(req.getParameter("operStatus"));
		
		List<Organization> pageList = null;
		try {
			pageList=organizationService.getOrganizationList(new Organization());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表信息出错", e);
		}
		mv.addObject("pageInfo", pageList);
		mv.addObject("operStatus", operStatus);
		return mv;
	}
	
	
	@RequestMapping(value = "/intoAddOrganization")
	public ModelAndView intoAddOrganization(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("sys/organization/addOrganization");
		Organization entity=new Organization();
		
		List<Organization>  entityList=organizationService.getOrganizationList(entity);
		mv.addObject("entityList", entityList);
		return mv;
	}
	
	/**
	 * 部门添加提交
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addOrganizationCommit")
	@ResponseBody
	public Map<String, Object> addOrganizationCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("status", Boolean.TRUE);
		try {
			Organization organization=getOrganizationInfo(req);
			organizationService.saveOrganization(organization);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "新增部门失败，请重新添加");
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
	@RequestMapping(value = "/intoEditOrganization")
	public ModelAndView intoEditOrganization(HttpServletRequest req, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("sys/organization/editOrganization");
		String organId=req.getParameter("organId");
		Organization organ=organizationService.getOrganizationById(organId);
		
		//查找上级菜单列表
		Organization organ1=new Organization();
		List<Organization>  entityList=organizationService.getOrganizationList(organ1);
		
		mv.addObject("entityList", entityList);
		mv.addObject("organ", organ);
		return mv;
	}
	
	
	/**
	 * 部门编辑 提交
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editOrganizationCommit")
	@ResponseBody
	public Map<String, Object> editOrganizationCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		try {
			Organization organ=getOrganizationInfo(req);
			
	
			organizationService.updateOrganization(organ);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "编辑部门失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}

	
	/**
	* @Title: getorganizationInfo
	* @Description: 部门表封装
	* @param @param req
	* @param @return
	* @param @throws Exception
	* @return organization    返回类型
	* @throws
	*/ 
	private Organization getOrganizationInfo(HttpServletRequest req) throws Exception {
		Organization organ=null;
		String organId=StringUtils.nullToString(req.getParameter("organId"));
		if(!StringUtils.isNullOrEmpty(organId)){
			organ=organizationService.getOrganizationById(organId);
		}else{
			organ=new Organization();
		}
	
		organ.setName(StringUtils.nullToString(req.getParameter("name")));
		organ.setCode(StringUtils.nullToString(req.getParameter("code")));
		organ.setAddress(StringUtils.nullToString(req.getParameter("address")));
		organ.setIcon(StringUtils.nullToString(req.getParameter("icon")));
		organ.setSeq(StringUtils.nullToString(req.getParameter("seq")));
		organ.setPid(StringUtils.nullToString(req.getParameter("pid")));
		return organ;
	}

	
	/**
	 * 删除部门 commit
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteOrganizationCommit")
	@ResponseBody
	public Map<String, Object> deleteOrganizationCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		String organId=req.getParameter("organId");
		try {
			organizationService.deleteOrganization(organId);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "删除部门失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}
	
}
