package com.cn.iboot.diy.api.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.cn.iboot.diy.api.base.utils.StringUtils;
import com.cn.iboot.diy.api.system.domain.Resource;
import com.cn.iboot.diy.api.system.service.ResourceService;

@Service
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	@Autowired
	private ResourceService resourceService ;
	
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}
	/**
	 * @PostConstruct是Java EE 5引入的注解，
	 * Spring允许开发者在受管Bean中使用它。当DI容器实例化当前受管Bean时，
	 * @PostConstruct注解的方法会被自动触发，从而完成一些初始化工作，
	 * 
	 * //加载所有资源与权限的关系
	 */
	@PostConstruct
	private void loadResourceDefine() {
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<Resource> resources = this.resourceService.getList();
			// TODO:ZZQ 通过资源名称来表示具体的权限 注意：必须"ROLE_"开头
			// 关联代码：applicationContext-security.xml
			// 关联代码：com.huaxin.security.MyUserDetailServiceImpl#obtionGrantedAuthorities
			resources.stream().forEach(res ->{
				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
				ConfigAttribute configAttribute = new SecurityConfig("ROLE_" + res.getKey());
				configAttributes.add(configAttribute);
				if(!StringUtils.isEmpty(res.getUrl()))
					resourceMap.put(res.getUrl(), configAttributes);
			});
		}
	}
	
	/**
	 * 返回所请求资源所需要的权限
	 * 
	 */
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		if(resourceMap == null)
			loadResourceDefine();
		if(requestUrl.indexOf("?")>-1)
			requestUrl=requestUrl.substring(0,requestUrl.indexOf("?"));
		Collection<ConfigAttribute> configAttributes = resourceMap.get(requestUrl);
//		if(configAttributes == null){
//			Collection<ConfigAttribute> returnCollection = new ArrayList<ConfigAttribute>();
//			 returnCollection.add(new SecurityConfig("ROLE_NO_USER")); 
//			return returnCollection;
//		}
		return configAttributes;
	}


}