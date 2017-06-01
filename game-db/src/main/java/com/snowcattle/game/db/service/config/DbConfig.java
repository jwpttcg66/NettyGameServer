package com.snowcattle.game.db.service.config;

import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 17/4/6.
 * db的配置
 */
@Service
public class DbConfig {

    /**
     * db集群的编号
     */
    private int dbId;

    /**
     * 异步执行线程存储的线程大小
     */
    private int asyncDbOperationSaveWorkerSize = 1;
    /**
     * 异步执行线程存储选择的线程大小
     */
    private int asyncDbOperationSelectWorkerSize = 1;

    /**
     * 实体服务的包名
     */
    private String asyncOperationPackageName;

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public int getAsyncDbOperationSaveWorkerSize() {
        return asyncDbOperationSaveWorkerSize;
    }

    public void setAsyncDbOperationSaveWorkerSize(int asyncDbOperationSaveWorkerSize) {
        this.asyncDbOperationSaveWorkerSize = asyncDbOperationSaveWorkerSize;
    }

    public int getAsyncDbOperationSelectWorkerSize() {
        return asyncDbOperationSelectWorkerSize;
    }

    public void setAsyncDbOperationSelectWorkerSize(int asyncDbOperationSelectWorkerSize) {
        this.asyncDbOperationSelectWorkerSize = asyncDbOperationSelectWorkerSize;
    }

    public String getAsyncOperationPackageName() {
        return asyncOperationPackageName;
    }

    public void setAsyncOperationPackageName(String asyncOperationPackageName) {
        this.asyncOperationPackageName = asyncOperationPackageName;
    }
}
