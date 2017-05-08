package com.snowcattle.game.common.util;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * Shoot机制的工具函数
 *
 * 生成随机汉字
 *
 * 生成随机颜色
 *
 * 生成随机Shoot图片，包含随机的汉字和颜色
 *
 * 建议使用过程
 * 1. 预处理阶段，生成一组图片，每个图片一个文件+文字，每次系统启动阶段生成 jpegdata byte[] + chinese string
 *
 * 2. 每次使用的时候，传递给对方三个选项和数组，每个图片控制在xK以下
 *
 * @author jackflit
 *
 */
public class ShootUtils {

	public static int getRandom(int Num){
		return (int)(Math.random()*Num);
	}

	  public Color getRandColor(int fc, int bc) { //给定范围获得随机颜色
		    Random random = new Random();
		    if (fc > 255)
		      fc = 255;
		    if (bc > 255)
		      bc = 255;
		    int r = fc + random.nextInt(bc - fc);
		    int g = fc + random.nextInt(bc - fc);
		    int b = fc + random.nextInt(bc - fc);
		    return new Color(r, g, b);
		  }


	public static String getRandomChineseChar(){
		try {
			return new String(new byte[]{
					(new Integer(176+getRandom(71+1))).byteValue(), //176=B0 D7   0~71
					(new Integer(161+getRandom(88+1))).byteValue()  //161=A1 FE  0~93
				},"GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "熵";
		}
	}

	public static String getRandomChineseString(int length){
		StringBuffer buffer = new StringBuffer(length);
		for(int i=0; i<length; i++){
			buffer.append(getRandomChineseChar());
		}
		return buffer.toString();
	}

	public static char getRandomNumber(){
		final char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		int digit = getRandom(10);
		if(digit < 0){
			digit = 0;
		}
		if(digit > 9){
			digit = 9;
		}
		return chars[digit];

	}

	public static String getRandomNumber(int length){
		StringBuffer buffer = new StringBuffer(length);
		for(int i=0; i<length; i++){
			buffer.append(getRandomNumber());
		}
		return buffer.toString();
	}


//	public static void main(String[] args){
//		for(int i=0; i<100; i++){
//			String chinese = getRandomNumber(4);
//			try {
//				FileOutputStream fout = new FileOutputStream(chinese+".jpg");
//				fout.write(getNumberShootImage(chinese));
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//		}
//
//	}

	public static byte[] getNumberShootImage(String numbers){
		ByteArrayOutputStream dest = new ByteArrayOutputStream();
		new RegisterCode().create(70, 25, numbers,
							 "宋体", 19,//隶书  楷体_GB2312
							 1, 15,
							dest);
		return dest.toByteArray();

	}

	public static byte[] getDefaultShootImage(String chinese){
		ByteArrayOutputStream dest = new ByteArrayOutputStream();
		new RegisterCode().create(150, 50, RegisterCode.insertSpace(chinese,1),
							 "宋体", 24,//隶书  楷体_GB2312
							 1+ShootUtils.getRandom(30), 33,
							dest);
		return dest.toByteArray();
	}



}
