package com.cn.thinkx.oms.module.enterpriseorder.service.impl;

import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.module.enterpriseorder.mapper.BatchInvoiceOrderMapper;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchInvoiceOrder;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchInvoiceOrderService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.NumberUtils;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.redis.core.JedisClusterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service("batchInvoiceOrderService")
public class BatchInvoiceOrderServiceImpl implements BatchInvoiceOrderService {

    Logger logger = LoggerFactory.getLogger(BatchInvoiceOrderServiceImpl.class);

    @Autowired
    @Qualifier("batchInvoiceOrderMapper")
    private BatchInvoiceOrderMapper batchInvoiceOrderMapper;

    @Override
    public BatchInvoiceOrder getBatchOrderByOrderId(String orderId) {
        BatchInvoiceOrder batchInvoiceOrder = null;
        try {
            batchInvoiceOrder = batchInvoiceOrderMapper.getBatchOrderByOrderId(orderId);
            if (!StringUtils.isNullOrEmpty(batchInvoiceOrder.getInvoiceAmt())) {
                batchInvoiceOrder.setInvoiceAmt(NumberUtils.RMBCentToYuan(batchInvoiceOrder.getInvoiceAmt()));
            } else {
                batchInvoiceOrder.setInvoiceAmt(NumberUtils.RMBCentToYuan("0"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查看批量充值开票出错", e);
        }
        return batchInvoiceOrder;
    }

    @Override
    public ModelMap addBatchInvoiceOrderCommit(HttpServletRequest req) {
        ModelMap resultMap = new ModelMap();
        resultMap.addAttribute("status", Boolean.TRUE);
        try {
            String orderId = StringUtils.nullToString(req.getParameter("orderId"));
            if (JedisClusterUtils.getInstance().get(orderId) == null) {
                if (getBatchInvoiceCountByOrderId(orderId) < 1) {
                    JedisClusterUtils.getInstance().set(orderId, orderId, 0);
                    BatchInvoiceOrder batchInvoiceOrder = new BatchInvoiceOrder();
                    batchInvoiceOrder.setOrderId(orderId);
                    batchInvoiceOrder.setInvoiceAmt(NumberUtils.RMBYuanToCent(StringUtils.nullToString(req.getParameter("invoiceAmt"))));
                    batchInvoiceOrder.setMchntCode(StringUtils.nullToString(req.getParameter("mchntCode")));
                    batchInvoiceOrder.setShopCode(StringUtils.nullToString(req.getParameter("shopCode")));
                    batchInvoiceOrder.setInvoiceInfo(StringUtils.nullToString(req.getParameter("invoiceInfo")));
                    HttpSession session = req.getSession();
                    User user = (User) session.getAttribute(Constants.SESSION_USER);
                    batchInvoiceOrder.setInvoiceType("批量充值开票");
                    batchInvoiceOrder.setInvoiceStat("1");    // 1代表已开票
                    batchInvoiceOrder.setInvoiceUserName(user.getName());
                    batchInvoiceOrder.setCreateUser(user.getId().toString());
                    batchInvoiceOrder.setUpdateUser(user.getId().toString());
                    batchInvoiceOrderMapper.insertBatchInvoiceOrder(batchInvoiceOrder);
                    JedisClusterUtils.getInstance().del(orderId);
                    return resultMap;
                } else {
                    logger.error("## 该条流水[{}]在别处已被开票", orderId);
                    resultMap.addAttribute("status", Boolean.FALSE);
                    resultMap.addAttribute("msg", "该条数据已被操作，请核实后再操作");
                    return resultMap;
                }
            } else {
                logger.error("## 该条流水[{}]在别处已被开票", orderId);
                resultMap.addAttribute("status", Boolean.FALSE);
                resultMap.addAttribute("msg", "该条数据已被操作，请核实后再操作");
                return resultMap;
            }
        } catch (Exception e) {
            resultMap.addAttribute("status", Boolean.FALSE);
            resultMap.addAttribute("msg", "开票失败");
            logger.error("## 开票失败", e);
        }
        return resultMap;
    }

    @Override
    public BatchInvoiceOrder getBatchInvoiceOrderByOrderId(String orderId) {
        BatchInvoiceOrder batchInvoiceOrder = new BatchInvoiceOrder();
        try {
            batchInvoiceOrder = batchInvoiceOrderMapper.getBatchInvoiceOrderByOrderId(orderId);
            if (!StringUtils.isNullOrEmpty(batchInvoiceOrder.getInvoiceAmt())) {
                batchInvoiceOrder.setInvoiceAmt(NumberUtils.RMBCentToYuan(batchInvoiceOrder.getInvoiceAmt()));
            } else {
                batchInvoiceOrder.setInvoiceAmt(NumberUtils.RMBCentToYuan("0"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("批量开票详情查询出错", e);
        }
        return batchInvoiceOrder;
    }

    @Override
    public int getBatchInvoiceCountByOrderId(String orderId) {
        return batchInvoiceOrderMapper.getBatchInvoiceCountByOrderId(orderId);
    }

}
