package com.wolf.shoot.service.net.message.command;

/**
 * Created by jiangwenping on 17/2/20.
 */
public class MessageCommandProxy implements IMessageCommandProxy {

    private MesageCommandBuilder mesageCommandBuilder;

    public MessageCommandProxy(MesageCommandBuilder mesageCommandBuilder) {
        this.mesageCommandBuilder = mesageCommandBuilder;
    }

    @Override
    public String getMessageCommandName(int commandId) {
        MessageCommand messageCommand = mesageCommandBuilder.getMessageCommands(commandId);
        if(messageCommand != null){
            return messageCommand.toString();
        }
        return null;
    }

    public MessageCommand getMessageCommand(int commandId) {
        return  mesageCommandBuilder.getMessageCommands(commandId);

    }

    public int getBoIdByCommandId(int commandId) {
        MessageCommand messageCommand = mesageCommandBuilder.getMessageCommands(commandId);
        if(messageCommand != null){
            return messageCommand.getBo_id();
        }
        return -1;
    }

}
