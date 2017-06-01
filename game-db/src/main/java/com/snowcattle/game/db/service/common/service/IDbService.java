package com.snowcattle.game.db.service.common.service;

/**
 * Created by jiangwenping on 17/4/17.
 */
public interface IDbService {
    public String getDbServiceName();
    public void startup() throws Exception;
    public void shutdown() throws Exception;
}
