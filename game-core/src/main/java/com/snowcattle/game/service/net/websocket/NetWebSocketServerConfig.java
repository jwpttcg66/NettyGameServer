package com.snowcattle.game.service.net.websocket;

import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.util.FileUtil;
import com.snowcattle.game.common.util.JdomUtils;
import com.snowcattle.game.service.net.SdNetConfig;
import com.snowcattle.game.service.net.http.SdHttpServerConfig;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * Created by jiangwenping on 2017/10/18.
 */
@Service
public class NetWebSocketServerConfig extends SdNetConfig{
    private static final Logger LOGGER = Loggers.serverLogger;

    private SdWebSocketServerConfig sdWebSocketServerConfig;

    public void init() throws Exception {
        URL url =  FileUtil.getConfigURL(GlobalConstants.ConfigFile.WEBSOCKET_SERVER_CONFIG);
        if(url != null) {
            Element rootElement = JdomUtils.getRootElemet(url.getFile());
            Element element = rootElement.getChild("server");
            sdWebSocketServerConfig = new SdWebSocketServerConfig();
            sdWebSocketServerConfig.load(element);
        }
    }

    public SdWebSocketServerConfig getSdWebSocketServerConfig() {
        return sdWebSocketServerConfig;
    }

    public void setSdWebSocketServerConfig(SdWebSocketServerConfig sdWebSocketServerConfig) {
        this.sdWebSocketServerConfig = sdWebSocketServerConfig;
    }
}
