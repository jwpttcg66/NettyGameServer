package com.snowcattle.game.service.message;

/**
 * Created by jiangwenping on 2017/9/28.
 */
public abstract class AbstractNetProtoBufHttpMessage extends AbstractNetProtoBufMessage{

    public AbstractNetProtoBufHttpMessage(){
        super();
        setNetMessageHead(new NetHttpMessageHead());
        setNetMessageBody(new NetProtoBufMessageBody());
        initHeadCmd();
    }

}
