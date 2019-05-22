package com.cn.thinkx.pms.base.utils;

import com.cn.thinkx.pms.base.domain.CnapsVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

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

    /**
     *  获取联行号
     * @param bankName
     * @param province
     * @param city
     * @return
     */
    public static String getCnapsNo(String bankName,String province,String city){
        Optional<CnapsVO> optionalList = cnapsList.stream().filter(
                cnapsVO -> bankName.equals(cnapsVO.getBankName()) && province.contains(cnapsVO.getProvince())  &&  city.contains(cnapsVO.getCity())
        ).findFirst();
        if (optionalList.isPresent()) {
            return optionalList.get().getCnapsNo();
        }
        return "";
    }
}
