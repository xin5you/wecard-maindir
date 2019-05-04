package com.cn.thinkx.wecard.api.module.trans.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.wecard.api.module.trans.mapper.CtrlSystemMapper;
import com.cn.thinkx.wecard.api.module.trans.service.CtrlSystemService;

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
