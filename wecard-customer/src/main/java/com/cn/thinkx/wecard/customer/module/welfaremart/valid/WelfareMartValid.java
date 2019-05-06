package com.cn.thinkx.wecard.customer.module.welfaremart.valid;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.pms.base.redis.util.ChannelSignUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.TransOrderResp;
import com.cn.thinkx.wechat.base.wxapi.util.WxSignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.SortedMap;

public class WelfareMartValid {

    public static Logger logger = LoggerFactory.getLogger(WelfareMartValid.class);

    /**
     * 福利集市 重定向 参数校验
     *
     * @param resp
     * @return
     */
    public static boolean redirectValid(TransOrderResp resp) {
        logger.info("福利集市--->重定向接口请求参数[{}]", JSONArray.toJSONString(resp));
        if (StringUtil.isNullOrEmpty(resp.getChannel())) {
            logger.error("##福利集市--->重定向接口，渠道号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(resp.getRespResult())) {
            logger.error("##福利集市--->重定向接口，成功失败标识为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(resp.getUserId())) {
            logger.error("##福利集市--->重定向接口，用户ID为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(resp.getOrderId())) {
            logger.error("##福利集市--->重定向接口，商户订单号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(resp.getSign())) {
            logger.error("##福利集市--->重定向接口，签名为空");
            return true;
        }
        String genSign = ChannelSignUtil.genSign(resp);
        if (!genSign.equals(resp.getSign())) {
            StringBuffer buf = new StringBuffer();
            SortedMap<String, String> map = WxSignUtil.genSortedMap(resp);
            for (Map.Entry<String, String> item : map.entrySet()) {
                String key = item.getKey();
                String val = item.getValue();
                buf.append(key).append("=").append(val);
                buf.append("&");
            }
            logger.info("##福利集市--->重定向接口，验签失败，福利集市生成签名[{}]", genSign);
            return true;
        }
        return false;
    }

}
