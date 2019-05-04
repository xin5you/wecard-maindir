package com.cn.thinkx.integrationpay.base.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
	
	private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

	/**
	 * @param param 请求参数
	 * @param httpUrl 请求地址
	 * @return
	 */
	public static String httpUrlConnection(String param,String httpUrl){
		InputStream is = null;
		HttpURLConnection httpUrlConnection = null;
		try {
			URL url = new URL(httpUrl);// 测试环境

			URLConnection urlConnection = url.openConnection();
			httpUrlConnection = (HttpURLConnection) urlConnection;
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setDoInput(true);
			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setRequestMethod("POST");
			httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			httpUrlConnection.setRequestProperty("Charset", "UTF-8");
			httpUrlConnection.connect();

			DataOutputStream dos = new DataOutputStream(httpUrlConnection.getOutputStream());
			dos.writeBytes(param);
			dos.flush();
			dos.close();

			int resultCode = httpUrlConnection.getResponseCode();
			if (HttpURLConnection.HTTP_OK == resultCode) {
				StringBuffer sb1 = new StringBuffer();
				String readLine = new String();
				BufferedReader responseReader = new BufferedReader(
						new InputStreamReader(httpUrlConnection.getInputStream()));
				while ((readLine = responseReader.readLine()) != null) {
					sb1.append(readLine).append("\n");
				}
				responseReader.close();
				return sb1.toString();
			}
		} catch (Exception e) {
			log.error("Send ShenXinPay throws Exception", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error("Send ShenXinPay throws Exception", e);
				}
			}
			if (httpUrlConnection != null) {
				httpUrlConnection.disconnect();
			}
		}
		return httpUrl;
	}
	
	public static String refundHttpUrlConnection(String param,String httpUrl){
		InputStream is = null;
		HttpURLConnection httpUrlConnection = null;
		try {
			URL url = new URL(httpUrl);// 测试环境

			URLConnection urlConnection = url.openConnection();
			httpUrlConnection = (HttpURLConnection) urlConnection;
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setDoInput(true);
			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setRequestMethod("GET");
			httpUrlConnection.setRequestProperty("Content-Type", "application/json");
			httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			httpUrlConnection.setRequestProperty("Charset", "UTF-8");
			httpUrlConnection.connect();
			DataOutputStream dos = new DataOutputStream(httpUrlConnection.getOutputStream());
			dos.writeBytes(param);
			dos.flush();
			dos.close();

			int resultCode = httpUrlConnection.getResponseCode();
			if (HttpURLConnection.HTTP_OK == resultCode) {
				StringBuffer sb1 = new StringBuffer();
				String readLine = new String();
				BufferedReader responseReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
				while ((readLine = responseReader.readLine()) != null) {
					sb1.append(readLine).append("\n");
				}
				responseReader.close();
				return sb1.toString();
			}
		} catch (Exception e) {
			log.error("Send ShenXinPay throws Exception", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error("Send ShenXinPay throws Exception", e);
				}
			}
			if (httpUrlConnection != null) {
				httpUrlConnection.disconnect();
			}
		}
		return httpUrl;
	}
}
