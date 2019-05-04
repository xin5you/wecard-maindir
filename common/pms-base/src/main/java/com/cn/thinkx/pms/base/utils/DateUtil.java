/**  ©2012, NCS (China) CO.LTD.All rights reserved.
 *
 *   Foreign Exchange Clearing System - version 1.2.0
 *
 *   This software is the property of NCS and its licensors and is protected by copyright. 
 *   Any reproduction in whole or in part is strictly prohibited.
 *  
 *   you can use the production compliance with the License. 
 *
 *   You can obtain a copy of the License at
 *
 *   http://www.ncs.com.sg
 *
 *   Unless required by applicable law or agreed to in writing.
 *   Source coding and software can be distributed  by the authorization of NCS,only.
 *   All other related product, document and logos are trademarks or registered trademarks of NCS.
 */
/*
 * Change Revision
 * ---------------
 * Date     		Author    			Remarks
 * 2012-2-4   		xiaoxia  		    initial files
 */

package com.cn.thinkx.pms.base.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {
	private static Log log = LogFactory.getLog(DateUtil.class);

	public static final int _Year = 1;
	public static final int _Month = 2;
	public static final int _Date = 3;
	public static final int auditlogDownloadDateIntervalMax = 7;
	public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
	public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String FORMAT_HHMMSS = "HHmmss";
	public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_CHINESE_FULL = "yyyy年MM月dd日 HH时mm分ss秒";
	public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String FORMAT_YYYYMMDDHHMMSS3S = "yyyyMMddHHmmssSSS";

	private SimpleDateFormat format;

	public DateUtil(SimpleDateFormat format) {
		this.format = format;
	}

	public SimpleDateFormat getFormat() {
		return format;
	}

	// 紧凑型日期格式，也就是纯数字类型yyyyMMdd
	public static final DateUtil COMPAT = new DateUtil(new SimpleDateFormat(FORMAT_YYYYMMDD));

	// 常用日期格式，yyyy-MM-dd
	public static final DateUtil COMMON = new DateUtil(new SimpleDateFormat(FORMAT_YYYY_MM_DD));
	public static final DateUtil COMMON_FULL = new DateUtil(new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MM_SS));

	// 使用斜线分隔的，西方多采用，yyyy/MM/dd
	public static final DateUtil SLASH = new DateUtil(new SimpleDateFormat("yyyy/MM/dd"));

	// 中文日期格式常用，yyyy年MM月dd日
	public static final DateUtil CHINESE = new DateUtil(new SimpleDateFormat("yyyy年MM月dd日"));
	public static final DateUtil CHINESE_FULL = new DateUtil(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒"));

	/**
	 * 日期获取字符串
	 */
	public String getDateText(Date date) {
		return getFormat().format(date);
	}

	/**
	 * 日期获取字符串
	 */
	public static String getDateText(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 字符串获取日期
	 * 
	 * @throws ParseException
	 */
	public static Date getTextDate(String dateText, String format) throws ParseException {
		return new SimpleDateFormat(format).parse(dateText);
	}

	/**
	 * 根据日期，返回其星期数，周一为1，周日为7
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int w = calendar.get(Calendar.DAY_OF_WEEK);
		int ret;
		if (w == Calendar.SUNDAY)
			ret = 7;
		else
			ret = w - 1;
		return ret;
	}

	/**
	 * get system date
	 * 
	 * @return date
	 */
	public static Date getSystemDate() {
		return new Date(System.currentTimeMillis());
	}

	public static long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 功能：得到当前日期
	 * 
	 * @return Date
	 */
	public static Date getCurrDate() {
		return new Date();
	}

	public static Date toDateStart(String dateStr) throws ParseException {
		return new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MM_SS).parse(dateStr + " 00:00:00");
	}

	public static Date toDateEnd(String dateStr) throws ParseException {
		return new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MM_SS).parse(dateStr + " 23:59:59");
	}

	/**
	 * get current day, not time
	 * 
	 * @return date
	 */
	public static Date getCurrentDate() {
		return getDateFromString(getStringFromDate(getSystemDate(), FORMAT_YYYYMMDD), FORMAT_YYYYMMDD);
	}

	public static String getCurrentDateStr() {
		return getStringFromDate(getSystemDate(), FORMAT_YYYYMMDD);
	}

	public static String getCurrentDateStr2() {
		return getStringFromDate(getSystemDate(), FORMAT_YYYY_MM_DD);
	}

	public static String getCurrentDateStr(String formatStr) {
		return getStringFromDate(getSystemDate(), formatStr);
	}

	public static String getCurrentTimeStr() {
		return getStringFromDate(getSystemDate(), FORMAT_HHMMSS);
	}

	public static String getCurrentDateTimeStr() {
		return getStringFromDate(getCurrDate(), FORMAT_YYYY_MM_DD_HH_MM_SS);
	}

	public static String getFullFormatStr(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYYMMDD + FORMAT_HHMMSS);
		try {
			return getStringFromDate(sdf.parse(dateStr), FORMAT_YYYY_MM_DD_HH_MM_SS);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * check if the date
	 * 
	 * @param str
	 *            -it is the date string
	 * @param format
	 *            -format,eg: yyyyMMdd
	 * @return true/false
	 */
	public static boolean isDate(String str, String format) {
		try {
			if (StringUtil.isEmpty(str)) {
				return false;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date d = sdf.parse(str);
			if (str.equals(sdf.format(d))) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public static Date[] getStartAndEndOfTheDate(Date date) {
		return new Date[] { getStartOfDay(date), getEndOfDay(date) };
	}

	public static Date getBeforeDate(Date date, int dayNum) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 0 - dayNum);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date addMonth(Date date, int i) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTimeInMillis(date.getTime());
		c.add(GregorianCalendar.MONTH, i);
		return new Date(c.getTimeInMillis());
	}

	public static Date[] getFirstAndLastDayOfMonth(Date date) {
		Date[] ds = new Date[2];
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
		ds[0] = new Date(c.getTimeInMillis());
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
		ds[1] = new Date(c.getTimeInMillis());
		return ds;
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 */
	public static Date convertStringToDate(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df;
		Date date;
		df = new SimpleDateFormat(aMask);

		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * @param date
	 * @return the start of that day
	 */
	public static Date getStartOfDay(Date date) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTimeInMillis(date.getTime());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * @param date
	 * @return the end of that day
	 */
	public static Date getEndOfDay(Date date) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTimeInMillis(date.getTime());
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * get the date by format
	 * 
	 * @param str
	 *            -it is the date string
	 * @param format
	 *            -format,eg: yyyyMMdd
	 * @return date
	 */
	public static Date getDateFromString(String str, String format) {
		try {
			if (StringUtils.isEmpty(str)) {
				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * compare if dateEnds > dateBegins + interval
	 * 
	 * @param dateBegins
	 *            -begin date
	 * @param dateEnds
	 *            -end date
	 * @param interval
	 *            -the count of the interval
	 * @param format
	 *            -format, eg: yyyyMMdd
	 * @return true/false
	 * @throws Exception
	 *             -parse Exception
	 */
	public static boolean checkBeginEndWithinInterval(String dateBegins, String dateEnds, int interval, String format)
			throws Exception {
		Date beginDate = new SimpleDateFormat(format).parse(dateBegins);
		Date endDate = new SimpleDateFormat(format).parse(dateEnds);

		GregorianCalendar grc = new GregorianCalendar();
		grc.setTime(beginDate);
		grc.add(GregorianCalendar.DATE, interval);

		return grc.getTime().before(endDate);
	}

	/**
	 * get date of beginDate + interval
	 * 
	 * @param beginDate
	 *            -begin date
	 * @param interval
	 *            -the count of interval
	 * @return date
	 */
	public static Date getDatebyInterval(Date beginDate, int interval) {
		GregorianCalendar grc = new GregorianCalendar();
		grc.setTime(beginDate);
		grc.add(GregorianCalendar.DATE, interval);
		return grc.getTime();
	}

	public static boolean isDate(String str) {

		if (StringUtils.isEmpty(str))
			return false;
		if (str.length() != 8 && str.length() != 10)
			return false;
		String test = new String(str);
		if (test.length() == 10) {
			test = test.substring(0, 4) + test.substring(5, 7) + test.substring(8, 10);
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYYMMDD);
			Date d = sdf.parse(test);
			if (test.equals(sdf.format(d)))
				return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public static Date getDateFromString(String str) throws Exception {
		if (StringUtils.isEmpty(str))
			return null;
		if (str.length() != 8 && str.length() != 10)
			return null;
		if (str.length() == 10) {
			str = str.substring(0, 4) + str.substring(5, 7) + str.substring(8, 10);
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYYMMDD);
			return sdf.parse(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date getDateTimeFromString(String str) throws Exception {
		if (StringUtils.isEmpty(str))
			return null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS);
			return sdf.parse(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getStringFromDate(Date date, String format) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String getStringFromDate(Date date) {
		if (date == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sdf.format(date);
	}

	public static Date moveToDate(int flg, Date date, int count) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(date);
			switch (flg) {
			case _Year:
				calendar.add(Calendar.YEAR, count);
			case _Month:
				calendar.add(Calendar.MONTH, count);
			case _Date:
				calendar.add(Calendar.DATE, count);
			}
			return calendar.getTime();
		} catch (Exception e) {
			return null;
		}
	}

	public static Date getCurrentTime() {
		return new Date(System.currentTimeMillis());
	}

	public static boolean isBeginEndWithinInterval(String dateBegins, String dateEnds, int interval) throws Exception {
		Date beginDate = new SimpleDateFormat(FORMAT_YYYY_MM_DD).parse(dateBegins);
		Date endDate = new SimpleDateFormat(FORMAT_YYYY_MM_DD).parse(dateEnds);

		GregorianCalendar grc = new GregorianCalendar();
		grc.setTime(beginDate);
		grc.add(GregorianCalendar.DATE, interval);

		return grc.getTime().after(endDate);
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to mm/dd/yyyy.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	/*
	 * public static String getDate(Date aDate) { SimpleDateFormat df; String
	 * returnValue = "";
	 * 
	 * if (aDate != null) { df = new SimpleDateFormat(getDatePattern());
	 * returnValue = df.format(aDate); }
	 * 
	 * return (returnValue); }
	 * 
	 *//**
		 * Return default datePattern (MM/dd/yyyy)
		 * 
		 * @return a string representing the date pattern on the UI
		 *//*
		 * public static String getDatePattern() { Locale locale =
		 * LocaleContextHolder.getLocale(); String defaultDatePattern; try {
		 * defaultDatePattern = ResourceBundle.getBundle(
		 * "ApplicationResources", locale).getString("date.format"); } catch
		 * (MissingResourceException mse) { defaultDatePattern = "MM/dd/yyyy"; }
		 * 
		 * return defaultDatePattern; }
		 */

	public static boolean isLastDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int day1 = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		if (day == day1)
			return true;
		return false;
	}

	public static boolean isfirstDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_MONTH);
		if (day == 1)
			return true;
		return false;
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	public static int getDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/*
	 * pay attention srcDate&targetDate do not have hour.min.second or 00:00:00
	 */
	public static int getBetweenDays(Date srcDate, Date targetDate) {
		Date srcDate1 = DateUtils.truncate(srcDate, Calendar.DATE);
		Date targetDate1 = DateUtils.truncate(targetDate, Calendar.DATE);
		long i = targetDate1.getTime() - srcDate1.getTime();
		int result = (int) (i / (1000 * 60 * 60 * 24));
		return result;
	}

	public static Date addDate(Date date, int i) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTimeInMillis(date.getTime());
		c.add(GregorianCalendar.DATE, i);
		return new Date(c.getTimeInMillis());

	}

	public static Date getNextDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	public static String getNextDay(String date) {
		Date d = null;
		try {
			d = getNextDay(getDateFromString(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getStringFromDate(d, FORMAT_YYYY_MM_DD);
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

	public static boolean isSameDay(Date d1, Date d2) {
		if (d1 == null || d2 == null)
			return false;
		else
			return getStringFromDate(d1, FORMAT_YYYYMMDD).equals(getStringFromDate(d2, FORMAT_YYYYMMDD));
	}

	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	public static Timestamp getTimeStampFormStr(String dateStr) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat(FORMAT_YYYYMMDD);
		return new Timestamp(sf.parse(dateStr).getTime());
	}

	public static String convertDateStrYYYYMMDD(String date) {
		try {
			return getStringFromDate(getDateFromString(date), FORMAT_YYYYMMDD);
		} catch (Throwable t) {
			return "";
		}
	}

	public static String getFormatStringFormString(String dateStr) throws Exception {
		return getStringFromDate(getDateFromString(dateStr), FORMAT_YYYYMMDD);
	}

	public static String getFormatStringFormString(String dateStr, String formatStr) throws Exception {
		return getStringFromDate(getDateFromString(dateStr), formatStr);
	}

	public static String getFormatDateFormString(Date date, String formatStr) throws Exception {
		return getStringFromDate(date, formatStr);
	}

	public static String getChineseFullFormStr(String dateStr) throws Exception {
		return getStringFromDate(getDateTimeFromString(dateStr), FORMAT_CHINESE_FULL);
	}

	public static String getChineseDateFormStr(String dateStr) throws Exception {
		return getStringFromDate(getDateTimeFromString(dateStr), FORMAT_YYYY_MM_DD_HH_MM_SS);
	}

	public static void main(String[] args) {
		try {
			System.out.println(getCurrentDateTimeStr());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * convert new Date() to yyyyMMDDH24Hmmss
	 * 
	 * @return
	 */
	public static String convertCurrentDateTimeToString() {
		return new SimpleDateFormat("yyyyMMddH24Hmmss", Locale.CHINA).format(new Date());
	}

	public static boolean isInPeriod(Date date, Date startDate, Date endDate) {
		if (startDate != null && endDate != null) {
			String d = getStringFromDate(date, FORMAT_YYYYMMDD);
			String sd = getStringFromDate(startDate, FORMAT_YYYYMMDD);
			String ed = getStringFromDate(endDate, FORMAT_YYYYMMDD);
			return d.compareTo(sd) >= 0 && d.compareTo(ed) <= 0;
		} else if (startDate == null && endDate == null) {
			return true;
		} else if (startDate != null) {
			String d = getStringFromDate(date, FORMAT_YYYYMMDD);
			String sd = getStringFromDate(startDate, FORMAT_YYYYMMDD);
			return d.compareTo(sd) >= 0;
		} else {
			String d = getStringFromDate(date, FORMAT_YYYYMMDD);
			String ed = getStringFromDate(endDate, FORMAT_YYYYMMDD);
			return d.compareTo(ed) <= 0;
		}
	}

}