package com.cn.thinkx.wecard.customer.module.eshop.ctrl;

import java.io.PrintWriter;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.common.redis.util.HttpWebUtil;
import com.cn.thinkx.common.redis.util.RedisConstants;
import com.cn.thinkx.common.redis.util.RedisDictProperties;
import com.cn.thinkx.common.redis.util.SignUtil;
import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysProduct;
import com.cn.thinkx.common.wecard.domain.eshop.MchntEshopInf;
import com.cn.thinkx.common.wecard.domain.person.PersonInf;
import com.cn.thinkx.common.wecard.domain.user.UserInf;
import com.cn.thinkx.facade.bean.CusAccListQueryRequest;
import com.cn.thinkx.facade.service.HKBTxnFacade;
import com.cn.thinkx.pms.base.utils.AESUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.CardProductType;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelEcomType;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.customer.module.base.ctrl.BaseController;
import com.cn.thinkx.wecard.customer.module.customer.service.PersonInfService;
import com.cn.thinkx.wecard.customer.module.customer.service.UserInfService;
import com.cn.thinkx.wecard.customer.module.customer.service.UserMerchantAcctService;
import com.cn.thinkx.wecard.customer.module.customer.util.SignUtils;
import com.cn.thinkx.wecard.customer.module.eshop.service.MchntEshopInfService;
import com.cn.thinkx.wecard.customer.module.welfaremart.service.CardKeysProductService;
import com.cn.thinkx.wecard.customer.module.welfaremart.service.WelfareMartService;
import com.cn.thinkx.wecard.customer.module.welfaremart.vo.WelfareUserInfo;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.entity.CustomerAccListQueryITFResp;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("eshop/mchntEshop")
public class MchntEshopInfManagerCtrl extends BaseController{
	Logger logger = LoggerFactory.getLogger(MchntEshopInfManagerCtrl.class);

	@Autowired
	private HKBTxnFacade hkbTxnFacade;

	@Autowired
	@Qualifier("mchntEshopInfService")
	private MchntEshopInfService mchntEshopInfService;

	@Autowired
	@Qualifier("personInfService")
	private PersonInfService personInfService;

	@Autowired
	@Qualifier("userInfService")
	private UserInfService userInfService;

	@Autowired
	@Qualifier("jedisCluster")
	private JedisCluster jedisCluster;

	@Autowired
	@Qualifier("userMerchantAcctService")
	private UserMerchantAcctService userMerchantAcctService;

	@Autowired
	@Qualifier("welfareMartService")
	private WelfareMartService welfareMartService;

	@Autowired
	@Qualifier("cardKeysProductService")
	private CardKeysProductService cardKeysProductService;

	@RequestMapping("/mchntEshopList")
	public ModelAndView mchntEshopList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("eshop/mchntEshop");

		CustomerAccListQueryITFResp accList = new CustomerAccListQueryITFResp(); // 商户门店信息
		String openid = WxMemoryCacheClient.getOpenid(request);

		String innerMerchantNo = request.getParameter("innerMerchantNo"); // 商户CODE
		long timestamp = System.currentTimeMillis();// 时间戳
		CusAccListQueryRequest baseTxnReq = new CusAccListQueryRequest();

		MchntEshopInf eshopInf = new MchntEshopInf();
		List<MchntEshopInf> mechntEshopList = null;
		String[] str = null;
		try {
			baseTxnReq.setChannel(BaseConstants.ChannelCode.CHANNEL1.toString());
			baseTxnReq.setInnerMerchantNo(innerMerchantNo);
			baseTxnReq.setUserId(openid);
			baseTxnReq.setTimestamp(timestamp);
			baseTxnReq.setRemarks(BaseConstants.ACC_ITF);
			baseTxnReq.setSign(SignUtil.genSign(baseTxnReq));

			String jsonStr = hkbTxnFacade.customerAccListQueryITF(baseTxnReq);
			accList = JSONArray.parseObject(jsonStr, CustomerAccListQueryITFResp.class);
		} catch (Exception e) {
			logger.error("## mchntEshopList  throws exception", e);
		}

		try {
			if (accList != null && accList.getProductList() != null && BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(accList.getCode())) {
				if (accList.getProductList().size() == 1) {
					eshopInf = mchntEshopInfService.getMchntEshopInfByMchntCode(accList.getProductList().get(0).getMchntCode());
					if(eshopInf != null){
						mv = new ModelAndView("redirect:/eshop/mchntEshop/redirectUrl.html?eShopId=" + eshopInf.geteShopId());
					}
					return mv;
				}
				str = new String[accList.getProductList().size()];
				for (int i = 0; i < accList.getProductList().size(); i++) {
					str[i] = accList.getProductList().get(i).getMchntCode();
				}
			}
			if (str != null) {
				eshopInf.setMchntCodes(str);
				mechntEshopList = mchntEshopInfService.getMchntEshopInfList(eshopInf);
				if (mechntEshopList != null && mechntEshopList.size() > 0) {
					if (mechntEshopList.size() == 1) {
						mv = new ModelAndView("redirect:/eshop/mchntEshop/redirectUrl.html?eShopId=" + mechntEshopList.get(0).geteShopId());
						return mv;
					}
					String urlImg = HttpWebUtil.getHkbUrlImg();
					for (MchntEshopInf eShopList : mechntEshopList) {
						eShopList.seteShopLogo(urlImg + eShopList.geteShopLogo());
						eShopList.setBgUrl(urlImg + eShopList.getBgUrl());
					}
				}
			}

		} catch (Exception e) {
			logger.error("## mchntEshopList throws exception", e);
		}
		mv.addObject("eshopList", mechntEshopList);
		return mv;
	}

	@RequestMapping("/redirectUrl")
	public ModelAndView redirectUrl(HttpServletRequest request) {
		ModelAndView mv = null;
		String eShopId = request.getParameter("eShopId");
		String openid = WxMemoryCacheClient.getOpenid(request);// 从缓存中获取openid
		UserInf user = userInfService.getUserInfByOpenId(openid);
		/*** 用户是否已经注册汇卡包会员 **/
		if (user == null) {
			mv = new ModelAndView("customer/user/userRegister");
			return mv;
		}
		try {
			MchntEshopInf eShopInf = mchntEshopInfService.getMchntEshopInfById(eShopId);
			if (BaseConstants.ChannelCode.CHANNEL6.toString().equals(eShopInf.getChannelCode())) {
				PersonInf person = personInfService.getPersonInfByOpenId(openid); // 获取用户基本信息
				StringBuffer sb = new StringBuffer();
				sb.append(person.getUserId())
				.append("|")
				.append(person.getMobilePhoneNo())
				.append("|")
				.append(openid)
				.append("|")
				.append(eShopInf.getMchntCode())
				.append("|")
				.append(eShopInf.getShopCode());
				String sKey = HttpWebUtil.getReqAesKey();
				String paramInfo = AESUtil.jzEncrypt(sb.toString(), sKey); // 加密后的用户信息
				String strTime = String.valueOf(System.currentTimeMillis());
				String timeStamp = AESUtil.jzEncrypt(strTime, sKey); // 加密后的时间戳
				String url = RedisDictProperties.getInstance().getdictValueByCode("40006001_REQ_URL") + "?paramInfo="
						+ paramInfo + "&timeStamp=" + timeStamp;
				mv = new ModelAndView("redirect:" + url);
			}
		} catch (Exception e) {
			logger.error("## redirectUrl throws exception", e);
			mv = new ModelAndView("common/failure");
			mv.addObject("failureMsg", "系统异常");
			return mv;
		}
		return mv;
	}

	/**
	 * 订单物流信息查询接口
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/redirectOrderUrl")
	public ModelAndView redirectOrderUrl(HttpServletRequest request) {
		ModelAndView mv = null;
		String openid = WxMemoryCacheClient.getOpenid(request);// 从缓存中获取openid
		UserInf user = userInfService.getUserInfByOpenId(openid);
		/*** 用户是否已经注册汇卡包会员 **/
		if (user == null) {
			mv = new ModelAndView("customer/user/userRegister");
			return mv;
		}
		try {
			PersonInf person = personInfService.getPersonInfByOpenId(openid); // 获取用户基本信息
			//			String str = person.getUserId() + "|" + person.getMobilePhoneNo() + "|" + openid;
			StringBuffer sb = new StringBuffer();
			sb.append(person.getUserId()).append("|").append(person.getMobilePhoneNo()).append("|").append(openid);
			String sKey = HttpWebUtil.getReqAesKey();
			String paramInfo = AESUtil.jzEncrypt(sb.toString(), sKey); // 加密后的用户信息
			String strTime = String.valueOf(System.currentTimeMillis());
			String timeStamp = AESUtil.jzEncrypt(strTime, sKey); // 加密后的时间戳
			String orderUrl = HttpWebUtil.getOrderDomainUrl();
			String url = orderUrl + "?paramInfo=" + paramInfo + "&timeStamp=" + timeStamp;
			mv = new ModelAndView("redirect:" + url);
		} catch (Exception e) {
			logger.error("## redirectOrderUrl throws exception", e);
			mv = new ModelAndView("common/failure");
			mv.addObject("failureMsg", "系统异常");
			return mv;
		}
		return mv;
	}

	/**
	 * 进入福利账户通卡商城
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/AccHKBEshop")
	public ModelAndView AccHKBEshop(HttpServletRequest request) {
		ModelAndView mv = null;
		String ecmChnl = request.getParameter("channel");
		if (StringUtil.isNullOrEmpty(ecmChnl)) {
			logger.info("★★★★★Request AccHKBEshop get channel is [Null]★★★★★");
			return super.error500(request);
		}

		String openid = WxMemoryCacheClient.getOpenid(request); 
		if (StringUtil.isNullOrEmpty(openid)) {
			logger.info("★★★★★Request AccHKBEshop get openid is [Null]★★★★★");
			return super.error500(request);
		}

		PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
		if (StringUtil.isNullOrEmpty(personInf)) {
			logger.info("★★★★★Request AccHKBEshop get personInf is [Null]★★★★★");
			return super.error500(request);
		}

		String mchntCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ACC_HKB_MCHNT_NO);
		String shopCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ACC_HKB_SHOP_NO);

		String url = null;
		if (ChannelEcomType.CEU03.getCode().equals(ecmChnl)) 
			url = mchntEshopInfService.JFUrl(request, personInf, ecmChnl, mchntCode, shopCode);
		else if (ChannelEcomType.CEU04.getCode().equals(ecmChnl))
			url = mchntEshopInfService.JFUrl(request, personInf, ecmChnl, mchntCode, shopCode);
		else if (ChannelEcomType.CEU05.getCode().equals(ecmChnl))
			url = mchntEshopInfService.JFUrl(request, personInf, ecmChnl, mchntCode, shopCode);
		else if (ChannelEcomType.CEU07.getCode().equals(ecmChnl))
			url = mchntEshopInfService.HTTTDUrl(request, personInf);
		else if (ChannelEcomType.CEU08.getCode().equals(ecmChnl))
			return new ModelAndView("welfaremart/cardin/cardIn");
		else if (ChannelEcomType.CEU09.getCode().equals(ecmChnl)) {
			mv = new ModelAndView("welfaremart/homepage/homepage");
			try {
				WelfareUserInfo userInfo = welfareMartService.toWelfareMartHome(personInf.getUserId());
				/** 卡产品列表 */
				List<CardKeysProduct> cardKeysProductList = cardKeysProductService.getCardKeysProductByType(CardProductType.CP11.getCode());
				mv.addObject("cardKeysProductList", cardKeysProductList);
				mv.addObject("userInfo", userInfo);
				return mv;
			} catch (Exception e) {
				logger.error("## AccHKBEshop --->跳转卡券集市主页异常[{}]", e);
			}
		} else {
			logger.info("## 渠道号为[{}]在知了企服系统中不存在", ecmChnl);
			return super.error500(request);
		}
		return new ModelAndView("redirect:" + url);
	}

	/**
	 * 进入知了企服商城
	 * @param request
	 * @return
	 */
	@RequestMapping("/toHKBEshop")
	public String toHKBEshop(HttpServletRequest request, HttpServletResponse resp) {
		String openid = WxMemoryCacheClient.getOpenid(request);// 从缓存中获取openid
		
		/*** 用户是否已经注册知了企服会员 **/
		UserInf user = userInfService.getUserInfByOpenId(openid);
		if (user != null) {
			String ecom_url = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ESHOP_HKB_ECOM_STORE_URL);
			logger.info("## 知了企服商城  toHKBEshop openId-->[{}],redirect_url-->[{}]", WxMemoryCacheClient.getOpenid(request),ecom_url);
			
			SortedMap<String, String> dataMap = new TreeMap<String, String>();
			dataMap.put("channelCode", BaseConstants.ChannelCode.CHANNEL1.toString());
			dataMap.put("service", "h5.scene.ecom");
			dataMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
			dataMap.put("version", "1");
			dataMap.put("cUserId", WxMemoryCacheClient.getOpenid(request));

			
			StringBuilder forSign = new StringBuilder();
			for (String key : dataMap.keySet()) {
				forSign.append(key).append("=").append(dataMap.get(key)).append("&");
			}
			forSign.append("key=").append("YPEgSbuyRcoDV49yHzx4wS4ZeKHFVQA84Hv4IunjHT7");
			String sign = SignUtils.MD5Encode(forSign.toString());
			dataMap.put("sign", sign);
			
			try {
				// 跳转知了企服电商
				resp.setContentType("text/html;charset=utf-8");
				PrintWriter out = resp.getWriter();
				out.println("<form name='mainForm' method='post'  action='" + ecom_url + "' >");
				for (String key : dataMap.keySet()) {
					out.println("<input type='hidden' name='"+key+"' value='" + dataMap.get(key) + "'>");
				}
				out.println("</form>");
				out.println("<script type='text/javascript'>");
				out.println("document.mainForm.submit()");
				out.println("</script>");

			} catch (Exception e) {
				logger.error("跳转电商异常：-->[{}]",e);
			}
		}else{
			try{
				resp.setContentType("text/html;charset=utf-8");
				PrintWriter out = resp.getWriter();
				out.println("<form name='mainForm' method='post'  action='/customer/user/userRegister.html' >");
				out.println("</form>");
				out.println("<script type='text/javascript'>");
				out.println("document.mainForm.submit()");
				out.println("</script>");
			} catch (Exception e) {
				logger.error("跳转电商异常：-->[{}]",e);
			}
		}
		
		return null;
	}

}
