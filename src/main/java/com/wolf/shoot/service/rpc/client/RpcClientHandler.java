package com.wolf.shoot.service.rpc.client;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.service.net.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;

/**
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
    private Logger logger = Loggers.rpcLogger;

    //那个服务器
    private RpcConnectClient rpcConnectClient;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, RpcResponse response) throws Exception {
        rpcConnectClient.handleRpcResponser(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("rpc client caught exception", cause);
        rpcConnectClient.close();
    }

    public RpcConnectClient getRpcConnectClient() {
        return rpcConnectClient;
    }

    public void setRpcConnectClient(RpcConnectClient rpcConnectClient) {
        this.rpcConnectClient = rpcConnectClient;
    }
}
