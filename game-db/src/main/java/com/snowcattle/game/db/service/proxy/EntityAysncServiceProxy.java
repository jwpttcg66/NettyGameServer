package com.snowcattle.game.db.service.proxy;

import com.snowcattle.game.db.service.async.AsyncDbRegisterCenter;
import com.snowcattle.game.db.service.redis.AsyncSave;
import com.snowcattle.game.db.service.redis.RedisInterface;
import com.snowcattle.game.db.service.redis.RedisListInterface;
import com.snowcattle.game.db.service.redis.RedisService;
import com.snowcattle.game.db.common.Loggers;
import com.snowcattle.game.db.common.annotation.DbOperation;
import com.snowcattle.game.db.common.enums.DbOperationEnum;
import com.snowcattle.game.db.entity.AbstractEntity;
import com.snowcattle.game.db.entity.IEntity;
import com.snowcattle.game.db.service.entity.EntityService;
import com.snowcattle.game.db.util.EntityUtils;
import org.slf4j.Logger;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by jiangwenping on 17/3/29.
 * 存储策略为全部存入缓存(包括删除)，然后存入队列，进行异步线程存入db
 */
public class EntityAysncServiceProxy<T extends EntityService> extends  EntityServiceProxy implements MethodInterceptor {
    private static final Logger proxyLogger = Loggers.dbServiceProxyLogger;

    private RedisService redisService;

    private AsyncDbRegisterCenter asyncDbRegisterCenter;

    public EntityAysncServiceProxy(RedisService redisService, AsyncDbRegisterCenter asyncDbRegisterCenter) {
        super(redisService, true);
        this.redisService = redisService;
        this.asyncDbRegisterCenter = asyncDbRegisterCenter;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        DbOperation dbOperation = method.getAnnotation(DbOperation.class);
        if(dbOperation == null) { //如果没有进行注解，直接进行返回
            result = methodProxy.invokeSuper(obj, args);
        }else {
            //进行数据库操作
            DbOperationEnum dbOperationEnum = dbOperation.operation();
            switch (dbOperationEnum) {
                case insert:
                    AbstractEntity abstractEntity = (AbstractEntity) args[0];
                    EntityUtils.updateAllFieldEntity(redisService, abstractEntity);
                    asyncSaveEntity((EntityService) obj, dbOperationEnum, abstractEntity);
                    break;
                case insertBatch:
                    List<AbstractEntity> entityList = (List<AbstractEntity>) args[0];
                    EntityUtils.updateAllFieldEntityList(redisService, entityList);
                    asyncBatchSaveEntity((EntityService)obj, dbOperationEnum, entityList);
                    break;
                case update:
                    abstractEntity = (AbstractEntity) args[0];
                    EntityUtils.updateChangedFieldEntity(redisService, abstractEntity);
                    asyncSaveEntity((EntityService) obj, dbOperationEnum, abstractEntity);
                    break;
                case updateBatch:
                    entityList = (List<AbstractEntity>) args[0];
                    EntityUtils.updateChangedFieldEntityList(redisService, entityList);
                    asyncBatchSaveEntity((EntityService) obj, dbOperationEnum, entityList);
                    break;
                case delete:
                    abstractEntity = (AbstractEntity) args[0];
//                    deleteEntity(abstractEntity);
                    //如果是删除， db删除后执行回调
                    asyncSaveEntity((EntityService) obj, dbOperationEnum, abstractEntity);
                    break;
                case deleteBatch:
                    entityList = (List<AbstractEntity>) args[0];
                    EntityUtils.deleteEntityList(redisService, entityList);
                    asyncBatchSaveEntity((EntityService) obj, dbOperationEnum, entityList);
                    break;
                case query:
                    abstractEntity = (AbstractEntity) args[0];
                    if (abstractEntity != null) {
                        if (abstractEntity instanceof RedisInterface) {
                            RedisInterface redisInterface = (RedisInterface) abstractEntity;
                            result = redisService.getObjectFromHash(EntityUtils.getRedisKey(redisInterface), abstractEntity.getClass());
                        } else {
                            proxyLogger.error("query interface RedisListInterface " + abstractEntity.getClass().getSimpleName() + " use RedisInterface " + abstractEntity.toString());
                        }
                    }
                    break;
                case queryList:
                    abstractEntity = (AbstractEntity) args[0];
                    if (abstractEntity != null) {
                        if (abstractEntity instanceof RedisListInterface) {
                            RedisListInterface redisInterface = (RedisListInterface) abstractEntity;
                            result = redisService.getListFromHash(EntityUtils.getRedisKeyByRedisListInterface(redisInterface), abstractEntity.getClass());
                            if(result != null){
                                result = filterEntity((List<IEntity>) result, abstractEntity);
                            }
                        } else {
                            proxyLogger.error("query interface RedisInterface " + abstractEntity.getClass().getSimpleName() + " use RedisListInterface " + abstractEntity.toString());
                        }
                    }
                    break;
            }
        }
        return result;
    }

    /**
     * 个体放入异步注册中心
     * @param entityService
     * @param dbOperationEnum
     * @param abstractEntity
     */
    public void asyncSaveEntity(EntityService entityService, DbOperationEnum dbOperationEnum, AbstractEntity abstractEntity){
        //放入异步存储的key
        if(abstractEntity instanceof AsyncSave) {
            asyncDbRegisterCenter.asyncRegisterEntity(entityService, dbOperationEnum, abstractEntity);
        }else{
            proxyLogger.error("async save interface not asynccachekey " + abstractEntity.getClass().getSimpleName() + " use " + abstractEntity.toString());
        }
    }

    /**
     * 批量放入异步注册中心
     * @param entityService
     * @param dbOperationEnum
     * @param abstractEntityList
     */
    public void asyncBatchSaveEntity(EntityService entityService, DbOperationEnum dbOperationEnum, List<AbstractEntity> abstractEntityList){
        if(abstractEntityList.size() > 0) {
            AbstractEntity abstractEntity = abstractEntityList.get(0);
            //放入异步存储的key
            if (abstractEntity instanceof AsyncSave) {
                asyncDbRegisterCenter.asyncBatchRegisterEntity(entityService, dbOperationEnum, abstractEntityList);
            } else {
                proxyLogger.error("async batch save interface not asynccachekey " + abstractEntity.getClass().getSimpleName() + " use " + abstractEntity.toString());
            }
        }
    }
}
