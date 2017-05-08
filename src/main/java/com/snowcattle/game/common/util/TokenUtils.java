package com.snowcattle.game.common.util;

public class TokenUtils {

	private static DesEncrypter encrypter = new DesEncrypter("@$8hLzrO");

	// 初始化
	public static void init(){
		//
	}

	// 根据输入的帐号ID和时间戳，加密生成Token
	public static String encodeToken(long accountId, long timestamp){
		return encrypter.encrypt(accountId+","+timestamp);
	}

	// 根据Token结出account和时间戳
	public static String decodeToken(String token){
		return encrypter.decrypt(token);
	}

	// 根据ACTI(accountId, charId, timestamp, ip)来生成token
	public static String encodeToken(long passportId, int charId, long timestamp, String ip){
		return encrypter.encrypt(passportId+","+charId+","+timestamp+","+ip);
	}
}
