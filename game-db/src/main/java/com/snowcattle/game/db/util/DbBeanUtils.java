package com.snowcattle.game.db.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Created by jwp on 2017/4/12.
 */
@Service
public class DbBeanUtils implements ApplicationContextAware {

    private ApplicationContext ctx;
    @Override
    public void setApplicationContext(ApplicationContext arg0)throws BeansException {
        ctx = arg0;
    }

    public Object getBean(String beanName) {
        if(ctx == null){
            throw new NullPointerException();
        }
        return ctx.getBean(beanName);
    }


}