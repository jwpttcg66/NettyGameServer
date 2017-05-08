package com.snowcattle.game.common.util;

/**
 * 关键词过滤接口
 *
 *
 */
public interface IKeyWordsFilter {

	/**
	 * 使用关键字列表进行初始化
	 *
	 * @param keyWords
	 *            关键字列表
	 * @return 是否初始化成功，成功true，否则false
	 */
	public abstract boolean initialize(String[] keyWords);

	/**
	 * 过滤关键词
	 *
	 * @param s
	 *            要被处理的字符串
	 * @return 处理完毕的被过滤的字符串
	 */
	public abstract String filt(String s);

	/**
	 * 检测输入的消息是否有关键字
	 *
	 * @param inputMsg
	 *            要检测的消息
	 * @return 若有返回true，否则false
	 */
	public abstract boolean contain(String inputMsg);

}