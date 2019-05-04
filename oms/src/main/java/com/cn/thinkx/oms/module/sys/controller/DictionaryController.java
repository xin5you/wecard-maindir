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
import com.cn.thinkx.oms.module.sys.model.Dictionary;
import com.cn.thinkx.oms.module.sys.service.DictionaryService;
import com.cn.thinkx.oms.util.StringUtils;



@Controller
@RequestMapping(value = "sys/dictionary")
public class DictionaryController extends BaseController {
	
	@Autowired
	@Qualifier("dictionaryService")
	private DictionaryService dictionaryService;

	
	@RequestMapping(value = "/listDictionary")
	public ModelAndView listDictionary(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("sys/dictionary/listDictionary");
		String operStatus=StringUtils.nullToString(req.getParameter("operStatus"));
		
		List<Dictionary> pageList = null;
		try {
			pageList=dictionaryService.getDictionaryList(new Dictionary());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表信息出错", e);
		}
		mv.addObject("pageInfo", pageList);
		mv.addObject("operStatus", operStatus);
		return mv;
	}
	
	
	@RequestMapping(value = "/intoAddDictionary")
	public ModelAndView intoAddDictionary(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("sys/dictionary/addDictionary");
		Dictionary dict=new Dictionary();
		dict.setType("0");
		List<Dictionary>  dictList=dictionaryService.getDictionaryList(dict);
		mv.addObject("dictList", dictList);
		return mv;
	}
	
	/**
	 * 字典添加提交
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addDictionaryCommit")
	@ResponseBody
	public Map<String, Object> addDictionaryCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("status", Boolean.TRUE);
		try {
			Dictionary dict=getDictionaryInfo(req);
			dictionaryService.insertDictionary(dict);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "新增字典失败，请重新添加");
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
	@RequestMapping(value = "/intoEditDictionary")
	public ModelAndView intoEditDictionary(HttpServletRequest req, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("sys/dictionary/editDictionary");
		String dictId=req.getParameter("dictId");
		Dictionary dict=dictionaryService.getDictionaryById(dictId);
		
		//查找上级菜单列表
		Dictionary dict1=new Dictionary();
		dict1.setType("0");
		List<Dictionary>  dictList=dictionaryService.getDictionaryList(dict1);
		
		mv.addObject("dictList", dictList);
		mv.addObject("dict", dict);
		return mv;
	}
	
	
	/**
	 * 字典编辑 提交
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editDictionaryCommit")
	@ResponseBody
	public Map<String, Object> editDictionaryCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		try {
			Dictionary dict=getDictionaryInfo(req);
			
			if(!StringUtils.isNullOrEmpty(dict.getCode())){
				Dictionary dict1=dictionaryService.getDictionaryByCode(dict.getCode());
				if(dict1 !=null){
					if(!dict.getId().equals(dict1.getId())){
						resultMap.put("status", Boolean.FALSE);
						resultMap.put("msg", "当前CODE已存在，请重新输入");
						return resultMap;
					}
				}
			}
			dictionaryService.updateDictionary(dict);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "编辑字典失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}

	
	/**
	* @Title: getDictionaryInfo
	* @Description: 字典表封装
	* @param @param req
	* @param @return
	* @param @throws Exception
	* @return Dictionary    返回类型
	* @throws
	*/ 
	private Dictionary getDictionaryInfo(HttpServletRequest req) throws Exception {
		Dictionary dict=null;
		String dictId=StringUtils.stringToNull(req.getParameter("dictId"));
		if(!StringUtils.isNullOrEmpty(dictId)){
			dict=dictionaryService.getDictionaryById(dictId);
		}else{
			dict=new Dictionary();
		}
		dict.setId(dictId);
		dict.setName(StringUtils.nullToString(req.getParameter("name")));
		dict.setCode(StringUtils.nullToString(req.getParameter("code")).toUpperCase());
		dict.setType(StringUtils.nullToString(req.getParameter("type")));
		dict.setValue(StringUtils.nullToString(req.getParameter("value")));
		dict.setSeq(StringUtils.nullToString(req.getParameter("seq")));
		dict.setState(StringUtils.nullToString(req.getParameter("state")));
		dict.setPid(StringUtils.nullToString(req.getParameter("pid")));
		return dict;
	}

	
	/**
	 * 删除字典 commit
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteDictionaryCommit")
	@ResponseBody
	public Map<String, Object> deleteDictionaryCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		String dictId=req.getParameter("dictId");
		try {
			dictionaryService.deleteDictionary(dictId);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "删除字典失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}
	
}
