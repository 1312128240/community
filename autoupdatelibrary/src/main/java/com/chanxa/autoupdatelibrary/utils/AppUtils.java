package com.chanxa.autoupdatelibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

public class AppUtils {

    /**
     * 获取包名
     *
     * @param context
     * @return
     */
    public static String getPackgeName(Context context) {
        String packName = "";
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            packName = packInfo.packageName;
        }
        return packName;
    }

    /**
     * 获取版本号
     * @param context
     * @return
     */
    private static String getVersionName(Context context) {
        String versionName = "";
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            versionName = packInfo.versionName;
        }
        return versionName;
    }

    /**
     * 获得apk版本升级码
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            versionCode = packInfo.versionCode;
        }
        return versionCode;
    }


    /**
     * 获得apkinfo
     * @param context
     * @return
     */
    public static PackageInfo getPackInfo(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo;
    }


    /**
     * 安装apk文件
     * @param context
     * @param file 安装文件
     * @param appName app名称
     * @param versionName 版本号
     */
    public static void installApkFile(final Context context, final File file, String appName, String versionName) {
        DialogUtils.showInstallApkDialog(context, appName, versionName, new DialogListener() {
            @Override
            public void onComplete() {
                //安装
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //兼容7.0
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                } else {
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                    context.startActivity(intent);
                }
            }

            @Override
            public void onFail() {

            }
        });

    }


}
