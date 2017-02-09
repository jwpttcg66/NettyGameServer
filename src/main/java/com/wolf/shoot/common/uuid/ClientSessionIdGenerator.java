package com.wolf.shoot.common.uuid;

import java.util.concurrent.atomic.AtomicLong;
/**
 * Created by jwp on 2017/2/9.
 * sessionId生成器
 */

public class ClientSessionIdGenerator {

    protected AtomicLong id_gen = new AtomicLong(0);

    public long getSessionId(){
        return id_gen.incrementAndGet();
    }
}