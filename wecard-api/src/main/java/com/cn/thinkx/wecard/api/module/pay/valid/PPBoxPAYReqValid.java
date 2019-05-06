package com.cn.thinkx.wecard.api.module.pay.valid;

import com.cn.thinkx.pms.base.redis.util.ChannelSignUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.api.module.pay.req.PPScanCodeReq;
import com.cn.thinkx.wecard.api.module.pay.resp.PPScanCodeResp;
import com.cn.thinkx.wecard.api.module.pay.utils.PPBoxConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * paipai盒子支付请求验证
 *
 * @author zqy
 */
public class PPBoxPAYReqValid {

    private static Logger logger = LoggerFactory.getLogger(PPBoxPAYReqValid.class);

    /**
     * 客户开户接口参数校验
     *
     * @param req
     * @param channelInf 渠道信息
     * @return
     */
    public static boolean scancodeValid(PPScanCodeReq req, PPScanCodeResp resp) {
//		resp.setCode(PPBoxConstants.PPBoxCode.FAIL.getValue());
//		resp.setSub_code(PPBoxConstants.PPBoxSubCode.INVALID_PARAMETER.getCode());
//		resp.setSub_msg(PPBoxConstants.PPBoxSubCode.INVALID_PARAMETER.getValue());
        if (StringUtil.isNullOrEmpty(req.getDevice_no())) {
            resp.setMsg("设备号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getPp_trade_no())) {
            resp.setMsg("订单号为空");
            return true;
        }
        //定额支付的金额允许为空
//		if (StringUtil.isNullOrEmpty(req.getTotal_fee())) {
//			resp.setMsg("订单金额为空");
//			return true;
//		}
        if (StringUtil.isNullOrEmpty(req.getNonce_str())) {
            resp.setMsg("随机数为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getSign())) {
            resp.setSub_code(PPBoxConstants.PPBoxSubCode.SIGNERROR.getCode());
            resp.setMsg("签名为空");
            return true;
        }
        if (!req.getSign().equals(ChannelSignUtil.genPPScanPaySign(req))) {
            resp.setSub_code(PPBoxConstants.PPBoxSubCode.SIGNERROR.getCode());
            resp.setMsg("签名错误");
            logger.info("请求参数[{}],签名[{}]", req, ChannelSignUtil.genPPScanPaySign(req));
            return true;
        }
        return false;
    }

}
