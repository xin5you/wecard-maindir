package com.cn.thinkx.oms.module.cardkeys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.cardkeys.mapper.CardKeysTransLogMapper;
import com.cn.thinkx.oms.module.cardkeys.model.CardKeysTransLog;
import com.cn.thinkx.oms.module.cardkeys.service.CardKeysTransLogService;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("cardKeysTransLogService")
public class CardKeysTransLogServiceImpl implements CardKeysTransLogService {

	@Autowired
	private CardKeysTransLogMapper cardKeysTransLogMapper;

	@Override
	public PageInfo<CardKeysTransLog> getCardKeysTransLogPage(int startNum, int pageSize, CardKeysTransLog entity) {
		PageHelper.startPage(startNum, pageSize);
		List<CardKeysTransLog> list = this.cardKeysTransLogMapper.getCardKeysTransLogByTransLog(entity);
		if (list != null && list.size() > 0) {
			for (CardKeysTransLog ckt : list) {
				ckt.setTransId(BaseConstants.TransType.findByCode(ckt.getTransId()).getValue());
				if (!StringUtil.isNullOrEmpty(ckt.getTransResult())) {
					if (ckt.getTransResult().equals(BaseConstants.RESPONSE_SUCCESS_CODE.toString())) {
						ckt.setTransResult("交易成功");
					} else if (ckt.getTransResult().equals(BaseConstants.TXN_TRANS_ERROR.toString())) {
						ckt.setTransResult("交易失败");
					}
				}
				if (!StringUtil.isNullOrEmpty(ckt.getTransFeeType())) {
					ckt.setTransFeeType(BaseConstants.TransFeeType.findByCode(ckt.getTransFeeType()).getValue());
				}
				ckt.setTransAmt(NumberUtils.RMBCentToYuan(ckt.getTransAmt()));	
				ckt.setOrgTransAmt(NumberUtils.RMBCentToYuan(ckt.getOrgTransAmt()));	
			}
		}
		PageInfo<CardKeysTransLog> page = new PageInfo<CardKeysTransLog>(list);
		return page;
	}

	@Override
	public List<CardKeysTransLog> getCardKeysTransLogByTransLog(CardKeysTransLog ckt) {
		return this.cardKeysTransLogMapper.getCardKeysTransLogByTransLog(ckt);
	}


}
