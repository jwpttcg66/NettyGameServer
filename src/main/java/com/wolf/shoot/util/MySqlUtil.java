package com.wolf.shoot.util;

import java.io.UnsupportedEncodingException;

/**
 * @author dongyong.wang
 *
 */
public class MySqlUtil {
	/**
	 * 使用解码的方式检查指定的字符串中是有效的Mysql支持的UTF-8编码
	 *
	 * @param str
	 * @return true,有效的;false,无效的
	 * @throws UnsupportedEncodingException
	 */
	public static boolean isValidMySqlUTF8(String str) {
		byte[] strBytes;
		try {
			strBytes = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		final int length = strBytes.length;
		for (int i = 0; i < length;) {
			final int c = 0xFF & strBytes[i];
			do {
				if (c < 0x80) {
					i++;
					break;
				}
				if (c < 0xc2) {
					return false;
				}
				if (c < 0xe0) {
					final int c1Index = i + 1;
					if (c1Index >= length) {
						return false;
					}
					final int c1 = 0xFF & strBytes[c1Index];
					if (!((c1 ^ 0x80) < 0x40)) {
						return false;
					}
					i += 2;
					break;
				}
				if (c < 0xf0) {
					final int c1Index = i + 1;
					final int c2Index = c1Index + 1;
					if (c2Index >= length) {
						return false;
					}
					final int c1 = 0xFF & strBytes[c1Index];
					final int c2 = 0xFF & strBytes[c2Index];
					if (!((c1 ^ 0x80) < 0x40 && (c2 ^ 0x80) < 0x40 && (c >= 0xe1 || c1 >= 0xa0))) {
						return false;
					}
					i += 3;
					break;
				}
				return false;
			} while (false);
		}
		return true;
	}

	/**
	 * 检查指定的字符串中是有效的Mysql支持的UTF-8编码,该检测直接比较每个code point是否在0X0000~0XFFFF中(Mysql
	 * utf8能够有效支持的UNICODE编码范围0X000~0XFFFF)
	 *
	 * @param str
	 * @return
	 */
	public static boolean isValidMySqlUTF8Fast(String str) {
		final int length = str.length();
		for (int i = 0; i < length; i++) {
			int c = str.codePointAt(i);
			if (c < 0X0000 || c > 0Xffff) {
				return false;
			}
		}
		return true;
	}
}