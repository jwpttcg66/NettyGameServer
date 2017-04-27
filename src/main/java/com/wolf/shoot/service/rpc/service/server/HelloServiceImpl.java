package com.wolf.shoot.service.rpc.service.server;

import com.wolf.shoot.common.annotation.RpcServiceAnnotation;
import com.wolf.shoot.common.annotation.RpcServiceBoEnum;
import com.wolf.shoot.common.constant.BOEnum;
import com.wolf.shoot.service.rpc.service.client.HelloService;

/**
 * Created by jwp on 2017/3/7.
 */
@RpcServiceAnnotation(HelloService.class)
@RpcServiceBoEnum(bo = BOEnum.WORLD)
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "Hello! " + name;
    }
}

