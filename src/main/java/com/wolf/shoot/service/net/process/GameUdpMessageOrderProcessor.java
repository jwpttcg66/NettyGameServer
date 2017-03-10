package com.wolf.shoot.service.net.process;

import com.wolf.shoot.common.thread.executor.OrderedQueuePoolExecutor;
import com.wolf.shoot.service.net.message.AbstractNetMessage;

/**
 * Created by jiangwenping on 17/3/10.
 * game udp按照
 */
public class GameUdpMessageOrderProcessor implements  IMessageProcessor{

    private OrderedQueuePoolExecutor orderedQueuePoolExecutor;

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void put(AbstractNetMessage msg) {

    }

    @Override
    public boolean isFull() {
        return false;
    }
}
