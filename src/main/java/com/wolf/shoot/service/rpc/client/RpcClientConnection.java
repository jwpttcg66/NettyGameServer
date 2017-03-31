package com.wolf.shoot.service.rpc.client;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.service.net.RpcRequest;
import com.wolf.shoot.service.rpc.server.SdServer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jiangwenping on 17/3/14.
 * 管理连接
 */
public class RpcClientConnection {

    private Logger logger = Loggers.rpcLogger;

    private NioSocketChannel channel;

    private ReentrantLock statusLock;
    /**
     * 重连线程池工具
     */
    private ExecutorService threadPool;
    EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);

    /**
     * 重连标识
     */
    private volatile boolean reConnect = false;

    /**
     * 是否启用重连
     */
    private volatile boolean reConnectOn = true;

    private RpcClient rpcClient;
    private SdServer sdServer;
    public RpcClientConnection(RpcClient rpcClient, SdServer sdServer, ExecutorService threadPool) {
        if (threadPool == null) {
            throw new IllegalArgumentException("All parameters must accurate.");
        }
        this.rpcClient = rpcClient;
        this.sdServer = sdServer;
        this.threadPool = threadPool;
        this.statusLock = new ReentrantLock();
    }

    /**
     * 创建打开连接
     *
     * @return
     */
    public boolean open() {
        // 判断是否已经连接
        if (isConnected()) {
            throw new IllegalStateException("Already connected. Disconnect first.");
        }
        // 创建Socket连接
        try {

            InetSocketAddress remotePeer = new InetSocketAddress(sdServer.getIp(), sdServer.getCommunicationPort());
            //连接结束
            logger.debug("connect to remote server. remote peer = " + remotePeer);
            Future future = threadPool.submit(new RpcServerConnectTask(sdServer, eventLoopGroup, rpcClient));
            future.get();
            if(isConnected()){
                return false;
            }
            if (logger.isInfoEnabled()) {
                logger.info("Connect success.");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    //是否连接
    public boolean isConnected(){
        if(channel == null){
            return false;
        }
        return channel.isActive();
    }


    /**
     * 发送一条消息
     *
     * @param message
     * @return
     */
    public boolean writeRequest(RpcRequest rpcRequest) {
        if (!isConnected() && reConnectOn) {
            // 是否正在重连中
            if (!reConnect) {
                // 重新连接
                tryReConnect();
            }
            //依然链接不上,返回false
            if (!isConnected()) {
                return false;
            }
        }
        // 发送消息
        if (channel != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("【Send】" + rpcRequest);
            }
            channel.writeAndFlush(rpcRequest);
            return true;
        }
        return false;
    }

    public void tryReConnect() {

        statusLock.lock();  // block until condition holds
        try {
            if(!isConnected()) {
                reConnect = true;
                try {
                    //强制链接,进行等待
                    Future<?> future = threadPool.submit(new ReConnect());
                    future.get();
                } catch (Exception e) {
                    reConnect = false;
                }
            }
        } catch (Exception e) {
            reConnect = false;
        }finally {
            statusLock.unlock();
        }
    }

    /**
     * 重连线程内部类
     *
     * @author Fancy
     */
    private class ReConnect implements Runnable {

        public void run() {
            try {
                open();
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("Restart connection error.");
                }
            } finally {
                // 设置为允许重连
                reConnect = false;
            }
        }
    }

    /**
     * 启动自动重连
     */
    public void setReconnectOn() {
        this.reConnectOn = true;
    }

    /**
     * 关闭自动重连
     */
    public void setReconnectOff() {
        this.reConnectOn = false;
    }

    public NioSocketChannel getChannel() {
        return channel;
    }

    public void setChannel(NioSocketChannel channel) {
        this.channel = channel;
    }

    public void close(){
        if(channel != null) {
            channel.close();
        }
        eventLoopGroup.shutdownGracefully();
    }
}
