package com.cn.thinkx.wecard.facade.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelAreaInf;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelAreaInfFacade;

@RunWith(SpringJUnit4ClassRunner.class)    
@ContextConfiguration(locations = {"classpath*:/conf/applicationContext.xml"}) 
public class TelChanelAreaInfTest {

	@Autowired
	private TelChannelAreaInfFacade telChanelAreaInfFacade;
	
	
//	@Test
//	public void save(){
//		TelChannelAreaInf	telChannelAreaInf =new TelChannelAreaInf();
//		try {
//			telChannelAreaInf.setAreaName("上海");
//			telChanelAreaInfFacade.saveTelChannelAreaInf(telChannelAreaInf);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
// 	}
	
	@Test
	public void getById(){
		
		System.out.println("-----------------");
		TelChannelAreaInf  telChannelAreaInf=null;
		try {
			telChannelAreaInf = telChanelAreaInfFacade.getTelChannelAreaInfById("2018070504552200000024");
			telChannelAreaInf.setAreaName("湖南");
			telChanelAreaInfFacade.updateTelChannelAreaInf(telChannelAreaInf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
 	}
	
//	@Test
//	public void delete(){
//		try {
//			telChanelAreaInfFacade.deleteTelChannelAreaInfById("2018070504552200000024");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
// 	}
	
}
