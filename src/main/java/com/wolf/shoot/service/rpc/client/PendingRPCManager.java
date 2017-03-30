package com.wolf.shoot.service.rpc.client;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * rpc客户端RPCFuture管理
 */
@Service
public class PendingRPCManager {

	private ConcurrentHashMap<String, RPCFuture> pendingRPC = new ConcurrentHashMap<>();

	public RPCFuture getRPCFuture(String requestId){
		if(pendingRPC.get(requestId)!=null){
			return pendingRPC.get(requestId);
		}
		return null;
	}
	public void addRPCFuture(String requestId, RPCFuture rpcFuture){
        pendingRPC.put(requestId, rpcFuture);
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
