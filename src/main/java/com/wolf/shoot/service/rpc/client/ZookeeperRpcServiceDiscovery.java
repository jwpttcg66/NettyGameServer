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

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private CuratorFramework client;
    
    public void discovery(ZooKeeperNodeBoEnum zooKeeperNodeBoEnum){
        if(zk == null){
            zk = connectServer();
            if (zk != null) {
                watchNode(zooKeeperNodeBoEnum);
            }
        }
    	if(client != null){
    		try {
				setListenter(client,zooKeeperNodeBoEnum);
			} catch (Exception e) {
				logger.debug("CuratorFramework Listenning Exception:"+e.getMessage());
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
        	try {
        		client = creatClient();
			} catch (Exception e) {
				logger.debug("Create CuratorFramework Client Exception:"+e.getMessage());
			}
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
    private static CuratorFramework creatClient() {
		ACLProvider aclProvider = new ACLProvider() {
			private List<ACL> acl;

			@Override
			public List<ACL> getDefaultAcl() {
				if (acl == null) {
					ArrayList<ACL> acl = ZooDefs.Ids.CREATOR_ALL_ACL;
					acl.clear();
					acl.add(new ACL(Perms.ALL, new Id("auth", "admin:admin")));
					this.acl = acl;
				}
				return acl;
			}

			@Override
			public List<ACL> getAclForPath(String path) {
				return acl;
			}
		};
		//String scheme = "digest";
		//byte[] auth = "admin:admin".getBytes();
		int connectionTimeoutMs = 5000;
		//String connectString = "192.168.0.158:2181";
		GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        ZooKeeperConfig zooKeeperConfig = gameServerConfigService.getZooKeeperConfig();
        String registryAdress = zooKeeperConfig.getProperty(GlobalConstants.ZooKeeperConstants.registryAdress);
		String namespace = "";
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.aclProvider(aclProvider)//.authorization(scheme, auth)
				.connectionTimeoutMs(connectionTimeoutMs)
				.connectString(registryAdress)//.namespace(namespace)
				.retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).build();
		client.start();
		return client;
	}
    private static void setListenter(CuratorFramework client,ZooKeeperNodeBoEnum zooKeeperNodeBoEnum)throws Exception {
		ExecutorService pool = Executors.newCachedThreadPool();
		TreeCache cache = new TreeCache(client, zooKeeperNodeBoEnum.getRootPath());
		cache.getListenable().addListener(new TreeCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, TreeCacheEvent event)
					throws Exception {
				ChildData data = event.getData();
				if (data != null) {
					switch (event.getType()) {
					case NODE_ADDED:
						logger.debug("NODE_ADDED : " + data.getPath()+ "  数据:" + new String(data.getData()));
						break;
					case NODE_REMOVED:
						logger.debug("NODE_REMOVED : " + data.getPath()+ "  数据:" + new String(data.getData()));
						break;
					case NODE_UPDATED:
						logger.debug("NODE_UPDATED : " + data.getPath()+ "  数据:" + new String(data.getData()));
						break;
					default:
						break;
					}
				} else {
					switch (event.getType()) {
					case CONNECTION_SUSPENDED:
						logger.debug("data is null : " + "CONNECTION_SUSPENDED");
						break;
					case CONNECTION_RECONNECTED:
						logger.debug("data is null : " + "CONNECTION_RECONNECTED");
						break;
					case CONNECTION_LOST:
						logger.debug("data is null : " + "CONNECTION_LOST");
						break;
					default:
						break;
					}
				}
			}
		});
		// 开始监听
		cache.start();
	}
}
