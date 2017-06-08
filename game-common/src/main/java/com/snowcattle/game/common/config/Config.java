package com.snowcattle.game.common.config;

/**
 * 配置接口,用于提供系统配置内容
 *
 *
 */
public interface Config {
	/**
	 * 取得系统配置的版本号,该版本号不代表程序的真实版本号,只是声明的版本号
	 */
	public abstract String getVersion();

	/**
	 * 校验配置参数是否符合有效
	 *
	 * @exception IllegalArgumentException
	 *                如果有参数配置错误,会抛出此异常
	 */
	public abstract void validate();

	/**
	 * 是否是调试模式
	 *
	 * @return
	 */
	public boolean getIsDebug();

}