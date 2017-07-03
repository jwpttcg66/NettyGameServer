package com.snowcattle.game.service.net.proxy;

import com.snowcattle.game.service.net.SdNetConfig;
import org.jdom2.DataConversionException;
import org.jdom2.Element;

/**
 * Created by jiangwenping on 2017/6/27.
 * 代理配置
 */
public class SdProxyConfig extends SdNetConfig{

    private String transferIp;
    private int transferPort;
    private int bossThreadSize;
    private int workerThreadSize;

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
        super.load(element);
        transferIp = element.getChildTextTrim("transfer-ip");
        transferPort = Integer.valueOf(element.getChildTextTrim("transfer-port"));
//        bossThreadSize = Integer.valueOf(element.getChildTextTrim("gate-thread-bosss"));
//        workerThreadSize = Integer.valueOf(element.getChildTextTrim("gate-thread-worker"));
    }


}
