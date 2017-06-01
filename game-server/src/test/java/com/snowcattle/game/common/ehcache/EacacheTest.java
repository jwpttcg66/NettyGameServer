package com.snowcattle.game.common.ehcache;//package com.snowcattle.game.common.ehcache;
//
//import java.io.Serializable;
//import java.net.URL;
//import java.util.Iterator;
//
//import org.ehcache.Cache;
//import org.ehcache.Cache.Entry;
//import org.ehcache.CacheManager;
//import org.ehcache.config.Configuration;
//import org.ehcache.config.builders.CacheManagerBuilder;
//import org.ehcache.xml.XmlConfiguration;
//import org.junit.Before;
//
//
//public class EacacheTest {
//
//	CacheManager manager = null;
//
//	public void init(){
//		final URL myUrl = this.getClass().getResource("/ehcache_test.xml");
//		Configuration conf = new XmlConfiguration(myUrl);
//		manager = CacheManagerBuilder.newCacheManager(conf);
//		manager.init();
//	}
//	/**
//	 * key(String)-value(Object)
//	 * */
//	public void testSeriazable(){
////		Cache<String,Serializable> cache = manager.getCache("serializerTest", String.class,Serializable.class);
////		for (int i = 0; i < 300; i++) {
////			Data data = new Data();
////			cache.put(""+i, data);
////		}
////		Iterator<Entry<String, Serializable>> iterator = cache.iterator();
////		while (iterator.hasNext()) {
////			Entry<String, Serializable> next = iterator.next();
////			System.err.println(next.getKey()+"-"+next.getValue());
////		}
////		cache.put("hello", new Data());
////		System.err.println(cache.get("hello"));
////		check(cache, "hello");
//	}
//	/**
//	 * key(String)-value(String)
//	 * */
//	public void test() throws InterruptedException {
////		Cache<String,Integer> cache = manager.getCache("myTest", String.class,Integer.class);
////		cache.put("1", 100);
////		System.err.println(cache.get("1"));
////		check(cache, "1");
//	}
//
//	public void check(Cache cache,String key){
//		boolean flag = true;
//		while(flag){
//			try {
//				if(cache.get(key) == null){
//					System.err.println("has expire.....");
//					flag = false;
//				}
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//		}
//	}
//}
