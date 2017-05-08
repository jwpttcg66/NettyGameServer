package com.snowcattle.game.common.exception;

/**
 * 当服务处理失败的时候抛出该异常
 */
public class IServerServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IServerServiceException(String name){
		super(name);
	}
	public IServerServiceException(String name,Throwable t){
		super(name,t);
	}
}