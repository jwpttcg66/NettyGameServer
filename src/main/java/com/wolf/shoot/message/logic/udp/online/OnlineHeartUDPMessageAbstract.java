package com.wolf.shoot.message.logic.udp.online;

import com.wolf.shoot.common.annotation.MessageCommandAnnotation;
import com.wolf.shoot.service.net.message.MessageCommands;
import com.wolf.shoot.service.net.message.AbstractNetProtoBufUDPMessage;
import com.wolf.shoot.message.auto.udp.online.OnlineUDPProBuf;

/**
 * Created by jwp on 2017/2/16.
 */
@MessageCommandAnnotation(command = MessageCommands.ONLINE_HEART_UDP_MESSAGE)
public class OnlineHeartUDPMessageAbstract extends AbstractNetProtoBufUDPMessage {

    private int id;

    public OnlineHeartUDPMessageAbstract(){
        setCmd(MessageCommands.ONLINE_HEART_UDP_MESSAGE.command_id);
    }

    @Override
    public void decoderNetProtoBufMessageBody() throws Exception {
        byte[] bytes = getNetMessageBody().getBytes();
        OnlineUDPProBuf.OnlineHeartUDPProBuf req = OnlineUDPProBuf.OnlineHeartUDPProBuf.parseFrom(bytes);
        setId(req.getId());
    }

    @Override
    public void release() {

    }

    @Override
    public void encodeNetProtoBufMessageBody() throws Exception {
        OnlineUDPProBuf.OnlineHeartUDPProBuf.Builder builder = OnlineUDPProBuf.OnlineHeartUDPProBuf.newBuilder();
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

