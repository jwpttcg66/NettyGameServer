package com.snowcattle.game.common.exception;

/**
 * Created by jiangwenping on 17/3/13.
 */
public class StartUpException extends Exception{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public StartUpException(String name){
        super(name);
    }
    public StartUpException(String name,Throwable t){
        super(name,t);
    }
}
