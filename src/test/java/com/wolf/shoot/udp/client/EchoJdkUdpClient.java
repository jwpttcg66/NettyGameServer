package com.wolf.shoot.udp.client;

import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by jiangwenping on 17/1/22.
 */
public class EchoJdkUdpClient {
    public static final Logger utilLogger = LoggerFactory.getLogger("util");
    public static void main(String[] args) throws  Exception{
        final String data = "博主邮箱:zou90512@126.com";
        byte[] bytes = data.getBytes(Charset.forName("UTF-8"));
        InetSocketAddress targetHost = new InetSocketAddress("127.0.0.1", 9999);

        // 发送udp内容
        DatagramSocket socket = new DatagramSocket();
        socket.send(new DatagramPacket(bytes, 0, bytes.length, targetHost));

        while (true) {
            //接收数据报的包
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
//            DatagramSocket localUDPSocket = LocalUDPSocketProvider.getInstance().getLocalUDPSocket();
//            if (localUDPSocket == null || (localUDPSocket.isClosed())) {
//                continue;
//            }

            socket.receive(packet);
            //解析发来的数据
            String response = new String(packet.getData(), 0, packet.getLength(), CharsetUtil.UTF_8);
            utilLogger.debug("收到服务器信息" + response);
        }
    }
}
