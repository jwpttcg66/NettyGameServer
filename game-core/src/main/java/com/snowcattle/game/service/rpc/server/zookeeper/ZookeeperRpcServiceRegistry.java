package com.snowcattle.game.service.rpc.server.zookeeper;

import com.snowcattle.game.service.config.GameServerConfigService;
import com.snowcattle.game.service.rpc.server.RpcServerRegisterConfig;
import com.snowcattle.game.common.config.GameServerConfig;
import com.snowcattle.game.common.config.GameServerDiffConfig;
import com.snowcattle.game.common.config.ZooKeeperConfig;
import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.common.util.StringUtils;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.IService;
import com.snowcattle.game.service.rpc.server.SdRpcServiceProvider;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

/**
 * Created by jiangwenping on 17/3/30.
 * 注册自己到zookeeper
 */
@Service
public class ZookeeperRpcServiceRegistry implements IService{
    private static final Logger logger = Loggers.rpcLogger;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private ZooKeeper zk;
    public void registerZooKeeper(){
        if(zk == null){
            zk = connectServer();
        }
    }
    public void register(String registry_path, String nodePath, String nodeData){
        if(!StringUtils.isEmpty(registry_path)){
            if (zk != null) {
                addRootNode(zk, registry_path);
                try {
                    if(zk.exists(nodePath, false) != null) {
                        deleteNode(zk, nodePath);
                    }
                } catch (KeeperException e) {
//                        e.printStackTrace();
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                }
                createNode(zk, nodePath, nodeData);
            }
        }
    }

    /**
     * 链接zookeeper
     * @return
     */
    private ZooKeeper connectServer(){
        ZooKeeper zk = null;
        try {
            GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
            ZooKeeperConfig zooKeeperConfig = gameServerConfigService.getZooKeeperConfig();
            String registryAdress = zooKeeperConfig.getProperty(GlobalConstants.ZooKeeperConstants.registryAdress);
            zk = new ZooKeeper(registryAdress, GlobalConstants.ZooKeeperConstants.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    countDownLatch.countDown();
                }
            });
        }catch (Exception e){
            logger.error(e.toString(), e);
        }
        return zk;
    }

    private void  addRootNode(ZooKeeper zk, String registry_path){
        try{
            Stat s = zk.exists(registry_path, false);
            if (s == null){
                createRootNode(registry_path, new byte[0]);
            }
        }catch (Exception e){
            logger.error(e.toString(), e);
        }

    }
    private void createNode(ZooKeeper zk ,String nodePath, String nodeData){
        try {
            byte[] bytes = nodeData.getBytes();
            String path = create(nodePath, bytes);
            logger.debug("create zookeeper node ({} => {})", path, bytes);
        }catch (Exception e){
            logger.error(e.toString(), e);
        }
    }

    /**
     *
     *<b>function:</b>创建持久态的znode,比支持多层创建.比如在创建/parent/child的情况下,无/parent.无法通过
     *@author cuiran
     *@createDate 2013-01-16 15:08:38
     *@param path
     *@param data
     *@throws KeeperException
     *@throws InterruptedException
     */
    public String createRootNode(String path,byte[] data) throws  Exception{
        /**
         * 此处采用的是CreateMode是PERSISTENT  表示The znode will not be automatically deleted upon client's disconnect.
         * EPHEMERAL 表示The znode will be deleted upon the client's disconnect.
         */
       return this.zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }


    /**
     *
     *<b>function:</b>创建持久态的znode,比支持多层创建.比如在创建/parent/child的情况下,无/parent.无法通过
     *@author cuiran
     *@createDate 2013-01-16 15:08:38
     *@param path
     *@param data
     *@throws KeeperException
     *@throws InterruptedException
     */
    public String create(String path,byte[] data) throws  Exception{
        /**
         * 此处采用的是CreateMode是PERSISTENT  表示The znode will not be automatically deleted upon client's disconnect.
         * EPHEMERAL 表示The znode will be deleted upon the client's disconnect.
         */
        return this.zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }


    public void deleteNode(ZooKeeper zk, String nodePath){
        try {
            zk.delete(nodePath, -1);
            logger.debug("delete zookeeper node pathc ({} => {})", nodePath);
        }catch (Exception e){
            logger.error(e.toString(), e);
        }
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    @Override
    public String getId() {
        return ServiceName.ZookeeperRpcServiceRegistry;
    }

    @Override
    public void startup() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        GameServerDiffConfig gameServerDiffConfig = gameServerConfigService.getGameServerDiffConfig();
        if(gameServerDiffConfig.isZookeeperFlag()) {
            registerZooKeeper();
            registerNode();
        }
    }

    public void registerNode() throws Exception{

        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        RpcServerRegisterConfig rpcServerRegisterConfig = gameServerConfigService.getRpcServerRegisterConfig();
        SdRpcServiceProvider sdRpcServiceProvider = rpcServerRegisterConfig.getSdRpcServiceProvider();
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();
        String serverId = gameServerConfig.getServerId();
        String host = gameServerConfig.getRpcBindIp();
        String ports = gameServerConfig.getRpcPorts();
        if(sdRpcServiceProvider.isWorldOpen()){
            ZooKeeperNodeInfo zooKeeperNodeInfo = new ZooKeeperNodeInfo(ZooKeeperNodeBoEnum.WORLD, serverId, host, ports);
            register(zooKeeperNodeInfo.getZooKeeperNodeBoEnum().getRootPath(), zooKeeperNodeInfo.getNodePath(), zooKeeperNodeInfo.serialize());
        }
        if(sdRpcServiceProvider.isGameOpen()){
            ZooKeeperNodeInfo zooKeeperNodeInfo = new ZooKeeperNodeInfo(ZooKeeperNodeBoEnum.GAME, serverId, host, ports);
            register(zooKeeperNodeInfo.getZooKeeperNodeBoEnum().getRootPath(), zooKeeperNodeInfo.getNodePath(), zooKeeperNodeInfo.serialize());
        }

        if(sdRpcServiceProvider.isDbOpen()){
            ZooKeeperNodeInfo zooKeeperNodeInfo = new ZooKeeperNodeInfo(ZooKeeperNodeBoEnum.DB, serverId, host, ports);
            register(zooKeeperNodeInfo.getZooKeeperNodeBoEnum().getRootPath(), zooKeeperNodeInfo.getNodePath(), zooKeeperNodeInfo.serialize());
        }

    }
    @Override
    public void shutdown() throws Exception {
        if(zk != null){
            try {
                zk.close();
            }catch (Exception e){
                logger.error(e.toString(), e);
            }
        }
    }
}
