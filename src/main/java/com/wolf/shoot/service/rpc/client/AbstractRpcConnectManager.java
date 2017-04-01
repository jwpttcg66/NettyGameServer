package com.wolf.shoot.service.rpc.client;

import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.rpc.client.net.RpcClient;
import com.wolf.shoot.service.rpc.server.SdServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 */
public abstract class AbstractRpcConnectManager {

    private final Logger LOGGER = LoggerFactory.getLogger(AbstractRpcConnectManager.class);

    private ThreadPoolExecutor threadPoolExecutor;

    private Map<Integer, RpcClient> serverNodes = new HashMap<>();

    private AtomicInteger roundRobin = new AtomicInteger();

    public void initManager(){
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        int threadSize = gameServerConfigService.getGameServerConfig().getRpcConnectThreadSize();
        threadPoolExecutor = new ThreadPoolExecutor(threadSize, threadSize, 600L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));
    }
    public void initServers(List<SdServer> allServerAddress) throws InterruptedException {
        //增加同步，当前
        synchronized (this) {
            if (allServerAddress != null) {
                serverNodes.clear();
                for (SdServer sdServer : allServerAddress) {
                    RpcClient rpcClient = new RpcClient(sdServer, threadPoolExecutor);
                    serverNodes.put(sdServer.getServerId(), rpcClient);
                }
            }
        }
    }

    public RpcClient chooseClient(int serverId) {

        if (serverId == 0) {
            List<RpcClient> handlers = new ArrayList(this.serverNodes.values());
            int size = handlers.size();
            int index = (roundRobin.getAndAdd(1) + size) % size;
            return handlers.get(index);
        } else {
            try {
                RpcClient rpcClient = this.serverNodes.get(serverId);
                return rpcClient;
            } catch (Exception e) {
                LOGGER.error("Waiting for available node is interrupted! ", e);
                throw new RuntimeException("Can't connect any servers!", e);
            }
        }
    }

    public void stop() {
        for (RpcClient rpcClient : serverNodes.values()) {
            rpcClient.close();
        }
        threadPoolExecutor.shutdown();
    }
}
