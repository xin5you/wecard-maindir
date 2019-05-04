package com.cn.iboot.diy.api.base.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApacheHttpHandler implements HttpHandler {

	Logger LOG = LoggerFactory.getLogger(ApacheHttpHandler.class);

	private static String DEFAULT_CHARSET = "UTF-8";

	/** 连接超时时间，由bean factory设置，缺省为8秒钟 */
	private static int defaultConnectionTimeout = 30000;

	/** 回应超时时间, 由bean factory设置，缺省为30秒钟 */
	private static int defaultSoTimeout = 30000;

	/** 闲置连接超时时间, 由bean factory设置，缺省为60秒钟 */
	private static int defaultIdleConnTimeout = 60000;

	private static int defaultMaxConnPerHost = 30;

	private static int defaultMaxTotalConn = 80;

	/** 默认等待HttpConnectionManager返回连接超时（只有在达到最大连接数时起作用）：1秒 */
	private static final long defaultHttpConnectionManagerTimeout = 3 * 1000;

	/**
	 * HTTP连接管理器，该连接管理器必须是线程安全的.
	 */
	private static PoolingClientConnectionManager connectionManager;

	/**
	 * 私有的构造方法
	 */
	ApacheHttpHandler() {
		// 创建一个线程安全的HTTP连接池
		connectionManager = new PoolingClientConnectionManager();
		connectionManager.setMaxTotal(defaultMaxTotalConn);
		connectionManager.setDefaultMaxPerRoute(defaultMaxConnPerHost);

		// IdleConnectionTimeoutThread ict = new IdleConnectionTimeoutThread();
		// ict.addConnectionManager(connectionManager);
		// ict.setConnectionTimeout(defaultIdleConnTimeout);
		//
		// ict.start();
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

		// if (null != request.getParameters()) {
		//
		// for (String key : request.getParameters().keySet()) {
		// LOG.debug("{} \t {}", key, request.getParameters().get(key));
		// }
		// }
		DefaultHttpClient httpclient = new DefaultHttpClient(connectionManager);

		// 设置连接超时
		int connectionTimeout = request.getConnectionTimeout() > 0 ? request.getConnectionTimeout()
				: defaultConnectionTimeout;
		httpclient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);

		// 设置回应超时
		int soTimeout = request.getTimeout() > 0 ? request.getTimeout() : defaultSoTimeout;
		httpclient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);

		// 设置等待ConnectionManager释放connection的时间

		// 设置字符编码
		String charset = request.getCharset() != null ? request.getCharset() : DEFAULT_CHARSET;

		HttpRequestBase method = null;
		HttpResponse response = new HttpResponse();

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
			method.addHeader("Content-Type", "application/x-www-form-urlencoded; text/html; charset=" + charset);
			// 设置Http Header中的User-Agent属性
			method.addHeader("User-Agent", "Mozilla/4.0");

			org.apache.http.HttpResponse apacheHttpResponse = httpclient.execute(method);

			HttpEntity entity = apacheHttpResponse.getEntity();

			InputStream inputStream = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset));
			StringBuilder stringBuffer = new StringBuilder();
			String str = "";
			while ((str = br.readLine()) != null) {
				stringBuffer.append(str);
			}
			response.setStringResult(stringBuffer.toString());
			response.setResponseHeaders(method.getAllHeaders());
			EntityUtils.consume(entity);
		} catch (UnsupportedEncodingException ex) {
			throw ex;
		} catch (IOException ex) {
			throw ex;
		} catch (Exception ex) {
			throw ex;
		} finally {
			method.releaseConnection();
		}
		return response;
	}

	public static void main(String[] args) throws Exception {
		HttpRequest r = new HttpRequest("http://www.baidu.com");

		HttpResponse resp = new ApacheHttpHandler().get(r);
		System.out.println(resp.getStringResult());

	}
}
