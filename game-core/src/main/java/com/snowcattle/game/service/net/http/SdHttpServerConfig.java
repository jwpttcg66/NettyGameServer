package com.snowcattle.game.service.net.http;

import com.snowcattle.game.service.net.SdNetConfig;
import org.jdom2.DataConversionException;
import org.jdom2.Element;

/**
 * Created by jiangwenping on 2017/7/3.
 */
public class SdHttpServerConfig extends SdNetConfig{

    private  int handleThreadSize;

    public void load(Element element) throws DataConversionException {
        super.load(element);
        handleThreadSize = Integer.valueOf(element.getChildTextTrim("handleThreadSize"));
    }

    public int getHandleThreadSize() {
        return handleThreadSize;
    }

    public void setHandleThreadSize(int handleThreadSize) {
        this.handleThreadSize = handleThreadSize;
    }
}
