package com.snowcattle.game.common.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * 资源工具类
 *
 *
 */
public class ResourceUtil {
	/**
	 * 在类路径中查找指定的资源
	 *
	 * @param resource
	 * @return
	 */
	public static URL getResourceURL(String resource) {
		ClassLoader _classLoader = Thread.currentThread().getContextClassLoader();
		return _classLoader.getResource(resource);
	}

	/**
	 * 取得指定的URL中文本内容
	 *
	 * @param url
	 * @param charset
	 * @return
	 */
	public static String getTextFormURL(final URL url, final String charset) {
		Reader _r = null;
		String _text = null;
		try {
			_r = new InputStreamReader(url.openStream(), charset != null ? charset : "UTF-8");
			_text = IOUtils.toString(_r);
		} catch (IOException e) {
			throw new IllegalStateException("Can't load config from url [" + url + "]");
		} finally {
			IOUtils.closeQuietly(_r);
		}
		return _text;
	}

	/**
	 * 取得指定resource中文本内容
	 *
	 * @param resource
	 * @param charset
	 * @return
	 */
	public static String getTextFormResource(String resource, String charset) {
		URL _url = getResourceURL(resource);
		if (_url == null) {
			throw new IllegalArgumentException("Can't load config from resource [" + resource + "]");
		}
		return getTextFormURL(_url, charset);
	}

	/**
	 * 取得指定resource中文本内容 默认utf8
	 *
	 * @param resource
	 * @return
	 */
	public static String getTextFormResource(String resource) {
		URL _url = getResourceURL(resource);
		if (_url == null) {
			throw new IllegalArgumentException("Can't load config from resource [" + resource + "]");
		}
		return getTextFormURL(_url, null);
	}

	/**
	 * 取得指定resource中文本内容 默认utf8
	 *
	 * @param resource
	 * @return
	 */
	public static String getTextFormResourceNoException(String resource) {
		URL _url = getResourceURL(resource);
		if (_url == null) {
			return null;
		}
		return getTextFormURL(_url, null);
	}
}
