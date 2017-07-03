package com.snowcattle.game.net.client.rpc;


import com.snowcattle.game.TestStartUp;
import com.snowcattle.game.service.rpc.client.RpcContextHolder;
import com.snowcattle.game.service.rpc.client.RpcContextHolderObject;
import com.snowcattle.game.service.rpc.client.RpcProxyService;
import com.snowcattle.game.common.enums.BOEnum;
import com.snowcattle.game.common.util.BeanUtil;
import com.snowcattle.game.service.rpc.service.client.HelloService;
import org.junit.Assert;


/**
 * Created by jwp on 2017/3/8.
 */
public class HelloServiceTest {

    private RpcProxyService rpcProxyService;

    public static void main(String[] args) {
        HelloServiceTest helloServiceTest = new HelloServiceTest();
        helloServiceTest.init();
        helloServiceTest.helloTest1();
        helloServiceTest.setTear();
    }
    public void init(){
        TestStartUp.startUpWithSpring();
        rpcProxyService = (RpcProxyService) BeanUtil.getBean("rpcProxyService");
    }

    public void helloTest1() {
        HelloService helloService = rpcProxyService.createProxy(HelloService.class);
//        HelloService helloService = rpcProxyService.createRemoteProxy(HelloService.class);
        int serverId = 8001;
        RpcContextHolderObject rpcContextHolderObject = new RpcContextHolderObject(BOEnum.WORLD, serverId);
        RpcContextHolder.setContextHolder(rpcContextHolderObject);
        String result = helloService.hello("World");
        System.out.println(result);
        Assert.assertEquals("Hello! World", result);
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
