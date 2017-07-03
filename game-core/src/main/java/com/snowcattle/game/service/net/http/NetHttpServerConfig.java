package com.snowcattle.game.service.net.http;

import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.util.FileUtil;
import com.snowcattle.game.common.util.JdomUtils;
import com.snowcattle.game.service.net.udp.SdUdpServerConfig;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * Created by jiangwenping on 2017/7/3.
 * http服务器配置
 */
@Service
public class NetHttpServerConfig{

    private static final Logger LOGGER = Loggers.serverLogger;

    private SdHttpServerConfig sdHttpServerConfig;

    public void init() throws Exception {
        URL url =  FileUtil.getConfigURL(GlobalConstants.ConfigFile.HTTP_SERVER_CONFIG);
        if(url != null) {
            Element rootElement = JdomUtils.getRootElemet(url.getFile());
            Element element = rootElement.getChild("server");
            sdHttpServerConfig = new SdHttpServerConfig();
            sdHttpServerConfig.load(element);
        }
    }

    public SdHttpServerConfig getSdHttpServerConfig() {
        return sdHttpServerConfig;
    }

    public void setSdHttpServerConfig(SdHttpServerConfig sdHttpServerConfig) {
        this.sdHttpServerConfig = sdHttpServerConfig;
    }
}
