package com.wolf.shoot.service.rpc.client;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.service.rpc.SdServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

/**
 * Created by jiangwenping on 17/3/14.
 * 服务器连接
 */
public class RpcServerConnectTask implements Runnable{

    private Logger logger = Loggers.serverLogger;

    private InetSocketAddress remotePeer;

    private CountDownLatch countDownLatch;

    EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);

    private int serverId;

    public RpcServerConnectTask(SdServer sdServer, CountDownLatch countDownLatch) {
        this.serverId = sdServer.getServerId();
        this.remotePeer =  new InetSocketAddress(sdServer.getIp(), sdServer.getCommunicationPort());
        this.countDownLatch = countDownLatch;
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
                    logger.debug("Successfully connect to remote server. remote peer = " + remotePeer + " success");
                    RpcClient rpcClient = new RpcClient((NioSocketChannel) channelFuture.channel());
                    RpcClientHandler handler = channelFuture.channel().pipeline().get(RpcClientHandler.class);
                    handler.setRpcClient(rpcClient);
                    ConnectManage.getInstance().addClient(serverId, rpcClient);
                }else{
                    logger.debug("Successfully connect to remote server. remote peer = " + remotePeer + "fail");
                }
                countDownLatch.countDown();
            }

        });

    }
}
