package com.snowcattle.game;

import com.snowcattle.game.common.util.BeanUtil;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.bootstrap.manager.spring.LocalSpringBeanManager;
import com.snowcattle.game.bootstrap.manager.spring.LocalSpringServiceManager;
import com.snowcattle.game.bootstrap.manager.spring.LocalSpringServicerAfterManager;
import com.snowcattle.game.service.config.GameServerConfigService;
import com.snowcattle.game.service.message.command.MessageCommandFactory;
import com.snowcattle.game.service.message.registry.MessageRegistry;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiangwenping on 17/4/19.
 */
public class TestStartUp {
    public static void startUpWithSpring(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/*.xml"});
        LocalSpringServiceManager localSpringServiceManager = (LocalSpringServiceManager) BeanUtil.getBean("localSpringServiceManager");
        LocalSpringBeanManager localSpringBeanManager = (LocalSpringBeanManager) BeanUtil.getBean("localSpringBeanManager");
        LocalSpringServicerAfterManager localSpringServicerAfterManager  = (LocalSpringServicerAfterManager) BeanUtil.getBean("localSpringServicerAfterManager");
        LocalMananger.getInstance().setLocalSpringBeanManager(localSpringBeanManager);
        LocalMananger.getInstance().setLocalSpringServiceManager(localSpringServiceManager);
        LocalMananger.getInstance().setLocalSpringServicerAfterManager(localSpringServicerAfterManager);
        try {
            localSpringServiceManager.start();
            localSpringServicerAfterManager.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startUp() throws  Exception{
        LocalSpringServiceManager localSpringServiceManager = new LocalSpringServiceManager();
        LocalSpringBeanManager localSpringBeanManager = new LocalSpringBeanManager();
        MessageCommandFactory messageCommandFactory = new MessageCommandFactory();
        localSpringBeanManager.setMessageCommandFactory(messageCommandFactory);
        GameServerConfigService gameServerConfigService = new GameServerConfigService();
        gameServerConfigService.startup();
        localSpringServiceManager.setGameServerConfigService(gameServerConfigService);
        LocalMananger.getInstance().setLocalSpringServiceManager(localSpringServiceManager);
        LocalMananger.getInstance().setLocalSpringBeanManager(localSpringBeanManager);
        LocalMananger.getInstance().create(MessageRegistry.class, MessageRegistry.class);
        localSpringServiceManager.setMessageRegistry(LocalMananger.getInstance().get(MessageRegistry.class));
    }

}
