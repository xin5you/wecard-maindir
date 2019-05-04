package com.cn.thinkx.wxclient.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cn.thinkx.core.util.Constants;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;
import com.cn.thinkx.wxclient.domain.WxResource;
import com.cn.thinkx.wxclient.service.WxResourceService;


public class AuthorityHandlerInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(AuthorityHandlerInterceptor.class);

	@Autowired
	private WxResourceService wxResourceService;
	
	private String[] excludes=null;// 不需要拦截的
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String uri = request.getRequestURI();

		boolean oauthFlag = false;// 为方便展示的参数，开发者自行处理
		for (String s : excludes) {
			if (uri.contains(s)) {// 如果包含，就拦截
				oauthFlag = true;
				break;
			}
		}
		if (oauthFlag) {// 如果不需要拦截 则 返回
			return true;
		}
		HttpSession session = request.getSession();
		String openid = WxMemoryCacheClient.getOpenid(request);
		if(StringUtil.isNotEmpty(openid)){
			/***查询所属资源 **/
			List<WxResource> resourceList=null;
			resourceList=(List<WxResource>)session.getAttribute(Constants.MANAGER_RESOURCES_SESSION);
			if(resourceList==null || resourceList.size()<=0){
				WxResource wxResource=new WxResource();
				wxResource.setOpenId(openid);
				resourceList=wxResourceService.findWxResourceListByParam(wxResource);
				session.setAttribute(Constants.MANAGER_RESOURCES_SESSION, resourceList);
			}
			/**是否包含了该访问链接**/
			if(resourceList !=null && resourceList.size()>0){
				WxResource wr;
				boolean f=false;
				for(int i=0;i<resourceList.size();i++){
					wr=resourceList.get(i);
					if(uri.contains(wr.getDesp())){
						f=true;
						break;
					}
				}
				if(f){
					return true;
				}else{
					request.getRequestDispatcher("/wxclient/mchnt/unvalidated.html").forward(request, response);
					return false;
				}
			}else{
				request.getRequestDispatcher("/wxclient/mchnt/unvalidated.html").forward(request, response);
				return false;
			}
		}
		return true;
	}
	public String[] getExcludes() {
		return excludes;
	}

	public void setExcludes(String[] excludes) {
		this.excludes = excludes;
	}
	
	
}
