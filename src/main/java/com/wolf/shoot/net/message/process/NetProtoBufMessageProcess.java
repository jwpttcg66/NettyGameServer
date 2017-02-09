package com.wolf.shoot.net.message.process;

import com.wolf.shoot.common.IUpdatable;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.exception.GameHandlerException;
import com.wolf.shoot.common.util.ErrorsUtil;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.net.message.NetProtoBufMessage;
import com.wolf.shoot.net.message.facade.GameFacade;
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
    private Queue<NetProtoBufMessage> netMessagesQueue;

    public NetProtoBufMessageProcess() {
        this.netMessagesQueue = new ConcurrentLinkedDeque<NetProtoBufMessage>();
    }

    @Override
    public void processNetMessage() {
        int i = 0;
        NetProtoBufMessage message = null;
        while ((message = netMessagesQueue.poll())!= null && i < GlobalConstants.Constants.session_prcoss_message_max_size) {
            i++;
            long begin = 0;
            if (logger.isInfoEnabled()) {
                begin = System.nanoTime();
            }
            statisticsMessageCount++;
            try {
                GameFacade gameFacade = (GameFacade) LocalMananger.getInstance().get(GameFacade.class);
                NetProtoBufMessage respone = null;
                respone = (NetProtoBufMessage) gameFacade.dispach(message);
                if(respone != null) {
                    respone.setSerial(message.getNetMessageHead().getSerial());
//                    write(respone);
//                }
                }
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    Loggers.errorLogger.error(ErrorsUtil.error("Error",
                            "#.QueueMessageExecutorProcessor.process", "param"), e);
                }

                if(e instanceof GameHandlerException){
                    GameHandlerException gameHandlerException = (GameHandlerException) e;
//                    IMessageFactory iMessageFactory = LocalMananger.getInstance().get(IMessageFactory.class);
//                    AbstractGameMessage errorMessage = iMessageFactory.createCommonErrorResponseMessage(gameHandlerException.getSerial(), message.getCommandId(), gameHandlerException.COMMON_ERROR_STATE);
//                    write(errorMessage);
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
    public boolean update() {
        try {
            processNetMessage();
        } catch (Exception e) {
            Loggers.errorLogger.error(e.toString(), e);
        }

        return false;
    }
}
