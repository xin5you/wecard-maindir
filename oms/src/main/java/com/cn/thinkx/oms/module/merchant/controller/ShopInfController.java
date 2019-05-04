package com.cn.thinkx.oms.module.merchant.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.module.city.model.CityInf;
import com.cn.thinkx.oms.module.city.service.CityInfService;
import com.cn.thinkx.oms.module.common.model.ImageManager;
import com.cn.thinkx.oms.module.common.service.ImageManagerService;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.model.ShopInf;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfService;
import com.cn.thinkx.oms.module.merchant.service.ShopInfService;
import com.cn.thinkx.oms.module.sys.model.Dictionary;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.OMSSellCardFlag;
import com.cn.thinkx.pms.base.utils.BaseConstants.OMSShopType;
import com.github.pagehelper.PageInfo;



@Controller
@RequestMapping(value = "merchant/shopInf")
public class ShopInfController extends BaseController {

	Logger logger = LoggerFactory.getLogger(ShopInfController.class);
	
	@Autowired
	@Qualifier("shopInfService")
	private ShopInfService shopInfService;
	
	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService merchantInfService;
	
	@Autowired
	@Qualifier("imageManagerService")
	private ImageManagerService imageManagerService;
	
	@Autowired
	@Qualifier("cityInfService")
	private CityInfService cityInfService;
	
	/**
	 * 商户列表查询
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listShopInf")
	public ModelAndView listShopInf(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("merchant/shopInf/listShopInf");
		String operStatus=StringUtils.nullToString(req.getParameter("operStatus"));
		//MCHNT_ACCOUNT_STAT
		PageInfo<ShopInf> pageList = null;
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		ShopInf shopInf=null;
		List<Dictionary> dictList=null;
		try {
			//商户开户状态字典值
			shopInf=getShopInfInfo(req,null);
			pageList=shopInfService.getShopInfPage(startNum,pageSize,shopInf);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表信息出错", e);
		}
		mv.addObject("dictList", dictList);
		mv.addObject("pageInfo", pageList);
		mv.addObject("operStatus", operStatus);
		mv.addObject("shopInf", shopInf);
		return mv;
	}
	
	
	@RequestMapping(value = "/intoAddShopInf")
	public ModelAndView intoAddShopInf(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("merchant/shopInf/addShopInf");

		Map<String, String> mapType = new HashMap<String,String>();
		for(OMSShopType st : OMSShopType.values()){
			mapType.put(st.getCode(), st.getName());
		}
		
		Map<String, String> mapCardFlag = new HashMap<String,String>();
		for(OMSSellCardFlag cf : OMSSellCardFlag.values()){
			mapCardFlag.put(cf.getCode(), cf.getName());
		}
		
		List<MerchantInf> mchntList= merchantInfService.getMerchantInfList(null);
		
		Map<String, ShopInf> shopList = shopInfService.findShopInfListFirstLevel(null);
		
		mv.addObject("shopList",shopList.values());
		mv.addObject("mchntList", mchntList);
		mv.addObject("mapType", mapType);
		mv.addObject("mapCardFlag", mapCardFlag);
		return mv;
	}
	
	/**
	 * 图片文件上传
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addshopInfUpload")
	public ModelAndView addshopInfUpload(HttpServletRequest req,HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView();

		return mv;
	}
	
	/**
	 * 商户添加提交
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addShopInfCommit")
	public ModelAndView addShopInfCommit(HttpServletRequest req,HttpServletResponse resp,
			@RequestParam(value = "fileShopInfImgs[]", required = false)MultipartFile[] fileShopInfImgs, 		//上传店铺照
//			@RequestParam(value = "fileShopMenuImgs[]", required = false)MultipartFile[] fileShopMenuImgs,		//上传商品实物照
			@RequestParam(value = "fileShopInStoreImgs[]", required = false)MultipartFile[] fileShopInStoreImgs //上传店内照
	) {
		ModelAndView mv = new ModelAndView("redirect:/merchant/shopInf/listShopInf.do");
		User user=getCurrUser(req);
		try{
			ShopInf shopInf=this.getShopInfInfo(req,user);
			shopInf.setCreateUser(user.getId().toString());
			shopInf.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
			String shopId=shopInfService.addShopInf(shopInf);
			
			if(StringUtils.isNotNull(shopId)){ 
//		
					ShopInf s1=shopInfService.getShopInfById(shopId);
					MerchantInf m1=merchantInfService.getMerchantInfById(s1.getMchntId());
					
					//上传店铺照
					imageManagerService.addUploadImange(m1.getMchntCode(),
														Constants.ImageApplicationEnum.Application20.getCode(), 
														s1.getShopCode(), 
														Constants.ImageApplicationTypeEnum.ApplicationType2001.getCode(), 
														fileShopInfImgs);
//					
//					//上传店内照
					imageManagerService.addUploadImange(m1.getMchntCode(),
														Constants.ImageApplicationEnum.Application20.getCode(), 
														s1.getShopCode(), 
														Constants.ImageApplicationTypeEnum.ApplicationType2002.getCode(), 
														fileShopInStoreImgs);
//					
////					//上传商品实物照
//					imageManagerService.addUploadImange(m1.getMchntCode(),
//														Constants.ImageApplicationEnum.Application20.getCode(),
//														s1.getShopCode(),
//														Constants.ImageApplicationTypeEnum.ApplicationType2003.getCode(), 
//														fileShopMenuImgs);
//				
					mv.addObject("operStatus", 1);
				}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	
		return mv;
	}
	
	
	/** 商户编辑页面
	 * @param req
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/intoEditshopInf")
	public ModelAndView intoEditshopInf(HttpServletRequest req, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("merchant/shopInf/editShopInf");
		String shopId=req.getParameter("shopId");
		ShopInf shop=shopInfService.getShopInfById(shopId);
		List<MerchantInf> mchntList= merchantInfService.getMerchantInfList(null);
		/***地区***/
		String province="";
		String city="";
		String district="";
		if(shop !=null){
			if(!StringUtils.isNullOrEmpty(shop.getProvince())){
				CityInf cityInf=cityInfService.getCityInfById(shop.getProvince());
				if(cityInf !=null){
				 province=cityInf.getCityName();
				}
				 cityInf=cityInfService.getCityInfById(shop.getCity());
				 if(cityInf !=null){
					 city=cityInf.getCityName();
				 }
				
				 cityInf=cityInfService.getCityInfById(shop.getDistrict());
				 if(cityInf !=null){
					 district=cityInf.getCityName();
				 }
			}
		}
		
		Map<String, String> mapType = new HashMap<String,String>();
		for(OMSShopType st : OMSShopType.values()){
			mapType.put(st.getCode(), st.getName());
		}
		
		Map<String, String> mapCardFlag = new HashMap<String,String>();
		for(OMSSellCardFlag cf : OMSSellCardFlag.values()){
			mapCardFlag.put(cf.getCode(), cf.getName());
		}
		mv.addObject("province", province);
		mv.addObject("city", city);
		mv.addObject("district", district);
		mv.addObject("mapType", mapType);
		mv.addObject("mapCardFlag", mapCardFlag);
		/****地区***/
		
		
		ImageManager im=new ImageManager();
		im.setApplication(Constants.ImageApplicationEnum.Application20.getCode());
		im.setApplicationId(shop.getShopCode());
		im.setApplicationType(Constants.ImageApplicationTypeEnum.ApplicationType2001.getCode());
		List<ImageManager> imgList=imageManagerService.getImageManagerPathList(im);
		mv.addObject("imgList2001", imgList);
		
		im.setApplicationType(Constants.ImageApplicationTypeEnum.ApplicationType2002.getCode());
		imgList=imageManagerService.getImageManagerPathList(im);
		mv.addObject("imgList2002", imgList);
		
//		im.setApplicationType(Constants.ImageApplicationTypeEnum.ApplicationType2003.getCode());
//		imgList=imageManagerService.getImageManagerPathList(im);
//		mv.addObject("imgList2003", imgList);
//		
		Map<String, ShopInf> shopList = shopInfService.findShopInfListFirstLevel(shop.getMchntId());
	    shopList.remove(shop.getShopCode());
		
		mv.addObject("shopList",shopList.values());
		
		mv.addObject("mchntList", mchntList);
		mv.addObject("shop", shop);
		return mv;
	}
	
	
	/**
	 * 商户编辑 提交
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editShopInfCommit")
	public ModelAndView editShopInfCommit(HttpServletRequest req, HttpServletResponse response,
			@RequestParam(value = "fileShopInfImgs[]", required = false)MultipartFile[] fileShopInfImgs, 		//上传店铺照
//			@RequestParam(value = "fileShopMenuImgs[]", required = false)MultipartFile[] fileShopMenuImgs,		//上传商品实物照
			@RequestParam(value = "fileShopInStoreImgs[]", required = false)MultipartFile[] fileShopInStoreImgs //上传店内照
		) {
		
		ModelAndView mv = new ModelAndView("redirect:/merchant/shopInf/listShopInf.do");
		User user=getCurrUser(req);
		try{
			ShopInf shopInf=this.getShopInfInfo(req,user);
			if(StringUtils.isEmpty(shopInf.getpShopCode())){
				shopInf.setpShopCode(shopInf.getShopCode());  //编辑时默认为门店code
			}
			int oper=shopInfService.updateShopInf(shopInf);
			if(oper >0){ 		//图片上传步骤
				
					ShopInf s1=shopInfService.getShopInfById(shopInf.getShopId());
					MerchantInf m1=merchantInfService.getMerchantInfById(s1.getMchntId());
				//上传店铺照
				imageManagerService.updateUploadImange(m1.getMchntCode(),
													Constants.ImageApplicationEnum.Application20.getCode(), 
													s1.getShopCode(), 
													Constants.ImageApplicationTypeEnum.ApplicationType2001.getCode(), 
													fileShopInfImgs);
				//上传店内照
				imageManagerService.addUploadImange(m1.getMchntCode(),
													Constants.ImageApplicationEnum.Application20.getCode(), 
													s1.getShopCode(), 
													Constants.ImageApplicationTypeEnum.ApplicationType2002.getCode(), 
													fileShopInStoreImgs);
//				//上传商品实物照
//				imageManagerService.addUploadImange(m1.getMchntCode(),
//													Constants.ImageApplicationEnum.Application20.getCode(),
//													s1.getShopCode(),
//													Constants.ImageApplicationTypeEnum.ApplicationType2003.getCode(), 
//													fileShopMenuImgs);
					mv.addObject("operStatus", 2);
		}
					
		}catch(Exception ex){
			ex.printStackTrace();
		}
	
		return mv;
	
	}

	/**
	 * 跳转门店详细页面
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoViewShopInf")
	public ModelAndView intoViewShopInf(HttpServletRequest req,HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView("merchant/shopInf/viewShopInf");
		String shopId=req.getParameter("shopId");
		ShopInf shop=shopInfService.getShopInfById(shopId);
		if(shop.getpShopCode()!=null)
		shop.setpShopName(shopInfService.getShopInfByCode(shop.getpShopCode()).getShopName());		
		MerchantInf merchantInf=merchantInfService.getMerchantInfById(shop.getMchntId());
		
		
		/***地区***/
		 StringBuffer sbf=new StringBuffer("");
		if(shop !=null){
			if(!StringUtils.isNullOrEmpty(shop.getProvince())){
				String province="";
				String city="";
				String district="";
				CityInf cityInf=cityInfService.getCityInfById(shop.getProvince());
				if(cityInf !=null){
				 province=cityInf.getCityName();
				}
				 cityInf=cityInfService.getCityInfById(shop.getCity());
				 if(cityInf !=null){
					 city=cityInf.getCityName();
				 }
				 cityInf=cityInfService.getCityInfById(shop.getDistrict());
				 if(cityInf !=null){
					 district=cityInf.getCityName();
				 }
				 sbf.append(province);
				if(!"市辖区".equals(city) && !"县".equals(city)){
					sbf.append(city);
				}
				sbf.append(district);
				
			}
		}
		ImageManager im=new ImageManager();
		im.setApplication(Constants.ImageApplicationEnum.Application20.getCode());
		im.setApplicationId(shop.getShopCode());
		im.setApplicationType(Constants.ImageApplicationTypeEnum.ApplicationType2001.getCode());
		List<ImageManager> imgList=imageManagerService.getImageManagerPathList(im);
		mv.addObject("imgList2001", imgList);
		
		im.setApplicationType(Constants.ImageApplicationTypeEnum.ApplicationType2002.getCode());
		imgList=imageManagerService.getImageManagerPathList(im);
		mv.addObject("imgList2002", imgList);
		
		Map<String, String> mapType = new HashMap<String,String>();
		for(OMSShopType st : OMSShopType.values()){
			mapType.put(st.getCode(), st.getName());
		}
		
		Map<String, String> mapCardFlag = new HashMap<String,String>();
		for(OMSSellCardFlag cf : OMSSellCardFlag.values()){
			mapCardFlag.put(cf.getCode(), cf.getName());
		}
//		
//		im.setApplicationType(Constants.ImageApplicationTypeEnum.ApplicationType2003.getCode());
//		imgList=imageManagerService.getImageManagerPathList(im);
//		mv.addObject("imgList2003", imgList);
		mv.addObject("city", sbf.toString());
		mv.addObject("shop", shop);
		mv.addObject("merchantInf", merchantInf);
		mv.addObject("mapType",	mapType);
		mv.addObject("mapCardFlag",	mapCardFlag);
		return mv;
	}
	
	/**
	 * 删除用户 commit
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteshopInfCommit")
	@ResponseBody
	public Map<String, Object> deleteshopInfCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		String shopId=req.getParameter("shopId");
		String shopCode = shopInfService.getShopInfById(shopId).getShopCode();
		if(!shopInfService.getShopInfListByPShopCode(shopCode).isEmpty()){
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "旗下有分店，请勿删除");
			return resultMap;
		}		
		try {
			shopInfService.deleteShopInf(shopId);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "删除商户失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}
	/**
	 * 列出该商户下一级门店
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/bindMchnt")
	@ResponseBody
	public Map<String,Object> bindMchnt(HttpServletRequest req, HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String mchntId=req.getParameter("mchntId");
		Map<String, ShopInf> shopList = shopInfService.findShopInfListFirstLevel(mchntId);
		if(!shopList.isEmpty()){
			resultMap.put("shopList", shopList.values());
		}
		return resultMap;
	} 
	/**
	* @Title: getShopInfInfo
	* @Description: 门店表封装
	* @param @param req
	* @param @return
	* @param @throws Exception
	* @return shopInf    返回类型
	* @throws
	*/ 
	private ShopInf getShopInfInfo(HttpServletRequest req,User user) throws Exception {
		ShopInf shopInf=null;
		String shopId=StringUtils.nullToString(req.getParameter("shopId"));
		if(!StringUtils.isNullOrEmpty(shopId)){
			shopInf=shopInfService.getShopInfById(shopId);
		}else{
			shopInf=new ShopInf();
		}
		shopInf.setShopId(shopId);
		shopInf.setShopCode(StringUtils.nullToString(req.getParameter("shopCode")));
		shopInf.setShopName(StringUtils.nullToString(req.getParameter("shopName")));
		shopInf.setShopType(StringUtils.nullToString(req.getParameter("shopType")));
		shopInf.setSellCardFlag(StringUtils.nullToString(req.getParameter("sellCardFlag")));
		shopInf.setMchntId(StringUtils.nullToString(req.getParameter("mchntId")));
		shopInf.setMchntName(StringUtils.nullToString(req.getParameter("mchntName")));
		shopInf.setMchntCode(StringUtils.nullToString(req.getParameter("mchntCode")));
		shopInf.setpShopCode(StringUtils.nullToString(req.getParameter("pShopCode")));
		if(!"".equals(shopInf.getpShopCode())){
			shopInf.setShopLevel(2);
		}else{
			shopInf.setShopLevel(1);

		}
		shopInf.setProvince(StringUtils.nullToString(req.getParameter("province")));
		shopInf.setCity(StringUtils.nullToString(req.getParameter("city")));
		shopInf.setDistrict(StringUtils.nullToString(req.getParameter("district")));
		shopInf.setShopAddr(StringUtils.nullToString(req.getParameter("address")));
		shopInf.setLatitude(StringUtils.nullToString(req.getParameter("latitude")));
		shopInf.setLongitude(StringUtils.nullToString(req.getParameter("longitude")));
		shopInf.setTelephone(StringUtils.nullToString(req.getParameter("telephone")));
		shopInf.setBusinessHours(StringUtils.nullToString(req.getParameter("businessHours")));
		shopInf.setEvaluate(StringUtils.nullToString(req.getParameter("evaluate")));
		shopInf.setRemarks(StringUtils.nullToString(req.getParameter("remarks")));
		if(user !=null){
			shopInf.setUpdateUser(user.getId().toString());
		}
		return shopInf;
	}
	
}
