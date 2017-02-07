package com.wolf.shoot.common.constant;

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
    public static final Logger updateLogger = LoggerFactory.getLogger("updateLogger");
    /** 登录相关的日志 */
    public static final Logger loginLogger = LoggerFactory.getLogger("login");
    /** 玩家相关的日志 */
    public static final Logger playerLogger = LoggerFactory.getLogger("player");
    /** 数据库相关的日志 */
    public static final Logger dbLogger = LoggerFactory.getLogger("db");
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
    public static final Logger chearLogger = LoggerFactory.getLogger("cheat");
    /** error相关的日志 */
    public static final Logger errorLogger = LoggerFactory.getLogger("error");
    /**event相关的日志 */
    public static final Logger eventLogger = LoggerFactory.getLogger("event");
    /**util相关的日志 */
    public static final Logger utilLogger = LoggerFactory.getLogger("util");

    /**communication相关的日志 */
    public static final Logger communicationLogger = LoggerFactory.getLogger("communication");
}
