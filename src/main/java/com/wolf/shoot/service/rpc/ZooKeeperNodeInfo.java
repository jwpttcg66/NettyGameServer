package com.wolf.shoot.service.rpc;

import com.wolf.shoot.common.constant.BOEnum;

/**
 * Created by jiangwenping on 17/3/31.
 */
public enum  ZooKeeperNodeInfo {
    world(BOEnum.WORLD, "world_registryAdress"),
    game(BOEnum.GAME, "game_registryAdress"),
    db(BOEnum.DB, "db_registryAdress"),
    ;
    private BOEnum boEnum;
    private String registryAdress;

    ZooKeeperNodeInfo(BOEnum boEnum, String registryAdress) {
        this.boEnum = boEnum;
        this.registryAdress = registryAdress;
    }
}
