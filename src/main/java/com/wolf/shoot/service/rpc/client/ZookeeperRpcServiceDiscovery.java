package com.wolf.shoot.service.rpc.client;

import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.config.ZooKeeperConfig;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.manager.LocalMananger;
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
public class ZookeeperRpcServiceDiscovery {

    private static final Logger logger = Loggers.rpcLogger;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private volatile List<String> dataList = new ArrayList<>();

    private Random random = new Random();

    private ZooKeeper zk;

    public void discovery(){
        if(zk == null){
            zk = connectServer();
            if (zk != null) {
                watchNode();
            }
        }
    }

    private String chooseService(){
        String data = null;
        int size = dataList.size();
        if(size > 0){
            if(size == 1){
                data = dataList.get(0);
                logger.debug("use only data: " , data);
            }else{
                data = dataList.get(random.nextInt(size));
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
            ZooKeeperConfig zooKeeperConfig = gameServerConfigService.getZooKeeperConfig();
            String rootPath = GlobalConstants.ZooKeeperConstants.ZK_REGISTRY_PATH;
//            String registryAdress = zooKeeperConfig.getProperty(GlobalConstants.ZooKeeperConstants.registryAdress);
            List<String> nodeList = zk.getChildren(rootPath, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    watchNode();
                }
            });

            List<String> dataList = new ArrayList<>();
            for (String node: nodeList){
                byte[] bytes = zk.getData(GlobalConstants.ZooKeeperConstants.ZK_DATA_PATH, false, null);
                dataList.add(new String(bytes));
            }
            this.dataList = dataList;

            logger.debug("node data: {}", dataList);
            this.dataList = dataList;

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

    public List<String> getDataList() {
        return dataList;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }
}
