package com.wolf.shoot.service.net.message.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangwenping on 17/2/20.
 */
public class MesageCommandsBuilder {

    private Map<Integer, MessageCommands> messageCommandMap = new HashMap<Integer, MessageCommands>();

    public MessageCommands buildMessageCommands(int commandId, int boId, boolean is_need_filter, boolean is_common ){
        MessageCommands messageCommands =  new MessageCommands(commandId, boId, is_need_filter, is_common);
        messageCommandMap.put(commandId, messageCommands);
        return messageCommands;
    }

    public MessageCommands getMessageCommands(int cmd){
        return this.messageCommandMap.get(cmd);
    }
}
