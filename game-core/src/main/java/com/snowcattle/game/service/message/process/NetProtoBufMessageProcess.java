package com.snowcattle.game.service.message.process;

import com.snowcattle.game.logic.net.NetMessageProcessLogic;
import com.snowcattle.game.common.IUpdatable;
import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.exception.GameHandlerException;
import com.snowcattle.game.common.util.ErrorsUtil;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.message.AbstractNetMessage;
import com.snowcattle.game.service.message.AbstractNetProtoBufMessage;
import com.snowcattle.game.service.message.factory.ITcpMessageFactory;
import com.snowcattle.game.service.net.tcp.session.NettySession;
import org.slf4j.Logger;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by jwp on 2017/2/9.
 * 消息处理器
 */
public class NetProtoBufMessageProcess implements INetProtoBufMessageProcess, IUpdatable{

    protected static final Logger logger = Loggers.sessionLogger;
    protected static final Logger statLog = Loggers.serverStatusStatistics;

    /** 处理的消息总数 */
    protected long statisticsMessageCount = 0;

    /**
     * 网络消息处理队列
     */
    private Queue<AbstractNetProtoBufMessage> netMessagesQueue;
    private NettySession nettySession;

    /** 中断消息处理 */
    protected boolean suspendedProcess;

    public NetProtoBufMessageProcess(NettySession nettySession) {
        this.netMessagesQueue = new ConcurrentLinkedDeque<AbstractNetProtoBufMessage>();
        this.nettySession = nettySession;
    }

    @Override
    public void processNetMessage() {
        int i = 0;
        AbstractNetProtoBufMessage message = null;
        while (!isSuspendedProcess() && (message = netMessagesQueue.poll())!= null && i < GlobalConstants.Constants.session_prcoss_message_max_size) {
            i++;
            long begin = 0;
            if (logger.isInfoEnabled()) {
                begin = System.nanoTime();
            }
            statisticsMessageCount++;
            try {
                NetMessageProcessLogic netMessageProcessLogic = LocalMananger.getInstance().getLocalSpringBeanManager().getNetMessageProcessLogic();
                netMessageProcessLogic.processMessage(message, nettySession);
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    Loggers.errorLogger.error(ErrorsUtil.error("Error",
                            "#.QueueMessageExecutorProcessor.process", "param"), e);
                }

                if(e instanceof GameHandlerException){
                    GameHandlerException gameHandlerException = (GameHandlerException) e;
                    ITcpMessageFactory iTcpMessageFactory = LocalMananger.getInstance().get(ITcpMessageFactory.class);
                    AbstractNetMessage errorMessage = iTcpMessageFactory.createCommonErrorResponseMessage(gameHandlerException.getSerial(), gameHandlerException.COMMON_ERROR_STATE);
                    try {
                        nettySession.write(errorMessage);
                    }catch (Exception writeException){
                        Loggers.errorLogger.error(ErrorsUtil.error("Error",
                                "#.QueueMessageExecutorProcessor.writeErrorMessage", "param"), e);
                    }

                }

            } finally {
                if (logger.isInfoEnabled()) {
                    // 特例，统计时间跨度
                    long time = (System.nanoTime() - begin) / (1000 * 1000);
                    if (time > 1) {
                        statLog.info("#CORE.MSG.PROCESS.STATICS Message id:"
                                + message.getNetMessageHead().getCmd() + " Time:"
                                + time + "ms" + " Total:"
                                + statisticsMessageCount);
                    }
                }

            }

        }
    }

    @Override
    public void addNetMessage(AbstractNetMessage abstractNetMessage) {
        this.netMessagesQueue.add((AbstractNetProtoBufMessage) abstractNetMessage);
    }

    @Override
    public void close() {
        this.netMessagesQueue.clear();
        setSuspendedProcess(true);
    }

    @Override
    public boolean update() {
        try {

            processNetMessage();
        } catch (Exception e) {
            Loggers.errorLogger.error(e.toString(), e);
        }

        return false;
    }

    public boolean isSuspendedProcess() {
        return suspendedProcess;
    }

    public void setSuspendedProcess(boolean suspendedProcess) {
        this.suspendedProcess = suspendedProcess;
    }
}
