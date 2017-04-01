package com.wolf.shoot.service.rpc.server;

import com.wolf.shoot.common.constant.BOEnum;

/**
 * Created by jiangwenping on 17/3/31.
 */
public enum ZooKeeperNodeBoEnum {
    world(BOEnum.WORLD, "world_registry_adress"),
    game(BOEnum.GAME, "game_registry_adress"),
    db(BOEnum.DB, "db_registry_adress"),
    ;
    private BOEnum boEnum;
    private String registryAdress;

    ZooKeeperNodeBoEnum(BOEnum boEnum, String registryAdress) {
        this.boEnum = boEnum;
        this.registryAdress = registryAdress;
    }
}
