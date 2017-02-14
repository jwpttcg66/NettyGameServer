package com.wolf.shoot.common.config;


public class GameServerDiffConfig implements Config {

    /**
     * 系统配置的数据库版本号
     */
    protected String version;


    private int heartCyleTime;

    /**
     * 房间等待掉线时间(ms)
     */
    private int roomPlayerDisconnectTime;

    /**
     * 房间生命周期(s)
     */
    private int roomLifcCycleTime;

    /**
     * 游戏发送loginout是否关闭session
     */
    private boolean loginOutCloseSession;

    /**
     * 游戏每次处理的消息协议大小
     */
    private int session_prcoss_message_max_size;


    /**
     * 全局更新标志
     */
    private boolean globalUpdateFlag = false;
    /**
     * 全局更新生产线程数量
     */
    private int globalUpdateProduceThreadsSize = 10;
    /**
     * 全局更新生产线程数量
     */
    private int globalUpdateConsumeThreadsSize = 50;
    /**
     * 全局更新最小占用时间
     */
    private int globalUpdateAllUseTimeMinSize = 600;
    /**
     * 全局更新调度过快睡眠时间
     */
    private int globalUpdateAllUseSleepTime = 1;

    /**
     * 异步通讯链接保持活跃时间(秒)
     */
    private int communicationTcpHeartKeepAliveTime = 60;


    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void validate() {

    }

    @Override
    public boolean getIsDebug() {
        return false;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public int getRoomPlayerDisconnectTime() {
        return roomPlayerDisconnectTime;
    }

    public void setRoomPlayerDisconnectTime(int roomPlayerDisconnectTime) {
        this.roomPlayerDisconnectTime = roomPlayerDisconnectTime;
    }

    public int getRoomLifcCycleTime() {
        return roomLifcCycleTime;
    }

    public void setRoomLifcCycleTime(int roomLifcCycleTime) {
        this.roomLifcCycleTime = roomLifcCycleTime;
    }


    public int getSession_prcoss_message_max_size() {
        return session_prcoss_message_max_size;
    }

    public void setSession_prcoss_message_max_size(
            int session_prcoss_message_max_size) {
        this.session_prcoss_message_max_size = session_prcoss_message_max_size;
    }


    public boolean isLoginOutCloseSession() {
        return loginOutCloseSession;
    }

    public void setLoginOutCloseSession(boolean loginOutCloseSession) {
        this.loginOutCloseSession = loginOutCloseSession;
    }

    public int getHeartCyleTime() {
        return heartCyleTime;
    }

    public void setHeartCyleTime(int heartCyleTime) {
        this.heartCyleTime = heartCyleTime;
    }


    public boolean isGlobalUpdateFlag() {
        return globalUpdateFlag;
    }

    public void setGlobalUpdateFlag(boolean globalUpdateFlag) {
        this.globalUpdateFlag = globalUpdateFlag;
    }

    public int getGlobalUpdateProduceThreadsSize() {
        return globalUpdateProduceThreadsSize;
    }

    public void setGlobalUpdateProduceThreadsSize(int globalUpdateProduceThreadsSize) {
        this.globalUpdateProduceThreadsSize = globalUpdateProduceThreadsSize;
    }

    public int getGlobalUpdateConsumeThreadsSize() {
        return globalUpdateConsumeThreadsSize;
    }

    public void setGlobalUpdateConsumeThreadsSize(int globalUpdateConsumeThreadsSize) {
        this.globalUpdateConsumeThreadsSize = globalUpdateConsumeThreadsSize;
    }

    public int getGlobalUpdateAllUseTimeMinSize() {
        return globalUpdateAllUseTimeMinSize;
    }

    public void setGlobalUpdateAllUseTimeMinSize(int globalUpdateAllUseTimeMinSize) {
        this.globalUpdateAllUseTimeMinSize = globalUpdateAllUseTimeMinSize;
    }

    public int getGlobalUpdateAllUseSleepTime() {
        return globalUpdateAllUseSleepTime;
    }

    public void setGlobalUpdateAllUseSleepTime(int globalUpdateAllUseSleepTime) {
        this.globalUpdateAllUseSleepTime = globalUpdateAllUseSleepTime;
    }

    public int getCommunicationTcpHeartKeepAliveTime() {
        return communicationTcpHeartKeepAliveTime;
    }

    public void setCommunicationTcpHeartKeepAliveTime(int communicationTcpHeartKeepAliveTime) {
        this.communicationTcpHeartKeepAliveTime = communicationTcpHeartKeepAliveTime;
    }

}
