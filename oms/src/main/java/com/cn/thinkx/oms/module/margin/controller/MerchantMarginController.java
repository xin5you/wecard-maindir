package com.cn.thinkx.oms.module.margin.controller;

import java.util.Date;
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
import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.module.margin.model.MerchantCashManage;
import com.cn.thinkx.oms.module.margin.model.MerchantMarginList;
import com.cn.thinkx.oms.module.margin.service.MerchantCashManageService;
import com.cn.thinkx.oms.module.margin.service.MerchantMarginService;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfService;
import com.cn.thinkx.oms.module.sys.model.Dictionary;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.module.sys.service.DictionaryService;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.github.pagehelper.PageInfo;


/**
 * 商户保证金保证金管理
 * @author zqy
 *
 */
@Controller
@RequestMapping(value = "margin/mchntCashManage")
public class MerchantMarginController extends BaseController {
	
	Logger logger = LoggerFactory.getLogger(MerchantMarginController.class);
	
	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService merchantInfService;
	
	@Autowired
	@Qualifier("merchantMarginService")
	private MerchantMarginService MerchantMarginService;
	
	@Autowired
	@Qualifier("merchantCashManageService")
	private MerchantCashManageService merchantCashManageService;
	
	@Autowired
	@Qualifier("merchantMarginService")
	private MerchantMarginService merchantMarginService;
	
	@Autowired
	@Qualifier("dictionaryService")
	private DictionaryService dictionaryService;
	

	
	/**
	 * 商户保证金保证金列表查询
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listMerchantCashManage")
	public ModelAndView listMerchantCashManage(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("margin/merchantCashManage/listMerchantCashManage");
		MerchantCashManage merchantCashManage = getMerchantCashManageInfo(req,null);
		String operStatus=StringUtils.nullToString(req.getParameter("operStatus"));
		PageInfo<MerchantCashManage> pageList = null;
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		try {
			

			pageList=merchantCashManageService.getMerchantCashManagePage(startNum,pageSize,merchantCashManage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
		mv.addObject("operStatus", operStatus);
		mv.addObject("pageInfo", pageList);
		mv.addObject("merchantCashManage", merchantCashManage);
		return mv;
	}
	
	
	/**
	 * 检查商户押款是否有记录
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/checkMchtCashManage")
	@ResponseBody
	public Map<String, Object> checkMchtCashManage(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.FALSE);
		try {
			String mchntId=StringUtils.nullToString(req.getParameter("mchntId"));
	
			MerchantCashManage merchantCashManage=merchantCashManageService.getMerchantCashManageByMchntId(mchntId);
			if(merchantCashManage !=null ){
					resultMap.put("status", Boolean.FALSE);
					resultMap.put("msg", "当前商户押款记录已存在,请重新选择");
					resultMap.put("merchantCashManage", merchantCashManage);
					return resultMap;
			}else{
				resultMap.put("status", Boolean.TRUE);
				return resultMap;
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "当前商户押款记录查询失败,请重新选择");
			return resultMap;
		}
	}
	
	
	@RequestMapping(value = "/intoAddMerchantCashManage")
	public ModelAndView intoAddMerchantCashManage(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("margin/merchantCashManage/addMerchantCashManage");
		List<MerchantInf> mchntList= merchantInfService.getMerchantInfListBySelect();
		
		// 商户保证金押款标志
		List<Dictionary> dictList=dictionaryService.getDictionaryListByPCode(Constants.DictType.MCHNT_MORTGAGE_FLG.getCode());
		mv.addObject("dictList", dictList);
		
		
		mv.addObject("mchntList", mchntList);
		return mv;
	}

	
	/**
	 * 商户保证金添加提交
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addMerchantCashManageCommit")
	@ResponseBody
	public Map<String, Object>  addMerchantCashManageCommit(HttpServletRequest req,HttpServletResponse resp) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.FALSE);
		User user=getCurrUser(req);
		try{
			MerchantCashManage merchantCashManage=getMerchantCashManageInfo(req, user);
			int oper=merchantCashManageService.insertMerchantCashManage(merchantCashManage);
			if(oper>0){
				resultMap.put("status", Boolean.TRUE);
			}else{
				resultMap.put("status", Boolean.FALSE);
				resultMap.put("msg", "商户保证金添加失败，请重新添加");
			}
		}catch(Exception e){
			logger.error(e.getLocalizedMessage(), e);
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "商户保证金添加失败，请重新添加");
		}
		return resultMap;
	}
	
	
	/** 商户保证金编辑页面
	 * @param req
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/intoViewMerchantCashManage")
	public ModelAndView intoEditMerchantCashManage(HttpServletRequest req, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("margin/merchantCashManage/viewMerchantCashManage");
		String chashId=req.getParameter("chashId");
		
		MerchantCashManage merchantCashManage=merchantCashManageService.getMerchantCashManageById(chashId);
		
		MerchantInf merchantInf=merchantInfService.getMerchantInfById(merchantCashManage.getMchntId());
		// 商户保证金押款标志
		List<Dictionary> dictList=dictionaryService.getDictionaryListByPCode(Constants.DictType.MCHNT_MORTGAGE_FLG.getCode());
		mv.addObject("dictList", dictList);
		
		mv.addObject("merchantCashManage", merchantCashManage);
		mv.addObject("merchantInf", merchantInf);
	
		return mv;
	}
	
	
	
	
	
	
	/**
	 * 商户保证金保证金明细列表查询
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listMerchantMargin")
	public ModelAndView listMerchantMargin(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("margin/merchantCashManage/listMerchantMargin");
		MerchantMarginList merchantMarginList = new MerchantMarginList();
		String chashId=StringUtils.nullToString(req.getParameter("chashId"));
		String operStatus=StringUtils.nullToString(req.getParameter("operStatus"));
		
		if(StringUtils.isNullOrEmpty(chashId)){
			mv = new ModelAndView("redirect:/margin/mchntCashManage/listMerchantCashManage.do");
			return mv;
		}
		merchantMarginList.setChashId(chashId);
		PageInfo<MerchantMarginList> pageList = null;
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		
		MerchantCashManage merchantCashManage=merchantCashManageService.getMerchantCashManageById(chashId);
		if(merchantCashManage !=null){
			MerchantInf merchantInf=merchantInfService.getMerchantInfById(merchantCashManage.getMchntId());
			try {
				pageList=merchantMarginService.getMerchantMarginListPage(startNum,pageSize,merchantMarginList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mv.addObject("merchantInf", merchantInf);
		}
		//保证金状态
		List<Dictionary> approveDictList=dictionaryService.getDictionaryListByPCode(Constants.DictType.MCHNT_MARGIN_STAT.getCode());
		mv.addObject("approveDictList", approveDictList);
		mv.addObject("operStatus", operStatus);
		mv.addObject("chashId", chashId);
		mv.addObject("pageInfo", pageList);
		return mv;
	}
	
	
	
	@RequestMapping(value = "/intoAddMerchantMargin")
	public ModelAndView intoAddMerchantMargin(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("margin/merchantCashManage/addMerchantMargin");

		String chashId=StringUtils.nullToString(req.getParameter("chashId"));
		MerchantCashManage merchantCashManage=merchantCashManageService.getMerchantCashManageById(chashId);
		// 商户保证金押款标志
		List<Dictionary> dictList=dictionaryService.getDictionaryListByPCode(Constants.DictType.MCHNT_MORTGAGE_FLG.getCode());
		mv.addObject("dictList", dictList);
		
		//选择的商户
		MerchantInf merchantInf=merchantInfService.getMerchantInfById(merchantCashManage.getMchntId());
		mv.addObject("merchantInf", merchantInf);
		
//		// 商户保证金审核状态
//		List<Dictionary> approveStatList=dictionaryService.getDictionaryListByPCode(Constants.DictType.MCHNT_MARGIN_STAT.getCode());
//		mv.addObject("approveStatList", approveStatList);


		mv.addObject("merchantCashManage", merchantCashManage);
		return mv;
	}
	

	/**
	 * 追加商户保证金提交
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addMerchantMarginCommit")
	@ResponseBody
	public Map<String, Object> addMerchantMarginCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		User user=getCurrUser(req);
		try{
			MerchantMarginList merchantMarginList=getMerMerchantMarginList(req,user);
			if(user !=null){
				merchantMarginList.setApplyTime(new Date());
				merchantMarginList.setApplyUserId(user.getId().toString().trim());
				merchantMarginList.setApplyUserName(user.getName());
				merchantMarginList.setCreateUser(user.getId().toString().trim());
				merchantMarginList.setUpdateUser(user.getId().toString().trim());
			}
			int oper=merchantMarginService.addMerchantMarginList(merchantMarginList);
			if(oper<1){
				resultMap.put("status", Boolean.FALSE);
				resultMap.put("msg","追加伤害保证金失败，请重新提交");
				return resultMap;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg","追加伤害保证金失败，请重新提交");
			return resultMap;
		}
		return resultMap;
	}
	
	
	@RequestMapping(value = "/intoEditMerchantMargin")
	public ModelAndView intoEditMerchantMargin(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("margin/merchantCashManage/editMerchantMargin");

		String marginListId=StringUtils.nullToString(req.getParameter("marginListId"));
		MerchantMarginList merchantMargin=merchantMarginService.getMerchantMarginListById(marginListId);
		// 商户保证金押款标志
		List<Dictionary> dictList=dictionaryService.getDictionaryListByPCode(Constants.DictType.MCHNT_MORTGAGE_FLG.getCode());
		mv.addObject("dictList", dictList);
		
		//选择的商户
		MerchantInf merchantInf=merchantInfService.getMerchantInfById(merchantMargin.getMerchantId());
		mv.addObject("merchantInf", merchantInf);
		
//		// 商户保证金审核状态
//		List<Dictionary> approveStatList=dictionaryService.getDictionaryListByPCode(Constants.DictType.MCHNT_MARGIN_STAT.getCode());
//		mv.addObject("approveStatList", approveStatList);


		mv.addObject("merchantMargin", merchantMargin);
		return mv;
	}
	
	
	/**
	 * 编辑 追加商户保证金提交
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editMerchantMarginCommit")
	@ResponseBody
	public Map<String, Object> editMerchantMarginCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		User user=getCurrUser(req);
		try{
			MerchantMarginList merchantMarginList=getMerMerchantMarginList(req,user);
			if(user !=null){
				merchantMarginList.setApplyTime(new Date());
				merchantMarginList.setApplyUserId(user.getId().toString().trim());
				merchantMarginList.setApplyUserName(user.getName());
				merchantMarginList.setCreateUser(user.getId().toString().trim());
				merchantMarginList.setUpdateUser(user.getId().toString().trim());
			}
			int oper=merchantMarginService.updateMerchantMarginList(merchantMarginList);
			if(oper<1){
				resultMap.put("status", Boolean.FALSE);
				resultMap.put("msg","追加伤害保证金失败，请重新提交");
				return resultMap;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg","追加伤害保证金失败，请重新提交");
			return resultMap;
		}
		return resultMap;
	}
	
	/**
	 * 追加商户保证金 删除
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/delMerchantMarginCommit")
	@ResponseBody
	public Map<String, Object> delMerchantMarginCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		User user=getCurrUser(req);
		String marginListId=req.getParameter("marginListId");
		try{
			
			int oper=merchantMarginService.deleteMerchantMarginListById(marginListId);
			if(oper<1){
				resultMap.put("status", Boolean.FALSE);
				resultMap.put("msg","数据删除失败，请重新提交");
				return resultMap;
			}
		}catch(Exception ex){
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg","数据删除失败，请重新提交");
			return resultMap;
		}
		return resultMap;
	}
	
	
	
	
	
	/**
	 * 商户保证金 审批列表查询
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listMchntMarginApprove")
	public ModelAndView listMchntMarginApprove(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("margin/merchantCashManage/listMchntMarginApprove");
		
		User user=getCurrUser(req);
		MerchantMarginList merchantMargin = getMerMerchantMarginList(req,null);
		String operStatus=StringUtils.nullToString(req.getParameter("operStatus"));
		PageInfo<MerchantMarginList> pageList = null;
		
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		try {
			merchantMargin.setApproveStats(new String[]{"20","30","40"});
			pageList=merchantMarginService.getMerchantMarginApproveList(startNum,pageSize,merchantMargin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 商户保证金押款标志
		List<Dictionary> dictList=dictionaryService.getDictionaryListByPCode(Constants.DictType.MCHNT_MORTGAGE_FLG.getCode());
		
		//保证金状态
		List<Dictionary> approveDictList=dictionaryService.getDictionaryListByPCode(Constants.DictType.MCHNT_MARGIN_STAT.getCode());
		
		mv.addObject("dictList", dictList);
		mv.addObject("approveDictList", approveDictList);
		mv.addObject("operStatus", operStatus);
		mv.addObject("pageInfo", pageList);
		mv.addObject("merchantMargin", merchantMargin);
		return mv;
	}
	
	/**
	 * 审批 商户保证金提交
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/approveMerchantMarginCommit")
	@ResponseBody
	public Map<String, Object> approveMerchantMarginCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		User user=getCurrUser(req);
		try{
			String marginListId=StringUtils.nullToString(req.getParameter("marginListId"));
			MerchantMarginList merchantMarginList=merchantMarginService.getMerchantMarginListById(marginListId);
			merchantMarginList.setApproveStat(StringUtils.nullToString(req.getParameter("approveStat")));//申请状态
			merchantMarginList.setApproveId(user.getId().toString());
			merchantMarginList.setApproveName(user.getName());
			merchantMarginList.setApproveTime(new Date());
			merchantMarginList.setUpdateTime(new Date());
			merchantMarginList.setUpdateUser(user.getId().toString());
			int oper=merchantMarginService.updateMerchantMarginList(merchantMarginList);
			if(oper<1){
				resultMap.put("status", Boolean.FALSE);
				resultMap.put("msg","审核商户保证金失败，请重新提交");
				return resultMap;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg","审核商户保证金失败，请重新提交");
			return resultMap;
		}
		return resultMap;
	}
	
	/**
	 * 商户保证金审批页面
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoApproveMcerchantMargin")
	public ModelAndView intoApproveMcerchantMargin(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("margin/merchantCashManage/approveMerchantMargin");

		String marginListId=StringUtils.nullToString(req.getParameter("marginListId"));
		MerchantMarginList merchantMargin=merchantMarginService.getMerchantMarginListById(marginListId);
		// 商户保证金押款标志
		List<Dictionary> dictList=dictionaryService.getDictionaryListByPCode(Constants.DictType.MCHNT_MORTGAGE_FLG.getCode());
		mv.addObject("dictList", dictList);
		
		//选择的商户
		MerchantInf merchantInf=merchantInfService.getMerchantInfById(merchantMargin.getMerchantId());
		mv.addObject("merchantInf", merchantInf);
		
//		// 商户保证金审核状态
//		List<Dictionary> approveStatList=dictionaryService.getDictionaryListByPCode(Constants.DictType.MCHNT_MARGIN_STAT.getCode());
//		mv.addObject("approveStatList", approveStatList);

		mv.addObject("merchantMargin", merchantMargin);
		return mv;
	}
	
	
	/**
	 * 商户保证金 确认押款列表查询
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listMerchantCashConfirm")
	public ModelAndView listMerchantCashConfirm(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("margin/merchantCashManage/listMerchantCashConfirm");
		

		MerchantMarginList merchantMargin = getMerMerchantMarginList(req,null);
		String operStatus=StringUtils.nullToString(req.getParameter("operStatus"));
		PageInfo<MerchantMarginList> pageList = null;
		
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		try {
			merchantMargin.setApproveStats(new String[]{"40","50"});
			pageList=merchantMarginService.getMerchantMarginApproveList(startNum,pageSize,merchantMargin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 商户保证金押款标志
		List<Dictionary> dictList=dictionaryService.getDictionaryListByPCode(Constants.DictType.MCHNT_MORTGAGE_FLG.getCode());
		
		//保证金状态
		List<Dictionary> approveDictList=dictionaryService.getDictionaryListByPCode(Constants.DictType.MCHNT_MARGIN_STAT.getCode());
		
		mv.addObject("dictList", dictList);
		mv.addObject("approveDictList", approveDictList);
		mv.addObject("operStatus", operStatus);
		mv.addObject("pageInfo", pageList);
		mv.addObject("merchantMargin", merchantMargin);
		return mv;
	}
	
	/**
	 * 商户保证金确认押款页面
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoCashMcerchantMargin")
	public ModelAndView intoCashMcerchantMargin(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("margin/merchantCashManage/mcerchantCashMarginConfirm");

		String marginListId=StringUtils.nullToString(req.getParameter("marginListId"));
		MerchantMarginList merchantMargin=merchantMarginService.getMerchantMarginListById(marginListId);
		// 商户保证金押款标志
		List<Dictionary> dictList=dictionaryService.getDictionaryListByPCode(Constants.DictType.MCHNT_MORTGAGE_FLG.getCode());
		mv.addObject("dictList", dictList);
		
		//选择的商户
		MerchantInf merchantInf=merchantInfService.getMerchantInfById(merchantMargin.getMerchantId());
		mv.addObject("merchantInf", merchantInf);
		
//		// 商户保证金审核状态
//		List<Dictionary> approveStatList=dictionaryService.getDictionaryListByPCode(Constants.DictType.MCHNT_MARGIN_STAT.getCode());
//		mv.addObject("approveStatList", approveStatList);

		mv.addObject("merchantMargin", merchantMargin);
		return mv;
	}
	
	
	
	/**
	 * 审批 商户保证金 押款确认提交
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/mchntCashConfirmCommit")
	@ResponseBody
	public Map<String, Object> mchntCashConfirmCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		User user=getCurrUser(req);
		try{
			String marginListId=StringUtils.nullToString(req.getParameter("marginListId"));
			MerchantMarginList merchantMarginList=merchantMarginService.getMerchantMarginListById(marginListId);
			merchantMarginList.setConfirmPaymentId(user.getId().toString());
			merchantMarginList.setConfirmPaymentName(user.getName());
			merchantMarginList.setConfirmPaymentTime(new Date());
			merchantMarginList.setUpdateTime(new Date());
			merchantMarginList.setUpdateUser(user.getId().toString());
			int oper=merchantMarginService.saveMchntCashConfirm(merchantMarginList);
			if(oper<1){
				resultMap.put("status", Boolean.FALSE);
				resultMap.put("msg","保证金押款失败，请重新提交");
				return resultMap;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg","保证金押款失败，请重新提交");
			return resultMap;
		}
		return resultMap;
	}
	
	/**
	 * 商户保证金查看页面
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoViewMchntMargin")
	public ModelAndView intoViewMchntMargin(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("margin/merchantCashManage/viewMerchantMargin");

		String marginListId=StringUtils.nullToString(req.getParameter("marginListId"));
		MerchantMarginList merchantMargin=merchantMarginService.getMerchantMarginListById(marginListId);
		// 商户保证金押款标志
		List<Dictionary> dictList=dictionaryService.getDictionaryListByPCode(Constants.DictType.MCHNT_MORTGAGE_FLG.getCode());
		mv.addObject("dictList", dictList);
		
		//选择的商户
		MerchantInf merchantInf=merchantInfService.getMerchantInfById(merchantMargin.getMerchantId());
		mv.addObject("merchantInf", merchantInf);
		
//		// 商户保证金审核状态
		List<Dictionary> approveStatList=dictionaryService.getDictionaryListByPCode(Constants.DictType.MCHNT_MARGIN_STAT.getCode());
		mv.addObject("approveStatList", approveStatList);

		mv.addObject("merchantMargin", merchantMargin);
		return mv;
	}
	
	/**
	* @Title: getMerchantCashManageInfo
	* @Description: 商户保证金表封装
	* @param @param req
	* @param @return
	* @param @throws Exception
	* @return merchantInf    返回类型
	* @throws
	*/ 
	private MerchantMarginList getMerMerchantMarginList(HttpServletRequest req,User user) {
		
		MerchantMarginList entity=null;
		String marginListId=StringUtils.nullToString(req.getParameter("marginListId"));
		if(!StringUtils.isNullOrEmpty(marginListId)){
			entity=merchantMarginService.getMerchantMarginListById(marginListId);
		}else{
			entity=new MerchantMarginList();
		}
		entity.setMarginListId(marginListId);
		entity.setMchntCode(StringUtils.nullToString(req.getParameter("mchntCode")));
		entity.setMchntName(StringUtils.nullToString(req.getParameter("mchntName")));
		
		String chashId=StringUtils.nullToString(req.getParameter("chashId"));
		if(!StringUtils.isNullOrEmpty(chashId)){
			entity.setChashId(chashId);
		}
		String mortgageFlg=req.getParameter("mortgageFlg");
		if(!StringUtils.isNullOrEmpty(mortgageFlg)){
			entity.setMortgageFlg(StringUtils.nullToString(req.getParameter("mortgageFlg")));
		}

		String addMortgageAmt=StringUtils.nullToString(req.getParameter("addMortgageAmt"));
		if(!StringUtils.isNullOrEmpty(addMortgageAmt)){
			entity.setAddMortgageAmt(NumberUtils.RMBYuanToCent(addMortgageAmt));
		}
		
		String approveStat=StringUtils.nullToString(req.getParameter("approveStat"));
		if(!StringUtils.isNullOrEmpty(approveStat)){
			entity.setApproveStat(StringUtils.nullToString(req.getParameter("approveStat")));//申请状态
		}
		
		String addGetQuota=StringUtils.nullToString(req.getParameter("addGetQuota"));
		if(!StringUtils.isNullOrEmpty(addGetQuota)){
			entity.setAddGetQuota(NumberUtils.RMBYuanToCent(addGetQuota));
		}
		

		return entity;
	}
	
	/**
	* @Title: getMerchantCashManageInfo
	* @Description: 商户保证金表封装
	* @param @param req
	* @param @return
	* @param @throws Exception
	* @return merchantInf    返回类型
	* @throws
	*/ 
	private MerchantCashManage getMerchantCashManageInfo(HttpServletRequest req,User user) {
		
		MerchantCashManage entity=null;
		String chashId=StringUtils.nullToString(req.getParameter("chashId"));
		if(!StringUtils.isNullOrEmpty(chashId)){
			entity=merchantCashManageService.getMerchantCashManageById(chashId);
		}else{
			entity=new MerchantCashManage();
		}
		entity.setChashId(chashId);
		entity.setMchntId(StringUtils.nullToString(req.getParameter("mchntId")));
		entity.setMchntName(StringUtils.nullToString(req.getParameter("mchntName")));
		entity.setMchntCode(StringUtils.nullToString(req.getParameter("mchntCode")));
		String mortgageFlg=req.getParameter("mortgageFlg");
		if(!StringUtils.isNullOrEmpty(mortgageFlg)){
			entity.setMortgageFlg(StringUtils.nullToString(req.getParameter("mortgageFlg")));
		}
		if(user !=null){
			entity.setCreateUser(user.getId().toString());
			entity.setUpdateUser(user.getId().toString());
		}
		return entity;
	}
}
