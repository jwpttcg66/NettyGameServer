package com.snowcattle.game.service.net.websocket.handler;

import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.common.config.GameServerConfig;
import com.snowcattle.game.common.exception.CodecException;
import com.snowcattle.game.logic.net.NetMessageProcessLogic;
import com.snowcattle.game.service.config.GameServerConfigService;
import com.snowcattle.game.service.message.AbstractNetProtoBufMessage;
import com.snowcattle.game.service.message.decoder.NetProtoBufHttpMessageDecoderFactory;
import com.snowcattle.game.service.message.decoder.NetProtoBufTcpMessageDecoderFactory;
import com.snowcattle.game.service.net.tcp.MessageAttributeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by jiangwenping on 2017/11/8.
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private static final String WEBSOCKET_PATH = "/websocket";

    private WebSocketServerHandshaker handshaker;

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpRequest) {
            handleHttpRequest(ctx, (HttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, HttpRequest req) {
        // Handle a bad request.
        if (!req.decoderResult().isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }

        // Allow only GET methods.
        if (req.method() != GET) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }

//        // Send the demo page and favicon.ico
//        if ("/".equals(req.uri())) {
//            ByteBuf content = WebSocketServerBenchmarkPage.getContent(getWebSocketLocation(req));
//            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);
//
//            res.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
//            HttpUtil.setContentLength(res, content.readableBytes());
//
//            sendHttpResponse(ctx, req, res);
//            return;
//        }
        if ("/favicon.ico".equals(req.uri()) || "/".equals(req.uri())) {
            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);
            sendHttpResponse(ctx, req, res);
            return;
        }

        // Handshake
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                getWebSocketLocation(req), null, true, 5 * 1024 * 1024);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {

        // Check for closing frame
        if (frame instanceof CloseWebSocketFrame) {
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

    private static void sendHttpResponse(
            ChannelHandlerContext ctx, HttpRequest req, FullHttpResponse res) {
        // Generate an error page if response getStatus code is not OK (200).
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpUtil.setContentLength(res, res.content().readableBytes());
        }

        // Send the response and close the connection if necessary.
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private static String getWebSocketLocation(HttpRequest req) {
        String location =  req.headers().get(HttpHeaderNames.HOST) + WEBSOCKET_PATH;
        return "ws://" + location;
//        if (WebSocketServer.SSL) {
//            return "wss://" + location;
//        } else {
//            return "ws://" + location;
//        }
    }
}

