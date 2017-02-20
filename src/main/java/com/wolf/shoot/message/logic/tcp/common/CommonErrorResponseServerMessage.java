package com.wolf.shoot.message.logic.tcp.common;

import com.wolf.shoot.common.annotation.MessageCommandAnnotation;
import com.wolf.shoot.common.exception.CodecException;
import com.wolf.shoot.message.auto.common.CommonMessageProBuf;
import com.wolf.shoot.service.net.message.AbstractNetProtoBufTcpMessage;
import com.wolf.shoot.service.net.message.command.MessageCommandIndex;

/**
 * Created by jwp on 2017/2/10.
 */
@MessageCommandAnnotation(command = MessageCommandIndex.COMMON_ERROR_RESPONSE_MESSAGE)
public class CommonErrorResponseServerMessage extends AbstractNetProtoBufTcpMessage {

    /**
     * 状态码
     */
    private int state;
    /**
     * 特殊提示
     */
    private String arg;

    @Override
    public void decoderNetProtoBufMessageBody() throws CodecException, Exception {
        byte[] bytes = getNetMessageBody().getBytes();
        CommonMessageProBuf.CommonErrorResponseServerProBuf req = CommonMessageProBuf.CommonErrorResponseServerProBuf.parseFrom(bytes);
        this.state = req.getState();
        this.arg = req.getArg();
    }

    @Override
    public void release() throws CodecException {

    }

    @Override
    public void encodeNetProtoBufMessageBody() throws CodecException, Exception {
        CommonMessageProBuf.CommonErrorResponseServerProBuf.Builder builder = CommonMessageProBuf.CommonErrorResponseServerProBuf.newBuilder();
        builder.setArg(arg);
        builder.setState(state);
        byte[] bytes = builder.build().toByteArray();
        getNetMessageBody().setBytes(bytes);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

}
