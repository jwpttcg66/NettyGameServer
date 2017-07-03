package com.snowcattle.game.bootstrap.manager;

/**
 * Created by jwp on 2017/2/4.
 */
public interface ILocalManager {

    public <T > T get(Class<T> clazz);

    public <X,Y extends X> void create(Class<Y> clazz, Class<X> inter) throws Exception;

    public <T> void add(Object service, Class<T> inter);

    public void shutdown();
}

