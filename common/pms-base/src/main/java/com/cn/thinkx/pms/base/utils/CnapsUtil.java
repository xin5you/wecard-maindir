package com.cn.thinkx.pms.base.utils;

import com.cn.thinkx.pms.base.domain.CnapsVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 中付联行号工具类
 *
 * @author pucker
 * @date 2019/5/20 15:10
 */
public class CnapsUtil {
    public static List<CnapsVO> cnapsList = new ArrayList<>();

    public static synchronized void setCnapsList(List<CnapsVO> list) {
        cnapsList = list;
    }

    public static List<CnapsVO>  getCnapsList() {
        return cnapsList;
    }
}
