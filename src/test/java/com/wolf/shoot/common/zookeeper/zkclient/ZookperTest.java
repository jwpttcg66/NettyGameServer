package com.wolf.shoot.common.zookeeper.zkclient;

import java.util.List;
import java.util.concurrent.Callable;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class ZookperTest {
	public static void main(String[] args) {
		ZkClient zkClient = new ZkClient("192.168.0.158:2181");
		String path = "/master123";
		//创建节点
		zkClient.createEphemeral(path);
		//创建子节点
		//zkClient.create(path+"/slave", "child node", CreateMode.EPHEMERAL);
		//获得子节点
		//List<String> children = zkClient.getChildren(path);
		//获得子节点个数
		//int countChildren = zkClient.countChildren(path);
		//判断节点是否存在
		//boolean exists = zkClient.exists(path);
		//写入数据
		//zkClient.writeData(path+"/slave", "hello child");
		//读取数据
		//System.err.println(zkClient.readData(path+"/slave"));
		//删除节点
		//zkClient.delete(path+"/slave");
		new Thread(new Listener(path, zkClient)).start();
		zkClient.close();
		zkClient.retryUntilConnected(new RetryConnect(path, zkClient));
		//子节点变化
		System.err.println("xx");
		//ZkClient zkClient2 = new ZkClient("192.168.0.158:2181");
	}
}
