package com.cn.iboot.diy;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "myBatis")
public class MyBatisProps {

	private String mapperLocations;
	private String configLocations;
	private String typeAliasesPackage;

	public String getMapperLocations() {
		return mapperLocations;
	}

	public void setMapperLocations(String mapperLocations) {
		this.mapperLocations = mapperLocations;
	}

	public String getConfigLocations() {
		return configLocations;
	}

	public void setConfigLocations(String configLocations) {
		this.configLocations = configLocations;
	}

	public String getTypeAliasesPackage() {
		return typeAliasesPackage;
	}

	public void setTypeAliasesPackage(String typeAliasesPackage) {
		this.typeAliasesPackage = typeAliasesPackage;
	}

}
