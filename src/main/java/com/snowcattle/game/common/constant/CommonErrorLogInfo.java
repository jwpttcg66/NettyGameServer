package com.snowcattle.game.common.constant;

/**
 * 定义core的错误代码
 *
 *
 *
 */
public class CommonErrorLogInfo {
	/* 消息包错误定义开始 */

    /** 错误的消息头长度 */
    public static final String PACKET_BAD_HEADER_LEN = "PAC.ERR.HEAD.LEN";

	/* 消息包错误定义结束 */

	/* 线程错误定义开始 */

    /** 线程被中断 */
    public static final String THRAD_ERR_INTERRUPTED = "THR.ERR.INTRRUPTED";
    /** 不在同一个线程中调用 */
    public static final String THREAD_NOT_SAME = "THR.ERR.NOT.SAME";

	/* 线程错误定义结束 */

	/* 消息处理错误定义开始 */

    /** 未知的消息 */
    public static final String MSG_UNKNOWN = "MSG.ERR.UNKNOWN";
    /** 消息处理时收到空的消息 */
    public static final String MSG_PRO_ERR_NULL_MSG = "MSG.PRO.ERR.NULL.MSG";
    /** 消息处理时发生未知的异常 */
    public static final String MSG_PRO_ERR_EXP = "MSG.PRO.ERR.EXP";
    /** 消息处理过程中,断掉Sender时失败 */
    public static final String MSG_PRO_ERR_DIS_SENDER_FAIL = "MSG.PRO.ERR.DIS.SENDER.FAIL";
    /** 读消息失败 */
    public static final String MSG_PRO_ERR_READ_FAIL = "MSG.PRO.ERR.READ.FAIL";
	/* 消息处理错误定义结束 */

	/* 数据库处理错误定义开始 */

    /** ibatis 连接失败 */
    public static final String DB_IBATIS_CONNECT_FAIL = "DB.IBATIS.CONNECT.FAIL";
    /** 数据库操作失败 */
    public static final String DB_OPERATE_FAIL = "DB.ERR.OPR";
    /** 没有正确设置ID*/
    public static final String DB_NO_ID  = "DB.ERR.NOID";
	/* 数据库处理错误定义结束 */

    /** 脚本执行失败 */
    public static final String SCRITP_EXECUTE_FAIL = "SCRIPT.ERR.EXE.FAIL";

    /** 文件IO异常 */
    public static final String FILE_IO_FAIL = "FILE.ERR.IO.FAILE";

	/* 参数相关的错误定义 */
    /** 期望的参数是正数 */
    public static final String ARG_POSITIVE_NUMBER_EXCEPT = "ARG.ERR.POSITIVE.NUM.EXCEPT";
    /** 期望的参数不是null */
    public static final String ARG_NOT_NULL_EXCEPT = "ARG.ERR.NOT.NULL.EXCEPT";
    /** 参数无效 */
    public static final String ARG_INVALID = "ARG.ERR.INVALID";


    /** 服务器初始化失败 */
    public static final String SERVER_INIT_FAIL = "SERVER.ERR.INIT.FAIL";

    /** Excel中有重复的配置 */
    public static final String CONFIG_DUP_FAIL = "CONFIG.ERR.DUP";

    /** Excel中有重复的配置 */
    public static final String CONFIG_INVALID = "CONFIG.ERR.INVALID";

    /** JsonUtils 处理数据失败 */
    public static final String JSON_ANALYZE_FAIL = "JSON.ERR.ANALYZE.FAIL";

    /** 无效的逻辑状态 */
    public static final String INVALID_STATE = "STATE.ERR.INVALID";



}
