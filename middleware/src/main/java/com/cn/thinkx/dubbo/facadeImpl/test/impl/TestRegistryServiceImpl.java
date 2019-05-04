package com.cn.thinkx.dubbo.facadeImpl.test.impl;

import org.springframework.stereotype.Service;

import com.cn.thinkx.service.test.TestRegistryService;

@Service("testRegistryService")
public class TestRegistryServiceImpl implements TestRegistryService {

	@Override
	public String hello(String name) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Hello, " + name;
	}

}
