package com.wolf.shoot.service.rpc.server.zookeeper;

import com.alibaba.fastjson.JSON;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.util.JsonSerializer;

/**
 * Created by sunmosh on 2017/4/1.
 * zookeeper节点信息
 */
public class ZooKeeperNodeInfo implements JsonSerializer {
    /**
     * 根节点信息
     */
    private ZooKeeperNodeBoEnum zooKeeperNodeBoEnum;
    /**
     * 服务器id
     */
    private String serverId;
    private String host;
    private String port;

    public ZooKeeperNodeInfo(){

    }

    public ZooKeeperNodeInfo(ZooKeeperNodeBoEnum zooKeeperNodeBoEnum, String serverId, String host, String port) {
        this.zooKeeperNodeBoEnum = zooKeeperNodeBoEnum;
        this.serverId = serverId;
        this.host = host;
        this.port = port;
    }

    public ZooKeeperNodeBoEnum getZooKeeperNodeBoEnum() {
        return zooKeeperNodeBoEnum;
    }

    public void setZooKeeperNodeBoEnum(ZooKeeperNodeBoEnum zooKeeperNodeBoEnum) {
        this.zooKeeperNodeBoEnum = zooKeeperNodeBoEnum;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    };

    //获取节点数据
    public String getNodePath(){
        return zooKeeperNodeBoEnum.getRootPath()  + GlobalConstants.ZooKeeperConstants.ZK_DATA_PATH + serverId;
    }

    @Override
    public String serialize() {
        String coopreationMatchJsonString = JSON.toJSONString(this);
        return coopreationMatchJsonString;
    }

    @Override
    public void deserialize(String pack) {
        ZooKeeperNodeInfo temp = JSON.parseObject(pack, this.getClass());
        this.zooKeeperNodeBoEnum = temp.getZooKeeperNodeBoEnum();
        this.serverId = temp.getServerId();
        this.host = temp.getHost();
        this.port = temp.getPort();
    }
}
