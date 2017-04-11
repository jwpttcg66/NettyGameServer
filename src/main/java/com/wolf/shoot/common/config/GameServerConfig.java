package com.wolf.shoot.common.config;


/**
 * 服务器配置信息
 *
 * 一些key/value对 获取资源的路径
 *
 */
public class GameServerConfig extends ServerConfig {

    /** 最大允许在线人数 */
    private int maxOnlineUsers;
    /**
     * 开启存储策略
    */
    private boolean upgradeDbStrategy = true;

    /** 存储时间间隔,单位为毫秒 */
    private int dbUpdateInterval = 200 * 1000;

    private boolean tcpMessageQueueDirectDispatch=true;
    /** session过期失效 ，单位为秒 */
    private int sessionExpireTime = 30 * 60 * 1000;

    /** 检查非法session的时间 单位：秒 */
    private int checkSessionExpireTime = 60;
    /**开发模式*/
    private int developModel;

    /**
     * gameExcutor中UpdateExecutorService线程池心线程大小
     */
    private int gameExcutorCorePoolSize;
    /**
     * gameExcutor中UpdateExecutorService线程池keepalivetime;
     */
    private int gameExcutorKeepAliveTime;
    /**
     * gameExcutor中LockSupportDisptachThread中循环时间 单位毫秒
     */
    private int gameExcutorCycleTime;
    /**
     * gameExcutor中LockSupportDisptachThread中最小循环时间 单位纳秒，计算需要出去gameExcutorCycleTime
     */
    private int gameExcutorMinCycleTime;
    /**服务器监听的端口,多个商品以逗号","分隔 */
    private String updPorts;
    /*udp的queueMessageProcess默认的worker大小*/
    private int updQueueMessageProcessWorkerSize;
    /*udp是否采用GameUdpMessageOrderProcessor*/
    private boolean udpMessageOrderQueueFlag = true;

    /*updateservice是否使用将多个update绑定在一个线程执行*/
    private boolean updateServiceExcutorFlag = true;

    //开启rpc
    private boolean rpcFlag = false;

    /*rpc端口*/
    private String rpcPorts;
    /*rpc线程池*/
    private int rpcThreadPoolSize;
    /*rpc等待大小*/
    private int rpcThreadPoolQueueSize;
    /*rpc连接线程池大小*/
    private int rpcConnectThreadSize;
    /*rpc连接线程池大小*/
    private int rpcSendProxyThreadSize;
    /*这个是提供外网使用的，请使用外网地址*/
    private String rpcBindIp;
    /*rpc服务的包名字*/
    private String rpcServicePackage;
    /*rpc服务的包名字*/
    private String netMessageHandlerNameSpace;
    /*rpc服务的包名字*/
    private String netMsgNameSpace;

    public GameServerConfig() {
    }

    @Override
    public void validate() {
        super.validate();
    }

    /**
     * @return the maxOnlineUsers
     */
    public int getMaxOnlineUsers() {
        return maxOnlineUsers;
    }

    /**
     * @param maxOnlineUsers
     *            the maxOnlineUsers to set
     */
    public void setMaxOnlineUsers(int maxOnlineUsers) {
        this.maxOnlineUsers = maxOnlineUsers;
    }

    public boolean isUpgradeDbStrategy() {
        return upgradeDbStrategy;
    }

    public void setUpgradeDbStrategy(boolean upgradeDbStrategy) {
        this.upgradeDbStrategy = upgradeDbStrategy;
    }

    public int getDbUpdateInterval() {
        return dbUpdateInterval;
    }

    public void setDbUpdateInterval(int dbUpdateInterval) {
        this.dbUpdateInterval = dbUpdateInterval;
    }

    public int getSessionExpireTime() {
        return sessionExpireTime;
    }

    public void setSessionExpireTime(int sessionExpireTime) {
        this.sessionExpireTime = sessionExpireTime;
    }

    public int getCheckSessionExpireTime() {
        return checkSessionExpireTime;
    }

    public void setCheckSessionExpireTime(int checkSessionExpireTime) {
        this.checkSessionExpireTime = checkSessionExpireTime;
    }

    public int getDevelopModel() {
        return developModel;
    }

    public void setDevelopModel(int developModel) {
        this.developModel = developModel;
    }

    /**
     * 是否是开发模式
     * @return
     */
    public boolean isDevelopModel() {
        return this.developModel == 1;
    }

    public int getServerIdInt(){
        return Integer.parseInt(this.serverId);
    }

    public boolean isTcpMessageQueueDirectDispatch() {
        return tcpMessageQueueDirectDispatch;
    }

    public void setTcpMessageQueueDirectDispatch(boolean tcpMessageQueueDirectDispatch) {
        this.tcpMessageQueueDirectDispatch = tcpMessageQueueDirectDispatch;
    }

    public int getGameExcutorCorePoolSize() {
        return gameExcutorCorePoolSize;
    }

    public void setGameExcutorCorePoolSize(int gameExcutorCorePoolSize) {
        this.gameExcutorCorePoolSize = gameExcutorCorePoolSize;
    }

    public int getGameExcutorKeepAliveTime() {
        return gameExcutorKeepAliveTime;
    }

    public void setGameExcutorKeepAliveTime(int gameExcutorKeepAliveTime) {
        this.gameExcutorKeepAliveTime = gameExcutorKeepAliveTime;
    }

    public int getGameExcutorCycleTime() {
        return gameExcutorCycleTime;
    }

    public void setGameExcutorCycleTime(int gameExcutorCycleTime) {
        this.gameExcutorCycleTime = gameExcutorCycleTime;
    }

    public int getGameExcutorMinCycleTime() {
        return gameExcutorMinCycleTime;
    }

    public void setGameExcutorMinCycleTime(int gameExcutorMinCycleTime) {
        this.gameExcutorMinCycleTime = gameExcutorMinCycleTime;
    }

    public String getUpdPorts() {
        return updPorts;
    }

    public void setUpdPorts(String updPorts) {
        this.updPorts = updPorts;
    }

    public int getUdpPort(){
        String ports = getUpdPorts();
        String[] splitPorts = ports.split(",");
        return Integer.parseInt(splitPorts[0]);
    }

    public int getUpdQueueMessageProcessWorkerSize() {
        return updQueueMessageProcessWorkerSize;
    }

    public void setUpdQueueMessageProcessWorkerSize(int updQueueMessageProcessWorkerSize) {
        this.updQueueMessageProcessWorkerSize = updQueueMessageProcessWorkerSize;
    }

    public boolean isUpdateServiceExcutorFlag() {
        return updateServiceExcutorFlag;
    }

    public void setUpdateServiceExcutorFlag(boolean updateServiceExcutorFlag) {
        this.updateServiceExcutorFlag = updateServiceExcutorFlag;
    }

    public boolean isRpcFlag() {
        return rpcFlag;
    }

    public void setRpcFlag(boolean rpcFlag) {
        this.rpcFlag = rpcFlag;
    }

    public String getRpcPorts() {
        return rpcPorts;
    }

    public void setRpcPorts(String rpcPorts) {
        this.rpcPorts = rpcPorts;
    }

    public int getRpcThreadPoolSize() {
        return rpcThreadPoolSize;
    }

    public void setRpcThreadPoolSize(int rpcThreadPoolSize) {
        this.rpcThreadPoolSize = rpcThreadPoolSize;
    }

    public int getRpcThreadPoolQueueSize() {
        return rpcThreadPoolQueueSize;
    }

    public void setRpcThreadPoolQueueSize(int rpcThreadPoolQueueSize) {
        this.rpcThreadPoolQueueSize = rpcThreadPoolQueueSize;
    }

    public int getFirstRpcPort(){
        String ports = getRpcPorts();
        String[] splitPorts = ports.split(",");
        return Integer.parseInt(splitPorts[0]);
    }

    public boolean isUdpMessageOrderQueueFlag() {
        return udpMessageOrderQueueFlag;
    }

    public void setUdpMessageOrderQueueFlag(boolean udpMessageOrderQueueFlag) {
        this.udpMessageOrderQueueFlag = udpMessageOrderQueueFlag;
    }

    public int getRpcConnectThreadSize() {
        return rpcConnectThreadSize;
    }

    public void setRpcConnectThreadSize(int rpcConnectThreadSize) {
        this.rpcConnectThreadSize = rpcConnectThreadSize;
    }

    public int getRpcSendProxyThreadSize() {
        return rpcSendProxyThreadSize;
    }

    public void setRpcSendProxyThreadSize(int rpcSendProxyThreadSize) {
        this.rpcSendProxyThreadSize = rpcSendProxyThreadSize;
    }

    public String getRpcBindIp() {
        return rpcBindIp;
    }

    public void setRpcBindIp(String rpcBindIp) {
        this.rpcBindIp = rpcBindIp;
    }

    public String getRpcServicePackage() {
        return rpcServicePackage;
    }

    public void setRpcServicePackage(String rpcServicePackage) {
        this.rpcServicePackage = rpcServicePackage;
    }

    public String getNetMessageHandlerNameSpace() {
        return netMessageHandlerNameSpace;
    }

    public void setNetMessageHandlerNameSpace(String netMessageHandlerNameSpace) {
        this.netMessageHandlerNameSpace = netMessageHandlerNameSpace;
    }

    public String getNetMsgNameSpace() {
        return netMsgNameSpace;
    }

    public void setNetMsgNameSpace(String netMsgNameSpace) {
        this.netMsgNameSpace = netMsgNameSpace;
    }
}
