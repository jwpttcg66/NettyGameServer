package com.snowcattle.game.common.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 服务器状态汇报接口
 *
 *
 */
public class ReportServerStatus {

	/**
	 * 服务器运行状态
	 */
	public static enum ServerStatusType {
		/** 初始化状态 */
		STATUS_INIT("INIT"),
		/** 受限访问状态 */
		STATUS_LIMITED("LIMITED"),
		/** 运行状态 */
		STATUS_RUN("RUN"),
		/** 准备停止状态 */
		STATUS_STOPPING("STOPPING"),
		/** 停止状态 */
		STATUS_STOPPED("STOPPED"),
		/** 有错误 */
		STATUS_ERROR("ERROR"),
		/** 服务器过载 */
		STATUS_OVERLOAD("OVERLOAD"),
		;

		/** 服务器状态码 */
		private final String code;

		private ServerStatusType(String code) {
			this.code = code;
		}

		public String getStatusCode() {
			return code;
		}
	}

	/**
	 * 汇报服务器的状态
	 * @param url  汇报的地址
	 * @param domain 服务器的域名
	 * @param servers 服务器的ID,如有多个用","分隔
	 * @param serverStatus 服务器的状态,与servers对应,如有多个用","分隔
	 * @param extra 状态汇报时的额外参数
	 * @return 汇报的结果
	 * @throws IOException
	 */
	public static String report(String url, String domain, String servers, String serverStatus, String extra)
			throws IOException {
		String _stamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		long _nowTime = System.currentTimeMillis();
		return HttpUtil.getUrl(url, _stamp, servers, domain, serverStatus, extra != null ? extra : "",
				_nowTime / 1000);
	}
}
