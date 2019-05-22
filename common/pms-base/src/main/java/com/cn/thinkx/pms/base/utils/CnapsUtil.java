package com.cn.thinkx.pms.base.utils;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.pms.base.domain.CnapsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 中付联行号工具类
 *
 * @author pucker
 * @date 2019/5/20 15:10
 */
public class CnapsUtil {
    private static Logger logger = LoggerFactory.getLogger(CnapsUtil.class);

    public static List<CnapsVO> cnapsList = new ArrayList<>();

    public static synchronized void setCnapsList(List<CnapsVO> list) {
        cnapsList = list;
        logger.info("加载联行号：{}", JSONObject.toJSONString(list));
    }

    public static List<CnapsVO>  getCnapsList() {
        return cnapsList;
    }
}
