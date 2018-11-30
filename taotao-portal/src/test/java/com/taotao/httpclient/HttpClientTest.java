package com.taotao.httpclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClientTest {
	@Test
	public void daget() throws Exception{
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet("http://www.baidu.com");
		CloseableHttpResponse response = client.execute(get);
		int statusCode = response.getStatusLine().getStatusCode();
		
		System.out.println(statusCode);
		HttpEntity entity = response.getEntity();
		
		String string = EntityUtils.toString(entity,"utf-8");
		 
		System.out.println(string);
		
		response.close();
		client.close();
	}
	
	//测试一个post请求
	@Test
	public void dopostParam() throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8082/httpclient/post.html");
		//模拟一个表单
		List<NameValuePair> kvList = new ArrayList<>();
		kvList.add(new BasicNameValuePair("user", "zhangsan"));
		kvList.add(new BasicNameValuePair("password", "1234"));
		//包装成entity
		StringEntity entity = new UrlEncodedFormEntity(kvList, "utf-8");
		httpPost.setEntity(entity);
		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity2 = response.getEntity();
		String string = EntityUtils.toString(entity2, "utf-8");
		System.out.println(string);
		response.close();
		httpClient.close();
	}
}
