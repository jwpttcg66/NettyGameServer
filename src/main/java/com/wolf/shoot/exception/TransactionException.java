package com.wolf.shoot.exception;

public class TransactionException extends RuntimeException {
	
	public TransactionException(){
		super();
	}
	
	public TransactionException(String msg){
		super(msg);
	}
}

