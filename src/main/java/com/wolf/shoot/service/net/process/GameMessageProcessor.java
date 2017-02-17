package com.wolf.shoot.service.net.process;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.net.message.AbstractNetMessage;
import com.wolf.shoot.server.GameServerRuntime;
import org.slf4j.Logger;


/**
 * Created by jiangwenping on 17/2/13.
 */
public class GameMessageProcessor implements IMessageProcessor {
    protected static final Logger log = Loggers.msgLogger;

    /** 主消息处理器，处理服务器内部消息、玩家不属于任何场景时发送的消息 */
    private IMessageProcessor mainMessageProcessor;

    public GameMessageProcessor(IMessageProcessor messageProcessor) {
        mainMessageProcessor = messageProcessor;
    }

    @Override
    public boolean isFull() {
        return mainMessageProcessor.isFull();
    }

    /**
     * <pre>
     * 1、服务器内部消息、玩家不属于任何场景时发送的消息，单独一个消息队列进行处理
     * 2、玩家在场景中发送过来的消息，添加到玩家的消息队列中，在场景的线程中进行处理
     * </pre>
     */
    @Override
    public void put(AbstractNetMessage msg) {
        if (!GameServerRuntime.isOpen()) {
            log.info("【Receive but will not process because server not open】"	+ msg);
            return;
        }

        mainMessageProcessor.put(msg);

    }

    @Override
    public void directPutTcpMessage(AbstractNetMessage msg) {
        if (!GameServerRuntime.isOpen()) {
            log.info("【Direct put Receive but will not process because server not open】"	+ msg);
            return;
        }

        mainMessageProcessor.directPutTcpMessage(msg);
    }

    @Override
    public void start() {
        mainMessageProcessor.start();
    }

    @Override
    public void stop() {
        mainMessageProcessor.stop();
    }

}

