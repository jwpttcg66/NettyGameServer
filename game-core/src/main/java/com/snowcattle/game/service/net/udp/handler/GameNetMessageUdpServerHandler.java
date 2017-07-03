package com.snowcattle.game.service.net.udp.handler;

import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.message.AbstractNetProtoBufUdpMessage;
import com.snowcattle.game.service.net.tcp.pipeline.IServerPipeLine;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by jwp on 2017/2/17.
 * udp协议处理handler
 */
public class GameNetMessageUdpServerHandler extends SimpleChannelInboundHandler<AbstractNetProtoBufUdpMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AbstractNetProtoBufUdpMessage netMessage) throws Exception {
        //获取管道
        IServerPipeLine iServerPipeLine = LocalMananger.getInstance().getLocalSpringBeanManager().getDefaultUdpServerPipeLine();
        iServerPipeLine.dispatchAction(channelHandlerContext.channel(), netMessage);
    }

}
