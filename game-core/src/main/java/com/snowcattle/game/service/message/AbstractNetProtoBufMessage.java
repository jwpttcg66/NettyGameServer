package com.snowcattle.game.service.message;

import com.snowcattle.game.common.annotation.MessageCommandAnnotation;
import com.snowcattle.game.common.exception.CodecException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by jiangwenping on 17/2/3.
 * 需要重新读取body
 */
public abstract  class AbstractNetProtoBufMessage extends AbstractNetMessage {

    public AbstractNetProtoBufMessage(){
        setNetMessageHead(new NetMessageHead());
        setNetMessageBody(new NetMessageBody());
    }

    protected void initHeadCmd(){
        //设置包头
        MessageCommandAnnotation messageCommandAnnotation = this.getClass().getAnnotation(MessageCommandAnnotation.class);
        if(messageCommandAnnotation != null){
            getNetMessageHead().setCmd((short) messageCommandAnnotation.command());
        }
    }
    /*解析protobuf协议*/
    public abstract void decoderNetProtoBufMessageBody() throws CodecException, Exception;

    /*释放message的body*/
    public  void releaseMessageBody() throws CodecException, Exception{
        getNetMessageBody().setBytes(null);
    }

    public abstract void release() throws CodecException;

    public abstract  void encodeNetProtoBufMessageBody() throws CodecException, Exception;

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

}
