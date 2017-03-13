package com.wolf.shoot.service.rpc.client;

import com.wolf.shoot.common.util.StringUtils;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.rpc.RpcServiceDiscovery;
import com.wolf.shoot.service.rpc.SdServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 */
public class ConnectManage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectManage.class);
    private static final ConnectManage connectManage = new ConnectManage();

    EventLoopGroup eventLoopGroup = new NioEventLoopGroup(4);

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));

    private ConcurrentHashMap<InetSocketAddress, RpcClientHandler> connectedHandlers = new ConcurrentHashMap<>();
    private Map<InetSocketAddress, RpcClientHandler> connectedServerNodes = new ConcurrentHashMap<>();


    private AtomicInteger roundRobin = new AtomicInteger(0);

    private volatile boolean isRuning = true;
    //是否已经load完成
    private volatile boolean loading = true;

    private ConnectManage() {
    }

    public static ConnectManage getInstance() {
        return connectManage;
    }

//    public void updateConnectedServer(List<SdServer> allServerAddress) {
//        if (allServerAddress != null) {
//            if (allServerAddress.size() > 0) {  // Get available server node
//                //update local serverNodes cache
//                HashSet<InetSocketAddress> newAllServerNodeSet = new HashSet<InetSocketAddress>();
//                for (int i = 0; i < allServerAddress.size(); ++i) {
//                    SdServer sdServer = allServerAddress.get(i);
//                    String host = sdServer.getIp();
//                    int port = sdServer.getCommunicationPort();
//                    final InetSocketAddress remotePeer = new InetSocketAddress(host, port);
//                    newAllServerNodeSet.add(remotePeer);
//                }
//                CountDownLatch downLatch = new CountDownLatch(newAllServerNodeSet.size());
//                // Add new server node
//                for (final InetSocketAddress serverNodeAddress : newAllServerNodeSet) {
//                    if (!connectedServerNodes.keySet().contains(serverNodeAddress)) {
//                        connectServerNode(serverNodeAddress, downLatch);
//                    }
//                }
//
//                // Close and remove invalid server nodes
//                for (int i = 0; i < connectedHandlers.size(); ++i) {
//                    RpcClientHandler connectedServerHandler = connectedHandlers.get(i);
//                    SocketAddress remotePeer = connectedServerHandler.getRemotePeer();
//                    if (!newAllServerNodeSet.contains(remotePeer)) {
//                        LOGGER.info("Remove invalid server node " + remotePeer);
//                        RpcClientHandler handler = connectedServerNodes.get(remotePeer);
//                        handler.close();
//                        connectedServerNodes.remove(remotePeer);
//                        connectedHandlers.remove(remotePeer);
//                    }
//                }
//
//            } else { // No available server node ( All server nodes are down )
//                LOGGER.error("No available server node. All server nodes are down !!!");
//                for (final RpcClientHandler connectedServerHandler : connectedHandlers.values()) {
//                    SocketAddress remotePeer = connectedServerHandler.getRemotePeer();
//                    RpcClientHandler handler = connectedServerNodes.get(remotePeer);
//                    handler.close();
//                    connectedServerNodes.remove(remotePeer);
//                }
//                connectedHandlers.clear();
//            }
//        }
//        //
//        loading = false;
//    }


    public void initServers(List<SdServer> allServerAddress) throws InterruptedException {
        if (allServerAddress != null) {
            HashSet<InetSocketAddress> newAllServerNodeSet = new HashSet<InetSocketAddress>();
            for (SdServer sdServer : allServerAddress) {
                newAllServerNodeSet.add(new InetSocketAddress(sdServer.getIp(), sdServer.getCommunicationPort()));
            }

            CountDownLatch downLatch = new CountDownLatch(newAllServerNodeSet.size());

            // Add new server node
            for (final InetSocketAddress serverNodeAddress : newAllServerNodeSet) {
                if (!connectedServerNodes.keySet().contains(serverNodeAddress)) {
                    connectServerNode(serverNodeAddress, downLatch);
                }
            }
            downLatch.await();
            loading = false;
        }

    }

    private void connectServerNode(final InetSocketAddress remotePeer,final CountDownLatch downLatch) {
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                Bootstrap b = new Bootstrap();
                b.group(eventLoopGroup)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .handler(new LoggingHandler(LogLevel.DEBUG))
                        .handler(new RpcClientInitializer());
                ChannelFuture channelFuture = b.connect(remotePeer);
                channelFuture.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                        if (channelFuture.isSuccess()) {
                            LOGGER.debug("Successfully connect to remote server. remote peer = " + remotePeer);
                            RpcClientHandler handler = channelFuture.channel().pipeline().get(RpcClientHandler.class);
                            addHandler(handler);
                            downLatch.countDown();
                        }
                    }
                });
            }
        });
    }

    private void addHandler(RpcClientHandler handler) {
        InetSocketAddress remoteAddress = (InetSocketAddress) handler.getChannel().remoteAddress();
        connectedHandlers.put(remoteAddress, handler);
        connectedServerNodes.put(remoteAddress, handler);
    }


    public RpcClientHandler chooseHandler(String serverId) {
        List<RpcClientHandler> handlers = new ArrayList(this.connectedHandlers.values());
        if (StringUtils.isEmpty(serverId)) {
            int size = handlers.size();
            while (isRuning && size <= 0) {
                try {

                    while (loading) {
                        Thread.currentThread().sleep(500);
                    }

                    handlers = new ArrayList(this.connectedHandlers.values());
                    size = handlers.size();
                } catch (InterruptedException e) {
                    LOGGER.error("Waiting for available node is interrupted! ", e);
                    throw new RuntimeException("Can't connect any servers!", e);
                }
            }
            int index = (roundRobin.getAndAdd(1) + size) % size;
            return handlers.get(index);
        } else {
            try {
                while (loading) {
                    Thread.currentThread().sleep(500);
                }
                RpcServiceDiscovery rpcService = LocalMananger.getInstance().getLocalSpringServiceManager().getRpcServiceDiscovery();
                SdServer sdServer = rpcService.getSdServer(serverId);
                InetSocketAddress inetSocketAddress = new InetSocketAddress(sdServer.getIp(), sdServer.getCommunicationPort());
                RpcClientHandler rpcClientHandler = this.connectedHandlers.get(inetSocketAddress);
                return rpcClientHandler;
            } catch (Exception e) {
                LOGGER.error("Waiting for available node is interrupted! ", e);
                throw new RuntimeException("Can't connect any servers!", e);
            }
        }
    }

    public void stop() {
        isRuning = false;
        for (RpcClientHandler rpcClientHandler : connectedHandlers.values()) {
            rpcClientHandler.close();
        }
        threadPoolExecutor.shutdown();
        eventLoopGroup.shutdownGracefully();
    }
}
