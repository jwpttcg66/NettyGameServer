package com.snowcattle.game.service.message;

/**
 * Created by jiangwenping on 2017/9/28.
 * http消息头部
 */
public class NetHttpMessageHead extends NetMessageHead {

    private long playerId;
    private String tocken="";

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getTocken() {
        return tocken;
    }

    public void setTocken(String tocken) {
        this.tocken = tocken;
    }
}
