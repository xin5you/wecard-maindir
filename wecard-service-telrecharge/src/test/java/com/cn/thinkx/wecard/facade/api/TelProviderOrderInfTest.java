package com.cn.thinkx.wecard.facade.api;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.common.json.JSONObject;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelProviderOrderInf;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelProviderOrderInf;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelProviderOrderInfFacade;


@RunWith(SpringJUnit4ClassRunner.class)    
@ContextConfiguration(locations = {"classpath*:/conf/applicationContext.xml"}) 
public class TelProviderOrderInfTest {

	@Autowired
	private TelProviderOrderInfFacade telProviderOrderInfFacade;
	
//	@Test
//	public void save(){
//		TelProviderOrderInf	telProviderOrderInf =new TelProviderOrderInf();
//		try {
//			telProviderOrderInf.setDataStat("0");
//			telProviderOrderInf.setRemarks("11111111111111111111");
//			telProviderOrderInfFacade.saveTelProviderOrderInf(telProviderOrderInf);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
// 	}
	
	@Test
	public void delete(){
		try {
			telProviderOrderInfFacade.deleteTelProviderOrderInfById("2018070505260600000033");
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
	
	
//	@Test
//	public void getById(){
//		
//		System.out.println("-----------------");
//		TelProviderOrderInf  telProviderOrderInf=null;
//		try {
//			telProviderOrderInf = telProviderOrderInfFacade.getTelProviderOrderInfById("2018070505260600000033");
//			
//			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(telProviderOrderInf.getOperateTime()));
////			telProviderOrderInf.setOperateTime(new Date());
////			telProviderOrderInf.setRemarks("123456789");
////			telProviderOrderInfFacade.updateTelProviderOrderInf(telProviderOrderInf);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
// 	}
	
}
