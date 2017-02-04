package com.wolf.shoot.net.message;

import com.wolf.shoot.constant.BOConst;

/**
 * Created by jwp on 2017/2/4.
 */
public enum MessageCommands
{
    TEST_MESSAGE(1, BOConst.BO_GAME,false, false),

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

    MessageCommands(int commandId, int boId, boolean is_need_filter, boolean is_common) {
        this.command_id = commandId;
        this.bo_id = boId;
        this.is_need_filter = is_need_filter;
        this.is_common = is_common;
    }

    public static String getMessageCommandName(int commandId) {
        MessageCommands commands = MessageCommands.TEST_MESSAGE;
        MessageCommands[] set = commands.values();
        for (int i = 0; i < set.length; i++) {
            MessageCommands tempCommand = set[i];
            if (tempCommand.command_id == commandId) {
                commands = tempCommand;
                break;
            }
        }
        return commands.name();

    }

    public static MessageCommands getMessageCommand(int commandId) {
        MessageCommands commands = MessageCommands.TEST_MESSAGE;
        MessageCommands[] set = commands.values();
        for (int i = 0; i < set.length; i++) {
            MessageCommands tempCommand = set[i];
            if (tempCommand.command_id == commandId) {
                commands = tempCommand;
                break;
            }
        }
        return commands;

    }

    public static int getBoIdByCommandId(int commandId) {
        MessageCommands commands = MessageCommands.TEST_MESSAGE;
        MessageCommands[] set = commands.values();
        for (int i = 0; i < set.length; i++) {
            MessageCommands tempCommand = set[i];
            if (tempCommand.command_id == commandId) {
                commands = tempCommand;
                break;
            }
        }
        return commands.bo_id;
    }

    public boolean isIs_need_filter() {
        return is_need_filter;
    }

    public boolean isIs_common() {
        return is_common;
    }

    public int getCommand_id() {
        return command_id;
    }
}