package com.cn.thinkx.oms.module.cardkeys.controller;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.common.redis.util.RedisDictProperties;
import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.constants.Constants.ProductUnit;
import com.cn.thinkx.oms.module.cardkeys.model.CardKeysProduct;
import com.cn.thinkx.oms.module.cardkeys.service.CardKeysProductService;
import com.cn.thinkx.oms.module.common.service.ImageManagerService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.NumberUtils;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "cardKeys")
public class CardKeysProductController extends BaseController {

	Logger logger = LoggerFactory.getLogger(CardKeysProductController.class);

	@Autowired
	@Qualifier("cardKeysProductService")
	private CardKeysProductService cardKeysProductService;
	
	@Autowired
	@Qualifier("imageManagerService")
	private ImageManagerService imageManagerService;

	/**
	 * 查询卡密产品列表
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listCardKeysProduct")
	public ModelAndView listCardKeysProduct(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("enterpriseOrder/cardKeys/listCardKeysProduct");
		String productCode = StringUtils.nullToString(req.getParameter("productCode"));
		String productName = StringUtils.nullToString(req.getParameter("productName"));
		String productType = StringUtils.nullToString(req.getParameter("productType"));
		String isPutaway = StringUtils.nullToString(req.getParameter("isPutaway"));
		String operStatus = StringUtils.nullToString(req.getParameter("operStatus"));
		PageInfo<CardKeysProduct> pageList = null;

		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);

		CardKeysProduct ckp = new CardKeysProduct();
		ckp.setProductCode(productCode);
		ckp.setProductName(productName);
		ckp.setProductType(productType);
		ckp.setIsPutaway(isPutaway);

		try {
			pageList = cardKeysProductService.getCardKeysProductPage(startNum, pageSize, ckp);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询卡密产品列表信息出错", e);
		}
		mv.addObject("pageInfo", pageList);
		mv.addObject("cardKeysProduct", ckp);
		mv.addObject("operStatus", operStatus);
		mv.addObject("productTypeList", BaseConstants.CardProductType.values());
		mv.addObject("isPutawayList", Constants.isPutaway.values());
		mv.addObject("dataStatList", Constants.isPutaway.values());
		return mv;
	}
	
	/**
	 * 新增卡密产品信息
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoAddCardKeysProduct")
	public ModelAndView intoAddUser(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("enterpriseOrder/cardKeys/addCardKeysProduct");
		mv.addObject("productTypeList", BaseConstants.CardProductType.values());
		mv.addObject("productUnitList", Constants.ProductUnit.values());
		return mv;
	}

	/**
	 * 新增卡密产品信息提交
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addCardKeysProductCommit")
	@ResponseBody
	public ModelMap addCardKeysProductCommit(HttpServletRequest req, HttpServletResponse response) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		
		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) req;  
        MultipartFile cardKeysLogo = multipartRequest.getFile("logoUrl");//LOGO
        
		User user = getCurrUser(req);
		try {
			CardKeysProduct ckp = getCardKeysProduct(req, user);
			//上传LOGO
			String logoUrl = imageManagerService.addUploadImange(ckp.getProductCode(),
						Constants.ImageApplicationEnum.Application50.getCode(), 
						Constants.ImageApplicationTypeEnum.ApplicationType5001.getCode(), 
						cardKeysLogo);
			ckp.setLogoUrl(logoUrl);
			ckp.setIsPutaway(Constants.isPutaway.P0.getCode());
			cardKeysProductService.insertCardKeysProduct(ckp);
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
	 * 
	 * 编辑卡密产品信息
	 * @param req
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/intoEditCardKeysProduct")
	public ModelAndView intoEditCardKeysProduct(HttpServletRequest req, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("enterpriseOrder/cardKeys/editCardKeysProduct");
		String productCode = StringUtils.nullToString(req.getParameter("productCode"));
		
		CardKeysProduct cardKeysProduct = cardKeysProductService.getCardKeysProductById(productCode);
		if (cardKeysProduct != null) {
			cardKeysProduct.setOrgAmount(NumberUtils.RMBCentToYuan(cardKeysProduct.getOrgAmount()));
			cardKeysProduct.setAmount(NumberUtils.RMBCentToYuan(cardKeysProduct.getAmount()));
			cardKeysProduct.setLogoUrl(RedisDictProperties.getInstance().getdictValueByCode("HKB_URL_IMG") + cardKeysProduct.getLogoUrl());
		}
		mv.addObject("cardKeysProduct", cardKeysProduct);
		mv.addObject("productTypeList", BaseConstants.CardProductType.values());
		mv.addObject("productUnitList", Constants.ProductUnit.values());
		return mv;
	}

	/**
	 * 编辑卡密产品信息提交
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editCardKeysProductCommit")
	@ResponseBody
	public ModelMap editCardKeysProductCommit(HttpServletRequest req, HttpServletResponse response) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		
		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) req;  
        MultipartFile cardKeysLogo = multipartRequest.getFile("logoUrl");//LOGO
        
		User user = getCurrUser(req);
		try {
			CardKeysProduct cardKeysProduct = getCardKeysProduct(req, user);
			String logoUrl = null;
			//上传LOGO
			if(cardKeysLogo != null){
				logoUrl = imageManagerService.addUploadImange(cardKeysProduct.getProductCode(),
						Constants.ImageApplicationEnum.Application50.getCode(), 
						Constants.ImageApplicationTypeEnum.ApplicationType5001.getCode(), 
						cardKeysLogo);
				cardKeysProduct.setLogoUrl(logoUrl);
			}
			cardKeysProductService.updateCardKeysProduct(cardKeysProduct);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "编辑失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}
	
	/**
	 * 查询卡密产品上下架状态
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getProductIsPutaway")
	@ResponseBody
	public CardKeysProduct getProductIsPutaway(HttpServletRequest req, HttpServletResponse response){
		String productCode = StringUtils.nullToString(req.getParameter("productCode"));
		CardKeysProduct ckp = cardKeysProductService.getCardKeysProductById(productCode);
		return ckp;
	}
	
	/**
	 * 卡密产品上下架提交
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/pencilCardKeysProductCommit")
	@ResponseBody
	public int pencilCardKeysProductCommit(HttpServletRequest req) {
		try {
			String productCode = req.getParameter("productCode");
			String isPutaway = req.getParameter("isPutaway");
			CardKeysProduct cardKeysProduct = new CardKeysProduct();
			CardKeysProduct ckp = cardKeysProductService.getCardKeysProductById(productCode);
			if (ckp != null) {
				cardKeysProduct.setProductCode(productCode);
				cardKeysProduct.setIsPutaway(isPutaway);
			} else {
				return 0;
			}
			return cardKeysProductService.updateStandUpAndDown(cardKeysProduct);
		} catch (Exception e) {
			logger.error("卡密产品上下架出错", e);
		}
		return 0;
	}

	/**
	 * 删除卡密产品信息
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteCardKeysProductCommit")
	@ResponseBody
	public ModelMap deleteCardKeysProductCommit(HttpServletRequest req, HttpServletResponse response) {
		ModelMap resultMap = new ModelMap();
		resultMap.put("status", Boolean.TRUE);
		String productCode = StringUtils.nullToString(req.getParameter("productCode"));
		try {
			cardKeysProductService.deleteCardKeysProduct(productCode);
		} catch (Exception e) {
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "删除卡密产品失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}

	/**
	 * 查看卡密产品详情
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/intoViewCardKeysProduct")
	public ModelAndView intoViewMchntEshopInf(HttpServletRequest req,HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView("enterpriseOrder/cardKeys/viewCardKeysProduct");
		String productCode = StringUtils.nullToString(req.getParameter("productCode"));
		CardKeysProduct cardKeysProduct = cardKeysProductService.getCardKeysProductById(productCode);
		if (cardKeysProduct != null) {
			if (Constants.isPutaway.P0.getCode().equals(cardKeysProduct.getIsPutaway()))
				cardKeysProduct.setIsPutaway("已"+Constants.isPutaway.P0.getValue());
			else
				cardKeysProduct.setIsPutaway("已"+Constants.isPutaway.P1.getValue());
			
			cardKeysProduct.setProductType(BaseConstants.CardProductType.findByCode(cardKeysProduct.getProductType()).getValue());
			cardKeysProduct.setAmount(String.valueOf(NumberUtils.RMBCentToYuan(cardKeysProduct.getAmount())));
			cardKeysProduct.setOrgAmount(String.valueOf(NumberUtils.RMBCentToYuan(cardKeysProduct.getOrgAmount())));
			cardKeysProduct.setLogoUrl(RedisDictProperties.getInstance().getdictValueByCode("HKB_URL_IMG") + cardKeysProduct.getLogoUrl());
		}
		mv.addObject("cardKeysProduct", cardKeysProduct);
		return mv;
	}

	/**
	 * 卡密产品类封装方法
	 * 
	 * @param req
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private CardKeysProduct getCardKeysProduct(HttpServletRequest req, User user) throws Exception {
		CardKeysProduct cardKeysProduct = new CardKeysProduct();
		String check = StringUtils.nullToString(req.getParameter("check"));
		String productType = StringUtils.nullToString(req.getParameter("productType"));
		String productCode = null;
		if ("add".equals(check)) {
			if (BaseConstants.CardProductType.CP11.getCode().equals(productType) || BaseConstants.CardProductType.CP12.getCode().equals(productType))
				productCode = StringUtils.nullToString(req.getParameter("productCode"));
			else
				productCode = cardKeysProductService.getPrimaryKey();
		} else if ("update".equals(check)) {
			productCode = StringUtils.nullToString(req.getParameter("productCode"));
			if (!StringUtils.isNullOrEmpty(productCode))
				cardKeysProduct = cardKeysProductService.getCardKeysProductById(productCode);
		}
		cardKeysProduct.setProductCode(productCode);
		cardKeysProduct.setProductName(StringUtils.nullToString(req.getParameter("productName")));
		cardKeysProduct.setProductType(productType);
		cardKeysProduct.setOrgAmount(NumberUtils.RMBYuanToCent(StringUtils.nullToString(req.getParameter("orgAmount"))));
		cardKeysProduct.setProductUnit(ProductUnit.findByCode(req.getParameter("productUnit")).getValue());
		cardKeysProduct.setAmount(NumberUtils.RMBYuanToCent(StringUtils.nullToString(req.getParameter("amount"))));
		if (StringUtils.isNullOrEmpty(req.getParameter("totalNum")))
			cardKeysProduct.setTotalNum("0");
		else 
			cardKeysProduct.setTotalNum(StringUtils.nullToString(req.getParameter("totalNum")));
		
		cardKeysProduct.setAvailableNum(StringUtils.nullToString(req.getParameter("availableNum")));
		cardKeysProduct.setSupplier(StringUtils.nullToString(req.getParameter("supplier")));
		cardKeysProduct.setProductDesc(StringUtils.nullToString(req.getParameter("productDesc")));
		cardKeysProduct.setRemarks(StringUtils.nullToString(req.getParameter("remarks")));
		if(user != null){
			cardKeysProduct.setCreateUser(user.getId().toString());
			cardKeysProduct.setUpdateUser(user.getId().toString());
		}
		return cardKeysProduct;
	}

}
