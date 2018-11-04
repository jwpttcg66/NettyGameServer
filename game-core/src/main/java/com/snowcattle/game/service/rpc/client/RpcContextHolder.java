package com.snowcattle.game.service.rpc.client;

/**
 * Created by jiangwenping on 17/3/13.
 * rpc 服务器列表选择
 */
public final class RpcContextHolder {

    private static  final ThreadLocal<RpcContextHolderObject> contextHolder = new ThreadLocal<RpcContextHolderObject>();

    private RpcContextHolder() {
    }

    public  static RpcContextHolderObject getContext() {
        return contextHolder.get();
    }
    /**
     * 通过字符串选择数据源
     * @param rpcContextHolderObject
     */
    public static void setContextHolder(RpcContextHolderObject rpcContextHolderObject) {
        contextHolder.set(rpcContextHolderObject);
    }
}
