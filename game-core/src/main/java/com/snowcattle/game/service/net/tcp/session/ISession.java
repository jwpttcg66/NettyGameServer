package com.snowcattle.game.service.net.tcp.session;

import com.snowcattle.game.service.message.AbstractNetMessage;

/**
 * Created by jwp on 2017/2/9.
 *  封装会话的业务逻辑
 */
public interface ISession {

    /**
     * 判断当前会话是否处于连接状态
     *
     * @return
     */
    public boolean isConnected();

    /**
     * @param msg
     */
    public void write(AbstractNetMessage msg) throws Exception;

    /**
     *
     */
    public void close(boolean immediately);

    /**
     * 出现异常时是否关闭连接
     *
     * @return
     */
    public boolean closeOnException();

    public void write(byte[] msg);

}

