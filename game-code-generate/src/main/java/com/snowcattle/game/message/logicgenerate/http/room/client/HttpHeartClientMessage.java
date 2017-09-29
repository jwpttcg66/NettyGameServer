package com.snowcattle.game.message.logicgenerate.http.room.client;

import com.snowcattle.game.common.annotation.MessageCommandAnnotation;
import com.snowcattle.game.service.message.AbstractNetProtoBufHttpMessage;
import com.snowcattle.game.service.net.GameMessageCommandIndex;
import com.snowcattle.game.message.protogenerate.http.room.client.GameRoomHttpClientProBuf.*;

/**
* room tcp 心跳协议
*
* @author CodeGenerator, don't modify this file please.
*/
@MessageCommandAnnotation(command = GameMessageCommandIndex.HTTP_HEART_CLIENT_MESSAGE)
public class HttpHeartClientMessage extends AbstractNetProtoBufHttpMessage {

/**请求*/
        private RoomHeartHttpClientProBuf req;
    

@Override
public void decoderNetProtoBufMessageBody() throws Exception {
byte[] bytes = getNetMessageBody().getBytes();
    RoomHeartHttpClientProBuf req = RoomHeartHttpClientProBuf.parseFrom(bytes);
this.req=req;
}


//以下为客户端专用代码======================================================
@Override
public void encodeNetProtoBufMessageBody() throws Exception {
byte[] bytes = req.toByteArray();
getNetMessageBody().setBytes(bytes);
}

@Override
public void release() {

}
            public void setReq(RoomHeartHttpClientProBuf req){
    this.req = req;
    }
    public RoomHeartHttpClientProBuf getReq(){
    return this.req;
    }
    }
