package com.wolf.shoot.manager.spring;

import com.wolf.shoot.common.loader.DefaultClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by jiangwenping on 17/3/2.
 * 这里的都是单例
 *
 */
@Repository
public class LocalSpringBeanManager {

    @Autowired
    private DefaultClassLoader defaultClassLoader;

    public DefaultClassLoader getDefaultClassLoader() {
        return defaultClassLoader;
    }

    public void setDefaultClassLoader(DefaultClassLoader defaultClassLoader) {
        this.defaultClassLoader = defaultClassLoader;
    }

    public void start() throws  Exception{
        defaultClassLoader.startup();
    }

    public void stop() throws  Exception{

    }
}
