package com.snowcattle.game.service.rpc.client;

import com.snowcattle.game.common.enums.BOEnum;

/**
 * Created by jiangwenping on 17/3/15.
 */
public class RpcContextHolderObject {

    private BOEnum boEnum;
    private int ServerId;

    public RpcContextHolderObject(BOEnum boEnum, int serverId) {
        this.boEnum = boEnum;
        ServerId = serverId;
    }

    public BOEnum getBoEnum() {
        return boEnum;
    }

    public void setBoEnum(BOEnum boEnum) {
        this.boEnum = boEnum;
    }

    public int getServerId() {
        return ServerId;
    }

    public void setServerId(int serverId) {
        ServerId = serverId;
    }
}
