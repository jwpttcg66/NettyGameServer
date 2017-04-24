package com.wolf.shoot.common.zookeeper.Carutor;
import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
public class Carutor2 {
	public static void main(String[] args) throws Exception {
		CuratorFramework client = clientTwo();
		client.setData().forPath("/test","test".getBytes());
		client.setData().forPath("/test","test-update".getBytes());
	}
	private static CuratorFramework clientTwo() {

		// 默认创建的根节点是没有做权限控制的--需要自己手动加权限???----
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
		String scheme = "xxxxx";
		byte[] auth = "xx:xx".getBytes();
		int connectionTimeoutMs = 5000;
		String connectString = "192.168.0.158:2181";
		String namespace = "testnamespace";
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.aclProvider(aclProvider).authorization(scheme, auth)
				.connectionTimeoutMs(connectionTimeoutMs)
				.connectString(connectString).namespace(namespace)
				.retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).build();
		client.start();
		return client;
	}
}
