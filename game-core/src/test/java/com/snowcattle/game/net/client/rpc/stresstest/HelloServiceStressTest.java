package com.snowcattle.game.net.client.rpc.stresstest;


import com.snowcattle.game.TestStartUp;
import com.snowcattle.game.common.enums.BOEnum;
import com.snowcattle.game.common.util.BeanUtil;
import com.snowcattle.game.service.rpc.client.RpcContextHolder;
import com.snowcattle.game.service.rpc.client.RpcContextHolderObject;
import com.snowcattle.game.service.rpc.client.RpcProxyService;
import com.snowcattle.game.service.rpc.service.client.HelloService;
import org.junit.Assert;


/**
 * Created by jwp on 2017/3/8.
 */
public class HelloServiceStressTest {

    private RpcProxyService rpcProxyService;


    public static void main(String[] args) {
        HelloServiceStressTest helloServiceStressTest = new HelloServiceStressTest();
        helloServiceStressTest.init();
        helloServiceStressTest.helloTest1();
        helloServiceStressTest.setTear();
    }
    public void init() {
        TestStartUp.startUpWithSpring();
        rpcProxyService = (RpcProxyService) BeanUtil.getBean("rpcProxyService");
    }

    public void helloTest1() {
        int serverId = 9001;
        HelloService helloService = rpcProxyService.createProxy(HelloService.class);
//        HelloService helloService = rpcProxyService.createRemoteProxy(HelloService.class);
        final String result = "Hello! World";
        final int test_size = 1_000;
        int wrong_size = 0;
        int right_size = 0;
        long current_time = System.currentTimeMillis();

        RpcContextHolderObject rpcContextHolderObject = new RpcContextHolderObject(BOEnum.WORLD, serverId);
        RpcContextHolder.setContextHolder(rpcContextHolderObject);
        for (int i = 0; i < test_size; i++) {
            if(helloService!=null){
                String test = helloService.hello("World");
                if (test != null && result.equals(test))
                    right_size++;
                else
                    wrong_size++;
            }
        }

        ;
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("------------------------- ------------------------- ");

        System.out.println("right_size " + right_size);
        System.out.println("wrong_size " + wrong_size);
        System.out.println("cost time " + (System.currentTimeMillis() - current_time));
        System.out.println("------------------------- ------------------------- ");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        Assert.assertEquals("Hello! World", result);
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
