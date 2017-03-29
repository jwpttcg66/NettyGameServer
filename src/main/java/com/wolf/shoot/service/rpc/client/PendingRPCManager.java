package com.wolf.shoot.service.rpc.client;

import com.wolf.shoot.service.net.RpcRequest;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class PendingRPCManager {

	private ConcurrentHashMap<String, RPCFuture> pendingRPC = new ConcurrentHashMap<>();

	public RPCFuture getRPCFuture(String requestId){
		if(pendingRPC.get(requestId)!=null){
			return pendingRPC.get(requestId);
		}
		return null;
	}
	public void addRPCFuture(RpcRequest request){
		RPCFuture rpcFuture = new RPCFuture(request);
        pendingRPC.put(request.getRequestId(), rpcFuture);
	}
	public ConcurrentHashMap<String, RPCFuture> getPendingRPC(){
		return pendingRPC;
	}
	public void removeRPCFuture(String requestId){
		pendingRPC.remove(requestId);
	}
	public void clearPendRPC(){
		pendingRPC.clear();
	}
}
