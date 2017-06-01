package com.snowcattle.game.extend;

import com.snowcattle.game.bootstrap.GameServer;

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
