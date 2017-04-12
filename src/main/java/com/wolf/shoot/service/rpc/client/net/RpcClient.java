package com.wolf.shoot.service.rpc.client.net;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.net.RpcRequest;
import com.wolf.shoot.service.net.RpcResponse;
import com.wolf.shoot.service.rpc.client.RPCFuture;
import com.wolf.shoot.service.rpc.client.RPCFutureService;
import com.wolf.shoot.service.rpc.server.RpcNodeInfo;
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


    public RpcClient(RpcNodeInfo rpcNodeInfo, ExecutorService threadPool){
        rpcClientConnection = new RpcClientConnection(this, rpcNodeInfo, threadPool);
    }
    public RPCFuture sendRequest(RpcRequest request) {
        RPCFuture rpcFuture = new RPCFuture(request);
        RPCFutureService rpcFutureService = LocalMananger.getInstance().getLocalSpringServiceManager().getRPCFutureService();
        rpcFutureService.addRPCFuture(request.getRequestId(), rpcFuture);
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
        RPCFutureService rpcFutureService = LocalMananger.getInstance().getLocalSpringServiceManager().getRPCFutureService();
        RPCFuture rpcFuture = rpcFutureService.getRPCFuture(requestId);
        if (rpcFuture != null) {
            rpcFutureService.removeRPCFuture(requestId);
            rpcFuture.done(rpcResponse);
        }
    }

    public RpcClientConnection getRpcClientConnection() {
        return rpcClientConnection;
    }

}
