package com.snowcattle.game.net.client.rpc;

import com.snowcattle.game.service.rpc.client.*;
import com.snowcattle.game.TestStartUp;
import com.snowcattle.game.common.enums.BOEnum;
import com.snowcattle.game.common.util.BeanUtil;
import com.snowcattle.game.service.rpc.client.proxy.AsyncRpcProxy;
import com.snowcattle.game.service.rpc.service.client.HelloService;

import java.util.concurrent.CountDownLatch;

/**
 * Created by jwp on 2017/3/9.
 */
public class HelloCallbackTest {

    private RpcProxyService rpcProxyService;

    public void init() {
        TestStartUp.startUpWithSpring();
        rpcProxyService = (RpcProxyService) BeanUtil.getBean("rpcProxyService");
    }

    public static void main(String[] args) {
        HelloCallbackTest helloCallbackTest = new HelloCallbackTest();
        helloCallbackTest.init();
        helloCallbackTest.test();
        helloCallbackTest.setTear();
    }

    public void test() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        try {
            AsyncRpcProxy proxy = (AsyncRpcProxy) rpcProxyService.createAsync(HelloService.class);
            RpcContextHolderObject rpcContextHolderObject = new RpcContextHolderObject(BOEnum.WORLD, 8001);
            RpcContextHolder.setContextHolder(rpcContextHolderObject);
            RPCFuture rpcFuture = proxy.call("hello", "xiaoming");
            rpcFuture.addCallback(new AsyncRPCCallback() {
                @Override
                public void success(Object result) {
                    System.out.println(result);
                    countDownLatch.countDown();
                }

                @Override
                public void fail(Exception e) {
                    System.out.println(e);
                    countDownLatch.countDown();
                }
            });
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("End");
    }

    public void setTear() {
        if (rpcProxyService != null) {
            try {
                rpcProxyService.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
