package com.snowcattle.game.common.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 统一定义系统使用的slf4j的Logger
 *
 *
 */
public class Loggers {
    /** Server相关的日志 */
    public static final Logger serverLogger = LoggerFactory.getLogger("server");
    /** Game Server相关的日志 */
    public static final Logger gameLogger = LoggerFactory.getLogger("game");
    /** Game Server相关的日志 */
    public static final Logger handlerLogger = LoggerFactory.getLogger("handler");

    /** 登录相关的日志 */
    public static final Logger loginLogger = LoggerFactory.getLogger("login");
    /** 玩家相关的日志 */
    public static final Logger playerLogger = LoggerFactory.getLogger("player");
    /** 任务调度相关的日志 */
    public static final Logger scheduleLogger = LoggerFactory.getLogger("shcedule");
    /** 异步操作相关的日志 */
    public static final Logger asyncLogger = LoggerFactory.getLogger("async");
    /** 消息处理相关的日志 */
    public static final Logger msgLogger = LoggerFactory.getLogger("msg");

    /** 服务器状态统计 */
    public static final Logger serverStatusStatistics = LoggerFactory.getLogger("statistics");
    /** timeEventTask定时任务*/
    public static final Logger timeEventTaskLogger = LoggerFactory.getLogger("timeEventTask");
    /** logserver */
    public static final Logger logServerServiceLogger = LoggerFactory.getLogger("logserver");

    /** session相关的日志 */
    public static final Logger sessionLogger = LoggerFactory.getLogger("session");
    /** admin相关的日志 */
    public static final Logger adminLogger = LoggerFactory.getLogger("admin");
    /** cheat相关的日志 */
    public static final Logger cheatLogger = LoggerFactory.getLogger("cheat");
    /** error相关的日志 */
    public static final Logger errorLogger = LoggerFactory.getLogger("error");
    /**util相关的日志 */
    public static final Logger utilLogger = LoggerFactory.getLogger("util");

    /**rpc相关的日志 */
    public static final Logger rpcLogger = LoggerFactory.getLogger("rpc");

    /**thread相关的日志 */
    public static final Logger threadLogger = LoggerFactory.getLogger("thread");

    /**TimeMonitor相关的日志 */
    public static final Logger timeMonitorLogger = LoggerFactory.getLogger("timeMonitorLogger");
}
