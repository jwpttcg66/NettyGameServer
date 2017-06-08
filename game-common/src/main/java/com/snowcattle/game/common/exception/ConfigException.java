package com.snowcattle.game.common.exception;

/**
 * 适用于读取配置文件时产生的异常
 *
 */
public class ConfigException extends RuntimeException {
	/** */
	private static final long serialVersionUID = 1L;

	public ConfigException(String msg) {
		super(msg);
	}

	public ConfigException(Exception e) {
		super(e);
	}

	public ConfigException(String msg, Exception e) {
		super(msg, e);
	}
}
