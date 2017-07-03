package com.snowcattle.game.service.rpc.service.server;

import com.snowcattle.game.common.annotation.RpcServiceAnnotation;
import com.snowcattle.game.common.annotation.RpcServiceBoEnum;
import com.snowcattle.game.common.enums.BOEnum;
import com.snowcattle.game.service.rpc.service.client.HelloService;
import org.springframework.stereotype.Repository;

/**
 * Created by jwp on 2017/3/7.
 */
@RpcServiceAnnotation(HelloService.class)
@RpcServiceBoEnum(bo = BOEnum.WORLD)
@Repository
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "Hello! " + name;
    }
}

