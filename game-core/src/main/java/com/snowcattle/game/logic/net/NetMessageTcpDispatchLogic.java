package com.snowcattle.game.logic.net;

import com.snowcattle.game.common.constant.CommonErrorLogInfo;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.service.net.tcp.MessageAttributeEnum;
import com.snowcattle.game.service.message.AbstractNetMessage;
import com.snowcattle.game.service.net.tcp.session.NettyTcpSession;
import com.snowcattle.game.common.util.ErrorsUtil;
import com.snowcattle.game.service.net.tcp.process.IMessageProcessor;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 17/2/15.
 */
@Service
public class NetMessageTcpDispatchLogic {

    /** 处理的消息总数 */
    public long statisticsMessageCount = 0;

    public void dispatchTcpMessage(AbstractNetMessage msg, IMessageProcessor iMessageProcessor){
        if (msg == null) {
            if (Loggers.serverStatusStatistics.isWarnEnabled()) {
                Loggers.serverStatusStatistics.warn("[#CORE.QueueMessageExecutorProcessor.process] ["
                        + CommonErrorLogInfo.MSG_PRO_ERR_NULL_MSG + "]");
            }
            return;
        }
        long begin = 0;
        if (Loggers.serverStatusStatistics.isInfoEnabled()) {
            begin = System.nanoTime();
        }
        statisticsMessageCount++;
        try {
            NettyTcpSession clientSesion = (NettyTcpSession) msg.getAttribute(MessageAttributeEnum.DISPATCH_SESSION);
            if(clientSesion != null){
//                GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
//                RpcConfig rpcConfig = gameServerConfigService.getRpcConfig();
//                if(rpcConfig.getSdRpcServiceProvider().isWorldOpen()){
//                    Loggers.serverStatusStatistics.debug("processor session" + clientSesion.getPlayerId() + " process message" + msg.getCmd());
//                    clientSesion.addNetMessage(msg);
//                }
                clientSesion.addNetMessage(msg);
            }else{
                if(Loggers.serverStatusStatistics.isInfoEnabled()) {
                    Loggers.serverStatusStatistics.info("session is closed, the message is unDispatch");
                }
            }

        } catch (Exception e) {
            if (Loggers.errorLogger.isErrorEnabled()) {
                Loggers.errorLogger.error(ErrorsUtil.error("Error",
                        "#.QueueMessageExecutorProcessor.process", "param"), e);
            }

        } finally {
//            msg.removeAttribute(MessageAttributeEnum.DISPATCH_SESSION);

            if (Loggers.serverStatusStatistics.isInfoEnabled()) {
                // 特例，统计时间跨度
                long time = (System.nanoTime() - begin) / (1000 * 1000);
                if (time > 1) {
                    Loggers.serverStatusStatistics.info("#CORE.MSG.PROCESS.DISPATCH_STATICS disptach Message id:"
                            + msg.getCmd() + " Time:"
                            + time + "ms" + " Total:"
                            + statisticsMessageCount);
                }
            }
        }
    }
}
