package com.snowcattle.game.db.service.proxy;

import com.snowcattle.game.db.common.Loggers;
import com.snowcattle.game.db.common.annotation.MethodSaveProxy;
import com.snowcattle.game.db.entity.IEntity;
import com.snowcattle.game.db.util.ObjectUtils;
import org.slf4j.Logger;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiangwenping on 17/3/16.
 * db存储的实体代理对象
 */
public class EntityProxy< T extends IEntity> implements MethodInterceptor {

    private Logger logger = Loggers.dbProxyLogger;
    //实体对象
    private T entity;

    //是否需要存储
    private boolean dirtyFlag;

    //那些字段存在变化
    private Map<String, Object> changeParamSet;

    //初始化标志，只有初始化之后才会采集变化字段
    private boolean collectFlag;

    public EntityProxy(T entity) {
        this.changeParamSet = new ConcurrentHashMap<>();
        this.entity = entity;
    }

    //实现MethodInterceptor接口方法
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy methodProxy) throws Throwable {
        //通过代理类调用父类中的方法
        Object result = null;
        if(!collectFlag){
            result = methodProxy.invokeSuper(obj, args);
        }else {
            //检查MethodProxy注解
            MethodSaveProxy methodSaveProxyAnnotation = (MethodSaveProxy) method
                    .getAnnotation(MethodSaveProxy.class);
            if (methodSaveProxyAnnotation != null) {
                //检查对象原来数值
                String filedName = methodSaveProxyAnnotation.proxy();
                Object oldObject = ObjectUtils.getFieldsValueObj(entity, filedName);
                if (logger.isDebugEnabled()) {
                    logger.debug(filedName + "替换前为" + oldObject);
                }

                //获取新参数
                Object newObject = args[0];
                result = methodProxy.invokeSuper(obj, args);

                if (oldObject == null) {
                    dirtyFlag = true;
                } else if (!oldObject.equals(newObject)) {
                    dirtyFlag = true;
                }

                if (dirtyFlag) {
                    if (logger.isDebugEnabled()) {
                        logger.debug(filedName + "替换后为" + newObject);
                    }
                    changeParamSet.put(filedName, newObject);
                }
            } else {
                result = methodProxy.invokeSuper(obj, args);
            }
        }

        return result;
    }


    public IEntity getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public boolean isDirtyFlag() {
        return dirtyFlag;
    }

    public void setDirtyFlag(boolean dirtyFlag) {
        this.dirtyFlag = dirtyFlag;
    }

    public Map<String, Object> getChangeParamSet() {
        return changeParamSet;
    }

    public void setChangeParamSet(Map<String, Object> changeParamSet) {
        this.changeParamSet = changeParamSet;
    }

    public boolean isCollectFlag() {
        return collectFlag;
    }

    public void setCollectFlag(boolean collectFlag) {
        this.collectFlag = collectFlag;
    }
}
