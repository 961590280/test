package com.weimingfj.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

	/**
	 * 默认时间格式
	 */
	public static final String DEFUALT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 获取当前时间字符�?
	 * 
	 * @param pattern
	 *            字符串格�?
	 * @return
	 */
	public static String getCurrDateStr(String pattern) {
		return getDateStr(Calendar.getInstance(), pattern);
	}

	/**
	 * 获取当前时间字符�?
	 * 
	 * @return
	 */
	public static String getCurrDateStr() {
		return getCurrDateStr(DEFUALT_DATE_FORMAT);
	}

	/**
	 * 获取指定时间格式的字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getDateStr(Calendar date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date.getTime());
	}

	/**
	 * 将时间字符串转换成时间对象Calendar
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 * @throws Exception
	 */
	public static Calendar getDate(String dateStr, String pattern)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dateStr));
		return c;
	}

	/**
	 * 将时间字符串转换成时间对象Calendar，字符串格式默认：yyyy-MM-dd HH:mm:ss格式
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public static Calendar getDate(String dateStr) throws Exception {
		return getDate(dateStr, DEFUALT_DATE_FORMAT);
	}

	/**
	 * 将long时间转换成时间字符串
	 * 
	 * @param longTime
	 * @param pattern
	 * @return
	 */
	public static String getDateStr(long longTime, String pattern) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(longTime);
		return getDateStr(c, pattern);
	}

	/**
	 * 将long时间转成时间字符串HH:mm
	 * 
	 * @param longTime
	 * @return
	 */
	public static String getTimeStr(String longTime) {
		String time = "";
		if (longTime != null && !"".equals(longTime)) {
			Long times = 0L;
			try {
				times = Long.parseLong(longTime);
			} catch (Exception e) {
				return time;
			}
			long offset = System.currentTimeMillis() - times;
			long day = (offset / 1000 / 60 / 60 / 24);
			if (day == 0) {
				time = getDateStr(times, "HH:mm");
			} else if (day == 1) {
				time = "昨天";
			} else {
				time = day + "天前";
			}
		}
		return time;
	}
}
