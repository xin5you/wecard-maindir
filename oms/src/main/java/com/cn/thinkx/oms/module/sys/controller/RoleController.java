package com.cn.thinkx.oms.module.sys.controller;

import java.util.ArrayList;
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
import com.cn.thinkx.oms.module.sys.model.Role;
import com.cn.thinkx.oms.module.sys.model.ZTreeResource;
import com.cn.thinkx.oms.module.sys.service.ResourceService;
import com.cn.thinkx.oms.module.sys.service.RoleService;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;


@Controller
@RequestMapping(value = "sys/role")
public class RoleController extends BaseController {
	
	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;

	
	@Autowired
	@Qualifier("resourceService")
	private ResourceService resourceService;
	
	@RequestMapping(value = "/listRole")
	public ModelAndView loginIndex(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("sys/role/listRole");
		
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		
		PageInfo<Role> pageList = null;
		Role role=new Role();
		try {
			pageList=roleService.getRolePage(startNum, pageSize, role);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询角色列表信息出错", e);
		}
		mv.addObject("pageInfo", pageList);
		return mv;
	}
	
	/**
	 * 授权
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/roleAuthorization")
	public ModelAndView roleAuthorization(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("sys/role/roleAuthorization");
		String roleId=req.getParameter("roleId");
		mv.addObject("roleId", roleId);
		return mv;
	}
	
	
	@RequestMapping(value = "/submitRoleAuthorization")
	@ResponseBody
	public Map<String, Object> submitRoleAuthorization(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String[] resourceIds=req.getParameterValues("ids[]");
		String roleId=req.getParameter("roleId");
		resultMap.put("status", Boolean.TRUE);
		try {

		roleService.editRoleResource(roleId, resourceIds);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "角色授权失败，请重新选择权限");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}
	

	
	/**
	 * 获取授权所有的资源列表
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getRoleResources")
	@ResponseBody
	public Map<String, Object> getRoleResources(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String roleId=req.getParameter("roleId");
		List<Resource> allResourceList=resourceService.getResourceList(new Resource()); //所以的资源列表
		
		List<Resource> roleResList=resourceService.getRoleResourceByRoleId(roleId); //当前角色的权限
		
		List<ZTreeResource> list=new ArrayList<ZTreeResource>();
		ZTreeResource entity=null;
		if(roleResList !=null && allResourceList.size()>0){
			for(int i=0;i<allResourceList.size();i++){
				entity=new ZTreeResource();
				entity.setId(allResourceList.get(i).getId());
				entity.setName(allResourceList.get(i).getName());
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
	
	
	@RequestMapping(value = "/intoAddRole")
	public ModelAndView intoAddRole(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("sys/role/addRole");
		
		return mv;
	}
	
	/**
	 * 角色添加提交
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addRoleCommit")
	@ResponseBody
	public Map<String, Object> addRoleCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String roleName=req.getParameter("roleName");
		String seq=req.getParameter("seq");
		String description=req.getParameter("description");
		try{
			Role role=new Role();
			role.setName(roleName);
			role.setSeq(Integer.parseInt(seq));
			role.setDescription(description);
			role.setIsdefault("0");
			roleService.insertRole(role);
			resultMap.put("status", Boolean.TRUE);
		}catch(Exception ex){
			ex.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg","添加失败，请稍微再试");
		}
		return resultMap;
	}
	
	
	@RequestMapping(value = "/intoEditRole")
	public ModelAndView intoEditRole(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("sys/role/editRole");
		String roleId=req.getParameter("roleId");
		Role role=roleService.getRoleById(roleId);
		mv.addObject("role", role);
		return mv;
	}
	
	
	/**
	 * 角色编辑 提交
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editRoleCommit")
	@ResponseBody
	public Map<String, Object> editRoleCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String roleId=req.getParameter("roleId");
		String roleName=req.getParameter("roleName");
		String seq=req.getParameter("seq");
		String description=req.getParameter("description");
		try{
			Role role=roleService.getRoleById(roleId);
			role.setName(roleName);
			role.setDescription(description);
			role.setSeq(Integer.parseInt(seq));
			roleService.updateRole(role);
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
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteRoleCommit")
	@ResponseBody
	public Map<String, Object> deleteRoleCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		String roleId=req.getParameter("roleId");
		try {
			roleService.deleteRoleById(roleId);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "角色授权失败，请重新选择权限");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}
}
