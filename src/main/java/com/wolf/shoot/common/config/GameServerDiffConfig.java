package com.wolf.shoot.common.config;


public class GameServerDiffConfig implements Config {

    protected String version;

    /**
     * 系统配置的数据库版本号
     */
    private int heartCyleTime;

    /**
     * 房间等待掉线时间(ms)
     */
    private int roomPlayerDisconnectTime;

    /**
     * 是否腾讯压测
     */
    private int txStressTest = 0;

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
     * 房间更新核心线程数量
     */
    private int roomUpdateCoreThreads = 1;
    /**
     * 房间更新最大线程数量
     */
    private int roomUpdateMaxThreads = 6000;
    /**
     * 房间更新最大信号量
     */
    private int roomSemaphoreMaxSize = 500;
    /**
     * 房间更新单次最多占用时间(ms)
     */
    private int roomUpdateSingleUseTimeMaxSize = 300;
    /**
     * 房间更新单次间隔（单位毫秒）
     */
    private int roomUpdateRefreshDistanceTime = 1000;

    /**
     * 队列执行线程线程大小
     */
    private int queueMessageExecutorSize = 10;

    /**
     * session更新生产线程数量
     */
    private int sessionUpdateProduceThreadsSize = 1;
    /**
     * session更新消费线程数量
     */
    private int sessionUpdateConsumeThreadsSize = 5;

    /**
     * room更新生产线程数量
     */
    private int roomUpdateProduceThreadsSize = 1;
    /**
     * room更新消费线程数量
     */
    private int roomUpdateConsumeThreadsSize = 5;
    /**
     * session更新单次最多占用时间(ms)
     */
    private int sessionUpdateSingleUseTimeMaxSize = 300;

    /**
     * 开启queue分发模式
     */
    private boolean queueDispatchFlag = false;
    /**
     * 开启commmon结果缓存
     */
    private boolean commonResultCacheFlag = true;
    /**
     * session增加生产者为空时睡眠时间
     */
    private int sessionAddProductNullSleepTime = 5;
    /**
     * room增加生产者为空时睡眠时间
     */
    private int roomAddProductNullSleepTime = 5;
    /**
     * 链接保持活跃时间
     */
    private int tcpHeartKeepAliveTime = 60;

    /**
     * 整个session更新最少占用时间(ms)
     */
    private int sessionUpdateAllUseTimeMinSize = 600;
    /**
     * 整个Room更新最小占用时间(ms)
     */
    private int roomUpdateAllUseTimeMinSize = 600;
    /**
     * session调度过快睡眠时间
     */
    private int sessionUpdateAllUseSleepTime = 1;
    /**
     * room调度过快睡眠时间
     */
    private int roomUpdateAllUseSleepTime = 1;

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
     * 提前放入世界标志
     */
    private boolean prePutWorldMatchFlag = true;

    /**
     * 加入世界匹配查询大小
     */
    private int joinWorldMatchCycleQuerySize = 1;

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


    public int getTxStressTest() {
        return txStressTest;
    }

    public void setTxStressTest(int txStressTest) {
        this.txStressTest = txStressTest;
    }

    /**
     * 是否是压测模式
     *
     * @return
     */
    public boolean isTxStressTest() {
        return this.txStressTest != 0;
    }

    public int getSession_prcoss_message_max_size() {
        return session_prcoss_message_max_size;
    }

    public void setSession_prcoss_message_max_size(
            int session_prcoss_message_max_size) {
        this.session_prcoss_message_max_size = session_prcoss_message_max_size;
    }

    public int getRoomUpdateMaxThreads() {
        return roomUpdateMaxThreads;
    }

    public void setRoomUpdateMaxThreads(int roomUpdateMaxThreads) {
        this.roomUpdateMaxThreads = roomUpdateMaxThreads;
    }

    public int getRoomSemaphoreMaxSize() {
        return roomSemaphoreMaxSize;
    }

    public void setRoomSemaphoreMaxSize(int roomSemaphoreMaxSize) {
        this.roomSemaphoreMaxSize = roomSemaphoreMaxSize;
    }

    public int getRoomUpdateSingleUseTimeMaxSize() {
        return roomUpdateSingleUseTimeMaxSize;
    }

    public void setRoomUpdateSingleUseTimeMaxSize(int roomUpdateSingleUseTimeMaxSize) {
        this.roomUpdateSingleUseTimeMaxSize = roomUpdateSingleUseTimeMaxSize;
    }


    public int getRoomUpdateCoreThreads() {
        return roomUpdateCoreThreads;
    }

    public void setRoomUpdateCoreThreads(int roomUpdateCoreThreads) {
        this.roomUpdateCoreThreads = roomUpdateCoreThreads;
    }

    public int getRoomUpdateRefreshDistanceTime() {
        return roomUpdateRefreshDistanceTime;
    }

    public void setRoomUpdateRefreshDistanceTime(int roomUpdateRefreshDistanceTime) {
        this.roomUpdateRefreshDistanceTime = roomUpdateRefreshDistanceTime;
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

    public int getSessionUpdateProduceThreadsSize() {
        return sessionUpdateProduceThreadsSize;
    }

    public void setSessionUpdateProduceThreadsSize(
            int sessionUpdateProduceThreadsSize) {
        this.sessionUpdateProduceThreadsSize = sessionUpdateProduceThreadsSize;
    }

    public int getSessionUpdateConsumeThreadsSize() {
        return sessionUpdateConsumeThreadsSize;
    }

    public void setSessionUpdateConsumeThreadsSize(
            int sessionUpdateConsumeThreadsSize) {
        this.sessionUpdateConsumeThreadsSize = sessionUpdateConsumeThreadsSize;
    }

    public int getRoomUpdateProduceThreadsSize() {
        return roomUpdateProduceThreadsSize;
    }

    public void setRoomUpdateProduceThreadsSize(int roomUpdateProduceThreadsSize) {
        this.roomUpdateProduceThreadsSize = roomUpdateProduceThreadsSize;
    }

    public int getRoomUpdateConsumeThreadsSize() {
        return roomUpdateConsumeThreadsSize;
    }

    public void setRoomUpdateConsumeThreadsSize(int roomUpdateConsumeThreadsSize) {
        this.roomUpdateConsumeThreadsSize = roomUpdateConsumeThreadsSize;
    }

    public int getQueueMessageExecutorSize() {
        return queueMessageExecutorSize;
    }

    public void setQueueMessageExecutorSize(int queueMessageExecutorSize) {
        this.queueMessageExecutorSize = queueMessageExecutorSize;
    }

    public int getSessionUpdateSingleUseTimeMaxSize() {
        return sessionUpdateSingleUseTimeMaxSize;
    }

    public void setSessionUpdateSingleUseTimeMaxSize(
            int sessionUpdateSingleUseTimeMaxSize) {
        this.sessionUpdateSingleUseTimeMaxSize = sessionUpdateSingleUseTimeMaxSize;
    }

    public boolean isQueueDispatchFlag() {
        return queueDispatchFlag;
    }

    public void setQueueDispatchFlag(boolean queueDispatchFlag) {
        this.queueDispatchFlag = queueDispatchFlag;
    }

    public boolean isCommonResultCacheFlag() {
        return commonResultCacheFlag;
    }

    public void setCommonResultCacheFlag(boolean commonResultCacheFlag) {
        this.commonResultCacheFlag = commonResultCacheFlag;
    }


    public int getSessionAddProductNullSleepTime() {
        return sessionAddProductNullSleepTime;
    }

    public void setSessionAddProductNullSleepTime(int sessionAddProductNullSleepTime) {
        this.sessionAddProductNullSleepTime = sessionAddProductNullSleepTime;
    }

    public int getRoomAddProductNullSleepTime() {
        return roomAddProductNullSleepTime;
    }

    public void setRoomAddProductNullSleepTime(int roomAddProductNullSleepTime) {
        this.roomAddProductNullSleepTime = roomAddProductNullSleepTime;
    }

    public int getSessionUpdateAllUseTimeMinSize() {
        return sessionUpdateAllUseTimeMinSize;
    }

    public void setSessionUpdateAllUseTimeMinSize(int sessionUpdateAllUseTimeMinSize) {
        this.sessionUpdateAllUseTimeMinSize = sessionUpdateAllUseTimeMinSize;
    }

    public int getRoomUpdateAllUseTimeMinSize() {
        return roomUpdateAllUseTimeMinSize;
    }

    public void setRoomUpdateAllUseTimeMinSize(int roomUpdateAllUseTimeMinSize) {
        this.roomUpdateAllUseTimeMinSize = roomUpdateAllUseTimeMinSize;
    }

    public int getTcpHeartKeepAliveTime() {
        return tcpHeartKeepAliveTime;
    }

    public void setTcpHeartKeepAliveTime(int tcpHeartKeepAliveTime) {
        this.tcpHeartKeepAliveTime = tcpHeartKeepAliveTime;
    }

    public int getSessionUpdateAllUseSleepTime() {
        return sessionUpdateAllUseSleepTime;
    }

    public void setSessionUpdateAllUseSleepTime(int sessionUpdateAllUseSleepTime) {
        this.sessionUpdateAllUseSleepTime = sessionUpdateAllUseSleepTime;
    }

    public int getRoomUpdateAllUseSleepTime() {
        return roomUpdateAllUseSleepTime;
    }

    public void setRoomUpdateAllUseSleepTime(int roomUpdateAllUseSleepTime) {
        this.roomUpdateAllUseSleepTime = roomUpdateAllUseSleepTime;
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

    public boolean isPrePutWorldMatchFlag() {
        return prePutWorldMatchFlag;
    }

    public void setPrePutWorldMatchFlag(boolean prePutWorldMatchFlag) {
        this.prePutWorldMatchFlag = prePutWorldMatchFlag;
    }

    public int getJoinWorldMatchCycleQuerySize() {
        return joinWorldMatchCycleQuerySize;
    }

    public void setJoinWorldMatchCycleQuerySize(int joinWorldMatchCycleQuerySize) {
        this.joinWorldMatchCycleQuerySize = joinWorldMatchCycleQuerySize;
    }

    public int getCommunicationTcpHeartKeepAliveTime() {
        return communicationTcpHeartKeepAliveTime;
    }

    public void setCommunicationTcpHeartKeepAliveTime(int communicationTcpHeartKeepAliveTime) {
        this.communicationTcpHeartKeepAliveTime = communicationTcpHeartKeepAliveTime;
    }

}
