package com.snowcattle.game.service.net.tcp.process;

import com.snowcattle.game.common.config.GameServerDiffConfig;
import com.snowcattle.game.common.constant.CommonErrorLogInfo;
import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.util.ErrorsUtil;
import com.snowcattle.game.common.util.ExecutorUtil;
import com.snowcattle.game.logic.net.NetMessageProcessLogic;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.net.tcp.MessageAttributeEnum;
import com.snowcattle.game.common.ThreadNameFactory;
import com.snowcattle.game.service.message.AbstractNetMessage;
import com.snowcattle.game.service.message.AbstractNetProtoBufMessage;
import com.snowcattle.game.service.net.udp.session.NettyUdpSession;
import org.slf4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by jiangwenping on 17/2/13.
 */
public class QueueMessageExecutorProcessor implements IMessageProcessor {
    public static final Logger logger = Loggers.serverStatusStatistics;
    /** 消息队列 * */
    protected final BlockingQueue<AbstractNetMessage> queue;

    /** 消息处理线程停止时剩余的还未处理的消息 **/
    private volatile List<AbstractNetMessage> leftQueue;

    /** 消息处理线程池 */
    private volatile ExecutorService executorService;

    /** 线程池的线程个数 */
    private int excecutorCoreSize;

    /** 是否停止 */
    private volatile boolean stop = false;

    /** 处理的消息总数 */
    public long statisticsMessageCount = 0;

    private final boolean processLeft;

    @SuppressWarnings("unchecked")
    public QueueMessageExecutorProcessor() {
        this(false, 1);
    }

    @SuppressWarnings("unchecked")
    public QueueMessageExecutorProcessor(boolean processLeft, int executorCoreSize) {
        queue = new LinkedBlockingQueue<AbstractNetMessage>();
        this.processLeft = processLeft;
        this.excecutorCoreSize = executorCoreSize;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.mop.lzr.core.server.IMessageProcessor#put(com.mop.lzr.core.msg.
     * IMessage)
     */
    public void put(AbstractNetMessage msg) {
        try {
            queue.put(msg);
            if (logger.isDebugEnabled()) {
                logger.debug("put queue size:" + queue.size());
            }
        } catch (InterruptedException e) {
            if (logger.isErrorEnabled()) {
                logger.error(CommonErrorLogInfo.THRAD_ERR_INTERRUPTED, e);
            }
        }
    }

    /**
     * 处理具体的消息，每个消息有自己的参数和来源,如果在处理消息的过程中发生异常,则马上将此消息的发送者断掉
     *
     * @param msg
     */
    @SuppressWarnings("unchecked")
    public void process(AbstractNetMessage msg) {
        if (msg == null) {
            if (logger.isWarnEnabled()) {
                logger.warn("[#CORE.QueueMessageExecutorProcessor.process] ["
                        + CommonErrorLogInfo.MSG_PRO_ERR_NULL_MSG + "]");
            }
            return;
        }
        long begin = 0;
        if (logger.isInfoEnabled()) {
            begin = System.nanoTime();
        }
        this.statisticsMessageCount++;
        try {
            AbstractNetProtoBufMessage abstractNetProtoBufMessage = (AbstractNetProtoBufMessage) msg;
            NettyUdpSession clientSesion = (NettyUdpSession) abstractNetProtoBufMessage.getAttribute(MessageAttributeEnum.DISPATCH_SESSION);
            //所有的session已经强制绑定了，这里不需要再判定空了
            if(logger.isDebugEnabled()) {
                logger.debug("processor session" + clientSesion.getPlayerId() + " process message" + abstractNetProtoBufMessage.toAllInfoString());
            }

            NetMessageProcessLogic netMessageProcessLogic = LocalMananger.getInstance().getLocalSpringBeanManager().getNetMessageProcessLogic();
            netMessageProcessLogic.processMessage(msg, clientSesion);

        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(ErrorsUtil.error("Error",
                        "#.QueueMessageExecutorProcessor.process", "param"), e);
            }

        } finally {
            if (logger.isInfoEnabled()) {
                // 特例，统计时间跨度
                long time = (System.nanoTime() - begin) / (1000 * 1000);
                if (time > 1) {
                    logger.info("#CORE.MSG.PROCESS.DISPATCH_STATICS disptach Message id:"
                            + msg.getNetMessageHead().getCmd() + " Time:"
                            + time + "ms" + " Total:"
                            + this.statisticsMessageCount);
                }
            }
        }
    }

    /**
     * 开始消息处理
     */
    public synchronized void start() {
        if (this.executorService != null) {
            throw new IllegalStateException(
                    "The executorSerive has not been stopped.");
        }
        stop = false;
        ThreadNameFactory factory = new ThreadNameFactory(GlobalConstants.Thread.GAME_MESSAGE_QUEUE_EXCUTE);
        this.executorService = Executors
                .newFixedThreadPool(this.excecutorCoreSize, factory);

        GameServerDiffConfig gameServerDiffConfig = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService().getGameServerDiffConfig();
        for (int i = 0; i < this.excecutorCoreSize; i++) {
            this.executorService.execute(new Worker());
        }

        logger.info("Message processor executorService started ["
                + this.executorService + " with " + this.excecutorCoreSize
                + " threads ]");
    }

    /**
     * 停止消息处理
     */
    public synchronized void stop() {
        logger.info("Message processor executor " + this + " stopping ...");
        this.stop = true;
        if (this.executorService != null) {
            ExecutorUtil.shutdownAndAwaitTermination(this.executorService, 50,
                    TimeUnit.MILLISECONDS);
            this.executorService = null;
        }
        logger.info("Message processor executor " + this + " stopped");
        if (this.processLeft) {
            // 将未处理的消息放入到leftQueue中,以备后续处理
            this.leftQueue = new LinkedList<AbstractNetMessage>();
            while (true) {
                AbstractNetMessage _msg = this.queue.poll();
                if (_msg != null) {
                    this.leftQueue.add(_msg);
                } else {
                    break;
                }
            }
        }
        this.queue.clear();
    }

    /**
     * 达到5000上限时认为满了
     */
    public boolean isFull() {
        return this.queue.size() > 5000;
    }

    /**
     * 取得消息处理器停止后的遗留的消息
     *
     * @return the leftQueue
     */
    public List<AbstractNetMessage> getLeftQueue() {
        return leftQueue;
    }

    /**
     * 重置
     */
    public void resetLeftQueue() {
        this.leftQueue = null;
    }

    private final class Worker implements Runnable {
        @Override
        public void run() {
            while (!stop) {
                try {
                    process(queue.take());
                    if (logger.isDebugEnabled()) {
                        logger.debug("run queue size:" + queue.size());
                    }
                } catch (InterruptedException e) {
                    if (logger.isWarnEnabled()) {
                        logger
                                .warn("[#CORE.QueueMessageExecutorProcessor.run] [Stop process]");
                    }
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(CommonErrorLogInfo.MSG_PRO_ERR_EXP, e);
                }
            }
        }
    }

}

