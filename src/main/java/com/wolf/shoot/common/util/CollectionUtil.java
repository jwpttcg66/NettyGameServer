package com.wolf.shoot.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 集合相关的工具类
 *
  *
 *
 */
public class CollectionUtil {
	/**
	 * 构建泛型类型的HashMap,该Map的初始容量是0
	 *
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> Map<K, V> buildHashMap() {
		return new HashMap<K, V>(0);
	}
}
