package com.cn.thinkx.oms.module.trans.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.trans.mapper.CardTransLogMapper;
import com.cn.thinkx.oms.module.trans.model.CardTransInf;
import com.cn.thinkx.oms.module.trans.model.CardTransLog;
import com.cn.thinkx.oms.module.trans.model.CardTransLogUpload;
import com.cn.thinkx.oms.module.trans.service.CardTransLogService;
import com.cn.thinkx.oms.util.NumberUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("cardTransLogService")
public class CardTransLogSerivceImpl implements CardTransLogService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("cardTransLogMapper")
	private CardTransLogMapper cardTransLogMapper;

	public PageInfo<CardTransLog> getTransLogPage(int startNum, int pageSize, CardTransInf entity) {
		List<CardTransLog> list = null;
		try {
			// PageHelper.startPage(startNum, pageSize);
			// list = this.getTransLogList(entity);
			if (StringUtil.isNullOrEmpty(entity.getQueryType()) || "cur".equals(entity.getQueryType())) {
				String curLogNum = cardTransLogMapper.getCurLogNum();
				entity.setTableNum(curLogNum);
			}

			PageHelper.startPage(startNum, pageSize);
			if ("his".equals(entity.getQueryType()))
				list = cardTransLogMapper.getTransLogHisByMchntCode(entity);
			else {
				list = cardTransLogMapper.getTransLogCurByMchntCode(entity);
			}
			if (list != null && list.size() > 0) {
				for (CardTransLog obj : list) {
					obj.setSettleDate(DateUtil.getFormatStringFormString(obj.getSettleDate(), "yyyy-MM-dd"));
					if (obj.getTransAmt() != null && !"".equals(obj.getTransAmt())) {
						obj.setTransAmt("" + NumberUtils.formatMoney(obj.getTransAmt()));
					}
					if (!StringUtil.isEmpty(obj.getRespCode())) {
						obj.setRespCode(BaseConstants.ITFRespCode.findByCode(obj.getRespCode()).getValue());
					}
					if (!StringUtil.isEmpty(obj.getTransId())) {
						obj.setTransId(BaseConstants.TransCode.findByCode(obj.getTransId()).getValue());
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询交易明细列表出错：", e);
		}
		PageInfo<CardTransLog> page = new PageInfo<CardTransLog>(list);

		return page;
	}

	@Override
	public List<CardTransLogUpload> getTransLogList(CardTransInf entity) {
		List<CardTransLogUpload> list = null;
		try {
			if (StringUtil.isNullOrEmpty(entity.getQueryType()) || "cur".equals(entity.getQueryType())) {
				String curLogNum = cardTransLogMapper.getCurLogNum();
				entity.setTableNum(curLogNum);
			}

			if ("his".equals(entity.getQueryType()))
				list = cardTransLogMapper.getTransLogUploadHisByMchntCode(entity);
			else {
				list = cardTransLogMapper.getTransLogUploadCurByMchntCode(entity);
			}

			if (list != null && list.size() > 0) {
				for (CardTransLogUpload obj : list) {
					obj.setSettleDate(DateUtil.getFormatStringFormString(obj.getSettleDate(), "yyyy-MM-dd"));
					if (obj.getTransAmt() != null && !"".equals(obj.getTransAmt())) {
						obj.setTransAmt("" + NumberUtils.formatMoney(obj.getTransAmt()));
					}
					if (!StringUtil.isEmpty(obj.getRespCode())) {
						obj.setRespCode(BaseConstants.ITFRespCode.findByCode(obj.getRespCode()).getValue());
					}
					if (!StringUtil.isEmpty(obj.getTransId())) {
						obj.setTransId(BaseConstants.TransCode.findByCode(obj.getTransId()).getValue());
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询交易明细列表出错：", e);
		}
		return list;
	}

}
