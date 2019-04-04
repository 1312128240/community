package com.chanxa.linayi.tools;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * Created by chanxa on 2018/1/13.
 */

public class FormatUtils {

    public static String format(String value){
        if (value == null|| TextUtils.isEmpty(value)){
            return "0.00";
        }
        return  String.valueOf(new DecimalFormat("0.00").format(Float.parseFloat(value)/100));
    }
}
