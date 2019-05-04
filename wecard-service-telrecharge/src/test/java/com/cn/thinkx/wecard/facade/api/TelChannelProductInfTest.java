package com.cn.thinkx.wecard.facade.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelProductInf;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelProductInf;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelProductInfFacade;


@RunWith(SpringJUnit4ClassRunner.class)    
@ContextConfiguration(locations = {"classpath*:/conf/applicationContext.xml"}) 
public class TelChannelProductInfTest {

	@Autowired
	private TelChannelProductInfFacade telChannelProductInfFacade;
	
//	@Test
//	public void save(){
//		TelChannelProductInf	telChannelProductInf =new TelChannelProductInf();
//		try {
//			telChannelProductInf.setDataStat("0");
//			telChannelProductInfFacade.saveTelChannelProductInf(telChannelProductInf);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
// 	}
	
	@Test
	public void delete(){
		try {
			telChannelProductInfFacade.deleteTelChannelProductInfById("2018070505141600000029");
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
	
	
//	@Test
//	public void getById(){
//		
//		System.out.println("-----------------");
//		TelChannelProductInf  telChannelProductInf=null;
//		try {
//			telChannelProductInf = telChannelProductInfFacade.getTelChannelProductInfById("2018070505141600000029");
//			telChannelProductInf.setAreaFlag("1");
//			telChannelProductInfFacade.updateTelChannelProductInf(telChannelProductInf);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
// 	}
	
}
