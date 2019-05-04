package com.cn.thinkx.test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.thinkx.oms.module.city.model.CityInf;
import com.cn.thinkx.oms.module.city.service.CityInfService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/conf/spring-mybatis.xml"})
public class CityInfDaoTest {


	@Autowired
	@Qualifier("cityInfService")
	private CityInfService cityInfService;
	
	@Autowired
	@Test
	public void CityInfTest() throws Exception{
		
		String filePath="c:/city/city.txt";
		
		File file = new File(filePath);
		if(!file.exists()){
			file.createNewFile();
		}
		FileOutputStream out=new FileOutputStream(filePath);
		byte[] buff=new byte[]{};

	try{
		CityInf c1=new CityInf();
		String pid1="100000";
		c1.setParentId(pid1);
		List<CityInf> allList=cityInfService.findCityInfList(c1);
		
		if(allList !=null && allList.size()>0){
			
			StringBuffer sb1 =new StringBuffer();
			sb1.append("\n").append("86").append(":{").append("\n");
			
			for(int a=0;a<allList.size();a++){
				
				sb1.append("\n").append(allList.get(a).getId()).append(":'").append(allList.get(a).getCityName()).append("'");
				if(a<allList.size()-1){
					sb1.append(",").append("\n");
				}
				
				CityInf cityInf=new CityInf();
				String pid=allList.get(a).getId();
				cityInf.setParentId(pid);
				List<CityInf> list=cityInfService.findCityInfList(cityInf);
				
				if(list !=null && list.size()>0){
					StringBuffer sb =new StringBuffer();
					sb.append("\n").append(pid).append(":{").append("\n");
					
					for(int i=0;i<list.size();i++){
					
						sb.append("\n").append(list.get(i).getId()).append(":'").append(list.get(i).getCityName()).append("'");
						if(i<list.size()-1){
							 sb.append(",").append("\n");
						}
						
						CityInf entity=new CityInf();
						entity.setParentId(list.get(i).getId());
						List<CityInf> list2=cityInfService.findCityInfList(entity);
						
						if(list2 !=null && list2.size()>0){
							StringBuffer sb2 =new StringBuffer();
							sb2.append(list.get(i).getId()).append(":{").append("\n");
							for(int j=0;j<list2.size();j++){
								sb2.append(list2.get(j).getId()).append(":'").append(list2.get(j).getCityName()).append("'");
								if(j<list2.size()-1){
								 sb2.append(",").append("\n");
								}
							}
							sb2.append("},");
							buff=sb2.toString().getBytes();
							out.write(buff,0,buff.length);
						}
					}
					sb.append("},");
					buff=sb.toString().getBytes();
					out.write(buff,0,buff.length);
				}
			
			}
			
			sb1.append("},");
			buff=sb1.toString().getBytes();
			out.write(buff,0,buff.length);
	}
		
	}catch(Exception ex){
		 ex.printStackTrace();
	}finally {
		out.close();
		out.flush();
	}
	
	}
}
