package com.wolf.shoot.service.rpc.client.net;

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
    private RpcClient rpcClient;

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
        rpcClient.handleRpcResponser(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("rpc client caught exception", cause);
        rpcClient.close();
    }

    public RpcClient getRpcClient() {
        return rpcClient;
    }

    public void setRpcClient(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
    }
}
