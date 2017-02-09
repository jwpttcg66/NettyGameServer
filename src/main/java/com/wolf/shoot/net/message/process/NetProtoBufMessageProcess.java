package com.wolf.shoot.net.message.process;

import com.wolf.shoot.common.IUpdatable;
import com.wolf.shoot.net.message.NetProtoBufMessage;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by jwp on 2017/2/9.
 * 消息处理器
 */
public class NetProtoBufMessageProcess implements INetProtoBufMessageProcess, IUpdatable{

    /**
     * 网络消息处理队列
     */
    private Queue<NetProtoBufMessage> netMessages;

    public NetProtoBufMessageProcess() {
        this.netMessages = new ConcurrentLinkedDeque<NetProtoBufMessage>();
    }

    @Override
    public void processNetMessage(NetProtoBufMessage netMessage) {

    }

    @Override
    public boolean update() {
        return false;
    }
}
