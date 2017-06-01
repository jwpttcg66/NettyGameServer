package com.snowcattle.game.db.sharding;

/**
 * Created by jwp on 2017/3/28.
 * 默认的分批策略
 */
public class EntityServiceShardingStrategy {

    /**
     * 数据库数量
     */
    private int dbCount;
    /**
     * 数据表数量
     */
    private int tableCount;
    /**
     * 数据源名称
     */
    private String dataSource;
    /**
     * 是否分批读取
     */
    private boolean pageFlag = false;

    /**
     * 单次db限制数量
     */
    private int pageLimit = 50;

    /**
     * 是否开放sharding
     */
    private boolean openSharding = true;

    public String getShardingDBKeyByUserId(long userId) {
        if(!openSharding){
            return dataSource;
        }
        long dbIndex = userId % dbCount;
        return dataSource+ dbIndex;
    }

    public int getShardingDBTableIndexByUserId(long userId){
        if(!openSharding){
            return 0;
        }
        return (int) (userId%tableCount);
    }

    public int getDbCount() {
        return dbCount;
    }

    public void setDbCount(int dbCount) {
        this.dbCount = dbCount;
    }

    public int getTableCount() {
        return tableCount;
    }

    public void setTableCount(int tableCount) {
        this.tableCount = tableCount;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public int getPageLimit() {
        return pageLimit;
    }

    public void setPageLimit(int pageLimit) {
        this.pageLimit = pageLimit;
    }

    public boolean isPageFlag() {
        return pageFlag;
    }

    public void setPageFlag(boolean pageFlag) {
        this.pageFlag = pageFlag;
    }

    public boolean isOpenSharding() {
        return openSharding;
    }

    public void setOpenSharding(boolean openSharding) {
        this.openSharding = openSharding;
    }
}
