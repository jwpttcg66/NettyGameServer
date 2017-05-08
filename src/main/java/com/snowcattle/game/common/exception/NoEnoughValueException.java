package com.snowcattle.game.common.exception;

public class NoEnoughValueException extends Exception {

	public NoEnoughValueException() {
	}

	public NoEnoughValueException(String message) {
		super(message);
	}

	public NoEnoughValueException(Throwable cause) {
		super(cause);
	}

	public NoEnoughValueException(String message, Throwable cause) {
		super(message, cause);
	}

}
