package com.wolf.shoot.service.net.message.command;

import com.wolf.shoot.common.constant.BOConst;

/**
 * Created by jiangwenping on 17/2/20.
 */
public enum MessageCommandEnum {

    ONLINE_HEART_MESSAGE(1, BOConst.BO_WORLD,false, false),
    COMMON_ERROP_RESPONSE_WITH_COMMAND_MESSAGE(2, BOConst.BO_WORLD, false, true),
    COMMON_RESPONSE_WITH_COMMAND_MESSAGE(3, BOConst.BO_WORLD, false, true),
    ONLINE_LOGIN_MESSAGE(4, BOConst.BO_WORLD, false, false),

    ONLINE_HEART_UDP_MESSAGE(20003, BOConst.BO_WORLD,false, false),
    ;
    /**
     * 协议号
     */
    public final int command_id;

    /**
     * boconstant的id
     */
    public final int bo_id;

    /**
     * 是否需要过滤
     */
    private final boolean is_need_filter;

    /**
     * 通用消息
     */
    private final boolean is_common;

    MessageCommandEnum(int commandId, int boId, boolean is_need_filter, boolean is_common) {
        this.command_id = commandId;
        this.bo_id = boId;
        this.is_need_filter = is_need_filter;
        this.is_common = is_common;
    }



    public int getCommand_id() {
        return command_id;
    }

    public int getBo_id() {
        return bo_id;
    }

    public boolean is_need_filter() {
        return is_need_filter;
    }

    public boolean is_common() {
        return is_common;
    }
}
