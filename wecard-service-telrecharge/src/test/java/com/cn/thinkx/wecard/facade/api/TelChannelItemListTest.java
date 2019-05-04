package com.cn.thinkx.wecard.facade.api;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelItemList;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelItemList;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelItemListFacade;

import net.sf.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)    
@ContextConfiguration(locations = {"classpath*:/conf/applicationContext.xml"}) 
public class TelChannelItemListTest {

	@Autowired
	private TelChannelItemListFacade telChannelItemListFacade;
	
//	@Test
//	public void save(){
//		TelChannelItemList	telChannelItemList =new TelChannelItemList();
//		try {
//			telChannelItemListFacade.saveTelChannelItemList(telChannelItemList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
// 	}
	
//	@Test
//	public void delete(){
//		try {
//			telChannelItemListFacade.deleteTelChannelItemListById("2018070505031700000025");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
// 	}
	
	
	@Test
	public void getById(){
		
		System.out.println("-----------------");
		TelChannelItemList  telChannelItemList=null;
		try {
			telChannelItemList = telChannelItemListFacade.getTelChannelItemListById("2018070505080300000027");
			telChannelItemList.setChannelRate(new BigDecimal(0.997));
			telChannelItemListFacade.updateTelChannelItemList(telChannelItemList);
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
	
}
