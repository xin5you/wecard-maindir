package com.cn.thinkx.oms.module.withdrawBlacklist.controller;

import com.alibaba.fastjson.JSON;
import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.withdrawBlacklist.model.WithdrawBlacklist;
import com.cn.thinkx.oms.module.withdrawBlacklist.service.BatchOpenWBService;
import com.cn.thinkx.oms.module.withdrawBlacklist.util.PageWithdrawBlacklistUtil;
import com.cn.thinkx.oms.module.withdrawBlacklist.util.WBConstants;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.redis.core.JedisClusterUtils;
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
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "enterpriseOrder/batchOpenWBAccount")
public class BatchOpenWBController extends BaseController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("batchOpenWBService")
    private BatchOpenWBService batchOpenWBService;

    /**
     * 批量提现黑名单列表
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/listOpenWBAccount")
    public ModelAndView listOpenWBAccount(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("enterpriseOrder/batchOpenWithdrawBlacklist/listOpenWithdrawBlacklist");
        String operStatus = StringUtils.nullToString(req.getParameter("operStatus"));
        PageInfo<WithdrawBlacklist> pageList = null;
        int startNum = parseInt(req.getParameter("pageNum"), 1);
        int pageSize = parseInt(req.getParameter("pageSize"), 10);
        WithdrawBlacklist wb = new WithdrawBlacklist();
        wb.setStartTime(StringUtils.nullToString(req.getParameter("startTime")));
        wb.setEndTime(StringUtils.nullToString(req.getParameter("endTime")));
        wb.setUserName(StringUtils.nullToString(req.getParameter("userName")));
        wb.setUserPhone(StringUtils.nullToString(req.getParameter("userPhone")));
        try {
            pageList = batchOpenWBService.getWithdrawBlacklistPage(startNum, pageSize, wb);
        } catch (Exception e) {
            logger.error("## 批量提现黑名单查询列表信息出错", e);
        }
        mv.addObject("withdrawBlacklist", wb);
        mv.addObject("pageInfo", pageList);
        mv.addObject("operStatus", operStatus);
        return mv;
    }

    /**
     * 进入批量提现黑名单
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/intoAddOpenWBAccount")
    public ModelAndView intoAddOpenWBAccount(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("enterpriseOrder/batchOpenWithdrawBlacklist/addOpenWithdrawBlacklist");
        LinkedList<WithdrawBlacklist> wbInfList = PageWithdrawBlacklistUtil
                .getRedisWithdrawBlacklist(WBConstants.bathOpenWBSession);
        int startNum = parseInt(req.getParameter("pageNum"), 1);
        int pageSize = parseInt(req.getParameter("pageSize"), 10);
        Page<WithdrawBlacklist> page = new Page<>(startNum, pageSize, false);
        if (wbInfList != null) {
            page.setTotal(wbInfList.size());
            List<WithdrawBlacklist> list = PageWithdrawBlacklistUtil.getWithdrawBlacklistInfPageList(startNum, pageSize,
                    wbInfList);
            for (WithdrawBlacklist o : list) {
                page.add(o);
            }
        } else {
            page.setTotal(0);
        }
        PageInfo<WithdrawBlacklist> pageList = new PageInfo<WithdrawBlacklist>(page);
        mv.addObject("pageInfo", pageList);
        mv.addObject("count", page.getTotal());
        return mv;
    }

    /**
     * 批量提现黑名单提交
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/addOpenWBAccountCommit")
    @ResponseBody
    public ModelMap addOpenWBAccountCommit(HttpServletRequest req, HttpServletResponse response) {
        return batchOpenWBService.insertWithdrawBlacklist(req);
    }

    /**
     * 删除批量提现黑名单的订单信息
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/deleteOpenWBAccountCommit")
    @ResponseBody
    public ModelMap deleteOpenWBAccountCommit(HttpServletRequest req, HttpServletResponse response) {
        ModelMap resultMap = new ModelMap();
        int i = 0;
        resultMap.put("status", Boolean.TRUE);
        String id = StringUtils.nullToString(req.getParameter("id"));
        try {
            if (StringUtils.isNullOrEmpty(id)) {
                resultMap.put("status", Boolean.FALSE);
                resultMap.put("msg", "删除提现黑名单失败");
                logger.error("## 删除提现黑名单失败，id：[{}]", id);
                return resultMap;
            }
            i = batchOpenWBService.deleteWithdrawBlacklistById(id);
            if (i < 0) {
                resultMap.put("status", Boolean.FALSE);
                resultMap.put("msg", "删除提现黑名单失败");
                logger.error("## 删除提现黑名单失败，i：[{}]", i);
                return resultMap;
            }
        } catch (Exception e) {
            resultMap.put("status", Boolean.FALSE);
            logger.error("## 删除订单号：[{}]批量开户的订单，出错", id, e);
        }
        return resultMap;
    }

    /**
     * 批量提现黑名单新增，添加开户名单
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/addWBAccountInf")
    @ResponseBody
    public ModelMap addWBAccountInf(HttpServletRequest req, HttpServletResponse response) {
        ModelMap resultMap = new ModelMap();
        resultMap.put("status", Boolean.TRUE);
        String phone = StringUtils.nullToString(req.getParameter("phone"));
        try {
            WithdrawBlacklist wb = new WithdrawBlacklist();
            wb.setPuid(UUID.randomUUID().toString().replace("-", ""));
            wb.setUserName(StringUtils.nullToString(req.getParameter("name")));
            wb.setUserPhone(phone);

            LinkedList<WithdrawBlacklist> wbInfList = PageWithdrawBlacklistUtil
                    .getRedisWithdrawBlacklist(WBConstants.bathOpenWBSession);
            if (wbInfList == null) {
                wbInfList = new LinkedList<WithdrawBlacklist>();
            }
            for (WithdrawBlacklist withdrawBlacklist : wbInfList) {
                if (withdrawBlacklist.getUserPhone().equals(phone)) {
                    resultMap.put("status", Boolean.FALSE);
                    resultMap.put("msg", "电话号码重复！！！");
                    return resultMap;
                }
            }
            wbInfList.addFirst(wb);
            JedisClusterUtils.getInstance().setex(WBConstants.bathOpenWBSession, JSON.toJSON(wbInfList).toString(),
                    1800); // 设置有效时间30分钟
        } catch (Exception e) {
            resultMap.put("status", Boolean.FALSE);
            resultMap.put("msg", "系统故障，请稍后再试");
            logger.error("## 批量开户新增，添加开户名单出错！", e);
        }
        return resultMap;
    }

    /**
     * 批量开户新增，删除提现黑名单
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/deleteWBAccountInf")
    @ResponseBody
    public ModelMap deleteWBAccountInf(HttpServletRequest req, HttpServletResponse response) {
        ModelMap resultMap = new ModelMap();
        resultMap.put("status", Boolean.TRUE);
        try {
            String puid = req.getParameter("puid");
            LinkedList<WithdrawBlacklist> wbInfList = PageWithdrawBlacklistUtil
                    .getRedisWithdrawBlacklist(WBConstants.bathOpenWBSession);
            for (WithdrawBlacklist withdrawBlacklist : wbInfList) {
                if (withdrawBlacklist.getPuid().equals(puid)) {
                    wbInfList.remove(withdrawBlacklist);
                    break;
                }
            }
            JedisClusterUtils.getInstance().setex(WBConstants.bathOpenWBSession, JSON.toJSON(wbInfList).toString(),
                    1800);
        } catch (Exception e) {
            resultMap.put("status", Boolean.FALSE);
            resultMap.put("msg", "系统故障，请稍后再试");
            logger.error("## 批量开户新增，删除开户名单出错", e);
        }
        return resultMap;
    }

}
