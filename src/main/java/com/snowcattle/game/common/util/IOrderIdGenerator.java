package com.snowcattle.game.common.util;

/**
 * 订单号生成接口
 *
 * @author crazyjohn
 *
 */
public interface IOrderIdGenerator {

	/**
	 * 获取下一个订单号
	 *
	 * @throws IllegalStateException
	 *             当生成的订单非法的时候抛出此异常
	 * @return 返回下一个订单号
	 */
	public long nextOrderId();

	/**
	 * 检查生成的订单号是否合法
	 *
	 * @return 合法返回true;否则返回false;
	 */
	public boolean check(long orderId);
}
