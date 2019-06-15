package com.cn.thinkx.cgb.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.XML;
import com.cn.thinkx.cgb.config.Constart;
import com.cn.thinkx.cgb.model.*;
import com.cn.thinkx.cgb.util.CGBUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
public class CgbService {

    private Logger logger=LoggerFactory.getLogger(this.getClass());
    /**
     * 代付请求
     */
    public JSONObject dfPaymentResult(CgbRequestDTO cgbRequestDTO) {
        CgbInit cgbInit = new CgbInit();
        CommHeadDTO commHeadDTO = new CommHeadDTO();
        commHeadDTO.setTranCode("0021");
        commHeadDTO.setCifMaster(cgbInit.getMaster());//
        commHeadDTO.setEntSeqNo(cgbRequestDTO.getEntSeqNo());  //平台每日唯一订单号
        commHeadDTO.setEntUserId(cgbInit.getUserId());
        commHeadDTO.setPassword(cgbInit.getPassword());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        commHeadDTO.setTranDate(new SimpleDateFormat("yyyyMMdd").format(time));
        commHeadDTO.setTranTime(new SimpleDateFormat("HHmmss").format(time));
        commHeadDTO.setRetCode("000"); //成功返回的标识

        OutwardPaymentDTO outwardPaymentDTO = new OutwardPaymentDTO();
        outwardPaymentDTO.setOutAccName(cgbInit.getAccountName());
        outwardPaymentDTO.setOutAcc(cgbInit.getAccount());
        outwardPaymentDTO.setInAccName(cgbRequestDTO.getInAccName()); //收款人姓名
        outwardPaymentDTO.setInAcc(cgbRequestDTO.getInAcc());  //收款人银行卡号
        outwardPaymentDTO.setInAccBank(cgbRequestDTO.getInAccBank()); //"交通银行" 银行名称
        outwardPaymentDTO.setAmount(cgbRequestDTO.getAmount()); //金额 单位元
        outwardPaymentDTO.setPaymentBankid(cgbRequestDTO.getPaymentBankid()); //联行号

        /**
         * 非必填项 后期扩展
         */
        if(StringUtil.isNotEmpty(cgbRequestDTO.getComment())){
            outwardPaymentDTO.setComment(cgbRequestDTO.getComment());
        }
        if(StringUtil.isNotEmpty(cgbRequestDTO.getRemark())){
            outwardPaymentDTO.setRemark(cgbRequestDTO.getRemark());
        }
        if(StringUtil.isNotEmpty(cgbRequestDTO.getInAccAdd())){
           outwardPaymentDTO.setInAccAdd(cgbRequestDTO.getInAccAdd());
        }

        logger.info("#广发银行代付请求业务参数:outwardPaymentDTO={}" , outwardPaymentDTO);
        RequestParametersDTO<OutwardPaymentDTO> requestParametersDTO = new RequestParametersDTO<OutwardPaymentDTO>(outwardPaymentDTO);
        requestParametersDTO.setCommHeadDTO(commHeadDTO);
        String strXml = CGBUtil.getxml(requestParametersDTO, cgbInit.getPubPath(), cgbInit.getPvbPath());
        try {
            logger.info("#广发银行代付封装后请求参数：{}" ,strXml);
            String xmlMap = CGBUtil.doPost(cgbInit.getUpProt(), strXml);
            logger.info("#广发银行代付返回参数：{}" + xmlMap);
            JSONObject jsonObject = XML.toJSONObject(xmlMap);
            return jsonObject;
        } catch (Exception e) {
            logger.error("#广发银行代付发生异常：{}" , e);
            return null;
        }

    }

    /**
     * 查询订单状态
     */
    public JSONObject dfQueryOrderResult(CgbRequestDTO cgbRequestDTO) {
        CgbInit cgbInit = new CgbInit();
        CommHeadDTO commHeadDTO = new CommHeadDTO();
        commHeadDTO.setTranCode("1004");
        commHeadDTO.setCifMaster(cgbInit.getMaster());//
        commHeadDTO.setEntSeqNo(cgbRequestDTO.getEntSeqNo());
        commHeadDTO.setEntUserId(cgbInit.getUserId());
        commHeadDTO.setPassword(cgbInit.getPassword());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        commHeadDTO.setTranDate(new SimpleDateFormat("yyyyMMdd").format(time));
        commHeadDTO.setTranTime(new SimpleDateFormat("HHmmss").format(time));
        commHeadDTO.setRetCode("000");

        QueryPaymentDTO queryPaymentDTO=new QueryPaymentDTO();
        queryPaymentDTO.setOrigEntseqno(cgbRequestDTO.getOrigEntseqno());
        queryPaymentDTO.setOrigEntdate(cgbRequestDTO.getEntSeqNo());
        logger.info("#广发银行查询订单状态请求参数：{}", queryPaymentDTO);
        RequestParametersDTO<QueryPaymentDTO> requestParametersDTO = new RequestParametersDTO<QueryPaymentDTO>(queryPaymentDTO);
        requestParametersDTO.setCommHeadDTO(commHeadDTO);
        String strXml = CGBUtil.getxml(requestParametersDTO, cgbInit.getPubPath(), cgbInit.getPvbPath());
        try {
            logger.info("#广发银行查询订单状态封装后请求参数：{}" ,strXml);
            String xmlMap = CGBUtil.doPost(cgbInit.getUpProt(), strXml);
            logger.info("#广发银行查询订单返回参数：{}" + xmlMap);
            JSONObject jsonObject = XML.toJSONObject(xmlMap);
            return jsonObject;
        } catch (Exception e) {
            logger.error("#广发银行查询订单状态发生异常：{}" , e);
            return null;
        }

    }

    /**
     * 查询账号余额
     */
    public JSONObject queryAccountBalResult(CgbRequestDTO cgbRequestDTO) {
        CgbInit cgbInit = new CgbInit();
        CommHeadDTO commHeadDTO = new CommHeadDTO();
        commHeadDTO.setTranCode("0001");
        commHeadDTO.setCifMaster(cgbInit.getMaster());//
        commHeadDTO.setEntSeqNo(cgbRequestDTO.getEntSeqNo());
        commHeadDTO.setEntUserId(cgbInit.getUserId());
        commHeadDTO.setPassword(cgbInit.getPassword());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        commHeadDTO.setTranDate(new SimpleDateFormat("yyyyMMdd").format(time));
        commHeadDTO.setTranTime(new SimpleDateFormat("HHmmss").format(time));
        commHeadDTO.setRetCode("000");

        QueryAccountBalDTO accountBalDTO=new QueryAccountBalDTO();
        accountBalDTO.setAccount(Constart.account);
        logger.info("# 广发银行查询账户余额请求参数 QueryAccountBalDTO={}", accountBalDTO);
        RequestParametersDTO<QueryAccountBalDTO> requestParametersDTO = new RequestParametersDTO<QueryAccountBalDTO>(accountBalDTO);
        requestParametersDTO.setCommHeadDTO(commHeadDTO);
        String strXml = CGBUtil.getxml(requestParametersDTO, cgbInit.getPubPath(), cgbInit.getPvbPath());
        try {
            logger.info("#广发银行查询账号余额封装请求c参数：{}" + strXml);
            String xmlMap = CGBUtil.doPost(cgbInit.getUpProt(), strXml);
            logger.info("#广发银行查询账号余额返回参数：{}" + xmlMap);
            JSONObject jsonObject = XML.toJSONObject(xmlMap);
            return jsonObject;
        } catch (Exception e) {
            logger.error("#广发银行查询账户余额发生异常：{}" , e);
            return null;
        }

    }
}
