package com.snowcattle.game.common.util;

/**
 * 断言工具类，用于对方法的传入参数进行校验，如果未通过则
 * 抛出<code>IllegalArgumentException</code>异常
 *
 * @see IllegalArgumentException
 */
public abstract class Assert {
	/**
	 * 断言对象不为空
	 *
	 * @param obj
	 */
	public static void notNull(Object obj) {
		if (obj == null) {
			notNull(obj, null);
		}
	}

	/**
	 * 断言对象不为空
	 *
	 * @param obj
	 */
	public static void notNull(Object obj, String msg) {
		if (obj == null) {
			throw new IllegalArgumentException(msg);
		}
	}

	/**
	 * 断言表达式为真
	 *
	 * @param expression
	 */
	public static void isTrue(boolean expression) {
		if (!expression) {
			isTrue(expression, null);
		}
	}

	/**
	 * 断言表达式为真
	 *
	 * @param expression
	 */
	public static void isTrue(boolean expression, String msg) {
		if (!expression) {
			throw new IllegalArgumentException(msg);
		}
	}
}
