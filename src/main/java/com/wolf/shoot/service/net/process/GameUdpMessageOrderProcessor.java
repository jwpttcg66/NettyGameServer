package com.wolf.shoot.service.net.process;

import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.thread.executor.OrderedQueuePoolExecutor;
import com.wolf.shoot.common.thread.worker.AbstractWork;
import com.wolf.shoot.common.util.ExecutorUtil;
import com.wolf.shoot.logic.net.NetMessageProcessLogic;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.net.MessageAttributeEnum;
import com.wolf.shoot.service.net.message.AbstractNetMessage;
import com.wolf.shoot.service.net.message.AbstractNetProtoBufMessage;
import com.wolf.shoot.service.net.message.AbstractNetProtoBufUdpMessage;
import com.wolf.shoot.service.net.session.NettyUdpSession;
import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiangwenping on 17/3/10.
 * game udp按照
 */
public class GameUdpMessageOrderProcessor implements  IMessageProcessor{
    protected static final Logger logger = Loggers.msgLogger;
    private OrderedQueuePoolExecutor orderedQueuePoolExecutor;
    private int workSize;
    @Override
    public void start() {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        int udpWorkerSize = gameServerConfigService.getGameServerConfig().getUpdQueueMessageProcessWorkerSize();
        orderedQueuePoolExecutor = new OrderedQueuePoolExecutor(GlobalConstants.Thread.NET_UDP_MESSAGE_PROCESS, udpWorkerSize, Integer.MAX_VALUE);
        this.workSize = udpWorkerSize;
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
        AbstractNetProtoBufUdpMessage abstractNetProtoBufUdpMessage = (AbstractNetProtoBufUdpMessage) msg;
        //直接执行
        long playerId = abstractNetProtoBufUdpMessage.getPlayerId();
        long index = playerId % workSize;
        orderedQueuePoolExecutor.addTask(index, new UdpWorker(msg));
    }

    @Override
    public boolean isFull() {
        return false;
    }


    private final class UdpWorker extends AbstractWork {

        private AbstractNetMessage netMessage;

        public UdpWorker(AbstractNetMessage netMessage) {
            this.netMessage = netMessage;
        }

        @Override
        public void run() {
            AbstractNetProtoBufMessage abstractNetProtoBufMessage = (AbstractNetProtoBufMessage) netMessage;
            NettyUdpSession clientSesion = (NettyUdpSession) abstractNetProtoBufMessage.getAttribute(MessageAttributeEnum.DISPATCH_SESSION);
            //所有的session已经强制绑定了，这里不需要再判定空了
            if (logger.isDebugEnabled()) {
                logger.debug("processor session" + clientSesion.getPlayerId() + " process message" + abstractNetProtoBufMessage.toAllInfoString());
            }

            NetMessageProcessLogic netMessageProcessLogic = LocalMananger.getInstance().getLocalSpringBeanManager().getNetMessageProcessLogic();
            netMessageProcessLogic.processMessage(netMessage, clientSesion);

        }
    }
}
