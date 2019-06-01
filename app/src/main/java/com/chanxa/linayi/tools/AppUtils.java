
package com.chanxa.linayi.tools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;

import com.chanxa.linayi.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.chanxa.linayi.tools.DialogUtils.showDialog;


public class AppUtils {

    /**
     * 判断是否有网络
     * @param context
     * @param isShowDialog   true：显示加载框       false:不显示加载框
     * @return
     */
    public static boolean isNetwork(Context context, boolean isShowDialog) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo == null || !cm.getBackgroundDataSetting()) {
            if (isShowDialog)
                showDialog(context, context.getString(R.string.network_anomaly), false);
            return false;
        }
        return true;
    }


    /**
     * 判断手机格式是否正确
     * @param mobiles 手机号码
     * @return true正确的手机号码，flase手机号码不正确
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    /**
     * 判断只能是数字、字母或者数字和字母组合
     */
    public static boolean isOnlyNumOrLetter(String passwod) {
        Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher m = p.matcher(passwod);
        if (m.matches()) {
            return true;
        }
        return false;

    }

    /**
     * 判断字符只能是字母和数字组合
     * @param password
     * @return true表示是数字和字母组合，false不是
     */
    public static boolean isLetterAndNum(String password) {

        if (isOnlyNumOrLetter(password) && isContainLetter(password) && isContainNumber(password)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否含有字母
     * true为含有字母
     */
    public static boolean isContainLetter(String password) {

        return Pattern.compile("(?i)[a-zA-Z]").matcher(password).find();
    }

    /**
     * 判断是否含有数字
     * true为含有数字
     */
    public static boolean isContainNumber(String password) {

        return Pattern.compile("(?i)[0-9]").matcher(password).find();
    }


    /**
     * 设置系统的基本信息
     *
     * @param
     */
    public static String getSystemInfo(Context context) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);

            java.util.Random random = new java.util.Random();// 定义随机类
            String result = String.valueOf(random.nextInt(100000));// 返回[0,1000000)集合中的整数，注意不包括1000000
            String string = "";
            if (result.length() != 6) {
                for (int i = 0; i < (6 - result.length()); i++) {
                    string = string + "0";
                }
                result = string + result;
            }

            Date date = new Date();
            String str = "timestamp=" + sdf.format(date)
                    + "&version=1.0&"
                    + "App_version=" + packageInfo.versionName + "&"
                    + "mobile_os=Android&"
                    + "mobile_os_version=" + Build.VERSION.RELEASE + "&"
                    + "message_id=" + new SimpleDateFormat("yyyyMMddHHmmss").format(date) + result + "&"
                    + "mobile_type=" + Build.MODEL + "&"
                    + "resolution=" + dm.widthPixels + "*" + dm.heightPixels;

            return str;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }


}
