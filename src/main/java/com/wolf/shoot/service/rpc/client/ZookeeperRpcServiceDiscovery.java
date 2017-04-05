package com.wolf.shoot.service.rpc.client;

import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.config.GameServerDiffConfig;
import com.wolf.shoot.common.config.ZooKeeperConfig;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.IService;
import com.wolf.shoot.service.rpc.server.zookeeper.ZooKeeperNodeBoEnum;
import com.wolf.shoot.service.rpc.server.zookeeper.ZooKeeperNodeInfo;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by jiangwenping on 17/3/30.
 * zookeeper发现服务
 */
@Service
public class ZookeeperRpcServiceDiscovery implements IService{

    private static final Logger logger = Loggers.rpcLogger;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private volatile Map<ZooKeeperNodeBoEnum, List<ZooKeeperNodeInfo>> nodeMap = new ConcurrentHashMap<>();

    private Random random = new Random();

    private ZooKeeper zk;

    public void discovery(ZooKeeperNodeBoEnum zooKeeperNodeBoEnum){
        if(zk == null){
            zk = connectServer();
            if (zk != null) {
                watchNode(zooKeeperNodeBoEnum);
            }
        }
    }

    private ZooKeeperNodeInfo chooseService(ZooKeeperNodeBoEnum zooKeeperNodeBoEnum){
        ZooKeeperNodeInfo data = null;
        List<ZooKeeperNodeInfo> nodeList = nodeMap.get(zooKeeperNodeBoEnum);
        if(nodeList != null) {
            int size = nodeList.size();
            if (size > 0) {
                if (size == 1) {
                    data = nodeList.get(0);
                    logger.debug("use only data: ", data);
                } else {
                    data = nodeList.get(random.nextInt(size));
                    logger.debug("use random data:", data);
                }
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

    private void watchNode(final ZooKeeperNodeBoEnum zooKeeperNodeBoEnum){
        try{
            GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
            String rootPath = zooKeeperNodeBoEnum.getRootPath();
            List<String> nodeList = zk.getChildren(rootPath, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    watchNode(zooKeeperNodeBoEnum);
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
            this.nodeMap.put(zooKeeperNodeBoEnum, tempNodeList);
            logger.debug("Service discovery triggered updating connected server node.");

            //通知链接服务器进行链接
            RpcClientConnectService rpcClientConnectService = LocalMananger.getInstance().getLocalSpringServicerAfterManager().getRpcClientConnectService();
            rpcClientConnectService.notifyConnect(zooKeeperNodeBoEnum, nodeMap.get(zooKeeperNodeBoEnum));
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

    public List<ZooKeeperNodeInfo> getNodeList(final ZooKeeperNodeBoEnum zooKeeperNodeBoEnum) {
        return nodeMap.get(zooKeeperNodeBoEnum);
    }


    @Override
    public String getId() {
        return ServiceName.ZookeeperRpcServiceDiscovery;
    }

    @Override
    public void startup() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        GameServerDiffConfig gameServerDiffConfig = gameServerConfigService.getGameServerDiffConfig();
        if(gameServerDiffConfig.isZookeeperFlag()) {
            ZooKeeperNodeBoEnum[] zooKeeperNodeBoEnums = ZooKeeperNodeBoEnum.values();
            for (ZooKeeperNodeBoEnum zooKeeperNodeBoEnum : zooKeeperNodeBoEnums) {
                discovery(zooKeeperNodeBoEnum);
            }
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
