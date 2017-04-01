package com.wolf.shoot.service.rpc.server.zookeeper;

import com.wolf.shoot.common.constant.BOEnum;

/**
 * Created by jiangwenping on 17/3/31.
 */
public enum ZooKeeperNodeBoEnum {
    WORLD(BOEnum.WORLD, "world_registry_adress"),
    GAME(BOEnum.GAME, "game_registry_adress"),
    DB(BOEnum.DB, "db_registry_adress"),
    ;
    private BOEnum boEnum;
    private String registryAdress;

    ZooKeeperNodeBoEnum(BOEnum boEnum, String registryAdress) {
        this.boEnum = boEnum;
        this.registryAdress = registryAdress;
    }

    public BOEnum getBoEnum() {
        return boEnum;
    }

    public void setBoEnum(BOEnum boEnum) {
        this.boEnum = boEnum;
    }

    public String getRegistryAdress() {
        return registryAdress;
    }

    public void setRegistryAdress(String registryAdress) {
        this.registryAdress = registryAdress;
    }
}
