package com.cn.thinkx.oms.module.trans.controller;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.statement.util.UploadUtil;
import com.cn.thinkx.oms.module.trans.model.CardTransInf;
import com.cn.thinkx.oms.module.trans.model.CardTransLog;
import com.cn.thinkx.oms.module.trans.model.CardTransLogUpload;
import com.cn.thinkx.oms.module.trans.service.CardTransLogService;
import com.cn.thinkx.oms.util.ChannelCodeUtils;
import com.cn.thinkx.oms.util.ExcelUtil;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.OMSChannel;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "trans/cardTransInf")
public class CardTransLogController extends BaseController {

    Logger logger = LoggerFactory.getLogger(CardTransLogController.class);

    @Autowired
    @Qualifier("cardTransLogService")
    private CardTransLogService cardTransLogService;

    /**
     * 商户交易列表查询
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/listTrans")
    public ModelAndView listTrans(HttpServletRequest req) {
        ModelAndView mv = new ModelAndView("trans/cardTrans/listCardTrans");

        PageInfo<CardTransLog> pageList = null;
        List<ChannelCodeUtils> codeList = new ArrayList<ChannelCodeUtils>();
        for (OMSChannel omsChannel : OMSChannel.values()) {
            ChannelCodeUtils ccu = new ChannelCodeUtils(omsChannel.getCode(), omsChannel.getName());
            codeList.add(ccu);
        }
        int startNum = parseInt(req.getParameter("pageNum"), 1);
        int pageSize = parseInt(req.getParameter("pageSize"), 10);
        CardTransInf entity = getTransInf(req);
        try {
            pageList = cardTransLogService.getTransLogPage(startNum, pageSize, entity);
            for (CardTransLog transLog : pageList.getList()) {
                String chnlName = BaseConstants.OMSChannel.contansValue(transLog.getTransChnl());
                transLog.setChnlName(chnlName);
            }
        } catch (Exception e) {
            logger.error("## 查询通卡交易列表出错", e);
        }
        mv.addObject("pageInfo", pageList);
        mv.addObject("transInf", entity);
        mv.addObject("transTypeList", BaseConstants.TransCode.values());
        mv.addObject("ChannelCodeList", codeList);
        return mv;
    }

    /**
     * 导出excle表格
     *
     * @param req
     * @param response
     */
    @RequestMapping(value = "/uploadListTrans")
    public void uploadListTrans(HttpServletRequest req, HttpServletResponse response) {
        try {
            CardTransInf entity = getTransInf(req);
            List<CardTransLogUpload> listTransLog = cardTransLogService.getTransLogList(entity);
            for (CardTransLogUpload transLogUpload : listTransLog) {
                String chnlName = BaseConstants.OMSChannel.contansValue(transLogUpload.getTransChnl());
                transLogUpload.setChnlName(chnlName);
            }
            String title = "通卡交易明细列表";
            String titlerow = "通卡交易报表";
            String[] titlehead = new String[]{"流水号", "清算日", "渠道", "用户名", "卡号", "账户号", "商户名称", "门店名称", "交易类型", "交易金额",
                    "交易状态", "交易时间", "参考号", "交易渠道",};
            ExcelUtil<CardTransLogUpload> ex = new ExcelUtil<CardTransLogUpload>();
            HSSFWorkbook workBook = ex.exportExcel(title, titlerow,
                    DateUtil.getFormatStringFormString(entity.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD),
                    DateUtil.getFormatStringFormString(entity.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD), titlehead,
                    null, null, listTransLog, CardTransLogUpload.class, null);
            UploadUtil.upLoad(workBook, title, response);
        } catch (Exception e) {
            logger.error("## 导出通卡汇总报表出错", e);
        }
    }

    /**
     * 封装TransInf实体
     *
     * @param req
     * @return
     */
    public CardTransInf getTransInf(HttpServletRequest req) {
        CardTransInf entity = new CardTransInf();
        entity.setMchntCode(RedisDictProperties.getInstance().getdictValueByCode("ACC_HKB_MCHNT_NO"));
        String channelCode = req.getParameter("channelCode");
        entity.setOmschannelCode(channelCode);
        if (!"".equals(channelCode) && channelCode != null) {
            OMSChannel omsc = BaseConstants.OMSChannel.findByCode(channelCode);
            entity.setChannelCode(omsc.getValue().split(","));
        }
        entity.setQueryType(StringUtils.nullToString(req.getParameter("queryType")));
        entity.setTransType(StringUtils.nullToString(req.getParameter("transType")));
        entity.setUserName(StringUtils.nullToString(req.getParameter("userName")));
        entity.setStartTime(StringUtils.nullToString(req.getParameter("startTime")));
        entity.setEndTime(StringUtils.nullToString(req.getParameter("endTime")));
        return entity;
    }
}
