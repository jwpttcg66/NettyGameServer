package com.wolf.shoot.service.rpc.client;

import com.snowcattle.game.excutor.utils.ExecutorUtil;
import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.IService;
import org.springframework.stereotype.Service;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * rpc客户端RPCFuture清理服务
 */
@Service
public class DetectRPCPendingService implements IService {

	@Override
	public String getId() {
		return ServiceName.DetectRPCPendingService;
	}

	private ScheduledExecutorService executorService;

	@Override
	public void startup() throws Exception {
		executorService = Executors.newScheduledThreadPool(1);
		executorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				PendingRPCManager pendingRPCManager = LocalMananger.getInstance().getLocalSpringBeanManager().getPendingRPCManager();
				ConcurrentHashMap<String, RPCFuture> pendingRPC = pendingRPCManager.getPendingRPC();
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
	
}

