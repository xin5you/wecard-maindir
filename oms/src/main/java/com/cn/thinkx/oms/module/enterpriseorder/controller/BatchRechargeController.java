package com.cn.thinkx.oms.module.enterpriseorder.controller;

import com.alibaba.fastjson.JSON;
import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchInvoiceOrder;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrder;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrderList;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchInvoiceOrderService;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchOrderListService;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchOrderService;
import com.cn.thinkx.oms.module.enterpriseorder.util.OrderConstants;
import com.cn.thinkx.oms.module.enterpriseorder.util.PagePersonUtil;
import com.cn.thinkx.oms.module.merchant.model.Product;
import com.cn.thinkx.oms.module.merchant.model.ShopInf;
import com.cn.thinkx.oms.module.merchant.service.ProductService;
import com.cn.thinkx.oms.module.merchant.service.ShopInfService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.NumberUtils;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.redis.core.JedisClusterUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.OMSOrderStat;
import com.github.pagehelper.Page;
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
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping(value = "enterpriseOrder/batchRecharge")
public class BatchRechargeController extends BaseController {

    Logger logger = LoggerFactory.getLogger(BatchRechargeController.class);

    @Autowired
    @Qualifier("productService")
    private ProductService productService;

    @Autowired
    @Qualifier("batchOrderService")
    private BatchOrderService batchOrderService;

    @Autowired
    @Qualifier("batchOrderListService")
    private BatchOrderListService batchOrderListService;

    @Autowired
    @Qualifier("batchInvoiceOrderService")
    private BatchInvoiceOrderService batchInvoiceOrderService;

    @Autowired
    @Qualifier("shopInfService")
    private ShopInfService shopInfService;

    /**
     * 批量充值列表
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/listRecharge")
    public ModelAndView listRecharge(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("enterpriseOrder/batchRecharge/listRecharge");
        String operStatus = StringUtils.nullToString(req.getParameter("operStatus"));
        PageInfo<BatchOrder> pageList = null;
        int startNum = parseInt(req.getParameter("pageNum"), 1);
        int pageSize = parseInt(req.getParameter("pageSize"), 10);
        BatchOrder order = new BatchOrder();
        order.setProductCode(StringUtils.nullToString(req.getParameter("productCode")));
        String invoiceStat = StringUtils.nullToString(req.getParameter("invoiceStat"));
        if (invoiceStat == "") {
            order.setInvoiceStat("100");
        } else {
            order.setInvoiceStat(invoiceStat);
        }
        order.setOrderType(BaseConstants.OMSOrderType.orderType_300.getCode());
        List<Product> productList = productService.getProductList(null);
        Map<String, String> mapOrderStat = new HashMap<String, String>();
        for (OMSOrderStat st : OMSOrderStat.values()) {
            mapOrderStat.put(st.getCode(), st.getStat());
        }
        try {
            pageList = batchOrderService.getBatchOrderPage(startNum, pageSize, order, req);
        } catch (Exception e) {
            logger.error("## 查询列表信息出错", e);
        }
        mv.addObject("order", order);
        mv.addObject("mapOrderStat", mapOrderStat);
        mv.addObject("pageInfo", pageList);
        mv.addObject("operStatus", operStatus);
        mv.addObject("productList", productList);
        mv.addObject("rechargeTypeList", BaseConstants.OMSOrderBizType.values());
        return mv;
    }

    /**
     * 新增批量充值
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/intoAddRecharge")
    public ModelAndView intoAddRecharge(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("enterpriseOrder/batchRecharge/addRecharge");
        BigDecimal sumMoney = BigDecimal.ZERO;
        List<Product> productList = productService.getProductList(null);
        LinkedList<BatchOrderList> orderList = PagePersonUtil.getRedisBatchOrderList(OrderConstants.bathRechargeSession);
        int startNum = parseInt(req.getParameter("pageNum"), 1);
        int pageSize = parseInt(req.getParameter("pageSize"), 10);
        Page<BatchOrderList> page = new Page<>(startNum, pageSize, false);
        if (orderList != null) {
            for (BatchOrderList batchOrderList : orderList) {
                sumMoney = sumMoney.add(BigDecimal.valueOf(Double.valueOf(batchOrderList.getAmount())));
            }
            page.setTotal(orderList.size());
            List<BatchOrderList> list = PagePersonUtil.getPersonInfPageList(startNum, pageSize, orderList);
            for (BatchOrderList o : list) {
                page.add(o);
            }
        } else {
            page.setTotal(0);
        }
        PageInfo<BatchOrderList> pageList = new PageInfo<BatchOrderList>(page);
        mv.addObject("pageInfo", pageList);
        mv.addObject("count", pageList.getTotal());
        mv.addObject("sumMoney", sumMoney.doubleValue());
        mv.addObject("productList", productList);
        mv.addObject("rechargeTypeList", BaseConstants.OMSOrderBizType.values());
        return mv;
    }

    /**
     * 批量充值提交
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/addRechargeCommit")
    @ResponseBody
    public ModelMap addRechargeCommit(HttpServletRequest req, HttpServletResponse response) {
        ModelMap resultMap = new ModelMap();
        resultMap.addAttribute("status", Boolean.TRUE);
        int i = 0;
        BatchOrder order = new BatchOrder();
        LinkedList<BatchOrderList> orderList = PagePersonUtil.getRedisBatchOrderList(OrderConstants.bathRechargeSession);
        if (orderList == null) {
            resultMap.addAttribute("status", Boolean.FALSE);
            resultMap.addAttribute("msg", "没有添加任何数据！！！");
            return resultMap;
        }
        String productCode = req.getParameter("productCode");
        User user = this.getCurrUser(req);
        order.setCompanyName(StringUtils.nullToString(req.getParameter("companyName")));
        order.setBizType(StringUtils.nullToString(req.getParameter("bizType")));
        order.setOrderName(StringUtils.nullToString(req.getParameter("orderName")));
        order.setOrderStat(BaseConstants.OMSOrderStat.orderStat_10.getCode());
        order.setOrderType(BaseConstants.OMSOrderType.orderType_300.getCode());
        Product product = productService.getProductByProductCode(productCode);
        order.setProductName(product.getProductName());
        order.setProductCode(productCode);
        order.setCreateUser(user.getId().toString());
        order.setUpdateUser(user.getId().toString());
        try {
            i = batchOrderService.addBatchOrder(order, orderList);
            if (i > 0) {
                JedisClusterUtils.getInstance().del(OrderConstants.bathRechargeSession);
            }
        } catch (Exception e) {
            logger.error("新增出错----->>[{}]", e.getMessage());
            resultMap.addAttribute("status", Boolean.FALSE);
            resultMap.addAttribute("msg", "新增失败，请重新添加");
        }
        return resultMap;
    }

    /**
     * 批量充值订单详情
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/intoViewRecharge")
    public ModelAndView intoViewRecharge(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("enterpriseOrder/batchRecharge/viewRecharge");
        String orderId = req.getParameter("orderId");
        BatchOrder order = batchOrderService.getBatchOrderByOrderId(orderId);
        order.setOrderStat(BaseConstants.OMSOrderStat.findStat(order.getOrderStat()));
        order.setBizType(order.getBizType() == null ? "" : BaseConstants.OMSOrderBizType.findType(order.getBizType()));
        if (order.getSumAmount() == null || "".equals(order.getSumAmount())) {
            order.setSumAmount("" + NumberUtils.formatMoney(0));
        } else {
            order.setSumAmount("" + NumberUtils.formatMoney(order.getSumAmount()));
        }
        int startNum = parseInt(req.getParameter("pageNum"), 1);
        int pageSize = parseInt(req.getParameter("pageSize"), 10);
        PageInfo<BatchOrderList> pageList = batchOrderListService.getBatchOrderListPage(startNum, pageSize, orderId);
        mv.addObject("order", order);
        mv.addObject("pageInfo", pageList);
        return mv;
    }

    /**
     * 进入批量充值订单编辑
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/intoEditRecharge")
    public ModelAndView intoEditRecharge(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("enterpriseOrder/batchRecharge/editRecharge");
        String orderId = req.getParameter("orderId");
        String operStatus = StringUtils.nullToString(req.getParameter("operStatus"));
        BatchOrder order = batchOrderService.getBatchOrderByOrderId(orderId);
        order.setOrderStat(BaseConstants.OMSOrderStat.findStat(order.getOrderStat()));
        if (order.getSumAmount() == null || "".equals(order.getSumAmount())) {
            order.setSumAmount("" + NumberUtils.formatMoney(0));
        } else {
            order.setSumAmount("" + NumberUtils.formatMoney(order.getSumAmount()));
        }
        int startNum = parseInt(req.getParameter("pageNum"), 1);
        int pageSize = parseInt(req.getParameter("pageSize"), 10);
        PageInfo<BatchOrderList> pageList = batchOrderListService.getBatchOrderListPage(startNum, pageSize, orderId);
        mv.addObject("order", order);
        mv.addObject("pageInfo", pageList);
        mv.addObject("operStatus", operStatus);
        return mv;
    }

    /**
     * 删除批量充值订单
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/deleteRechargeCommit")
    @ResponseBody
    public ModelMap deleteRechargeCommit(HttpServletRequest req, HttpServletResponse response) {
        ModelMap map = new ModelMap();
        map.put("status", Boolean.TRUE);
        String orderId = StringUtils.nullToString(req.getParameter("orderId"));
        try {
            BatchOrder order = batchOrderService.getBatchOrderById(orderId);
            order.setOrderStat(BaseConstants.OMSOrderStat.orderStat_19.getCode());
            batchOrderService.updateBatchOrder(order);
        } catch (Exception e) {
            map.put("status", Boolean.FALSE);
            logger.error("## 删除批量充值订单,订单号：[{}]", orderId, e);
        }
        return map;
    }

    /**
     * 提交批量充值订单
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/addOrderCommit")
    @ResponseBody
    public ModelMap addOrderCommit(HttpServletRequest req, HttpServletResponse response) {
        ModelMap resultMap = new ModelMap();
        resultMap.put("status", Boolean.TRUE);
        String orderId = StringUtils.nullToString(req.getParameter("orderId"));
        User user = this.getCurrUser(req);
        try {
            BatchOrder order = batchOrderService.getBatchOrderById(orderId);
            if (BaseConstants.OMSOrderStat.orderStat_10.getCode().equals(order.getOrderStat())) {
                batchOrderService.batchRechargeCardITF(orderId, user, BaseConstants.OMSOrderStat.orderStat_10.getCode());
            }
        } catch (Exception e) {
            resultMap.put("status", Boolean.FALSE);
            logger.error("## 提交批量充值订单，订单号：[{}]，出错", orderId, e);
        }
        return resultMap;
    }

    /**
     * 重复提交批量充值订单
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/addOrderAgainCommit")
    @ResponseBody
    public ModelMap addOrderAgainCommit(HttpServletRequest req, HttpServletResponse response) {
        ModelMap resultMap = new ModelMap();
        resultMap.put("status", Boolean.TRUE);
        String orderId = StringUtils.nullToString(req.getParameter("orderId"));
        User user = this.getCurrUser(req);
        try {
            BatchOrder order = batchOrderService.getBatchOrderById(orderId);
            if (BaseConstants.OMSOrderStat.orderStat_90.getCode().equals(order.getOrderStat())) {
                batchOrderService.batchRechargeCardITF(orderId, user, BaseConstants.OMSOrderStat.orderStat_90.getCode());
            }
        } catch (Exception e) {
            resultMap.put("status", Boolean.FALSE);
            logger.error("## 重复提交批量充值订单，订单号：[{}],出错[{}]", orderId, e);
        }
        return resultMap;
    }

    /**
     * 批量充值添加充值用户信息
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/addOrderListCommit")
    @ResponseBody
    public ModelMap addOrderListCommit(HttpServletRequest req, HttpServletResponse response) {
        ModelMap resultMap = new ModelMap();
        resultMap.put("status", Boolean.TRUE);
        String orderId = StringUtils.nullToString(req.getParameter("orderId"));
        User user = this.getCurrUser(req);
        try {
            BatchOrder order = batchOrderService.getBatchOrderById(orderId);
            BatchOrderList orderList = new BatchOrderList();
            orderList.setOrderId(orderId);
            orderList.setUserName(StringUtils.nullToString(req.getParameter("name")));
            orderList.setPhoneNo(StringUtils.nullToString(req.getParameter("phone")));
            orderList.setUserCardNo(StringUtils.nullToString(req.getParameter("card")));
            orderList.setAmount(NumberUtils.RMBYuanToCent(StringUtils.nullToString(req.getParameter("money"))));
            orderList.setProductCode(order.getProductCode());
            orderList.setProductName(order.getProductName());
            orderList.setOrderStat(BaseConstants.OMSOrderListStat.orderListStat_0.getCode());
            orderList.setCreateUser(user.getId().toString());
            orderList.setUpdateUser(user.getId().toString());
            BatchOrderList orderList2 = batchOrderListService.getBatchOrderListByOrderIdAndPhoneNo(orderList);
            if (orderList2 != null) {
                resultMap.put("status", Boolean.FALSE);
                resultMap.put("msg", "电话号码重复！！！");
                return resultMap;
            }
            batchOrderListService.addOrderList(orderList);
        } catch (Exception e) {
            resultMap.put("status", Boolean.FALSE);
            resultMap.put("msg", "系统故障，请稍后再试");
            logger.error("## 批量充值添加充值信息出错", e);
        }
        return resultMap;
    }

    /**
     * 删除批量充值的订单信息
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/deleteOrderListCommit")
    @ResponseBody
    public ModelMap deleteOrderListCommit(HttpServletRequest req, HttpServletResponse response) {
        ModelMap resultMap = new ModelMap();
        resultMap.put("status", Boolean.TRUE);
        try {
            batchOrderListService.deleteBatchOrderList(req);
        } catch (Exception e) {
            resultMap.put("status", Boolean.FALSE);
            resultMap.put("msg", "系统故障，请稍后再试");
            logger.error("## 删除批量充值信息出错", e);
        }
        return resultMap;
    }

    /**
     * 添加充值用户信息
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/addAccountInf")
    @ResponseBody
    public ModelMap addAccountInf(HttpServletRequest req, HttpServletResponse response) {
        ModelMap resultMap = new ModelMap();
        resultMap.put("status", Boolean.TRUE);
        try {
            String phone = StringUtils.nullToString(req.getParameter("phone"));
            String money = StringUtils.nullToString(req.getParameter("money"));
            BatchOrderList order = new BatchOrderList();
            order.setPuid(UUID.randomUUID().toString().replace("-", ""));
            order.setUserName(StringUtils.nullToString(req.getParameter("name")));
            order.setUserCardNo(StringUtils.nullToString(req.getParameter("card")));
            order.setPhoneNo(phone);
            order.setAmount("" + NumberUtils.formatMoney(NumberUtils.RMBYuanToCent(money)));
            LinkedList<BatchOrderList> orderList = PagePersonUtil.getRedisBatchOrderList(OrderConstants.bathRechargeSession);
            if (orderList == null) {
                orderList = new LinkedList<BatchOrderList>();
            }
            for (BatchOrderList batchOrderList : orderList) {
                if (batchOrderList.getPhoneNo().equals(phone)) {
                    resultMap.put("status", Boolean.FALSE);
                    resultMap.put("msg", "电话号码重复！！！");
                    return resultMap;
                }
            }
            orderList.addFirst(order);
            JedisClusterUtils.getInstance().setex(OrderConstants.bathRechargeSession, JSON.toJSON(orderList).toString(), 1800); // 设置有效时间30分钟
        } catch (Exception e) {
            resultMap.put("status", Boolean.FALSE);
            resultMap.put("msg", "系统故障，请稍后再试");
            logger.error("## 添加充值用户信息出错", e);
        }
        return resultMap;
    }

    /**
     * 删除批量充值用户信息
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/deleteAccountInf")
    @ResponseBody
    public ModelMap deleteAccountInf(HttpServletRequest req, HttpServletResponse response) {
        ModelMap resultMap = new ModelMap();
        resultMap.put("status", Boolean.TRUE);
        try {
            String puid = req.getParameter("puid");
            LinkedList<BatchOrderList> orderList = PagePersonUtil.getRedisBatchOrderList(OrderConstants.bathRechargeSession);
            for (BatchOrderList order : orderList) {
                if (order.getPuid().equals(puid)) {
                    orderList.remove(order);
                    break;
                }
            }
            JedisClusterUtils.getInstance().setex(OrderConstants.bathRechargeSession, JSON.toJSON(orderList).toString(), 1800); // 设置有效时间30分钟
        } catch (Exception e) {
            resultMap.put("status", Boolean.FALSE);
            resultMap.put("msg", "系统故障，请稍后再试");
            logger.error("## 删除批量充值用户信息", e);
        }
        return resultMap;
    }

    /**
     * 批量充值点击进入开票页面
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/intoAddBatchInvoiceOrder")
    public ModelAndView intoAddBatchInvoiceOrder(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("enterpriseOrder/batchRecharge/addBatchInvoiceOrder");
        String orderId = StringUtils.nullToString(req.getParameter("orderId"));
        BatchInvoiceOrder batchInvoiceOrder = batchInvoiceOrderService.getBatchOrderByOrderId(orderId);
        User user = this.getCurrUser(req);
        batchInvoiceOrder.setCreateUser(user.getLoginname());
        String mchntCode = batchInvoiceOrder.getMchntCode();
        List<ShopInf> shopInfList = shopInfService.getShopInfListByMchntCode(mchntCode);
        mv.addObject("shopInfList", shopInfList);
        mv.addObject("batchInvoiceOrder", batchInvoiceOrder);
        return mv;
    }

    /**
     * 批量充值开票确认提交
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/addBatchInvoiceOrderCommit")
    @ResponseBody
    public ModelMap addBatchInvoiceOrderCommit(HttpServletRequest req) {
        ModelMap resultMap = batchInvoiceOrderService.addBatchInvoiceOrderCommit(req);
        return resultMap;
    }

    /**
     * 批量充值开票详情
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/intoViewBatchInvoiceOrder")
    public ModelAndView intoViewBatchInvoiceOrder(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("enterpriseOrder/batchRecharge/viewBatchInvoiceOrder");
        String orderId = req.getParameter("orderId");
        BatchInvoiceOrder batchInvoiceOrder = batchInvoiceOrderService.getBatchInvoiceOrderByOrderId(orderId);
        mv.addObject("batchInvoiceOrder", batchInvoiceOrder);
        return mv;
    }

}
