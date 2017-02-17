package com.wolf.shoot.net.message.registry;


import com.wolf.shoot.common.annotation.MessageCommandAnnotation;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.common.util.ClassScanner;
import com.wolf.shoot.net.message.MessageCommands;
import com.wolf.shoot.net.message.AbstractAbstractNetProtoBufMessage;
import com.wolf.shoot.service.IService;
import com.wolf.shoot.service.Reloadable;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiangwenping on 17/2/8.
 */
public class MessageRegistry implements Reloadable, IService{

    public static Logger logger = Loggers.serverLogger;

    public ClassScanner messageScanner = new ClassScanner();

    private ConcurrentHashMap<Short, MessageCommands> messageCommandMap = new ConcurrentHashMap<Short, MessageCommands>();

    private Map<Integer, Class<? extends AbstractAbstractNetProtoBufMessage>> messages = new HashMap<Integer, Class<? extends AbstractAbstractNetProtoBufMessage>>();

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
    public final AbstractAbstractNetProtoBufMessage getMessage(int commandId) {
        if (commandId < 0)
            return null;

        try {
            Class<? extends AbstractAbstractNetProtoBufMessage> cls = messages.get(commandId);
            if (cls == null) {
                return null;
            }
            AbstractAbstractNetProtoBufMessage message = cls.newInstance();
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
                if (annotation != null && annotation.command() != null) {
                    putMessageCommands(annotation.command().command_id, messageClass);
                }
            }
        }
    }

    public final void loadMessageCommand(){
        MessageCommands[] set = MessageCommands.values();
        for(int i = 0; i< set.length; i++){
            MessageCommands messageCommands = set[i];
            messageCommandMap.put((short) messageCommands.command_id, messageCommands);
            logger.info("messageCommands load:" + messageCommands);
        }
    }

    public MessageCommands getMessageCommand(short commandId){
        return messageCommandMap.get(commandId);
    }

    public void reload() throws Exception {
        loadMessageCommand();
        loadPackage(GlobalConstants.MessageCommandConstants.MsgNameSpace,
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

