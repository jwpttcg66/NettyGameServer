package com.snowcattle.game.service.net.http;

import com.snowcattle.game.service.net.SdNetConfig;
import org.jdom2.DataConversionException;
import org.jdom2.Element;

/**
 * Created by jiangwenping on 2017/7/3.
 */
public class SdHttpServerConfig extends SdNetConfig{
    public void load(Element element) throws DataConversionException {
        super.load(element);
    }
}
