package com.snowcattle.game.service.event;

import com.snowcattle.game.common.annotation.GlobalEventListenerAnnotation;
import com.snowcattle.game.common.config.GameServerConfig;
import com.snowcattle.game.common.config.GameServerConfigService;
import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.common.loader.DefaultClassLoader;
import com.snowcattle.game.common.loader.DynamicGameClassLoader;
import com.snowcattle.game.common.loader.scanner.ClassScanner;
import com.snowcattle.game.common.util.BeanUtil;
import com.snowcattle.game.common.util.StringUtils;
import com.snowcattle.game.executor.event.AbstractEventListener;
import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.event.SingleEvent;
import com.snowcattle.game.executor.event.service.AsyncEventService;
import com.snowcattle.game.manager.LocalMananger;
import com.snowcattle.game.service.IService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 2017/5/22.
 * 游戏内的事件全局服务
 */
@Service
public class GameAsyncEventService implements IService{

    /**
     * Logger for this class
     */
    public static final Logger logger = Loggers.serverLogger;

    public ClassScanner classScanner = new ClassScanner();

    private AsyncEventService asyncEventService;

    private EventBus eventBus;

    @Override
    public String getId() {
        return ServiceName.GameAsyncEventService;
    }

    @Override
    public void startup() throws Exception {
        eventBus = new EventBus();
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();
        String nameSpace = gameServerConfig.getAsyncEventListenerNameSpace();
        scanListener(nameSpace, GlobalConstants.FileExtendConstants.Ext);
        int eventQueueSize = gameServerConfig.getAsyncEventQueueSize();
        int workerSize = gameServerConfig.getAsyncEventWorkSize();
        String queueWorkTheadName = GlobalConstants.Thread.EVENT_WORKER;
        int handleSize = gameServerConfig.getAsyncEventHandlerThreadSize();
        String workerHanlderName = GlobalConstants.Thread.EVENT_HANDLER;
        int handleQueueSize =  gameServerConfig.getAsyncEventHandleQueueSize();
        asyncEventService = new AsyncEventService(eventBus, eventQueueSize, workerSize, queueWorkTheadName, handleSize, workerHanlderName, handleQueueSize);
        asyncEventService.startUp();
    }

    private void scanListener(String namespace, String ext) throws Exception {
        String[] fileNames = classScanner.scannerPackage(namespace, ext);
        // 加载class,获取协议命令
        DefaultClassLoader defaultClassLoader = LocalMananger.getInstance().getLocalSpringServiceManager().getDefaultClassLoader();
        defaultClassLoader.resetDynamicGameClassLoader();
        DynamicGameClassLoader dynamicGameClassLoader = defaultClassLoader.getDynamicGameClassLoader();

        if(fileNames != null) {
            for (String fileName : fileNames) {
                String realClass = namespace
                        + "."
                        + fileName.subSequence(0, fileName.length()
                        - (ext.length()));
//                Class<?> messageClass = null;
//                FileClassLoader fileClassLoader = defaultClassLoader.getDefaultClassLoader();
//                if (!defaultClassLoader.isJarLoad()) {
//                    defaultClassLoader.initClassLoaderPath(realClass, ext);
//                    byte[] bytes = fileClassLoader.getClassData(realClass);
//                    messageClass = dynamicGameClassLoader.findClass(realClass, bytes);
//                } else {
//                    //读取 game_server_handler.jar包所在位置
//                    URL url = ClassLoader.getSystemClassLoader().getResource("./");
//                    File file = new File(url.getPath());
//                    File parentFile = new File(file.getParent());
//                    String jarPath = parentFile.getPath() + File.separator + "lib/game_server_handler.jar";
//                    logger.info("message load jar path:" + jarPath);
//                    JarFile jarFile = new JarFile(new File(jarPath));
//                    fileClassLoader.initJarPath(jarFile);
//                    byte[] bytes = fileClassLoader.getClassData(realClass);
//                    messageClass = dynamicGameClassLoader.findClass(realClass, bytes);
//                }
                Class<?> messageClass = Class.forName(realClass);
                logger.info("GameAsyncEventService load: " + messageClass);

                AbstractEventListener eventListener = getListener(messageClass);
                GlobalEventListenerAnnotation annotation = (GlobalEventListenerAnnotation) messageClass
                        .getAnnotation(GlobalEventListenerAnnotation.class);
                if (annotation != null) {
                    eventBus.addEventListener(eventListener);
                }
            }
        }
    }


    @Override
    public void shutdown() throws Exception {
        asyncEventService.shutDown();
        eventBus.clear();
    }

    /**
     * 获取消息对象
     *
     * @return
     * @throws Exception
     */
    public final AbstractEventListener getListener(Class<?> classes) {

        try {
            if (classes == null) {
                return null;
            }
            //如果是spring对象，直接获取，使用spring
            if(classes.getAnnotation(Service.class) != null){
                return (AbstractEventListener) BeanUtil.getBean(StringUtils.toLowerCaseFirstOne(classes.getSimpleName()));
            }

            AbstractEventListener object = (AbstractEventListener) classes
                    .newInstance();
            return object;
        } catch (Exception e) {
            logger.error("getListener - classes=" + classes.getName()
                    + ". ", e);
        }
        return null;
    }

    /*放入消息*/
    public void putEvent(SingleEvent event){
        asyncEventService.put(event);
    }
}
