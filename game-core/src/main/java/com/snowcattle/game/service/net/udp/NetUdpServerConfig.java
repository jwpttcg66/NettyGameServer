package com.snowcattle.game.service.net.udp;

import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.util.FileUtil;
import com.snowcattle.game.common.util.JdomUtils;
import com.snowcattle.game.service.net.proxy.SdProxyConfig;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * Created by jiangwenping on 2017/7/3.
 */
@Service
public class NetUdpServerConfig{

    private static final Logger LOGGER = Loggers.serverLogger;

    private SdUdpServerConfig sdUdpServerConfig;

    public void init() throws Exception {
        URL url =  FileUtil.getConfigURL(GlobalConstants.ConfigFile.UDP_SERVER_CONFIG);
        if(url != null) {
            Element rootElement = JdomUtils.getRootElemet(url.getFile());
            Element element = rootElement.getChild("server");
            sdUdpServerConfig = new SdUdpServerConfig();
            sdUdpServerConfig.load(element);
        }
    }

    public SdUdpServerConfig getSdUdpServerConfig() {
        return sdUdpServerConfig;
    }

    public void setSdUdpServerConfig(SdUdpServerConfig sdUdpServerConfig) {
        this.sdUdpServerConfig = sdUdpServerConfig;
    }
}

