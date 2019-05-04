package com.cn.thinkx.wecard.api.module.trans.mapper;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.beans.CtrlSystem;

@Repository("ctrlSystemMapper")
public interface CtrlSystemMapper {
	
	/**
	 * 得到唯一日切信息
	 * @return
	 */
	CtrlSystem getCtrlSystem();
	
}
