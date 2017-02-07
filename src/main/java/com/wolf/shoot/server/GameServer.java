package com.wolf.shoot.server;

import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.util.MemUtils;
import com.wolf.shoot.manager.Globals;
import com.wolf.shoot.service.ServerServiceManager;
import com.wolf.shoot.service.net.AbstractServerService;
import org.slf4j.Logger;

/**
 * Created by jiangwenping on 17/2/6.
 *
 /**
 *
 *                 _oo0oo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  0\  =  /0
                ___/`---'\___
              .' \\|     |-- '.
             / \\|||  :  |||-- \
            / _||||| -:- |||||- \
           |   | \\\  -  --/ |   |
           | \_|  ''\---/''  |_/ |
           \  .-\__  '-'  ___/-. /
         ___'. .'  /--.--\  `. .'___
      ."" '<  `.___\_<|>_/___.' >' "".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `_.   \_ __\ /__ _/   .-` /  /
 =====`-.____`.___ \_____/___.-`___.-'=====
                   `=---='


 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

              佛祖保佑    永无BUG
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *
 *
 * 负责游戏服务器的初始化，基础资源的加载，服务器进程的启动
 *
 */
public class GameServer extends AbstractServerService{


    /** 日志 */
    private static final Logger logger = Loggers.gameLogger;

    /**
     * @param cfgPath
     *            主配置文件路径
     */
    public GameServer() {
        super(ServerServiceManager.SERVICE_ID_ROOT);
    }

    /**
     * 初始化各种资源和服务
     *
     * @throws Exception
     */
    public void init(String configFile) throws Exception {
        logger.info("Begin to initialize Globals");
        Globals.init(configFile);
//        ServerServiceManager.getInstance().onReady();
//        CommunicationServerService.getInstance().onReady();
        logger.info("Globals initialized");
        this.initServer();
    }


    private void initServer() {

    }

    /**
     * 启动服务器
     *
     * @throws IOException
     */
    public void start() throws Exception {

        logger.info("Begin to start Globals");
        logger.info("Globals started");

        // 注册停服监听器，用于执行资源的销毁等停服时的处理工作
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                logger.info("Begin to shutdown Game Server ");
                // 设置GameServer关闭状态
                GameServerRuntime.setShutdowning();
                ServerStatusLog.getDefaultLog().logRunning();
                // 关闭游戏连接服务
                try {
//                    ServerService.getInstance().onDown();
//                    logger.info("gameCommService.shutdown:ok");
//
//                    GameServerConfigServiceEx gameServerConfigService = (GameServerConfigServiceEx)LocalMananger.getInstance().getGameServerConfigService();
//                    GameServerConfig cfg = gameServerConfigService.getGameServerConfig();
//                    if(gameServerConfigService.getA5GameDynamicPropertiesConfig().getProperty(DynamicPropertiesEnum.COMMUNICATION_START_UP_OPEN.toString().toLowerCase(), true)) {
//                        CommunicationServerService.getInstance().onDown();
//                    }
//                    logger.info("CommunicationServerService.shutdown:ok");
//
//                    Globals.stop();
                    logger.info("Globals.shutdown:ok");
                } catch (Exception e) {
                    logger.error("close connector service exception:", e);
                } catch (Error e) {
                    logger.error("close connector service error:", e);
                }

                ServerStatusLog.getDefaultLog().logStopped();
                // 注销性能收集
                logger.info("Game Server shutdowned");
            }
        });
        GameServerRuntime.setOpenOn();
    }

    public static void main(String[] args) {
        logger.info("Starting Game Server");
        logger.info(MemUtils.memoryInfo());
        String configFile = GlobalConstants.ConfigFile.GAME_SERVER_CONIFG;
        String admCmd = "";
        String[] admArgs = null;

        if(args != null && args.length == 1){
            configFile = args[0];
        }

        if (args != null && args.length >= 2){
            admCmd = args[1];
            if (args.length >= 3) {
                admArgs = new String[args.length - 2];
                for (int i=0; i<admArgs.length; ++i) {
                    admArgs[i] = args[i + 2];
                }
            }
        }

        if (admCmd.isEmpty()) {
            try {
                /**
                 * 程序初始化程序缓存模块
                 */

                ServerStatusLog.getDefaultLog().logStarting();
//                IoBuffer.setUseDirectBuffer(false);
//                IoBuffer.setAllocator(new SimpleBufferAllocator());
                GameServer server = new GameServer();

                server.init(configFile);
                server.start();
                ServerStatusLog.getDefaultLog().logRunning();
            } catch (Exception e) {
                logger.error("Failed to start server", e);
                System.err.println(e);
                ServerStatusLog.getDefaultLog().logStartFail();
                System.exit(1);
                return;
            }
            logger.info(MemUtils.memoryInfo());

//            GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getGameServerConfigService();
//            if(gameServerConfigService.getGameServerConfig().getServerType() == BOConst.BO_WORLD){
//                logger.info("World Server started");
//            }else {
//                logger.info("Game Server started");
//            }
        } else {
//            // Xiong 管理功能
//            Admin adm = Admin.make(configFile, admCmd);
//            adm.run(admArgs);
        }
    }
}
