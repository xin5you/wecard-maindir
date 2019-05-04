package com.cn.iboot.diy.api.trans.ctrl;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.cn.iboot.diy.api.base.constants.BaseConstants;
import com.cn.iboot.diy.api.base.constants.Constants;
import com.cn.iboot.diy.api.base.domain.ResultHtml;
import com.cn.iboot.diy.api.base.utils.ChannelSignUtil;
import com.cn.iboot.diy.api.base.utils.NumberUtils;
import com.cn.iboot.diy.api.base.utils.StringUtil;
import com.cn.iboot.diy.api.operate.domain.PositOprStatistics;
import com.cn.iboot.diy.api.operate.service.PositOprStatisticsService;
import com.cn.iboot.diy.api.system.domain.User;
import com.cn.iboot.diy.api.trans.domain.TransLog;
import com.cn.iboot.diy.api.trans.domain.TransRefundReq;
import com.cn.iboot.diy.api.trans.service.TransService;
import com.cn.iboot.diy.api.trans.utils.TransUtils;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping(value = "trans/mchnt")
public class TransController {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Value("${CHANNEL_CODE}")
	private String channelCode;
	
	@Value("${HKB_URL}")
	private String hkbUrl;

	@Autowired
	private TransService transService;

	@Autowired
	private PositOprStatisticsService positOprStatisticsService;


	@GetMapping(value = "/getTransQuery")
	public ModelAndView getTransQuery(HttpServletRequest req,TransLog log) {
		ModelAndView mv = new ModelAndView("trans/hkbTransQuery");//hkbTransQuery
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(Constants.SESSION_USER);
		log.setMchntCode(user.getMchntCode());
		log.setShopCode(user.getShopCode());
		log.setQueryType("cur");
		
		PageInfo<TransLog> pageList = new PageInfo<TransLog>(null);
		pageList = transService.getTransLogPage(startNum, pageSize, log);
		mv.addObject("pageInfo", pageList);
		mv.addObject("transTypeList", BaseConstants.TransQueryCode.values());
		mv.addObject("invoiceTypeList", BaseConstants.DiyInvoiceStat.values());
		mv.addObject("log", log);
		return mv;
	}

	@PostMapping(value = "/getTransQuery")
	public ModelAndView getTransQuery(HttpServletRequest req,HttpServletResponse response,TransLog log) {
		ModelAndView mv = new ModelAndView("trans/hkbTransQuery");
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(Constants.SESSION_USER);
		log.setMchntCode(user.getMchntCode());
		log.setShopCode(user.getShopCode());
		PageInfo<TransLog> pageList = new PageInfo<TransLog>(null);

		pageList = transService.getTransLogPage(startNum, pageSize, log);

		mv.addObject("pageInfo", pageList);
		mv.addObject("transTypeList", BaseConstants.TransQueryCode.values());
		mv.addObject("invoiceTypeList", BaseConstants.DiyInvoiceStat.values());
		mv.addObject("log", log);
		return mv;
	}
	
	/**
	 * 
	 * @param req
	 * @param pos
	 * @return
	 */
	@GetMapping(value = "/getTransCount")
	public ModelAndView getTransCount(HttpServletRequest req,PositOprStatistics pos) {
		ModelAndView mv = new ModelAndView("trans/hkbTransCount");
		Map<String,Object> params = new HashMap<String,Object>();
		User user = (User) req.getSession().getAttribute(Constants.SESSION_USER);
		PageInfo<PositOprStatistics> pageList = null;
		try {
			int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
			int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
			String startDate = StringUtil.nullToString(req.getParameter("startDate"));
			String endDate = StringUtil.nullToString(req.getParameter("endDate"));
			LocalDate date = LocalDate.now();
			pos.setMchntCode(user.getMchntCode());
			pos.setShopCode(user.getShopCode());
			if(startDate!=null&&!startDate.equals(""))	
			pos.setStartDate(date+" "+startDate);
			if(endDate!=null&&!endDate.equals(""))
			pos.setEndDate(date+" "+endDate);
			pageList = positOprStatisticsService.getPositCurStatisticsPage(startNum, pageSize, pos);
	/*		params.put("mechntcode",user.getMchntCode());
			if(startDate!=null&&!startDate.equals(""))
			params.put("startdate",date+" "+Constants.DiyTime.findByCode(startDate).getName());
			if(endDate!=null&&!endDate.equals(""))
			params.put("enddate",date+" "+Constants.DiyTime.findByCode(endDate).getName());	
			params.put("shopcode",user.getShopCode());
			params.put("results", new ArrayList<Map<String, Object>>());	
			pageList = positOprStatisticsService.getPositCurStatisticsPage(startNum, pageSize, params);
*/
			if (pageList.getList()!= null&&pageList.getList().size()>0){
				pos.setTotalPay(pageList.getList().get(0).getTotalPay());
				pos.setTotalNum(pageList.getList().get(0).getTotalNum());
			}else{
				pos.setTotalPay("0.00");
				pos.setTotalNum(0);
			}		
			pos.setStartDate(startDate);
			pos.setEndDate(endDate);
			pos.setSettleDate(date.getYear()+"年"+date.getMonthValue()+"月"+date.getDayOfMonth()+"日");
		} catch (Exception e) {
			log.error("实时统计查询信息出错-->[{}]", e);
		}
	
		pos.setShopName(getShopName(user));
		pos.setMchntName(getMchntName(user));
		mv.addObject("positOprStatistics", pos);
		mv.addObject("pageInfo", pageList);
		mv.addObject("startSelect", Constants.DiyTime.values());
		mv.addObject("endSelect", Constants.DiyTime.values());
		return mv;
	}
	/**
	 * 
	 * @param req
	 * @param pos
	 * @return
	 */
	@PostMapping(value = "/getTransCount")
	public ModelAndView getTransCount(HttpServletRequest req,HttpServletResponse resp,PositOprStatistics pos) {
		ModelAndView mv = new ModelAndView("trans/hkbTransCount");
		Map<String,Object> params = new HashMap<String,Object>();
		User user = (User) req.getSession().getAttribute(Constants.SESSION_USER);
		PageInfo<PositOprStatistics> pageList = null;
		try {
			int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
			int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
			String startDate = StringUtil.nullToString(req.getParameter("startDate"));
			String endDate = StringUtil.nullToString(req.getParameter("endDate"));
			LocalDate date = LocalDate.now();
			pos.setMchntCode(user.getMchntCode());
			pos.setShopCode(user.getShopCode());
			if(startDate!=null&&!startDate.equals(""))	
			pos.setStartDate(date+" "+Constants.DiyTime.findByCode(startDate).getName());
			if(endDate!=null&&!endDate.equals(""))
			pos.setEndDate(date+" "+Constants.DiyTime.findByCode(endDate).getName());
			pageList = positOprStatisticsService.getPositCurStatisticsPage(startNum, pageSize, pos);
	/*		params.put("mechntcode",user.getMchntCode());
			if(startDate!=null&&!startDate.equals(""))
			params.put("startdate",date+" "+Constants.DiyTime.findByCode(startDate).getName());
			if(endDate!=null&&!endDate.equals(""))
			params.put("enddate",date+" "+Constants.DiyTime.findByCode(endDate).getName());	
			params.put("shopcode",user.getShopCode());
			params.put("results", new ArrayList<Map<String, Object>>());	
			pageList = positOprStatisticsService.getPositCurStatisticsPage(startNum, pageSize, params);
*/
			if (pageList.getList()!= null&&pageList.getList().size()>0){
				pos.setTotalPay(pageList.getList().get(0).getTotalPay());
				pos.setTotalNum(pageList.getList().get(0).getTotalNum());
			}else{
				pos.setTotalPay("0.00");
				pos.setTotalNum(0);
			}		
			pos.setStartDate(startDate);
			pos.setEndDate(endDate);
			pos.setSettleDate(date.getYear()+"年"+date.getMonthValue()+"月"+date.getDayOfMonth()+"日");
		} catch (Exception e) {
			log.error("实时统计查询信息出错-->[{}]", e);
		}
	
		pos.setShopName(getShopName(user));
		pos.setMchntName(getMchntName(user));
		mv.addObject("positOprStatistics", pos);
		mv.addObject("pageInfo", pageList);
//		mv.addObject("startSelect", Constants.DiyTime.values());
//		mv.addObject("endSelect", Constants.DiyTime.values());
		return mv;
	}


	/**
	 * 查询门店名称
	 * 
	 * @param user
	 * @return
	 */
	private String getShopName(User user) {
		String shopName = "";
		try {
			if(user!=null)
				shopName = positOprStatisticsService.getShopInfByShopCode(user.getShopCode());
		} catch (Exception e) {
			log.error("查询门店名称出错-->[{}]", e);
		}
		return shopName;
	}

	/**
	 * 查询商户名称
	 * 
	 * @param user
	 * @return
	 */
	private String getMchntName(User user) {
		String mchntName = "";
		try {
			if(user!=null)
				mchntName = positOprStatisticsService.getMchntInfByMchntCode(user.getMchntCode());
		} catch (Exception e) {
			log.error("查询商户名称出错-->[{}]", e);
		}
		return mchntName;
	}

	
	@PostMapping(value="/intoNotarizeTrans")
	public TransRefundReq intoNotarizeTrans(HttpServletRequest reqest){
		TransRefundReq req = new TransRefundReq();
		String dmsRelatedKey = reqest.getParameter("dmsRelatedKey");
		User user = (User) reqest.getSession().getAttribute(Constants.SESSION_USER);
		try{
			String tableNum = positOprStatisticsService.getCurLogNum();
			TransLog transLog = new TransLog();
			transLog.setTableNum(tableNum);
			transLog.setDmsRelatedKey(dmsRelatedKey);
			TransLog log = transService.getTransLogByDmsRelatedKey(transLog);
			req.setTxnPrimaryKey(log.getTxnPrimaryKey());
			req.setPhoneNumber(user.getPhoneNo());
			req.setTransAmt(log.getTransAmt());
			req.setDmsRelatedKey(log.getDmsRelatedKey());
			req.setSettleDate(log.getSettleDate());
		} catch(Exception e){
			log.error("into确认退款页面出错",e.getMessage());
		}
		return req;
	}
	
	@PostMapping(value="/transRefundCommit")
	public ResultHtml transRefundCommit(HttpServletRequest request){
		TransRefundReq req = new TransRefundReq();
		ResultHtml resp =new ResultHtml();
		String txnPrimaryKey = request.getParameter("txnKey");
		String phoneNumber = request.getParameter("phone");
		String phoneCode = request.getParameter("phoneCode");
		req.setChannel(channelCode);
		req.setTxnPrimaryKey(txnPrimaryKey);
		req.setPhoneNumber(phoneNumber);
		req.setPhoneCode(phoneCode);
		String sgin = ChannelSignUtil.genSign(req);
		req.setSign(sgin);
		String transreq = JSONObject.toJSONString(req);
		try {
			log.info("请求退款参数-------->[{}]",transreq);
			transreq= URLEncoder.encode(transreq, "utf-8");
			String result = TransUtils.sendGet(hkbUrl, "transreq="+transreq);
			log.info("退款返回参数-------->[{}]",result);
			resp = JSONObject.parseObject(result,ResultHtml.class);
		} catch (Exception e) {
			log.error("退款出错啦--------->[{}]",e.getMessage());
		}
		return resp;
	}
	
}
