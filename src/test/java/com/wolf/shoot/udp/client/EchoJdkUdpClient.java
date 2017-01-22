package com.wolf.shoot.udp.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by jiangwenping on 17/1/22.
 */
public class EchoJdkUdpClient {
    public static void main(String[] args) throws  Exception{
        final String data = "博主邮箱:zou90512@126.com";
        byte[] bytes = data.getBytes(Charset.forName("UTF-8"));
        InetSocketAddress targetHost = new InetSocketAddress("127.0.0.1", 9999);

        // 发送udp内容
        DatagramSocket socket = new DatagramSocket();
        socket.send(new DatagramPacket(bytes, 0, bytes.length, targetHost));
    }
}
