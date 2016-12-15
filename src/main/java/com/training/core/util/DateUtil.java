package com.training.core.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateUtils;

/**
 * 日期工具类。<br>
 *
 * @author zhou jintong
 * @version 1.0
 */
public class DateUtil {

    /**
     * 按照指定的格式返回日期字符串. 默认 "yyyy-MM-dd"
     *
     * @param date
     * @param pattern
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) return "";
        if (pattern == null) pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return (sdf.format(date));
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        return (formatDate(date, "yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 当前时间 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String formatDateTime() {
        return (formatDate(now(), "yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 当前时间 yyyyMMddHHmmss
     *
     * @return
     */
    public static String formatDateTime2() {
        return (formatDate(now(), "yyyyMMddHHmmss"));
    }

    /**
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return (formatDate(date, "yyyy-MM-dd"));
    }

    /**
     * 当前日期 yyyy-MM-dd
     *
     * @return
     */
    public static String formatDate() {
        return (formatDate(now(), "yyyy-MM-dd"));
    }

    /**
     * 当前日期 yyyyMMdd
     *
     * @return
     */
    public static String formatDate2() {
        return (formatDate(now(), "yyyyMMdd"));
    }

    /**
     * HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatTime(Date date) {
        return (formatDate(date, "HH:mm:ss"));
    }

    /**
     * 当前时间 HH:mm:ss
     *
     * @return
     */
    public static String formatTime() {
        return (formatDate(now(), "HH:mm:ss"));
    }

    /**
     * 当前时间 HHmmss
     *
     * @return
     */
    public static String formatTime2() {
        return (formatDate(now(), "HHmmss"));
    }

    /**
     * 当前时间 Date类型
     *
     * @return
     */
    public static Date now() {
        return (new Date());
    }

    /**
     * yyyy-MM-dd HH:mm:ss 转Date
     *
     * @param datetime
     * @return
     */
    public static Date parseDateTime(String datetime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if ((datetime == null) || (datetime.equals(""))) {
            return null;
        } else {
            try {
                return formatter.parse(datetime);
            } catch (ParseException e) {
                return parseDate(datetime);
            }
        }
    }

    /**
     * yyyy-MM-dd 转Date
     *
     * @param date
     * @return
     */
    public static Date parseDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        if ((date == null) || (date.equals(""))) {
            return null;
        } else {
            try {
                return formatter.parse(date);
            } catch (ParseException e) {
                return null;
            }
        }
    }

    public static Date parseDate(Date datetime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        if (datetime == null) {
            return null;
        } else {
            try {
                return formatter.parse(formatter.format(datetime));
            } catch (ParseException e) {
                return null;
            }
        }
    }

    public static String formatDate(Object o) {
        if (o == null) return "";
        if (o.getClass() == String.class)
            return formatDate((String) o);
        else if (o.getClass() == Date.class)
            return formatDate((Date) o);
        else if (o.getClass() == Timestamp.class) {
            return formatDate(new Date(((Timestamp) o).getTime()));
        } else
            return o.toString();
    }

    public static String formatDateTime(Object o) {
        if (o.getClass() == String.class)
            return formatDateTime((String) o);
        else if (o.getClass() == Date.class)
            return formatDateTime((Date) o);
        else if (o.getClass() == Timestamp.class) {
            return formatDateTime(new Date(((Timestamp) o).getTime()));
        } else
            return o.toString();
    }

    /**
     * 给时间加上或减去指定毫秒，秒，分，时，天、月或年等，返回变动后的时间
     *
     * @param date   要加减前的时间，如果不传，则为当前日期
     * @param field  时间域，有Calendar.MILLISECOND,Calendar.SECOND,Calendar.MINUTE,<br>
     *               Calendar.HOUR,Calendar.DATE, Calendar.MONTH,Calendar.YEAR
     * @param amount 按指定时间域加减的时间数量，正数为加，负数为减。
     * @return 变动后的时间
     */
    public static Date add(Date date, int field, int amount) {
        if (date == null) {
            date = new Date();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, amount);

        return cal.getTime();
    }

    public static Date addMilliSecond(Date date, int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }

    public static Date addSecond(Date date, int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    public static Date addMiunte(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    public static Date addHour(Date date, int amount) {
        return add(date, Calendar.HOUR, amount);
    }

    public static Date addDay(Date date, int amount) {
        return add(date, Calendar.DATE, amount);
    }

    public static Date addMonth(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    public static Date addYear(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    /**
     * @return 当前日期 yyyy-MM-dd
     */
    public static Date getDate() {
        return parseDate(formatDate());
    }

    public static Date getDateTime() {
        return parseDateTime(formatDateTime());
    }

    public static boolean between(Date date, int offset, TimeUnit unit) {
        return System.currentTimeMillis() - date.getTime() <= unit.toMillis(offset);
    }

    /**
     * 间隔日时间
     *
     * @param date
     * @param interval
     * @return
     */
    public static Date getDate(Date date, int interval) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + interval);
        return cal.getTime();
    }

    /**
     * 间隔月时间
     *
     * @param date
     * @param interval
     * @return
     */
    public static Date getDateByMonth(Date date, int interval) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + interval);
        return cal.getTime();
    }

    /**
     * 间隔分钟时间
     *
     * @param date
     * @param interval
     * @return
     */
    public static Date getDateByMinute(Date date, int interval) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + interval);
        return cal.getTime();
    }

    public static int compare(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        return cal1.compareTo(cal2);
    }

    /**
     * @param date1
     * @param date2
     * @return
     */
    public static int getDaysBetween(Date date1, Date date2) {
        java.util.Calendar d1 = java.util.Calendar.getInstance();
        java.util.Calendar d2 = java.util.Calendar.getInstance();
        d1.setTime(date1);
        d2.setTime(date2);

        if (d1.after(d2)) {
            java.util.Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days = d2.get(java.util.Calendar.DAY_OF_YEAR)
                - d1.get(java.util.Calendar.DAY_OF_YEAR);
        int y2 = d2.get(java.util.Calendar.YEAR);

        if (d1.get(java.util.Calendar.YEAR) != y2) {
            d1 = (java.util.Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
                d1.add(java.util.Calendar.YEAR, 1);
            } while (d1.get(java.util.Calendar.YEAR) != y2);
        }
        return days;
    }

    public static int getDiffDays(Date startdate, Date enddate) {
        int days = (int) ((enddate.getTime() - startdate.getTime()) / (24 * 3600 * 1000));
        return days;
    }
    /**
     * 根据日期获取年月日数组
     * @param date 不能为null,该方法不做date为null判断,为null时不知返回什么信息
     * @return int[]{年，月，日}数组长度固定
     *  exp :[2016, 05, 19]
     */
    public static String[] getYearAndMonthAndDay(Date date){
    	String strDate = DateUtil.formatDate(date, "yyyy-MM-dd");
        return new String[]{strDate.substring(0, 4),strDate.substring(5, 7),strDate.substring(8, 10)};
    }

    /**
     * 根据日期获取年
     * @param date 不能为null,该方法不做date为null判断,为null时不知返回什么信息
     * @return
     */
    public static String getYear(Date date) {
    	String strDate = DateUtil.formatDate(date, "yyyy-MM-dd");
        return strDate.substring(0, 4);
    }

    /**
     * 根据日期获取月
     * @param date 不能为null,该方法不做date为null判断,为null时不知返回什么信息
     * @return
     */
    public static String getMonth(Date date) {
    	String strDate = DateUtil.formatDate(date, "yyyy-MM-dd");
        return strDate.substring(5, 7);
    }

    /**
     * 根据日期获取日
     * @param date 不能为null,该方法不做date为null判断,为null时不知返回什么信息
     * @return
     */
    public static String getDay(Date date) {
    	String strDate = DateUtil.formatDate(date, "yyyy-MM-dd");
        return strDate.substring(8, 10);
    }

    public static void main(String[] args) {
        Date d1 = getDate();
        System.out.println(formatDate(getDate(d1, 180), "yyyy-MM-dd"));
        System.out.println(formatDate(getDateByMonth(d1, 3), "yyyy-MM-dd"));

        Date date1 = DateUtil.parseDate("2016-03-01");
        Date date2 = DateUtil.parseDate("2016-03-03");
        Date date3 = DateUtil.parseDate("2015-03-02");
        Date date4 = DateUtil.parseDate("2016-08-05");
        Date date5 = DateUtil.parseDate("2016-11-05");
        System.out.println(compare(date1, date2));
        System.out.println(getDaysBetween(date1, date2));
        System.out.println(getDaysBetween(date1, date2));
        System.out.println(getDaysBetween(date1, date3));
        System.out.println(getDaysBetween(date1, date4));
        System.out.println(getDaysBetween(date1, date5));
        System.out.println(Arrays.toString(getYearAndMonthAndDay(d1)));
        System.out.println(getYear(d1) + getMonth(d1) +getDay(d1));
        System.out.println("compare= "+DateUtil.compare(DateUtils.addDays(date1, 0), DateUtils.addDays(date2,-1)));
        
        
    }
}