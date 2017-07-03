package com.snowcattle.game.tps.rpc;

import com.snowcattle.game.service.rpc.client.RpcContextHolder;
import com.snowcattle.game.service.rpc.client.RpcContextHolderObject;
import com.snowcattle.game.service.rpc.client.RpcProxyService;
import com.snowcattle.game.service.rpc.service.client.HelloService;
import com.snowcattle.game.common.enums.BOEnum;
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

    private AtomicLong privateAtomicLong;

    public RpcTpsRunable(RpcProxyService rpcProxyService, AtomicLong atomicLong, int maxSize, CountDownLatch countDownLatch) {
        this.rpcProxyService = rpcProxyService;
        this.atomicLong = atomicLong;
        this.maxSize = maxSize;
        this.countDownLatch = countDownLatch;
        privateAtomicLong = new AtomicLong();
    }

    @Override
    public void run() {

        try {
//            HelloService helloService = rpcProxyService.createRemoteProxy(HelloService.class);
            HelloService helloService = rpcProxyService.createProxy(HelloService.class);
            int serverId = 9001;
            RpcContextHolderObject rpcContextHolderObject = new RpcContextHolderObject(BOEnum.WORLD, serverId);
            RpcContextHolder.setContextHolder(rpcContextHolderObject);
            long startTime = System.currentTimeMillis();
            for(int i = 0; i < maxSize; i++){

                String result = helloService.hello(String.valueOf(i));
                Assert.assertEquals("Hello! " + String.valueOf(i), result);
                atomicLong.getAndIncrement();
                privateAtomicLong.getAndIncrement();
//                System.out.println(atomicLong.get());
            }
            long endTime = System.currentTimeMillis();
            long useTime = endTime - startTime;
//            System.out.println("rpc 总数量" + atomicLong.get() + "时间" + useTime);
//            System.out.println("rpc 私有数量" + privateAtomicLong.get() + "时间" + useTime);
        }catch (Throwable e){
            e.printStackTrace();
        }

        this.countDownLatch.countDown();
    }
}
