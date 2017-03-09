package com.wolf.shoot.rpc.client;

import com.wolf.shoot.common.util.BeanUtil;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.manager.spring.LocalSpringBeanManager;
import com.wolf.shoot.manager.spring.LocalSpringServiceManager;
import com.wolf.shoot.service.rpc.RpcServiceDiscovery;
import com.wolf.shoot.service.rpc.client.AsyncRPCCallback;
import com.wolf.shoot.service.rpc.client.RPCFuture;
import com.wolf.shoot.service.rpc.client.RpcClient;
import com.wolf.shoot.service.rpc.client.proxy.IAsyncObjectProxy;
import com.wolf.shoot.service.rpc.service.client.HelloService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;

/**
 * Created by jwp on 2017/3/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean/applicationContext-manager.xml")
public class HelloCallbackTest {

    @Autowired
    private RpcClient rpcClient;

    @Before
    public void init(){
        LocalSpringServiceManager localSpringServiceManager = (LocalSpringServiceManager) BeanUtil.getBean("localSpringServiceManager");
        LocalSpringBeanManager localSpringBeanManager = (LocalSpringBeanManager) BeanUtil.getBean("localSpringBeanManager");
        LocalMananger.getInstance().setLocalSpringBeanManager(localSpringBeanManager);
        LocalMananger.getInstance().setLocalSpringServiceManager(localSpringServiceManager);
        try {
            localSpringServiceManager.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        RpcServiceDiscovery rpcServiceDiscovery = localSpringServiceManager.getRpcServiceDiscovery();
        rpcServiceDiscovery.updateOnlineConnectedServer();
    }

    @Test
    public  void test() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        try {
            IAsyncObjectProxy client = rpcClient.createAsync(HelloService.class);
            RPCFuture helloPersonFuture = client.call("hello", "xiaoming");
            helloPersonFuture.addCallback(new AsyncRPCCallback() {
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

    @After
    public void setTear(){
        if(rpcClient != null) {
            rpcClient.stop();
        }
    }
}
