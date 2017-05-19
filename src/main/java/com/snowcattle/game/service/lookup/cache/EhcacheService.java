package com.snowcattle.game.service.lookup.cache;

import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.service.IService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by jwp on 2017/3/9.
 * cache元素的属性：

 name：缓存名称

 maxElementsInMemory：内存中最大缓存对象数

 maxElementsOnDisk：硬盘中最大缓存对象数，若是0表示无穷大

 eternal：true表示对象永不过期，此时会忽略timeToIdleSeconds和timeToLiveSeconds属性，默认为false

 overflowToDisk：true表示当内存缓存的对象数目达到了

 maxElementsInMemory界限后，会把溢出的对象写到硬盘缓存中。注意：如果缓存的对象要写入到硬盘中的话，则该对象必须实现了Serializable接口才行。

 diskSpoolBufferSizeMB：磁盘缓存区大小，默认为30MB。每个Cache都应该有自己的一个缓存区。

 diskPersistent：是否缓存虚拟机重启期数据，是否持久化磁盘缓存,当这个属性的值为true时,系统在初始化时会在磁盘中查找文件名 为cache名称,后缀名为index的文件，这个文件中存放了已经持久化在磁盘中的cache的index,找到后会把cache加载到内存，要想把 cache真正持久化到磁盘,写程序时注意执行net.sf.ehcache.Cache.put(Element element)后要调用flush()方法。

 diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认为120秒

 timeToIdleSeconds： 设定允许对象处于空闲状态的最长时间，以秒为单位。当对象自从最近一次被访问后，如果处于空闲状态的时间超过了timeToIdleSeconds属性 值，这个对象就会过期，EHCache将把它从缓存中清空。只有当eternal属性为false，该属性才有效。如果该属性值为0，则表示对象可以无限 期地处于空闲状态

 timeToLiveSeconds：设定对象允许存在于缓存中的最长时间，以秒为单位。当对象自从被存放到缓存中后，如果处于缓存中的时间超过了 timeToLiveSeconds属性值，这个对象就会过期，EHCache将把它从缓存中清除。只有当eternal属性为false，该属性才有 效。如果该属性值为0，则表示对象可以无限期地存在于缓存中。timeToLiveSeconds必须大于timeToIdleSeconds属性，才有 意义

 memoryStoreEvictionPolicy：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。可选策略有：LRU（最近最少使用，默认策略）、FIFO（先进先出）、LFU（最少访问次数）。


 maxElementsInMemory="10000" 	//Cache中最多允许保存的数据对象的数量
 external="false" 				//缓存中对象是否为永久的，如果是，超时设置将被忽略，对象从不过期
 timeToLiveSeconds="3600"  		//缓存的存活时间，从开始创建的时间算起
 timeToIdleSeconds="3600"  		//多长时间不访问该缓存，那么ehcache 就会清除该缓存
 这两个参数很容易误解，看文档根本没用，我仔细分析了ehcache的代码。结论如下：
 1、timeToLiveSeconds的定义是：以创建时间为基准开始计算的超时时长；
 2、timeToIdleSeconds的定义是：在创建时间和最近访问时间中取出离现在最近的时间作为基准计算的超时时长；
 3、如果仅设置了timeToLiveSeconds，则该对象的超时时间=创建时间+timeToLiveSeconds，假设为A；
 4、如果没设置timeToLiveSeconds，则该对象的超时时间=min(创建时间，最近访问时间)+timeToIdleSeconds，假设为B；
 5、如果两者都设置了，则取出A、B最少的值，即min(A,B)，表示只要有一个超时成立即算超时。

 overflowToDisk="true"    		//内存不足时，是否启用磁盘缓存

 diskSpoolBufferSizeMB	//设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区
 maxElementsOnDisk		//硬盘最大缓存个数
 diskPersistent			//是否缓存虚拟机重启期数据The default value is false

 diskExpiryThreadIntervalSeconds	//磁盘失效线程运行时间间隔，默认是120秒。

 memoryStoreEvictionPolicy="LRU" //当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。
 clearOnFlush	//内存数量最大时是否清除

 maxEntriesLocalHeap="0"
 maxEntriesLocalDisk="1000"

 */
@Service
public class EhcacheService implements IService {

    private Logger logger = Loggers.serverLogger;
    @Override
    public String getId() {
        return ServiceName.EhcacheService;
    }

    @Override
    public void startup() throws Exception {
//        DefaultClassLoader defaultClassLoader = LocalMananger.getInstance().getLocalSpringServiceManager().getDefaultClassLoader();
//        Configuration xmlConfig = new XmlConfiguration(defaultClassLoader.getClass().getResource("/ehcache.xml"));
//        CacheManager cacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
//
//        try {
//            cacheManager.init();
//
////            Cache<String, String> basicCache = cacheManager.getCache("stringCache", String.class, String.class);
////
////            basicCache.put("dd", "kk");
////            System.out.println(basicCache.get("dd"));
//        }catch (Exception e){
//            logger.error(e.toString(), e);
//        }

    }


    @Override
    public void shutdown() throws Exception {

    }
}
