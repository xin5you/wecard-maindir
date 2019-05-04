package com.cn.thinkx.service.test;

import com.cn.thinkx.service.test.TestRegistryService;

public class TestRegistryServiceMock implements TestRegistryService {

	@Override
	public String hello(String name) {
		System.out.println("This is a test mock class!");
		return "Mock, " + name;
	}

}
