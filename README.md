NettyGameServer

- 使用netty4.X实现的手机游戏服务器，支持tcp，udp链接，采用protobuf自定义协议栈进行网络通信,支持rpc远程调用,使用mybatis3支持db存储分库分表，支持异步mysql存储，db保存时同步更新reids缓存。
- 使用ExcelToCode工程，将excel数据生成java类和json数据字典，DictService直接读取json，减少数据字典部分代码。
- 使用game-executor工程，增加游戏内的异步事件全局服务, 支持事件sharding,均衡的异步执行事件逻辑
- 使用netty的proxy模式，增加网关代理转发
## [Wiki/文档](https://github.com/jwpttcg66/NettyGameServer/wiki)

#### 贡献源码&合作&交流

- 作者qq 330258845
- QQ群:310158485
