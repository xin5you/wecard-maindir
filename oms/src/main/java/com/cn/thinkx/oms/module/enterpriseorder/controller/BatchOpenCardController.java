package com.cn.thinkx.oms.module.enterpriseorder.controller;

import com.alibaba.fastjson.JSON;
import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrder;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrderList;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchOrderListService;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchOrderService;
import com.cn.thinkx.oms.module.enterpriseorder.util.OrderConstants;
import com.cn.thinkx.oms.module.enterpriseorder.util.PagePersonUtil;
import com.cn.thinkx.oms.module.merchant.model.Product;
import com.cn.thinkx.oms.module.merchant.service.ProductService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.redis.core.JedisClusterUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.OMSOrderStat;
import com.cn.thinkx.pms.base.utils.NumberUtils;
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
import java.util.*;

@Controller
@RequestMapping(value = "enterpriseOrder/batchOpenCard")
public class BatchOpenCardController extends BaseController {

    Logger logger = LoggerFactory.getLogger(BatchOpenCardController.class);

    @Autowired
    @Qualifier("productService")
    private ProductService productService;

    @Autowired
    @Qualifier("batchOrderService")
    private BatchOrderService batchOrderService;

    @Autowired
    @Qualifier("batchOrderListService")
    private BatchOrderListService batchOrderListService;

    /**
     * 批量开卡列表
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/listOpenCard")
    public ModelAndView listOpenCard(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("enterpriseOrder/batchOpenCard/listOpenCard");
        String operStatus = StringUtils.nullToString(req.getParameter("operStatus"));
        PageInfo<BatchOrder> pageList = null;
        int startNum = parseInt(req.getParameter("pageNum"), 1);
        int pageSize = parseInt(req.getParameter("pageSize"), 10);
        BatchOrder order = new BatchOrder();
        order.setProductCode(StringUtils.nullToString(req.getParameter("productCode")));
        order.setOrderType(BaseConstants.OMSOrderType.orderType_200.getCode());
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
        return mv;
    }

    /**
     * 进入新增批量开卡
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/intoAddOpenCard")
    public ModelAndView intoAddOpenCard(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("enterpriseOrder/batchOpenCard/addOpenCard");
        LinkedList<BatchOrderList> orderList = PagePersonUtil.getRedisBatchOrderList(OrderConstants.bathOpenCardSession);
        List<Product> productList = productService.getProductList(null);
        int startNum = parseInt(req.getParameter("pageNum"), 1);
        int pageSize = parseInt(req.getParameter("pageSize"), 10);
        Page<BatchOrderList> page = new Page<>(startNum, pageSize, false);
        if (orderList != null) {
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
        mv.addObject("productList", productList);
        return mv;
    }

    /**
     * 批量开卡提交
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/addOpenCardCommit")
    @ResponseBody
    public ModelMap addOpenCardCommit(HttpServletRequest req, HttpServletResponse response) {
        ModelMap resultMap = new ModelMap();
        resultMap.addAttribute("status", Boolean.TRUE);
        int i = 0;
        BatchOrder order = new BatchOrder();
        LinkedList<BatchOrderList> orderList = PagePersonUtil.getRedisBatchOrderList(OrderConstants.bathOpenCardSession);
        if (orderList == null) {
            resultMap.addAttribute("status", Boolean.FALSE);
            resultMap.addAttribute("msg", "没有添加任何数据！！！");
            return resultMap;
        }
        String productCode = StringUtils.nullToString(req.getParameter("productCode"));
        User user = this.getCurrUser(req);
        order.setOrderName(StringUtils.nullToString(req.getParameter("orderName")));
        order.setOrderStat(BaseConstants.OMSOrderStat.orderStat_10.getCode());
        order.setOrderType(BaseConstants.OMSOrderType.orderType_200.getCode());
        Product product = productService.getProductByProductCode(productCode);
        order.setProductName(product.getProductName());
        order.setProductCode(productCode);
        order.setCreateUser(user.getId().toString());
        order.setUpdateUser(user.getId().toString());
        try {
            i = batchOrderService.addBatchOrder(order, orderList);
            if (i > 0) {
                JedisClusterUtils.getInstance().del(OrderConstants.bathOpenCardSession);
            }
        } catch (Exception e) {
            logger.error("## 新增出错----->>[{}]", e.getMessage());
            resultMap.addAttribute("status", Boolean.FALSE);
            resultMap.addAttribute("msg", "新增失败，请重新添加");
        }

        return resultMap;
    }

    /**
     * 批量开卡详情
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/intoViewOpenCard")
    public ModelAndView intoViewOpenCard(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("enterpriseOrder/batchOpenCard/viewOpenCard");
        String orderId = req.getParameter("orderId");
        BatchOrder order = batchOrderService.getBatchOrderByOrderId(orderId);
        order.setOrderStat(BaseConstants.OMSOrderStat.findStat(order.getOrderStat()));
        int startNum = parseInt(req.getParameter("pageNum"), 1);
        int pageSize = parseInt(req.getParameter("pageSize"), 10);
        PageInfo<BatchOrderList> pageList = batchOrderListService.getBatchOrderListPage(startNum, pageSize, orderId);
        mv.addObject("order", order);
        mv.addObject("pageInfo", pageList);
        return mv;
    }

    /**
     * 进入批量开卡编辑
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/intoEditOpenCard")
    public ModelAndView intoEditOpenCard(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("enterpriseOrder/batchOpenCard/editOpenCard");
        String orderId = req.getParameter("orderId");
        String operStatus = StringUtils.nullToString(req.getParameter("operStatus"));
        BatchOrder order = batchOrderService.getBatchOrderByOrderId(orderId);
        order.setOrderStat(BaseConstants.OMSOrderStat.findStat(order.getOrderStat()));
        int startNum = parseInt(req.getParameter("pageNum"), 1);
        int pageSize = parseInt(req.getParameter("pageSize"), 10);
        PageInfo<BatchOrderList> pageList = batchOrderListService.getBatchOrderListPage(startNum, pageSize, orderId);
        mv.addObject("order", order);
        mv.addObject("pageInfo", pageList);
        mv.addObject("operStatus", operStatus);
        return mv;
    }

    /**
     * 删除批量开卡订单信息
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/deleteOpenCardCommit")
    @ResponseBody
    public ModelMap deleteOpenCardCommit(HttpServletRequest req, HttpServletResponse response) {
        ModelMap map = new ModelMap();
        map.put("status", Boolean.TRUE);
        String orderId = StringUtils.nullToString(req.getParameter("orderId"));
        try {
            BatchOrder order = batchOrderService.getBatchOrderById(orderId);
            order.setOrderStat(BaseConstants.OMSOrderStat.orderStat_19.getCode());
            batchOrderService.updateBatchOrder(order);
        } catch (Exception e) {
            map.put("status", Boolean.FALSE);
            logger.error("## 删除批量开卡订单信息，订单号：[{}],出错", orderId, e);
        }
        return map;
    }

    /**
     * 提交批量开卡订单
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
                batchOrderService.batchOpenCardITF(orderId, user, BaseConstants.OMSOrderStat.orderStat_10.getCode());
            }
        } catch (Exception e) {
            resultMap.put("status", Boolean.FALSE);
            logger.error("## 提交批量开卡订单，订单号：[{}],出错", orderId, e);
        }
        return resultMap;
    }

    /**
     * 重复提交批量开卡订单
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
                batchOrderService.batchOpenCardITF(orderId, user, BaseConstants.OMSOrderStat.orderStat_90.getCode());
            }
        } catch (Exception e) {
            resultMap.put("status", Boolean.FALSE);
            logger.error("## 重复提交批量开卡订单，订单号：[{}],出错", orderId, e);
        }
        return resultMap;
    }

    /**
     * 提交批量开卡信息，编辑页面
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
            logger.error("## 提交批量开卡信息出错", e);
        }
        return resultMap;
    }

    /**
     * 删除批量开卡用户信息，编辑页面
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
            logger.error("## 删除批量开卡用户信息异常", e);
        }
        return resultMap;
    }

    /**
     * 添加批量开卡用户信息，新增页面
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
            BatchOrderList order = new BatchOrderList();
            order.setPuid(UUID.randomUUID().toString().replace("-", ""));
            order.setUserName(StringUtils.nullToString(req.getParameter("name")));
            order.setUserCardNo(StringUtils.nullToString(req.getParameter("card")));
            order.setPhoneNo(phone);
            LinkedList<BatchOrderList> orderList = PagePersonUtil.getRedisBatchOrderList(OrderConstants.bathOpenCardSession);
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
            JedisClusterUtils.getInstance().setex(OrderConstants.bathOpenCardSession, JSON.toJSON(orderList).toString(), 1800); // 设置有效时间30分钟
        } catch (Exception e) {
            resultMap.put("status", Boolean.FALSE);
            resultMap.put("msg", "系统故障，请稍后再试");
            logger.error("## 添加批量开卡用户信息出错", e);
        }
        return resultMap;
    }

    /**
     * 删除批量开卡用户信息，新增页面
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
            LinkedList<BatchOrderList> orderList = PagePersonUtil.getRedisBatchOrderList(OrderConstants.bathOpenCardSession);
            for (BatchOrderList order : orderList) {
                if (order.getPuid().equals(puid)) {
                    orderList.remove(order);
                    break;
                }
            }
            JedisClusterUtils.getInstance().setex(OrderConstants.bathOpenCardSession, JSON.toJSON(orderList).toString(), 1800); // 设置有效时间30分钟
        } catch (Exception e) {
            resultMap.put("status", Boolean.FALSE);
            resultMap.put("msg", "系统故障，请稍后再试");
            logger.error("## 删除批量开卡用户信息，", e);
        }

        return resultMap;
    }

    /**
     * 跳转限额编辑页面
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/intoEditQuota")
    public ModelAndView intoEditQuota(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("enterpriseOrder/batchOpenCard/editQuota");
        String operStatus = StringUtils.nullToString(req.getParameter("operStatus"));
        String orderId = req.getParameter("orderId");
        int startNum = parseInt(req.getParameter("pageNum"), 1);
        int pageSize = parseInt(req.getParameter("pageSize"), 10);
        PageInfo<BatchOrderList> pageList = batchOrderListService.getBatchOrderListPage(startNum, pageSize, orderId);
        mv.addObject("pageInfo", pageList);
        mv.addObject("operStatus", operStatus);
        mv.addObject("orderId", orderId);
        return mv;
    }

    @RequestMapping(value = "/editQuotaCommit")
    @ResponseBody
    public ModelMap editQuotaCommit(HttpServletRequest req, HttpServletResponse response) {
        ModelMap map = new ModelMap();
        map.addAttribute("status", Boolean.TRUE);
        try {
            String orderId = req.getParameter("orderId");
            String maxAmt = NumberUtils.RMBYuanToCent(req.getParameter("maxQuota"));
            User user = this.getCurrUser(req);
            batchOrderService.batchUpdateQuotaITF(orderId, maxAmt, user);
        } catch (Exception e) {
            logger.error("## 编辑出错----->>[{}]", e.getMessage());
            map.addAttribute("status", Boolean.FALSE);
            map.addAttribute("msg", "编辑失败");
        }
        return map;
    }
}
