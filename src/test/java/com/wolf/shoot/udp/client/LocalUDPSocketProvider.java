package com.wolf.shoot.udp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by jwp on 2017/1/20.
 */
public class LocalUDPSocketProvider {

    public static final Logger utilLogger = LoggerFactory.getLogger("util");
    private static String Tag = LocalUDPSocketProvider.class.getSimpleName();

    private static LocalUDPSocketProvider instance = null;
    private DatagramSocket datagramSocket = null;

    public static LocalUDPSocketProvider getInstance(){
        if(instance == null){
            instance = new LocalUDPSocketProvider();
        }
        return instance;
    }

    public void initConnect(){
        try{
            int port = 9090;
            this.datagramSocket = new DatagramSocket();
            this.datagramSocket.connect(InetAddress.getByName("127.0.0.1"), port);
            this.datagramSocket.setReuseAddress(true);
            utilLogger.debug(Tag, "new DatagramSocket() 创建成功");
        }catch (Exception e){
            utilLogger.debug(Tag, "localudpconnet 创建出错" + e.getMessage());
        }
    }

    public DatagramSocket getLocalUDPSocket(){
        return this.datagramSocket;
    }

}
