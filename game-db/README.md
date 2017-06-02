# GameShardingDb
基于spring-sharding-mybaits集成redis缓存的游戏分布式存储框架.支持将对象序列化到队列里，异步存储。 使用spring集成mybatis3垂直和水平分库mysql.使用模版编程，采用代理模式,采集变化的字段，自动完成拼写sql,降低数据库落地难度
集成Mybatis-PageHelper分页,大数据量可以分批查询，提升查询速度

将同步存储底层落地抽象为EntityService, 具体存储服务可继承EntityService

### [使用手册](https://github.com/jwpttcg66/GameShardingDb/wiki)

### 参考demo为test下jdbc

#### 贡献源码&合作&交流

- 作者qq 330258845
- QQ群:310158485


