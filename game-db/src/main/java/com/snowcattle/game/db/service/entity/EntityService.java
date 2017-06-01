package com.snowcattle.game.db.service.entity;

import com.github.pagehelper.PageRowBounds;
import com.snowcattle.game.db.common.Loggers;
import com.snowcattle.game.db.common.annotation.DbMapper;
import com.snowcattle.game.db.common.annotation.DbOperation;
import com.snowcattle.game.db.common.enums.DbOperationEnum;
import com.snowcattle.game.db.entity.AbstractEntity;
import com.snowcattle.game.db.entity.BaseLongIDEntity;
import com.snowcattle.game.db.entity.BaseStringIDEntity;
import com.snowcattle.game.db.entity.IEntity;
import com.snowcattle.game.db.service.jdbc.mapper.IDBMapper;
import com.snowcattle.game.db.service.proxy.EntityProxyWrapper;
import com.snowcattle.game.db.sharding.CustomerContextHolder;
import com.snowcattle.game.db.sharding.EntityServiceShardingStrategy;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangwenping on 17/3/21.
 * 模版实体数据提服务
 * 批量应该保证它们在同一个数据库中
 */
public abstract class EntityService<T extends AbstractEntity> implements IEntityService<T> {

    private static final Logger logger = Loggers.dbLogger;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private SqlSessionTemplate sqlSessionBatchTemplate;

    @Autowired
    private EntityServiceShardingStrategy defaultEntityServiceShardingStrategy;

    private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();

    /**
     * 插入实体
     *
     * @param entity
     * @return
     */
    @Override
    @DbOperation(operation = DbOperationEnum.insert)
    public long insertEntity(T entity) {
        long selectId = getShardingId(entity);
        CustomerContextHolder.setCustomerType(getEntityServiceShardingStrategy().getShardingDBKeyByUserId(selectId));
        entity.setSharding_table_index(getEntityServiceShardingStrategy().getShardingDBTableIndexByUserId(selectId));
        IDBMapper<T> idbMapper = getTemplateMapper(entity);
        long result = -1;
        try {
            result = idbMapper.insertEntity(entity);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
        }
        return result;
    }

    /**
     * 查询实体
     *
     * @return
     */
    @DbOperation(operation = DbOperationEnum.query)
    public IEntity getEntity(T entity) {
        long selectId = getShardingId(entity);
        CustomerContextHolder.setCustomerType(getEntityServiceShardingStrategy().getShardingDBKeyByUserId(selectId));
        entity.setSharding_table_index(getEntityServiceShardingStrategy().getShardingDBTableIndexByUserId(selectId));
        IDBMapper<T> idbMapper = getTemplateMapper(entity);
        IEntity result = null;
        try {
            result = idbMapper.getEntity(entity);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
        }
        return result;
    }

    @DbOperation(operation = DbOperationEnum.queryList)
    public List<T> getEntityList(T entity) {
        long selectId = getShardingId(entity);
        CustomerContextHolder.setCustomerType(getEntityServiceShardingStrategy().getShardingDBKeyByUserId(selectId));
        entity.setSharding_table_index(getEntityServiceShardingStrategy().getShardingDBTableIndexByUserId(selectId));
        IDBMapper<T> idbMapper = getTemplateMapper(entity);
        List<T> result = null;

        EntityServiceShardingStrategy entityServiceShardingStrategy = getDefaultEntityServiceShardingStrategy();
        try {
            if(!entityServiceShardingStrategy.isPageFlag()){
                result = idbMapper.getEntityList(entity);
            }else{
                int pageLimit = entityServiceShardingStrategy.getPageLimit();
                PageRowBounds pageRowBounds = new PageRowBounds(0, pageLimit);
                result = idbMapper.getEntityList(entity, pageRowBounds);
                long count = pageRowBounds.getTotal().longValue();
                if(count > pageLimit) {
                    int offset = pageLimit;
                    while (offset < count){
                        pageRowBounds = new PageRowBounds(offset, pageLimit);
                        result.addAll(idbMapper.getEntityList(entity, pageRowBounds));
                        offset+=pageLimit;
                    }
                }
            }

        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
        }
        return result;
    }

    /**
     * RedisInterface直接查询db的list接口
     * @param entity 需要实现代理
     * @return
     */
    public List<T> filterList(T entity) {
        long selectId = getShardingId(entity);
        CustomerContextHolder.setCustomerType(getEntityServiceShardingStrategy().getShardingDBKeyByUserId(selectId));
        entity.setSharding_table_index(getEntityServiceShardingStrategy().getShardingDBTableIndexByUserId(selectId));
        IDBMapper<T> idbMapper = getTemplateMapper(entity);
        List<T> result = null;

        Map hashMap = new HashMap<>();
        hashMap.put("sharding_table_index", entity.getSharding_table_index());
        hashMap.put("userId", entity.getUserId());
        EntityProxyWrapper entityProxyWrapper = entity.getEntityProxyWrapper();
        if(entityProxyWrapper != null){
            hashMap.putAll(entityProxyWrapper.getEntityProxy().getChangeParamSet());
        }

        EntityServiceShardingStrategy entityServiceShardingStrategy = getDefaultEntityServiceShardingStrategy();
        try {
            if(!entityServiceShardingStrategy.isPageFlag()){
                result = idbMapper.filterList(hashMap);
            }else{
                int pageLimit = entityServiceShardingStrategy.getPageLimit();
                PageRowBounds pageRowBounds = new PageRowBounds(0, pageLimit);
                result = idbMapper.filterList(hashMap, pageRowBounds);
                long count = pageRowBounds.getTotal().longValue();
                if(count > pageLimit) {
                    int offset = pageLimit;
                    while (offset < count){
                        pageRowBounds = new PageRowBounds(offset, pageLimit);
                        result.addAll(idbMapper.filterList(hashMap, pageRowBounds));
                        offset+=pageLimit;
                    }
                }
            }

        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
        }
        return result;
    }

    /**
     * 修改实体
     *
     * @param entity
     */
    @DbOperation(operation = DbOperationEnum.update)
    public void updateEntity(T entity) {
        long selectId = getShardingId(entity);
        CustomerContextHolder.setCustomerType(getEntityServiceShardingStrategy().getShardingDBKeyByUserId(selectId));
        int sharding_table_index = getEntityServiceShardingStrategy().getShardingDBTableIndexByUserId(selectId);
        Map hashMap = new HashMap<>();
        hashMap.put("sharding_table_index", sharding_table_index);
        hashMap.put("userId", entity.getUserId());
        hashMap.put("id", entity.getId());
        EntityProxyWrapper entityProxyWrapper = entity.getEntityProxyWrapper();
        //只有数据变化的时候才会更新
        if (entityProxyWrapper != null && entityProxyWrapper.getEntityProxy().isDirtyFlag()) {
            hashMap.putAll(entityProxyWrapper.getEntityProxy().getChangeParamSet());
            IDBMapper<T> idbMapper = getTemplateMapper((T) entity);
            try {
                idbMapper.updateEntityByMap(hashMap);
            } catch (Exception e) {
                logger.error(e.toString(), e);
            } finally {
            }
        } else {
            logger.error("updateEntity cance " + entity.getClass().getSimpleName() + "id:" + entity.getId() + " userId:" + entity.getUserId());
        }
    }

    /**
     * 删除实体
     *
     * @param entity
     */
    @DbOperation(operation = DbOperationEnum.delete)
    public void deleteEntity(T entity) {
        long selectId = getShardingId(entity);
        CustomerContextHolder.setCustomerType(getEntityServiceShardingStrategy().getShardingDBKeyByUserId(selectId));
        entity.setSharding_table_index(getEntityServiceShardingStrategy().getShardingDBTableIndexByUserId(selectId));
        IDBMapper<T> idbMapper = getTemplateMapper(entity);
        try {
            idbMapper.deleteEntity(entity);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
        }
    }

    /**
     * 获取分库主键
     * @param entity
     * @return
     */
    @Override
    public long getShardingId(T entity) {
        long shardingId = entity.getUserId();
        if (entity.getEntityKeyShardingStrategyEnum().equals(EntityKeyShardingStrategyEnum.ID)) {
            if(entity instanceof BaseLongIDEntity) {
                BaseLongIDEntity baseLongIDEntity = (BaseLongIDEntity) entity;
                shardingId = baseLongIDEntity.getId();
            }else if(entity instanceof BaseStringIDEntity){
                BaseStringIDEntity baseStringIDEntity = (BaseStringIDEntity)entity;
                shardingId = baseStringIDEntity.getId().hashCode();
            }
        }
        return shardingId;
    }

    /**
     * Function  : 获取sqlSession
     */
    public SqlSession getBatchSession() {
        SqlSession session = threadLocal.get();

        if (session == null) {
            //如果sqlSessionFactory不为空则获取sqlSession，否则返回null
            session = (sqlSessionBatchTemplate.getSqlSessionFactory() != null) ? sqlSessionBatchTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, true) : null;
            threadLocal.set(session);
        }
        return session;
    }

    /**
     * Function  : 关闭sqlSession
     */
    public void closeBatchSession() {
        SqlSession session = threadLocal.get();
        if (session != null) {
            if(logger.isDebugEnabled()) {
                logger.debug("销毁");
            }
            session.close();
            threadLocal.set(null);
        }
    }

    /**
     * Function  : 关闭sqlSession
     */
    public void rollbackBatchSession() {
        SqlSession session = threadLocal.get();
        if (session != null) {
            session.rollback();
        }
    }

    public void commitBatchSession(){
        SqlSession session = threadLocal.get();
        if (session != null) {
            session.commit();
        }
    }

    public IDBMapper<T> getTemplateMapper(T entity) {
        DbMapper mapper = entity.getClass().getAnnotation(DbMapper.class);
        return (IDBMapper<T>) sqlSessionTemplate.getMapper(mapper.mapper());
    }

    public IDBMapper<T> getBatchTemplateMapper(SqlSession sqlSession, T entity) {
        DbMapper mapper = entity.getClass().getAnnotation(DbMapper.class);
        return (IDBMapper<T>) sqlSession.getMapper(mapper.mapper());
    }

    @Override
    @DbOperation(operation = DbOperationEnum.insertBatch)
    public List<Long> insertEntityBatch(List<T> entityList) {
        List<Long> result = new ArrayList<>();
        SqlSession sqlSession = getBatchSession();
        try {
            for (T entity : entityList) {
                long selectId = getShardingId(entity);
                CustomerContextHolder.setCustomerType(getEntityServiceShardingStrategy().getShardingDBKeyByUserId(selectId));
                entity.setSharding_table_index(getEntityServiceShardingStrategy().getShardingDBTableIndexByUserId(selectId));
                IDBMapper<T> mapper = getBatchTemplateMapper(sqlSession, entity);
                long insertReuslt = mapper.insertEntity(entity);
                result.add(insertReuslt);
            }
            commitBatchSession();
        }catch (Exception e){
            logger.error("insertBatch error " + e.toString(), e);
            rollbackBatchSession();
        }finally {
            closeBatchSession();
        }
        return result;
    }

    @Override
    @DbOperation(operation = DbOperationEnum.updateBatch)
    public void updateEntityBatch(List<T> entityList) {
        SqlSession sqlSession = getBatchSession();
        try {
            for (T entity : entityList) {
                long selectId = getShardingId(entity);
                CustomerContextHolder.setCustomerType(getEntityServiceShardingStrategy().getShardingDBKeyByUserId(selectId));
                int sharding_table_index = getEntityServiceShardingStrategy().getShardingDBTableIndexByUserId(selectId);
                entity.setSharding_table_index(sharding_table_index);
                IDBMapper<T> mapper = getBatchTemplateMapper(sqlSession, (T) entity);
                Map hashMap = new HashMap<>();
                hashMap.put("sharding_table_index", sharding_table_index);
                hashMap.put("userId", entity.getUserId());
                hashMap.put("id", entity.getId());
                EntityProxyWrapper entityProxyWrapper = entity.getEntityProxyWrapper();
                //只有数据变化的时候才会更新
                if (entityProxyWrapper != null && entityProxyWrapper.getEntityProxy().isDirtyFlag()) {
                    hashMap.putAll(entityProxyWrapper.getEntityProxy().getChangeParamSet());
                    try {
                        mapper.updateEntityByMap(hashMap);
                    } catch (Exception e) {
                        logger.error(e.toString(), e);
                    } finally {
                    }
                } else {
                    logger.error("updateEntityBatch cancer " + entity.getClass().getSimpleName() + "id:" + entity.getId() + " userId:" + entity.getUserId());
                }
            }
            commitBatchSession();
        }catch (Exception e){
            logger.error("updateBatchError" + e.toString(), e);
            rollbackBatchSession();
        }finally {
            closeBatchSession();
        }
    }

    @Override
    @DbOperation(operation = DbOperationEnum.deleteBatch)
    public void deleteEntityBatch(List<T> entityList) {
        SqlSession sqlSession = getBatchSession();
        try {
            for (T iEntity : entityList) {
                long selectId = getShardingId(iEntity);
                CustomerContextHolder.setCustomerType(getEntityServiceShardingStrategy().getShardingDBKeyByUserId(selectId));
                iEntity.setSharding_table_index(getEntityServiceShardingStrategy().getShardingDBTableIndexByUserId(selectId));
                IDBMapper<T> mapper = getBatchTemplateMapper(sqlSession, iEntity);
                mapper.deleteEntity(iEntity);
            }
            commitBatchSession();
        }catch (Exception e){
            logger.error("deleteBatchError" + e.toString(), e);
            rollbackBatchSession();
        }finally {
            closeBatchSession();
        }
    }

    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public SqlSessionTemplate getSqlSessionBatchTemplate() {
        return sqlSessionBatchTemplate;
    }

    public void setSqlSessionBatchTemplate(SqlSessionTemplate sqlSessionBatchTemplate) {
        this.sqlSessionBatchTemplate = sqlSessionBatchTemplate;
    }

    public EntityServiceShardingStrategy getDefaultEntityServiceShardingStrategy() {
        return defaultEntityServiceShardingStrategy;
    }

    public void setDefaultEntityServiceShardingStrategy(EntityServiceShardingStrategy defaultEntityServiceShardingStrategy) {
        this.defaultEntityServiceShardingStrategy = defaultEntityServiceShardingStrategy;
    }

    abstract  public EntityServiceShardingStrategy getEntityServiceShardingStrategy();

    //获取模版参数类
    public Class<T> getEntityTClass(){
        Class classes = getClass();
        Class result = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        return result;
    }
}
