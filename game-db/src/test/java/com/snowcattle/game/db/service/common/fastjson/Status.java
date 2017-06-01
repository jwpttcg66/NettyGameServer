package com.snowcattle.game.db.service.common.fastjson;

/**
 * Created by jiangwenping on 17/4/6.
 */
public enum Status {

    Ready(10),

    Completed(20);

    private int value;

    private Status(int value) {
        this.value = value;
    }

    public static Status create(String value) {
        return Status.valueOf(value);
    }

    public static int value(Status status){
        return status.value;
    }
}
