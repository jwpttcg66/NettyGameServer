package com.wolf.shoot.service.net;

import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.net.message.AbstractNetProtoBufUdpMessage;
import com.wolf.shoot.service.net.pipeline.IServerPipeLine;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by jwp on 2017/2/17.
 */
public class GameNetMessageUdpServerHandler extends SimpleChannelInboundHandler<AbstractNetProtoBufUdpMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AbstractNetProtoBufUdpMessage netMessage) throws Exception {
        //获取管道
        IServerPipeLine iServerPipeLine = LocalMananger.getInstance().getLocalSpringBeanManager().getDefaultUdpServerPipeLine();
        iServerPipeLine.dispatchAction(channelHandlerContext.channel(), netMessage);

    }

}
