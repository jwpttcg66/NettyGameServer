package com.wolf.shoot.message.logic.tcp.online.client;

import com.wolf.shoot.common.annotation.MessageCommandAnnotation;
import com.wolf.shoot.message.auto.tcp.online.client.OnlineTCPClientProBuf;
import com.wolf.shoot.service.net.message.AbstractNetProtoBufTcpMessage;
import com.wolf.shoot.service.net.message.command.MessageCommandIndex;

/**
 * Created by jiangwenping on 17/2/8.
 */
@MessageCommandAnnotation(command = MessageCommandIndex.ONLINE_HEART_MESSAGE)
public class OnlineHeartClientMessage extends AbstractNetProtoBufTcpMessage {

    private int id;

    public OnlineHeartClientMessage(){
        setCmd(MessageCommandIndex.ONLINE_HEART_MESSAGE);
    }

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
