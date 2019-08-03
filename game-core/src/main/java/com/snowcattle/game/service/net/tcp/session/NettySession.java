package com.snowcattle.game.service.net.tcp.session;

import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.exception.NetMessageException;
import com.snowcattle.game.service.message.AbstractNetMessage;
import io.netty.channel.Channel;
import org.slf4j.Logger;

/**
 * Created by jwp on 2017/2/9.
 * netty会话
 */
public abstract class NettySession implements ISession  {

    private static final Logger errorLogger = Loggers.errorLogger;

    protected volatile Channel channel;


    private long playerId;

    public NettySession(Channel s) {
        channel = s;
    }

    @Override
    public boolean isConnected() {
        if (channel != null) {
            return channel.isActive();
        }
        return false;
    }

    @SuppressWarnings({ "rawtypes" })
    @Override
    public void write(AbstractNetMessage msg) throws Exception {
        if (msg != null) {
            try {
                channel.writeAndFlush(msg);
            }catch (Exception e){
                errorLogger.info("session write msg exception", e);
                throw new NetMessageException(e);
            }

//			if(msg instanceof ISliceMessage){
//				final ISliceMessage<BaseMinaMessage> _slices = (ISliceMessage<BaseMinaMessage>) msg;
//				for(final BaseMinaMessage _msg:_slices.getSliceMessages()){
//					// 统计消息数据
//					StatisticsLoggerHelper.logMessageSent(msg);
//					session.write(_msg);
//				}
//			}else{
//				StatisticsLoggerHelper.logMessageSent(msg);
//				session.write(msg);
//			}
        }
    }

    @Override
    public void close(boolean immediately) {
        if (channel != null) {
            channel.close();
        }
    }


    public boolean closeOnException() {
        return true;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    @Override
    public void write(byte[] msg) throws Exception {
        if (channel != null) {
            try {
                channel.writeAndFlush(msg);
            }catch (Exception e){
                errorLogger.info("session write bytes exception", e);
                throw new NetMessageException(e);
            }
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
