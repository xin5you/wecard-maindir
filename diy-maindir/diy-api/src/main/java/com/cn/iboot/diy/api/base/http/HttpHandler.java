package com.cn.iboot.diy.api.base.http;

public interface HttpHandler {

	HttpResponse post(HttpRequest request) throws Exception;

	HttpResponse get(HttpRequest request) throws Exception;

	HttpResponse stream(HttpRequest request) throws Exception;

}
