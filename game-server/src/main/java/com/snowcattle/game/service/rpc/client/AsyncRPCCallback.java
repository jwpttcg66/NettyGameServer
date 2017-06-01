package com.snowcattle.game.service.rpc.client;

/**
 */
public interface AsyncRPCCallback {

    void success(Object result);

    void fail(Exception e);

}
