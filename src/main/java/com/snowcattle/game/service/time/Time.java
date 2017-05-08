package com.snowcattle.game.service.time;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author C172
 *	时间
 */
public class Time {
	
	public final Logger log = LoggerFactory.getLogger(Time.class);
	
	/**
	 * 服务器的运行时间
	 */
	public int currTime = 0;
	public long startTime = 0;
	public int tick = 0;
	
//	public static int day = 0;
	
	public static Date currDate = null;
	
	
//	public static final List<DayListener> dayListeners = new ArrayList<DayListener>();
	
	public  void init(){
		currDate = new Date();
		startTime = currDate.getTime();
//		Calendar cal = Calendar.getInstance();
//		day = (cal.get(Calendar.YEAR)<<16)|cal.get(Calendar.DAY_OF_YEAR);
	}
	
//	public static void resetDay(){
//		Calendar cal = Calendar.getInstance();
//		int newDay = (cal.get(Calendar.YEAR)<<16)|cal.get(Calendar.DAY_OF_YEAR);
//		if(newDay!=day){
//			day = newDay;
//			log.info("[DAYCHANGED]OLD["+day+"]NEW["+newDay+"]");
////			for(DayListener l:dayListeners){
////				try {
////					l.dayChanged();
////				} catch (Exception e) {
////					e.printStackTrace();
////				}
////			}
//		}
//	}
	
	public long update(){
		currDate = new Date();
		long t = currDate.getTime()-startTime;
		long ret = t - currTime;
		if (ret < 0) {
		    startTime += ret;
		    ret = 0;
		    t = currTime;
		}
		currTime = (int)t;
//		resetDay();
		return ret;
	}
	
	public int elapseTime(long time){
		return (int)(time - startTime);
	}
	
	public  long currentTimeMillis(int t){
		return startTime + t;
	}
//	
//	
//	/**
//	 * ȡ��һ�������ʱ��
//	 * @param date
//	 * @return
//	 */
//	public static Date getDateNextDay(Date date){
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(date);
//		cal.add(Calendar.DAY_OF_MONTH, 1);
//		cal.set(Calendar.HOUR_OF_DAY, 0);
//		cal.set(Calendar.MINUTE, 0);
//		cal.set(Calendar.SECOND, 0);
//		cal.set(Calendar.MILLISECOND, 0);
//		return cal.getTime();
//	}
	
//	public boolean betweenHour(Date date,int begin,int end){
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(date);
//		int hour = cal.get(Calendar.HOUR_OF_DAY);
//		return hour>=begin&&hour<=end;
//	}
	
//	public static void addDayListener(DayListener l){
//		dayListeners.add(l);
//	}
}
