package com.cn.thinkx.pms.base.http;

import java.io.ByteArrayInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApacheHttpHandler implements HttpHandler {

	Logger LOG = LoggerFactory.getLogger(ApacheHttpHandler.class);

	private static String DEFAULT_CHARSET = "UTF-8";

	/** 连接超时时间，由bean factory设置，缺省为8秒钟 */
	private static int defaultConnectionTimeout = 8000;

	/** 回应超时时间, 由bean factory设置，缺省为30秒钟 */
	private static int defaultSoTimeout = 30000;

	private static int defaultMaxTotalConn = 80;
	
	private static int defaultMaxPerRoute = 16;

	/**
	 * HTTP连接管理器，该连接管理器必须是线程安全的.
	 */
	private static PoolingHttpClientConnectionManager connectionManager;
	
	private static CloseableHttpClient httpClient = null;
	
	private final static Object syncLock = new Object();

	/**
	 * 私有的构造方法
	 */
	ApacheHttpHandler() {
		// 创建一个线程安全的HTTP连接池
		connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(defaultMaxTotalConn);// 设置最大连接数
		connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);// 设置每个路由默认最大连接数
	}

	public HttpResponse post(HttpRequest request) throws Exception {
		return execute(Method.POST, request);
	}

	public HttpResponse get(HttpRequest request) throws Exception {
		return execute(Method.GET, request);
	}

	public HttpResponse stream(HttpRequest request) throws Exception {
		return execute(Method.STREAM, request);
	}

	private enum Method {
		POST, GET, STREAM,
	}

	private HttpResponse execute(Method methodType, HttpRequest request) throws Exception {
		// 配置请求的超时设置
		RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(defaultConnectionTimeout)
                .setConnectTimeout(defaultConnectionTimeout)
                .setSocketTimeout(defaultSoTimeout).build();
        
		if (httpClient == null) {
            synchronized (syncLock) {
                if (httpClient == null) {
                    httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
                }
            }
        }

		// 设置字符编码
		String charset = request.getCharset() != null ? request.getCharset() : DEFAULT_CHARSET;

		HttpRequestBase method = null;
		HttpResponse response = new HttpResponse();
		CloseableHttpResponse httpResponse = null;

		try {
			if (methodType == Method.POST) {
				method = new HttpPost(request.getUrl());
				if (request.getParameters() != null) {
					StringBuilder sb = new StringBuilder();
					for (String key : request.getParameters().keySet()) {
						sb.append(key).append("=").append(request.getParameters().get(key)).append("&");
					}
					StringEntity entity = new StringEntity(sb.substring(0, sb.length() - 1));
					((HttpPost) method).setEntity(entity);
				}
			} else if (methodType == Method.GET) {
				if (request.getParameters() != null && request.getParameters().size() > 0) {
					StringBuilder sb = new StringBuilder(request.getUrl()).append("?");
					for (String key : request.getParameters().keySet()) {
						sb.append(key).append("=").append(request.getParameters().get(key)).append("&");
					}
					method = new HttpGet(sb.substring(0, sb.length() - 1));
				} else {
					method = new HttpGet(request.getUrl());
				}
			} else if (methodType == Method.STREAM) {
				method = new HttpPost(request.getUrl());
				byte[] data = request.getParameter().getBytes();
				ByteArrayInputStream bais = new ByteArrayInputStream(data);

				((HttpPost) method).setEntity(new InputStreamEntity(bais, data.length));
			}
			
			// 设置Http Header中的User-Agent属性
			method.addHeader("User-Agent", "Mozilla/5.0");
			method.addHeader("Content-Type", "application/x-www-form-urlencoded; text/html; charset=" + charset);
			method.setConfig(requestConfig);

			httpResponse = httpClient.execute(method, HttpClientContext.create());
			HttpEntity entity = httpResponse.getEntity();

			response.setStringResult(EntityUtils.toString(entity, "utf-8"));
			response.setResponseHeaders(method.getAllHeaders());
			EntityUtils.consume(entity);
		} catch (Exception ex) {
			LOG.error("## 知了企服收银台回调异常", ex);
			throw ex;
		} finally {
			method.releaseConnection();
			if (httpResponse != null)
				httpResponse.close();
		}
		return response;
	}

	public static void main(String[] args) throws Exception {
		HttpRequest r = new HttpRequest("http://192.168.1.221:10505/trans/order/transOrderQuery.html");
		HttpResponse resp = new ApacheHttpHandler().get(r);
		System.out.println(resp.getStringResult());

	}
}
