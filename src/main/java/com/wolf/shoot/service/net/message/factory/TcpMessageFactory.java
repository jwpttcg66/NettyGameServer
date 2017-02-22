package com.wolf.shoot.service.net.message.factory;

import com.wolf.shoot.message.logic.tcp.common.CommonErrorResponseServerMessage;
import com.wolf.shoot.message.logic.tcp.common.CommonResponseServerMessage;
import com.wolf.shoot.service.net.message.AbstractNetMessage;

/**
 * Created by jwp on 2017/2/10.
 */
public class TcpMessageFactory implements ITcpMessageFactory {

    @Override
    public AbstractNetMessage createCommonErrorResponseMessage(int serial, int state) {
        CommonErrorResponseServerMessage commonErrorResponseServerMessage = new CommonErrorResponseServerMessage();
        commonErrorResponseServerMessage.setSerial(serial);
        commonErrorResponseServerMessage.setState(state);
        return commonErrorResponseServerMessage;
    }

    @Override
    public AbstractNetMessage createCommonResponseMessage(int serial) {
        CommonResponseServerMessage commonResponseServerMessage = new CommonResponseServerMessage();
        commonResponseServerMessage.setSerial(serial);
        return commonResponseServerMessage;
    }

}
