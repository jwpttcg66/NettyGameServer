package com.wolf.shoot.extend;

import com.wolf.shoot.bootstrap.GameServer;
import com.wolf.shoot.bootstrap.GameServerRuntime;
import com.wolf.shoot.bootstrap.GamerServerStartFinishedService;
import com.wolf.shoot.bootstrap.ServerStatusLog;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.util.MemUtils;
import com.wolf.shoot.manager.GlobalManager;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.net.LocalNetService;

/**
 * Created by jwp on 2017/5/5.
 */
public class GameServerEx extends GameServer{

    public static void main(String[] args) {
        GameServerEx gameServerEx = new GameServerEx();
        GlobalManagerEx globalManagerEx = new GlobalManagerEx();
        gameServerEx.setGlobalManager(globalManagerEx);
        gameServerEx.startServer();
    }
}
