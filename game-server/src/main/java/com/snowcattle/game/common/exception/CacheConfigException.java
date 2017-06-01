package com.snowcattle.game.common.exception;

/**
 * 初始化cache
 *
 */
public class CacheConfigException extends ConfigException {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param cacheName cache名称
	 * @param errorInfo 错误
	 */
	public CacheConfigException(String cacheName,
			String errorInfo) {
		super(String.format("[%s]%s", cacheName , errorInfo));
	}
}
