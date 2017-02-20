package com.wolf.shoot.service.net.message.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangwenping on 17/2/20.
 */
public class MesageCommandBuilder {

    private Map<Integer, MessageCommand> messageCommandMap = new HashMap<Integer, MessageCommand>();

    public MessageCommand buildMessageCommands(int commandId, int boId, boolean is_need_filter, boolean is_common ){
        MessageCommand messageCommand =  new MessageCommand(commandId, boId, is_need_filter, is_common);
        messageCommandMap.put(commandId, messageCommand);
        return messageCommand;
    }

    public MessageCommand getMessageCommands(int cmd){
        return this.messageCommandMap.get(cmd);
    }
}
