package com.snowcattle.game.db.util;

import com.snowcattle.game.db.entity.AbstractEntity;
import com.snowcattle.game.db.service.redis.RedisInterface;
import com.snowcattle.game.db.service.redis.RedisListInterface;
import com.snowcattle.game.db.common.annotation.FieldSave;
import com.snowcattle.game.db.entity.IEntity;
import com.snowcattle.game.db.service.redis.RedisService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jwp on 2017/3/22.
 * 实体辅助类
 */
public class EntityUtils {

    //
    public static String ENTITY_SPLIT_STRING="#";
    /**
     * 获取所有缓存的字段跟值
     * @param iEntity
     * @return
     */
    public static Map<String, String> getCacheValueMap(IEntity iEntity){
        Map<String, String> map = new HashMap<>();
        Field[] fields = getAllCacheFields(iEntity);
        for(Field field: fields){
            String fieldName = field.getName();
            String value = ObjectUtils.getFieldsValueStr(iEntity, fieldName);
            map.put(fieldName, value);
        }
        return map;
    }

    /**
     * 获取代理对象里面变化的值
     * @param iEntity
     * @return
     */
    public static Map<String, String> getProxyChangedCacheValueMap(AbstractEntity entity){
        Map<String, Object> change = entity.getEntityProxyWrapper().getEntityProxy().getChangeParamSet();
        return ObjectUtils.getTransferMap(change);
    }

    /**
     * 获取所有缓存的字段field
     * @param obj
     * @return
     */
    public static Field[] getAllCacheFields(IEntity obj){
        Class<?> clazz = obj.getClass();
        List<Field> fieldList = new ArrayList<>();
        for(;clazz!=Object.class;clazz=clazz.getSuperclass()){
            Field[] fields = clazz.getDeclaredFields();
            for(Field field: fields){
                //获取filed注解
                FieldSave fieldSave = field.getAnnotation(FieldSave.class);
                if(fieldSave != null) {
                    fieldList.add(field);
                }
            }
        }
        return fieldList.toArray(new Field[0]);
    }

    //获取rediskey
    public static String getRedisKey(RedisInterface redisInterface){
        return redisInterface.getRedisKeyEnumString() + redisInterface.getUnionKey();
    }

    //获取rediskey
    public static String getRedisKeyByRedisListInterface(RedisListInterface redisInterface){
        return redisInterface.getRedisKeyEnumString() + redisInterface.getShardingKey();
    }


    /**
     * 更新变化字段
     * @param entity
     */
    public static void updateChangedFieldEntity(RedisService redisService, AbstractEntity entity){
        if (entity != null) {
            if (entity instanceof RedisInterface) {
                RedisInterface redisInterface = (RedisInterface) entity;
                redisService.updateObjectHashMap(EntityUtils.getRedisKey(redisInterface), entity.getEntityProxyWrapper().getEntityProxy().getChangeParamSet());
            } else if (entity instanceof RedisListInterface) {
                RedisListInterface redisListInterface = (RedisListInterface) entity;
                List<RedisListInterface> redisListInterfaceList = new ArrayList<>();
                redisListInterfaceList.add(redisListInterface);
                redisService.setListToHash(EntityUtils.getRedisKeyByRedisListInterface(redisListInterface), redisListInterfaceList);
            }
        }
    }

    /**
     * 更新所有字段
     * @param entity
     */
    public static void updateAllFieldEntity(RedisService redisService, AbstractEntity entity){
        if (entity != null) {
            if (entity instanceof RedisInterface) {
                RedisInterface redisInterface = (RedisInterface) entity;
                redisService.setObjectToHash(EntityUtils.getRedisKey(redisInterface), entity);
            } else if (entity instanceof RedisListInterface) {
                RedisListInterface redisListInterface = (RedisListInterface) entity;
                List<RedisListInterface> redisListInterfaceList = new ArrayList<>();
                redisListInterfaceList.add(redisListInterface);
                redisService.setListToHash(EntityUtils.getRedisKeyByRedisListInterface(redisListInterface), redisListInterfaceList);
            }
        }
    }

    /**
     * 删除实体
     * @param abstractEntity
     */
    public static void deleteEntity(RedisService redisService, AbstractEntity abstractEntity){
        if (abstractEntity != null) {
            if (abstractEntity instanceof RedisInterface) {
                RedisInterface redisInterface = (RedisInterface) abstractEntity;
                redisService.deleteKey(EntityUtils.getRedisKey(redisInterface));
            }else if(abstractEntity instanceof RedisListInterface){
                RedisListInterface redisListInterface = (RedisListInterface) abstractEntity;
                redisService.hdel(EntityUtils.getRedisKeyByRedisListInterface(redisListInterface), redisListInterface.getSubUniqueKey());
            }
        }
    }

    /**
     * 更新所有字段实体列表
     * @param entityList
     */
    public static void updateAllFieldEntityList(RedisService redisService, List<AbstractEntity> entityList){
        //拿到第一个，看一下类型
        if(entityList.size() > 0){
            AbstractEntity entity = entityList.get(0);
            if(entity instanceof  RedisInterface){
                for(AbstractEntity abstractEntity : entityList){
                    updateAllFieldEntity(redisService, entity);
                }
            }else if(entity instanceof RedisListInterface){
                List<RedisListInterface> redisListInterfaceList = new ArrayList<>();
                for(AbstractEntity abstractEntity : entityList){
                    redisListInterfaceList.add((RedisListInterface) abstractEntity);
                }
                redisService.setListToHash(EntityUtils.getRedisKeyByRedisListInterface((RedisListInterface) entity), redisListInterfaceList);
            }
        }
    }

    /**
     * 更新变化字段实体列表
     * @param entityList
     */
    public static void updateChangedFieldEntityList(RedisService redisService, List<AbstractEntity> entityList){
        if(entityList.size() > 0) {
            AbstractEntity entity = entityList.get(0);
            if (entity != null) {
                if (entity instanceof RedisInterface) {
                    for(AbstractEntity abstractEntity : entityList){
                        updateChangedFieldEntity(redisService, entity);
                    }
                } else if (entity instanceof RedisListInterface) {

                    List<RedisListInterface> redisListInterfaceList = new ArrayList<>();
                    for(AbstractEntity abstractEntity : entityList) {
                        RedisListInterface redisListInterface = (RedisListInterface) abstractEntity;
                        redisListInterfaceList.add(redisListInterface);
                    }
                    redisService.setListToHash(EntityUtils.getRedisKeyByRedisListInterface((RedisListInterface) entity), redisListInterfaceList);
                }
            }
        }
    }

    //删除实体列表
    public static void deleteEntityList(RedisService redisService, List<AbstractEntity> entityList){
        if(entityList.size() > 0) {
            AbstractEntity entity = entityList.get(0);
            if (entity != null) {
                if (entity instanceof RedisInterface) {
                    for(AbstractEntity abstractEntity : entityList){
                        deleteEntity(redisService, abstractEntity);
                    }
                } else if (entity instanceof RedisListInterface) {
                    List<String> redisListInterfaceList = new ArrayList<>();
                    for(AbstractEntity abstractEntity : entityList) {
                        RedisListInterface redisListInterface = (RedisListInterface) abstractEntity;
                        redisListInterfaceList.add(redisListInterface.getSubUniqueKey());
                    }
                    redisService.hdel(EntityUtils.getRedisKeyByRedisListInterface((RedisListInterface) entity), redisListInterfaceList.toArray(new String[0]));
                }
            }
        }
    }
}
