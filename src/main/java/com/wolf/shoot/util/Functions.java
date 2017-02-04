package com.wolf.shoot.util;

/**
 * 保存所有函数接口的定义.
 *
 *
 */
public final class Functions {

	/**
	 * 函数接口.使用此接口可实现只有一个参数且无返回值的匿名函数
	 *
	 * @param <T> 参数类型
	 *
	 *
	 */
	public interface Function<T> {
		/**
		 * 调用该函数
		 *
		 * @param param
		 */
		public void apply(T param);
	}

	/**
	 * 函数接口.使用此接口可实现有两个参数且无返回值的匿名函数
	 *
	 * @param <T> 参数类型
	 *
	 */
	public interface Function2<T1, T2> {
		/**
		 * 调用该函数
		 *
		 * @param param
		 */
		public void apply(T1 param1, T2 param2);
	}
}
