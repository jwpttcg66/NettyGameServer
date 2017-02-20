package com.wolf.shoot.service.net.message.command;

/**
 * Created by jwp on 2017/2/4.
 */
public class MessageCommands
{
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

    MessageCommands(int commandId, int boId, boolean is_need_filter, boolean is_common) {
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