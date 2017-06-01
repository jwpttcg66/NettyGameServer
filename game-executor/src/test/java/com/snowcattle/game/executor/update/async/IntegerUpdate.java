package com.snowcattle.game.executor.update.async;

import com.snowcattle.game.executor.update.entity.AbstractUpdate;
import com.snowcattle.game.executor.common.utils.Loggers;

/**
 * Created by jiangwenping on 17/1/16.
 */
public class IntegerUpdate extends AbstractUpdate<Long> {

    private long incrId;

    private  long maxSize;
    private  long startTime;
    public IntegerUpdate(long id, long maxSize) {
        setUpdateId(id);
        this.maxSize = maxSize;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        incrId++;
        long difference  = incrId;
        if (difference == maxSize) {
            setActive(false);
            long endTime = System.currentTimeMillis();
            long useTime = endTime - startTime;
            System.out.println("耗时" + useTime + "id" + getUpdateId());
        }
        if (Loggers.gameExecutorUtil.isDebugEnabled()) {
            Loggers.gameExecutorUtil.debug("update id " + getUpdateId() + " incrId:" + incrId);
        }
    }

    public String toString() {
        return String.valueOf(this.getUpdateId());
    }
}
