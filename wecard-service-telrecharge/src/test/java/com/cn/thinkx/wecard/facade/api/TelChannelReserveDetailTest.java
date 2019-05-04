package com.cn.thinkx.wecard.facade.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelReserveDetail;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelReserveDetail;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelReserveDetailFacade;


@RunWith(SpringJUnit4ClassRunner.class)    
@ContextConfiguration(locations = {"classpath*:/conf/applicationContext.xml"}) 
public class TelChannelReserveDetailTest {

	@Autowired
	private TelChannelReserveDetailFacade telChannelReserveDetailFacade;
	
//	@Test
//	public void save(){
//		TelChannelReserveDetail	telChannelReserveDetail =new TelChannelReserveDetail();
//		try {
//			telChannelReserveDetail.setDataStat("0");
//			telChannelReserveDetail.setChannelId("11111111111111111111");
//			telChannelReserveDetailFacade.saveTelChannelReserveDetail(telChannelReserveDetail);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
// 	}
	
	@Test
	public void delete(){
		try {
			telChannelReserveDetailFacade.deleteTelChannelReserveDetailById("2018070505201000000031");
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
	
	
//	@Test
//	public void getById(){
//		
//		System.out.println("-----------------");
//		TelChannelReserveDetail  telChannelReserveDetail=null;
//		try {
//			telChannelReserveDetail = telChannelReserveDetailFacade.getTelChannelReserveDetailById("2018070505201000000031");
//			telChannelReserveDetail.setRemarks("123456789");
//			telChannelReserveDetailFacade.updateTelChannelReserveDetail(telChannelReserveDetail);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
// 	}
	
}
