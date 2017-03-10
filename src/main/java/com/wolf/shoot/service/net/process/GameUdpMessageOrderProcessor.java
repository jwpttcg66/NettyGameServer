package com.wolf.shoot.service.net.process;

import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.thread.executor.OrderedQueuePoolExecutor;
import com.wolf.shoot.common.util.ExecutorUtil;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.net.message.AbstractNetMessage;
import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiangwenping on 17/3/10.
 * game udp按照
 */
public class GameUdpMessageOrderProcessor implements  IMessageProcessor{
    protected static final Logger logger = Loggers.msgLogger;
    private OrderedQueuePoolExecutor orderedQueuePoolExecutor;

    @Override
    public void start() {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        int udpWorkerSize = gameServerConfigService.getGameServerConfig().getUpdQueueMessageProcessWorkerSize();
        orderedQueuePoolExecutor = new OrderedQueuePoolExecutor(GlobalConstants.Thread.NET_UDP_MESSAGE_PROCESS, udpWorkerSize, Integer.MAX_VALUE);
        logger.info("GameUdpMessageOrderProcessor executor " + this + " started");
    }

    @Override
    public void stop() {

        if (this.orderedQueuePoolExecutor != null) {
            ExecutorUtil.shutdownAndAwaitTermination(this.orderedQueuePoolExecutor, 50,
                    TimeUnit.MILLISECONDS);
            this.orderedQueuePoolExecutor = null;
        }
        logger.info("GameUdpMessageOrderProcessor executor " + this + " stopped");
    }

    @Override
    public void put(AbstractNetMessage msg) {
        //直接执行
    }

    @Override
    public boolean isFull() {
        return false;
    }
}
