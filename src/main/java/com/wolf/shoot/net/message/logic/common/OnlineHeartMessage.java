package com.wolf.shoot.net.message.logic.common;

import com.wolf.shoot.common.annotation.MessageCommandAnnotation;
import com.wolf.shoot.net.message.MessageCommands;
import com.wolf.shoot.net.message.NetMessageHead;
import com.wolf.shoot.net.message.NetProtoBufMessage;
import com.wolf.shoot.net.message.auto.common.CommonMessageProBuf;

/**
 * Created by jiangwenping on 17/2/8.
 */
@MessageCommandAnnotation(command = MessageCommands.ONLINE_HEART_MESSAGE)
public class OnlineHeartMessage extends NetProtoBufMessage {

    public  OnlineHeartMessage(){
        setCmd(NetMessageHead.MESSAGE_HEADER_FLAG);
    }

    @Override
    public void decoderNetProtoBufMessageBody() throws Exception {
        byte[] bytes = getNetMessageBody().getBytes();
        CommonMessageProBuf.OnlineHeartProBuf req = CommonMessageProBuf.OnlineHeartProBuf.parseFrom(bytes);
        System.out.println(req.getId());
    }

    @Override
    public void release() {

    }

    @Override
    public void encodeNetProtoBufMessageBody() throws Exception {
        CommonMessageProBuf.OnlineHeartProBuf.Builder builder = CommonMessageProBuf.OnlineHeartProBuf.newBuilder();
        builder.setId(2);
        byte[] bytes = builder.build().toByteArray();
        getNetMessageBody().setBytes(bytes);
    }

}
