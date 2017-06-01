package com.snowcattle.game.common.exception;

/**
 * Created by jwp on 2017/2/9.
 * 网络消息发送异常
 */
public class NetMessageException extends Exception {
    private static final long serialVersionUID = 1L;

    public NetMessageException(String name){
        super(name);
    }
    public NetMessageException(String name, Throwable t){
        super(name,t);
    }
}

