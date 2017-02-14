package com.wolf.shoot.common.config;


/**
 * 服务器配置信息
 *
 * 一些key/value对 获取资源的路径
 *
 */
public class GameServerConfig extends ServerConfig {

    /** 系统配置的数据库版本号 */
    private String dbVersion;

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

    /** 登陆墙是否打开，默认关闭 */
    private volatile boolean loginWallEnabled = false;

    /** 反沉迷累计时长同步间隔 5分钟 :5 * 60,单位：秒 */
    private long wallowPeriod = 5 * 60;
    /** 防沉迷配置 */
    private boolean wallowControlled = false;
    /** 开启新手引导 */
    private int openNewerGuide = 1;

    /** 最大玩家等级 */
    private int maxHumanLevel = 120;
    /** 是否以异或方式加载模版资源 */
    private boolean templateXorLoad = true;

    /**
     * 开启存储策略
     */
    private boolean upgradeDbStrategy = true;

    /** 存储时间间隔,单位为毫秒 */
    private int dbUpdateInterval = 200 * 1000;

    /**
     * 帐号激活是否开启
     */
    private boolean accountActivityOpen = false;

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

    public GameServerConfig() {

    }

    @Override
    public void validate() {
        super.validate();
    }

    /**
     * 登陆墙是否打开
     *
     * @return
     */
    public boolean isLoginWallEnabled() {
        return loginWallEnabled;
    }

    /**
     * 设置登陆墙是否打开
     *
     * @param loginWallEnabled
     */
    public void setLoginWallEnabled(boolean loginWallEnabled) {
        this.loginWallEnabled = loginWallEnabled;
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

    public long getWallowPeriod() {
        return wallowPeriod;
    }

    public void setWallowPeriod(long wallowPeriod) {
        this.wallowPeriod = wallowPeriod;
    }

    public boolean isWallowControlled() {
        return wallowControlled;
    }

    public void setWallowControlled(boolean wallowControlled) {
        this.wallowControlled = wallowControlled;
    }

    public void setDbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
    }

    public String getDbVersion() {
        return dbVersion;
    }

    public int getOpenNewerGuide() {
        return this.openNewerGuide;
    }

    public void setOpenNewerGuide(int value) {
        this.openNewerGuide = value;
    }

    public int getMaxHumanLevel() {
        return this.maxHumanLevel;
    }

    public void setMaxHumanLevel(int value) {
        this.maxHumanLevel = value;
    }


    /**
     * 使用异或方式加载模版资源 ?
     *
     * @return
     */
    public boolean isTemplateXorLoad() {
        return this.templateXorLoad;
    }

    /**
     * 使用异或方式加载模版资源 ?
     *
     * @param value
     */
    public void setTemplateXorLoad(boolean value) {
        this.templateXorLoad = value;
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

    public boolean isAccountActivityOpen() {
        return accountActivityOpen;
    }

    public void setAccountActivityOpen(boolean accountActivityOpen) {
        this.accountActivityOpen = accountActivityOpen;
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

}
