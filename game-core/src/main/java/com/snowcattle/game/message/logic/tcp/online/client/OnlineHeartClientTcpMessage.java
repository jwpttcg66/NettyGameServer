package com.snowcattle.game.message.logic.tcp.online.client;

import com.snowcattle.game.common.annotation.MessageCommandAnnotation;
import com.snowcattle.game.message.auto.tcp.online.client.OnlineTCPClientProBuf;
import com.snowcattle.game.service.message.AbstractNetProtoBufTcpMessage;
import com.snowcattle.game.service.message.command.MessageCommandIndex;

/**
 * Created by jiangwenping on 17/2/8.
 */
@MessageCommandAnnotation(command = MessageCommandIndex.ONLINE_HEART_CLIENT_TCP_MESSAGE)
public class OnlineHeartClientTcpMessage extends AbstractNetProtoBufTcpMessage {

    private int id;

    @Override
    public void decoderNetProtoBufMessageBody() throws Exception {
        byte[] bytes = getNetMessageBody().getBytes();
        OnlineTCPClientProBuf.OnlineHeartTCPClientProBuf req = OnlineTCPClientProBuf.OnlineHeartTCPClientProBuf.parseFrom(bytes);
        setId(req.getId());
    }

    @Override
    public void release() {

    }

    @Override
    public void encodeNetProtoBufMessageBody() throws Exception {
        OnlineTCPClientProBuf.OnlineHeartTCPClientProBuf.Builder builder = OnlineTCPClientProBuf.OnlineHeartTCPClientProBuf.newBuilder();
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
