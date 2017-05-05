package com.wolf.shoot.extend;

import com.wolf.shoot.bootstrap.GameServer;
import com.wolf.shoot.bootstrap.GameServerRuntime;
import com.wolf.shoot.bootstrap.GamerServerStartFinishedService;
import com.wolf.shoot.bootstrap.ServerStatusLog;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.util.BeanUtil;
import com.wolf.shoot.common.util.MemUtils;
import com.wolf.shoot.manager.Globals;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.net.LocalNetService;

/**
 * Created by jwp on 2017/5/5.
 */
public class GameServerEx extends GameServer{

    public static void main(String[] args) {
        logger.info("Starting Game Server");
        logger.info(MemUtils.memoryInfo());
        String configFile = GlobalConstants.ConfigFile.GAME_SERVER_CONIFG;
        try {
            /**
             * 程序初始化程序缓存模块
             */

            ServerStatusLog.getDefaultLog().logStarting();
            GameServerEx server = new GameServerEx();
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

        logger.info("Server started");
    }

    /**
     * 启动服务器
     *
     * @throws Exception
     */
    public void start() throws Exception {

        logger.info("Begin to start Globals");
        Globals.start();
        logger.info("Globals started");

        LocalMananger.getInstance().create(LocalNetService.class, LocalNetService.class);
        logger.info("local net Server started");
        LocalMananger.getInstance().create(GamerServerStartFinishedService.class, GamerServerStartFinishedService.class);
        logger.info("GamerServerStartFinishedService started");

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
                    Globals.stop();
                    logger.info("Globals.shutdown:ok");
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
        GameServerRuntime.setOpenOn();
    }
}
