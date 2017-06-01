package com.snowcattle.game.db.service.async.thread;

import com.snowcattle.game.db.common.Loggers;
import com.snowcattle.game.db.service.entity.EntityService;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by jwp on 2017/4/18.
 * 监视器
 */
public class AsyncDbOperationMonitor{

    private Logger logger = Loggers.dbServerLogger;
    public AsyncDbOperationMonitor() {
        this.count = new AtomicLong();
    }

    public AtomicLong count;

//    public long startTime;

    private boolean totalFlag = true;
    public long startTime = System.currentTimeMillis();

    public void start(){
        if(!totalFlag){
            this.count.set(0);
            startTime = System.currentTimeMillis();
        }

    }
    public void monitor(){
        this.count.getAndIncrement();
    }

    public void stop()
    {
        if(!totalFlag) {
            this.count.set(0);
        }
    }

    public void printInfo(String opeartionName){
        long endTime = System.currentTimeMillis();
        long useTime = endTime - startTime;
        logger.debug("operation " + opeartionName + " count " + count.get() + "use time" + useTime);
    }

}
