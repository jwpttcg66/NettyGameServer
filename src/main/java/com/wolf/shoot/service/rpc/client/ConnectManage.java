package com.wolf.shoot.service.rpc.client;

import com.wolf.shoot.common.util.StringUtils;
import com.wolf.shoot.service.rpc.SdServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 */
public class ConnectManage {

    private final Logger LOGGER = LoggerFactory.getLogger(ConnectManage.class);
    private static final ConnectManage connectManage = new ConnectManage();

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));

    private ConcurrentHashMap<Long, RpcClientHandler> connectedHandlers = new ConcurrentHashMap<>();
    private List<SdServer> serverNodes = new ArrayList<>();

    private AtomicInteger roundRobin;

    private ConnectManage() {
    }

    public static ConnectManage getInstance() {
        return connectManage;
    }

    public void initServers(List<SdServer> allServerAddress) throws InterruptedException {
        if (allServerAddress != null) {
            serverNodes.clear();
            serverNodes.addAll(allServerAddress);
            CountDownLatch downLatch = new CountDownLatch(serverNodes.size());

            // Add new server node
            for(SdServer sdServer:  serverNodes) {
                long serverId = sdServer.getServerId();
                if (!connectedHandlers.keySet().contains(serverId)) {
                    connectServerNode(sdServer, downLatch);
                }
        }
            downLatch.await();
        }
    }

    private void connectServerNode(final SdServer sdServer,final CountDownLatch downLatch) {
        threadPoolExecutor.submit(new RpcServerConnectTask(sdServer, downLatch));
    }

    public void addHandler(long sdServerId, RpcClientHandler handler) {
        InetSocketAddress remoteAddress = (InetSocketAddress) handler.getChannel().remoteAddress();
        connectedHandlers.put(sdServerId, handler);
    }


    public RpcClientHandler chooseHandler(String serverId) {
        List<RpcClientHandler> handlers = new ArrayList(this.connectedHandlers.values());
        if (StringUtils.isEmpty(serverId)) {
            int size = handlers.size();
                try {
                    handlers = new ArrayList(this.connectedHandlers.values());
                    size = handlers.size();
                } catch (Exception e) {
                    LOGGER.error("Waiting for available node is interrupted! ", e);
                    throw new RuntimeException("Can't connect any servers!", e);
                }
            int index = (roundRobin.getAndAdd(1) + size) % size;
            return handlers.get(index);
        } else {
            try {
                RpcClientHandler rpcClientHandler = this.connectedHandlers.get(Long.parseLong(serverId));
                return rpcClientHandler;
            } catch (Exception e) {
                LOGGER.error("Waiting for available node is interrupted! ", e);
                throw new RuntimeException("Can't connect any servers!", e);
            }
        }
    }

    public void stop() {
        for (RpcClientHandler rpcClientHandler : connectedHandlers.values()) {
            rpcClientHandler.close();
        }
        threadPoolExecutor.shutdown();
    }
}
