package com.wolf.shoot.service.lookuptest;

import com.wolf.shoot.logic.player.GamePlayer;
import com.wolf.shoot.service.lookup.GamePlayerLoopUpService;

/**
 * Created by jiangwenping on 17/2/13.
 */
public class lookup {

    public static void main(String[] args) {
//        ConcurrentHashMap<String, String> sessions = new ConcurrentHashMap<String, String>();
//        String t = new String("123");
//        sessions.put(t, t);
//        String ts = new String("123");
//        System.out.println(sessions.get(ts));
        GamePlayerLoopUpService gamePlayerLoopUpService = new GamePlayerLoopUpService();
        long playerId = 6666;
        int tocken = 3;
        GamePlayer gamePlayer = new GamePlayer(null, playerId, tocken);
        gamePlayerLoopUpService.addT(gamePlayer);
        GamePlayer result = gamePlayerLoopUpService.lookup(playerId);
        System.out.println(result);
    }

}
