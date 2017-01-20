package com.wolf.shoot.udp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;

/**
 * Created by jwp on 2017/1/20.
 */
public class EchoClient {
    public static final Logger utilLogger = LoggerFactory.getLogger("util");
    public static void main(String[] args) throws Exception{

        // 初始化本地UDP的Socket
        LocalUDPSocketProvider.getInstance().initConnect();
        // 启动本地UDP监听（接收数据用的）
        LocalUDPDataReciever.getInstance().startup();


        // 循环发送数据给服务端
        while(true)
        {
            // 要发送的数据
            String toServer = "Hi，我是客户端，我的时间戳"+System.currentTimeMillis();
            byte[] toServerBytes = toServer.getBytes("UTF-8");

            // 开始发送
//            boolean ok = UDPUtils.send(soServerBytes, soServerBytes.length);
            DatagramPacket send_packet = new DatagramPacket(toServerBytes, toServerBytes.length);
            LocalUDPSocketProvider.getInstance().getLocalUDPSocket().send(send_packet);
            utilLogger.debug("EchoClient", "发往服务端的信息没有成功发出！！！");

            // 3000秒后进入下一次循环
            Thread.sleep(3000);
        }
    }
}
