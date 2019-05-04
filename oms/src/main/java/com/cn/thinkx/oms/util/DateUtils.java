package com.cn.thinkx.oms.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期辅助类
 */
public class DateUtils {
	/**
	 * 禁止实例化
	 */
	private DateUtils() {}

	/**
	 * 默认样式
	 */
	private static String defalutPattern = "yyyy-MM-dd";

	/**
	 * 功能：得到当前日期
	 * 
	 * @return Date
	 */
	public static Date getCurrDate() {
		return new Date();
	}

	/**
	 * 功能：得到简单日期格式类
	 * 
	 * @return SimpleDateFormat
	 */
	public static SimpleDateFormat getSimpleDateFormat() {
		return new SimpleDateFormat();
	}

	/**
	 * 功能：得到简单日期格式类，并且使用样式
	 * 
	 * @param pattern
	 * @return
	 */
	public static SimpleDateFormat getSimpleDateFormat(String pattern) {
		SimpleDateFormat sf = getSimpleDateFormat();
		sf.applyPattern(pattern);
		return sf;
	}

	/**
	 * 功能：解析日期
	 * 
	 * @param dateStr
	 * @return Date
	 */
	public static Date parseDate(String dateStr) {
		return parseDate(dateStr, defalutPattern);
	}

	public static String parseDateStr(String dateStr, String pattern) {
		if (StringUtils.isNullOrEmpty(dateStr)) {
			return "";
		}
		SimpleDateFormat sf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(parseDate(dateStr, pattern));
	}

	/**
	 * 功能：解析日期，根据样式
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return Date
	 */
	public static Date parseDate(String dateStr, String pattern) {
		if (StringUtils.isNullOrEmpty(dateStr)) {
			return null;
		}
		Date date = null;
		try {
			SimpleDateFormat sf = getSimpleDateFormat(pattern);
			date = sf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
			date = null;
		}
		return date;
	}

	/**
	 * 功能：默认年月日
	 * 
	 * @return String
	 */
	public static String getYMD() {
		SimpleDateFormat sf = getSimpleDateFormat(defalutPattern);
		return sf.format(getCurrDate());
	}

	/**
	 * 功能：得到日期时间
	 * 
	 * @return
	 */
	public static String getDateTime() {
		SimpleDateFormat sf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(getCurrDate());
	}

	/**
	 * 功能：得到日期时间
	 * 
	 * @return
	 */
	public static String getDateTime(String pattern) {
		SimpleDateFormat sf = getSimpleDateFormat(pattern);
		return sf.format(getCurrDate());
	}

	/**
	 * 功能：得到无格式日期时间
	 * 
	 * @return
	 */
	public static String getDateTimeNoFormat() {
		SimpleDateFormat sf = getSimpleDateFormat("yyyyMMddHHmmss");
		return sf.format(getCurrDate());
	}

	/**
	 * 功能：得到日期时间及毫秒时间
	 * 
	 * @return
	 */
	public static String getDateMilliTime() {
		SimpleDateFormat sf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		return sf.format(getCurrDate());
	}

	/**
	 * 功能：到得日期字符类型
	 * 
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static String getDate(Date date, String pattern) {
		SimpleDateFormat sf = getSimpleDateFormat(pattern);
		return sf.format(date);
	}

	/**
	 * 功能：到得日期字符类型
	 * 
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static String getDate(Date date) {
		return getDate(date, defalutPattern);
	}

	/**
	 * 功能：操作日期类
	 * 
	 * @param date
	 * @param type
	 * @param num
	 * @return Date
	 */
	public static Date addDate(Date date, int type, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(type, num);
		return cal.getTime();
	}

	/**
	 * 功能：操作日期类
	 * 
	 * @param date
	 * @param num
	 * @return Date
	 */
	public static Date addDate(Date date, int num) {
		return addDate(date, Calendar.DAY_OF_MONTH, num);
	}

	/**
	 * 得到两个日期间隔的天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return int
	 */
	public static int getDateDistance(Date startDate, Date endDate) {
		long num = endDate.getTime() - startDate.getTime();
		return (int) (num / (1000 * 60 * 60 * 24));
	}

	/**
	 * 得到两个日期间隔的天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return int
	 */
	public static int getDateDistance(long startDate, long endDate) {
		long num = endDate - startDate;
		return (int) (num / (1000 * 60 * 60 * 24));
	}

	/**
	 * 功能：得到日期的星期几,周一到周日
	 * 
	 * @param date
	 * @return int
	 */
	public static int getDateWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.DAY_OF_WEEK);
		if (week == Calendar.SUNDAY) {
			week = 8;
		}
		return week - 1;
	}

	/**
	 * 功能：判断date是否为工作日 工作日默认为：1-5
	 * 
	 * @param date
	 *            boolean
	 * @return
	 */
	public static boolean isWorkday(Date date) {
		int week = getDateWeek(date);
		boolean workday = week > 0 && week < 6;
		return workday;
	}

	/**
	 * 功能：得到某天最小值
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getMinDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 功能：得到今天最小值
	 * 
	 * @param date
	 * @return Date
	 */
	public static long getTodayYmd() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime().getTime();
	}

	/**
	 * 功能：得到日期中的小时，为24小时制
	 * 
	 * @param date
	 * @return int
	 */
	public static int getDateHour(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 功能：设置日期时间
	 * 
	 * @param date
	 * @param key
	 *            Calendar.HOUR_OF_DAY ： 小时；Calendar.MINUTE：分钟
	 * @param value
	 * @return Date
	 */
	public static Date setDateTime(Date date, int key, int value) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(key, value);
		return cal.getTime();
	}

	/**
	 * 功能：得到某天最大值
	 * 
	 * @return Date
	 */
	public static Date getMaxDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	/**
	 * 功能：得到日期是哪一天
	 * 
	 * @return Date
	 */
	public static int getDateDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 功能：得到日期是哪一天
	 * 
	 * @return Date
	 */
	public static int getYearDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 功能：得到今天最小值
	 * 
	 * @return Date
	 */
	public static String getMinToday() {
		return getDate(getMinDate(getCurrDate()), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 功能：得到某天最大值
	 * 
	 * @return Date
	 */
	public static String getMaxTodayAfterDay(int days) {
		return getDate(getMaxDate(getTodayAfterDay(days)), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 功能：得到某天最小值
	 * 
	 * @return Date
	 */
	public static String getMinTodayAfterDay(int days) {
		return getDate(getMinDate(getTodayAfterDay(days)), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到今天以后多少天的日期
	 * 
	 * @param days
	 *            多少天以后
	 * @return Date
	 */
	public static Date getTodayAfterDay(int days) {
		return addDate(getCurrDate(), Calendar.DAY_OF_MONTH, days);
	}

	/**
	 * 得到今天以后多少天的最大日期
	 * 
	 * @param days
	 *            多少天以后
	 * @return Date
	 */
	public static Date getMaxAfterDay(int days) {
		return getMaxDate(addDate(getCurrDate(), Calendar.DAY_OF_MONTH, days));
	}

	/**
	 * 得到今天以后多少天的最小日期
	 * 
	 * @param days
	 *            多少天以后
	 * @return Date
	 */
	public static Date getMinAfterDay(int days) {
		return getMinDate(addDate(getCurrDate(), Calendar.DAY_OF_MONTH, days));
	}

	/**
	 * 功能：得到当前时间前几小时时间
	 * 
	 * @return Date
	 */
	public static String getTodayBeforeHours(int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hours);
		return getDate(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 功能：测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(parseDateStr("20150724143115", "yyyyMMddHHmmss"));
	}
}
