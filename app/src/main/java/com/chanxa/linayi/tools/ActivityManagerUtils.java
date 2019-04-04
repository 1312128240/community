package com.chanxa.linayi.tools;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/3/28.
 */

public class ActivityManagerUtils {
    private static ActivityManagerUtils mActivityManagerUtils;
    private List<Activity> activities = new ArrayList<Activity>();

    static {
        mActivityManagerUtils = new ActivityManagerUtils();
    }


    public static ActivityManagerUtils getInstance() {
        return mActivityManagerUtils;

    }

    //将activity添加进集合
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    //结束指定的activity
    public void finishActivity(Activity activity) {
        if (activity != null) {
            this.activities.remove(activity);
            activity.finish();
        }
    }

    //结束所有activity,退出程序
    public void exit() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivityclass(Class<?> cls) {
        if (activities != null&&cls!=null) {
            for (Activity activity : activities) {
                if (activity.getClass().equals(cls)) {
                    this.activities.remove(activity);
                    finishActivity(activity);
                    break;
                }
            }
        }

    }

}
