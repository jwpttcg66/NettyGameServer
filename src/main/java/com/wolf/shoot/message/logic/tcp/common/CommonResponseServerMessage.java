package com.wolf.shoot.message.logic.tcp.common;

import com.wolf.shoot.common.annotation.MessageCommandAnnotation;
import com.wolf.shoot.common.exception.CodecException;
import com.wolf.shoot.message.auto.common.CommonMessageProBuf;
import com.wolf.shoot.service.net.message.AbstractNetProtoBufTcpMessage;
import com.wolf.shoot.service.net.message.command.MessageCommandIndex;

/**
 * Created by jiangwenping on 17/2/20.
 */
@MessageCommandAnnotation(command = MessageCommandIndex.COMMON_RESPONSE_MESSAGE)
public class CommonResponseServerMessage extends AbstractNetProtoBufTcpMessage {


    @Override
    public void decoderNetProtoBufMessageBody() throws CodecException, Exception {
//        byte[] bytes = getNetMessageBody().getBytes();
//        CommonMessageProBuf.CommonErrorResponseServerProBuf req = CommonMessageProBuf.CommonErrorResponseServerProBuf.parseFrom(bytes);
    }

    @Override
    public void release() throws CodecException {

    }

    @Override
    public void encodeNetProtoBufMessageBody() throws CodecException, Exception {
        CommonMessageProBuf.CommonErrorResponseServerProBuf.Builder builder = CommonMessageProBuf.CommonErrorResponseServerProBuf.newBuilder();
        byte[] bytes = builder.build().toByteArray();
        getNetMessageBody().setBytes(bytes);
    }

}
