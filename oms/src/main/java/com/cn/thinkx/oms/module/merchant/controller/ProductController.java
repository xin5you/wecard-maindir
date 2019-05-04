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
import com.cn.thinkx.oms.module.common.service.ImageManagerService;
import com.cn.thinkx.oms.module.key.model.KeyIndex;
import com.cn.thinkx.oms.module.key.model.KeyVersion;
import com.cn.thinkx.oms.module.key.service.KeyIndexService;
import com.cn.thinkx.oms.module.key.service.KeyVersionService;
import com.cn.thinkx.oms.module.merchant.model.InsInf;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.model.Product;
import com.cn.thinkx.oms.module.merchant.service.InsInfService;
import com.cn.thinkx.oms.module.merchant.service.MerchantContractListService;
import com.cn.thinkx.oms.module.merchant.service.MerchantContractService;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfService;
import com.cn.thinkx.oms.module.merchant.service.ProductService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.github.pagehelper.PageInfo;



@Controller
@RequestMapping(value = "merchant/product")
public class ProductController extends BaseController {
	
	Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	@Qualifier("productService")
	private ProductService productService;
	
	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService  merchantInfService;
	
	@Autowired
	@Qualifier("insInfService")
	private InsInfService insInfService;
	
	@Autowired
	@Qualifier("merchantContractService")
	private MerchantContractService  merchantContractService;
	
	@Autowired
	@Qualifier("merchantContractListService")
	private MerchantContractListService  merchantContractListService;
	
	@Autowired
	@Qualifier("keyVersionService")
	private KeyVersionService keyVersionService;
	
	@Autowired
	@Qualifier("keyIndexService")
	private KeyIndexService keyIndexService;
	
	@Autowired
	@Qualifier("imageManagerService")
	private ImageManagerService imageManagerService;
	
	/**
	 * 产品列表查询
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listProduct")
	public ModelAndView listProduct(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("merchant/product/listProduct");
		String operStatus=StringUtils.nullToString(req.getParameter("operStatus"));
		//定义产品列表
		PageInfo<Product> pageList = null;
		//获取分页参数值
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		//定义产品
		Product product = new Product();
		//获取产品参数值
		product.setProductCode(req.getParameter("productCode"));
		product.setProductName(req.getParameter("productName"));
		product.setCardBin(req.getParameter("cardBin"));
		product.setBusinessType(req.getParameter("businessType"));
		
		try {
			//获取产品数据列表
			pageList = productService.getProductPage(startNum,pageSize,product);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表信息出错", e);
		}
		
		mv.addObject("pageInfo", pageList);
		mv.addObject("product", product);
		mv.addObject("operStatus", operStatus);
		return mv;
	}
	
	/**
	 * 跳转至新增页面
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoAddProduct")
	public ModelAndView intoAddProduct(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("merchant/product/addProduct");
		List<MerchantInf> mchntList= merchantInfService.getMerchantInfListBySelect();
		mv.addObject("mchntList", mchntList);
		return mv;
	}
	
	/**
	 * 获取产品信息
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getProductDetail")
	@ResponseBody
	public Map<String,Object> getProductDetail(HttpServletRequest req, HttpServletResponse response) {
		Map<String,Object> resultMap=new HashMap<String, Object>();
		//定义产品
		Product product = null;
		//获取商户id
		String mchntId=StringUtils.nullToString(req.getParameter("mchntId"));
		resultMap.put("status", Boolean.TRUE);
			
		try{
			logger.info("mchntId" + mchntId);
			//根据商户号获取产品信息
			product = productService.getProductByMerchantId(mchntId);
			//判断是否为空
			if(product == null){
				product	= new Product();
				MerchantInf merchantInf = merchantInfService.getMerchantInfById(mchntId);
				product.setCardBin(merchantInf.getMchntCode().substring(2, 12));
			}
		}catch(Exception ex){
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "系统异常");	
			return resultMap;
		}
		resultMap.put("product", product);
		return resultMap;
	}
	
	/**
	 * 新增产品
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addProductCommit")
	public ModelAndView addProductCommit(HttpServletRequest req, HttpServletResponse response ,
			@RequestParam(value = "cardFaceImgs[]", required = false)MultipartFile[] cardFaceImgs //卡面图片
	) {
		ModelAndView mv = new ModelAndView("redirect:/merchant/product/listProduct.do");
		User user = getCurrUser(req);
		String mchntId = StringUtils.nullToString(req.getParameter("mchntId"));
		try{
			//获取产品信息并插入数据库
			Product product = getFormatProduct(req,user);
			if(product == null){
				throw new Exception();
			}
			//根据商户id获取商户信息
			MerchantInf merchantInf = merchantInfService.getMerchantInfById(mchntId);
			//根据机构id获取机构信息
			if(merchantInf == null){
				throw new Exception();
			}
			InsInf insInf = insInfService.getInsInfById(merchantInf.getInsId());
			if (insInf == null){
				throw new Exception();
			}
			//保存机构产品关联表
			productService.insertProductAndRelInsInf(product, insInf, merchantInf);
					
			//上传卡面图片
			imageManagerService.addUploadImange(merchantInf.getMchntCode(),
					Constants.ImageApplicationEnum.Application30.getCode(), 
					product.getProductCode(), 
					Constants.ImageApplicationTypeEnum.ApplicationType3001.getCode(), 
					cardFaceImgs);
			
			mv.addObject("operStatus", 1);
		}catch(Exception ex){
			mv.addObject("operStatus", 9);
			ex.printStackTrace();
		}
		
		return mv;
	}
	
	
	/**
	 * 根据请求信息组装产品属性
	 * @param req
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private Product getFormatProduct(HttpServletRequest req,User user) throws Exception{
		Product product = new Product();
		//获取请求中的数据
		product.setProductCode(StringUtils.nullToString(req.getParameter("productCode")));
		product.setCardBin(StringUtils.nullToString(req.getParameter("cardBin")));
		product.setProductName(StringUtils.nullToString(req.getParameter("productName")));
		product.setOnymousStat(StringUtils.nullToString(req.getParameter("onymousStat")));
		product.setBusinessType(StringUtils.nullToString(req.getParameter("businessType")));
		product.setProductType(StringUtils.nullToString(req.getParameter("productType")));
		product.setConsumTimes(StringUtils.nullToString(req.getParameter("consumTimes")));
		product.setRechargeTimes(StringUtils.nullToString(req.getParameter("rechargeTimes")));
		//最大余额装换成分
		String maxBalanceReq = StringUtils.nullToString(req.getParameter("maxBalance"));
		if(maxBalanceReq != null && "".equals(maxBalanceReq)){
			product.setMaxBalance(NumberUtils.RMBYuanToCent("0"));
		}else{
			product.setMaxBalance(NumberUtils.RMBYuanToCent(maxBalanceReq));
		}
		
		product.setRemarks(StringUtils.nullToString(req.getParameter("remarks")));
		
		//设置默认值
		product.setLastCardNum(0);
		product.setValidityPeriod(999);
		product.setCvv2ErrTimes("3");
		product.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
		
		//获取用户信息
		if(user !=null){
			product.setCreateUser(user.getId().toString());
			product.setUpdateUser(user.getId().toString());
		}
		
		//设置默认秘钥信息
		//获取秘钥默认版本
		KeyVersion keyVersion = keyVersionService.getDefaultKeyVersionByViesionType(Constants.KeyTypeEnum.ACTKEY.getCode());
		if (keyVersion != null)
			product.setVersionId(keyVersion.getVersionId());
		else
			return null;
		//获取秘钥信息
		KeyIndex keyIndex = null;
		//获取密码秘钥
		keyIndex = keyIndexService.getKeyIndexByKeyNameAndVersionId(Constants.KeyEnum.PWD.getCode(), keyVersion.getVersionId());
		if (keyIndex != null)
			product.setPwdKeyIndex(keyIndex.getKeyIndex());
		else 
			return null;
		//获取余额秘钥
		keyIndex = keyIndexService.getKeyIndexByKeyNameAndVersionId(Constants.KeyEnum.BAL.getCode(), keyVersion.getVersionId());
		if (keyIndex != null)
			product.setBalKeyIndex(keyIndex.getKeyIndex());
		else 
			return null;
		//获取CVV秘钥
		keyIndex = keyIndexService.getKeyIndexByKeyNameAndVersionId(Constants.KeyEnum.CVV.getCode(), keyVersion.getVersionId());
		if (keyIndex != null)
			product.setCvvKeyIndex(keyIndex.getKeyIndex());
		else 
			return null;

		return product;
	}
	
	/**
	 * 图片文件上传
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/addProductUpload")
	public ModelAndView addProductUpload(HttpServletRequest req,HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView();
		return mv;
	}
	
	/**
	 * 跳转至详情页面
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoViewProduct")
	public ModelAndView intoViewProduct(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("merchant/product/viewProduct");
		//获取产品号
		String productCode = StringUtils.nullToString(req.getParameter("productCode"));
		Product product = productService.getProductByProductCode(productCode);
		mv.addObject("product", product);
		//根据产品号获取商户id
		String mchntId = productService.getMerchantIdByProduct(productCode);
		//通过商户id获取商户信息
		MerchantInf merchantInf = merchantInfService.getMerchantInfById(mchntId);
		mv.addObject("merchantInf",merchantInf);
		
		ImageManager imageManager = new ImageManager();
		imageManager.setApplication(Constants.ImageApplicationEnum.Application30.getCode());
		imageManager.setApplicationId(product.getProductCode());
		imageManager.setApplicationType(Constants.ImageApplicationTypeEnum.ApplicationType3001.getCode());
		List<ImageManager> imgList=imageManagerService.getImageManagerPathList(imageManager);
		mv.addObject("imgList3001", imgList);

		return mv;
	}
	
	/**
	 * 跳转至编辑页面
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoEditProduct")
	public ModelAndView intoEditProduct(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("merchant/product/editProduct");
		//获取产品号
		String productCode = StringUtils.nullToString(req.getParameter("productCode"));
		Product product = productService.getProductByProductCode(productCode);
		mv.addObject("product", product);
		//根据产品号获取商户id
		String mchntId = productService.getMerchantIdByProduct(productCode);
		//通过商户id获取商户信息
		MerchantInf merchantInf = merchantInfService.getMerchantInfById(mchntId);
		mv.addObject("merchantInf",merchantInf);
		
		ImageManager imageManager = new ImageManager();
		imageManager.setApplication(Constants.ImageApplicationEnum.Application30.getCode());
		imageManager.setApplicationId(product.getProductCode());
		imageManager.setApplicationType(Constants.ImageApplicationTypeEnum.ApplicationType3001.getCode());
		List<ImageManager> imgList=imageManagerService.getImageManagerPathList(imageManager);
		mv.addObject("imgList3001", imgList);

		return mv;
	}
	
	@RequestMapping(value = "/editProduct")
	public ModelAndView editProduct(HttpServletRequest req, HttpServletResponse response ,
			@RequestParam(value = "cardFaceImgs[]", required = false)MultipartFile[] cardFaceImgs //卡面图片
	) {
		ModelAndView mv = new ModelAndView("redirect:/merchant/product/listProduct.do");
		User user = getCurrUser(req);
		String mchntId = StringUtils.nullToString(req.getParameter("mchntId"));
		try{
			//获取产品信息并插入数据库
			Product product = getFormatProduct(req,user);
			if(product == null){
				throw new Exception();
			}
			productService.updateProduct(product);
			//根据商户id获取商户信息
			MerchantInf merchantInf = merchantInfService.getMerchantInfById(mchntId);
			//上传卡面图片
			imageManagerService.updateUploadImange(merchantInf.getMchntCode(),
				Constants.ImageApplicationEnum.Application30.getCode(), 
				product.getProductCode(), 
				Constants.ImageApplicationTypeEnum.ApplicationType3001.getCode(), 
				cardFaceImgs);
				
			mv.addObject("operStatus", 1);
		}catch(Exception ex){
			mv.addObject("operStatus", 9);
			ex.printStackTrace();
		}
		
		return mv;
	}

}
