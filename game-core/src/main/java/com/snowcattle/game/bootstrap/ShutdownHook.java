package com.snowcattle.game.bootstrap;

import com.snowcattle.game.common.util.Assert;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiangwenping on 16/11/18.
 */
public class ShutdownHook implements Runnable {

    ClassPathXmlApplicationContext classPathXmlApplicationContext;

    public ShutdownHook(ClassPathXmlApplicationContext classPathXmlApplicationContext) {
        Assert.notNull(classPathXmlApplicationContext, "The 'beanfactory' argument must not be null.");
        this.classPathXmlApplicationContext = classPathXmlApplicationContext;
    }

    public void run() {  //重写Runnable中的run方法并在此销毁bean
        this.classPathXmlApplicationContext.destroy();
    }
}
