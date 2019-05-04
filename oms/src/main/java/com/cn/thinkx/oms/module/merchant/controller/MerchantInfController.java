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
import com.cn.thinkx.oms.module.common.model.ImageManager;
import com.cn.thinkx.oms.module.common.model.IndustryClassification;
import com.cn.thinkx.oms.module.common.service.ImageManagerService;
import com.cn.thinkx.oms.module.common.service.IndustryClassificationService;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.model.MerchantInfList;
import com.cn.thinkx.oms.module.merchant.model.ShopInf;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfListService;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfService;
import com.cn.thinkx.oms.module.merchant.service.ShopInfService;
import com.cn.thinkx.oms.module.sys.model.Dictionary;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.module.sys.service.DictionaryService;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.github.pagehelper.PageInfo;



@Controller
@RequestMapping(value = "merchant/merchantInf")
public class MerchantInfController extends BaseController {
	
	Logger logger = LoggerFactory.getLogger(MerchantInfController.class);
	
	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService merchantInfService;
	
	@Autowired
	@Qualifier("shopInfService")
	private ShopInfService shopInfService;
	
	@Autowired
	@Qualifier("dictionaryService")
	private DictionaryService dictionaryService;
	
	@Autowired
	@Qualifier("merchantInfListService")
	private MerchantInfListService merchantInfListService;
	
	@Autowired
	@Qualifier("industryClassificationService")
	private IndustryClassificationService industryClassificationService;
	
	@Autowired
	@Qualifier("imageManagerService")
	private ImageManagerService imageManagerService;
	
	/**
	 * 商户列表查询
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listMerchantInf")
	public ModelAndView listMerchantInf(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("merchant/merchantInf/listMerchantInf");
		String operStatus=StringUtils.nullToString(req.getParameter("operStatus"));
		//MCHNT_ACCOUNT_STAT
		PageInfo<MerchantInf> pageList = null;
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		MerchantInf merchantInf=null;
		List<Dictionary> dictList=null;
		try {
			//商户开户状态字典值
			dictList=dictionaryService.getDictionaryListByPCode(Constants.DictType.MCHNT_ACCOUNT_STAT.getCode());
			merchantInf=getMerchantInfInfo(req,null);
			pageList=merchantInfService.getMerchantInfPage(startNum,pageSize,merchantInf);
		} catch (Exception e) {
			
			logger.error("查询列表信息出错", e);
		}
		mv.addObject("mchntTypeList", BaseConstants.MchntTypeEnum.values());
		mv.addObject("dictList", dictList);
		mv.addObject("pageInfo", pageList);
		mv.addObject("operStatus", operStatus);
		mv.addObject("merchantInf", merchantInf);
		return mv;
	}
	
	
	@RequestMapping(value = "/intoAddMerchantInf")
	public ModelAndView intoAddMerchantInf(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("merchant/merchantInf/addMerchantInf");
		List<Dictionary> dictList=dictionaryService.getDictionaryListByPCode(Constants.DictType.MCHNT_ACCOUNT_STAT.getCode());
		List<IndustryClassification> industryList=industryClassificationService.findIndustryClassificationList(new IndustryClassification());
		
		mv.addObject("mchntTypeList", BaseConstants.MchntTypeEnum.values());
		mv.addObject("dictList", dictList);
		mv.addObject("industryList", industryList);
		return mv;
	}
	
	
	/**
	 * 检查商户的邀请码是否有效
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/checkMchtInviteCode")
	@ResponseBody
	public Map<String, Object> checkMchtInviteCode(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		try {
			String inviteCode=StringUtils.nullToString(req.getParameter("inviteCode"));
			String mchntId=StringUtils.nullToString(req.getParameter("mchntId"));
	
			MerchantInfList merchantInfList=merchantInfListService.getMerchantInfListByCode(inviteCode);
			if(merchantInfList !=null ){
				if(!mchntId.equals(merchantInfList.getMchntId())){
					resultMap.put("status", Boolean.FALSE);
					resultMap.put("msg", "当前邀请码已存在,请重新输入");
				}
			}
			
		} catch (Exception e) {
			
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "当前邀请码已存在,请重新输入");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}
	
	
	/**
	 * 图片文件上传
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addmerchantInfUpload")
	public ModelAndView addmerchantInfUpload(HttpServletRequest req,HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView();

		return mv;
	}
	
	/**
	 * 商户添加提交
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addmerchantInfCommit")
	public ModelAndView addmerchantInfCommit(HttpServletRequest req,HttpServletResponse resp,
			@RequestParam(value = "fileShopInfImgs[]", required = false)MultipartFile[] fileShopInfImgs, 		//上传店铺照
//			@RequestParam(value = "fileShopMenuImgs[]", required = false)MultipartFile[] fileShopMenuImgs,		//上传商品实物照
			@RequestParam(value = "fileShopInStoreImgs[]", required = false)MultipartFile[] fileShopInStoreImgs, //上传店内照
			
			@RequestParam(value = "brandLogos[]", required = false)MultipartFile[] brandLogos,  //商户品牌LOG照片
			@RequestParam(value = "insCodeCards[]", required = false)MultipartFile[] insCodeCards, //组织机构文件代码文件
			@RequestParam(value = "businessLicences[]", required = false)MultipartFile[] businessLicences, ///商户企业工商营业执照文件
			@RequestParam(value = "idCards[]", required = false)MultipartFile[] idCards  ///商户法人身份证正面照片

	) {
		ModelAndView mv = new ModelAndView("redirect:/merchant/merchantInf/listMerchantInf.do");
		User user=getCurrUser(req);
		try{
			MerchantInf merchantInf=this.getMerchantInfInfo(req,user);
			merchantInf.setCreateUser(user.getId().toString());
			merchantInf.setAccountStat("00");//开户状态
			
			MerchantInfList merchantInfList=this.getMerchantInfListInfo(req,user);
			merchantInfList.setCreateUser(user.getId().toString());
			merchantInfList.setInviteCodeStat("0");
			
			ShopInf shopInf=this.getShopInfInfo(req,user);
			shopInf.setCreateUser(user.getId().toString());
			
			Map<String, Object> resMap=merchantInfService.insertMerchantInf(merchantInf, merchantInfList, shopInf);
			
			if(resMap !=null){ 
				//图片上传步骤
				if((boolean)resMap.get("status")){
					String mchntId=(String)resMap.get("mchntId");
					String shopId=(String)resMap.get("shopId");
					
					MerchantInf m1=merchantInfService.getMerchantInfById(mchntId);
					ShopInf s1=shopInfService.getShopInfById(shopId);
					
					//上传店铺照
					imageManagerService.addUploadImange(m1.getMchntCode(),
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
					
//					//上传商品实物照
//					imageManagerService.addUploadImange(m1.getMchntCode(),
//														Constants.ImageApplicationEnum.Application20.getCode(),
//														s1.getShopCode(),
//														Constants.ImageApplicationTypeEnum.ApplicationType2003.getCode(), 
//														fileShopMenuImgs);
					
					//商户品牌LOG照片
					imageManagerService.addUploadImange(m1.getMchntCode(),
														Constants.ImageApplicationEnum.Application10.getCode(),
														m1.getMchntCode(),
														Constants.ImageApplicationTypeEnum.ApplicationType1001.getCode(), 
														brandLogos);
					
					//商户组织机构文件代码文件
					imageManagerService.addUploadImange(m1.getMchntCode(),
														Constants.ImageApplicationEnum.Application10.getCode(),
														m1.getMchntCode(),
														Constants.ImageApplicationTypeEnum.ApplicationType1002.getCode(), 
														insCodeCards);
					
					//商户企业工商营业执照文件
					imageManagerService.addUploadImange(m1.getMchntCode(),
														Constants.ImageApplicationEnum.Application10.getCode(),
														m1.getMchntCode(),
														Constants.ImageApplicationTypeEnum.ApplicationType1003.getCode(), 
														businessLicences);
					
					
					//商户法人身份证正面照片
					imageManagerService.addUploadImange(m1.getMchntCode(),
														Constants.ImageApplicationEnum.Application10.getCode(),
														m1.getMchntCode(),
														Constants.ImageApplicationTypeEnum.ApplicationType1004.getCode(), 
														idCards);
					mv.addObject("operStatus", 1);
				}
				
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
	@RequestMapping(value = "/intoEditMerchantInf")
	public ModelAndView intoEditMerchantInf(HttpServletRequest req, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("merchant/merchantInf/editMerchantInf");
		String mchntId=req.getParameter("mchntId");
		MerchantInf merchantInf=merchantInfService.getMerchantInfById(mchntId);
		MerchantInfList mInflist=merchantInfListService.getMerchantInfoListByMchntId(mchntId);
		
		List<IndustryClassification> industryList=industryClassificationService.findIndustryClassificationList(new IndustryClassification());
		mv.addObject("industryList", industryList);
		
		
		ImageManager im=new ImageManager();
		im.setApplication(Constants.ImageApplicationEnum.Application10.getCode());
		im.setApplicationId(merchantInf.getMchntCode());
		
		im.setApplicationType(Constants.ImageApplicationTypeEnum.ApplicationType1001.getCode());
		List<ImageManager> imgList=imageManagerService.getImageManagerPathList(im);
		mv.addObject("imgList1001", imgList);
		
		im.setApplicationType(Constants.ImageApplicationTypeEnum.ApplicationType1002.getCode());
		imgList=imageManagerService.getImageManagerPathList(im);
		mv.addObject("imgList1002", imgList);
		
		im.setApplicationType(Constants.ImageApplicationTypeEnum.ApplicationType1003.getCode());
		imgList=imageManagerService.getImageManagerPathList(im);
		mv.addObject("imgList1003", imgList);
		
		im.setApplicationType(Constants.ImageApplicationTypeEnum.ApplicationType1004.getCode());
		imgList=imageManagerService.getImageManagerPathList(im);
		mv.addObject("imgList1004", imgList);
		
		mv.addObject("mchntTypeList", BaseConstants.MchntTypeEnum.values());
		mv.addObject("merchantInf", merchantInf);
		mv.addObject("mInflist", mInflist);
		return mv;
	}
	
	
	/**
	 * 商户编辑 提交
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editMerchantInfCommit")
	public ModelAndView editMerchantInfCommit(HttpServletRequest req, HttpServletResponse response,
			@RequestParam(value = "brandLogos[]", required = false)MultipartFile[] brandLogos,  //商户品牌LOG照片
			@RequestParam(value = "insCodeCards[]", required = false)MultipartFile[] insCodeCards, //组织机构文件代码文件
			@RequestParam(value = "businessLicences[]", required = false)MultipartFile[] businessLicences, ///商户企业工商营业执照文件
			@RequestParam(value = "idCards[]", required = false)MultipartFile[] idCards  ///商户法人身份证正面照片
		) {
		
		ModelAndView mv = new ModelAndView("redirect:/merchant/merchantInf/listMerchantInf.do");
		User user=getCurrUser(req);
		try{
			MerchantInf merchantInf=this.getMerchantInfInfo(req,user);
			MerchantInfList merchantInfList=this.getMerchantInfListInfo(req,user);
			int oper=merchantInfService.updateMerchantInf(merchantInf, merchantInfList);
			if(oper >0){ 		//图片上传步骤
					//商户品牌LOG照片
					imageManagerService.updateUploadImange(merchantInf.getMchntCode(),
														Constants.ImageApplicationEnum.Application10.getCode(),
														merchantInf.getMchntCode(),
														Constants.ImageApplicationTypeEnum.ApplicationType1001.getCode(), 
														brandLogos);
					//商户组织机构文件代码文件
					imageManagerService.updateUploadImange(merchantInf.getMchntCode(),
														Constants.ImageApplicationEnum.Application10.getCode(),
														merchantInf.getMchntCode(),
														Constants.ImageApplicationTypeEnum.ApplicationType1002.getCode(), 
														insCodeCards);
					//商户企业工商营业执照文件
					imageManagerService.updateUploadImange(merchantInf.getMchntCode(),
														Constants.ImageApplicationEnum.Application10.getCode(),
														merchantInf.getMchntCode(),
														Constants.ImageApplicationTypeEnum.ApplicationType1003.getCode(), 
														businessLicences);
					//商户法人身份证正面照片
					imageManagerService.updateUploadImange(merchantInf.getMchntCode(),
														Constants.ImageApplicationEnum.Application10.getCode(),
														merchantInf.getMchntCode(),
														Constants.ImageApplicationTypeEnum.ApplicationType1004.getCode(), 
														idCards);
					mv.addObject("operStatus", 2);
			}
					
		}catch(Exception ex){
			ex.printStackTrace();
		}
	
		return mv;
	
	}
	
	/** 商户详情页面
	 * @param req
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/intoViewMerchantInf")
	public ModelAndView intoViewMerchantInf(HttpServletRequest req, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("merchant/merchantInf/viewMerchantInf");
		String mchntId=req.getParameter("mchntId");
		MerchantInf merchantInf=merchantInfService.getMerchantInfById(mchntId);
		MerchantInfList mInflist=merchantInfListService.getMerchantInfoListByMchntId(mchntId);
		
		List<IndustryClassification> industryList=industryClassificationService.findIndustryClassificationList(new IndustryClassification());
		mv.addObject("industryList", industryList);
		
		
		ImageManager im=new ImageManager();
		im.setApplication(Constants.ImageApplicationEnum.Application10.getCode());
		im.setApplicationId(merchantInf.getMchntCode());
		
		im.setApplicationType(Constants.ImageApplicationTypeEnum.ApplicationType1001.getCode());
		List<ImageManager> imgList=imageManagerService.getImageManagerPathList(im);
		mv.addObject("imgList1001", imgList);
		
		im.setApplicationType(Constants.ImageApplicationTypeEnum.ApplicationType1002.getCode());
		imgList=imageManagerService.getImageManagerPathList(im);
		mv.addObject("imgList1002", imgList);
		
		im.setApplicationType(Constants.ImageApplicationTypeEnum.ApplicationType1003.getCode());
		imgList=imageManagerService.getImageManagerPathList(im);
		mv.addObject("imgList1003", imgList);
		
		im.setApplicationType(Constants.ImageApplicationTypeEnum.ApplicationType1004.getCode());
		imgList=imageManagerService.getImageManagerPathList(im);
		mv.addObject("imgList1004", imgList);
		
		mv.addObject("mchntTypeList", BaseConstants.MchntTypeEnum.values());
		mv.addObject("merchantInf", merchantInf);
		mv.addObject("mInflist", mInflist);
		return mv;
	}
	
	/**
	 * 删除用户 commit
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteMerchantInfCommit")
	@ResponseBody
	public Map<String, Object> deleteMerchantInfCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		String mchntId=req.getParameter("mchntId");
		try {
			merchantInfService.deleteMerchantInf(mchntId);
		} catch (Exception e) {
			
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "删除商户失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}
	
	
	/**
	* @Title: getMerchantInfInfo
	* @Description: 商户表封装
	* @param @param req
	* @param @return
	* @param @throws Exception
	* @return merchantInf    返回类型
	* @throws
	*/ 
	private MerchantInf getMerchantInfInfo(HttpServletRequest req,User user) throws Exception {
		
		MerchantInf merchantInf=null;
		String mchntId=StringUtils.nullToString(req.getParameter("mchntId"));
		if(!StringUtils.isNullOrEmpty(mchntId)){
			merchantInf=merchantInfService.getMerchantInfById(mchntId);
		}else{
			merchantInf=new MerchantInf();
		}
		merchantInf.setMchntId(mchntId);
		String mchntCode=StringUtils.nullToString(req.getParameter("mchntCode"));
		if(!StringUtil.isNullOrEmpty(mchntCode)){
			merchantInf.setMchntCode(mchntCode);
		}
		merchantInf.setMchntType(StringUtils.nullToString(req.getParameter("mchntType1")));
		merchantInf.setMchntName(StringUtils.nullToString(req.getParameter("mchntName")));
		merchantInf.setIndustryType1(StringUtils.nullToString(req.getParameter("industryType1")));
		merchantInf.setAccountStat(StringUtils.nullToString(req.getParameter("accountStat")));
		if(user !=null){
			merchantInf.setUpdateUser(user.getId().toString());
		}
		return merchantInf;
	}

	/**
	* @Title: getMerchantInfListInfo
	* @Description: 商户详情表封装
	* @param @param req
	* @param @return
	* @param @throws Exception
	* @return merchantInf    返回类型
	* @throws
	*/ 
	private MerchantInfList getMerchantInfListInfo(HttpServletRequest req,User user) throws Exception {
		MerchantInfList mchntListInf=null;
		String mchntId=StringUtils.nullToString(req.getParameter("mchntId"));
		if(!StringUtils.isNullOrEmpty(mchntId)){
			mchntListInf=merchantInfListService.getMerchantInfoListByMchntId(mchntId);
		}else{
			mchntListInf=new MerchantInfList();
		}
		mchntListInf.setMchntId(mchntId);
		mchntListInf.setMchntType(StringUtils.nullToString(req.getParameter("mchntType")));
		mchntListInf.setMchntName(StringUtils.nullToString(req.getParameter("mListmchntName")));
		mchntListInf.setMchntCode(StringUtils.nullToString(req.getParameter("mListmchntCode")));
		mchntListInf.setBusLicenceCode(StringUtils.nullToString(req.getParameter("busLicenceCode")));
		mchntListInf.setName(StringUtils.nullToString(req.getParameter("name")));
		mchntListInf.setIdCardNo(StringUtils.nullToString(req.getParameter("idCardNo")));
		mchntListInf.setPhoneNumber(StringUtils.nullToString(req.getParameter("phoneNumber")));
		mchntListInf.setBackName(StringUtils.nullToString(req.getParameter("backName")));
		mchntListInf.setBackAct(StringUtils.nullToString(req.getParameter("backAct")));
		mchntListInf.setBackActName(StringUtils.nullToString(req.getParameter("backActName")));
		mchntListInf.setInsScale(StringUtils.nullToString(req.getParameter("insScale")));
		
		mchntListInf.setBrandName(StringUtils.nullToString(req.getParameter("brandName")));
		mchntListInf.setBrandLogo(StringUtils.nullToString(req.getParameter("brandLogo")));
		mchntListInf.setInviteCode(StringUtils.nullToString(req.getParameter("inviteCode")));
		mchntListInf.setRemarks(StringUtils.nullToString(req.getParameter("mListRemarks")));
		if(user !=null){
			mchntListInf.setUpdateUser(user.getId().toString());
		}
		return mchntListInf;
	}
	
	/**
	* @Title: getShopInfInfo
	* @Description: 门店表封装
	* @param @param req
	* @param @return
	* @param @throws Exception
	* @return merchantInf    返回类型
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
		shopInf.setShopName(StringUtils.nullToString(req.getParameter("shopName")));
		shopInf.setProvince(StringUtils.nullToString(req.getParameter("province")));
		shopInf.setCity(StringUtils.nullToString(req.getParameter("city")));
		shopInf.setDistrict(StringUtils.nullToString(req.getParameter("district")));
		shopInf.setShopAddr(StringUtils.nullToString(req.getParameter("address")));
		shopInf.setLatitude(StringUtils.nullToString(req.getParameter("latitude")));
		shopInf.setLongitude(StringUtils.nullToString(req.getParameter("longitude")));
		shopInf.setTelephone(StringUtils.nullToString(req.getParameter("telephone")));
		shopInf.setBusinessHours(StringUtils.nullToString(req.getParameter("businessHours")));
		shopInf.setEvaluate(StringUtils.nullToString(req.getParameter("evaluate")));
		if(user !=null){
			shopInf.setUpdateUser(user.getId().toString());
		}
		return shopInf;
	}
	

	
	
}
