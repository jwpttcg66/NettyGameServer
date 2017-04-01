package com.wolf.shoot.service.rpc.client.impl;

import com.wolf.shoot.service.rpc.client.ZookeeperRpcServiceDiscovery;
import com.wolf.shoot.service.rpc.server.zookeeper.ZooKeeperNodeBoEnum;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 17/4/1.
 */
@Service
public class DbZookeeperRpcServiceDiscovery extends ZookeeperRpcServiceDiscovery{
    public DbZookeeperRpcServiceDiscovery() {
        super(ZooKeeperNodeBoEnum.DB);
    }
}
