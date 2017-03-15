package com.wolf.shoot.rpc.client;


import com.wolf.shoot.common.constant.BOEnum;
import com.wolf.shoot.common.util.BeanUtil;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.manager.spring.LocalSpringBeanManager;
import com.wolf.shoot.manager.spring.LocalSpringServiceManager;
import com.wolf.shoot.service.rpc.RpcContextHolder;
import com.wolf.shoot.service.rpc.RpcContextHolderObject;
import com.wolf.shoot.service.rpc.RpcServiceDiscovery;
import com.wolf.shoot.service.rpc.client.RpcSenderProxy;
import com.wolf.shoot.service.rpc.service.client.HelloService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by jwp on 2017/3/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean/applicationContext-manager.xml")
public class HelloServiceTest {

    @Autowired
    private RpcSenderProxy rpcSenderProxy;

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
        try {
            rpcServiceDiscovery.initWorldConnectedServer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void helloTest1() {
        HelloService helloService = rpcSenderProxy.create(HelloService.class);
        int serverId = 8001;
        RpcContextHolderObject rpcContextHolderObject = new RpcContextHolderObject(BOEnum.WORLD, serverId);
        RpcContextHolder.setContextHolder(rpcContextHolderObject);
        String result = helloService.hello("World");
        System.out.println(result);
        Assert.assertEquals("Hello! World", result);
    }

    @After
    public void setTear(){
        if (rpcSenderProxy != null) {
            try {
                rpcSenderProxy.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
