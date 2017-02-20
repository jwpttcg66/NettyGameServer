package com.wolf.shoot.service.net.message.factory;

import com.wolf.shoot.service.net.message.AbstractNetMessage;
import com.wolf.shoot.message.logic.tcp.common.CommonErrorResponseWhieCmdServerMessage;

/**
 * Created by jwp on 2017/2/10.
 */
public class MessageFactory implements IMessageFactory {

    @Override
    public AbstractNetMessage createCommonErrorResponseMessage(int serial, int cmd, int state) {
        CommonErrorResponseWhieCmdServerMessage commonErrorResponseWhieCmdServerMessage = new CommonErrorResponseWhieCmdServerMessage();
        commonErrorResponseWhieCmdServerMessage.setSerial(serial);
        commonErrorResponseWhieCmdServerMessage.setCmd(cmd);
        commonErrorResponseWhieCmdServerMessage.setState(state);
        return commonErrorResponseWhieCmdServerMessage;
    }
}
