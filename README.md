NettyGameServer

使用netty4.X实现的手机游戏服务器，支持socket，udp链接，采用protobuf自定义协议栈进行网络通信
代码最后通过maven部署

socket自定义协议栈
消息结构为NetMessageHead,NetProtoBufMessageBody
消息头NetMessageHead结构为
魔法头 short head;
长度 int length;
版本号 byte version;
命令 short cmd;
序列号 int serial;

消息体NetProtoBufMessageBody结构为
内容 bytes body;

- 作者qq 330258845
- QQ群310158485