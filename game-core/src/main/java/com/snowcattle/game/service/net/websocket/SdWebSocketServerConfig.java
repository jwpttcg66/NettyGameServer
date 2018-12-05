package com.snowcattle.game.service.net.websocket;

import com.snowcattle.game.service.net.SdNetConfig;
import org.jdom2.DataConversionException;
import org.jdom2.Element;

/**
 * Created by jiangwenping on 2017/10/18.
 */
public class SdWebSocketServerConfig extends SdNetConfig {

    private  int handleThreadSize;
    private  boolean ssl;
    public void load(Element element) throws DataConversionException {
        super.load(element);
        handleThreadSize = Integer.valueOf(element.getChildTextTrim("handleThreadSize"));
        ssl = Boolean.valueOf(element.getChildTextTrim("ssl"));
    }

    public int getHandleThreadSize() {
        return handleThreadSize;
    }

    public void setHandleThreadSize(int handleThreadSize) {
        this.handleThreadSize = handleThreadSize;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }
}
