package com.snowcattle.game.common.constant;

import java.util.concurrent.TimeUnit;

/**
 * @author b053-mac
 * 全局变量
 */
public class GlobalConstants {

    /**
     *默认变量
     */
    public static final class Constants{
        public static final int session_prcoss_message_max_size = 10;
        public static final int life_cycle_interval = 6000;

        private Constants() {
        }
    }

    /**
     *心跳
     */
    public static final class HeartBeart{
        public static final int cycle_time = 50;
        /**误差大小*/
        public static final int cycle_diff_time = 150;

        private HeartBeart() {
        }
    }

    /**
     *异步线程
     */
    public static final class Damond{
        public static final int cycle_time = 500;
        /**误差大小*/
        public static final int cycle_diff_time = 500;
        /**清理欲分配房间*/
        public static final int clear_pre_room_cycle_time = 30000;

        public static final int pvp_match_cycle_time = 1000;

        private Damond() {
        }
    }


    /**
     *队列线程执行大小
     */
    public static final class QueueMessageExecutor{
        public static final boolean processLeft = true;

        private QueueMessageExecutor() {
        }
    }

    /**
     * redis Key的基本配置
     */
    public static final class RedisKeyConfig{
        /**正常缓存有效时间*/
        public static final int NORMAL_LIFECYCLE=86400;
        //mget时，key的最大值
        public static final int MGET_MAX_KEY=50;
        /**正常缓存有效时间一个月*/
        public static final int NORMAL_MONTH_LIFECYCLE=86400 * 24;

        private RedisKeyConfig() {
        }
    }

    /**
     * redis Key的基本配置
     */
    public static final class ConfigFile{
        /**game server*/
        public static final String GAME_SERVER_CONIFG="game_server.cfg.js";
        /**game server*/
        public static final String GAME_SERVER_DIFF_CONIFG="game_server_diff.cfg.js";
        /**rpc*/
        public static final String RPC_SERVER_REGISTER_CONFIG ="rpc-server-register.xml";
        /**dynmic*/
        public static final String DYNAMIC_CONFIG = "dynamic_config.properties";
        /**zookeeper*/
        public static final String ZOOKEEPER_CONFIG = "zookeeper.properties";
        /**rpcservice*/
        public static final String RPC_SERVEICE_CONFIG="rpc-service-register.xml";
        /**dictService*/
        public static final String DICT_ROOT_FILE="dict/dict.wg";
        /**proxy*/
        public static final String PROXY_SERVER_CONFIG="proxy-server.xml";
        /**udp-server*/
        public static final String UDP_SERVER_CONFIG="udp-server.xml";
        /**http-server*/
        public static final String HTTP_SERVER_CONFIG="http-server.xml";
        /**websocket-server*/
        public static final String WEBSOCKET_SERVER_CONFIG="websocket-server.xml";

        private ConfigFile() {
        }
    }

    /**
     * JSONFile
     */
    public static final class JSONFile{
        public static final String  dict_package  = "package";
        public static final String  dict_fils  = "dict";
        public static final String  multiKey  = "multiKey";
        public static final String  body  = "body";

        private JSONFile() {
        }
    }


    /**
     * Thread的名字前缀
     */
    public static final class Thread{
        public static final String NET_TCP_BOSS ="net_tcp_boss";
        public static final String NET_TCP_WORKER ="net_tcp_worker";
        public static final String GAME_MESSAGE_QUEUE_EXCUTE="game_message_queue";
        public static final String NET_UDP_WORKER ="net_udp_worker";
        public static final String NET_UDP_MESSAGE_PROCESS ="net_udp_message_process";
        public static final String NET_RPC_BOSS ="net_rpc_boss";
        public static final String NET_RPC_WORKER ="net_rpc_worker";
        public static final String START_FINISHED ="start_finished";
        public static final String DETECT_RPCPENDING ="detect_rpcpending";
        public static final String GAME_ASYNC_CALL = "game_async_call";
        public static final String RPC_HANDLER = "rpc_handler";
        public static final String ASYNC_EVENT_WORKER = "async_event_worker";
        public static final String ASYNC_EVENT_HANDLER = "async_event_handler";
        public static final String UPDATE_EXECUTOR_SERVICE = "UpdateExecutorService";
        public static final String NET_PROXY_BOSS ="net_proxy_boss";
        public static final String NET_PROXY_WORKER ="net_proxy_worker";
        public static final String NET_HTTP_BOSS ="net_http_boss";
        public static final String NET_HTTP_WORKER ="net_http_worker";

        public static final String NET_WEB_SOCKET_BOSS ="net_web_socket_boss";
        public static final String NET_WEB_SOCKET_WORKER ="net_web_socket_worker";

        private Thread() {
        }
    }

    /**
     * 网络
     */
    public static final class Net{

        /** 心跳间隔*/
        public static final int HEART_BASE_SIZE= 1;
        /** 心跳写时间超时(单位秒)*/
        public static final int SESSION_HEART_WRITE_TIMEOUT= HEART_BASE_SIZE * 60;
        /** 心跳写时间超时(单位秒)*/
        public static final int SESSION_HEART_READ_TIMEOUT= HEART_BASE_SIZE * 60;
        /** 心跳读写时间超时(单位秒)*/
        public static final int SESSION_HEART_ALL_TIMEOUT= HEART_BASE_SIZE * 60;

        private Net() {
        }
    }


    /**
     *上传协议
     */
    public static final class FileExtendConstants {
        public static final String Ext = ".class";

        private FileExtendConstants() {
        }
    };

    public static final class ZooKeeperConstants{
        public static final String registryAdress = "registry.address";
        public static final String ZK_REGISTRY_PATH = "/rpc_registry";
        public static final String ZK_DATA_PATH = "/data";
        public static final int ZK_SESSION_TIMEOUT = 5000;

        private ZooKeeperConstants() {
        }
    }


    /**
     * 网络管道
     */
    public static final class ChannelPipeline{
        public static final String WebSocketServerHandler = "WebSocketServerHandler";
        public static final String WebSocketFrameServerHandler = "WebSocketFrameServerHandler";

        private ChannelPipeline() {
        }
    }

    public static final class WheelTimer{
        public static final int tickDuration = 20;
        public static final TimeUnit timeUnit = TimeUnit.SECONDS;
        public static final int ticksPerWheel = 60;

        private WheelTimer() {
        }
    }
}