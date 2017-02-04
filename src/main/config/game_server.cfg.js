/*
 * Server基本信息
 */
config.serverType=2
config.debug=1;
config.charset="UTF-8";
config.version="0.2.0.1";
config.resourceVersion="0.2.0.1";
config.dbVersion="0.2.0.1";
config.regionId="1";
config.serverGroupId="1";
config.serverIndex=1;
config.serverId="8001";
config.bindIp="0.0.0.0";
config.ports="7090";
config.serverName="OnLine";
config.serverHost="0.0.0.0";
config.ioProcessor=1;

config.language="zh_CN";
config.i18nDir="i18n";

config.dbInitType=0;
config.dbConfigName="game_server_hibernate.cfg.xml,game_server_hibernate_query.xml";
config.gameServerCount=1;

config.maxOnlineUsers=2000;
config.openNewerGuide=1;

config.gameId="A5";
config.stressTest=0;

/*
 * iso充值环境
 */
config.appleStoreType="buy";

/**充值 人人豆兑换元宝的比率是1：10 ***/
config.chargeMM2DiamondRate =10;

config.asyncMinThreads=0;
config.asyncMaxThreads=10;

config.max_room_num= 1000;
config.developModel= 1;

/**异步通讯接口***/
config.communicationPort=10000
config.communicationMaxThreadPoolSize=140
config.communicationHandlerMaxThreadPoolSize=280
config.communicationMaxWriteIntervalTime=60*60*1000

