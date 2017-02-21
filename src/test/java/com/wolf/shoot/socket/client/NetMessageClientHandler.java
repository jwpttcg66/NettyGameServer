package com.wolf.shoot.socket.client;

import com.wolf.shoot.service.net.message.AbstractNetMessage;
import com.wolf.shoot.service.net.message.NetMessageBody;
import com.wolf.shoot.service.net.message.NetMessageHead;
import com.wolf.shoot.message.logic.tcp.online.client.OnlineHeartClientTcpMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by jwp on 2017/1/26.
 */
public class NetMessageClientHandler extends ChannelInboundHandlerAdapter {
    private final AbstractNetMessage abstractNetMessage;

    public NetMessageClientHandler() {
        abstractNetMessage = new OnlineHeartClientTcpMessage();
        NetMessageHead netMessageHead = new NetMessageHead();
        netMessageHead.setSerial(5);
        netMessageHead.setCmd((short) 2);
        netMessageHead.setVersion((byte) 3);

        NetMessageBody netMessageBody = new NetMessageBody();
        byte[] bytes = "hello world".getBytes(CharsetUtil.UTF_8);
        netMessageBody.setBytes(bytes);

        abstractNetMessage.setNetMessageBody(netMessageBody);
        abstractNetMessage.setNetMessageHead(netMessageHead);

    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(abstractNetMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.write(msg);
        AbstractNetMessage abstractNetMessage = (AbstractNetMessage) msg;
        String response = new String(abstractNetMessage.getNetMessageBody().getBytes(), CharsetUtil.UTF_8);
        System.out.println("客户端收到：" + response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
