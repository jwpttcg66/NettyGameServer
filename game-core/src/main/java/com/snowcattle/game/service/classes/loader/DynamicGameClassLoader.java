package com.snowcattle.game.service.classes.loader;

import com.snowcattle.game.common.constant.Loggers;
import org.slf4j.Logger;

import java.util.Hashtable;

/**
 * @author jwp
 *	动态游戏工厂
 */
public class DynamicGameClassLoader extends ClassLoader {

    public static final Logger logger = Loggers.serverLogger;

    private Hashtable<String, Class> loadedClasses = new Hashtable<String, Class>();

    public Class<?> findClass(String className, byte[] b) throws ClassNotFoundException {
        logger.info("class loader find:" + className);
        Class<?>  classes =  defineClass(null, b, 0, b.length);
        loadedClasses.put(className, classes);
        return classes;
    }

    @Override
    public synchronized Class loadClass(String className, boolean resolve) throws ClassNotFoundException {
        logger.info("class loader load:" + className);
        return super.loadClass(className, resolve);
    }

    @Override
    protected Class<?> findClass(final String className) throws ClassNotFoundException{
        if(loadedClasses.containsKey(className)){
            return loadedClasses.get(className);
        }
        return super.findClass(className);
    }


}

