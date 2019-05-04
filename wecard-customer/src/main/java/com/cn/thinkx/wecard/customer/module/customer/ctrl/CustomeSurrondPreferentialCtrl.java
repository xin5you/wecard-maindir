package com.cn.thinkx.wecard.customer.module.customer.ctrl;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.common.redis.util.SignUtil;
import com.cn.thinkx.facade.bean.MchntInfQueryRequest;
import com.cn.thinkx.facade.bean.ShopInfQueryRequest;
import com.cn.thinkx.facade.bean.base.BaseTxnReq;
import com.cn.thinkx.facade.service.HKBTxnFacade;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.wecard.customer.module.base.ctrl.BaseController;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.entity.MchtSellingCardListQueryITFResp;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.entity.MerchantInfoQueryITFResp;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.entity.ShopInfoQueryITFResp;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.entity.ShopListQueryITFResp;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.vo.ShopListQueryITFVo;

/**
 * 微信会员 附近优惠 模块
 */
@Controller
@RequestMapping("/customer/surround")
public class CustomeSurrondPreferentialCtrl  extends BaseController{
	Logger logger = LoggerFactory.getLogger(CustomeSurrondPreferentialCtrl.class);

	
	@Autowired
	private HKBTxnFacade hkbTxnFacade;
	

	
	/**
	 * 附近优惠列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/surroundPreferentialPage")
	public ModelAndView surroundPreferentialPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("customer/surround/surroundPreferentialList");
		// 拦截器已经处理了缓存,这里直接取
		return mv;
	}
	
	/**
	 * 附近优惠 门店列表List
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/surroundPreferentialList")
	public @ResponseBody ShopListQueryITFResp surroundPreferentialList(HttpServletRequest request){
		
		ShopListQueryITFResp resp=new ShopListQueryITFResp();
		String longitude=request.getParameter("longitude");
		String latitude=request.getParameter("latitude");
		String distance=request.getParameter("distance");
		String industryType=request.getParameter("industryType");
		String sort=request.getParameter("sort");
		String pageNum=request.getParameter("pageNum");
		String itemSize=request.getParameter("itemSize");
		long timestamp=System.currentTimeMillis();
		ShopInfQueryRequest shopInfQuery=new ShopInfQueryRequest();
		try {
			shopInfQuery.setLongitude(longitude);
			shopInfQuery.setLatitude(latitude);
			shopInfQuery.setDistance(distance);
			shopInfQuery.setIndustryType(industryType);
			shopInfQuery.setSort(sort);
			shopInfQuery.setPageNum(pageNum);
			shopInfQuery.setItemSize(itemSize);
			shopInfQuery.setTimestamp(timestamp);
			shopInfQuery.setSign(SignUtil.genSign(shopInfQuery));
			
			String jsonStr=hkbTxnFacade.shopListQueryITF(shopInfQuery);  //附近优惠 商户门店列表
			resp=JSONArray.parseObject(jsonStr, ShopListQueryITFResp.class);
			
			if(resp !=null && BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())){
				for(ShopListQueryITFVo tmp:resp.getShopList()){
					tmp.setDistance(NumberUtils.getConverDistance(tmp.getDistance())); //距离
					tmp.setIndustryType(BaseConstants.IndustryType.findByCode(tmp.getIndustryType()).getValue()); //行业类别
					
					//星级评价
					int evaluate=Integer.parseInt(tmp.getEvaluate());
					if(evaluate>0){
						int starNum=evaluate/20;
						int starRem=evaluate%20;
						for(int i=0;i<starNum;i++){
							tmp.getStars()[i]="1";
						}
						if(starNum<5){
							if(starRem !=0){
								tmp.getStars()[starNum]="99";
							}
						}
					}
					
					int soldCount = Integer.parseInt(tmp.getSoldCount());
					
					if(soldCount>=10000){
						tmp.setSoldCount(soldCount/10000+"w+");
					}
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return resp;
	}
	
	
	/**
	 * 附近优惠 查看商户门店信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/viewMchntShopInf")
	public ModelAndView viewMchntShopInf(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("customer/surround/viewMchntShopInf");
		
		ShopInfoQueryITFResp shopInf=new ShopInfoQueryITFResp(); //商户门店信息
		
		String innerMerchantNo=request.getParameter("innerMerchantNo");  //商户CODE
		String innerShopNo=request.getParameter("innerShopNo"); //门店CODE
		String detailFlag=request.getParameter("detailFlag"); //是否查询明细
		long timestamp=System.currentTimeMillis();//时间戳
		ShopInfQueryRequest shopInfReq=new ShopInfQueryRequest();
		try {
			shopInfReq.setInnerMerchantNo(innerMerchantNo);
			shopInfReq.setInnerShopNo(innerShopNo);
			shopInfReq.setDetailFlag(detailFlag);
			shopInfReq.setTimestamp(timestamp);
			shopInfReq.setSign(SignUtil.genSign(shopInfReq));
			
			String jsonStr=hkbTxnFacade.shopInfoQueryITF(shopInfReq);
			shopInf=JSONArray.parseObject(jsonStr, ShopInfoQueryITFResp.class);
			
			if(shopInf !=null &&  BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(shopInf.getCode())){
				shopInf.getShopInfo().setIndustryType(BaseConstants.IndustryType.findByCode(shopInf.getShopInfo().getIndustryType()).getValue()); //行业类别
				
				//星级评价
				int evaluate=Integer.parseInt(shopInf.getShopInfo().getEvaluate());
				if(evaluate>0){
					int starNum=evaluate/20;
					int starRem=evaluate%20;
					for(int i=0;i<starNum;i++){
						shopInf.getShopInfo().getStars()[i]="1";
					}
					if(starNum<5){
						if(starRem !=0){
							shopInf.getShopInfo().getStars()[starNum]="99";
						}
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("附近优惠 查看商户门店信息--->", e);
		}  //附近优惠 商户门店列表
		
		
		MchtSellingCardListQueryITFResp cardList=new MchtSellingCardListQueryITFResp(); //商户在售卡列表
		BaseTxnReq baseTxnReq=new BaseTxnReq();
		
		String activeRule="";
		try {
			baseTxnReq.setInnerMerchantNo(innerMerchantNo);
			baseTxnReq.setTimestamp(timestamp);
			baseTxnReq.setSign(SignUtil.genSign(baseTxnReq));
			
			String jsonStr=hkbTxnFacade.mchtSellingCardListQueryITF(baseTxnReq);
			cardList=JSONArray.parseObject(jsonStr, MchtSellingCardListQueryITFResp.class);
			
			if(cardList !=null &&  BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(cardList.getCode())){
				
				if(cardList.getCardList() !=null && cardList.getCardList().size()>0){
					String commodityPrice;
					String commodityAmount;
					for(int i=0;i<cardList.getCardList().size();i++){
						 activeRule=cardList.getCardList().get(0).getActiveRule();  //活动规则取的是第一个
						 commodityPrice=cardList.getCardList().get(i).getCommodityPrice();
						 commodityAmount=cardList.getCardList().get(i).getCommodityAmount();
						//商品售价
						cardList.getCardList().get(i).setCommodityPrice(NumberUtils.RMBCentToYuan(commodityPrice));
						//商品优惠了的价格
						cardList.getCardList().get(i).setFavorablePrice(NumberUtils.RMBCentToYuan((Integer.parseInt(commodityAmount)-Integer.parseInt(commodityPrice))));
					}
				}
			}
		} catch (Exception e) {
			logger.error("附近优惠	商户在售卡列表信息--->", e);
		}
		mv.addObject("activeRule", activeRule);
		mv.addObject("shopInf", shopInf.getShopInfo());
		mv.addObject("sellingCardList", cardList);
		return mv;
	}
	
	/**
	 * 附近优惠  查看门店位置
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/viewShopInfAddress")
	public ModelAndView viewShopInfAddress(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("customer/surround/viewShopInfAddress");
		ShopInfoQueryITFResp shopInf=new ShopInfoQueryITFResp(); //商户门店信息
		String innerMerchantNo=request.getParameter("innerMerchantNo");  //商户CODE
		String innerShopNo=request.getParameter("innerShopNo"); //门店CODE
		String detailFlag=request.getParameter("detailFlag"); //是否查询明细
		long timestamp=System.currentTimeMillis();//时间戳
		ShopInfQueryRequest shopInfReq=new ShopInfQueryRequest();
		try {
			shopInfReq.setInnerMerchantNo(innerMerchantNo);
			shopInfReq.setInnerShopNo(innerShopNo);
			shopInfReq.setDetailFlag(detailFlag);
			shopInfReq.setTimestamp(timestamp);
			shopInfReq.setSign(SignUtil.genSign(shopInfReq));
			
			String jsonStr=hkbTxnFacade.shopInfoQueryITF(shopInfReq);
			shopInf=JSONArray.parseObject(jsonStr, ShopInfoQueryITFResp.class);
			
			if(shopInf !=null &&  BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(shopInf.getCode())){
				shopInf.getShopInfo().setIndustryType(BaseConstants.IndustryType.findByCode(shopInf.getShopInfo().getIndustryType()).getValue()); //行业类别
				//星级评价
				int evaluate=Integer.parseInt(shopInf.getShopInfo().getEvaluate());
				if(evaluate>0){
					int starNum=evaluate/20;
					int starRem=evaluate%20;
					for(int i=0;i<starNum;i++){
						shopInf.getShopInfo().getStars()[i]="1";
					}
					if(starNum<5){
						if(starRem !=0){
							shopInf.getShopInfo().getStars()[starNum]="99";
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("附近优惠 查看商户门店信息--->", e);
		}
		mv.addObject("shopInf", shopInf.getShopInfo());
		return mv;
	}
	
	/**
	 * 附近优惠  查看门店客服电话
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/viewShopInfPhone")
	public ModelAndView viewShopInfPhone(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("customer/surround/viewShopInfPhone");
		ShopInfoQueryITFResp shopInf=new ShopInfoQueryITFResp(); //商户门店信息
		String innerMerchantNo=request.getParameter("innerMerchantNo");  //商户CODE
		String innerShopNo=request.getParameter("innerShopNo"); //门店CODE
		String detailFlag=request.getParameter("detailFlag"); //是否查询明细
		long timestamp=System.currentTimeMillis();//时间戳
		ShopInfQueryRequest shopInfReq=new ShopInfQueryRequest();
		//客服电话
		String[] phoneNums=new String[]{};
		try {
			shopInfReq.setInnerMerchantNo(innerMerchantNo);
			shopInfReq.setInnerShopNo(innerShopNo);
			shopInfReq.setDetailFlag(detailFlag);
			shopInfReq.setTimestamp(timestamp);
			shopInfReq.setSign(SignUtil.genSign(shopInfReq));
			
			String jsonStr=hkbTxnFacade.shopInfoQueryITF(shopInfReq);
			shopInf=JSONArray.parseObject(jsonStr, ShopInfoQueryITFResp.class);
			
			if(shopInf !=null &&  BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(shopInf.getCode())){
				shopInf.getShopInfo().setIndustryType(BaseConstants.IndustryType.findByCode(shopInf.getShopInfo().getIndustryType()).getValue()); //行业类别
				//客服电话
				if(shopInf.getShopInfo() !=null && shopInf.getShopInfo().getTelephone() !=null){
					phoneNums=shopInf.getShopInfo().getTelephone().split(",");
				}
			}
		} catch (Exception e) {
			logger.error("附近优惠 查看商户门店信息--->", e);
		}
		mv.addObject("phoneNums", phoneNums); //客服电话
		mv.addObject("shopInf", shopInf.getShopInfo());
		return mv;
	}

	/**
	 * 附近优惠	商户在售卡列表信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/mchtSellingCardList")
	public @ResponseBody  MchtSellingCardListQueryITFResp  mchtSellingCardList(HttpServletRequest request) {
		
		MchtSellingCardListQueryITFResp cardList=new MchtSellingCardListQueryITFResp(); //商户门店信息
		
		String innerMerchantNo=request.getParameter("innerMerchantNo");  //商户CODE
		long timestamp=System.currentTimeMillis();//时间戳
		BaseTxnReq baseTxnReq=new BaseTxnReq();
		
		try {
			baseTxnReq.setInnerMerchantNo(innerMerchantNo);
			baseTxnReq.setTimestamp(timestamp);
			baseTxnReq.setSign(SignUtil.genSign(baseTxnReq));
			
			String jsonStr=hkbTxnFacade.mchtSellingCardListQueryITF(baseTxnReq);
			cardList=JSONArray.parseObject(jsonStr, MchtSellingCardListQueryITFResp.class);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("附近优惠	商户在售卡列表信息--->", e);
		}
		return cardList;
	}

	
	
	/**
	 * 附近优惠	商户信息查询信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/viewMerchantInfo")
	public @ResponseBody  MerchantInfoQueryITFResp  viewMerchantInfo(HttpServletRequest request) {
		
		MerchantInfoQueryITFResp merchantInfo=new MerchantInfoQueryITFResp(); //商户门店信息
		String innerMerchantNo=request.getParameter("innerMerchantNo");  //商户CODE
		
		long timestamp=System.currentTimeMillis();//时间戳
		MchntInfQueryRequest baseTxnReq=new MchntInfQueryRequest();
		
		try {
			baseTxnReq.setInnerMerchantNo(innerMerchantNo);
			baseTxnReq.setTimestamp(timestamp);
			baseTxnReq.setSign(SignUtil.genSign(baseTxnReq));
			
			String jsonStr=hkbTxnFacade.merchantInfoQueryITF(baseTxnReq);
			merchantInfo=JSONArray.parseObject(jsonStr, MerchantInfoQueryITFResp.class);
			
		} catch (Exception e) {
			logger.error("附近优惠	商户信息查询信息--->", e);
		}
		return merchantInfo;
	}
	
	
}

