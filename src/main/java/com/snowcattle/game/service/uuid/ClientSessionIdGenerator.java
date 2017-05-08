package com.snowcattle.game.service.uuid;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
/**
 * Created by jwp on 2017/2/9.
 * sessionId生成器
 */
@Service
public class ClientSessionIdGenerator {

    protected AtomicLong id_gen = new AtomicLong(0);

    public long generateSessionId(){
        return id_gen.incrementAndGet();
    }
}