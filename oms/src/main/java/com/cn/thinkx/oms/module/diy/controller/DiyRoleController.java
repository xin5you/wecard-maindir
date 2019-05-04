package com.cn.thinkx.oms.module.diy.controller;


import java.util.ArrayList;
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
import com.cn.thinkx.oms.module.diy.model.DiyRole;
import com.cn.thinkx.oms.module.diy.service.DiyResourceService;
import com.cn.thinkx.oms.module.diy.service.DiyRoleService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.module.sys.model.ZTreeResource;
import com.cn.thinkx.oms.util.StringUtils;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value="diy/diyRole")
public class DiyRoleController extends BaseController{
	
	Logger logger = LoggerFactory.getLogger(DiyRoleController.class);
		
	@Autowired
	@Qualifier("diyRoleService")
	private DiyRoleService diyRoleService;
	
	@Autowired
	@Qualifier("diyResourceService")
	private DiyResourceService diyResourceService;
	
	/**
	 * 商户角色列表
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listDiyRole")
	public ModelAndView listDiyRole(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("diy/diyRole/listDiyRole");
		
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		
		PageInfo<DiyRole> pageList = null;
		DiyRole diyRole=new DiyRole();
		try {
			pageList=diyRoleService.getDiyRolePage(startNum, pageSize, diyRole);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询角色列表信息出错", e);
		}
		mv.addObject("pageInfo", pageList);
		return mv;
	}
	
	/**
	 * 进入商户角色新增
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoAddDiyRole")
	public ModelAndView intoAddDiyRole(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("diy/diyRole/addDiyRole");
		return mv;
	}
	
	/**
	 * 角色添加提交
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addDiyRoleCommit")
	@ResponseBody
	public Map<String, Object> addDiyRoleCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			DiyRole diyRole=null;
			User user = getCurrUser(req);
			diyRole = getDiyRoleTemp(req,user);
			diyRole.setDataStat("0");
			diyRole.setCreateUser(user.getId().toString());
			diyRoleService.insertDiyRole(diyRole);
			resultMap.put("status", Boolean.TRUE);
		}catch(Exception ex){
			ex.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg","添加失败，请稍微再试");
		}
		return resultMap;
	}
	
	/**
	 * 进入商户角色编辑
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoEditDiyRole")
	public ModelAndView intoEditDiyRole(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("diy/diyRole/editDiyRole");
		String id=req.getParameter("id");
		DiyRole diyRole=diyRoleService.getDiyRoleById(id);
		mv.addObject("diyRole", diyRole);
		return mv;
	}
	
	/**
	 * 商户角色编辑 提交
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editDiyRoleCommit")
	@ResponseBody
	public Map<String, Object> editDiyRoleCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			DiyRole diyRole = null;
			User user = getCurrUser(req);
			diyRole = getDiyRoleTemp(req,user);
			diyRoleService.updateDiyRole(diyRole);
			resultMap.put("status", Boolean.TRUE);
		}catch(Exception ex){
			ex.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg","添加失败，请稍微再试");
		}
		return resultMap;
	}
	
	/**
	 * 删除角色 commit
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteDiyRoleCommit")
	@ResponseBody
	public Map<String, Object> deleteDiyRoleCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		String id=StringUtils.nullToString(req.getParameter("id"));
		try {
			diyRoleService.deleteDiyRoleById(id);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "角色授权失败，请重新选择权限");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}
	
	/**
	 * 点击授权按钮，进入授权页面
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/diyRoleAuthorization")
	public ModelAndView diyRoleAuthorization(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("diy/diyRole/diyRoleAuthorization");
		String id=req.getParameter("id");
		mv.addObject("roleId", id);
		return mv;
	}
	
	/**
	 * 获取授权所有的资源列表
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getDiyRoleResources")
	@ResponseBody
	public Map<String, Object> getDiyRoleResources(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String roleId=req.getParameter("id");
		//所以的资源列表
		List<DiyResource> allResourceList=diyResourceService.getDiyResourceList(new DiyResource()); 
		//根据角色的id查看对应的资源信息
		List<DiyResource> roleResList=diyResourceService.getDiyRoleResourceByRoleId(roleId); 
		
		List<ZTreeResource> list=new ArrayList<ZTreeResource>();
		ZTreeResource entity=null;
		if(roleResList !=null && allResourceList.size()>0){
			for(int i=0;i<allResourceList.size();i++){
				entity=new ZTreeResource();
				entity.setId(allResourceList.get(i).getId());
				entity.setName(allResourceList.get(i).getResourceName());
				entity.setpId(allResourceList.get(i).getPid());
				for(int j=0;j<roleResList.size();j++){
					if(roleResList.get(j).getId().equals(allResourceList.get(i).getId())){
						entity.setChecked(true);
					}
				}
				list.add(entity);
			}
		}
		JSONArray jsonArray=JSONArray.fromObject(list);
		resultMap.put("json", jsonArray);
		return resultMap;
	}
	
	/**
	 * 授权保存提交
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/submitDiyRoleAuthorization")
	@ResponseBody
	public Map<String, Object> submitDiyRoleAuthorization(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String[] resourceIds=req.getParameterValues("ids[]");
		String roleId=req.getParameter("roleId");
		resultMap.put("status", Boolean.TRUE);
		try {
			User user = getCurrUser(req);
			diyRoleService.editDiyRoleResource(roleId, resourceIds,user);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "角色授权失败，请重新选择权限");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}
	
	/**
	 * 对商户角色进行封装
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	public DiyRole getDiyRoleTemp(HttpServletRequest req,User user){
		DiyRole diyRole = null;
		String id = StringUtils.nullToString(req.getParameter("id"));
		if(!StringUtils.isNullOrEmpty(id)){
			diyRole = diyRoleService.getDiyRoleById(id);
		}else{
			diyRole = new DiyRole();
		}
		diyRole.setId(id);
		diyRole.setRoleName(StringUtils.nullToString(req.getParameter("roleName")));
		diyRole.setSeq(StringUtils.nullToString(req.getParameter("seq")));
		diyRole.setDescription(StringUtils.nullToString(req.getParameter("description")));
		diyRole.setUpdateUser(user.getId().toString());
		return diyRole;
	}
}
