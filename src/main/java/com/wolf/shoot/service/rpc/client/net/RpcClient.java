package com.wolf.shoot.service.rpc.client.net;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.net.RpcRequest;
import com.wolf.shoot.service.net.RpcResponse;
import com.wolf.shoot.service.rpc.client.PendingRPCManager;
import com.wolf.shoot.service.rpc.client.RPCFuture;
import com.wolf.shoot.service.rpc.server.SdServer;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;

import java.util.concurrent.ExecutorService;

/**
 * Created by jiangwenping on 17/3/14.
 * 检查客户端连接
 */
public class RpcClient
{
    private Logger logger = Loggers.rpcLogger;
    private RpcClientConnection rpcClientConnection;


    public RpcClient(SdServer sdServer, ExecutorService threadPool){
        rpcClientConnection = new RpcClientConnection(this, sdServer, threadPool);
    }
    public RPCFuture sendRequest(RpcRequest request) {
        RPCFuture rpcFuture = new RPCFuture(request);
        PendingRPCManager pendingRPCManager = LocalMananger.getInstance().getLocalSpringBeanManager().getPendingRPCManager();
        pendingRPCManager.addRPCFuture(request.getRequestId(), rpcFuture);
        rpcClientConnection.writeRequest(request);
        return rpcFuture;
    }

    public NioSocketChannel getChannel() {
        return rpcClientConnection.getChannel();
    }

    public void close(){
        logger.error("rpc client close");
//        pendingRPC.clear();
        if(rpcClientConnection != null) {
            rpcClientConnection.close();
        }
    }

    public void handleRpcResponser(RpcResponse rpcResponse){
        String requestId = rpcResponse.getRequestId();
        PendingRPCManager pendingRPCManager = LocalMananger.getInstance().getLocalSpringBeanManager().getPendingRPCManager();
        RPCFuture rpcFuture = pendingRPCManager.getRPCFuture(requestId);
        if (rpcFuture != null) {
            pendingRPCManager.removeRPCFuture(requestId);
            rpcFuture.done(rpcResponse);
        }
    }

    public RpcClientConnection getRpcClientConnection() {
        return rpcClientConnection;
    }

}
