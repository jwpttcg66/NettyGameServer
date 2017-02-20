package com.wolf.shoot.service.net.message.command;

/**
 * Created by jiangwenping on 17/2/20.
 */
public class MessageCommandsProxy implements IMessageCommandsProxy {

    private MesageCommandsBuilder mesageCommandsBuilder;

    public MessageCommandsProxy(MesageCommandsBuilder mesageCommandsBuilder) {
        this.mesageCommandsBuilder = mesageCommandsBuilder;
    }

    @Override
    public String getMessageCommandName(int commandId) {
        MessageCommands messageCommands = mesageCommandsBuilder.getMessageCommands(commandId);
        if(messageCommands != null){
            return messageCommands.toString();
        }
        return null;
    }

    public MessageCommands getMessageCommand(int commandId) {
        return  mesageCommandsBuilder.getMessageCommands(commandId);

    }

    public int getBoIdByCommandId(int commandId) {
        MessageCommands messageCommands = mesageCommandsBuilder.getMessageCommands(commandId);
        if(messageCommands != null){
            return messageCommands.getBo_id();
        }
        return -1;
    }

}
