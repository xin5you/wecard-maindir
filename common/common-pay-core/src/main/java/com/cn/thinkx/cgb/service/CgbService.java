package com.cn.thinkx.cgb.service;

import com.cn.thinkx.cgb.model.*;
import com.cn.thinkx.cgb.util.CGBUtil;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CgbService {

    /**
     * 代付请求
     */
    public void dfPaymentResult() {
        CgbInit cgbInit = new CgbInit();
        CommHeadDTO commHeadDTO = new CommHeadDTO();
        commHeadDTO.setTranCode("0021");
        commHeadDTO.setCifMaster(cgbInit.getMaster());//
        commHeadDTO.setEntSeqNo("800000001200"+"102");
        commHeadDTO.setEntUserId(cgbInit.getUserId());
        commHeadDTO.setPassword(cgbInit.getPassword());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        commHeadDTO.setTranDate(new SimpleDateFormat("yyyyMMdd").format(time));
        commHeadDTO.setTranTime(new SimpleDateFormat("HHmmss").format(time));
        commHeadDTO.setRetCode("000");

        OutwardPaymentDTO outwardPaymentDTO = new OutwardPaymentDTO();
        outwardPaymentDTO.setOutAccName(cgbInit.getAccountName());
        outwardPaymentDTO.setOutAcc(cgbInit.getAccount());
        outwardPaymentDTO.setInAccName("何云东");
        outwardPaymentDTO.setInAcc("6222620110014498654");
        outwardPaymentDTO.setInAccBank("交通银行");
        // outwardPaymentDTO.setInAccAdd();
        outwardPaymentDTO.setAmount("10");
        outwardPaymentDTO.setPaymentBankid("301290050012");

        outwardPaymentDTO.setComment("测试");

        System.out.println(("outwardPaymentDTO {}" + outwardPaymentDTO));
        RequestParametersDTO<OutwardPaymentDTO> requestParametersDTO = new RequestParametersDTO<OutwardPaymentDTO>(outwardPaymentDTO);
        requestParametersDTO.setCommHeadDTO(commHeadDTO);
        String strXml = CGBUtil.getxml(requestParametersDTO, cgbInit.getPubPath(), cgbInit.getPvbPath());
        try {
            System.out.println("请求参数 {}" + strXml);
            String xmlMap = CGBUtil.doPost(cgbInit.getUpProt(), strXml);
            System.out.println("返回参数：" + xmlMap);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询订单状态
     */
    public void dfQueryOrderResult() {
        CgbInit cgbInit = new CgbInit();
        CommHeadDTO commHeadDTO = new CommHeadDTO();
        commHeadDTO.setTranCode("1004");
        commHeadDTO.setCifMaster(cgbInit.getMaster());//
        commHeadDTO.setEntSeqNo("800000001200"+"102");
        commHeadDTO.setEntUserId(cgbInit.getUserId());
        commHeadDTO.setPassword(cgbInit.getPassword());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        commHeadDTO.setTranDate(new SimpleDateFormat("yyyyMMdd").format(time));
        commHeadDTO.setTranTime(new SimpleDateFormat("HHmmss").format(time));
        commHeadDTO.setRetCode("000");

        QueryPaymentDTO queryPaymentDTO=new QueryPaymentDTO();
        queryPaymentDTO.setOrigEntseqno("800000001200102");
        queryPaymentDTO.setOrigEntdate("20190610");
        System.out.println(("queryPaymentDTO {}" + queryPaymentDTO));
        RequestParametersDTO<QueryPaymentDTO> requestParametersDTO = new RequestParametersDTO<QueryPaymentDTO>(queryPaymentDTO);
        requestParametersDTO.setCommHeadDTO(commHeadDTO);
        String strXml = CGBUtil.getxml(requestParametersDTO, cgbInit.getPubPath(), cgbInit.getPvbPath());
        try {
            System.out.println("请求参数 {}" + strXml);
            String xmlMap = CGBUtil.doPost(cgbInit.getUpProt(), strXml);
            System.out.println("返回参数：" + xmlMap);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询账号余额
     */
    public void queryAccountBalResult() {
        CgbInit cgbInit = new CgbInit();
        CommHeadDTO commHeadDTO = new CommHeadDTO();
        commHeadDTO.setTranCode("1004");
        commHeadDTO.setCifMaster(cgbInit.getMaster());//
        commHeadDTO.setEntSeqNo("800000001200"+"102");
        commHeadDTO.setEntUserId(cgbInit.getUserId());
        commHeadDTO.setPassword(cgbInit.getPassword());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        commHeadDTO.setTranDate(new SimpleDateFormat("yyyyMMdd").format(time));
        commHeadDTO.setTranTime(new SimpleDateFormat("HHmmss").format(time));
        commHeadDTO.setRetCode("000");

        QueryPaymentDTO queryPaymentDTO=new QueryPaymentDTO();
        queryPaymentDTO.setOrigEntseqno("800000001200102");
        queryPaymentDTO.setOrigEntdate("20190610");
        System.out.println(("queryPaymentDTO {}" + queryPaymentDTO));
        RequestParametersDTO<QueryPaymentDTO> requestParametersDTO = new RequestParametersDTO<QueryPaymentDTO>(queryPaymentDTO);
        requestParametersDTO.setCommHeadDTO(commHeadDTO);
        String strXml = CGBUtil.getxml(requestParametersDTO, cgbInit.getPubPath(), cgbInit.getPvbPath());
        try {
            System.out.println("请求参数 {}" + strXml);
            String xmlMap = CGBUtil.doPost(cgbInit.getUpProt(), strXml);
            System.out.println("返回参数：" + xmlMap);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
