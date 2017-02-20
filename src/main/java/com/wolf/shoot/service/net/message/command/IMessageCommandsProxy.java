package com.wolf.shoot.service.net.message.command;

/**
 * Created by jiangwenping on 17/2/20.
 */
public interface IMessageCommandsProxy {
    public String getMessageCommandName(int commandId);
    public MessageCommands getMessageCommand(int commandId);
    public int getBoIdByCommandId(int commandId);
}
