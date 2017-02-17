package com.wolf.shoot.net.message.logic.tcp.common;

import com.wolf.shoot.common.annotation.MessageCommandAnnotation;
import com.wolf.shoot.common.exception.CodecException;
import com.wolf.shoot.net.message.MessageCommands;
import com.wolf.shoot.net.message.AbstractNetProtoBufMessage;
import com.wolf.shoot.net.message.auto.common.CommonMessageProBuf;

/**
 * Created by jwp on 2017/2/10.
 */
@MessageCommandAnnotation(command = MessageCommands.COMMON_ERROP_RESPONSE_WITH_COMMAND_MESSAGE)
public class CommonServerErrorResponseMessageAbstract extends AbstractNetProtoBufMessage {

    /**
     * 状态码
     */
    private int state;
    /**
     * 特殊提示
     */
    private String arg;
    /**
     * 协议号
     */
    private int cmd;

    @Override
    public void decoderNetProtoBufMessageBody() throws CodecException, Exception {
        byte[] bytes = getNetMessageBody().getBytes();
        CommonMessageProBuf.CommonServerErrorResponseWithCmdProBuf req = CommonMessageProBuf.CommonServerErrorResponseWithCmdProBuf.parseFrom(bytes);
        this.cmd = req.getCmd();
        this.state = req.getCode();
        this.arg = req.getArg();
    }

    @Override
    public void release() throws CodecException {

    }

    @Override
    public void encodeNetProtoBufMessageBody() throws CodecException, Exception {
        CommonMessageProBuf.CommonServerErrorResponseWithCmdProBuf.Builder builder = CommonMessageProBuf.CommonServerErrorResponseWithCmdProBuf.newBuilder();
        builder.setArg(arg);
        builder.setCmd(cmd);
        builder.setCode(state);
        byte[] bytes = builder.build().toByteArray();
        getNetProtoBufMessageBody().setBytes(bytes);
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

    public int getCmd() {
        return cmd;
    }

    @Override
    public void setCmd(int cmd) {
        this.cmd = cmd;
    }
}
