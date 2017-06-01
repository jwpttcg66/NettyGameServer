package com.snowcattle.game.db.service.common.cglib;

/**
 * Created by jwp on 2017/3/3.
 */
public class DoCGLib {
    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();
        //通过生成子类的方式创建代理类
        SayHello proxyImp = (SayHello)proxy.getProxy(SayHello.class);
        proxyImp.say();
    }
}