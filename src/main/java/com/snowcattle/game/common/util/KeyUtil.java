package com.snowcattle.game.common.util;

import java.util.UUID;

/**
 * 产生各种key的工具类
 *
  *
 *
 */
public class KeyUtil {
	/** Key的长度 */
	public static final int KEY_LEN = 32;

	/**
	 * 产生一个UUID的Key
	 *
	 * @return
	 */
	public static String UUIDKey() {
		return toShortKey(genUUIDKey());
	}

	/**
	 * 生成UUID字符串
	 *
	 * @return
	 */
	static String genUUIDKey() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 去掉UUID的字符串中的"-"符号,以取得较短的key值
	 *
	 * @param key
	 * @return
	 */
	static String toShortKey(String key) {
		return key.substring(0, 8) + key.substring(9, 13) + key.substring(14, 18) + key.subSequence(19, 23)
				+ key.substring(24);
	}
}
