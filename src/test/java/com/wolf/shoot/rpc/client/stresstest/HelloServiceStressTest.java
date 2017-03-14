package com.wolf.shoot.rpc.client.stresstest;


import com.wolf.shoot.common.util.BeanUtil;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.manager.spring.LocalSpringBeanManager;
import com.wolf.shoot.manager.spring.LocalSpringServiceManager;
import com.wolf.shoot.service.rpc.RpcContextHolder;
import com.wolf.shoot.service.rpc.RpcServiceDiscovery;
import com.wolf.shoot.service.rpc.client.RpcSender;
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
public class HelloServiceStressTest {

    @Autowired
    private RpcSender rpcSender;


    @Before
    public void init() {
        long current_time = System.currentTimeMillis();
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
            rpcServiceDiscovery.updateOnlineConnectedServer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void helloTest1() {
        String serverId = "8001";
        HelloService helloService = rpcSender.create(HelloService.class);
        final String result = "Hello! World";
        final int test_size = 1_0000;
        int wrong_size = 0;
        int right_size = 0;
        long current_time = System.currentTimeMillis();

        for (int i = 0; i < test_size; i++) {
            RpcContextHolder.setServer(serverId);
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

    @After
    public void setTear() {
        if (rpcSender != null) {
            rpcSender.stop();
        }
    }

}
