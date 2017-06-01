package com.snowcattle.game.service.rpc.client;

/**
 * Created by jiangwenping on 17/3/13.
 * rpc 服务器列表选择
 */
public class RpcContextHolder {

    private static  final ThreadLocal<RpcContextHolderObject> contextHolder = new ThreadLocal<RpcContextHolderObject>();

    public  static RpcContextHolderObject getContext() {
        return (RpcContextHolderObject) contextHolder.get();
    }
    /**
     * 通过字符串选择数据源
     * @param customerType
     */
    public static void setContextHolder(RpcContextHolderObject rpcContextHolderObject) {
        contextHolder.set(rpcContextHolderObject);
    }
}
