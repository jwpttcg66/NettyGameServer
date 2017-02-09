package com.wolf.shoot.net.message.logic.common;

import com.wolf.shoot.common.annotation.MessageCommandAnnotation;
import com.wolf.shoot.net.message.MessageCommands;
import com.wolf.shoot.net.message.NetProtoBufMessage;
import com.wolf.shoot.net.message.auto.common.CommonMessageProBuf;

/**
 * Created by jiangwenping on 17/2/8.
 */
@MessageCommandAnnotation(command = MessageCommands.ONLINE_HEART_MESSAGE)
public class OnlineHeartMessage extends NetProtoBufMessage {

    private int id;

    public  OnlineHeartMessage(){
        setCmd(MessageCommands.ONLINE_HEART_MESSAGE.command_id);
    }

    @Override
    public void decoderNetProtoBufMessageBody() throws Exception {
        byte[] bytes = getNetMessageBody().getBytes();
        CommonMessageProBuf.OnlineHeartProBuf req = CommonMessageProBuf.OnlineHeartProBuf.parseFrom(bytes);
        setId(req.getId());
        System.out.println(getId());
    }

    @Override
    public void release() {

    }

    @Override
    public void encodeNetProtoBufMessageBody() throws Exception {
        CommonMessageProBuf.OnlineHeartProBuf.Builder builder = CommonMessageProBuf.OnlineHeartProBuf.newBuilder();
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
