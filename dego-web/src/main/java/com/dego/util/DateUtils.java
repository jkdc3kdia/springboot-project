package com.dego.util;

import com.dego.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * 描述：日期处理类
 *
 */
@Slf4j
public abstract class DateUtils {

    /**
     * 指定DateFormat的key值
     */
    public static final String DATE_FORMAT = "DATE_FORMAT";

    public static final String DATE = "yyyy-MM-dd";
    public static final String MONTH = "yyyy-MM";
    public static final String YYYY_MM_DD = "yyyy_MM_dd";
    public static final String DATE_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String DATE_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_HH_MM_SS_Z1 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DATE_HH_MM_SS_Z2 = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String DATE_HH_MM_SS_Z3 = "yyyy-MM-dd'T'HH:mm:ssz";
    public static final String DATE_HH_MM_SS_Z4 = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_HH_MM_SS_S_Z = "yyyy-MM-dd HH:mm:ss:S 'tz':z";

    public static final String DATE_HH_MM_SS_A = "MM/dd/yyyy HH:mm:ss a";
    public static final String DATE_HHMMSS = "yyyyMMddHHmmss";
    public static final String DATE_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_YYMMDD = "yyMMdd";

    /**
     * 一天的秒数
     */
    public final static int ONE_DAY_SECONDS = 24 * 60 * 60;

    public final static long ONE_DAY_MILLISECOND = 24 * 60 * 60 * 1000;

    private static BigDecimal MINUTE_TIME_MILLIS = new BigDecimal(60);

    private static List<DateFormat> formats = new ArrayList<>();

    static {
        /**alternative formats*/
        formats.add(new SimpleDateFormat(DATE_HH_MM_SS));
        formats.add(new SimpleDateFormat(DATE_HH_MM));
        formats.add(new SimpleDateFormat(DATE));
        formats.add(new SimpleDateFormat(MONTH));
        /**ISO formats*/
        formats.add(new SimpleDateFormat(DATE_HH_MM_SS_Z1));
        formats.add(new SimpleDateFormat(DATE_HH_MM_SS_Z2));
        formats.add(new SimpleDateFormat(DATE_HH_MM_SS_Z3));
        formats.add(DateFormat.getDateTimeInstance());
        /**XPDL examples format*/
        formats.add(new SimpleDateFormat(DATE_HH_MM_SS_A, Locale.US));
        formats.add(new SimpleDateFormat(DATE_HHMMSS));
        /**Only date, no time*/
        formats.add(DateFormat.getDateInstance());
    }

    /**
     * 字符串转化成日期
     *
     * @param dateString 字符串
     * @return 日期
     */
    public synchronized static Date parse(String dateString) {
        if (dateString == null) {
            return null;
        }
        for (DateFormat format : formats) {
            try {
                return format.parse(dateString);
            } catch (ParseException e) {
            }
        }
        return null;
    }

    public static Date parse(String str, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date parse(String str, String pattern, Locale locale) {
        DateFormat format = new SimpleDateFormat(pattern, locale);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            throw new BusinessException("date parse exception:" + e.getMessage());
        }
        return date;
    }

    public static Date parseExp(String str, String pattern) throws Exception {
        DateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        date = format.parse(str);
        return date;
    }

    /**
     * 日期类型转字符串
     *
     * @param date 日期
     * @return 字符串（格式为：yyyy-MM-dd）
     */
    public synchronized static String format(Date date) {
        return DateFormatUtils.format(date, DATE);

    }

    /**
     * 时间戳类型转字符串
     *
     * @param time 时间戳
     * @return 字符串（格式为：yyyy-MM-dd）
     */
    public synchronized static String format(Long time) {
        time = (time == null ? 0 : time);
        return format(new Date(time));
    }

    /**
     * 时间戳类型转字符串
     *
     * @param time 时间戳
     * @return 字符串（格式为：yyyy-MM-dd）
     */
    public synchronized static String format(Long time, String pattern) {
        time = (time == null ? 0 : time);
        return format(new Date(time), pattern);
    }

    /**
     * 日期类型转字符串
     *
     * @param date    日期
     * @param pattern 格式
     * @return 字符串
     */
    public static String format(Date date, String pattern) {
        if (date == null) return "";
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 将某个时间范围按天进行切分，未满一天的按一天算
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 日期集合
     */
    public static List<Date> splitByDay(Date startDate, Date endDate) {
        List<Date> dayList = new ArrayList<Date>();
        String startDateStr = DateFormatUtils.format(startDate, DATE);
        Date startDate1 = DateUtils.parse(startDateStr);

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate1);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        Calendar tempCal = Calendar.getInstance();
        tempCal.setTime(startDate1);
        tempCal.add(Calendar.DAY_OF_MONTH, 1);

        while (tempCal.before(endCal)) {
            dayList.add(startCal.getTime());
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            tempCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        dayList.add(startCal.getTime());
        return dayList;
    }

    /**
     * 判断两个时间是否在同一天内
     *
     * @param date1 时间
     * @param date2 时间
     * @return true=同一天；false=非同一天；
     */
    public static boolean isSameDay(Date date1, Date date2) {
        String date1Str = DateFormatUtils.format(date1, DATE);
        String date2Str = DateFormatUtils.format(date2, DATE);
        if (date1Str.equals(date2Str)) {
            return true;
        }
        return false;
    }

    /**
     * 判断两个时间是否在同一天内
     *
     * @param date1 时间
     * @param date2 时间
     * @return true=同一天；false=非同一天；
     */
    public static boolean isSameDay(LocalDate date1, LocalDate date2) {
        return date1.compareTo(date2) == 0;
    }

    /**
     * 判断两个时间是否在同一个月内
     *
     * @param date1 时间
     * @param date2 时间
     * @return true=在同一个月内；false=不在同一个月内；
     */
    public static boolean isSameMonth(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
            return true;
        }
        return false;
    }

    /**
     * 判断两个时间是否在同一季度里
     *
     * @param date1 时间
     * @param date2 时间
     * @return true=在同一个季度内；false=不在同一个季度内；
     */
    public static boolean isSameQuarter(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int month1 = cal1.get(Calendar.MONTH);
        int month2 = cal2.get(Calendar.MONTH);
        if (((month1 >= Calendar.JANUARY && month1 <= Calendar.MARCH) && (month2 >= Calendar.JANUARY && month2 <= Calendar.MARCH))
                || ((month1 >= Calendar.APRIL && month1 <= Calendar.JUNE) && (month2 >= Calendar.APRIL && month2 <= Calendar.JUNE))
                || ((month1 >= Calendar.JULY && month1 <= Calendar.SEPTEMBER) && (month2 >= Calendar.JULY && month2 <= Calendar.SEPTEMBER))
                || ((month1 >= Calendar.OCTOBER && month1 <= Calendar.DECEMBER) && (month2 >= Calendar.OCTOBER && month2 <= Calendar.DECEMBER))) {
            return true;
        }
        return false;
    }

    /**
     * 得到两个时间的差额
     *
     * @param date      时间
     * @param otherDate 时间
     * @return 时间差额
     */
    public static long betDate(Date date, Date otherDate) {
        return date.getTime() - otherDate.getTime();
    }

    /**
     * 获取当前日期
     *
     * @return long（毫秒）
     */
    public static long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    /**
     * 获取当前日期
     *
     * @return Date
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * 获取当前日期
     *
     * @return Calendar
     */
    public static Calendar getCurrentCalendar() {
        return Calendar.getInstance();
    }

    public static String calendarToString(Calendar calendar, String template) {
        String stringCalendar = template;
        stringCalendar = stringCalendar.replace("{year}",
                String.valueOf(calendar.get(Calendar.YEAR)));
        stringCalendar = stringCalendar.replace("{month}",
                String.valueOf(calendar.get(Calendar.MONTH)));
        stringCalendar = stringCalendar.replace("{date}",
                String.valueOf(calendar.get(Calendar.DATE)));
        stringCalendar = stringCalendar.replace("{hour}",
                String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
        stringCalendar = stringCalendar.replace("{minute}",
                String.valueOf(calendar.get(Calendar.MINUTE)));
        stringCalendar = stringCalendar.replace("{second}",
                String.valueOf(calendar.get(Calendar.SECOND)));
        stringCalendar = stringCalendar.replace("{millisecond}",
                String.valueOf(calendar.get(Calendar.MILLISECOND)));
        return stringCalendar;
    }

    /**
     * 比较时间差 1小时内的显示：**　分钟前，例如：25　分钟前 1小时前（１天内的）：今天　**时：**分，例如：　08：17
     * 1天前的（当前年）：*月*号　**时：**分，例如：05-09　23：58
     * 当前年之前的：年－月－日　**时：**分，例如：2009-09-26　16：33
     **/
    public static String timeCompare(Date _now, Date _date) {
        Calendar now = Calendar.getInstance();
        now.setTime(_now);
        Calendar date = Calendar.getInstance();
        date.setTime(_date);

        int nowY = now.get(Calendar.YEAR);
        int dateY = date.get(Calendar.YEAR);

        int nowM = now.get(Calendar.MONTH);
        int dateM = date.get(Calendar.MONTH);

        int nowD = now.get(Calendar.DAY_OF_MONTH);
        int dateD = date.get(Calendar.DAY_OF_MONTH);


        long l = now.getTimeInMillis() - date.getTimeInMillis();
        long m = nowM - dateM;
        long day = nowD - dateD;

        int dateHour = date.get(Calendar.HOUR_OF_DAY);
        int dateMinutes = date.get(Calendar.MINUTE);

        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long y = nowY - dateY;
        String ret = "";
        if (y > 0) {
            //大于一年的
            ret += (dateY + 1900) + "-";
        }

        if (day > 0 || y > 0 || m > 0) {// 大于一天的
            if (dateM + 1 < 10) {
                ret += "0";
            }

            ret += (dateM + 1) + "-";
            if (dateD < 10) {
                ret += "0";
            }
            ret += dateD + " ";
        }
        if (hour > 0 || day > 0 || y > 0 || m > 0) {// 大于一小时
            if (dateHour < 10) {
                ret += "0";
            }
            ret += dateHour + ":";
            if (dateMinutes < 10) {
                ret += "0";
            }
            ret += dateMinutes;
        }
        if (y == 0 && (day * 24 + hour) == 0 && min != 0) {
            ret = min + " 分前";
        }
        if (y == 0 && (day * 24 + hour) == 0 && min == 0) {
            ret = "1  分前";
        }
        return ret;
    }

    public static long getFormatedTime(long time) {
        return getFormatedTime(new Date(time));
    }

    public static long getFormatedTime(Date date) {
        if (date == null) {
            return 0;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return new Long(dateFormat.format(date)).longValue();
    }

    /**
     * @param deltaMillis 时差毫秒表示
     * @return
     * @description 给定时间差解析成中文表示(只计算过去的某个时间和当前时间的差)
     * <p>如"2小时32分"</p>
     */
    public static String subtractParse(long deltaMillis, String format) {
        if (deltaMillis <= 0) {
            return "";
        } else {
            StringBuffer res = new StringBuffer();
            long day = deltaMillis / (24 * 60 * 60 * 1000);
            long hour = (deltaMillis / (60 * 60 * 1000) - day * 24);
            long min = ((deltaMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec = (deltaMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            //long millis=(deltaMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-sec*1000);
            if (day > 0 && format.contains("d")) {
                res.append(day + "天");
            }
            if (hour > 0 && format.contains("H")) {
                res.append(hour + "小时");
            }
            if (min > 0 && format.contains("m")) {
                res.append(min + "分钟");
            }
            if (sec > 0 && format.contains("s")) {
                res.append(sec + "秒");
            }
            //String res = day+"天"+hour+"小时"+min+"分"+sec+"秒"+millis+"毫秒";
            return res.toString();
        }
    }

    /**
     * @param oldTime 过去的某个时间
     * @param format  格式化方式 。 如dHms表示 xx天xx小时xx分钟xx秒
     * @return
     * @description 解析过去某个时间和当前时间的差的中文表示
     * <p>如"2小时32分"</p>
     */
    public static String subtractParse(Date oldTime, String format) {
        Date current = getCurrentDate();
        if (oldTime.compareTo(current) <= 0) {
            return subtractParse(betDate(current, oldTime), format);
        }
        return "";
    }

    /**
     * 获取现在是几点（24小时）
     *
     * @return
     */
    public static int hourOfDay() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 现在是多少分
     *
     * @return
     */
    public static int minutesOfHour() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    /**
     * LocalDateTime 转 Date
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * LocalDateTime 转 Date
     *
     * @param localTime
     * @return
     */
    public static Date localTimeToDate(LocalTime localTime) {
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * LocalDateTime 转 时间戳
     *
     * @param localDateTime
     * @return
     */
    public static long localDateTimeToLong(LocalDateTime localDateTime) {
        return localDateTimeToDate(localDateTime).getTime();
    }

    /**
     * long 转 LocalDateTime
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime longToLocalDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * long 转 LocalDateTime
     *
     * @param timestamp
     * @return
     */
    public static LocalDate longToLocalDate(long timestamp) {
        return longToLocalDateTime(timestamp).toLocalDate();
    }

    /**
     * Date转LocalDate
     *
     * @param date
     */
    public static LocalDate dateToLocalDate(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Date转LocalDate
     *
     * @param date
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * LocalDate转Date
     *
     * @param localDate
     * @return
     */
    public static Date localDateToDate(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }


    /**
     * 获取当前时间指定天数之前或之后的时间
     *
     * @param days 负数：当前时间之前，正数：当前时间之后
     * @return
     */
    public static Date getDateForDays(int days) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 以一个时间为基点,增加天数返回新的时间
     *
     * @param date
     * @param num
     * @return
     */
    public static Date addDays(Date date, int num) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, num);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取某天的开始时间
     *
     * @param someday
     * @return
     */
    public static Date getStartOfDate(Date someday) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(someday);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    /**
     * 获取某天的开始时间
     * @param calendar
     * @return
     */
    public static Calendar getStartOfDate(Calendar calendar) {

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar;
    }
    /**
     * 获取某天的结束时间
     *
     * @param someday
     * @return
     */
    public static Date getEndOfDate(Date someday) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(someday);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.SECOND, -1);
        return cal.getTime();
    }

    public static Long getMinutes(Long startTime, Long endTime) {
        BigDecimal diffTimeMillis = new BigDecimal((endTime - startTime) / 1000);
        if (diffTimeMillis.compareTo(MINUTE_TIME_MILLIS) >= 0) {
            String[] splitUnit = getSplitUnit(diffTimeMillis, MINUTE_TIME_MILLIS);
            String minutes = splitUnit[0];
            return Long.valueOf(minutes);
        }
        return 0L;
    }

    private static String[] getSplitUnit(BigDecimal diffTimeMillis, BigDecimal unitTimeMillis) {
        BigDecimal result = diffTimeMillis.divide(unitTimeMillis, 2, RoundingMode.HALF_UP);
        return result.toString().split("\\.");
    }

    public static String secondToTime(long second) {
        //转换天数
        long days = second / 86400;
        //剩余秒数
        second = second % 86400;
        //转换小时
        long hours = second / 3600;
        //剩余秒数
        second = second % 3600;
        //转换分钟
        long minutes = second / 60;
        //剩余秒数
        second = second % 60;
        if (days > 0) {
            return days + "天" + hours + "小时" + minutes + "分" + second + "秒";
        } else {
            return hours + "小时" + minutes + "分" + second + "秒";
        }
    }

    public static void main(String[] args) {
        System.out.println(DateUtils.getDateForDays(-30).getTime());
        System.out.println(System.currentTimeMillis());


    }
}
