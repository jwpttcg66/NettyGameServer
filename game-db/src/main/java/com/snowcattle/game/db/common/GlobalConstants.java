package com.snowcattle.game.db.common;

/**
 * Created by jwp on 2017/2/28.
 */
public class GlobalConstants {

    /**
     * Thread的名字前缀
     */
    public static class Thread{
        public static final String GAME_DB_EXCUTE="game_db";
        public static final String GAME_DB_SYN_EXCUTE="game_db_syn";
        public static final String GAME_DB_SYN_UNCHAR_EXCUTE="game_db_syn_unchar_excute";
        public static final String GAME_MESSAGE_QUEUE_EXCUTE="game_message_queue";
    }

    /**
     * redis Key的基本配置
     */
    public static class RedisKeyConfig{
        /**正常缓存有效时间 一天*/
        public static final int NORMAL_LIFECYCLE=86400;
        //mget时，key的最大值
        public static final int MGET_MAX_KEY=50;
        /**正常缓存有效时间一个月*/
        public static final int NORMAL_MONTH_LIFECYCLE=86400 * 30;
    }

    /**
     * Thread的名字前缀
     */
    public static class RedisKeyString{
        public static final String GAME_DB_EXCUTE="async_db";
    }

    /**
     * class常量
     */
    public static class ClassConstants{
        public static final String Ext = ".class";
    };
}
