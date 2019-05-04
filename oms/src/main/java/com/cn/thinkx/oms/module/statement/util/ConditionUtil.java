package com.cn.thinkx.oms.module.statement.util;

import javax.servlet.http.HttpServletRequest;

import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.DateUtil;

public class ConditionUtil {
	public static Condition getCondition(HttpServletRequest req) throws Exception {
		Condition condition = new Condition();
		condition.setMchntCode(StringUtils.stringToNull(req.getParameter("mchntCode")));
		condition.setShopCode(StringUtils.stringToNull(req.getParameter("shopCode")));
		condition.setPositCode(StringUtils.stringToNull(req.getParameter("positCode")));
		String startTime = StringUtils.stringToNull(req.getParameter("startTime"));
		if (startTime != null) {
			condition.setStartTime(DateUtil.getFormatStringFormString(startTime, DateUtil.FORMAT_YYYYMMDD));
		}
		String endTime = StringUtils.stringToNull(req.getParameter("endTime"));
		if (endTime != null) {
			condition.setEndTime(DateUtil.getFormatStringFormString(endTime, DateUtil.FORMAT_YYYYMMDD));
		}
		return condition;
	}
}
