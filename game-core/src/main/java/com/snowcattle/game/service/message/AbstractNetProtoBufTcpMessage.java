package com.snowcattle.game.service.message;

/**
 * Created by jwp on 2017/2/17.
 */
public abstract class AbstractNetProtoBufTcpMessage extends AbstractNetProtoBufMessage{

    public AbstractNetProtoBufTcpMessage(){
        super();
        setNetMessageBody(new NetProtoBufMessageBody());
        initHeadCmd();

    }

}
