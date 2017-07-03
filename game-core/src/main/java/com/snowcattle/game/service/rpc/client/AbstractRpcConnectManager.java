package com.snowcattle.game.service.rpc.client;

import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.config.GameServerConfigService;
import com.snowcattle.game.service.rpc.server.RpcNodeInfo;
import com.snowcattle.game.service.rpc.server.SdServer;
import com.snowcattle.game.service.rpc.server.zookeeper.ZooKeeperNodeInfo;
import com.snowcattle.game.service.rpc.client.net.RpcClient;
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
//                serverNodes.clear();
                for (SdServer sdServer : allServerAddress) {
                    if(serverNodes.containsKey(sdServer.getServerId())){
                        continue;
                    }
                    RpcNodeInfo rpcNodeInfo  = new RpcNodeInfo();
                    rpcNodeInfo.setServerId(String.valueOf(sdServer.getServerId()));
                    rpcNodeInfo.setHost(sdServer.getIp());
                    rpcNodeInfo.setPort(String.valueOf(sdServer.getRpcPort()));
                    RpcClient rpcClient = new RpcClient(rpcNodeInfo, threadPoolExecutor);
                    serverNodes.put(sdServer.getServerId(), rpcClient);
                }
            }
        }
    }

    public void initZookeeperRpcServers(List<ZooKeeperNodeInfo> zooKeeperNodeInfoList) throws InterruptedException {
        //增加同步，当前
        synchronized (this) {
            if (zooKeeperNodeInfoList != null) {
//                serverNodes.clear();
                for (ZooKeeperNodeInfo zooKeeperNodeInfo : zooKeeperNodeInfoList) {
                    if(serverNodes.containsKey(zooKeeperNodeInfo.getServerId())){
                        continue;
                    }
                    RpcNodeInfo rpcNodeInfo  = new RpcNodeInfo();
                    rpcNodeInfo.setServerId(zooKeeperNodeInfo.getServerId());
                    rpcNodeInfo.setHost(zooKeeperNodeInfo.getHost());
                    rpcNodeInfo.setPort(zooKeeperNodeInfo.getPort());
                    RpcClient rpcClient = new RpcClient(rpcNodeInfo, threadPoolExecutor);
                    serverNodes.put(Integer.parseInt(zooKeeperNodeInfo.getServerId()), rpcClient);
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
        if(threadPoolExecutor != null) {
            threadPoolExecutor.shutdown();
        }
    }
}
