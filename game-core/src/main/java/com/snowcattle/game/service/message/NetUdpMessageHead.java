package com.snowcattle.game.service.message;

/**
 * Created by jiangwenping on 17/2/20.
 * udp需要加入playerId跟tocken
 */
public class NetUdpMessageHead  extends NetMessageHead{
    private long playerId;
    private int tocken;

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getTocken() {
        return tocken;
    }

    public void setTocken(int tocken) {
        this.tocken = tocken;
    }
}
