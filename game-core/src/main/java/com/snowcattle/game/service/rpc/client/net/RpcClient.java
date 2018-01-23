package com.snowcattle.game.service.rpc.client.net;

import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.net.tcp.RpcRequest;
import com.snowcattle.game.service.net.tcp.RpcResponse;
import com.snowcattle.game.service.rpc.client.RPCFuture;
import com.snowcattle.game.service.rpc.client.RPCFutureService;
import com.snowcattle.game.service.rpc.server.RpcNodeInfo;
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
        logger.info("rpc client close");
        if(rpcClientConnection != null) {
            rpcClientConnection.close();
        }
    }

    public void handleRpcResponser(RpcResponse rpcResponse){
        String requestId = rpcResponse.getRequestId();
        RPCFutureService rpcFutureService = LocalMananger.getInstance().getLocalSpringServiceManager().getRPCFutureService();
        RPCFuture rpcFuture = rpcFutureService.getRPCFuture(requestId);
        if (rpcFuture != null) {
            boolean removeFlag = rpcFutureService.removeRPCFuture(requestId, rpcFuture);
            if(removeFlag) {
                rpcFuture.done(rpcResponse);
            }else{
                //表示服务器已经处理过了,可能已经超时了
                logger.error("rpcFuture is remove " + requestId);
            }
        }
    }

    public RpcClientConnection getRpcClientConnection() {
        return rpcClientConnection;
    }

}
