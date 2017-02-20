package com.wolf.shoot.message.logic.udp.online;

import com.wolf.shoot.common.annotation.MessageCommandAnnotation;
import com.wolf.shoot.message.auto.udp.online.OnlineUDPProBuf;
import com.wolf.shoot.service.net.message.AbstractNetProtoBufUdpMessage;
import com.wolf.shoot.service.net.message.command.MessageCommandIndex;

/**
 * Created by jwp on 2017/2/16.
 */
@MessageCommandAnnotation(command = MessageCommandIndex.ONLINE_HEART_UDP_MESSAGE)
public class OnlineHeartUDPMessage extends AbstractNetProtoBufUdpMessage {

    private int id;

    public OnlineHeartUDPMessage(){
        setCmd(MessageCommandIndex.ONLINE_HEART_UDP_MESSAGE);
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
        getNetMessageBody().setBytes(bytes);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

