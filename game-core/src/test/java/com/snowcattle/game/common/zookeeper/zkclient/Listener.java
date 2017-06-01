package com.snowcattle.game.common.zookeeper.zkclient;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class Listener implements Runnable{

	private String path;
	private ZkClient zkClient = new ZkClient("127.0.0.1:2181");;
	
	public Listener(String path, ZkClient z) {
		super();
		this.path = path;
		//zkClient = z;
	}

	@Override
	public void run() {
		while (true) {
			try {
				new Thread().sleep(3000l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			zkClient.subscribeChildChanges(path, new IZkChildListener(){
				@Override
				public void handleChildChange(String path,List<String> list){
					System.err.println("child change......1111111111");
				}
			});
			//节点数据变化
			zkClient.subscribeDataChanges(path, new IZkDataListener() {
				
				@Override
				public void handleDataDeleted(String dataPath) throws Exception {
					System.err.println("data deleted......2222222222");
				}
				
				@Override
				public void handleDataChange(String dataPath, Object data) throws Exception {
					System.err.println("data changed......3333333333333");
				}
			});
			//节点状态变化
			zkClient.subscribeStateChanges(new IZkStateListener() {
				@Override
				public void handleStateChanged(KeeperState state) throws Exception {
					System.err.println("state changed......44444444");
				}
				
				@Override
				public void handleSessionEstablishmentError(Throwable error)throws Exception {
					System.err.println("state chanege:"+error);
				}
				
				@Override
				public void handleNewSession() throws Exception {
					System.err.println("recreate data path......5555555555");
				}
			});
		}
	}
}
