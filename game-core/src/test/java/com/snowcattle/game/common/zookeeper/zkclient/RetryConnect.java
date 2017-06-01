package com.snowcattle.game.common.zookeeper.zkclient;

import java.util.concurrent.Callable;

import org.I0Itec.zkclient.ZkClient;

public class RetryConnect implements Callable<String>{
	private String path;
	private ZkClient zk;

	public RetryConnect(String path, ZkClient zk) {
		super();
		this.path = path;
		//this.zk = zk;
	}

	@Override
	public String call() throws Exception {
		zk = new ZkClient("127.0.0.1:2181");
		return "success";
	}

}
