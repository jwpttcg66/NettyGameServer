package com.snowcattle.game.common.util;


public class ErrorsUtil {

	/**
	 *
	 * 构造一个错误消息,处理动作无
	 *
	 * @param reason
	 * @return
	 */
	@Deprecated
	public static String error(String reason) {
		return "Err:" + reason + ",Action:None";
	}

	/**
	 * 构造一个错误消息以及对错误采取的处理动作
	 *
	 * @return
	 */
	@Deprecated
	public static String error(String reason, String source) {
		return "Err:" + reason + ",Action:" + source;
	}

	/**
	 * 构造一个包括了错误原因,对错误采取的动作以及错误来源的错误信息
	 *
	 * @param reason
	 * @param action
	 * @param source
	 * @return
	 */
	@Deprecated
	public static String error(String reason, String action, Object source) {
		return "Err:" + reason + ",Action:" + action + ",Source:" + source;
	}

	/**
	 * 构造一个包包括了错误代码,错误来源以及调用者信息的错误描述
	 *
	 * @param errorCode
	 *            错误代码
	 * @param cause
	 *            错误原因
	 * @param callerDesc
	 *            调用者描述,用于标识调用者的信息
	 * @return
	 */
	@Deprecated
	public static String errorWithCaller(String errorCode, String cause, String callerDesc) {
		return new StringBuilder().append("Err:").append(errorCode).append(",Cause:").append(cause).append(",Caller:")
				.append(callerDesc).toString();
	}

	/**
	 * 构造一个标准格式的错误信息
	 *     [errorCode] [origin] [param]
	 * 例：[ITEM.ERR.NOEXIST] [#GS.ItemLogicalProcessor.onRepair] [bagId:1001,bagIndex:2]
	 *
	 * @author sd 2009-10-20
	 * @param errorCode
	 *             错误代码 @see {@link CommonErrorLogInfo}
	 * @param origin
	 *             错误产生地 #包缩写(GS,WS,LS,DBS,CORE,LOG).类名.方法名
	 * @param param
	 *             需要记录实时数据
	 * @return
	 */
	public static String error(String errorCode, String origin, String param) {
		StringBuilder _errorStr = new StringBuilder("[").append(errorCode).append("] [").append(origin).append("]");
		if(param != null && param.length() > 0) {
			_errorStr.append(" [").append(param).append("]");
		}
		return _errorStr.toString();
	}

}
