package com.wolf.shoot.service.rpc;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.service.IService;
import com.wolf.shoot.service.rpc.client.PendingRPCManager;
import com.wolf.shoot.service.rpc.client.RPCFuture;

import org.springframework.stereotype.Service;

@Service
public class DetectRPCPendingService implements IService {

	private boolean isRun = true;
	@Override
	public String getId() {
		return ServiceName.DetectRPCPendingService;
	}

	@Override
	public void startup() throws Exception {
		ExecutorService exec = Executors.newFixedThreadPool(1);
		exec.execute(new Runnable() {
			@Override
			public void run() {
				while (isRun) {
					ConcurrentHashMap<String, RPCFuture> pendingRPC = PendingRPCManager.getInstance().getPendingRPC();
					Set<Entry<String, RPCFuture>> entrySet = pendingRPC.entrySet();
					for (Entry<String, RPCFuture> entry : entrySet) {
						RPCFuture rpcFuture = entry.getValue();
						if(rpcFuture.isTimeout()){
							pendingRPC.remove(entry.getKey());
						}
					}
				}
				
			}
		});
	}

	@Override
	public void shutdown() throws Exception {
		isRun = false;
	}
	
}

