package com.cn.thinkx.oms.module.enterpriseorder.controller;

import com.alibaba.fastjson.JSON;
import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrder;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrderList;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchOrderListService;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchOrderService;
import com.cn.thinkx.oms.module.enterpriseorder.util.OrderConstants;
import com.cn.thinkx.oms.module.enterpriseorder.util.PagePersonUtil;
import com.cn.thinkx.oms.module.sys.model.User;
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
import java.util.*;

@Controller
@RequestMapping(value = "enterpriseOrder/batchOpenAccount")
public class BatchOpenAccountController extends BaseController {

    Logger logger = LoggerFactory.getLogger(BatchOpenAccountController.class);

    @Autowired
    @Qualifier("batchOrderService")
    private BatchOrderService batchOrderService;

    @Autowired
    @Qualifier("batchOrderListService")
    private BatchOrderListService batchOrderListService;

    /**
     * 批量开户查看
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/listOpenAccount")
    public ModelAndView listOpenAccount(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("enterpriseOrder/batchOpenAccount/listOpenAccount");
        String operStatus = StringUtils.nullToString(req.getParameter("operStatus"));
        PageInfo<BatchOrder> pageList = null;
        int startNum = parseInt(req.getParameter("pageNum"), 1);
        int pageSize = parseInt(req.getParameter("pageSize"), 10);
        BatchOrder order = new BatchOrder();
        order.setOrderType(BaseConstants.OMSOrderType.orderType_100.getCode());
        Map<String, String> mapOrderStat = new HashMap<String, String>();
        for (OMSOrderStat st : OMSOrderStat.values()) { // 订单状态
            mapOrderStat.put(st.getCode(), st.getStat());
        }
        try {
            pageList = batchOrderService.getBatchOrderPage(startNum, pageSize, order, req);
        } catch (Exception e) {
            logger.error("## 批量开户查询列表信息出错", e);
        }
        mv.addObject("order", order);
        mv.addObject("mapOrderStat", mapOrderStat);
        mv.addObject("pageInfo", pageList);
        mv.addObject("operStatus", operStatus);
        return mv;
    }

    /**
     * 进入开户页面
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/intoAddOpenAccount")
    public ModelAndView intoAddOpenAccount(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("enterpriseOrder/batchOpenAccount/addOpenAccount");
        // LinkedList<BatchOrderList> orderList =
        // (LinkedList<BatchOrderList>)req.getSession().getAttribute(OrderConstants.bathOpenAccountSession);
        LinkedList<BatchOrderList> orderList = PagePersonUtil.getRedisBatchOrderList(OrderConstants.bathOpenAccountSession);
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
        mv.addObject("count", page.getTotal());
        return mv;
    }

    /**
     * 批量开户提交
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/addOpenAccountCommit")
    @ResponseBody
    public ModelMap AddOpenAccountCommit(HttpServletRequest req, HttpServletResponse response) {
        ModelMap resultMap = new ModelMap();
        resultMap.addAttribute("status", Boolean.TRUE);
        int i = 0;
        BatchOrder order = new BatchOrder();
        LinkedList<BatchOrderList> orderList = PagePersonUtil.getRedisBatchOrderList(OrderConstants.bathOpenAccountSession);
        if (orderList == null) {
            resultMap.addAttribute("status", Boolean.FALSE);
            resultMap.addAttribute("msg", "没有添加任何数据！！！");
            return resultMap;
        }
        User user = this.getCurrUser(req);
        order.setOrderName(StringUtils.nullToString(req.getParameter("orderName")));
        order.setOrderStat(BaseConstants.OMSOrderStat.orderStat_10.getCode());
        order.setOrderType(BaseConstants.OMSOrderType.orderType_100.getCode());
        order.setCreateUser(user.getId().toString());
        order.setUpdateUser(user.getId().toString());
        try {
            i = batchOrderService.addBatchOrder(order, orderList);
            if (i > 0) {
                JedisClusterUtils.getInstance().del(OrderConstants.bathOpenAccountSession);
            }
        } catch (Exception e) {
            logger.error("新增出错---->>[{}]", e.getMessage());
            resultMap.addAttribute("status", Boolean.FALSE);
            resultMap.addAttribute("msg", "新增失败，请重新添加");
        }

        return resultMap;
    }

    /**
     * 订单详情
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/intoViewOpenAccount")
    public ModelAndView intoViewOpenAccount(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("enterpriseOrder/batchOpenAccount/viewOpenAccount");
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
     * 进入编辑页面
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/intoEditOpenAccount")
    public ModelAndView intoEditOpenAccount(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("enterpriseOrder/batchOpenAccount/editOpenAccount");
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
     * 删除批量开户的订单信息
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/deleteOpenAccountCommit")
    @ResponseBody
    public ModelMap deleteOpenAccountCommit(HttpServletRequest req, HttpServletResponse response) {
        ModelMap resultMap = new ModelMap();
        resultMap.put("status", Boolean.TRUE);
        String orderId = StringUtils.nullToString(req.getParameter("orderId"));
        try {
            BatchOrder order = batchOrderService.getBatchOrderById(orderId);
            order.setOrderStat(BaseConstants.OMSOrderStat.orderStat_19.getCode());
            batchOrderService.updateBatchOrder(order);
        } catch (Exception e) {
            resultMap.put("status", Boolean.FALSE);
            logger.error("## 删除订单号：[{}]批量开户的订单，出错", orderId, e);
        }
        return resultMap;
    }

    /**
     * 提交批量开户的订单信息
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
                batchOrderService.batchOpenAccountITF(orderId, user, BaseConstants.OMSOrderStat.orderStat_10.getCode());
            }
        } catch (Exception e) {
            resultMap.put("status", Boolean.FALSE);
            logger.error("## 提交批量开户的订单信息，订单号：[{}],出错", orderId, e);
        }
        return resultMap;
    }

    /**
     * 重新提交批量开户的订单信息
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
                batchOrderService.batchOpenAccountITF(orderId, user, BaseConstants.OMSOrderStat.orderStat_90.getCode());
            }
        } catch (Exception e) {
            resultMap.put("status", Boolean.FALSE);
            logger.error("## 重新提交批量开户的订单信息，订单号：[{}],出错", orderId, e);
        }
        return resultMap;
    }

    /**
     * 批量开户编辑页面，添加开户名单
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
        User user = this.getCurrUser(req);
        try {
            BatchOrderList orderList = new BatchOrderList();
            orderList.setOrderId(StringUtils.nullToString(req.getParameter("orderId")));
            orderList.setUserName(StringUtils.nullToString(req.getParameter("name")));
            orderList.setPhoneNo(StringUtils.nullToString(req.getParameter("phone")));
            orderList.setUserCardNo(StringUtils.nullToString(req.getParameter("card")));
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
            logger.error("## 批量开户编辑页面，添加开户名单,订单号：[{}],出错", req.getParameter("orderId"), e);
        }
        return resultMap;
    }

    /**
     * 批量开户编辑页面，删除开户名单
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
            logger.error("## 批量开户编辑页面，删除开户名单出错", e);
        }
        return resultMap;
    }

    /**
     * 批量开户新增，添加开户名单
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
        String phone = StringUtils.nullToString(req.getParameter("phone"));
        try {
            BatchOrderList person = new BatchOrderList();
            person.setPuid(UUID.randomUUID().toString().replace("-", ""));
            person.setUserName(StringUtils.nullToString(req.getParameter("name")));
            person.setUserCardNo(StringUtils.nullToString(req.getParameter("card")));
            person.setPhoneNo(phone);
            LinkedList<BatchOrderList> orderList = PagePersonUtil.getRedisBatchOrderList(OrderConstants.bathOpenAccountSession);
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
            orderList.addFirst(person);
            JedisClusterUtils.getInstance().setex(OrderConstants.bathOpenAccountSession, JSON.toJSON(orderList).toString(), 1800); // 设置有效时间30分钟
        } catch (Exception e) {
            resultMap.put("status", Boolean.FALSE);
            resultMap.put("msg", "系统故障，请稍后再试");
            logger.error("## 批量开户新增，添加开户名单出错！", e);
        }
        return resultMap;
    }

    /**
     * 批量开户新增，删除开户名单
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
            LinkedList<BatchOrderList> orderList = PagePersonUtil.getRedisBatchOrderList(OrderConstants.bathOpenAccountSession);
            for (BatchOrderList personInf : orderList) {
                if (personInf.getPuid().equals(puid)) {
                    orderList.remove(personInf);
                    break;
                }
            }
            JedisClusterUtils.getInstance().setex(OrderConstants.bathOpenAccountSession, JSON.toJSON(orderList).toString(), 1800);
        } catch (Exception e) {
            resultMap.put("status", Boolean.FALSE);
            resultMap.put("msg", "系统故障，请稍后再试");
            logger.error("## 批量开户新增，删除开户名单出错", e);
        }

        return resultMap;
    }

}
