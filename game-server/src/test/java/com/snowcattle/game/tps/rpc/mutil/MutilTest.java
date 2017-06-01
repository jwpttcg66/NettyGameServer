package com.snowcattle.game.tps.rpc.mutil;

import com.snowcattle.game.TestStartUp;
import com.snowcattle.game.common.ThreadNameFactory;
import com.snowcattle.game.common.util.BeanUtil;
import com.snowcattle.game.service.rpc.client.RpcProxyService;
import com.snowcattle.game.tps.rpc.RpcTpsRunable;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by jiangwenping on 17/4/19.
 */
public class MutilTest {

    private RpcProxyService rpcProxyService;

    public static void main(String[] args) throws InterruptedException {
        MutilTest mutilTest = new MutilTest();
        mutilTest.init();
        mutilTest.tps();
        mutilTest.setTear();
    }
    public void init(){
        TestStartUp.startUpWithSpring();
        rpcProxyService = (RpcProxyService) BeanUtil.getBean("rpcProxyService");
    }

    public void tps() throws InterruptedException {
        AtomicLong atomicLong = new AtomicLong();
        int size = 1000;
        int threadSize = 50;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        ThreadNameFactory threadNameFactory = new ThreadNameFactory("tps");
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(threadSize, threadSize, 600L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536), threadNameFactory);
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < threadSize;i++) {
            RpcTpsRunable rpcTpsRunable = new RpcTpsRunable(rpcProxyService, atomicLong, size,countDownLatch);
            executorService.execute(rpcTpsRunable);
        }
        countDownLatch.await();;
        long endTime = System.currentTimeMillis();
        long useTime = endTime - startTime;
        System.out.println("rpc 总数量" + atomicLong.get() + "时间" + useTime);
    }

    public void setTear(){
        if (rpcProxyService != null) {
            try {
                rpcProxyService.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}