
package com.chanxa.linayi.tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chanxa.linayi.HttpClient.api.ApiResponse;
import com.chanxa.linayi.Interface.DialogListener;
import com.chanxa.linayi.R;
import com.chanxa.linayi.bean.City;
import com.chanxa.linayi.bean.Country;
import com.chanxa.linayi.bean.Province;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
     * 获取应用程序版本名称信息
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
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
     * 判断是否含有非法字符(出数字、字母和中文以外的字符)
     * @param str
     * @return true表示没有特殊字符
     */
    public static boolean isIIIegalCharacter(String str) {
        Pattern p = Pattern.compile("[(a-zA-Z0-9\\u4e00-\\u9fa5)]+");       //过滤出字母、数字和中文
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 判断email格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        System.out.println("邮箱：" + email);
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 摄氏温度转华氏温度
     * @param centigrade 摄氏温度
     * @return 华氏温度
     */
    public static double centigrade2Fahrenheit(String centigrade) {

        if (TextUtils.isEmpty(centigrade)) {
            return 0;
        }
        double c = Double.parseDouble(centigrade);
        double F = c * 9 / 5 + 32;

        return F;
    }

    /**
     * 摄氏温度转华氏温度
     * @param centigrade 摄氏温度
     * @return 返回华氏温度
     */
    public static double centigrade2Fahrenheit(double centigrade) {

        double F = centigrade * 9 / 5 + 32;
        return F;
    }

    /**
     * 摄氏温度转华氏温度
     * @param centigrade 摄氏温度
     * @return 返回华氏温度
     */
    public static String centigrade2Fahrenheit2(String centigrade) {

        if (TextUtils.isEmpty(centigrade)) {
            return "";
        }
        double c = Double.parseDouble(centigrade);
        double F = c * 9 / 5 + 32;

        return new DecimalFormat("0").format(F);
    }

    /**
     * 华氏温度转摄氏温度
     * @param fahrenheit 华氏温度
     * @return 返回摄氏温度
     */
    public static String fahrenheit2Centigrade(String fahrenheit) {

        if (TextUtils.isEmpty(fahrenheit)) {
            return "";
        }
        double f = Double.parseDouble(fahrenheit);
        double c = ((f - 32) * 5) / 9;

        return new DecimalFormat("0").format(c);
    }

    /**
     * 华氏温度转摄氏温度
     * @param fahrenheit 华氏温度
     * @return 返回摄氏温度
     */
    public static String fahrenheit2Centigrade(double fahrenheit) {

        double c = ((fahrenheit - 32) * 5) / 9;

        return new DecimalFormat("0").format(c);
    }


    /**
     * 克转盎司(四舍五入)
     * @param gStr
     * @return
     */
    public static String g_to_oz(String gStr) {
        if (TextUtils.isEmpty(gStr)) {
            return "";
        }
        double g = Double.parseDouble(gStr);

        return String.format("%.1f", g / 28.3);
    }

    /**
     * 克转盎司(四舍五入)
     * @param gStr
     * @return
     */
    public static String g_to_oz(double gStr) {

        return String.format("%.1f", gStr / 28.3);
    }


    /**
     * 盎司转克(四舍五入)
     * @param ozStr
     * @return
     */
    public static String oz_to_g(String ozStr) {
        if (TextUtils.isEmpty(ozStr)) {
            return "";
        }

        double oz = Double.parseDouble(ozStr);

        return String.format("%.1f", oz * 28.3);
    }

    /**
     * 盎司转克(四舍五入)
     * @param oz
     * @return
     */
    public static String oz_to_g(double oz) {

        return String.format("%.1f", oz * 28.3);
    }


    /**
     * 进入登录页面
     * @param mContext
     */
    public static void enterLoginActivity(Context mContext) {
        //如果是简体中文,进入简体中文登录页面
//        if (Constants.LANGUAGE_CN.equals(SPUtils.get(mContext, SPUtils.SYSTEM_LANGUAGE_CODE, Constants.LANGUAGE_CN).toString())) {
//            LoginActivity.startLoginActivity(mContext);
//        }
//        //如果是繁体中文或英文，进入邮箱登录页面
//        else {
//            AccountLoginActivity.startAccountLoginActivity(mContext, AccountLoginActivity.EMAIL_LOGIN);
//        }
    }




    /**
     * 根据名称获取code
     */
    public static String getCounrtyOrCityCode(ArrayList<Country> list, String name) {

        String code = "";
        if (list != null && !TextUtils.isEmpty(name)) {
            for (int i = 0; i < list.size(); i++) {
                Country country = list.get(i);
                if (name.equals(country.getName())) {
                    code = country.getCode();
                    return code;
                }
                ArrayList<Province> provinces = country.getProvinceArrayList();
                if (provinces != null && provinces.size() > 0) {
                    for (int j = 0; j < provinces.size(); j++) {
                        Province province = provinces.get(j);
                        ArrayList<City> cities = province.getCityArrayList();
                        if (cities != null && cities.size() > 0) {
                            for (int g = 0; g < cities.size(); g++) {
                                City city = cities.get(g);
                                if (name.equals(city.getName())) {
                                    code = city.getCode();
                                    return code;
                                }
                            }
                        }
                    }
                }
            }

        }

        return code;
    }


    /**
     * 设置手机号码中间隐藏
     * @param mobile
     * @return
     */
    public static String setMobileNum(String mobile) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < mobile.length(); i++) {
            if (i >= 3 && i < 7) {
                stringBuffer.append("*");
            } else {
                stringBuffer.append(mobile.charAt(i));
            }
        }

        return stringBuffer.toString();
    }


    /**
     * bitmap转base64
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 计算图片的缩放值
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }


    /**
     * 格式化日期
     * @param date
     * @return
     */
    public static String formatDate(String date) {
        if (TextUtils.isEmpty(date)) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        return format.format(new Date(Long.parseLong(date)));
    }

    /**
     * 格式化日期
     * @param date
     * @return
     */
    public static String formatDateOnlyHour(String date) {
        if (TextUtils.isEmpty(date)) {
            return "";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            return format.format(new Date(Long.parseLong(date)));
        }catch (Exception e){
            e.printStackTrace();
            Log.e("AppUtils", "formatDateNoYear: 错误的时间戳" );
            return "";
        }
    }

    /**
     * 格式化日期
     * @param date
     * @return
     */
    public static String formatDateNoYear(String date) {
        if (date == null){
            return "";
        }
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

    /**
     * 由日期字符串获取毫秒数
     *
     * @param dateAndTimeStr 日期字符串 如:2015-11-10
     * @param formatStr      时间的格式 如:yyyy-MM-dd
     * @return
     */
    public static long dateAndTimeStrToTimeMillis(String dateAndTimeStr, String formatStr) {
        long mill = 0;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = formatter.parse(dateAndTimeStr, pos);
            mill = strtodate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mill;
    }


    /**
     * 判断是否登录
     * @param mContext
     * @param isShowDialog 是否显示弹窗，false不显示,true显示
     * @return
     */
    public static boolean isLogin(final Context mContext, boolean isShowDialog) {
        if (!(boolean) SPUtils.get(mContext, SPUtils.IS_LOGIN, false)) {
            if (!isShowDialog) {
                enterLoginActivity(mContext);
            } else {
               DialogUtils.showIsOkDialog(mContext, mContext.getResources().getString(R.string.yes),
                        mContext.getResources().getString(R.string.no), mContext.getResources().getString(R.string.is_login), "", new DialogListener() {
                    @Override
                    public void onComplete() {
                        enterLoginActivity(mContext);
                    }

                    @Override
                    public void onFail() {

                    }
                });
            }
            return false;
        }
        return true;

    }


    /**
     * 其他地方登录退出弹窗
     * @param context
     */
    public static void showReLoginDialog(Context context, int errorCode) {

        //判断如果activity已经关闭，不运行dialog
        if (((Activity) context).isFinishing())
            return;
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_re_login);
        //提示文字
        TextView tv_login_tips = dialog.findViewById(R.id.tv_login_tips);
        //如果是异地登录
        if (errorCode == ApiResponse.ERROR_CODE_INVALID) {
            tv_login_tips.setText(context.getString(R.string.forced_login_out_tips));
        }
        //如果是token自动失效
        else if (errorCode == ApiResponse.ERROR_CODE_TOKEN) {
            tv_login_tips.setText(context.getString(R.string.forced_login_token_error_tips));
        }

        Button close_btn =dialog.findViewById(R.id.close_btn);

        close_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    /**
     * 拼接缩略图的URL
     */
    public static String getImageUrl(String imageUrl) {
        String url = "";
        if (!TextUtils.isEmpty(url)) {
            String str = imageUrl.substring(0, imageUrl.lastIndexOf("."));
            String str2 = imageUrl.substring(imageUrl.lastIndexOf("."), imageUrl.length());
            url = str + "_200_200" + str2;
        }

        return url;
    }


    /**
     * 获取手机状态栏的高度
     * @param mContext
     * @return
     */
    public static int getStatusBarHeight(Context mContext) {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 设置状态栏的距离
     * 解决标题栏在顶部的距离问题，设置该控件的高度为手机标题栏的高度
     * @param mContext
     * @param tv
     */
    public static void setTextHeight(Context mContext, TextView tv) {

        if (tv != null) {
            //如果系统大于4.4版本
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                tv.setVisibility(View.VISIBLE);
                //获取手机状态栏的高度
                int height = getStatusBarHeight(mContext);
                ViewGroup.LayoutParams params = tv.getLayoutParams();
                params.height = height;
                params.width =ScreenUtils.getScreenWidth(mContext);
                tv.setLayoutParams(params);
            } else {
                tv.setVisibility(View.GONE);
            }
        }
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

    /**
     * 将分为单位的转换为元 （除100）
     */
    /**金额为分的格式 */
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";
    public static String changeF2Y(String amount) throws Exception {
        if(!amount.matches(CURRENCY_FEN_REGEX)) {
            throw new Exception("金额格式有误");
        }
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();
    }
}
