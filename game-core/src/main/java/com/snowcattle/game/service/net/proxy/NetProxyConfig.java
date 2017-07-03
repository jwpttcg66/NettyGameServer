package com.snowcattle.game.service.net.proxy;

import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.util.FileUtil;
import com.snowcattle.game.common.util.JdomUtils;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * Created by jiangwenping on 2017/6/27.
 * 记载代理
 */
@Service
public class NetProxyConfig{

    private static final Logger LOGGER = Loggers.serverLogger;

    private SdProxyConfig sdProxyConfig;

    public void init() throws Exception {
        URL url =  FileUtil.getConfigURL(GlobalConstants.ConfigFile.PROXY_SERVER_CONFIG);
        if(url != null) {
            Element rootElement = JdomUtils.getRootElemet(url.getFile());
            Element element = rootElement.getChild("server");
            sdProxyConfig = new SdProxyConfig();
            sdProxyConfig.load(element);
        }
    }

    public SdProxyConfig getSdProxyConfig() {
        return sdProxyConfig;
    }

    public void setSdProxyConfig(SdProxyConfig sdProxyConfig) {
        this.sdProxyConfig = sdProxyConfig;
    }
}
