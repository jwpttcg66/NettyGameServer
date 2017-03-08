package com.wolf.shoot.service.rpc;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.service.rpc.client.ConnectManage;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by jwp on 2017/3/8.
 * rpc的服务发现
 */

public class ServiceDiscovery {

    private static final Logger LOGGER = Loggers.rpcLogger;

    private String registryAddress;

    public ServiceDiscovery(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    private void updateConnectedServer(){
        String discovery="127.0.0.1:8090";
        ConnectManage.getInstance().updateConnectedServer(Arrays.asList(discovery));
    }
}

