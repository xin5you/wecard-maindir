package com.cn.thinkx.dubbo.filter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;

public class AuthorityFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityFilter.class);

	
	private IpWhiteList ipWhiteList;

	public void setIpWhiteList(IpWhiteList ipWhiteList) {
		this.ipWhiteList = ipWhiteList;
	}

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		if (!ipWhiteList.isEnabled()) {
			return invoker.invoke(invocation);
		}
		String clientIp = RpcContext.getContext().getRemoteHost();
		List<String> allowedIps = ipWhiteList.getAllowedIps();
		if (allowedIps.contains(clientIp)) {
			return invoker.invoke(invocation);
		} else {
			LOGGER.info("IP[{}] 不在白名单内，拒绝访问", clientIp);
			return new RpcResult();
		}
	}

}
