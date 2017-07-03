package com.snowcattle.game.service.net.tcp.session;

/**
 * Created by jiangwenping on 17/2/21.
 */
public enum TcpNetState {
    //链接状态
    CONNECTED,
    //掉线中
    DISCONNECTING,
    //掉线
    DISCONNECTED,
    //销毁
    DESTROY;
}
