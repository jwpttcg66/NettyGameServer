package com.wolf.shoot.manager.extend;

import com.wolf.shoot.service.rpc.service.server.HelloServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by jwp on 2017/4/27.
 */
@Repository
public class LocalGameSpringBeanManager {

    @Autowired
    private HelloServiceImpl  helloService;

    public HelloServiceImpl getHelloService() {
        return helloService;
    }

    public void setHelloService(HelloServiceImpl helloService) {
        this.helloService = helloService;
    }
}
