package com.wolf.shoot.service.net.message.registry;


import com.wolf.shoot.common.annotation.MessageCommandAnnotation;
import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.common.loader.scanner.ClassScanner;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.IService;
import com.wolf.shoot.service.Reloadable;
import com.wolf.shoot.service.net.message.AbstractNetProtoBufMessage;
import com.wolf.shoot.service.net.message.command.MessageCommand;
import com.wolf.shoot.service.net.message.command.MessageCommandEnum;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiangwenping on 17/2/8.
 */
@Service
public class MessageRegistry implements Reloadable, IService{

    public static Logger logger = Loggers.serverLogger;

    public ClassScanner messageScanner = new ClassScanner();

    private ConcurrentHashMap<Short, MessageCommand> messageCommandMap = new ConcurrentHashMap<Short, MessageCommand>();

    private Map<Integer, Class<? extends AbstractNetProtoBufMessage>> messages = new HashMap<Integer, Class<? extends AbstractNetProtoBufMessage>>();

    public void putMessageCommands(int key, Class putClass) {
        messages.put(key, putClass);
    }

    /**
     * 获取消息对象
     *
     * @param commandId
     * @return
     * @throws Exception
     */
    public final AbstractNetProtoBufMessage getMessage(int commandId) {
        if (commandId < 0)
            return null;

        try {
            Class<? extends AbstractNetProtoBufMessage> cls = messages.get(commandId);
            if (cls == null) {
                return null;
            }
            AbstractNetProtoBufMessage message = cls.newInstance();
            return message;
        } catch (Exception e) {
            logger.error("getMessage(int) - commandId=" + commandId + ". ", e);
        }
        return null;
    }

    public void loadPackage(String namespace, String ext) throws Exception {
        String[] fileNames = messageScanner.scannerPackage(namespace, ext);
        // 加载class,获取协议命令
        if(fileNames != null) {
            for (String fileName : fileNames) {
                String realClass = namespace
                        + "."
                        + fileName.subSequence(0, fileName.length()
                        - (ext.length()));
                Class<?> messageClass = Class.forName(realClass);

                logger.info("message load:" + messageClass);

                MessageCommandAnnotation annotation = (MessageCommandAnnotation) messageClass
                        .getAnnotation(MessageCommandAnnotation.class);
                if (annotation != null) {
                    putMessageCommands(annotation.command(), messageClass);
                }
            }
        }
    }

    public final void loadMessageCommand(){
        MessageCommandEnum[] set = MessageCommandEnum.values();
        for(int i = 0; i< set.length; i++){
            MessageCommandEnum messageCommandEnum = set[i];
            MessageCommand messageCommand = new MessageCommand(messageCommandEnum.command_id, messageCommandEnum.bo_id, messageCommandEnum.is_need_filter, messageCommandEnum.is_common);
            messageCommandMap.put((short) messageCommandEnum.command_id, messageCommand);
            logger.info("messageCommands load:" + messageCommandEnum);
        }
    }

    public MessageCommand getMessageCommand(short commandId){
        return messageCommandMap.get(commandId);
    }

    public void reload() throws Exception {
        loadMessageCommand();
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        loadPackage(gameServerConfigService.getGameServerConfig().getNetMsgNameSpace(),
                GlobalConstants.MessageCommandConstants.Ext);
    }

    @Override
    public String getId() {
        return ServiceName.MessageRegistry;
    }

    @Override
    public void startup() throws Exception {
        reload();

    }

    @Override
    public void shutdown() throws Exception {

    }
}

