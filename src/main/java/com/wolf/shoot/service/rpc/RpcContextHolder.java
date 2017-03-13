package com.wolf.shoot.service.rpc;

/**
 * Created by jiangwenping on 17/3/13.
 * rpc 服务器列表选择
 */
public class RpcContextHolder {

    private static  final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    public  static String getServer() {
        return (String) contextHolder.get();
    }
    /**
     * 通过字符串选择数据源
     * @param customerType
     */
    public static void setServer(String serverId) {
        contextHolder.set(serverId);
    }
}
