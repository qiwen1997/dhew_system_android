package com.example.util;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
public class HttpUtil {
	//服务器端根目录
	public static final String BASE_URL = "http://47.95.212.214:8080/Dhew_System_Server/";
	//本地服务器目录
	//public static final String BASE_URL="http://47.95.212.214:8080/CarRent_Server/";
	//public static final String BASE_URL = "http://localhost:8080/Dhew_System_Server/";
	//public static final String BASE_URL = "http://192.168.56.1:8080/Dhew_System_Server/";
	/**
	 * 获得Get请求
	 * @param url
	 * @return
	 */
	public static HttpGet getHttpGet(String url) {
		HttpGet request = new HttpGet(url);
		return request;
	}
	/**
	 * 获得Post请求
	 * @param url
	 * @return
	 */
	public static HttpPost getHttpPost(String url) {
		HttpPost request = new HttpPost(url);
		return request;
	}
	/**
	 * 获得Get响应
	 * @param request
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static HttpResponse getHttpResponse(HttpGet request) throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	/**
	 * 获得Post响应
	 * @param request
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static HttpResponse getHttpResponse(HttpPost request) throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	/**
	 * 传入Post请求字符地址，返回字符串响应
	 * @param url
	 * @return
	 */
	public static String queryStringForPost(String url) {
		HttpPost request = HttpUtil.getHttpPost(url);
		request.addHeader("Content-Type", "text/html");
		request.addHeader("charset", "utf-8");
		String result = null;
		try {
			HttpResponse response = HttpUtil.getHttpResponse(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(),"UTF-8");
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "";
			return result;
		}
		return null;
	}
	/**
	 * 传入Post请求，返回字符串响应
	 * @param request
	 * @return
	 */
	public static String queryStringForPost(HttpPost request) {
		String result = null;
		try {
			HttpResponse response = HttpUtil.getHttpResponse(request);
			if (response.getStatusLine().getStatusCode() == 200) { //Http请求成功
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "";
			return result;
		}
		return null;
	}
	/**
	 * 传入Get请求字符地址，返回字符串响应
	 * @param url
	 * @return
	 */
	public static String queryStringForGet(String url) {
		HttpGet request = HttpUtil.getHttpGet(url);
		request.addHeader("Content-Type", "text/html");
		request.addHeader("charset", "utf-8");
		String result = null;
		try {
			HttpResponse response = HttpUtil.getHttpResponse(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "";
			return result;
		}
		return null;
	}
}
