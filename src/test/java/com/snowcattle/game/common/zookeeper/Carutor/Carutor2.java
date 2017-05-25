package com.snowcattle.game.common.zookeeper.Carutor;
import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
public class Carutor2 {
	public static void main(String[] args) throws Exception {
		CuratorFramework client = clientTwo();
		client.create().withMode(CreateMode.EPHEMERAL).forPath("/test", "111".getBytes());
		byte[] data = client.getData().forPath("/test");
		System.err.println("data>>>>>>>>>"+new String(data));
		client.setData().forPath("/test","222".getBytes());
		client.setData().forPath("/test","222-update".getBytes());
		client.delete().forPath("/test");
	}
	private static CuratorFramework clientTwo() {

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
		String connectString = "127.0.0.1:2181";
		String namespace = "";
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.aclProvider(aclProvider).authorization(scheme, auth)
				.connectionTimeoutMs(connectionTimeoutMs)
				.connectString(connectString).namespace(namespace)
				.retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).build();
		client.start();
		return client;
	}
}
