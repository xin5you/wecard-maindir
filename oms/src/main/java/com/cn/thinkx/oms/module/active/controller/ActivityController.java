package com.cn.thinkx.oms.module.active.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.module.active.model.ActiveList;
import com.cn.thinkx.oms.module.active.model.CommodityInf;
import com.cn.thinkx.oms.module.active.model.MerchantActiveInf;
import com.cn.thinkx.oms.module.active.service.ActivityService;
import com.cn.thinkx.oms.module.active.service.CommodityInfService;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.DataStatEnum;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "active/activity")
public class ActivityController extends BaseController {

	@Autowired
	@Qualifier("activityService")
	private ActivityService activityService;
	
	@Autowired
	@Qualifier("commodityInfService")
	private CommodityInfService commodityInfService;

	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService merchantInfService;

	/**
	 * 活动列表查询
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listMerchantActives")
	public ModelAndView listMerchantActives(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("active/activity/listMerchantActives");
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		try {
			MerchantActiveInf merchantActiveInf = getMerchantActiveInf(req);
			PageInfo<MerchantActiveInf> pageList = activityService.getMerchantActiveInfPage(startNum, pageSize, 
					merchantActiveInf);
			mv.addObject("entity", merchantActiveInf);
			mv.addObject("activeStatList", Constants.ActiveStatus.values());
			mv.addObject("pageInfo", pageList);
		} catch (Exception e) {
			logger.error("查询活动列表信息出错", e);
		}
		return mv;
	}

	@RequestMapping(value = "/intoAdd")
	public ModelAndView intoAdd(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("active/activity/addMerchantActiveInf");
		List<MerchantInf> mchntList = merchantInfService.getMchntProList();
		mv.addObject("mchntList", mchntList);
		mv.addObject("activeStatList", Constants.ActiveStatus.values());
		return mv;
	}
	
	@RequestMapping(value = "/intoEdit")
	public ModelAndView intoEdit(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("active/activity/editMerchantActiveInf");
		List<MerchantInf> mchntList = merchantInfService.getMchntProList();
		mv.addObject("mchntList", mchntList);
		
		String id = req.getParameter("activeId");
		MerchantActiveInf entity = activityService.getMerchantActiveInfById(id);
		mv.addObject("entity", entity);
		
		if (Constants.ActiveStatus.draft.getCode().equals(entity.getActiveStat()))
			mv.addObject("activeStatList", Constants.ActiveStatus.values());
		else
			mv.addObject("activeStatList", Constants.ActiveStatusUsed.values());
		
		return mv;
	}
	
	@RequestMapping(value = "/intoView")
	public ModelAndView intoView(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("active/activity/viewMerchantActiveInf");
		
		String id = req.getParameter("activeId");
		MerchantActiveInf entity = activityService.getMerchantActiveInfById(id);
		mv.addObject("entity", entity);
		mv.addObject("activeStatList", Constants.ActiveStatus.values());
		
		return mv;
	}
	
	@RequestMapping("getMerchantActiveInfById")
	@ResponseBody
	public MerchantActiveInf getMerchantActiveInfById(HttpServletRequest request) {
		String id = request.getParameter("activeId");
		return activityService.getMerchantActiveInfById(id);
	}
	
	@RequestMapping("getCommoListByActiveId")
	@ResponseBody
	public List<CommodityInf> getCommoListByActiveId(HttpServletRequest request) {
		String activeId = request.getParameter("activeId");
		String mchntId = request.getParameter("mchntId");
		List<CommodityInf> commList = null;
		List<CommodityInf> list = null;
		
		if (!StringUtils.isNullOrEmpty(activeId)) {
			commList = activityService.getCommoListByActiveId(activeId);
		}
		if (!StringUtils.isNullOrEmpty(mchntId)) {
			list = commodityInfService.getCommoListByMchntId(mchntId);
		}
		for (CommodityInf inf : commList) {
			inf.setCommList(list);
		}
		return commList;
	}
	
	@RequestMapping("getCommoListByMchntId")
	@ResponseBody
	public List<CommodityInf> getCommoListByMchntId(HttpServletRequest request) {
		String id = request.getParameter("mchntId");
		return commodityInfService.getCommoListByMchntId(id);
	}
	
	@RequestMapping(value = "/addEntity")
	@ResponseBody
	public String addEntity(HttpServletRequest req) {
		String id = "";
		try {
			User user = getCurrUser(req);
			MerchantActiveInf entity = new MerchantActiveInf();
			entity.setMerchantId(req.getParameter("mchntId"));
			entity.setActiveName(req.getParameter("activeName"));
			entity.setActiveStat(req.getParameter("activeStat"));
			entity.setStartTime(req.getParameter("startTime"));
			entity.setEndTime(req.getParameter("endTime"));
			entity.setActiveExplain(req.getParameter("activeExplain"));
			entity.setActiveRule(req.getParameter("activeRule"));
			entity.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
			entity.setCreateUser("" + user.getId());
			entity.setUpdateUser("" + user.getId());
			id = activityService.addMerchantActiveInf(entity);
		} catch (Exception e) {
			logger.error("插入活动信息出错", e);
		}
		return id;
	}
	
	@RequestMapping(value = "/editEntity")
	@ResponseBody
	public int editEntity(HttpServletRequest req) {
		int result = 0;
		try {
			String id = req.getParameter("activeId");
			MerchantActiveInf entity = activityService.getMerchantActiveInfById(id);
			if (entity != null && DataStatEnum.TRUE_STATUS.getCode().equals(entity.getDataStat())) {
				entity.setMerchantId(req.getParameter("mchntId"));
				entity.setActiveName(req.getParameter("activeName"));
				entity.setActiveStat(req.getParameter("activeStat"));
				entity.setStartTime(req.getParameter("startTime"));
				entity.setEndTime(req.getParameter("endTime"));
				entity.setActiveExplain(req.getParameter("activeExplain"));
				entity.setActiveRule(req.getParameter("activeRule"));
				User user = getCurrUser(req);
				entity.setUpdateUser("" + user.getId());
				result = activityService.updateMerchantActiveInf(entity);
			}
		} catch (Exception e) {
			logger.error("编辑活动信息出错", e);
		}
		return result;
	}
	
	@RequestMapping(value = "/deleteEntity")
	@ResponseBody
	public int deleteEntity(HttpServletRequest req) {
		String id = req.getParameter("activeId");
		try {
			MerchantActiveInf entity = new MerchantActiveInf();
			entity.setActiveId(id);
			User user = getCurrUser(req);
			entity.setUpdateUser("" + user.getId());
			return activityService.deleteMerchantActiveInf(entity);
		} catch (Exception e) {
			logger.error("删除活动信息出错", e);
		}
		return 0;
	}
	
	@RequestMapping(value = "/addActiveList")
	@ResponseBody
	public String addActiveList(HttpServletRequest req) {
		String id = "";
		try {
			User user = getCurrUser(req);
			ActiveList entity = new ActiveList();
			entity.setActiveId(req.getParameter("activeId"));
			entity.setCommodityId(req.getParameter("commodityId"));
			entity.setSellingPrice(NumberUtils.RMBYuanToCent(req.getParameter("sellingPrice")));
			entity.setStock(Integer.parseInt(req.getParameter("stock")));
			entity.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
			entity.setCreateUser("" + user.getId());
			entity.setUpdateUser("" + user.getId());
			id = activityService.addActiveList(entity);
		} catch (Exception e) {
			logger.error("插入活动商品信息出错", e);
		}
		return id;
	}
	
	@RequestMapping(value = "/editActiveList")
	@ResponseBody
	public int editActiveList(HttpServletRequest req) {
		int result = 0;
		try {
			String id = req.getParameter("activeListId");
			ActiveList entity = activityService.getActiveListById(id);
			if (entity != null && DataStatEnum.TRUE_STATUS.getCode().equals(entity.getDataStat())) {
				String commId = req.getParameter("commodityId");
				if (!StringUtils.isNullOrEmpty(commId)) {
					entity.setCommodityId(commId);
				}
				entity.setSellingPrice(NumberUtils.RMBYuanToCent(req.getParameter("sellingPrice")));
				entity.setStock(Integer.parseInt(req.getParameter("stock")));
				User user = getCurrUser(req);
				entity.setUpdateUser("" + user.getId());
				result = activityService.updateActiveList(entity);
			}
		} catch (Exception e) {
			logger.error("编辑活动商品信息出错", e);
		}
		return result;
	}
	
	@RequestMapping(value = "/deleteActiveList")
	@ResponseBody
	public int deleteActiveList(HttpServletRequest req) {
		String id = req.getParameter("activeListId");
		try {
			ActiveList entity = new ActiveList();
			entity.setActiveListId(id);
			User user = getCurrUser(req);
			entity.setUpdateUser("" + user.getId());
			return activityService.deleteActiveList(entity);
		} catch (Exception e) {
			logger.error("删除活动信息出错", e);
		}
		return 0;
	}

	private MerchantActiveInf getMerchantActiveInf(HttpServletRequest req) throws Exception {
		MerchantActiveInf entity = null;
		String id = StringUtils.nullToString(req.getParameter("activeId"));
		if (!StringUtils.isNullOrEmpty(id)) {
			entity = activityService.getMerchantActiveInfById(id);
		} else {
			entity = new MerchantActiveInf();
		}
		entity.setActiveId(req.getParameter("activeId"));
		entity.setActiveName(req.getParameter("activeName"));
		entity.setMchntCode(req.getParameter("mchntCode"));
		entity.setMchntName(req.getParameter("mchntName"));
		entity.setActiveStat(req.getParameter("activeStat"));
		entity.setStartTime(req.getParameter("startTime"));
		entity.setEndTime(req.getParameter("endTime"));
		
		return entity;
	}

}
