package com.cn.thinkx.oms.module.enterpriseorder.controller;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrder;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrderDetail;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchWithdrawOrderDetailService;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchWithdrawOrderService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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
     */
    @RequestMapping(value = "/listBatchWithDrawOrderDetail")
    public ModelAndView listBatchWithDrawOrderDetail(HttpServletRequest req, BatchWithdrawOrderDetail order) {
        ModelAndView mv = new ModelAndView("enterpriseOrder/batchWithDraw/listBatchWithDrawOrderDetail");
        PageInfo<BatchWithdrawOrderDetail> pageList = null;
        int startNum = parseInt(req.getParameter("pageNum"), 1);
        int pageSize = parseInt(req.getParameter("pageSize"), 10);
        try {
            pageList = batchWithdrawOrderDetailService.getBatchWithdrawOrderDetailPage(startNum, pageSize, order);
        } catch (Exception e) {
            logger.error("## 查询列表信息出错", e);
        }

        mv.addObject("order", order);
        mv.addObject("pageInfo", pageList);
        return mv;
    }

    /**
     * 同步数据状态
     */
    @RequestMapping(value = "/synchronCommit")
    @ResponseBody
    public ModelMap synchronCommit(HttpServletRequest req) {
        ModelMap map = new ModelMap();
        map.addAttribute("status", Boolean.FALSE);

        String orderId = req.getParameter("orderId");
        BatchWithdrawOrder order = batchWithdrawOrderService.getById(orderId);

        if (order == null || Constants.withdrawStat.S00.getCode().equals(order.getStat())) {
            map.addAttribute("msg", "该订单状态正在处理中");
            return map;
        }
        try {
            //同步订单明细数据
            map = batchWithdrawOrderDetailService.doSynchronStat(order);
        } catch (Exception ex) {
            logger.error("## 提交代付失败", ex);
        }
        map.addAttribute("status", Boolean.TRUE);
        return map;
    }
}
