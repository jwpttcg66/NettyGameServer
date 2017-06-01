package com.snowcattle.game.db.service.entity;

import com.snowcattle.game.db.common.DbServiceName;
import com.snowcattle.game.db.common.GlobalConstants;
import com.snowcattle.game.db.common.Loggers;
import com.snowcattle.game.db.common.annotation.AsyncEntityOperation;
import com.snowcattle.game.db.common.loader.scanner.ClassScanner;
import com.snowcattle.game.db.service.async.thread.AsyncDbOperation;
import com.snowcattle.game.db.service.common.service.IDbService;
import com.snowcattle.game.db.service.config.DbConfig;
import com.snowcattle.game.db.util.DbBeanUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiangwenping on 17/4/11.
 * 异步服务注册中心
 */
@Service
public class AsyncOperationRegistry implements IDbService{

    public static Logger logger = Loggers.dbServerLogger;

    @Autowired
    private DbConfig dbConfig;

    @Autowired
    private DbBeanUtils dbBeanUtils;

    /**
     * 包体扫描
     */
    public ClassScanner messageScanner = new ClassScanner();

    /**
     * 注册map
     */
    private ConcurrentHashMap<String, AsyncDbOperation> opeartionMap = new ConcurrentHashMap<String, AsyncDbOperation>();

    @Override
    public String getDbServiceName() {
        return DbServiceName.asyncOperationRegistry;
    }

    public void startup() throws Exception {
        loadPackage(dbConfig.getAsyncOperationPackageName(),
                GlobalConstants.ClassConstants.Ext);
    }

    public void shutdown() throws Exception {

    }



    public void loadPackage(String namespace, String ext) throws Exception {
        String[] fileNames = messageScanner.scannerPackage(namespace, ext);
        // 加载class,获取协议命令
        if(fileNames != null) {
            for (String fileName : fileNames) {
                String realClass = namespace
                        + "."
                        + fileName.subSequence(0, fileName.length()
                        - (ext.length()));
                Class<?> messageClass = Class.forName(realClass);

                logger.info("AsyncEntityOperation load:" + messageClass);
                AsyncEntityOperation asyncEntityOperation = messageClass.getAnnotation(AsyncEntityOperation.class);
                if(asyncEntityOperation != null) {
                    AsyncDbOperation asyncDbOperation = (AsyncDbOperation) dbBeanUtils.getBean(asyncEntityOperation.bean());
                    opeartionMap.put(messageClass.getSimpleName(), asyncDbOperation);
                }
            }
        }
    }

    public DbConfig getDbConfig() {
        return dbConfig;
    }

    public void setDbConfig(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    public ConcurrentHashMap<String, AsyncDbOperation> getOpeartionMap() {
        return opeartionMap;
    }

    public void setOpeartionMap(ConcurrentHashMap<String, AsyncDbOperation> opeartionMap) {
        this.opeartionMap = opeartionMap;
    }

    public Collection<AsyncDbOperation> getAllAsyncEntityOperation(){
        return opeartionMap.values();
    }

    public DbBeanUtils getDbBeanUtils() {
        return dbBeanUtils;
    }

    public void setDbBeanUtils(DbBeanUtils dbBeanUtils) {
        this.dbBeanUtils = dbBeanUtils;
    }
}
