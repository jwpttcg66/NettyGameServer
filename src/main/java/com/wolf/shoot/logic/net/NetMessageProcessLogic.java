package com.wolf.shoot.logic.net;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.exception.GameHandlerException;
import com.wolf.shoot.common.util.ErrorsUtil;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.net.message.AbstractNetMessage;
import com.wolf.shoot.service.net.message.AbstractNetProtoBufMessage;
import com.wolf.shoot.service.net.message.facade.GameFacade;
import com.wolf.shoot.service.net.message.factory.ITcpMessageFactory;
import com.wolf.shoot.service.net.session.NettySession;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 17/2/22.
 * 消息的真正处理
 */
@Service
public class NetMessageProcessLogic {

    protected static final Logger logger = Loggers.sessionLogger;
    protected static final Logger statLog = Loggers.serverStatusStatistics;

    public void processMessage(AbstractNetMessage message, NettySession nettySession){
        long begin = 0;
        try {
            GameFacade gameFacade = LocalMananger.getInstance().getLocalSpringServiceManager().getGameFacade();
            AbstractNetProtoBufMessage respone = null;
            respone = (AbstractNetProtoBufMessage) gameFacade.dispatch(message);
            if(respone != null) {
                respone.setSerial(message.getNetMessageHead().getSerial());
                nettySession.write(respone);
            }
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
                            + time + "ms");
                }
            }

        }
    }

}
