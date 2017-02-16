package com.wolf.shoot.net.session;

import com.wolf.shoot.common.uuid.ClientSessionIdGenerator;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.net.message.NetMessage;
import io.netty.channel.Channel;

/**
 * Created by jwp on 2017/2/9.
 * netty会话
 */
public abstract class NettySession implements ISession  {

    protected volatile Channel channel;

    private long sessionId;

    private long playerId;

    public NettySession(Channel s) {
        ClientSessionIdGenerator clientSessionIdGenerator = LocalMananger.getInstance().get(ClientSessionIdGenerator.class);
        this.sessionId = clientSessionIdGenerator.getSessionId();
        channel = s;
    }

    @Override
    public boolean isConnected() {
        if (channel != null) {
            return channel.isActive();
        }
        return false;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void write(NetMessage msg) throws Exception {
        if (msg != null) {
            channel.writeAndFlush(msg);
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

    public long getSessionId() {
        return sessionId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    @Override
    public void write(byte[] msg) {
        if (channel != null) {
            channel.writeAndFlush(msg);
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
