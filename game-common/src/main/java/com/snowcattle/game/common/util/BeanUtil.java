package com.snowcattle.game.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 17/3/1.
 */
@Service
public class BeanUtil implements ApplicationContextAware {

    private static ApplicationContext ctx;
    @Override
    public void setApplicationContext(ApplicationContext arg0)throws BeansException {
        ctx = arg0;
    }

    public static Object getBean(String beanName) {
        if(ctx == null){
            throw new NullPointerException();
        }
        return ctx.getBean(beanName);
    }




}
