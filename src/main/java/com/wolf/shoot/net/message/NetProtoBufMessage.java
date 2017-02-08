package com.wolf.shoot.net.message;

import com.wolf.shoot.common.exception.CodecException;

/**
 * Created by jiangwenping on 17/2/3.
 * 需要重新读取body
 */
public abstract  class NetProtoBufMessage {

    private NetMessageHead netMessageHead;
    private NetProtoBufMessageBody netMessageBody;

    public NetProtoBufMessage(){
        this.netMessageHead = new NetMessageHead();
        this.netMessageBody = new NetProtoBufMessageBody();
    }
    public NetMessageHead getNetMessageHead() {
        return netMessageHead;
    }

    public void setNetMessageHead(NetMessageHead netMessageHead) {
        this.netMessageHead = netMessageHead;
    }

    public NetProtoBufMessageBody getNetMessageBody() {
        return netMessageBody;
    }

    public void setNetMessageBody(NetProtoBufMessageBody netMessageBody) {
        this.netMessageBody = netMessageBody;
    }

    //此方法需要
    public abstract void decoderNetProtoBufMessageBody() throws CodecException, Exception;

    public abstract void release() throws CodecException;

    public abstract  void encodeNetProtoBufMessageBody() throws CodecException, Exception;

    public void setCmd(short cmd){
        getNetMessageHead().setCmd(cmd);
    }
}
