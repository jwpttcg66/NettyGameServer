package com.snowcattle.game.service.net.ssl;

import com.snowcattle.game.common.config.GameServerConfig;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.IService;
import com.snowcattle.game.service.config.GameServerConfigService;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 2017/6/28.
 * websocket的ssl服务
 */
@Service
public class SSLService implements IService{

    private SslContext sslCtx;

    @Override
    public String getId() {
        return ServiceName.SSLService;
    }

    @Override
    public void startup() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();
        boolean webSocketSSLFlag = gameServerConfig.isWebSockectSSLFlag();
        if(webSocketSSLFlag){
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        }
    }

    @Override
    public void shutdown() throws Exception {

    }

    public SslContext getSslCtx() {
        return sslCtx;
    }

    public void setSslCtx(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }
}
