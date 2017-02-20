package com.wolf.shoot.service.net.message.command;

/**
 * Created by jiangwenping on 17/2/20.
 */
public interface IMessageCommandProxy {
    public String getMessageCommandName(int commandId);
    public MessageCommand getMessageCommand(int commandId);
    public int getBoIdByCommandId(int commandId);
}
