package com.wolf.shoot.net.session;

import com.wolf.shoot.net.message.NetMessage;

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
    public void write(NetMessage msg) throws Exception;

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

    /**
     * 获取sessionId
     * @return
     */
    public long getSessionId();

    public void write(byte[] msg);

}

