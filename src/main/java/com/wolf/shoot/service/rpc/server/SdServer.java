package com.wolf.shoot.service.rpc.server;

/**
 * Created by jwp on 2017/3/9.
 */

import org.jdom2.DataConversionException;
import org.jdom2.Element;

/**
 * @author jwp
 *	服务器
 */
public class SdServer {


    /**
     * 服务器配置id
     */
    private int serverId;
    private String ip;
    private int port;
    /**
     * 域名
     */
    private String domain;
    /**
     * 域名端口
     */
    private int domainPort;

    /**
     * 权重
     */
    private int weight;

    /**
     * 最大数量
     */
    private int maxNumber;


    /**
     * 通讯短端口
     */
    private int communicationPort;
    /**
     * 通讯链接数量
     */
    private int communicationNumber;

//    public SdServer(int serverId, String domain, int domainPort, String ip, int port, int weight, int maxNumber, int communicationPort
//            , int communicationNumber) {
//        super();
//        this.serverId = serverId;
//        this.domain = domain;
//        this.domainPort = domainPort;
//        this.ip = ip;
//        this.port = port;
//        this.weight = weight;
//        this.maxNumber = maxNumber;
//        this.communicationPort = communicationPort;
//        this.communicationNumber = communicationNumber;
//    }

    public int getServerId() {
        return serverId;
    }
    public void setServerId(int serverId) {
        this.serverId = serverId;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getDomainPort() {
        return domainPort;
    }

    public void setDomainPort(int domainPort) {
        this.domainPort = domainPort;
    }

    public int getCommunicationPort() {
        return communicationPort;
    }

    public void setCommunicationPort(int communicationPort) {
        this.communicationPort = communicationPort;
    }

    public int getCommunicationNumber() {
        return communicationNumber;
    }

    public void setCommunicationNumber(int communicationNumber) {
        this.communicationNumber = communicationNumber;
    }

    public void load(Element element) throws DataConversionException {
        serverId = element.getAttribute("serverId").getIntValue();
        domain = element.getAttributeValue("domain");
        domainPort = element.getAttribute("domainPort").getIntValue();
        ip = element.getAttributeValue("ip");
        port = element.getAttribute("port").getIntValue();
        weight = element.getAttribute("weight").getIntValue();
        maxNumber = element.getAttribute("maxNumber").getIntValue();
        communicationPort = element.getAttribute("communicationPort").getIntValue();
        communicationNumber = element.getAttribute("communicationNumber").getIntValue();
    }
}
