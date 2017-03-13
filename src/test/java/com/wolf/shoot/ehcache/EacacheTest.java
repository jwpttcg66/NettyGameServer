package com.wolf.shoot.ehcache;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.ValueSupplier;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.internal.util.ValueSuppliers;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.expiry.Expiry;
import org.ehcache.xml.XmlConfiguration;


public class EacacheTest {

	
	@org.junit.Test
	public void Test() throws InterruptedException {
		final URL myUrl = this.getClass().getResource("/ehcache_test.xml"); 
		Configuration conf = new XmlConfiguration(myUrl); 
		CacheManager manager = CacheManagerBuilder.newCacheManager(conf);
		manager.init();
		Cache<String,Integer> cache = manager.getCache("myTest", String.class,Integer.class);
		cache.put("1", 100);
		System.err.println(cache.get("1"));
		/*Duration d = new Duration(1000l, TimeUnit.SECONDS);
		Expirations.timeToLiveExpiration(d);
		CacheConfiguration<String,Integer> cacheConfiguration = CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Integer.class,ResourcePoolsBuilder.heap(100))
				.withExpiry(Expirations.timeToLiveExpiration(Duration.of(2000l, TimeUnit.SECONDS)))
				.build();
		Duration creation = cacheConfiguration.getExpiry().getExpiryForCreation("1", 1000);
		System.err.println(creation.getLength());*/
		Thread.sleep(1000l);
		boolean flag = true;
		while(flag){
			try {
				Integer val = cache.get("1");
				if(val == null){
					System.err.println("has expire.....");
					flag = false;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
		}
		
	}
}
