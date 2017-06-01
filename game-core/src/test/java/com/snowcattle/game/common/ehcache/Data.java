package com.snowcattle.game.common.ehcache;//package com.snowcattle.game.common.ehcache;
//
//import java.io.Serializable;
//import java.util.concurrent.TimeUnit;
//
//import org.ehcache.ValueSupplier;
//import org.ehcache.expiry.Duration;
//import org.ehcache.expiry.Expiry;
//
//public class Data implements Serializable,Expiry<String, Serializable>{
//
//	private String key;
//	private Integer time;
//	public String getKey() {
//		return key;
//	}
//	public void setKey(String key) {
//		this.key = key;
//	}
//	public Integer getTime() {
//		return time;
//	}
//	public void setTime(Integer time) {
//		this.time = time;
//	}
//
//	/**定义object的失效时间*/
//	@Override
//	public Duration getExpiryForCreation(String key, Serializable value) {
//		// TODO Auto-generated method stub
//		return new Duration(1, TimeUnit.MINUTES);
//	}
//	@Override
//	public Duration getExpiryForAccess(String key,
//			ValueSupplier<? extends Serializable> value) {
//		// TODO Auto-generated method stub
////		return new Duration(1, TimeUnit.MINUTES);
//		return null;
//	}
//	@Override
//	public Duration getExpiryForUpdate(String key,
//			ValueSupplier<? extends Serializable> oldValue,
//			Serializable newValue) {
//		// TODO Auto-generated method stub
////		return new Duration(1, TimeUnit.MINUTES);
//		return null;
//	}
//
//}
