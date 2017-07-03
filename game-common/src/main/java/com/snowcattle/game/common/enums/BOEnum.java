package com.snowcattle.game.common.enums;

/**
 * Created by jwp on 2017/2/4.
 */
/**
 * @author C172
 * BO 服务器对象类型
 */
public enum BOEnum {

    //网关
    PROXY(1),
    //世界
    WORLD(2),
    //游戏
    GAME(3),
    //db
    DB(4),
    ;
    private int boId;

    BOEnum(int boId) {
        this.boId = boId;
    }

    public int getBoId() {
        return boId;
    }

    public void setBoId(int boId) {
        this.boId = boId;
    }
}