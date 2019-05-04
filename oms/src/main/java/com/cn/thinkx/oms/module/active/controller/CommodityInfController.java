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
import com.cn.thinkx.oms.module.active.model.CommodityInf;
import com.cn.thinkx.oms.module.active.service.CommodityInfService;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "active/commodityInf")
public class CommodityInfController extends BaseController {

	@Autowired
	@Qualifier("commodityInfService")
	private CommodityInfService commodityInfService;

	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService merchantInfService;

	/**
	 * 商品列表查询
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listCommodities")
	public ModelAndView listCommodities(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("active/commodityInf/listCommodities");
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		try {
			CommodityInf commodityInf = getCommodityInfInfo(req);
			PageInfo<CommodityInf> pageList = commodityInfService.getCommodityInfPage(startNum, pageSize, commodityInf);
			mv.addObject("commodityInf", commodityInf);
			mv.addObject("commStatus", Constants.CommStatus.values());
			mv.addObject("pageInfo", pageList);
		} catch (Exception e) {
			logger.error("查询商品列表信息出错", e);
		}
		return mv;
	}

	@RequestMapping(value = "/intoAddCommodityInf")
	public ModelAndView intoAddCommodityInf(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("active/commodityInf/addCommodityInf");
		List<MerchantInf> mchntList = merchantInfService.getMchntProList();
		mv.addObject("mchntList", mchntList);
		mv.addObject("commStatus", Constants.CommStatus.values());
		return mv;
	}
	
	@RequestMapping("getCommodityInfById")
	@ResponseBody
	public CommodityInf getCommodityInfById(HttpServletRequest request) {
		String commId = request.getParameter("commId");
		CommodityInf commodityInf =  commodityInfService.getCommodityInfById(commId);
		commodityInf.setCommodityFacevalue(NumberUtils.RMBCentToYuan(commodityInf.getCommodityFacevalue()));
		commodityInf.setCommodityCost(NumberUtils.RMBCentToYuan(commodityInf.getCommodityCost()));
		return commodityInf;
	}
	
	@RequestMapping("getCommoditiesByMchntId")
	@ResponseBody
	public List<CommodityInf> getCommoditiesByMchntId(HttpServletRequest request) {
		CommodityInf comm = new CommodityInf();
		comm.setMerchantId(request.getParameter("mchntId"));
		List<CommodityInf> list = commodityInfService.findCommodityInfList(comm);
		for (CommodityInf obj : list) {
			obj.setCommodityFacevalue(NumberUtils.RMBCentToYuan(obj.getCommodityFacevalue()));
			obj.setCommodityCost(NumberUtils.RMBCentToYuan(obj.getCommodityCost()));
		}
		return list;
	}
	
	@RequestMapping(value = "/addCommodityInf")
	@ResponseBody
	public String addCommodityInf(HttpServletRequest req) {
		String commodityId = "";
		try {
			User user = getCurrUser(req);
			CommodityInf commodityInf = new CommodityInf();
			commodityInf.setMerchantId(req.getParameter("mchntId"));
			commodityInf.setProductCode(req.getParameter("productCode"));
			commodityInf.setCommodityName(req.getParameter("commodityName"));
			commodityInf.setCommodityFacevalue(NumberUtils.RMBYuanToCent(req.getParameter("commodityFacevalue")));
			commodityInf.setCommodityCost(NumberUtils.RMBYuanToCent(req.getParameter("commodityCost")));
			commodityInf.setDataStat(Constants.CommStatus.EFFECTIVE.getCode());
			commodityInf.setCreateUser("" + user.getId());
			commodityInf.setUpdateUser("" + user.getId());
			commodityId = commodityInfService.addCommodityInf(commodityInf);
		} catch (Exception e) {
			logger.error("插入商品信息出错", e);
		}
		return commodityId;
	}
	
	@RequestMapping(value = "/editCommodityInf")
	@ResponseBody
	public int editCommodityInf(HttpServletRequest req) {
		try {
			String commId = req.getParameter("commId");
			CommodityInf commodityInf = commodityInfService.getCommodityInfById(commId);
			if (commodityInf != null) {
				String commName = req.getParameter("commName");
				commodityInf.setCommodityName(commName);
				User user = getCurrUser(req);
				commodityInf.setUpdateUser("" + user.getId());
			} else {
				return 0;
			}
			return commodityInfService.updateCommodityInf(commodityInf);
		} catch (Exception e) {
			logger.error("编辑商品信息出错", e);
		}
		return 0;
	}
	
	@RequestMapping(value = "/deleteCommodityInf")
	@ResponseBody
	public int deleteCommodityInf(HttpServletRequest req) {
		String commId = req.getParameter("commId");
		try {
			return commodityInfService.deleteCommodityInf(commId);
		} catch (Exception e) {
			logger.error("删除商品信息出错", e);
		}
		return 0;
	}

	private CommodityInf getCommodityInfInfo(HttpServletRequest req) throws Exception {
		CommodityInf commodityInf = null;
		String commodityId = StringUtils.nullToString(req.getParameter("commodityId"));
		if (!StringUtils.isNullOrEmpty(commodityId)) {
			commodityInf = commodityInfService.getCommodityInfById(commodityId);
		} else {
			commodityInf = new CommodityInf();
		}
		commodityInf.setCommodityName(req.getParameter("commodityName"));
		commodityInf.setCommodityId(req.getParameter("commodityId"));
		commodityInf.setMchntCode(req.getParameter("mchntCode"));
		commodityInf.setMchntName(req.getParameter("mchntName"));
		commodityInf.setCommodityFacevalue(req.getParameter("commodityFacevalue"));
		
		User user = getCurrUser(req);
		commodityInf.setUpdateUser("" + user.getId());
		
		return commodityInf;
	}

}
