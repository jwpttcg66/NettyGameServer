package com.snowcattle.game.service.message;

/**
 * Created by jwp on 2017/1/24.
 *  网络消息头
 *  魔法头short+版本号byte+长度int+协议命令号short+唯一序列号
 */
public class NetMessageHead {

    public static final short MESSAGE_HEADER_FLAG = 0x2425;

    /**
     * 魔法头
     */
    private short head;
    /**
     * 版本号
     */
    private byte version;
    /**
     * 长度
     */
    private int length;
    /**
     * 命令
     */
    private short cmd;
    /**
     * 序列号
     */
    private int serial;


    public NetMessageHead(){
        this.head = MESSAGE_HEADER_FLAG;
    }

    public short getHead() {
        return head;
    }

    public void setHead(short head) {
        this.head = head;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

}
