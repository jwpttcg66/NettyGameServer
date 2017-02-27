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

    /** 记录统计值开关 */
    private boolean logStatistics = true;

	/* Telnet服务参数定义 */
    /** Telnet服务器名称 */
    private String telnetServerName;
    /** Telnet绑定的ip */
    private String telnetBindIp;
    /** Telnet绑定的端口 */
    private String telnetPort;

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
     * 通讯端口
     */
    private String communicationPort;

    /**
     * 通讯线程池维护链接最大执行数量
     */
    private int communicationMaxThreadPoolSize;

    /**
     * 通讯线程池处理链接最大执行数量
     */
    private int communicationHandlerMaxThreadPoolSize;

    /**
     * 通讯线程池处理 超过此时间写入回应
     */
    private int communicationMaxWriteIntervalTime;

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
    /*updateservice是否使用将多个update绑定在一个线程执行*/
    private boolean updateServiceExcutorFlag = true;
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

    public boolean isLogStatistics() {
        return logStatistics;
    }

    public void setLogStatistics(boolean logStatistics) {
        this.logStatistics = logStatistics;
    }

    public String getTelnetServerName() {
        return telnetServerName;
    }

    public void setTelnetServerName(String telnetServerName) {
        this.telnetServerName = telnetServerName;
    }

    public String getTelnetBindIp() {
        return telnetBindIp;
    }

    public void setTelnetBindIp(String telnetBindIp) {
        this.telnetBindIp = telnetBindIp;
    }

    public String getTelnetPort() {
        return telnetPort;
    }

    public void setTelnetPort(String telnetPort) {
        this.telnetPort = telnetPort;
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
    public String getCommunicationPort() {
        return communicationPort;
    }

    public void setCommunicationPort(String communicationPort) {
        this.communicationPort = communicationPort;
    }


    public int getCommunicationMaxThreadPoolSize() {
        return communicationMaxThreadPoolSize;
    }

    public void setCommunicationMaxThreadPoolSize(int communicationMaxThreadPoolSize) {
        this.communicationMaxThreadPoolSize = communicationMaxThreadPoolSize;
    }

    public int getCommunicationHandlerMaxThreadPoolSize() {
        return communicationHandlerMaxThreadPoolSize;
    }

    public void setCommunicationHandlerMaxThreadPoolSize(int communicationHandlerMaxThreadPoolSize) {
        this.communicationHandlerMaxThreadPoolSize = communicationHandlerMaxThreadPoolSize;
    }

    public int getCommunicationMaxWriteIntervalTime() {
        return communicationMaxWriteIntervalTime;
    }

    public void setCommunicationMaxWriteIntervalTime(int communicationMaxWriteIntervalTime) {
        this.communicationMaxWriteIntervalTime = communicationMaxWriteIntervalTime;
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
}
