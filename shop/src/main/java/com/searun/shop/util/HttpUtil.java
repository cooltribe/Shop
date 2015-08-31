package com.searun.shop.util;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpUtil {
	// http://192.168.2.36:8080/shopxx
//	public static final String IMG_PATH = "http://221.226.22.82:6666/shopxx";
//	private static final String BASE_URL = "http://221.226.22.82:6666/shopxx/apps/";
//	public static final String RETURN_URL = "http://221.226.22.82:6666/shopxx";

	//王
//	 public static final String IMG_PATH = "http://192.168.2.36:8877/shopxx";
//	 private static final String BASE_URL = "http://192.168.2.36:8877/shopxx/apps/";
//	 public static final String RETURN_URL = "http://192.168.2.36:8877/shopxx";
	 public static  String BASE_URL = "http://www.51egoods.com";
	public static  String PRE_URL = "http://221.226.22.84";

	public static void setBaseUrl(String baseUrl) {
		BASE_URL = baseUrl;
	}

	private static AsyncHttpClient client = new AsyncHttpClient();

	static {
		client.setTimeout(30000);
	}

	public static void get(String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler) {
		client.get(getAbsoluteUrl(paramString), paramAsyncHttpResponseHandler);
	}

	public static void get(String paramString, BinaryHttpResponseHandler paramBinaryHttpResponseHandler) {
		client.get(getAbsoluteUrl(paramString), paramBinaryHttpResponseHandler);
	}

	public static void get(String paramString, JsonHttpResponseHandler paramJsonHttpResponseHandler) {
		client.get(getAbsoluteUrl(paramString), paramJsonHttpResponseHandler);
	}

	public static void get(String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler) {
		client.get(getAbsoluteUrl(paramString), paramRequestParams, paramAsyncHttpResponseHandler);
	}

	public static void get(String paramString, RequestParams paramRequestParams, JsonHttpResponseHandler paramJsonHttpResponseHandler) {
		client.get(getAbsoluteUrl(paramString), paramRequestParams, paramJsonHttpResponseHandler);
	}

	private static String getAbsoluteUrl(String paramString) {
//		Log.i("uuuuuuuuu", BASE_URL);
		System.out.print("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" + BASE_URL);
		return BASE_URL +"/apps/" + paramString;
	}

	public static AsyncHttpClient getClient() {
		return client;
	}

	private static HttpClient mHttpClient;

	public static synchronized HttpClient getHttpClient() {
		try {
			if (mHttpClient == null) {
				// 创建http连接参数
				HttpParams params = new BasicHttpParams();
				// 设置超时
				// 1.设置连接池超时
				ConnManagerParams.setTimeout(params, 5000);
				// 2.设置连接超时
				HttpConnectionParams.setConnectionTimeout(params, 5000);
				// 3.设置请求超时
				HttpConnectionParams.setSoTimeout(params, 10000);

				// 设置client支持http及https两种格式
				SchemeRegistry schemeRegistry = new SchemeRegistry();
				// 一，支持何种协议，二，socket连接的工厂，也就是协议路径创建时所用的协议工厂，三，端口号
				schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
				schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

				ClientConnectionManager manager = new ThreadSafeClientConnManager(params, schemeRegistry);
				// 一，多线程连接管理者，二，连接参数
				mHttpClient = new DefaultHttpClient(manager, params);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mHttpClient;
	}

	public static void post(String paramString, RequestParams paramRequestParams, JsonHttpResponseHandler paramJsonHttpResponseHandler) {
		Log.i("hah啊哈哈哈哈   哈哈哈",getAbsoluteUrl(paramString));
		client.post(getAbsoluteUrl(paramString), paramRequestParams, paramJsonHttpResponseHandler);
	}
}
