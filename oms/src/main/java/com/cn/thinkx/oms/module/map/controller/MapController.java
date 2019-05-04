package com.cn.thinkx.oms.module.map.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.map.model.MapResp;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.http.HttpClient;
import com.cn.thinkx.pms.base.http.HttpRequest;
import com.cn.thinkx.pms.base.http.HttpResponse;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "map")
public class MapController extends BaseController {
	Logger logger = LoggerFactory.getLogger(MapController.class);
	
	private final String MAP_QQ_KEY="NMMBZ-CJJW5-JSNIN-QSNST-YMN2K-XRFHH";

	/**
	 * 本接口提供由地址描述到所述位置坐标的转换
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getGeocoder")
	@ResponseBody
	public MapResp getGeocoder(HttpServletRequest req, HttpServletResponse response) {
		MapResp resp =new MapResp();
		
		String province=StringUtils.nullToString(req.getParameter("province"));
		String city=StringUtils.nullToString(req.getParameter("city"));
		String district=StringUtils.nullToString(req.getParameter("district"));
		String address=StringUtils.nullToString(req.getParameter("address"));
		
		StringBuffer sbf=new StringBuffer("http://apis.map.qq.com/ws/geocoder/v1/?address=");
		sbf.append(province);
		if(!"市辖区".equals(city) && !"县".equals(city)){
			sbf.append(city);
		}
		sbf.append(district).append(address).append("&key=").append(MAP_QQ_KEY);
		HttpRequest r = new HttpRequest(sbf.toString());
		HttpResponse s=null;
		try {
			s = HttpClient.get(r);
	    	JSONObject json=JSONObject.fromObject(s.getStringResult());
	    	resp = (MapResp) JSONObject.toBean(json, MapResp.class);
		} catch (Exception e) {
			logger.error("## 获取经纬度失败：{}", e);
			resp.setStatus(1);
			resp.setMessage(province+city+district+address+"地理定位失败，请联系管理员");
		}
		
		return resp;
	} 
}
