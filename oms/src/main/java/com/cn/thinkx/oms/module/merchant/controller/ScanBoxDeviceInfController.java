package com.cn.thinkx.oms.module.merchant.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.channel.model.PaymentChannel;
import com.cn.thinkx.oms.module.channel.service.PaymentChannelService;
import com.cn.thinkx.oms.module.merchant.model.InsInf;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.model.ScanBoxDeviceInf;
import com.cn.thinkx.oms.module.merchant.model.ShopInf;
import com.cn.thinkx.oms.module.merchant.service.InsInfService;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfService;
import com.cn.thinkx.oms.module.merchant.service.ScanBoxDeviceInfService;
import com.cn.thinkx.oms.module.merchant.service.ShopInfService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.NumberUtils;
import com.cn.thinkx.oms.util.StringUtils;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "merchant/scanBoxDeviceInf")
public class ScanBoxDeviceInfController extends BaseController {

	Logger logger = LoggerFactory.getLogger(ScanBoxDeviceInfController.class);

	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService merchantInfService;

	@Autowired
	@Qualifier("shopInfService")
	private ShopInfService shopInfService;

	@Autowired
	@Qualifier("scanBoxDeviceInfService")
	private ScanBoxDeviceInfService scanBoxDeviceInfService;

	@Autowired
	@Qualifier("insInfService")
	private InsInfService insInfService;

	@Autowired
	@Qualifier("paymentChannelService")
	private PaymentChannelService paymentChannelService;

	/*
	 * 返回查询一览画面
	 */
	@RequestMapping(value = "/listScanBoxDeviceInf")
	public ModelAndView listScanBoxDeviceInf(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("merchant/scanBoxDeviceInf/listScanBoxDeviceInf");
		String operStatus = StringUtils.nullToString(req.getParameter("operStatus"));
		String mchntCode = StringUtils.nullToString(req.getParameter("mchntCode"));
		String mchntName = StringUtils.nullToString(req.getParameter("mchntName"));
		String shopCode = StringUtils.nullToString(req.getParameter("shopCode"));
		String shopName = StringUtils.nullToString(req.getParameter("shopName"));
		String deviceNo = StringUtils.nullToString(req.getParameter("deviceNo"));
		PageInfo<ScanBoxDeviceInf> pageList = null;
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		ScanBoxDeviceInf scanBoxDeviceInf = new ScanBoxDeviceInf();
		scanBoxDeviceInf.setMchntCode(mchntCode);
		scanBoxDeviceInf.setMchntName(mchntName);
		scanBoxDeviceInf.setShopCode(shopCode);
		scanBoxDeviceInf.setShopName(shopName);
		scanBoxDeviceInf.setDeviceNo(deviceNo);
		try {
			pageList = scanBoxDeviceInfService.getScanBoxDeviceInfListPage(startNum, pageSize, scanBoxDeviceInf);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表信息出错", e);
		}

		mv.addObject("pageInfo", pageList);
		mv.addObject("operStatus", operStatus);
		mv.addObject("scanBoxDeviceInf", scanBoxDeviceInf);
		return mv;
	}

	/**
	 * 添加设备盒子（Into）
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addScanBoxDeviceInf")
	public ModelAndView intoAddScanBoxDeviceInf(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("merchant/scanBoxDeviceInf/addScanBoxDeviceInf");
		List<MerchantInf> mchntList = merchantInfService.getMerchantInfListBySelect();
		PaymentChannel pc = new PaymentChannel();
		List<PaymentChannel> pcList = paymentChannelService.getPaymentChannelsList(pc);
		mv.addObject("pcList", pcList);
		mv.addObject("mchntList", mchntList);
		return mv;
	}

	/**
	 * 获取商户下的所有一级门店
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoAddMchntEshopInfGetShop1")
	@ResponseBody
	public ModelMap intoAddMchntEshopInfGetShop1(HttpServletRequest req, HttpServletResponse response) {
		ModelMap map = new ModelMap();
		map.addAttribute("status", Boolean.TRUE);
		String mchntCode = req.getParameter("mchntCode");
		ShopInf shopInf = new ShopInf();
		shopInf.setMchntCode(mchntCode);
		MerchantInf mchntInf = merchantInfService.getMerchantInfByMchntCode(mchntCode);
		Map<String, ShopInf> shopInfMap = shopInfService.findShopInfListFirstLevel(mchntInf.getMchntId()); // 获取所有的一级门店
		map.addAttribute("shopInfList", shopInfMap.values());
		return map;
	}

	/**
	 * 通过一级门店的code查看旗下的所有二级门店
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoAddMchntEshopInfGetShop2")
	@ResponseBody
	public ModelMap intoAddMchntEshopInfGetShop2(HttpServletRequest req, HttpServletResponse response) {
		ModelMap map = new ModelMap();
		map.addAttribute("status", Boolean.TRUE);
		String pShopCode = StringUtils.nullToString(req.getParameter("shopCode1"));
		List<ShopInf> shopInfList = shopInfService.getShopInfListByPShopCode(pShopCode); // 获取所有的二级门店
		map.addAttribute("shopInfList", shopInfList);
		return map;
	}

	/**
	 * 添加扫码盒子设备信息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/addScanBoxDeviceInfCommit")
	@ResponseBody
	public ModelMap addScanBoxDeviceInfCommit(HttpServletRequest req) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		try {
			ScanBoxDeviceInf scanBoxDeviceInf = this.getScanBoxDeviceInfTemp(req);
			User user = this.getCurrUser(req);
			user.getLoginname();
			if (user != null) {
				scanBoxDeviceInf.setCreateUser(user.getId().toString());
				scanBoxDeviceInf.setUpdateUser(user.getId().toString());
			}
			scanBoxDeviceInfService.insertScanBoxDeviceInf(scanBoxDeviceInf);
			resultMap.addAttribute("operStatus", "1");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "新增失败，请重新添加");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}

	/**
	 * 删除扫码盒子设备信息（物理删除）
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/deleteScanBoxDeviceInfByDeviceId")
	@ResponseBody
	public ModelMap deleteScanBoxDeviceInfByDeviceId(HttpServletRequest req) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		String deviceId = StringUtils.nullToString(req.getParameter("deviceId"));
		try {
			scanBoxDeviceInfService.deleteScanBoxDeviceInfByDeviceId(deviceId);
		} catch (Exception e) {
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "删除失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}

	/**
	 * 通过deviceId查询对应的数据信息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/intoViewScanBoxDeviceInf")
	public ModelAndView intoViewScanBoxDeviceInf(HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("merchant/scanBoxDeviceInf/viewScanBoxDeviceInf");
		ScanBoxDeviceInf scanBoxDeviceInf = null;
		String deviceId = StringUtils.nullToString(req.getParameter("deviceId"));
		scanBoxDeviceInf = scanBoxDeviceInfService.getScanBoxDeviceInfByDeviceId(deviceId);
		ShopInf s = shopInfService.getShopInfByCode(StringUtils.nullToString(StringUtils.nullToString(scanBoxDeviceInf.getShopCode()))); // 通过二级门店code获取的二级门店信息
		ShopInf shopInf = shopInfService.getShopInfByCode(StringUtils.nullToString(StringUtils.nullToString(s.getpShopCode()))); // 通过二级门店code获取的二级门店信息
		PaymentChannel pc = new PaymentChannel();
		List<PaymentChannel> pcList = paymentChannelService.getPaymentChannelsList(pc);
		mv.addObject("pcList", pcList);
		mv.addObject("shopInf", shopInf);
		mv.addObject("scanBoxDeviceInf", scanBoxDeviceInf);
		return mv;
	}

	/**
	 * 在列表画面点击进入编辑画面
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/editScanBoxDeviceInf")
	public ModelAndView intoEditScanBoxDeviceInf(HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("merchant/scanBoxDeviceInf/editScanBoxDeviceInf");
		ScanBoxDeviceInf scanBoxDeviceInf = null;
		String deviceId = StringUtils.nullToString(req.getParameter("deviceId"));
		scanBoxDeviceInf = scanBoxDeviceInfService.getScanBoxDeviceInfByDeviceId(deviceId);
		// 获取所有商户
		List<MerchantInf> mchntList = merchantInfService.getMerchantInfListBySelect();
		// 获取所有的一级门店
		List<ShopInf> shopInfList1 = shopInfService.getShopInfListByMchntCode(StringUtils.nullToString(scanBoxDeviceInf.getMchntCode())); // 获取所有的一级门店
		// 获取所有的二级门店
		ShopInf shopInf = shopInfService.getShopInfByCode(StringUtils.nullToString(scanBoxDeviceInf.getShopCode())); // 通过二级门店code获取的二级门店信息
		List<ShopInf> shopInfList2 = shopInfService.getShopInfListByPShopCode(StringUtils.nullToString(shopInf.getpShopCode())); // 通过二级门店中pShopCode获取二级门店的信息
		// 获取支付通道信息
		PaymentChannel pc = new PaymentChannel();
		List<PaymentChannel> pcList = paymentChannelService.getPaymentChannelsList(pc);
		mv.addObject("pcList", pcList);
		mv.addObject("shopInfList1", shopInfList1);
		mv.addObject("shopInfList2", shopInfList2);
		mv.addObject("shopInf", shopInf);
		mv.addObject("mchntList", mchntList);
		mv.addObject("scanBoxDeviceInf", scanBoxDeviceInf);
		return mv;
	}

	/*
	 * 编辑提交扫码盒子设备信息
	 */
	@RequestMapping(value = "/editScanBoxDeviceInfCommit")
	@ResponseBody
	public ModelMap editScanBoxDeviceInfCommit(HttpServletRequest req) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		try {
			ScanBoxDeviceInf scanBoxDeviceInf = this.getScanBoxDeviceInfTemp(req);
			scanBoxDeviceInf.setDeviceId(req.getParameter("deviceId"));
			User user = this.getCurrUser(req);
			user.getLoginname();
			if (user != null) {
				scanBoxDeviceInf.setUpdateUser(user.getId().toString());
			}
			scanBoxDeviceInfService.editScanBoxDeviceInf(scanBoxDeviceInf);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "编辑失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}

	/**
	 * 验证设备类型和设备号是否唯一
	 * 
	 */
	@RequestMapping(value = "/getScanBoxDeviceInfByDeviceTypeAndDeviceNo")
	@ResponseBody
	public ModelMap getScanBoxDeviceInfByDeviceTypeAndDeviceNo(HttpServletRequest req) {
		ModelMap resultMap = new ModelMap();
		String state = req.getParameter("state");
		String deviceId = req.getParameter("deviceId");
		ScanBoxDeviceInf scanBoxDeviceInf = new ScanBoxDeviceInf();
		scanBoxDeviceInf.setDeviceType(StringUtils.nullToString(req.getParameter("deviceType")));
		scanBoxDeviceInf.setDeviceNo(StringUtils.nullToString(req.getParameter("deviceNo")));
		scanBoxDeviceInf.setDeviceId(deviceId);
		List<ScanBoxDeviceInf> list = new ArrayList<ScanBoxDeviceInf>();
		// 获取未删除的设备号
		list = scanBoxDeviceInfService.getScanBoxDeviceInfByDeviceTypeAndDeviceNo(scanBoxDeviceInf);
		if (list.size() == 0 && "1".equals(state)) {
			resultMap.addAttribute("status", Boolean.TRUE);
		} else if ("1".equals(state) && list.size() > 0) {
			resultMap.addAttribute("status", Boolean.FALSE);
		} else if (list.size() == 1 && "2".equals(state)) { // 编辑画面的判断
			String deviceIdTemp = "";
			for (ScanBoxDeviceInf scanBoxDeviceInf2 : list) {
				deviceIdTemp = scanBoxDeviceInf2.getDeviceId();
				if (deviceIdTemp.equals(deviceId)) {
					resultMap.addAttribute("status", Boolean.TRUE);
				} else {
					resultMap.addAttribute("status", Boolean.FALSE);
				}
			}
		} else {
			// 删除的 设备号和设备类型
			resultMap.addAttribute("status", Boolean.TRUE);
		}
		return resultMap;
	}

	/*
	 * 封装扫码盒子设备信息表信息
	 * 
	 */
	public ScanBoxDeviceInf getScanBoxDeviceInfTemp(HttpServletRequest req) {
		ScanBoxDeviceInf scanBoxDeviceInf = new ScanBoxDeviceInf();
		scanBoxDeviceInf.setDeviceType(StringUtils.nullToString(req.getParameter("deviceType")));
		scanBoxDeviceInf.setDeviceNo(StringUtils.nullToString(req.getParameter("deviceNo")));
		String mchntCode = StringUtils.nullToString(req.getParameter("mchntCode"));
		scanBoxDeviceInf.setMchntCode(mchntCode);
		// 通过商户号查找机构id
		MerchantInf merchInf = null;
		if (!StringUtils.isNullOrEmpty(mchntCode)) {
			merchInf = scanBoxDeviceInfService.getMerchantInfByMchntCode(req.getParameter("mchntCode"));
		}
		// 通过机构id查找机构号
		InsInf insInf = null;
		if (merchInf != null) {
			insInf = insInfService.getInsInfById(merchInf.getInsId());
		}
		if (insInf != null) {
			// 机构号--
			scanBoxDeviceInf.setInsCode(StringUtils.nullToString(insInf.getInsCode()));
		}
		scanBoxDeviceInf.setShopCode(StringUtils.nullToString(req.getParameter("shopCode")));
		scanBoxDeviceInf.setFixedPayFlag(StringUtils.nullToString(req.getParameter("fixedPayFlag")));
		scanBoxDeviceInf
				.setFixedPayAmt(StringUtils.nullToString(NumberUtils.RMBYuanToCent(req.getParameter("fixedPayAmt"))));
		scanBoxDeviceInf.setPrint(StringUtils.nullToString(req.getParameter("print")));
		scanBoxDeviceInf.setPrintQr(StringUtils.nullToString(req.getParameter("printQr")));
		scanBoxDeviceInf.setPrintType(StringUtils.nullToString(req.getParameter("printType")));
		scanBoxDeviceInf.setChannelNo(StringUtils.nullToString(req.getParameter("channelNo")));
		// 状态-->0正常，1不正常
		scanBoxDeviceInf.setDataStat("0");
		scanBoxDeviceInf.setReceipt(StringUtils.nullToString(req.getParameter("receipt")));
		scanBoxDeviceInf.setRemarks(StringUtils.nullToString(req.getParameter("remarks")));
		return scanBoxDeviceInf;
	}

}
