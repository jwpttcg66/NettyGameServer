package com.snowcattle.game.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class DesEncrypter {
	Cipher ecipher;
	Cipher dcipher;

	// 8-byte Salt
	byte[] salt = { (byte) 0xB9, (byte) 0xAB, (byte) 0xD8, (byte) 0x42, (byte) 0x66, (byte) 0x45, (byte) 0xF3,
			(byte) 0x13 };

	// Iteration count
	int iterationCount = 17;

	public DesEncrypter(String passPhrase) {
		this(passPhrase, null);
	}

	public DesEncrypter(String passPhrase, byte[] newSalt) {
		try {
			if (newSalt != null) {
				salt = newSalt;
			}
			// Create the key
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
			ecipher = Cipher.getInstance(key.getAlgorithm());
			dcipher = Cipher.getInstance(key.getAlgorithm());

			// Prepare the parameter to the ciphers
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

			// Create the ciphers
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		} catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException e) {
		}
    }

	public String encrypt(String str) {
		try {
			// Encode the string into bytes using utf-8
			byte[] utf8 = str.getBytes("UTF8");

			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);

			// Encode bytes to base64 to get a string
			return new BASE64Encoder().encode(enc);
		} catch (BadPaddingException | IOException | IllegalBlockSizeException e) {
		}
        return null;
	}

	public String decrypt(String str) {
		try {
			// Decode base64 to get bytes
			byte[] dec = new BASE64Decoder().decodeBuffer(str);

			// Decrypt
			byte[] utf8 = dcipher.doFinal(dec);

			// Decode using utf-8
			return new String(utf8, "UTF8");
		} catch (BadPaddingException | IOException | IllegalBlockSizeException e) {
		}
        return null;
	}
	
//	private static DesEncrypter encrypter = new DesEncrypter("4DP2bC5r",
//			new byte[]{(byte)0x62, (byte)0xB1, (byte)0x35, (byte)0xF8, (byte)0xC6, (byte)0x5A, (byte)0x44, (byte)0xE2});
//
//	public static String encodeToken(long accountId, String ip, long timestamp, String name){
//		return encrypter.encrypt(accountId+","+ip+","+timestamp+","+name);
//	}
//
//	// 根据Token结出account和时间戳
//	public static String decodeToken(String token){
//		return encrypter.decrypt(token);
//	}

	public static void main(String[] args){
//		String token = encodeToken(1001, "127.0.0.1", System.currentTimeMillis(), "jackflit");
//		String msg = decodeToken(token);
//		System.out.println(msg);
		
//		DesEncrypter encrypter = new DesEncrypter("4DP2bC5r");
//		String message = "lool";
//		String msg = encrypter.encrypt(message);
//		System.out.println(msg);
//		String msg2 = encrypter.decrypt(msg);
//		System.out.println(msg2);
	}
}
