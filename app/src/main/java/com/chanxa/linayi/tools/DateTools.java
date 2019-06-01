package com.chanxa.linayi.tools;

import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTools {


    /**
     *   将时间戳成年月日时分秒格式
     */
    public static String getCurrentDate(String pattern) {
       // SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
        SimpleDateFormat formatter=new SimpleDateFormat(pattern);
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }


    /**
     * 格式化日期
     * @param date
     * @return
     */
    public static String formatDateNoYear(String date) {
        if (TextUtils.isEmpty(date)) {
            return "";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("MM-dd  HH:mm");
            return format.format(new Date(Long.parseLong(date)));
        }catch (Exception e){
            e.printStackTrace();
            Log.e("AppUtils", "formatDateNoYear: 错误的时间戳" );
            return "";
        }
    }

}
