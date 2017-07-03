package com.snowcattle.game.message.logic.tcp.online.server;

import com.snowcattle.game.common.annotation.MessageCommandAnnotation;
import com.snowcattle.game.common.exception.CodecException;
import com.snowcattle.game.message.auto.tcp.online.server.OnlineTCPServerProBuf;
import com.snowcattle.game.service.message.AbstractNetProtoBufTcpMessage;
import com.snowcattle.game.service.message.command.MessageCommandIndex;

/**
 * Created by jiangwenping on 17/2/21.
 */

@MessageCommandAnnotation(command = MessageCommandIndex.ONLINE_LOGIN_TCP_SERVER_MESSAGE)
public class OnlineLoginServerTcpMessage extends AbstractNetProtoBufTcpMessage {

    private long playerId;
    private int tocken;

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
