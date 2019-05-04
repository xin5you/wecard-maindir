package com.cn.thinkx.wecard.facade.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelOrderInf;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelOrderInf;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelOrderInfFacade;


@RunWith(SpringJUnit4ClassRunner.class)    
@ContextConfiguration(locations = {"classpath*:/conf/applicationContext.xml"}) 
public class TelChannelOrderInfTest {

	@Autowired
	private TelChannelOrderInfFacade telChannelOrderInfFacade;
	
//	@Test
//	public void save(){
//		TelChannelOrderInf	telChannelOrderInf =new TelChannelOrderInf();
//		try {
//			telChannelOrderInfFacade.saveTelChannelOrderInf(telChannelOrderInf);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
// 	}
	
	@Test
	public void delete(){
		try {
			telChannelOrderInfFacade.deleteTelChannelOrderInfById("2018070505105300000028");
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
	
	
//	@Test
//	public void getById(){
//		
//		System.out.println("-----------------");
//		TelChannelOrderInf  telChannelOrderInf=null;
//		try {
//			telChannelOrderInf = telChannelOrderInfFacade.getTelChannelOrderInfById("2018070505105300000028");
//			telChannelOrderInf.setChannelRate("0.997");
//			telChannelOrderInf.setRechargePhone("13501755206");
//			telChannelOrderInfFacade.updateTelChannelOrderInf(telChannelOrderInf);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
// 	}
	
}
