package com.snowcattle.game.message.logic.tcp.online.client;

import com.snowcattle.game.common.annotation.MessageCommandAnnotation;
import com.snowcattle.game.message.auto.tcp.online.client.OnlineTCPClientProBuf;
import com.snowcattle.game.service.message.AbstractNetProtoBufTcpMessage;
import com.snowcattle.game.service.message.command.MessageCommandIndex;

/**
 * Created by jiangwenping on 17/2/21.
 */
@MessageCommandAnnotation(command = MessageCommandIndex.ONLINE_LOGIN_TCP_CLIENT_MESSAGE)
public class OnlineLoginClientTcpMessage extends AbstractNetProtoBufTcpMessage {

    private int id;

    @Override
    public void decoderNetProtoBufMessageBody() throws Exception {
        byte[] bytes = getNetMessageBody().getBytes();
        OnlineTCPClientProBuf.OnlineLoginTCPClientProBuf req = OnlineTCPClientProBuf.OnlineLoginTCPClientProBuf.parseFrom(bytes);
        setId(req.getId());
    }

    @Override
    public void release() {

    }

    @Override
    public void encodeNetProtoBufMessageBody() throws Exception {
        OnlineTCPClientProBuf.OnlineLoginTCPClientProBuf.Builder builder = OnlineTCPClientProBuf.OnlineLoginTCPClientProBuf.newBuilder();
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

