package com.wolf.shoot.service.net.message.process;

import com.wolf.shoot.common.IUpdatable;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.exception.GameHandlerException;
import com.wolf.shoot.common.util.ErrorsUtil;
import com.wolf.shoot.logic.net.NetMessageProcessLogic;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.net.message.AbstractNetMessage;
import com.wolf.shoot.service.net.message.AbstractNetProtoBufMessage;
import com.wolf.shoot.service.net.message.factory.ITcpMessageFactory;
import com.wolf.shoot.service.net.session.NettySession;
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
                NetMessageProcessLogic netMessageProcessLogic = LocalMananger.getInstance().get(NetMessageProcessLogic.class);
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
