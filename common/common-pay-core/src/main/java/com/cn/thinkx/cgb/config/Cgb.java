package com.cn.thinkx.cgb.config;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.cn.thinkx.cgb.model.CgbRequestDTO;
import com.cn.thinkx.cgb.service.CgbService;

/**
 * @Author: lq
 * @Date: 2019/6/3 12:56
 */
public class Cgb {
    public static void main(String[] args) {
        CgbRequestDTO cgbRequestDTO=new CgbRequestDTO();

        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        long id = snowflake.nextId(); //获取雪花算法，获取分布式id

        cgbRequestDTO.setEntSeqNo(String.valueOf(id));

        JSONObject jsonObject= new CgbService().queryAccountBalResult(cgbRequestDTO);

        System.out.println(jsonObject.getJSONObject("BEDC").getJSONObject("Message").getJSONObject("Body"));
    }
}
