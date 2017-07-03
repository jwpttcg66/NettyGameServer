package com.snowcattle.game.message.logic.tcp.common;

import com.snowcattle.game.common.annotation.MessageCommandAnnotation;
import com.snowcattle.game.common.exception.CodecException;
import com.snowcattle.game.message.auto.common.CommonMessageProBuf;
import com.snowcattle.game.service.message.AbstractNetProtoBufTcpMessage;
import com.snowcattle.game.service.message.command.MessageCommandIndex;

/**
 * Created by jiangwenping on 17/2/20.
 */
@MessageCommandAnnotation(command = MessageCommandIndex.COMMON_RESPONSE_MESSAGE)
public class CommonResponseServerMessage extends AbstractNetProtoBufTcpMessage {

    @Override
    public void decoderNetProtoBufMessageBody() throws CodecException, Exception {
        byte[] bytes = getNetMessageBody().getBytes();
        CommonMessageProBuf.CommonResponseServerProBuf req = CommonMessageProBuf.CommonResponseServerProBuf.parseFrom(bytes);
    }

    @Override
    public void release() throws CodecException {

    }

    @Override
    public void encodeNetProtoBufMessageBody() throws CodecException, Exception {
        CommonMessageProBuf.CommonResponseServerProBuf.Builder builder = CommonMessageProBuf.CommonResponseServerProBuf.newBuilder();
        byte[] bytes = builder.build().toByteArray();
        getNetMessageBody().setBytes(bytes);
    }

}
