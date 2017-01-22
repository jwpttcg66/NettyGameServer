package com.wolf.shoot.udp.client;

import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by jwp on 2017/1/20.
 */
public class LocalUDPDataReciever {

    public static final Logger utilLogger = LoggerFactory.getLogger("util");
    private static  final String TAG = LocalUDPDataReciever.class.getSimpleName();
    private static LocalUDPDataReciever instance = null;
    private Thread thread = null;

    public static LocalUDPDataReciever getInstance(){
        if(instance == null){
            instance = new LocalUDPDataReciever();
        }
        return instance;
    }

    public void startup(){
        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int port = 9999;
                    utilLogger.debug(LocalUDPDataReciever.TAG, "本地udp端口侦听， 端口=" + port);
                    //开始侦听
                    LocalUDPDataReciever.getInstance().upListeningImpl();
                }catch (Exception e){
                    utilLogger.error(LocalUDPDataReciever.TAG, "本地udp端口停止"+e.getMessage());
                }

            }
        });
        this.thread.start();
    }

    private void upListeningImpl() throws Exception{
        while (true) {
            byte[] data = new byte[1024];
            //接收数据报的包
            DatagramPacket packet = new DatagramPacket(data, data.length);
            DatagramSocket localUDPSocket = LocalUDPSocketProvider.getInstance().getLocalUDPSocket();
            if (localUDPSocket == null || (localUDPSocket.isClosed())) {
                continue;
            }

            localUDPSocket.receive(packet);
            //解析发来的数据
            String response = new String(packet.getData(), 0, packet.getLength(), CharsetUtil.UTF_8);
            utilLogger.debug(LocalUDPDataReciever.TAG, "收到服务器信息" + response);
        }
    }
}
