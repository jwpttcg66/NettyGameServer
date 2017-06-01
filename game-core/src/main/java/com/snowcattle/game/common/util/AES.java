package com.snowcattle.game.common.util;



import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;


/**
 * This program generates a AES key, retrieves its raw bytes, and then
 * reinstantiates a AES key from the key bytes. The reinstantiated key is used
 * to initialize a AES cipher for encryption and decryption.
 */
public class AES {

	private static final String AES = "AES";
	private static final String CRYPT_KEY = "hsylgwk-20120101";

	private static Logger logger = Logger.getLogger(AES.class);

	/**
	 * 加密
	 * 
	 * @param encryptStr
	 * @return
	 */
	public static byte[] encrypt(byte[] src, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(AES);
		SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), AES);
		cipher.init(Cipher.ENCRYPT_MODE, securekey);// 设置密钥和加密形式
		return cipher.doFinal(src);
	}

	/**
	 * 解密
	 * 
	 * @param decryptStr
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(AES);
		SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), AES);// 设置加密Key
		cipher.init(Cipher.DECRYPT_MODE, securekey);// 设置密钥和解密形式
		return cipher.doFinal(src);
	}

	/**
	 * 二行制转十六进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	/**
	 * 解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public final static String decrypt(String data) {
		return decrypt(data, CRYPT_KEY);
	}

	public final static String decrypt(String data, String pass) {
		if (data == null||data.isEmpty()) {
			return "";
		}
		try {
			return new String(decrypt(hex2byte(data.getBytes()), pass));
		} catch (Exception e) {
			logger.error("AES-ENCRYPT:" + data, e);
		}
		return data;
	}

	/**
	 * 加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public final static String encrypt(String data) {
		return encrypt(data, CRYPT_KEY);
	}

	public final static String encrypt(String data, String pass) {
		if (data == null) {
			data = "";
		}
		try {
			return byte2hex(encrypt(data.getBytes(), pass));
		} catch (Exception e) {
			logger.error("AES-ENCRYPT" + data, e);
		}
		return "";
	}

	
//	public static void main(String[] args) {
//		String aesKey = "hsylgwk-20120101";
//		// 头信息部分长度
//		try {
//
////			byte[] aesByteArray = {95, 30, 67, -61, -126, 44, -21, -54, -112, -127, -78, -37, -21, 6, -59, 39, -57, 102, -18, -56, 58, 13, 40, -128, -113, -21, -96, 95, 42, 60, 25, 30, 98, -119, -125, -37, 20, -52, -68, 104, 29, -93, -67, -105, -84, -122, 14, 73, 48, -96, -84, 38, -33, 34, 101, 84, 48, -20, 97, -32, 121, 12, 123, -42, -121, -67, 41, 61, -32, -55, -39, 38, 92, -41, -52, -64, -20, 60, 54, 22, 124, -94, 54, 20, 68, -71, 5, 126, -82, -52, -9, -23, -40, 54, 18, 5, -63, 123, 50, -119, -21, 119, 125, -98, -43, 45, 46, 0, -80, 11, -76, -22, 42, -87, -4, -17, 103, -127, -41, 88, -6, 12, 107, -40, 110, -42, 122, -111};
////			
////			
////			DataInputStream dis = null;
////
////			// only for Systme.log begin------------
////			StringBuilder sb = new StringBuilder();
////			for (int i = 0; i < aesByteArray.length; i++) {
////				sb.append(aesByteArray[i]);
////				if (i < aesByteArray.length - 1) {
////					sb.append(",");
////				}
////			}
////			log("<server to  client -bytes> ", sb.toString());
//			// only for Systme.log end------------
//
//			// DownCmdBase down1 = (DownCmd3rdPartyLogin) (new
//			// ClientDownCmdParser(dis).parse());
//
//			String data = " 23r23radfdfsf  af875`1`23`1234567899~!@#$$%%^^&&*&*(())asfa ioafdssssssiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiijmlkljlj;jj"
//					+ "jkjljljljljljljljljljdfweue0orf923ur0u    \n";
//			byte[] aesByteArray = data.getBytes();
//			StringBuilder sb = new StringBuilder();
//			for (int i = 0; i < aesByteArray.length; i++) {
//				sb.append(aesByteArray[i]);
//				if (i < aesByteArray.length - 1) {
//					sb.append(",");
//				}
//			}
//			log("<server to  client -bytes> ", sb.toString());
//			// only for Systme.log end------------
//			String aseString = encrypt(data, aesKey);
//			System.out.println(aseString);
//			System.out.println(aesByteArray.length);
//			
//			// decrypt bin
//			byte[] realContentByteArray = decrypt(hex2byte(aseString.getBytes()), aesKey);
//			System.out.println("result" + new String(realContentByteArray));
//			System.out.println(realContentByteArray.length);
////			String realContentByteArrayString = decrypt(aseString);
////			System.out.println(realContentByteArrayString);
//			
//			// only for Systme.log end------------
//			sb = new StringBuilder();
//			for (int i = 0; i < realContentByteArray.length; i++) {
//				sb.append(realContentByteArray[i]);
//				if (i < realContentByteArray.length - 1) {
//					sb.append(",");
//				}
//			}
//			log("<realContentByteArray-bytes> ", sb.toString());
//			// only for Systme.log end------------
//
//			InputStream inStream = new ByteArrayInputStream(realContentByteArray);
////			 DownCmdBase down2 = new ClientDownCmdParser(dis).parse();
//
////			 log("down1", down2);
//			 
//			 
//			 
//			 
//			 
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//	}

}