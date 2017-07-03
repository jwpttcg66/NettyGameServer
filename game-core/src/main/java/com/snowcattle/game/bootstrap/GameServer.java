package com.snowcattle.game.bootstrap;

import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.util.MemUtils;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.net.tcp.AbstractServerService;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.bootstrap.manager.GlobalManager;
import com.snowcattle.game.bootstrap.manager.ServerServiceManager;
import com.snowcattle.game.service.net.LocalNetService;
import org.slf4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
public class GameServer extends AbstractServerService {


    /** 日志 */
    public static final Logger logger = Loggers.gameLogger;

    protected GlobalManager globalManager;
    protected LocalNetService localNetService;

    /**
     * @param
     *
     */
    public GameServer() {
        super(ServerServiceManager.SERVICE_ID_ROOT);
        this.globalManager = new GlobalManager();
        this.localNetService = new LocalNetService();
    }

    /**
     * 初始化各种资源和服务
     *
     * @throws Exception
     */
    public void init(String configFile) throws Exception {
        logger.info("Begin to initialize spring");
        initSpring();
        logger.info("Begin to initialize GlobalManager");
        globalManager.init(configFile);
        logger.info("GlobalManager initialized");
        this.initServer();
    }

    public void initSpring()throws Exception{
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/*.xml"});
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook(classPathXmlApplicationContext)));
    }

    private void initServer() {

    }

    /**
     * 启动服务器
     *
     * @throws Exception
     */
    public void start() throws Exception {

        logger.info("Begin to start GlobalManager");
        globalManager.start();
        logger.info("GlobalManager started");
        LocalMananger.getInstance().add(localNetService, LocalNetService.class);
        localNetService.startup();
        logger.info("local net Server started");
        LocalMananger.getInstance().create(GamerServerStartFinishedService.class, GamerServerStartFinishedService.class);
        logger.info("GamerServerStartFinishedService started");
        addShutdownHook();
        GameServerRuntime.setOpenOn();
    }

    public void addShutdownHook(){
        // 注册停服监听器，用于执行资源的销毁等停服时的处理工作
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                logger.info("Begin to shutdown Game Server ");
                // 设置GameServer关闭状态
                GameServerRuntime.setShutdowning();
                logger.info("GameServerRuntime shutdown:ok");
                ServerStatusLog.getDefaultLog().logStoppping();
                logger.info("ServerStatusLog shutdown:ok");
                // 关闭游戏连接服务
                try {
                    LocalNetService localNetService = LocalMananger.getInstance().get(LocalNetService.class);
                    localNetService.shutdown();
                    logger.info("tcp server shutdown:ok");
                    globalManager.stop();
                    logger.info("GlobalManager.shutdown:ok");
                    GamerServerStartFinishedService gamerServerStartFinishedService = LocalMananger.getInstance().get(GamerServerStartFinishedService.class);
                    gamerServerStartFinishedService.shutdown();
                    logger.info("GamerServerStartFinishedService.shutdown:ok");
                } catch (Exception e) {
                    logger.error("close connector service exception:", e);
                } catch (Error e) {
                    logger.error("close connector service error:", e);
                } catch (Throwable e) {
                    logger.error("close connector service throwable:", e);
                }

                ServerStatusLog.getDefaultLog().logStopped();
                // 注销性能收集
                logger.info("Game Server shutdowned");
            }
        });
    }

    public static void main(String[] args) {;
        GameServer server = new GameServer();
        server.startServer();
    }

    public void startServer(){
        logger.info("Starting Game Server");
        logger.info(MemUtils.memoryInfo());
        String configFile = GlobalConstants.ConfigFile.GAME_SERVER_CONIFG;
        try {
            /**
             * 程序初始化程序缓存模块
             */

            ServerStatusLog.getDefaultLog().logStarting();
            init(configFile);
            start();
            ServerStatusLog.getDefaultLog().logRunning();
        } catch (Exception e) {
            logger.error("Failed to start server", e);
            System.err.println(e);
            ServerStatusLog.getDefaultLog().logStartFail();
            System.exit(1);
            return;
        }
        logger.info(MemUtils.memoryInfo());
        logger.info("Server started");
    }

    public GlobalManager getGlobalManager() {
        return globalManager;
    }

    public void setGlobalManager(GlobalManager globalManager) {
        this.globalManager = globalManager;
    }

    public LocalNetService getLocalNetService() {
        return localNetService;
    }

    public void setLocalNetService(LocalNetService localNetService) {
        this.localNetService = localNetService;
    }
}
