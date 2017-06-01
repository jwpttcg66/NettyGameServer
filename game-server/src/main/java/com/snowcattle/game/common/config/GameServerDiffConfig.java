package com.snowcattle.game.common.config;


/**
 * 每个服务器独有的配置
 */
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
     * 异步通讯链接保持活跃时间(秒)
     */
    private int rpcTcpHeartKeepAliveTime = 60;

    /**
     * 是否启动zookeeper
     */
    private boolean zookeeperFlag;

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

    public int getRpcTcpHeartKeepAliveTime() {
        return rpcTcpHeartKeepAliveTime;
    }

    public void setRpcTcpHeartKeepAliveTime(int rpcTcpHeartKeepAliveTime) {
        this.rpcTcpHeartKeepAliveTime = rpcTcpHeartKeepAliveTime;
    }

    public boolean isZookeeperFlag() {
        return zookeeperFlag;
    }

    public void setZookeeperFlag(boolean zookeeperFlag) {
        this.zookeeperFlag = zookeeperFlag;
    }
}
