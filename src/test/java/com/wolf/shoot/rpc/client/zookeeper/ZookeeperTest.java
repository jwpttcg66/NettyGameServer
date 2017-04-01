package com.wolf.shoot.rpc.client.zookeeper;

import com.wolf.shoot.common.config.GameServerConfig;
import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.util.BeanUtil;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.manager.spring.LocalSpringBeanManager;
import com.wolf.shoot.manager.spring.LocalSpringServiceManager;
import com.wolf.shoot.service.rpc.client.ZookeeperRpcServiceDiscovery;
import com.wolf.shoot.service.rpc.server.RpcConfig;
import com.wolf.shoot.service.rpc.server.SdRpcServiceProvider;
import com.wolf.shoot.service.rpc.server.zookeeper.ZooKeeperNodeBoEnum;
import com.wolf.shoot.service.rpc.server.zookeeper.ZooKeeperNodeInfo;
import com.wolf.shoot.service.rpc.server.zookeeper.ZookeeperRpcServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by jiangwenping on 17/3/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean/*.xml")
public class ZookeeperTest {

    @Autowired
    private ZookeeperRpcServiceRegistry zookeeperRpcServiceRegistry;

    @Autowired
    private ZookeeperRpcServiceDiscovery worldZookeeperRpcServiceDiscovery;

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
        zookeeperRpcServiceRegistry.registerZooKeeper();
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        RpcConfig rpcConfig  = gameServerConfigService.getRpcConfig();
        SdRpcServiceProvider sdRpcServiceProvider = rpcConfig.getSdRpcServiceProvider();
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();
        String serverId = gameServerConfig.getServerId();
        String host = gameServerConfig.getRpcBindIp();
        String ports = gameServerConfig.getRpcPorts();
        ZooKeeperNodeInfo zooKeeperNodeInfo = new ZooKeeperNodeInfo(ZooKeeperNodeBoEnum.WORLD, serverId, host, ports);

        zookeeperRpcServiceRegistry.register(zooKeeperNodeInfo.getZooKeeperNodeBoEnum().getRootPath(),zooKeeperNodeInfo.getNodePath(), zooKeeperNodeInfo.serialize());
        worldZookeeperRpcServiceDiscovery.discovery(ZooKeeperNodeBoEnum.WORLD);
        List<ZooKeeperNodeInfo> dataList = worldZookeeperRpcServiceDiscovery.getNodeList(ZooKeeperNodeBoEnum.WORLD);
        System.out.println(dataList);
    }

    @After
    public void close() throws Exception {
//        zookeeperRpcServiceRegistry.deleteNode(zookeeperRpcServiceRegistry.getZk(), ZooKeeperNodeBoEnum.WORLD.getRootPath());
//        zookeeperRpcServiceRegistry.deleteNode(zookeeperRpcServiceRegistry.getZk(), ZooKeeperNodeBoEnum.GAME.getRootPath());
//        zookeeperRpcServiceRegistry.deleteNode(zookeeperRpcServiceRegistry.getZk(), ZooKeeperNodeBoEnum.DB.getRootPath());

        zookeeperRpcServiceRegistry.shutdown();
        worldZookeeperRpcServiceDiscovery.stop();
        LocalMananger.getInstance().getLocalSpringServiceManager().stop();
    }
}
