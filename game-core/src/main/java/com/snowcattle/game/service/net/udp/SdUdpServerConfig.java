package com.snowcattle.game.service.net.udp;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

/**
 * Created by jiangwenping on 2017/7/3.
 * udp服务器配置
 */
public class SdUdpServerConfig {

    private String name;
    private String id;
    private String ip;
    private int port;
    private int updQueueMessageProcessWorkerSize;
    private boolean udpMessageOrderQueueFlag;

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

    public int getUpdQueueMessageProcessWorkerSize() {
        return updQueueMessageProcessWorkerSize;
    }

    public void setUpdQueueMessageProcessWorkerSize(int updQueueMessageProcessWorkerSize) {
        this.updQueueMessageProcessWorkerSize = updQueueMessageProcessWorkerSize;
    }

    public boolean isUdpMessageOrderQueueFlag() {
        return udpMessageOrderQueueFlag;
    }

    public void setUdpMessageOrderQueueFlag(boolean udpMessageOrderQueueFlag) {
        this.udpMessageOrderQueueFlag = udpMessageOrderQueueFlag;
    }

    public void load(Element element) throws DataConversionException {
        name = element.getChildTextTrim("name");
        id = element.getChildTextTrim("id");
        ip = element.getChildTextTrim("ip");
        port = Integer.valueOf(element.getChildTextTrim("port"));
        updQueueMessageProcessWorkerSize = Integer.valueOf(element.getChildTextTrim("updQueueMessageProcessWorkerSize"));
        udpMessageOrderQueueFlag = Boolean.valueOf(element.getChildTextTrim("udpMessageOrderQueueFlag"));
    }
}
