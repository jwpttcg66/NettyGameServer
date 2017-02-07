package com.wolf.shoot.common.util;
/**
 * 根据平台规则生成订单号的生成器
 * <p>
 * 规则是: 13位时间戳 +　6位随机整数
 * </p>
 * @author crazyjohn
 *
 */
public class LocalRuleOrderIdGenerator implements IOrderIdGenerator {
	/** 6位数最大值 */
	private static final int RAND_END = 999999;
	/** 6位数最小值 */
	private static final int RAND_BEGIN = 100000;
	private static final int LOCAL_WANT_SIZE = 19;

	@Override
	public long nextOrderId() {
		StringBuilder orderSB = new StringBuilder();
		orderSB.append(System.currentTimeMillis());
		orderSB.append(RandomUtil.nextInt(RAND_BEGIN, RAND_END));
		long orderId = Long.parseLong(orderSB.toString());
		if (!check(orderId)) {
			throw new IllegalStateException("LocalRuleOrderIdGenerator generate illegal orderId: " + orderId);
		}
		return orderId;
	}
	@Override
	public boolean check(long orderId) {
		if (String.valueOf(orderId).length() == LOCAL_WANT_SIZE) {
			return true;
		}
		return false;
	}


}
