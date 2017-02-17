package com.wolf.shoot.net.message.factory;

import com.wolf.shoot.net.message.AbstractNetMessage;
import com.wolf.shoot.net.message.logic.tcp.common.CommonServerErrorResponseMessageAbstract;

/**
 * Created by jwp on 2017/2/10.
 */
public class MessageFactory implements IMessageFactory {

    @Override
    public AbstractNetMessage createCommonErrorResponseMessage(int serial, int cmd, int state) {
        CommonServerErrorResponseMessageAbstract commonServerErrorResponseMessage  = new CommonServerErrorResponseMessageAbstract();
        commonServerErrorResponseMessage.setSerial(serial);
        commonServerErrorResponseMessage.setCmd(cmd);
        commonServerErrorResponseMessage.setState(state);
        return commonServerErrorResponseMessage;
    }
}
