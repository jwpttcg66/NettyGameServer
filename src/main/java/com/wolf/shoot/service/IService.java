package com.wolf.shoot.service;

/**
 * Created by jwp on 2017/2/4.
 * 基础服务
 */
public interface IService {
    public String getId();
    public void startup() throws Exception;
    public void shutdown() throws Exception;
}
