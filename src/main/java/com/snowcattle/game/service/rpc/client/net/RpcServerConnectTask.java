package com.snowcattle.game.service.rpc.client.net;

import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.service.rpc.server.RpcNodeInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;

import java.net.InetSocketAddress;

/**
 * Created by jiangwenping on 17/3/14.
 * 服务器连接
 */
public class RpcServerConnectTask implements Runnable{

    private Logger logger = Loggers.serverLogger;

    private InetSocketAddress remotePeer;

    private EventLoopGroup eventLoopGroup;


    private RpcClient rpcClient;
    public RpcServerConnectTask(RpcNodeInfo rpcNodeInfo, EventLoopGroup eventLoopGroup, RpcClient rpcClient) {
        this.remotePeer =  new InetSocketAddress(rpcNodeInfo.getHost(), rpcNodeInfo.getIntPort());
        this.eventLoopGroup = eventLoopGroup;
        this.rpcClient = rpcClient;
    }

    @Override
    public void run() {
        Bootstrap b = new Bootstrap();
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .handler(new RpcClientInitializer());
        ChannelFuture channelFuture = b.connect(remotePeer);
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    logger.debug("connect to remote server. remote peer = " + remotePeer + " success");
                    RpcClientHandler handler = channelFuture.channel().pipeline().get(RpcClientHandler.class);
                    handler.setRpcClient(rpcClient);
                    rpcClient.getRpcClientConnection().setChannel((NioSocketChannel) channelFuture.channel());
                }else{
                    logger.debug("connect to remote server. remote peer = " + remotePeer + "fail");
                }
            }

        });
        try {
            channelFuture.await();
        } catch (InterruptedException e) {
            logger.error(e.toString(), e);
        }

        //连接结束
        logger.debug("connect to remote server. remote peer = " + remotePeer);
    }
}
