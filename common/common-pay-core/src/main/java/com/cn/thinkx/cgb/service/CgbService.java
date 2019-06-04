package com.cn.thinkx.cgb.service;

import com.cn.thinkx.cgb.model.CgbInit;
import com.cn.thinkx.cgb.model.CommHeadDTO;
import com.cn.thinkx.cgb.model.OutwardPaymentDTO;
import com.cn.thinkx.cgb.model.RequestParametersDTO;
import com.cn.thinkx.cgb.util.CGBUtil;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CgbService {

    public void dfPaymentResult() {
        CgbInit cgbInit = new CgbInit();
        CommHeadDTO commHeadDTO = new CommHeadDTO();
        commHeadDTO.setTranCode("0021");
        commHeadDTO.setCifMaster(cgbInit.getMaster());//
        commHeadDTO.setEntSeqNo("20190675312345671"+"001");
        commHeadDTO.setEntUserId(cgbInit.getUserId());
        commHeadDTO.setPassword(cgbInit.getPassword());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        commHeadDTO.setTranDate(new SimpleDateFormat("yyyyMMdd").format(time));
        commHeadDTO.setTranTime(new SimpleDateFormat("HHmmss").format(time));
        commHeadDTO.setRetCode("000");

        OutwardPaymentDTO outwardPaymentDTO = new OutwardPaymentDTO();
        outwardPaymentDTO.setOutAccName(cgbInit.getAccountName());
        outwardPaymentDTO.setOutAcc(cgbInit.getAccount());
        outwardPaymentDTO.setInAccName("朱秋友");
        outwardPaymentDTO.setInAcc("6214830215284406");
        outwardPaymentDTO.setInAccBank("招商银行");
        // outwardPaymentDTO.setInAccAdd();
        outwardPaymentDTO.setAmount("1");
        outwardPaymentDTO.setPaymentBankid("308290003011");

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
}
