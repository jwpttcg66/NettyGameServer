package com.wolf.shoot.service.rpc.client;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.service.net.RpcRequest;
import com.wolf.shoot.service.net.RpcResponse;
import com.wolf.shoot.service.rpc.SdServer;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * Created by jiangwenping on 17/3/14.
 * 检查客户端连接
 */
public class RpcClient
{
    private Logger logger = Loggers.rpcLogger;
    private ConcurrentHashMap<String, RPCFuture> pendingRPC = new ConcurrentHashMap<>();

    private RpcClientConnection rpcClientConnection;


    public RpcClient(SdServer sdServer, ExecutorService threadPool){
        rpcClientConnection = new RpcClientConnection(this, sdServer, threadPool);
    }
    public RPCFuture sendRequest(RpcRequest request) {
        RPCFuture rpcFuture = new RPCFuture(request);
        pendingRPC.put(request.getRequestId(), rpcFuture);
        rpcClientConnection.writeRequest(request);
        return rpcFuture;
    }

    public NioSocketChannel getChannel() {
        return rpcClientConnection.getChannel();
    }

    public void close(){
        logger.error("rpc client close");
        pendingRPC.clear();
        if(rpcClientConnection != null) {
            rpcClientConnection.getChannel().close();
        }
    }

    public void handleRpcResponser(RpcResponse rpcResponse){
        String requestId = rpcResponse.getRequestId();
        RPCFuture rpcFuture = pendingRPC.get(requestId);
        if (rpcFuture != null) {
            pendingRPC.remove(requestId);
            rpcFuture.done(rpcResponse);
        }
    }

    //是否连接
    public boolean isConnected(){
        return rpcClientConnection.isConnected();
    }



}
