package com.cn.thinkx.oms.module.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.oms.module.common.mapper.CtrlSystemMapper;
import com.cn.thinkx.oms.module.common.service.CtrlSystemService;

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
