package com.snowcattle.game.service.rpc.client;

import com.snowcattle.game.common.ThreadNameFactory;
import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.common.util.ExecutorUtil;
import com.snowcattle.game.service.IService;
import org.springframework.stereotype.Service;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * rpc客户端RPCFuture管理服务
 */
@Service
public class RPCFutureService implements IService {

	@Override
	public String getId() {
		return ServiceName.RPCFutureService;
	}

	private ScheduledExecutorService executorService;

	@Override
	public void startup() throws Exception {
		ThreadNameFactory threadNameFactory = new ThreadNameFactory(GlobalConstants.Thread.DETECT_RPCPENDING);
		executorService = Executors.newScheduledThreadPool(1, threadNameFactory);
		executorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				ConcurrentHashMap<String, RPCFuture> pendingRPC = getPendingRPC();
				Set<Entry<String, RPCFuture>> entrySet = pendingRPC.entrySet();
				for (Entry<String, RPCFuture> entry : entrySet) {
					RPCFuture rpcFuture = entry.getValue();
					if(rpcFuture.isTimeout()){
						pendingRPC.remove(entry.getKey());
					}
				}
			}
		}, 1, 1,TimeUnit.MINUTES);
	}

	@Override
	public void shutdown() throws Exception {
		ExecutorUtil.shutdownAndAwaitTermination(executorService, 60L, TimeUnit.MILLISECONDS);
	}

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

