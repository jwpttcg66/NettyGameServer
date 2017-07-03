package com.snowcattle.game.net.client.proxy;

import com.snowcattle.game.TestStartUp;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.bootstrap.manager.spring.LocalSpringServiceManager;
import com.snowcattle.game.net.client.tcp.GameClient;
import com.snowcattle.game.service.message.registry.MessageRegistry;

/**
 * Created by jiangwenping on 2017/6/28.
 */
public class ProxyClient extends GameClient {
    public static void main(String[] args) throws Exception {
        TestStartUp.startUp();
        LocalSpringServiceManager localSpringServiceManager = LocalMananger.getInstance().getLocalSpringServiceManager();
        localSpringServiceManager.setMessageRegistry(LocalMananger.getInstance().get(MessageRegistry.class));

        new GameClient().connect("127.0.0.1", 9090);
    }
}
