package com.snowcattle.game.common.zookeeper.Carutor;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CuratorListenerUtils {

	public static String connectString = "127.0.0.1:2181";
	public static void main(String[] args) throws Exception {
		CuratorFramework client = creatClient();
		setListenter(client);
		setListenterThreeTwo(client);
		Thread.sleep(99999999999l);
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
		String scheme = "digest";
		byte[] auth = "admin:admin".getBytes();
		int connectionTimeoutMs = 5000;
//		String connectString = "192.168.0.158:2181";
		String namespace = "";
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.aclProvider(aclProvider)//.authorization(scheme, auth)
				.connectionTimeoutMs(connectionTimeoutMs)
				.connectString(connectString)//.namespace(namespace)
				.retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).build();
		client.start();
		return client;
	}

	private static void setListenter(CuratorFramework client)
			throws Exception {
		ExecutorService pool = Executors.newCachedThreadPool();
		TreeCache cache = new TreeCache(client, "/test");
		cache.getListenable().addListener(new TreeCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, TreeCacheEvent event)
					throws Exception {
				ChildData data = event.getData();
				if (data != null) {
					switch (event.getType()) {
					case NODE_ADDED:
						System.err.println("NODE_ADDED : " + data.getPath()
								+ "  数据:" + new String(data.getData()));
						break;
					case NODE_REMOVED:
						System.err.println("NODE_REMOVED : " + data.getPath()
								+ "  数据:" + new String(data.getData()));
						break;
					case NODE_UPDATED:
						System.err.println("NODE_UPDATED : " + data.getPath()
								+ "  数据:" + new String(data.getData()));
						break;

					default:
						break;
					}
				} else {
					//CONNECTION_SUSPENDED CONNECTION_RECONNECTED CONNECTION_LOST
					System.err.println("data is null : " + event.getType());
				}
			}
		});
		// 开始监听
		cache.start();
	}
	private static void setListenterThreeTwo(CuratorFramework client)
			throws Exception {
		ExecutorService pool = Executors.newCachedThreadPool();
		final NodeCache nodeCache = new NodeCache(client, "/test", false);
		nodeCache.getListenable().addListener(new NodeCacheListener() {
			@Override
			public void nodeChanged() throws Exception {
				System.err.println("the test node is change and result is :");
				System.err.println("path : "+ nodeCache.getCurrentData().getPath());
				System.err.println("data : "+ new String(nodeCache.getCurrentData().getData()));
				System.err.println("stat : "+ nodeCache.getCurrentData().getStat());
			}
		});
		nodeCache.start();
	}
}
