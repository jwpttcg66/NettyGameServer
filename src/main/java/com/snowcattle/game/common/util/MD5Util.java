package com.snowcattle.game.common.util;

import java.security.MessageDigest;

/**
 * MD5 加密编码工具类（从龙之刃拷贝过来）
 *
 *
 */
public class MD5Util {

	/**
	 * 将输入的字符串进行MD5加密（编码）
	 *
	 * @param inputString
	 * @return
	 */
	public static String createMD5String(String inputString) {
		return encodeByMD5(inputString);
	}

	/**
	 * 验证MD5密码是否正确
	 *
	 * @param pass
	 * @param inputString
	 * @return
	 */
	public static boolean authMD5String(String md5, String inputString) {
		if (md5.equals(encodeByMD5(inputString))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 对字符串进行MD5编码
	 *
	 * @param originStr
	 * @return
	 */
	public static String encodeByMD5(String originStr) {
		if (originStr != null) {
			try {
				// 创建具有指定算法名称的信息摘要
				MessageDigest md = MessageDigest.getInstance("MD5");
				// 使用指定的字节数组对摘要进行最后的更新，然后完成摘要计算
				char[] _charStr = originStr.toCharArray();
				byte[] _byteStr = new byte[_charStr.length];
				for (int i = 0; i < _charStr.length; i++) {
					_byteStr[i] = (byte)_charStr[i];
				}
				byte[] _results = md.digest(_byteStr);
				StringBuffer _hexValue = new StringBuffer();
				for (int i = 0; i < _results.length; i++) {
					int _val = ((int)_results[i]) & 0xff;
					if(_val < 16){
						_hexValue.append("0");
					}
					_hexValue.append(Integer.toHexString(_val));
				}
				return _hexValue.toString();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}


}
