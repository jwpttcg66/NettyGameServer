package com.snowcattle.game.common.exception;

public class TransactionException extends RuntimeException {
	
	public TransactionException(){
		super();
	}
	
	public TransactionException(String msg){
		super(msg);
	}
}

