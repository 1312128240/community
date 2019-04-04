
package com.chanxa.linayi.tools;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class SPUtils {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "Choor";
    public final static String IS_FIRST = "isOne";//1第一次，2不是
    public final static String IS_GUIDE = "isGuide";//是否已经引导，true已经引导
    public final static String USER_MOBILE = "user_mobile";
    public final static String USER_EMAIL = "user_email";
    public final static String PUSH_TOKEN = "pushToken";
    public final static String IS_LOGIN = "isLogin";//true已登录，false未登录
    public final static String PASSWORD = "password";
    public final static String TAG_CACHE_FILE_NAME = "tagCacheFileName";
    public final static String IS_DEBUG_MODE = "isDebugMode";
    public final static String IS_NEW_VERSION = "isNewVersion";
    public final static String LANGUAGE_CODE = "language_code";//语言code,简体CN,繁体中文TW,英文EN
    public final static String SYSTEM_LANGUAGE_CODE = "system_language_code";//系统语言code,简体CN,繁体中文TW,英文EN
    public final static String WEIGHT_UNIT = "weight_unit";//重量单位code,克gram，盎司oz
    public final static String TEMPERATURE_UNIT = "temperature_unit";//温度单位code,摄氏度celsius，华氏度fahrenheit
    public final static String VOICE_REMIND_SWITCH = "voice_remind_switch";//声音提醒开关，1开，2关
    public final static String IS_LOGIN_OUT = "is_login_out";//是否退出登录,true退出登录，false不是
    public final static String ACCESSTOKEN = "accessToken";//登录令牌
    public final static String IS_TODAY = "is_today";
    public final static String IS_FIRST_MAIN = "is_first_main";
    public final static String IS_FIRST_PUSH = "is_first_push";
    public final static String DEVICE_UP_POWER = "device_up_power"; //电量相关
    public final static String IS_TIPS_TODAY = "is_tips_today";//电量提示相关，记录弹过的日期，一天只弹一次电量
    public final static String USER_ID = "user_id";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        try {
            if (defaultObject instanceof String) {
                return sp.getString(key, (String) defaultObject);
            } else if (defaultObject instanceof Integer) {
                return sp.getInt(key, (Integer) defaultObject);
            } else if (defaultObject instanceof Boolean) {
                return sp.getBoolean(key, (Boolean) defaultObject);
            } else if (defaultObject instanceof Float) {
                return sp.getFloat(key, (Float) defaultObject);
            } else if (defaultObject instanceof Long) {
                return sp.getLong(key, (Long) defaultObject);
            }
        } catch (Exception e) {
            return defaultObject;
        }
        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }

    public static boolean isLogin(Context context) {
        return (boolean) get(context, IS_LOGIN, false);
    }

    public static void setIsLogin(Context context, boolean isLogin) {
        put(context, IS_LOGIN, isLogin);
    }

    public static String getMobile(Context context) {
        return (String) get(context, USER_MOBILE, "0");
    }

    public static void setMobile(Context context, String mobile) {
        put(context, USER_MOBILE, mobile);
    }

    public static String getPassword(Context context) {
        return (String) get(context, PASSWORD, "0");
    }

    public static void setPassword(Context context, String pwd) {
        put(context, PASSWORD, pwd);
    }

    public static boolean getIsNewVersion(Context context) {
        String isLogin = (String) get(context, IS_NEW_VERSION, "0");
        return isLogin.equals("1");
    }

    public static void setIsNewVersion(Context context, boolean isNewVersion) {
        put(context, IS_NEW_VERSION, isNewVersion ? "1" : "0");
    }

    public static boolean getIsDebugMode(Context context) {
        String isLogin = (String) get(context, IS_DEBUG_MODE, "0");
        return isLogin.equals("1");
    }

    public static void setIsDebugMode(Context context, boolean isDebug) {
        put(context, IS_DEBUG_MODE, isDebug ? "1" : "0");
    }

    //获取App语言
    public static String getLanguageCode(Context context) {
        return (String) get(context, LANGUAGE_CODE, "ZH");
    }

    public static String getWeightUnit(Context context) {
        return (String) get(context, WEIGHT_UNIT, "g");
    }

    public static String getTemperature(Context context, String de) {
        return (String) get(context, TEMPERATURE_UNIT, de);
    }

    public static String getIsToday(Context context, String de) {
        return (String) get(context, IS_TODAY, de);
    }

    public static boolean getIsFirstMain(Context context) {
        boolean firstMain = (boolean) get(context, IS_FIRST_MAIN, true);
        return firstMain;
    }

    public static void setIsFirstMain(Context context, boolean firstMain) {
        put(context, IS_FIRST_MAIN, firstMain);
    }

    public static boolean getIsFirstPush(Context context) {
        boolean firstPush = (boolean) get(context, IS_FIRST_PUSH, true);
        return firstPush;
    }

    public static void setIsFirstPush(Context context, boolean isFirstPush) {
        put(context, IS_FIRST_PUSH, isFirstPush);
    }

    public static String getSystemLanguageCode(Context context) {
        return (String) get(context, SYSTEM_LANGUAGE_CODE, "ZH");
    }

    public static void setCommunityAccountId (Context context, String user_id){
        put(context, "communityAccountId", user_id);
    }
    public static String getCommunityAccountId(Context context){
        return (String) get(context, "communityAccountId", "");
    }

    public static void setCommunityId (Context context, String user_id){
        put(context, "communityId", user_id);
    }
    public static String getCommunityId(Context context){
        return (String) get(context, "communityId", "");
    }

    public static void setCommunityName (Context context, String user_id){
        put(context, "communityName", user_id);
    }
    public static String getCommunityName(Context context){
        return (String) get(context, "communityName", "");
    }

    public static void setAccessToken (Context context, String user_id){
        put(context, "accessToken", user_id);
    }
    public static String getAccessToken(Context context){
        return (String) get(context, "accessToken", "");
    }
}
