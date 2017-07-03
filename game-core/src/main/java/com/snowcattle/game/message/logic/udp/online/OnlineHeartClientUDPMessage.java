package com.snowcattle.game.message.logic.udp.online;

import com.snowcattle.game.common.annotation.MessageCommandAnnotation;
import com.snowcattle.game.message.auto.udp.online.OnlineUDPProBuf;
import com.snowcattle.game.service.message.AbstractNetProtoBufUdpMessage;
import com.snowcattle.game.service.message.command.MessageCommandIndex;

/**
 * Created by jwp on 2017/2/16.
 */
@MessageCommandAnnotation(command = MessageCommandIndex.ONLINE_HEART_CLIENT_UDP_MESSAGE)
public class OnlineHeartClientUDPMessage extends AbstractNetProtoBufUdpMessage {

    private int id;

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

