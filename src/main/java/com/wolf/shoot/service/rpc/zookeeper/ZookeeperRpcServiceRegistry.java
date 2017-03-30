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
                zk.create(GlobalConstants.ZooKeeperConstants.ZK_REGISTRY_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

//            List<String> children =  zk.getChildren(GlobalConstants.ZooKeeperConstants.ZK_REGISTRY_PATH, false);
//            System.out.println(children);
        }catch (Exception e){
            logger.error(e.toString(), e);
        }

    }
    private void createNode(ZooKeeper zk ,String data){
        try {
            byte[] bytes = data.getBytes();
            String nodePath = GlobalConstants.ZooKeeperConstants.ZK_DATA_PATH;
            String path = zk.create(nodePath, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            logger.debug("create zookeeper node ({} => {})", path, data);

//            List<String> children =  zk.getChildren(GlobalConstants.ZooKeeperConstants.ZK_REGISTRY_PATH, false);
//            System.out.println(children);
        }catch (Exception e){
            logger.error(e.toString(), e);
        }
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

    }
}
