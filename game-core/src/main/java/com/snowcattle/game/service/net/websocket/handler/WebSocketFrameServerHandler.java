package com.snowcattle.game.service.net.websocket.handler;

import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.common.exception.CodecException;
import com.snowcattle.game.logic.net.NetMessageProcessLogic;
import com.snowcattle.game.service.message.AbstractNetProtoBufMessage;
import com.snowcattle.game.service.message.decoder.NetProtoBufTcpMessageDecoderFactory;
import com.snowcattle.game.service.net.tcp.MessageAttributeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.websocketx.*;

/**
 * Created by jiangwenping on 2017/11/15.
 */
public class WebSocketFrameServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        handleWebSocketFrame(ctx, (WebSocketFrame) msg);
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // Check for closing frame
        if (frame instanceof CloseWebSocketFrame) {
            WebSocketServerHandler webSocketServerHandler = (WebSocketServerHandler) ctx.pipeline().get("webSocketServerHandler");
            WebSocketServerHandshaker handshaker = webSocketServerHandler.getHandshaker();
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        if (frame instanceof PingWebSocketFrame) {
            ctx.write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        if (frame instanceof TextWebSocketFrame) {
            // Echo the frame
            ctx.write(frame.retain());
            return;
        }
//        if (frame instanceof BinaryWebSocketFrame) {
//            // Echo the frame
//            ctx.write(frame.retain());
//            return;
//        }

        if (frame instanceof BinaryWebSocketFrame) {
            BinaryWebSocketFrame binaryWebSocketFrame = (BinaryWebSocketFrame) frame;
            ByteBuf buf = binaryWebSocketFrame.content();
            //开始解析
            NetProtoBufTcpMessageDecoderFactory netProtoBufTcpMessageDecoderFactory = LocalMananger.getInstance().getLocalSpringBeanManager().getNetProtoBufTcpMessageDecoderFactory();
            AbstractNetProtoBufMessage netProtoBufMessage = null;
            try {
                netProtoBufMessage = netProtoBufTcpMessageDecoderFactory.praseMessage(buf);
            } catch (CodecException e) {
                e.printStackTrace();
            }


            //封装属性
            netProtoBufMessage.setAttribute(MessageAttributeEnum.DISPATCH_CHANNEL, ctx);

            //进行处理
            NetMessageProcessLogic netMessageProcessLogic = LocalMananger.getInstance().getLocalSpringBeanManager().getNetMessageProcessLogic();
            netMessageProcessLogic.processWebSocketMessage(netProtoBufMessage, ctx.channel());

            return;
        }
    }
}
