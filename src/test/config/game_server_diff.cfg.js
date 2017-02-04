config.version="0.2.0.1";
//房间等待掉线时间(ms)
config.roomPlayerDisconnectTime=60000;
//房间生命周期(s)
config.roomLifcCycleTime=60 * 60 * 2;
//压力测试标志
config.txStressTest=0;
//每秒钟处理消息数量
config.session_prcoss_message_max_size=10;

/** 房间更新最小线程数量*/
config.roomUpdateCoreThreads = 500;
/**房间更新最大线程数量*/
config.roomUpdateMaxThreads = 6000;
/**房间更新最大信号量*/
config.roomSemaphoreMaxSize = 500;
config.roomUpdateRefreshDistanceTime=1000;

config.sessionUpdateProduceThreadsSize=2;
config.sessionUpdateConsumeThreadsSize=10;

config.roomUpdateProduceThreadsSize=2;
config.roomUpdateConsumeThreadsSize=20;

config.sessionUpdateSingleUseTimeMaxSize=200;
/**房间更新单次最多占用时间(ms)*/
config.roomUpdateSingleUseTimeMaxSize = 200;

config.queueDispatchFlag=false;
config.commonResultCacheFlag=true;
config.sessionAddProductNullSleepTime = 1;
config.roomAddProductNullSleepTime = 1;
config.tcpHeartKeepAliveTime=60;
config.sessionUpdateAllUseTimeMinSize=600;
config.roomUpdateAllUseTimeMinSize=600;
config.sessionUpdateAllUseSleepTime=1;
config.roomUpdateAllUseSleepTime=1;

config.globalUpdateFlag=true;
config.globalUpdateProduceThreadsSize=1;
config.globalUpdateConsumeThreadsSize=10;
config.globalUpdateAllUseTimeMinSize=600;
config.globalUpdateAllUseSleepTime=1;

config.prePutWorldMatchFlag=false;
config.communicationTcpHeartKeepAliveTime=60*60*24;

config.playerMatchThreadSize=10;
config.teamMatchThreadSize=10;
config.team_pk_match_open=false;
config.happy_pk_open=true;

config.happyMatchThreadSize=10;

//config.playerMatchThreadSize=1;
//config.teamMatchThreadSize=1;


