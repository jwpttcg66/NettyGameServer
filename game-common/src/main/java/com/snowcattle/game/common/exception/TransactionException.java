package com.snowcattle.game.common.exception;

public class TransactionException extends RuntimeException {

    private static final long serialVersionUID = -7019206205634720119L;

    public TransactionException(){
		super();
	}
	
	public TransactionException(String msg){
		super(msg);
	}
}

