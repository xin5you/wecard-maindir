package com.cn.thinkx.wecard.api.module.withdraw.suning.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientQueryUtil
{
  public static String post(String publicKeyIndex,String signAlgorithm,String merchantNo,String inputCharset,String url, String singnature, String batchNo,String payMerchantNo)
    throws UnsupportedEncodingException
  {
    DefaultHttpClient httpclient = new DefaultHttpClient();

    httpclient.getParams().setParameter("http.socket.timeout", Integer.valueOf(90000));

    httpclient.getParams().setParameter("http.connection.timeout", Integer.valueOf(90000));
    List params = new ArrayList();
    params.add(new BasicNameValuePair("publicKeyIndex", publicKeyIndex));
    params.add(new BasicNameValuePair("signAlgorithm", signAlgorithm));
    params.add(new BasicNameValuePair("merchantNo", merchantNo));
    params.add(new BasicNameValuePair("signature", singnature));
    params.add(new BasicNameValuePair("inputCharset", inputCharset));
    params.add(new BasicNameValuePair("batchNo", batchNo));;
    params.add(new BasicNameValuePair("payMerchantNo", payMerchantNo));
    HttpPost post = new HttpPost(url);
    post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
    String body = invoke(httpclient, post);
    httpclient.getConnectionManager().shutdown();
    return body;
  }

  public static String get(String url) {
    DefaultHttpClient httpclient = new DefaultHttpClient();
    String body = null;

    HttpGet get = new HttpGet(url);
    body = invoke(httpclient, get);
    httpclient.getConnectionManager().shutdown();
    return body;
  }

  private static String invoke(DefaultHttpClient httpclient, HttpUriRequest httpost) {
    HttpResponse response = sendRequest(httpclient, httpost);
    String body = paseResponse(response);
    return body;
  }

  private static String paseResponse(HttpResponse response)
  {
    HttpEntity entity = response.getEntity();

    String charset = EntityUtils.getContentCharSet(entity);

    String body = null;
    try {
      body = EntityUtils.toString(entity);
    }
    catch (ParseException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return body;
  }

  private static HttpResponse sendRequest(DefaultHttpClient httpclient, HttpUriRequest httpost)
  {
    HttpResponse response = null;
    try
    {
      response = httpclient.execute(httpost);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return response;
  }
}