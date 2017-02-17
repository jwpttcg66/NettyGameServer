package com.wolf.shoot.net.message.logic.tcp.online;

import com.wolf.shoot.common.annotation.MessageCommandAnnotation;
import com.wolf.shoot.net.message.MessageCommands;
import com.wolf.shoot.net.message.AbstractAbstractNetProtoBufMessage;
import com.wolf.shoot.net.message.auto.tcp.online.OnlineTCPProBuf;

/**
 * Created by jiangwenping on 17/2/8.
 */
@MessageCommandAnnotation(command = MessageCommands.ONLINE_HEART_MESSAGE)
public class OnlineHeartMessageAbstract extends AbstractAbstractNetProtoBufMessage {

    private int id;

    public OnlineHeartMessageAbstract(){
        setCmd(MessageCommands.ONLINE_HEART_MESSAGE.command_id);
    }

    @Override
    public void decoderNetProtoBufMessageBody() throws Exception {
        byte[] bytes = getNetMessageBody().getBytes();
        OnlineTCPProBuf.OnlineHeartTCPProBuf req = OnlineTCPProBuf.OnlineHeartTCPProBuf.parseFrom(bytes);
        setId(req.getId());
    }

    @Override
    public void release() {

    }

    @Override
    public void encodeNetProtoBufMessageBody() throws Exception {
        OnlineTCPProBuf.OnlineHeartTCPProBuf.Builder builder = OnlineTCPProBuf.OnlineHeartTCPProBuf.newBuilder();
        builder.setId(getId());
        byte[] bytes = builder.build().toByteArray();
        getNetProtoBufMessageBody().setBytes(bytes);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
