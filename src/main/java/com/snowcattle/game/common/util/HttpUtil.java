package com.snowcattle.game.common.util;

import com.snowcattle.game.common.constant.CommonErrorLogInfo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Http请求工具
 * 
 * 
 */
public class HttpUtil {
	private static final Logger logger = LoggerFactory.getLogger("HttpUtil");

	private static final int DEFAULT_TIMEOUT = 5;

	private static final String CHARSET = "charset=";
	/** 连接超时,默认5秒 */
	private static final int DEFAULT_CONNECT_TIMEOUT = 5000;
	/** 读取超时,默认5秒 */
	private static final int DEFAULT_READ_TIMEOUT = 5000;
	/** 连接local的参数编码 */
	private static final String DEFAULT_ENCODE_TYPE = "utf-8";

	/**
	 * 按照utf-8的编码格式进行编码
	 * 
	 * @param param
	 * @return
	 */
	public static String encode(String param) {
		try {
			return URLEncoder.encode(param, DEFAULT_ENCODE_TYPE);
		} catch (IOException e) {
			if (logger.isErrorEnabled()) {
				logger.error(
						ErrorsUtil.error(CommonErrorLogInfo.ARG_INVALID, "#Core.HttpUtil.encode",
								String.format("String:%s endcode to type:%s exception", param, DEFAULT_ENCODE_TYPE)), e);
			}

			// 出异常了返回自身
			return param;
		}
	}
	
	public static String decode(String param, String defaultVal) {
		try {
			return URLDecoder.decode(param, DEFAULT_ENCODE_TYPE);
		} catch (IOException e) {
			if (logger.isErrorEnabled()) {
				logger.error(
						ErrorsUtil.error(CommonErrorLogInfo.ARG_INVALID, "#Core.HttpUtil.decode",
								String.format("String:%s decode to type:%s exception", param, DEFAULT_ENCODE_TYPE)), e);
			}

			// 出异常了返回给的默认值
			return defaultVal;
		}
	}

	/**
	 * 获取指定地址的内容,如果能够从URLConnection中可以解析出编码则使用解析出的编码;否则就使用GBK编码
	 * 
	 * @param requestUrl
	 * @return
	 * @throws IOException
	 */
	public static String getUrl(String requestUrl) throws IOException {
		final long _begin = System.nanoTime();
		BufferedReader reader = null;
		HttpURLConnection urlConnection = null;
		try {
			InputStream urlStream;
			URL url = new URL(requestUrl);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
			urlConnection.setReadTimeout(DEFAULT_READ_TIMEOUT);
			urlConnection.connect();
			urlStream = urlConnection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(urlStream, parseEncoding(urlConnection)));
			char[] _buff = new char[128];
			StringBuilder temp = new StringBuilder();
			int _len = -1;
			while ((_len = reader.read(_buff)) != -1) {
				temp.append(_buff, 0, _len);
			}
			// 调用接口处理成功采集
//			PIProbeCollector.collect(ProbeName.RPC, ProcessResult.SUCCESS, (System.nanoTime() - _begin) / 1000000);
			return temp.toString();
		} catch (IOException e) {
			// 调用接口处理失败采集
//			PIProbeCollector.collect(ProbeName.RPC, ProcessResult.FAIL, (System.nanoTime() - _begin) / 1000000);
			throw e;
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				urlConnection.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 对url的参数进行编码，并返回编码后的url
	 * 
	 * @param requestParmUrl
	 *            带参数的url请求
	 * @param params
	 *            ulr中的参数
	 * @return
	 */
	public static String encodeUrl(String requestParmUrl, Object... params) {
		// 对所有的字符类型参数进行编码
		for (int i = 0; i < params.length; i++) {
			Object _o = params[i];
			if (_o != null && _o instanceof String) {
				params[i] = encode((String) params[i]);
			}
		}
		return String.format(requestParmUrl, params);
	}

	/**
	 * 带参数的url请求, 会先对URL中的参数进行编码
	 * 
	 * @param requestParmUrl
	 *            请求的url
	 * @param params
	 *            请求的参数
	 * @return 返回的结果
	 * @throws IOException
	 */
	public static String getUrl(String requestParmUrl, Object... params) throws IOException {
		String _url = encodeUrl(requestParmUrl, params);
		return getUrl(_url);
	}

	/**
	 * 尝试解析Http请求的编码格式,如果没有解析到则使用GBK编码(主要考虑到Local平台的返回编码是gb2312的)
	 * 
	 * @param urlConnection
	 * @return
	 */
	static String parseEncoding(HttpURLConnection urlConnection) {
		String _encoding = urlConnection.getContentEncoding();
		if (_encoding != null) {
			return _encoding;
		}
		String _contentType = urlConnection.getContentType();
		if (_contentType != null) {
			int _index = _contentType.toLowerCase().indexOf(CHARSET);
			if (_index > 0) {
				_encoding = _contentType.substring(_index + CHARSET.length());
			}
		}
		if (_encoding != null) {
			return _encoding;
		} else {
			return DEFAULT_ENCODE_TYPE;
		}
	}

	/**
	 * 访问
	 * 
	 * @param url
	 * @return
	 */
	public static String doPost(String url) {
		StringBuffer stringBuffer = new StringBuffer();
		HttpEntity entity = null;
		BufferedReader in = null;
		HttpResponse response = null;
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpParams params = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, DEFAULT_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, DEFAULT_TIMEOUT);
			HttpPost httppost = new HttpPost(url);
			httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");

			response = httpclient.execute(httppost);
			entity = response.getEntity();
			in = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			String ln;
			while ((ln = in.readLine()) != null) {
				logger.info("URL:" + url);
				logger.info("UC_LOGIN[result][oriental]" + ln);
				String newLn = new String(ln.getBytes("ISO-8859-1"), "UTF-8");
				stringBuffer.append(ln);
				logger.info("UC_LOGIN[result][newLn]" + newLn);
			}
			httpclient.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return stringBuffer.toString();
	}
	
//	public static void main(String args[]) {
//		String param = "domain=s4.app12345.qqopenapp.com数量的开发建设力度姐夫";
//		String b = encode(param);
//		String b1 = encode(b);
//		System.out.println(b);
//		System.out.println(b1);
//		String c = decode(b1, null);
//		System.out.println(c);
//		String c1 = decode(c, null);
//		System.out.println(c1);
//	}
}
