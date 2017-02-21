package com.wolf.shoot.message.logic.tcp.online.server;

import com.wolf.shoot.common.annotation.MessageCommandAnnotation;
import com.wolf.shoot.common.exception.CodecException;
import com.wolf.shoot.message.auto.tcp.online.server.OnlineTCPServerProBuf;
import com.wolf.shoot.service.net.message.AbstractNetProtoBufTcpMessage;
import com.wolf.shoot.service.net.message.command.MessageCommandIndex;

/**
 * Created by jiangwenping on 17/2/21.
 */

@MessageCommandAnnotation(command = MessageCommandIndex.ONLINE_LOGIN_TCP_SERVER_MESSAGE)
public class OnlineLoginServerTcpMessage extends AbstractNetProtoBufTcpMessage {

    private long playerId;
    private int tocken;

    public OnlineLoginServerTcpMessage() {
        setCmd(MessageCommandIndex.ONLINE_LOGIN_TCP_SERVER_MESSAGE);
    }

    @Override
    public void decoderNetProtoBufMessageBody() throws CodecException, Exception {

    }

    @Override
    public void release() throws CodecException {

    }

    @Override
    public void encodeNetProtoBufMessageBody() throws CodecException, Exception {
        OnlineTCPServerProBuf.OnlineHeartTCPServerProBuf.Builder builder = OnlineTCPServerProBuf.OnlineHeartTCPServerProBuf.newBuilder();
        builder.setPlayerId(playerId);
        builder.setTocken(tocken);
        byte[] bytes = builder.build().toByteArray();
        getNetMessageBody().setBytes(bytes);
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getTocken() {
        return tocken;
    }

    public void setTocken(int tocken) {
        this.tocken = tocken;
    }
}
