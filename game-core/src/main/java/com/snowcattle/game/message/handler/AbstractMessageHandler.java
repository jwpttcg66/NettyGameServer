package com.snowcattle.game.message.handler;

import com.snowcattle.game.common.annotation.MessageCommandAnnotation;
import com.snowcattle.game.common.constant.Loggers;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangwenping on 17/2/8.
 */
public abstract class AbstractMessageHandler implements IMessageHandler {


    /**
     * 日志
     */
    public static final Logger logger = Loggers.gameLogger;


    private Map<Integer, Method> handlerMethods;

    public AbstractMessageHandler() {
        init();
    }


    public Map<Integer, Method> getHandlerMethods() {
        return handlerMethods;
    }

    public void setHandlerMethods(Map<Integer, Method> handlerMethods) {
        this.handlerMethods = handlerMethods;
    }

    public void init() {
        handlerMethods = new HashMap<Integer, Method>();
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(MessageCommandAnnotation.class)) {
                MessageCommandAnnotation messageCommandAnnotation = method.getAnnotation(MessageCommandAnnotation.class);
                if (messageCommandAnnotation != null) {
                    handlerMethods.put(messageCommandAnnotation.command(), method);
                }
            }
        }
    }

    public Method getMessageHandler(int op) {
        return handlerMethods.get(op);
    }
}

