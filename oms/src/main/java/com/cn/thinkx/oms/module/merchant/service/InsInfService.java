package com.cn.thinkx.oms.module.merchant.service;

import com.cn.thinkx.oms.module.merchant.model.InsInf;

/**
 * 组织机构
 * 
 * @author zqy
 *
 */
public interface InsInfService {

	public int addInsInf(InsInf insInf);

	public int updateInsInf(InsInf insInf);

	public InsInf getInsInfById(String id);
}
