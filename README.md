NettyGameServer

- 使用netty4.X实现的手机游戏服务器,支持tcp,udp,http,websocket链接,采用protobuf自定义协议栈进行网络通信,支持rpc远程调用,使用mybatis3支持db存储分库分表,支持异步mysql存储,db保存时同步更新reids缓存。
- 使用ExcelToCode工程，将excel数据生成java类和json数据字典,DictService直接读取json,减少数据字典部分代码。
- 使用game-executor工程,增加游戏内的异步事件全局服务,支持事件sharding,均衡的异步执行事件逻辑
- 使用netty的proxy模式，增加网关代理转发
## [Wiki/文档](https://github.com/jwpttcg66/NettyGameServer/wiki)

## 源码工具链接
- [ExcelToCode 数据字典生成](https://github.com/youlanhai/ExcelToCode)
- [GameShardingDb 游戏数据库](https://github.com/jwpttcg66/GameShardingDb)
- [GameThreadPool 游戏常用线程池](https://github.com/jwpttcg66/GameThreadPool)
- [game-executor 游戏常用循环调度执行器](https://github.com/jwpttcg66/game-executor)
- [GameCodeGenerate 工具(包含新版本数据字典生成)](https://github.com/jwpttcg66/GameCodeGenerate)
- [redis-game-transaction 事务](https://github.com/jwpttcg66/redis-game-transaction)

#### 贡献源码&合作&交流

- 作者qq 330258845
- QQ群:310158485(已满)  513178622

![avatar](https://github.com/jwpttcg66/NettyGameServer/blob/master/qrcode.png)
