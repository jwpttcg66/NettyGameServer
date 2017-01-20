package com.wolf.shoot.udp.client;

/**
 * Created by jwp on 2017/1/20.
 */
public class EchoClient {
    public static void main(String[] args) throws Exception{
        LocalUDPSocketProvider.getInstance().initConnect();
    }
}
