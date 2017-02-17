package com.wolf.shoot.net.message;

import com.wolf.shoot.common.exception.CodecException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by jiangwenping on 17/2/3.
 * 需要重新读取body
 */
public abstract  class AbstractAbstractNetProtoBufMessage extends AbstractNetMessage {

    private NetProtoBufMessageBody netProtoBufMessageBody;

//    private long sessionId;

    public AbstractAbstractNetProtoBufMessage(){
        setNetMessageHead(new NetMessageHead());
        this.netProtoBufMessageBody = new NetProtoBufMessageBody();
    }

    public NetProtoBufMessageBody getNetProtoBufMessageBody() {
        return netProtoBufMessageBody;
    }

    public void setNetProtoBufMessageBody(NetProtoBufMessageBody netProtoBufMessageBody) {
        this.netProtoBufMessageBody = netProtoBufMessageBody;
    }

    //此方法需要
    public abstract void decoderNetProtoBufMessageBody() throws CodecException, Exception;

    public abstract void release() throws CodecException;

    public abstract  void encodeNetProtoBufMessageBody() throws CodecException, Exception;

    public void setCmd(int cmd){
        getNetMessageHead().setCmd((short)cmd);
    }
    public void setSerial(int serial){
        getNetMessageHead().setSerial(serial);
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + ": commandId=" + getNetMessageHead().getCmd();
    }

    public String toAllInfoString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE).replaceAll("\n", "");
    }

//    public long getSessionId() {
//        return sessionId;
//    }
//
//    public void setSessionId(long sessionId) {
//        this.sessionId = sessionId;
//    }
}
