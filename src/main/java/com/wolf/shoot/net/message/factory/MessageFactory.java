package com.wolf.shoot.net.message.factory;

import com.wolf.shoot.net.message.NetMessage;
import com.wolf.shoot.net.message.logic.tcp.common.CommonServerErrorResponseMessage;

/**
 * Created by jwp on 2017/2/10.
 */
public class MessageFactory implements IMessageFactory {

    @Override
    public NetMessage createCommonErrorResponseMessage(int serial, int cmd, int state) {
        CommonServerErrorResponseMessage commonServerErrorResponseMessage  = new CommonServerErrorResponseMessage();
        commonServerErrorResponseMessage.setSerial(serial);
        commonServerErrorResponseMessage.setCmd(cmd);
        commonServerErrorResponseMessage.setState(state);
        return commonServerErrorResponseMessage;
    }
}
