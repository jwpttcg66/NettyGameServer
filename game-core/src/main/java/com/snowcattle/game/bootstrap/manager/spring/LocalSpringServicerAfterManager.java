package com.snowcattle.game.bootstrap.manager.spring;

import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.service.rpc.client.RpcClientConnectService;
import com.snowcattle.game.service.rpc.client.ZookeeperRpcServiceDiscovery;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 17/4/1.
 * 因为有些服务需要等待其它服务加载完成后，才可以加载，这里用来解决加载顺序
 */
@Service
public class LocalSpringServicerAfterManager extends AbstractSpringStart{

    private Logger logger = Loggers.serverLogger;

    @Autowired
    private RpcClientConnectService rpcClientConnectService;

    @Autowired
    private ZookeeperRpcServiceDiscovery zookeeperRpcServiceDiscovery;

    public RpcClientConnectService getRpcClientConnectService() {
        return rpcClientConnectService;
    }

    public void setRpcClientConnectService(RpcClientConnectService rpcClientConnectService) {
        this.rpcClientConnectService = rpcClientConnectService;
    }

    public ZookeeperRpcServiceDiscovery getZookeeperRpcServiceDiscovery() {
        return zookeeperRpcServiceDiscovery;
    }

    public void setZookeeperRpcServiceDiscovery(ZookeeperRpcServiceDiscovery zookeeperRpcServiceDiscovery) {
        this.zookeeperRpcServiceDiscovery = zookeeperRpcServiceDiscovery;
    }
}
