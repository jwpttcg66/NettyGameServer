package com.snowcattle.game.db.service.common.uuid;

import com.snowcattle.game.db.common.DbServiceName;
import com.snowcattle.game.db.service.common.service.IDbService;
import com.snowcattle.game.db.service.config.DbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 17/3/16.
 * 低15为为同一时间序列号32768, 中间10位位服务器节点最大为1024， 高38位位当前时间跟开始时间的差值，(1L << 38) / (1000L * 60 * 60 * 24 * 365) 可以用8年
 */
@Service
public class SnowFlakeUUIDService implements IUUIDService, IDbService{
    // ==============================Fields===========================================
    /** 开始时间截 (2017-01-01) */
    private final long twepoch = 1483200000000L;


    /** node id所占的位数 */
    private final long nodeIdBits = 10L;

    /** 支持的最大机器nodeid，结果是1024 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long maxNodeId = -1L ^ (-1L << nodeIdBits);

    /** 序列在id中占的位数 */
    private final long sequenceBits = 15L;

    /** 时间截向左移25位(10+15) */
    private final long timestampLeftShift = sequenceBits + nodeIdBits;

    /** 生成序列的掩码，这里为4095*8 **/
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);


    //序列号
    private  long sequence;

    //产生的事件(毫秒)
    private long referenceTime;

    //节点号（0-1024）
    private int nodeId;

    @Autowired
    private DbConfig dbConfig;

    //用于spring构造
    public SnowFlakeUUIDService() {

    }
    /**
     * A snowflake is designed to operate as a singleton instance within the context of a node.
     * If you deploy different nodes, supplying a unique node id will guarantee the uniqueness
     * of ids generated concurrently on different nodes.
     *
     * @param node This is an id you use to differentiate different nodes.
     */
    public SnowFlakeUUIDService(int nodeId) {
        if (nodeId < 0 || nodeId > maxNodeId) {
            throw new IllegalArgumentException(String.format("node must be between %s and %s", 0, maxNodeId));
        }
        this.nodeId = nodeId;
    }

    /**
     * Generates a k-ordered unique 64-bit integer. Subsequent invocations of this method will produce
     * increasing integer values.
     *
     * @return The next 64-bit integer.
     */
    public long nextId() {

        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < referenceTime) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", referenceTime - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (referenceTime == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(referenceTime);
                if (timestamp <= referenceTime) {
                    throw new RuntimeException(
                            String.format("Clock til moved backwards.  Refusing to generate id for %d milliseconds", referenceTime - timestamp));
                }
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        referenceTime = timestamp;

        return (referenceTime - twepoch)  << timestampLeftShift | nodeId << sequenceBits | sequence;
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }


    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    public void setNodeId(int nodeId) {
        if (nodeId < 0 || nodeId > maxNodeId) {
            throw new IllegalArgumentException(String.format("node must be between %s and %s", 0, maxNodeId));
        }
        this.nodeId = nodeId;
    }

    @Override
    public String getDbServiceName() {
        return DbServiceName.uuidService;
    }

    @Override
    public void startup() throws Exception {
        setNodeId(dbConfig.getDbId());
    }

    @Override
    public void shutdown() throws Exception {

    }

    public DbConfig getDbConfig() {
        return dbConfig;
    }

    public void setDbConfig(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }
}

