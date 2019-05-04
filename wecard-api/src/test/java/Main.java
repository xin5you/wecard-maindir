import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.common.redis.vo.BoxDeviceInfoVO;
import com.cn.thinkx.wecard.api.module.pay.req.AggrPayReq;
import com.cn.thinkx.wecard.api.module.pay.req.PPScanCodeReq;
import com.cn.thinkx.wecard.api.module.pay.resp.PPScanCodeResp;
import com.cn.thinkx.wecard.api.module.pay.service.impl.PPBoxPayServiceImpl;
import com.cn.thinkx.wecard.api.module.pay.utils.PPBoxPayUtil;

public class Main {
	
//	private static Logger logger = LoggerFactory.getLogger(Main.class);
//	private static PPScanCodeResp doAggregatePay(PPScanCodeReq req, BoxDeviceInfoVO deviceVO) {
//		AggrPayReq aggr = new AggrPayReq();
//		PPScanCodeResp resp = null; 
//		try {
//			BeanUtils.copyProperties(req, aggr);
//			BeanUtils.copyProperties(deviceVO, aggr);
//			String areq = JSONObject.toJSONString(aggr);
//			String result = PPBoxPayUtil.sendGet("http://192.168.1.19:8080/aggrpay-web/boxpay/scancode", "message=" +areq);
//			logger.info("请求聚合支付的参数------->[{}]",areq);
//			resp = JSONArray.parseObject(result, PPScanCodeResp.class);
//			logger.info("聚合支付返回的参数------->[{}]",result);
//		} catch (Exception e) {
//			logger.error("## base64编码转换异常，流水号[{}]", e.getMessage());
//		}
//		return resp;
//	}
//	
//	public static void main(String[] args) {
//		PPScanCodeReq req = new PPScanCodeReq(); 
//		BoxDeviceInfoVO deviceVO = new BoxDeviceInfoVO();
//		req.setAuth_code("137431846689693615");
//		deviceVO.setMchntCode("101000000135142");
//		req.setTotal_fee("0.01");
//		req.setPp_trade_no("" + System.currentTimeMillis());
//		doAggregatePay(req,deviceVO);
//	}
}
