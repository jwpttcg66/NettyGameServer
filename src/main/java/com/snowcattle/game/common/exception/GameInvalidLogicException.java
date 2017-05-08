package com.snowcattle.game.common.exception;


/**
 * 不合法的逻辑异常，用于程序的逻辑判断时处理不应该发生的情况。
 * 当非法情况发生时，应该可以清晰的记录异常的详细信息，所以构造函数必须提供详细的异常描述。
 */
public final class GameInvalidLogicException  extends Exception {

    /**  */
    private static final long serialVersionUID = 552982663377276317L;

    /**
     * 构造一个不合法的逻辑异常。
     * @param message 异常的详细描述。
     */
    public GameInvalidLogicException(String message) {
        super(message);
    }

}
