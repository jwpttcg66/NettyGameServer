package com.wolf.shoot.net.message.process;

import com.wolf.shoot.common.IUpdatable;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.exception.GameHandlerException;
import com.wolf.shoot.common.util.ErrorsUtil;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.net.message.AbstractNetMessage;
import com.wolf.shoot.net.message.AbstractNetProtoBufMessage;
import com.wolf.shoot.net.message.facade.GameFacade;
import com.wolf.shoot.net.message.facade.IFacade;
import com.wolf.shoot.net.message.factory.IMessageFactory;
import com.wolf.shoot.net.session.NettySession;
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
    public NetProtoBufMessageProcess(NettySession nettySession) {
        this.netMessagesQueue = new ConcurrentLinkedDeque<AbstractNetProtoBufMessage>();
        this.nettySession = nettySession;
    }

    @Override
    public void processNetMessage() {
        int i = 0;
        AbstractNetProtoBufMessage message = null;
        while ((message = netMessagesQueue.poll())!= null && i < GlobalConstants.Constants.session_prcoss_message_max_size) {
            i++;
            long begin = 0;
            if (logger.isInfoEnabled()) {
                begin = System.nanoTime();
            }
            statisticsMessageCount++;
            try {
                GameFacade gameFacade = (GameFacade) LocalMananger.getInstance().get(IFacade.class);
                AbstractNetProtoBufMessage respone = null;
                respone = (AbstractNetProtoBufMessage) gameFacade.dispatch(message);
                if(respone != null) {
                    respone.setSerial(message.getNetMessageHead().getSerial());
                    nettySession.write(respone);
//                }
                }
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    Loggers.errorLogger.error(ErrorsUtil.error("Error",
                            "#.QueueMessageExecutorProcessor.process", "param"), e);
                }

                if(e instanceof GameHandlerException){
                    GameHandlerException gameHandlerException = (GameHandlerException) e;
                    IMessageFactory iMessageFactory = LocalMananger.getInstance().get(IMessageFactory.class);
                    AbstractNetMessage errorMessage = iMessageFactory.createCommonErrorResponseMessage(gameHandlerException.getSerial(), message.getNetMessageHead().getCmd(), gameHandlerException.COMMON_ERROR_STATE);
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
    public boolean update() {
        try {
            processNetMessage();
        } catch (Exception e) {
            Loggers.errorLogger.error(e.toString(), e);
        }

        return false;
    }
}
