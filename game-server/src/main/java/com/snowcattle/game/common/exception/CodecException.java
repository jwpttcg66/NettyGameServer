package com.snowcattle.game.common.exception;

/**
 * Created by jiangwenping on 17/2/8.
 */
public class CodecException  extends Exception {
    private static final long serialVersionUID = 1L;

    public CodecException(String name){
        super(name);
    }
    public CodecException(String name,Throwable t){
        super(name,t);
    }
}
