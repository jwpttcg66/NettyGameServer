package com.wolf.shoot.service.rpc;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.service.IService;
import com.wolf.shoot.service.rpc.client.PendingRPCManager;
import com.wolf.shoot.service.rpc.client.RPCFuture;

import org.springframework.stereotype.Service;

@Service
public class DetectRPCPendingService implements IService {

	@Override
	public String getId() {
		return ServiceName.DetectRPCPendingService;
	}

	@Override
	public void startup() throws Exception {
		
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
		executorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				ConcurrentHashMap<String, RPCFuture> pendingRPC = PendingRPCManager.getInstance().getPendingRPC();
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
	}
	
}

