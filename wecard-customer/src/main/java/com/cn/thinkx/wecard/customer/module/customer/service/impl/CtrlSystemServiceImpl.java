package com.cn.thinkx.wecard.customer.module.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.common.wecard.module.ctrlsystem.mapper.CtrlSystemMapper;
import com.cn.thinkx.wecard.customer.module.customer.service.CtrlSystemService;

@Service("ctrlSystemService")
public class CtrlSystemServiceImpl implements CtrlSystemService {

	@Autowired
	@Qualifier("ctrlSystemMapper")
	private CtrlSystemMapper ctrlSystemMapper;

	@Override
	public CtrlSystem getCtrlSystem() {
		return ctrlSystemMapper.getCtrlSystem();
	}

}
