package com.snowcattle.game.service.net.proxy;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

/**
 * Created by jiangwenping on 2017/6/27.
 * 代理配置
 */
public class SdProxyConfig {

    private String name;
    private String id;
    private String ip;
    private int port;
    private String transferIp;
    private int transferPort;
    private int bossThreadSize;
    private int workerThreadSize;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getTransferIp() {
        return transferIp;
    }

    public void setTransferIp(String transferIp) {
        this.transferIp = transferIp;
    }

    public int getTransferPort() {
        return transferPort;
    }

    public void setTransferPort(int transferPort) {
        this.transferPort = transferPort;
    }

    public int getBossThreadSize() {
        return bossThreadSize;
    }

    public void setBossThreadSize(int bossThreadSize) {
        this.bossThreadSize = bossThreadSize;
    }

    public int getWorkerThreadSize() {
        return workerThreadSize;
    }

    public void setWorkerThreadSize(int workerThreadSize) {
        this.workerThreadSize = workerThreadSize;
    }

    public void load(Element element) throws DataConversionException {
        name = element.getChildTextTrim("name");
        id = element.getChildTextTrim("id");
        ip = element.getChildTextTrim("ip");
        port = Integer.valueOf(element.getChildTextTrim("port"));
        transferIp = element.getChildTextTrim("transfer-ip");
        transferPort = Integer.valueOf(element.getChildTextTrim("transfer-port"));
//        bossThreadSize = Integer.valueOf(element.getChildTextTrim("gate-thread-bosss"));
//        workerThreadSize = Integer.valueOf(element.getChildTextTrim("gate-thread-worker"));
    }


}
