//package com.snowcattle.game.message.logicgenerate.tcp.room.client;
//
//import com.snowcattle.game.common.annotation.MessageCommandAnnotation;
//import com.snowcattle.game.service.message.AbstractNetProtoBufTcpMessage;
//import com.snowcattle.game.wolfshoot.service.net.GameMessageCommandIndex;
//import com.snowcattle.game.message.protogenerate.tcp.room.client.GameRoomTCPClientProBuf.*;
//
///**
// * room tcp 心跳协议
// *
// * @author CodeGenerator, don't modify this file please.
// */
//@MessageCommandAnnotation(command = GameMessageCommandIndex.ROOM_HEART_CLIENT_TCP_MESSAGE)
//public class RoomHeartClientMessage extends AbstractNetProtoBufTcpMessage {
//
//	/**请求*/
//	private RoomHeartTCPClientProBuf req;
//
//
//	@Override
//	public void decoderNetProtoBufMessageBody() throws Exception {
//		byte[] bytes = getNetMessageBody().getBytes();
//		RoomHeartTCPClientProBuf req = RoomHeartTCPClientProBuf.parseFrom(bytes);
//		this.req=req;
//	}
//
//
//	//以下为客户端专用代码======================================================
//	@Override
//	public void encodeNetProtoBufMessageBody() throws Exception {
//		byte[] bytes = req.toByteArray();
//		getNetMessageBody().setBytes(bytes);
//	}
//
//	@Override
//	public void release() {
//
//	}
//	public void setReq(RoomHeartTCPClientProBuf req){
//		this.req = req;
//	}
//	public RoomHeartTCPClientProBuf getReq(){
//		return this.req;
//	}
//}
