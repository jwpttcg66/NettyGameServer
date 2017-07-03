package com.snowcattle.game.service.net.proxy;

import com.snowcattle.game.common.config.GameServerConfig;
import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.config.GameServerConfigService;
import com.snowcattle.game.service.net.tcp.handler.GameLoggingHandler;
import com.snowcattle.game.service.message.decoder.NetProtoBufMessageTCPDecoder;
import com.snowcattle.game.service.message.encoder.NetProtoBufMessageTCPEncoder;
import com.snowcattle.game.service.net.proxy.handler.ProxyBackendHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by jiangwenping on 2017/6/28.
 */
public class ProxyTcpBackChannelInitializer extends ChannelInitializer<NioSocketChannel> {


    private Channel inboundChannel;

    public ProxyTcpBackChannelInitializer(Channel inboundChannel) {
        this.inboundChannel = inboundChannel;
    }

    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {

        ChannelPipeline channelPipLine = nioSocketChannel.pipeline();
        int maxLength = Integer.MAX_VALUE;
        channelPipLine.addLast("frame", new LengthFieldBasedFrameDecoder(maxLength, 2, 4, 0, 0));
        channelPipLine.addLast("encoder", new NetProtoBufMessageTCPEncoder());
        channelPipLine.addLast("decoder", new NetProtoBufMessageTCPDecoder());
        int readerIdleTimeSeconds = GlobalConstants.Net.SESSION_HEART_ALL_TIMEOUT;
        int writerIdleTimeSeconds = GlobalConstants.Net.SESSION_HEART_ALL_TIMEOUT;
        int allIdleTimeSeconds = GlobalConstants.Net.SESSION_HEART_ALL_TIMEOUT;
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();
        if(gameServerConfig.isDevelopModel()) {
            channelPipLine.addLast("logger", new GameLoggingHandler(LogLevel.DEBUG));
        }
        channelPipLine.addLast("idleStateHandler", new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds));
        channelPipLine.addLast("handler", new ProxyBackendHandler(inboundChannel));
    }
}
