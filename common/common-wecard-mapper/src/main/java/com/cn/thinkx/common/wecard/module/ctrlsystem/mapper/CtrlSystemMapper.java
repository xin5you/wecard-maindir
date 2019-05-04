package com.cn.thinkx.common.wecard.module.ctrlsystem.mapper;

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
