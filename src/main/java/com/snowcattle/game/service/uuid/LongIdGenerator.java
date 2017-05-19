package com.snowcattle.game.service.uuid;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
/**
 * Created by jwp on 2017/2/9.
 * sessionId生成器
 */
@Service
public class LongIdGenerator {

    protected AtomicLong id_gen = new AtomicLong(0);

    public long generateId(){
        return id_gen.incrementAndGet();
    }
}