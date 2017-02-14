config.version="0.2.0.1";
//房间等待掉线时间(ms)
config.roomPlayerDisconnectTime=60000;
//房间生命周期(s)
config.roomLifcCycleTime=60 * 60 * 2;
//每秒钟处理消息数量
config.session_prcoss_message_max_size=10;
config.globalUpdateFlag=true;
config.globalUpdateProduceThreadsSize=1;
config.globalUpdateConsumeThreadsSize=10;
config.globalUpdateAllUseTimeMinSize=600;
config.globalUpdateAllUseSleepTime=1;
config.communicationTcpHeartKeepAliveTime=60*60*24;
