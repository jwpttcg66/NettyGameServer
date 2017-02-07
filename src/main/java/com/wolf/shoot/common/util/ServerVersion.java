package com.wolf.shoot.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import com.wolf.shoot.common.annotation.NotThreadSafe;
import com.wolf.shoot.common.constant.CommonErrorLogInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 取得服务器的版本号的工具类
 *
  *
 *
 */
public class ServerVersion {
	private static final Logger logger = LoggerFactory.getLogger(ServerVersion.class);

	public static final String MMO_SERVER_VERSION = "MMO-Server-Version";
	/** 版本号的全部长度 */
	private static final int VERSION_LEN = 4;
	/** 主版本号的长度 */
	private static final int MAIN_VERSION_LEN = VERSION_LEN - 1;
	/** 当前服务器运行的版本号 */
	private static String serverVersion = null;

	/**
	 * 在Classpath中的各META-INF/MANIFEST.MF中查找服务器的版本号:
	 * {@value ServerVersion#MMO_SERVER_VERSION}属性
	 *
	 * @return 如果没有找到服务器版本号则会返回null
	 * @exception IllegalStateException
	 *                如果发现有多个MANIFEST.MF中定义了
	 *                {@value ServerVersion#MMO_SERVER_VERSION},则会抛出此异常
	 */
	@NotThreadSafe
	public static String getServerVersion() {
		if (serverVersion == null) {
			String _version = null;
			Enumeration<URL> resources = null;
			try {
				resources = Thread.currentThread().getContextClassLoader().getResources("META-INF/MANIFEST.MF");
				_version = findInManifest(resources, MMO_SERVER_VERSION);
			} catch (IOException e) {
				if (logger.isErrorEnabled()) {
					logger
							.error(ErrorsUtil.error(CommonErrorLogInfo.FILE_IO_FAIL, "#GS.ServerVersion.getServerVersion", ""),
									e);
				}
			}
			if (_version == null) {
				_version = "Unknown";
			}
			serverVersion = _version;
		}
		return serverVersion;
	}

	/**
	 * 判断指定的两个版本号的主版本号部分是否匹配,判断规则是前三组数字是否一致,即主版本号是否匹配 版本号的格式为 x.x.x.x,如0.1.2.3
	 *
	 * @param src
	 * @param dest
	 * @return true,主版本号匹配;false,主版本号不匹配
	 */
	public static boolean isMainVersionMatch(String src, String dest) {
		if (src == null || dest == null) {
			return false;
		}
		String[] _srcParts = src.split("\\.");
		String[] _destParts = dest.split("\\.");
		if (_srcParts.length != VERSION_LEN || _destParts.length != VERSION_LEN) {
			return false;
		}
		for (int i = 0; i < MAIN_VERSION_LEN; i++) {
			if (!_srcParts[i].equals(_destParts[i])) {
				return false;
			}
		}
		return true;
	}

	static String findInManifest(final Enumeration<URL> resources, final String attributeName) {
		String _version = null;
		URL _foundUrl = null;
		while (resources.hasMoreElements()) {
			URL _url = resources.nextElement();
			if (logger.isDebugEnabled()) {
				logger.debug("[#WS.ServerVersion.findInManifest] [find in manifest " + _url + "]");
			}
			InputStream _in = null;
			Manifest _manifest = null;
			try {
				_in = _url.openStream();
				_manifest = new Manifest(_in);
			} catch (Exception ioe) {
				ioe.printStackTrace();
			} finally {
				if (_in != null) {
					try {
						_in.close();
					} catch (Exception e) {
					}
				}
			}
			if (_manifest == null) {
				continue;
			}
			Attributes _mainAttributes = _manifest.getMainAttributes();
			if (_mainAttributes == null) {
				continue;
			}
			String _versionAttr = _mainAttributes.getValue(attributeName);
			if (_versionAttr != null) {
				if (_version == null) {
					_version = _versionAttr;
					_foundUrl = _url;
				} else {
					throw new IllegalStateException("Found a duplicate " + attributeName + " at " + _url + ",which is "
							+ _versionAttr + ".The previous url is " + _foundUrl);
				}
			}
		}
		return _version;
	}
}
