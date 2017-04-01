package com.wolf.shoot.service.rpc.client.impl;

import com.wolf.shoot.service.rpc.client.ZookeeperRpcServiceDiscovery;
import com.wolf.shoot.service.rpc.server.zookeeper.ZooKeeperNodeBoEnum;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 17/4/1.
 */
@Service
public class WorldZookeeperRpcServiceDiscovery extends ZookeeperRpcServiceDiscovery{

    public WorldZookeeperRpcServiceDiscovery() {
        super(ZooKeeperNodeBoEnum.WORLD);
    }
}
