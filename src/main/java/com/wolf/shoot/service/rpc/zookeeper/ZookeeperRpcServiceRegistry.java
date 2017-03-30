package com.wolf.shoot.service.rpc.zookeeper;

import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.config.ZooKeeperConfig;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.common.util.StringUtils;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.IService;
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
    public void register(String data){
        if(!StringUtils.isEmpty(data)){
            zk = connectServer();
            if (zk != null) {
                addRootNode(zk);
                createNode(zk, data);
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

    private void  addRootNode(ZooKeeper zk){
        try{
            Stat s = zk.exists(GlobalConstants.ZooKeeperConstants.ZK_REGISTRY_PATH, false);
            if (s == null){
                create(GlobalConstants.ZooKeeperConstants.ZK_REGISTRY_PATH, new byte[0]);
            }
        }catch (Exception e){
            logger.error(e.toString(), e);
        }

    }
    private void createNode(ZooKeeper zk ,String data){
        try {
            byte[] bytes = data.getBytes();
            String nodePath = GlobalConstants.ZooKeeperConstants.ZK_DATA_PATH;
            String path = create(nodePath, bytes);
            logger.debug("create zookeeper node ({} => {})", path, data);
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
    public String create(String path,byte[] data) throws  Exception{
        /**
         * 此处采用的是CreateMode是PERSISTENT  表示The znode will not be automatically deleted upon client's disconnect.
         * EPHEMERAL 表示The znode will be deleted upon the client's disconnect.
         */
       return this.zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }


    public void deleteNode(ZooKeeper zk, String data){
        try {
            byte[] bytes = data.getBytes();
            zk.delete(GlobalConstants.ZooKeeperConstants.ZK_DATA_PATH, -1);
            logger.debug("delete zookeeper node ({} => {})", data);
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

    }

    @Override
    public void shutdown() throws Exception {
        if(zk != null){
            try {
                zk.close();
            }catch (Exception e){

            }
        }
    }
}
