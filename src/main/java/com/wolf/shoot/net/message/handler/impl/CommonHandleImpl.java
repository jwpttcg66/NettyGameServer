package com.wolf.shoot.net.message.handler.impl;

import com.wolf.shoot.net.message.AbstractNetMessage;
import com.wolf.shoot.net.message.handler.auto.common.CommonHandler;
import com.wolf.shoot.net.message.logic.tcp.common.CommonServerErrorResponseMessageAbstract;
import com.wolf.shoot.net.message.logic.tcp.online.OnlineHeartMessage;

/**
 * Created by jiangwenping on 17/2/15.
 */
public class CommonHandleImpl extends CommonHandler{
    @Override
    public AbstractNetMessage handleOnlineHeartMessageImpl(OnlineHeartMessage message) throws Exception {
        CommonServerErrorResponseMessageAbstract response = new CommonServerErrorResponseMessageAbstract();
        response.setCmd(message.getCmd());
        return response;
    }
}
