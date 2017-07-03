package com.snowcattle.game.net.client.rpc.zookeeper;

import com.snowcattle.game.service.config.GameServerConfigService;
import com.snowcattle.game.service.rpc.server.RpcServerRegisterConfig;
import com.snowcattle.game.TestStartUp;
import com.snowcattle.game.common.config.GameServerConfig;
import com.snowcattle.game.common.util.BeanUtil;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.rpc.client.ZookeeperRpcServiceDiscovery;
import com.snowcattle.game.service.rpc.server.SdRpcServiceProvider;
import com.snowcattle.game.service.rpc.server.zookeeper.ZooKeeperNodeBoEnum;
import com.snowcattle.game.service.rpc.server.zookeeper.ZooKeeperNodeInfo;
import com.snowcattle.game.service.rpc.server.zookeeper.ZookeeperRpcServiceRegistry;

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
        RpcServerRegisterConfig rpcServerRegisterConfig = gameServerConfigService.getRpcServerRegisterConfig();
        SdRpcServiceProvider sdRpcServiceProvider = rpcServerRegisterConfig.getSdRpcServiceProvider();
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();
        String serverId = gameServerConfig.getServerId();
        String host = gameServerConfig.getRpcBindIp();
        String ports = gameServerConfig.getRpcPorts();
        ZooKeeperNodeInfo zooKeeperNodeInfo = new ZooKeeperNodeInfo(ZooKeeperNodeBoEnum.WORLD, serverId, host, ports);

        zookeeperRpcServiceRegistry.register(zooKeeperNodeInfo.getZooKeeperNodeBoEnum().getRootPath(),zooKeeperNodeInfo.getNodePath(), zooKeeperNodeInfo.serialize());
        zookeeperRpcServiceDiscovery.discovery(ZooKeeperNodeBoEnum.WORLD);
        List<ZooKeeperNodeInfo> dataList = zookeeperRpcServiceDiscovery.getNodeList(ZooKeeperNodeBoEnum.WORLD);
        System.out.println(dataList);
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
