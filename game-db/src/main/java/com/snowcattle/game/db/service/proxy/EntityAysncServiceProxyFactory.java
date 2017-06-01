package com.snowcattle.game.db.service.proxy;

import com.snowcattle.game.db.service.async.AsyncDbRegisterCenter;
import com.snowcattle.game.db.service.redis.RedisService;
import com.snowcattle.game.db.service.entity.EntityService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 17/3/29.
 * 实体存储异步代理服务工厂
 */
@Service
public class EntityAysncServiceProxyFactory {

    @Autowired
    private RedisService redisService;

    @Autowired
    private AsyncDbRegisterCenter asyncDbRegisterCenter;

    private EntityAysncServiceProxy createProxy(EntityService EntityService, AsyncDbRegisterCenter asyncDbRegisterCenter){
        return new EntityAysncServiceProxy<>(redisService, asyncDbRegisterCenter);
    }

    private <T extends  EntityService> T  createProxyService(T entityService, EntityAysncServiceProxy entityAysncServiceProxy){
        Enhancer enhancer = new Enhancer();
        //设置需要创建子类的类
        enhancer.setSuperclass(entityService.getClass());
        enhancer.setCallback(entityAysncServiceProxy);
        //通过字节码技术动态创建子类实例
        return (T) enhancer.create();
    }

    public <T extends  EntityService> T createProxyService(T entityService) throws Exception {
        T proxyEntityService = (T) createProxyService(entityService, createProxy(entityService, asyncDbRegisterCenter));
        BeanUtils.copyProperties(proxyEntityService, entityService);
        return proxyEntityService;
    }

    public RedisService getRedisService() {
        return redisService;
    }

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    public AsyncDbRegisterCenter getAsyncDbRegisterCenter() {
        return asyncDbRegisterCenter;
    }

    public void setAsyncDbRegisterCenter(AsyncDbRegisterCenter asyncDbRegisterCenter) {
        this.asyncDbRegisterCenter = asyncDbRegisterCenter;
    }
}
