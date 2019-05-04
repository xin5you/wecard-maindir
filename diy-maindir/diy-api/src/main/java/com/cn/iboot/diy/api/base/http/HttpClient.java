package com.cn.iboot.diy.api.base.http;

public class HttpClient {
	private static HttpHandler httpClient = new ApacheHttpHandler();

	public static HttpResponse post(HttpRequest request) throws Exception {
		return httpClient.post(request);
	}

	public static HttpResponse get(HttpRequest request) throws Exception {
		return httpClient.get(request);
	}

	public static HttpResponse stream(HttpRequest request) throws Exception {
		return httpClient.stream(request);
	}
	
}
