package com.snowcattle.game.service.message.facade;

import com.snowcattle.game.common.annotation.MessageCommandAnnotation;
import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.common.exception.GameHandlerException;
import com.snowcattle.game.common.scanner.ClassScanner;
import com.snowcattle.game.service.classes.loader.DefaultClassLoader;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.message.handler.AbstractMessageHandler;
import com.snowcattle.game.message.handler.IMessageHandler;
import com.snowcattle.game.service.Reloadable;
import com.snowcattle.game.service.config.GameServerConfigService;
import com.snowcattle.game.service.message.AbstractNetMessage;
import com.snowcattle.game.service.classes.loader.DynamicGameClassLoader;
import com.snowcattle.game.service.IService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangwenping on 17/2/8.
 */
@Service
public class  GameFacade implements IFacade ,Reloadable, IService{

    /**
     * Logger for this class
     */
    public static final Logger logger = Loggers.serverLogger;

    public ClassScanner classScanner = new ClassScanner();

    public String[] fileNames;

    protected Map<Integer, IMessageHandler> handlers = new HashMap<Integer, IMessageHandler>();

    public void addHandler(int httpCode, IMessageHandler handler) {
        handlers.put(httpCode, handler);
    }

    @Override
    public AbstractNetMessage dispatch(AbstractNetMessage message)
            throws GameHandlerException {
        try {
            int cmd = message.getCmd();
            IMessageHandler handler = handlers.get(cmd);
            Method method = handler.getMessageHandler(cmd);
            method.setAccessible(true);
            Object object =  method.invoke(handler,
                    message);
            AbstractNetMessage result = null;
            if(object != null){
                result = (AbstractNetMessage) object;
            }
            return result;
        } catch (Exception e) {
            throw new GameHandlerException(e, message.getSerial());
        }
    }

    public void loadPackage(String namespace, String ext)
            throws Exception {
        if(fileNames == null){
            fileNames = classScanner.scannerPackage(namespace, ext);
        }
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
                logger.info("handler load: " + messageClass);

                IMessageHandler iMessageHandler = getMessageHandler(messageClass);
                AbstractMessageHandler handler = (AbstractMessageHandler) iMessageHandler;
                handler.init();
                Method[] methods = messageClass.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(MessageCommandAnnotation.class)) {
                        MessageCommandAnnotation messageCommandAnnotation = (MessageCommandAnnotation) method
                                .getAnnotation(MessageCommandAnnotation.class);
                        if (messageCommandAnnotation != null) {
                            addHandler(messageCommandAnnotation.command(), iMessageHandler);
                        }
                    }
                }

            }
        }
    }

    /**
     * 获取消息对象
     *
     * @return
     * @throws Exception
     */
    public final IMessageHandler getMessageHandler(Class<?> classes) {

        try {
            if (classes == null) {
                return null;
            }

            IMessageHandler messageHandler = (IMessageHandler) classes
                    .newInstance();
            return messageHandler;
        } catch (Exception e) {
            logger.error("getMessageHandler - classes=" + classes.getName()
                    + ". ", e);
        }
        return null;
    }

    @Override
    public void reload() throws Exception {
        try {
            GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
            loadPackage(gameServerConfigService.getGameServerConfig().getNetMessageHandlerNameSpace(), GlobalConstants.FileExtendConstants.Ext);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
    }
    @Override
    public String getId() {
        return ServiceName.GameFacade;
    }
    @Override
    public void startup() throws Exception {
        reload();

    }
    @Override
    public void shutdown() throws Exception {

    }
}
