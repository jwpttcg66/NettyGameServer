package com.wolf.shoot.message.logic.tcp.online;

import com.wolf.shoot.common.annotation.MessageCommandAnnotation;
import com.wolf.shoot.message.auto.tcp.online.OnlineTCPProBuf;
import com.wolf.shoot.service.net.message.AbstractNetProtoBufTcpMessage;
import com.wolf.shoot.service.net.message.command.MessageCommandIndex;

/**
 * Created by jiangwenping on 17/2/8.
 */
@MessageCommandAnnotation(command = MessageCommandIndex.ONLINE_HEART_MESSAGE)
public class OnlineHeartMessage extends AbstractNetProtoBufTcpMessage {

    private int id;

    public OnlineHeartMessage(){
        setCmd(MessageCommandIndex.ONLINE_HEART_MESSAGE);
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
        getNetMessageBody().setBytes(bytes);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
