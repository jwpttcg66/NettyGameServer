NettyGameServer

使用netty4.X实现的手机游戏服务器，支持tcp，udp链接，采用protobuf自定义协议栈进行网络通信,支持rpc远程调用

tcp自定义协议栈
消息结构为NetMessageHead,NetProtoBufMessageBody
消息头NetMessageHead结构为
魔法头 short head;
长度 int length;
版本号 byte version;
命令 short cmd;
序列号 int serial;

消息体NetProtoBufMessageBody结构为
内容 bytes body;

udp自定义协议栈为
消息结构为NetUdpMessageHead,NetProtoBufMessageBody
消息头NetUdpMessageHead结构为
魔法头 short head;
长度 int length;
版本号 byte version;
命令 short cmd;
序列号 int serial;
玩家id long playerId;
玩家会话凭据 int tocken;

消息体NetProtoBufMessageBody结构为
内容 bytes body;


代码最后通过maven部署
- 作者qq 330258845
- QQ群310158485