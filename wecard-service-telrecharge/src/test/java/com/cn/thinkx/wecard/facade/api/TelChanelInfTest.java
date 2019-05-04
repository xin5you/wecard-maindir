package com.cn.thinkx.wecard.facade.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelInf;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelInfFacade;

import net.sf.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)    
@ContextConfiguration(locations = {"classpath*:/conf/applicationContext.xml"}) 
public class TelChanelInfTest {

	@Autowired
	private TelChannelInfFacade telChannelInfFacade;
	
//	@Test
//	public void getById(){
//		
//		System.out.println("-----------------");
//		TelChannelInf telChannle=null;
//		try {
//			telChannle = telChannelInfFacade.getTelChannelInfById("1");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println(JSONObject.fromObject(telChannle));
// 	}
	
	@Test
	public void delete(){
		try {
			 telChannelInfFacade.deleteTelChannelInfById("1");
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
}
