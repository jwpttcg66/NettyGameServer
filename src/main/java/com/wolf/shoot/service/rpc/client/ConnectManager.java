package com.wolf.shoot.service.rpc.client;

import com.wolf.shoot.common.util.StringUtils;
import com.wolf.shoot.service.rpc.SdServer;
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
public class ConnectManager {

    private final Logger LOGGER = LoggerFactory.getLogger(ConnectManager.class);
    private static final ConnectManager CONNECT_MANAGER = new ConnectManager();

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));

    private Map<Integer, RpcClient> serverNodes = new HashMap<>();

    private AtomicInteger roundRobin = new AtomicInteger();

    private ConnectManager() {
    }

    public static ConnectManager getInstance() {
        return CONNECT_MANAGER;
    }

    public void initServers(List<SdServer> allServerAddress) throws InterruptedException {
        if (allServerAddress != null) {
            serverNodes.clear();
            for(SdServer sdServer: allServerAddress){
                RpcClient rpcClient = new RpcClient(sdServer, threadPoolExecutor);
                serverNodes.put(sdServer.getServerId(), rpcClient);
            }
        }
    }

    public RpcClient chooseClient(String serverId) {

        if (StringUtils.isEmpty(serverId)) {
            List<RpcClient> handlers = new ArrayList(this.serverNodes.values());
            int size = handlers.size();
            int index = (roundRobin.getAndAdd(1) + size) % size;
            return handlers.get(index);
        } else {
            try {
                RpcClient rpcClient = this.serverNodes.get(Integer.parseInt(serverId));
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
