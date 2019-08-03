package com.snowcattle.game.service.message.process;

import com.snowcattle.game.logic.net.NetMessageProcessLogic;
import com.snowcattle.game.common.IUpdatable;
import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.message.AbstractNetMessage;
import com.snowcattle.game.service.message.AbstractNetProtoBufMessage;
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

    /** 处理的消息总数 */
    protected long statisticsMessageCount = 0;

    /**
     * 网络消息处理队列
     */
    private final Queue<AbstractNetProtoBufMessage> netMessagesQueue;
    private final NettySession nettySession;

    /** 中断消息处理 */
    protected boolean suspendedProcess;

    public NetProtoBufMessageProcess(NettySession nettySession) {
        this.netMessagesQueue = new ConcurrentLinkedDeque<AbstractNetProtoBufMessage>();
        this.nettySession = nettySession;
    }

    @Override
    public void processNetMessage() {
        int i = 0;
        AbstractNetProtoBufMessage message;
        while (!isSuspendedProcess() && (message = netMessagesQueue.poll())!= null && i < GlobalConstants.Constants.session_prcoss_message_max_size) {
            i++;
            statisticsMessageCount++;
            NetMessageProcessLogic netMessageProcessLogic = LocalMananger.getInstance().getLocalSpringBeanManager().getNetMessageProcessLogic();
            netMessageProcessLogic.processMessage(message, nettySession);
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
