package com.snowcattle.game.service.net.udp;

import com.snowcattle.game.service.net.SdNetConfig;
import org.jdom2.DataConversionException;
import org.jdom2.Element;

/**
 * Created by jiangwenping on 2017/7/3.
 * udp服务器配置
 */
public class SdUdpServerConfig extends SdNetConfig{

    private int updQueueMessageProcessWorkerSize;
    private boolean udpMessageOrderQueueFlag;

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
        super.load(element);
        updQueueMessageProcessWorkerSize = Integer.valueOf(element.getChildTextTrim("updQueueMessageProcessWorkerSize"));
        udpMessageOrderQueueFlag = Boolean.valueOf(element.getChildTextTrim("udpMessageOrderQueueFlag"));
    }
}
