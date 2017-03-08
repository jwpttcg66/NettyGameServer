package com.wolf.shoot.service.rpc;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.service.IService;
import com.wolf.shoot.service.rpc.client.ConnectManage;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by jwp on 2017/3/8.
 * rpc的服务发现
 */
@Service
public class RpcServiceDiscovery implements IService{

    private static final Logger LOGGER = Loggers.rpcLogger;

    private String registryAddress;

//    public RpcServiceDiscovery(String registryAddress) {
//        this.registryAddress = registryAddress;
//    }

    private void updateConnectedServer(){
        String discovery="127.0.0.1:8090";
        ConnectManage.getInstance().updateConnectedServer(Arrays.asList(discovery));
    }

    @Override
    public String getId() {
        return ServiceName.RpcServiceDiscovery;
    }

    @Override
    public void startup() throws Exception {
        updateConnectedServer();
    }

    @Override
    public void shutdown() throws Exception {
        ConnectManage.getInstance().stop();
    }
}

