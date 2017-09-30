package com.snowcattle.game.message.logic.http.client;

import com.snowcattle.game.common.annotation.MessageCommandAnnotation;
import com.snowcattle.game.message.auto.http.client.OnlineClientHttpProBuf;
import com.snowcattle.game.message.auto.udp.online.OnlineUDPProBuf;
import com.snowcattle.game.service.message.AbstractNetProtoBufHttpMessage;
import com.snowcattle.game.service.message.AbstractNetProtoBufUdpMessage;
import com.snowcattle.game.service.message.command.MessageCommandIndex;

/**
 * Created by jiangwenping on 2017/9/30.
 */

@MessageCommandAnnotation(command = MessageCommandIndex.ONLINE_HEART_HTTP_CLIENT_MESSAGE)
public class OnlineHeartClientHttpMessage extends AbstractNetProtoBufHttpMessage {

    private int id;

    @Override
    public void decoderNetProtoBufMessageBody() throws Exception {
        byte[] bytes = getNetMessageBody().getBytes();
        OnlineClientHttpProBuf.OnlineHeartClientHttpProBuf req = OnlineClientHttpProBuf.OnlineHeartClientHttpProBuf.parseFrom(bytes);
        setId(req.getId());
    }

    @Override
    public void release() {

    }

    @Override
    public void encodeNetProtoBufMessageBody() throws Exception {
        OnlineClientHttpProBuf.OnlineHeartClientHttpProBuf.Builder builder = OnlineClientHttpProBuf.OnlineHeartClientHttpProBuf.newBuilder();
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

