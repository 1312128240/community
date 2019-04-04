
package com.chanxa.linayi.tools;


import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class DataUtils {

    public static String dateFormat(Date date, String dateFormat){
        SimpleDateFormat format=new SimpleDateFormat(dateFormat);
       return format.format(date);
    }


    private static long getDayIdx(Calendar cdr) {
        return cdr.get(Calendar.YEAR) * 365 + cdr.get(Calendar.DAY_OF_YEAR);
    }

    public static String getTodayDate(){
        return dateFormat(new Date(), "yyyy-MM-dd");
    }

    public static String getTodayDateSs(){
        return dateFormat(new Date(), "yyyy_MM_dd_HH_mm");
    }

    // 以当前时间作为文件名
    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        return str;
    }


    public static long getDate(){
        return new Date().getTime();
    }


    public static String getLeavingListTime(String date){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");

        return TextUtils.isEmpty(date) ? null : format.format(new Date(Long.parseLong(date)));
    }

    public static int countDate(long startTime, long endTime){
        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);
        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(startDate);
        cal2.setTime(endDate);
        double dayCount = (cal2.getTimeInMillis()-cal1.getTimeInMillis())/(1000*3600*24);//从间隔毫秒变成间隔天数
        return (int) dayCount;
    }

    public static String getFootTime(String date){
        if (TextUtils.isEmpty(date)){
            return null;
        }
        SimpleDateFormat format=new SimpleDateFormat("HH点mm分");
        return format.format(new Date(Long.parseLong(date)));
    }

    public static String calMm(long ms) {
//        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");//初始化Formatter的转换格式。
        int minute = (int)ms / (1000 * 60);
        int second = (int)((ms % (1000 * 60)) / 1000);
        String m = Math.abs(minute) < 10 ? "0" + minute : minute + "";
        String s = Math.abs(second) < 10 ? "0" + second : second + "";
        return m + ":" + s;
    }

    public static boolean isTenMunite(long startTime){
        long minute1 = startTime / (1000 * 60);
        long minute2 = getDate() / (1000 * 60);
        long count = minute2 - minute1;
        return count >= 10;
    }

    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA));
        }
        return DateLocal.get();
    }

    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<>();
}
