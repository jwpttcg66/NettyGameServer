package com.snowcattle.game.service.rpc.server;

/**
 * Created by jwp on 2017/3/8.
 */
public class RpcSystemConfig {
    public static final String SystemPropertyThreadPoolRejectedPolicyAttr = "com.newlandframework.rpc.parallel.rejected.policy";
    public static final String SystemPropertyThreadPoolQueueNameAttr = "com.newlandframework.rpc.parallel.queue";
    public static final int PARALLEL = Math.max(2, Runtime.getRuntime().availableProcessors());

    private static boolean monitorServerSupport = false;

    public static boolean isMonitorServerSupport() {
        return monitorServerSupport;
    }

    public static void setMonitorServerSupport(boolean jmxSupport) {
        monitorServerSupport = jmxSupport;
    }
}
