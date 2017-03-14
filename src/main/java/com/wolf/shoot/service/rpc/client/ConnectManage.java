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

    private ConcurrentHashMap<Long, RpcClient> connectedClients = new ConcurrentHashMap<>();
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
                if (!connectedClients.keySet().contains(serverId)) {
                    connectServerNode(sdServer, downLatch);
                }
        }
            downLatch.await();
        }
    }

    private void connectServerNode(final SdServer sdServer,final CountDownLatch downLatch) {
        threadPoolExecutor.submit(new RpcServerConnectTask(sdServer, downLatch));
    }

    public void addClient(long sdServerId, RpcClient connectClient) {
        InetSocketAddress remoteAddress = (InetSocketAddress) connectClient.getChannel().remoteAddress();
        connectedClients.put(sdServerId, connectClient);
    }


    public RpcClient chooseClient(String serverId) {

        if (StringUtils.isEmpty(serverId)) {
            List<RpcClient> handlers = new ArrayList(this.connectedClients.values());
            int size = handlers.size();
            if(!checkConnnectFlag(handlers)) {
                try {
                    handlers = getConnectedServer(handlers);
                    size = handlers.size();
                } catch (Exception e) {
                    LOGGER.error("Waiting for available node is interrupted! ", e);
                    throw new RuntimeException("Can't connect any servers!", e);
                }
            }
            int index = (roundRobin.getAndAdd(1) + size) % size;
            return handlers.get(index);
        } else {
            try {
                RpcClient rpcClientHandler = this.connectedClients.get(Long.parseLong(serverId));
                return rpcClientHandler;
            } catch (Exception e) {
                LOGGER.error("Waiting for available node is interrupted! ", e);
                throw new RuntimeException("Can't connect any servers!", e);
            }
        }
    }

    public boolean checkConnnectFlag(List<RpcClient> clients ){
        boolean flag = true;
        for(RpcClient rpcClient : clients){
            if(!rpcClient.getChannel().isActive()){
                flag = false;
            }
        }
        return flag;
    }

    public List<RpcClient> getConnectedServer(List<RpcClient> clients ){
        List<RpcClient> rpcClientHandlers = new ArrayList<>();
        for(RpcClient rpcClient : clients){
            if(!rpcClient.getChannel().isActive()){
                rpcClientHandlers.add(rpcClient);
            }
        }
        return rpcClientHandlers;
    }

    public void stop() {
        for (RpcClient rpcClient : connectedClients.values()) {
            rpcClient.close();
        }
        threadPoolExecutor.shutdown();
    }
}
