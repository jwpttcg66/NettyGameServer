package com.snowcattle.game.service.message;

import com.google.protobuf.AbstractMessage;

/**
 * Created by jwp on 2017/1/26.
 *  protobuf的messagebody实体
 */
public class NetProtoBufMessageBody extends NetMessageBody {

    //将字节读取为protobuf的抽象对象
    private AbstractMessage abstractMessage;

    public AbstractMessage getAbstractMessage() {
        return abstractMessage;
    }

    public void setAbstractMessage(AbstractMessage abstractMessage) {
        this.abstractMessage = abstractMessage;
    }

}
