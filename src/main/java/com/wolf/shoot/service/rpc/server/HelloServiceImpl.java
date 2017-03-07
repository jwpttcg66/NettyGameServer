package com.wolf.shoot.service.rpc.server;

import com.wolf.shoot.common.annotation.RpcService;
import com.wolf.shoot.service.rpc.client.HelloService;

/**
 * Created by jwp on 2017/3/7.
 */
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "Hello! " + name;
    }
}

