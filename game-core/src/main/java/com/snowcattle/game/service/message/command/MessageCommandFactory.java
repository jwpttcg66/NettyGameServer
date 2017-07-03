package com.snowcattle.game.service.message.command;

import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 2017/5/10.
 */
@Service
public class MessageCommandFactory {

    public MessageCommand[] getAllCommands(){
        MessageCommandEnum[] set = MessageCommandEnum.values();
        MessageCommand[] messageCommands = new MessageCommand[set.length];
        for(int i = 0; i< set.length; i++){
            MessageCommandEnum messageCommandEnum = set[i];
            MessageCommand messageCommand = new MessageCommand(messageCommandEnum.command_id, messageCommandEnum.bo_id, messageCommandEnum.is_need_filter);
            messageCommands[i] = messageCommand;
        }

        return messageCommands;
    }
}
