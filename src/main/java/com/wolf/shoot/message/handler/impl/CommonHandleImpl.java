package com.wolf.shoot.message.handler.impl;

import com.wolf.shoot.message.logic.tcp.common.CommonServerErrorResponseMessageAbstract;
import com.wolf.shoot.service.net.message.AbstractNetMessage;
import com.wolf.shoot.message.handler.auto.common.CommonHandler;
import com.wolf.shoot.message.logic.tcp.online.OnlineHeartMessage;

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
