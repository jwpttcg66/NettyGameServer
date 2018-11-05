package com.snowcattle.game.common.lookuptest;

//import GamePlayerLoopUpService;

import com.snowcattle.game.logic.player.GamePlayer;
import com.snowcattle.game.service.lookup.GamePlayerLoopUpService;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiangwenping on 17/2/13.
 */
public final class lookup {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> sessions = new ConcurrentHashMap<String, String>();
        String t = "123";
        sessions.put(t, t);
        String ts = "123";
        System.out.println(sessions.get(ts));
        GamePlayerLoopUpService gamePlayerLoopUpService = new GamePlayerLoopUpService();
        long playerId = 6666;
        int tocken = 3;
        GamePlayer gamePlayer = new GamePlayer(null, playerId, tocken);
        gamePlayerLoopUpService.addT(gamePlayer);
        GamePlayer result = gamePlayerLoopUpService.lookup(playerId);
        System.out.println(result);
        gamePlayerLoopUpService.clear();
    }

}
