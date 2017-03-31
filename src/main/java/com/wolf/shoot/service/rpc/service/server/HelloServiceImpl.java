package com.wolf.shoot.service.rpc.service.server;

import com.wolf.shoot.common.annotation.RpcService;
import com.wolf.shoot.service.rpc.server.IRPCService;
import com.wolf.shoot.service.rpc.service.client.HelloService;

/**
 * Created by jwp on 2017/3/7.
 */
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService, IRPCService {

    @Override
    public String hello(String name) {
        return "Hello! " + name;
    }
}

