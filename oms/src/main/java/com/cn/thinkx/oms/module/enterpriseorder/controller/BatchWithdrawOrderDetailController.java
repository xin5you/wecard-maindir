package com.cn.thinkx.oms.module.enterpriseorder.controller;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrderDetail;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchWithdrawOrderDetailService;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchWithdrawOrderService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "batchWithdrawOrderDetail")
public class BatchWithdrawOrderDetailController extends BaseController {

	Logger logger = LoggerFactory.getLogger(BatchWithdrawOrderDetailController.class);

	@Autowired
	@Qualifier("batchWithdrawOrderService")
	private BatchWithdrawOrderService batchWithdrawOrderService;

	@Autowired
	@Qualifier("batchWithdrawOrderDetailService")
	private BatchWithdrawOrderDetailService batchWithdrawOrderDetailService;

	/**
	 * 后台批量提现明细列表
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listBatchWithDrawOrderDetail")
	public ModelAndView listBatchWithDrawOrderDetail(HttpServletRequest req, HttpServletResponse response ,BatchWithdrawOrderDetail order) {
		ModelAndView mv = new ModelAndView("enterpriseOrder/batchWithDraw/listBatchWithDrawOrderDetail");
		PageInfo<BatchWithdrawOrderDetail> pageList = null;
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		try {
			pageList = batchWithdrawOrderDetailService.getBatchWithdrawOrderDetailPage(startNum,pageSize,order);
		} catch (Exception e) {
			logger.error("## 查询列表信息出错", e);
		}

		mv.addObject("order", order);
		mv.addObject("pageInfo", pageList);
		return mv;
	}
}
