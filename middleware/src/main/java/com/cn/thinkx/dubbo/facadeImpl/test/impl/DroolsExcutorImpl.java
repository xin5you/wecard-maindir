package com.cn.thinkx.dubbo.facadeImpl.test.impl;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.biz.drools.model.PaymentInfo;
import com.cn.thinkx.biz.drools.service.DroolsService;
import com.cn.thinkx.service.test.IDroolsExcutor;

@Service("iDroolsExcutor")
public class DroolsExcutorImpl implements IDroolsExcutor {
	@Autowired
	@Qualifier("droolsService")
	private DroolsService dService;
	
	@Override
	public int getDrools_test(int inMoney) {
		try {
			KieSession kSession = dService.getKieSession("KS_DISCOUNT");
			PaymentInfo message = new PaymentInfo();
			// 因子设置
			message.setInMoney(2000);
			System.out.println("消费金额：" + message.getInMoney());
			dService.excute(kSession, message, null, null, null, null);
			System.out.println("优惠后金额：" + message.getOutMoney());
			return (int) message.getOutMoney();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}