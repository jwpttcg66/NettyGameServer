package com.wolf.shoot.service.rpc.client;

import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.config.ZooKeeperConfig;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.rpc.server.zookeeper.ZooKeeperNodeBoEnum;
import com.wolf.shoot.service.rpc.server.zookeeper.ZooKeeperNodeInfo;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by jiangwenping on 17/3/30.
 * zookeeper发现服务
 */
@Service
public abstract class ZookeeperRpcServiceDiscovery {

    private static final Logger logger = Loggers.rpcLogger;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private volatile List<ZooKeeperNodeInfo> nodeList = new ArrayList<>();

    private Random random = new Random();

    private ZooKeeper zk;

    private ZooKeeperNodeBoEnum zooKeeperNodeBoEnum;

    public ZookeeperRpcServiceDiscovery(ZooKeeperNodeBoEnum zooKeeperNodeBoEnum) {
        this.zooKeeperNodeBoEnum = zooKeeperNodeBoEnum;
    }

    public void discovery(){
        if(zk == null){
            zk = connectServer();
            if (zk != null) {
                watchNode();
            }
        }
    }

    private ZooKeeperNodeInfo chooseService(){
        ZooKeeperNodeInfo data = null;
        int size = nodeList.size();
        if(size > 0){
            if(size == 1){
                data = nodeList.get(0);
                logger.debug("use only data: " , data);
            }else{
                data = nodeList.get(random.nextInt(size));
                logger.debug("use random data:", data);
            }
        }

        return data;
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

    private void watchNode(){
        try{
            GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
            String rootPath = zooKeeperNodeBoEnum.getRootPath();
            List<String> nodeList = zk.getChildren(rootPath, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    watchNode();
                }
            });

            List<ZooKeeperNodeInfo> tempNodeList = new ArrayList<>();
            for (String node: nodeList){
                ZooKeeperNodeInfo zooKeeperNodeInfo = new ZooKeeperNodeInfo();
                byte[] bytes = zk.getData(zooKeeperNodeBoEnum.getRootPath() + "/" + node, false, null);
                if(bytes != null) {
                    zooKeeperNodeInfo.deserialize(new String(bytes));
                    tempNodeList.add(zooKeeperNodeInfo);
                }
            }

            logger.debug("node data: {}", tempNodeList);
            this.nodeList = tempNodeList;

            logger.debug("Service discovery triggered updating connected server node.");

        }catch (Exception e){
            logger.error(e.toString(), e);
        }
    }
    public void stop(){
        if(zk != null){
            try {
                zk.close();
            }catch (Exception e){
                logger.error(e.toString(), e);
            }
        }
    }

    public List<ZooKeeperNodeInfo> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<ZooKeeperNodeInfo> nodeList) {
        this.nodeList = nodeList;
    }
}
