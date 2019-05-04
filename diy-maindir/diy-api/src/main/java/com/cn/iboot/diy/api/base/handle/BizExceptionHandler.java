package com.cn.iboot.diy.api.base.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.iboot.diy.api.base.constants.ExceptionEnum;
import com.cn.iboot.diy.api.base.domain.BaseResult;
import com.cn.iboot.diy.api.base.exception.BizHandlerException;
import com.cn.iboot.diy.api.base.utils.ResultsUtil;

/**
 * 统一异常处理
 * 
 * @author pucker
 *
 */
@ControllerAdvice
@ResponseBody
public class BizExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(BizExceptionHandler.class);
	
	@ExceptionHandler(value = Exception.class)
	public BaseResult<?> handler(Exception e) {
		logger.error(ExceptionEnum.ERROR_MSG, e);
		if (e instanceof BizHandlerException) {
			BizHandlerException bhe = (BizHandlerException) e;
			return ResultsUtil.error(bhe.getCode(), bhe.getMessage());
		}
		
		return ResultsUtil.error(ExceptionEnum.ERROR_CODE, ExceptionEnum.ERROR_MSG);
	}
}
