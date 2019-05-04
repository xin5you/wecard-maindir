package com.cn.thinkx.wecard.facade.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.thinkx.wecard.facade.telrecharge.model.TelProviderInf;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelProviderInf;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelProviderInfFacade;


@RunWith(SpringJUnit4ClassRunner.class)    
@ContextConfiguration(locations = {"classpath*:/conf/applicationContext.xml"}) 
public class TelProviderInfTest {

	@Autowired
	private TelProviderInfFacade telProviderInfFacade;
	
//	@Test
//	public void save(){
//		TelProviderInf	telProviderInf =new TelProviderInf();
//		try {
//			telProviderInf.setDataStat("0");
//			telProviderInf.setRemarks("11111111111111111111");
//			telProviderInfFacade.saveTelProviderInf(telProviderInf);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
// 	}
//	
	@Test
	public void delete(){
		try {
			telProviderInfFacade.deleteTelProviderInfById("2018070505222400000032");
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
	
	
//	@Test
//	public void getById(){
//		
//		System.out.println("-----------------");
//		TelProviderInf  telProviderInf=null;
//		try {
//			telProviderInf = telProviderInfFacade.getTelProviderInfById("2018070505222400000032");
//			telProviderInf.setRemarks("123456789");
//			telProviderInfFacade.updateTelProviderInf(telProviderInf);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
// 	}
	
}
