package com.wolf.shoot.rpc.client.zookeeper;

import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.util.BeanUtil;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.manager.spring.LocalSpringBeanManager;
import com.wolf.shoot.manager.spring.LocalSpringServiceManager;
import com.wolf.shoot.service.rpc.zookeeper.ZookeeperRpcServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jiangwenping on 17/3/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean/applicationContext-manager.xml")
public class ZookeeperTest {

    @Autowired
    private ZookeeperRpcServiceRegistry zookeeperRpcServiceRegistry;

    @Before
    public void init() {
        LocalSpringServiceManager localSpringServiceManager = (LocalSpringServiceManager) BeanUtil.getBean("localSpringServiceManager");
        LocalSpringBeanManager localSpringBeanManager = (LocalSpringBeanManager) BeanUtil.getBean("localSpringBeanManager");
        LocalMananger.getInstance().setLocalSpringBeanManager(localSpringBeanManager);
        LocalMananger.getInstance().setLocalSpringServiceManager(localSpringServiceManager);
        try {
            localSpringServiceManager.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        zookeeperRpcServiceRegistry.register("dd");
    }

    @After
    public void close() {
//        zookeeperRpcServiceRegistry.deleteNode(zookeeperRpcServiceRegistry.getZk(), GlobalConstants.ZooKeeperConstants.ZK_DATA_PATH);
        zookeeperRpcServiceRegistry.deleteNode(zookeeperRpcServiceRegistry.getZk(), GlobalConstants.ZooKeeperConstants.ZK_REGISTRY_PATH);
    }
}
