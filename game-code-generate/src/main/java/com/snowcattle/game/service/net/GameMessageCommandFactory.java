package com.snowcattle.game.service.net;

import com.snowcattle.game.service.message.command.MessageCommand;
import com.snowcattle.game.service.message.command.MessageCommandFactory;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 2017/5/10.
 */
@Service
public class GameMessageCommandFactory extends MessageCommandFactory {

    public MessageCommand[] getAllCommands(){
        MessageCommand[] messageCommands = super.getAllCommands();
        GameMessageCommandEnum[] set = GameMessageCommandEnum.values();
        MessageCommand[] gameMessageCommands = new MessageCommand[set.length];

        for(int i = 0; i < set.length; ++i) {
            GameMessageCommandEnum gameMessageCommandEnum = set[i];
            MessageCommand messageCommand = new MessageCommand(gameMessageCommandEnum.command_id, gameMessageCommandEnum.bo_id, gameMessageCommandEnum.is_need_filter);
            gameMessageCommands[i] = messageCommand;
        }
        MessageCommand[] result = new MessageCommand[messageCommands.length + gameMessageCommands.length];
        System.arraycopy(messageCommands, 0, result, 0, messageCommands.length);
        System.arraycopy(gameMessageCommands, 0, result, messageCommands.length, gameMessageCommands.length);
        return result;
    }
}