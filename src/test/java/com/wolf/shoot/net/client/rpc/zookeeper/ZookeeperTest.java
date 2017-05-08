package com.wolf.shoot.net.client.rpc.zookeeper;

import com.wolf.shoot.TestStartUp;
import com.wolf.shoot.common.config.GameServerConfig;
import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.util.BeanUtil;
import com.wolf.shoot.manager.LocalMananger;
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
public class ZookeeperTest {

    private ZookeeperRpcServiceRegistry zookeeperRpcServiceRegistry;

    private ZookeeperRpcServiceDiscovery zookeeperRpcServiceDiscovery;

    public static void main(String[] args) throws Exception {
        ZookeeperTest zookeeperTest = new ZookeeperTest();
        zookeeperTest.init();
        zookeeperTest.test();
        zookeeperTest.close();
    }
    public void init() {
        TestStartUp.startUpWithSpring();
        zookeeperRpcServiceRegistry = (ZookeeperRpcServiceRegistry) BeanUtil.getBean("zookeeperRpcServiceRegistry");
        zookeeperRpcServiceDiscovery = (ZookeeperRpcServiceDiscovery) BeanUtil.getBean("zookeeperRpcServiceDiscovery");
    }

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
        zookeeperRpcServiceDiscovery.discovery(ZooKeeperNodeBoEnum.WORLD);
        List<ZooKeeperNodeInfo> dataList = zookeeperRpcServiceDiscovery.getNodeList(ZooKeeperNodeBoEnum.WORLD);
        System.out.println(dataList);
        try {
			new Thread().sleep(9999999l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void close() throws Exception {
//        zookeeperRpcServiceRegistry.deleteNode(zookeeperRpcServiceRegistry.getZk(), ZooKeeperNodeBoEnum.WORLD.getRootPath());
//        zookeeperRpcServiceRegistry.deleteNode(zookeeperRpcServiceRegistry.getZk(), ZooKeeperNodeBoEnum.GAME.getRootPath());
//        zookeeperRpcServiceRegistry.deleteNode(zookeeperRpcServiceRegistry.getZk(), ZooKeeperNodeBoEnum.DB.getRootPath());

        zookeeperRpcServiceRegistry.shutdown();
        zookeeperRpcServiceDiscovery.stop();
        LocalMananger.getInstance().getLocalSpringServiceManager().stop();
    }
}
