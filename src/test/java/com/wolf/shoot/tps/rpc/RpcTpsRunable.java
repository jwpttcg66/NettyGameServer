package com.wolf.shoot.tps.rpc;

import com.wolf.shoot.common.constant.BOEnum;
import com.wolf.shoot.service.rpc.client.RpcContextHolder;
import com.wolf.shoot.service.rpc.client.RpcContextHolderObject;
import com.wolf.shoot.service.rpc.client.RpcProxyService;
import com.wolf.shoot.service.rpc.service.client.HelloService;
import org.junit.Assert;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by jiangwenping on 17/4/19.
 */
public class RpcTpsRunable implements Runnable{

    private RpcProxyService rpcProxyService;

    private AtomicLong atomicLong;
    private int maxSize;

    private CountDownLatch countDownLatch;
    public RpcTpsRunable(RpcProxyService rpcProxyService, AtomicLong atomicLong, int maxSize, CountDownLatch countDownLatch) {
        this.rpcProxyService = rpcProxyService;
        this.atomicLong = atomicLong;
        this.maxSize = maxSize;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {

        HelloService helloService = rpcProxyService.createProxy(HelloService.class);
        int serverId = 9001;
        RpcContextHolderObject rpcContextHolderObject = new RpcContextHolderObject(BOEnum.WORLD, serverId);
        RpcContextHolder.setContextHolder(rpcContextHolderObject);

        long startTime = System.currentTimeMillis();
        for(int i = 0; i < maxSize; i++){
            String result = helloService.hello("World");
//            System.out.println(result);
            Assert.assertEquals("Hello! World", result);
            atomicLong.getAndIncrement();
        }
        long endTime = System.currentTimeMillis();
        long useTime = endTime - startTime;
        System.out.println("rpc 数量" + atomicLong.get() + "时间" + useTime);
        this.countDownLatch.countDown();
    }
}
