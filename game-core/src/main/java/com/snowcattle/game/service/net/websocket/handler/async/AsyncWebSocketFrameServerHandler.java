package com.snowcattle.game.service.net.websocket.handler.async;

import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.exception.CodecException;
import com.snowcattle.game.common.exception.GameHandlerException;
import com.snowcattle.game.common.exception.NetMessageException;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.logic.net.NetMessageProcessLogic;
import com.snowcattle.game.service.event.GameAsyncEventService;
import com.snowcattle.game.service.event.impl.SessionRegisterEvent;
import com.snowcattle.game.service.event.impl.SessionUnRegisterEvent;
import com.snowcattle.game.service.lookup.NetTcpSessionLoopUpService;
import com.snowcattle.game.service.message.AbstractNetMessage;
import com.snowcattle.game.service.message.AbstractNetProtoBufMessage;
import com.snowcattle.game.service.message.decoder.NetProtoBufTcpMessageDecoderFactory;
import com.snowcattle.game.service.message.factory.TcpMessageFactory;
import com.snowcattle.game.service.net.tcp.MessageAttributeEnum;
import com.snowcattle.game.service.net.tcp.session.NettyTcpSession;
import com.snowcattle.game.service.net.tcp.session.builder.NettyTcpSessionBuilder;
import com.snowcattle.game.service.net.websocket.handler.WebSocketServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import org.slf4j.Logger;

/**
 * Created by jiangwenping on 2017/11/15.
 */
public class AsyncWebSocketFrameServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    public static Logger logger = Loggers.handlerLogger;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
        NettyTcpSessionBuilder nettyTcpSessionBuilder = LocalMananger.getInstance().getLocalSpringBeanManager().getNettyTcpSessionBuilder();
        NettyTcpSession nettyTcpSession = (NettyTcpSession) nettyTcpSessionBuilder.buildSession(ctx.channel());
        NetTcpSessionLoopUpService netTcpSessionLoopUpService = LocalMananger.getInstance().getLocalSpringServiceManager().getNetTcpSessionLoopUpService();
        boolean flag = netTcpSessionLoopUpService.addNettySession(nettyTcpSession);
        if (!flag) {
            //被限制不能加入
            TcpMessageFactory messageFactory = LocalMananger.getInstance().getLocalSpringBeanManager().getTcpMessageFactory();
            AbstractNetMessage abstractNetMessage = messageFactory.createCommonErrorResponseMessage(-1, GameHandlerException.COMMON_ERROR_MAX_CONNECT_TCP_SESSION_NUMBER);
            nettyTcpSession.write(abstractNetMessage);
            nettyTcpSession.close();
            ctx.close();
            return;

        }
//        //加入到updateservice
//        UpdateService updateService = LocalMananger.getInstance().getUpdateService();
//        NettyTcpSerssionUpdate nettyTcpSerssionUpdate = new NettyTcpSerssionUpdate(nettyTcpSession);
//        EventParam<NettyTcpSerssionUpdate> param = new EventParam<NettyTcpSerssionUpdate>(nettyTcpSerssionUpdate);
//        CycleEvent cycleEvent = new CycleEvent(Constants.EventTypeConstans.readyCreateEventType, nettyTcpSerssionUpdate.getUpdateId(), param);
//        updateService.addReadyCreateEvent(cycleEvent);


        //生成aysnc事件
        long sessionId = nettyTcpSession.getSessionId();
        EventParam<NettyTcpSession> sessionEventParam = new EventParam<>(nettyTcpSession);
        SessionRegisterEvent sessionRegisterEvent = new SessionRegisterEvent(sessionId, sessionId, sessionEventParam);
        GameAsyncEventService gameAsyncEventService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameAsyncEventService();
        gameAsyncEventService.putEvent(sessionRegisterEvent);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        handleWebSocketFrame(ctx, msg);
    }

    private static void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
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
            AbstractNetProtoBufMessage netMessage = null;
            try {
                netMessage = netProtoBufTcpMessageDecoderFactory.praseMessage(buf);
            } catch (CodecException e) {
                e.printStackTrace();
            }

//            binaryWebSocketFrame.release();

            Channel channel = ctx.channel();
            //装配session
            NetTcpSessionLoopUpService netTcpSessionLoopUpService = LocalMananger.getInstance().getLocalSpringServiceManager().getNetTcpSessionLoopUpService();
            long sessonId = channel.attr(NettyTcpSessionBuilder.channel_session_id).get();
            NettyTcpSession nettySession = (NettyTcpSession) netTcpSessionLoopUpService.lookup(sessonId);
            if (nettySession == null) {
                logger.error("tcp netsession null channelId is:" + channel.id().asLongText());
                //已经丢失session， 停止处理
                return;
            }

            //封装属性
            netMessage.setAttribute(MessageAttributeEnum.DISPATCH_SESSION, nettySession);
            //进行处理
            NetMessageProcessLogic netMessageProcessLogic = LocalMananger.getInstance().getLocalSpringBeanManager().getNetMessageProcessLogic();
            netMessageProcessLogic.processWebSocketMessage(netMessage, ctx.channel());

        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        NetTcpSessionLoopUpService netTcpSessionLoopUpService = LocalMananger.getInstance().getLocalSpringServiceManager().getNetTcpSessionLoopUpService();
        long sessonId = ctx.channel().attr(NettyTcpSessionBuilder.channel_session_id).get();
        NettyTcpSession nettyTcpSession = (NettyTcpSession) netTcpSessionLoopUpService.lookup(sessonId);
        disconnect(ctx.channel());

        if (nettyTcpSession == null) {
            ctx.fireChannelUnregistered();
            return;
        }

        netTcpSessionLoopUpService.removeNettySession(nettyTcpSession);
        //因为updateService会自己删除，这里不需要逻辑

        //生成aysnc事件
        long sessionId = nettyTcpSession.getSessionId();
        EventParam<NettyTcpSession> sessionEventParam = new EventParam<>(nettyTcpSession);
        SessionUnRegisterEvent sessionUnRegisterEvent = new SessionUnRegisterEvent(sessionId, sessionId, sessionEventParam);
        GameAsyncEventService gameAsyncEventService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameAsyncEventService();
        gameAsyncEventService.putEvent(sessionUnRegisterEvent);

        ctx.fireChannelUnregistered();
    }

    private static void disconnect(Channel channel) throws NetMessageException {
        NetTcpSessionLoopUpService netTcpSessionLoopUpService = LocalMananger.getInstance().getLocalSpringServiceManager().getNetTcpSessionLoopUpService();
        long sessonId = channel.attr(NettyTcpSessionBuilder.channel_session_id).get();
        NettyTcpSession nettySession = (NettyTcpSession) netTcpSessionLoopUpService.lookup(sessonId);
        if (nettySession == null) {
            logger.error("tcp netsession null channelId is:" + channel.id().asLongText());
            return;
        }

        nettySession.close();
    }
}
