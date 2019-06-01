package com.chanxa.linayi.tools.ApkInstall;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.chanxa.linayi.App;

public class DownLoadApk {

    public  void download(Context context, String url, String title, final String appName) {
        // 获取存储ID
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        long downloadId = sp.getLong(DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
        if (downloadId != -1L) { //第二次下载
            FileDownloadManager fdm = FileDownloadManager.getInstance(context);
            int status = fdm.getDownloadStatus(downloadId);
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                //启动更新界面
                Uri uri = fdm.getDownloadUri(downloadId);
                if (uri != null) {
                    if (compare(getApkInfo(context, uri.getPath()), context)) {
                        Log.e("下载成功", "通知样没有相同的包，并安装");
                        startInstall(context, uri);
                    } else {
                        Log.e("下载成功,", "删除通知栏相同的包,并安装");
                        fdm.getDownloadManager().remove(downloadId);
                        start(context, url, title, appName);
                    }
                }

            } else if (status == DownloadManager.STATUS_FAILED) {
//                Log.e(TAG, "apk下载失败");
//                start(context, url, title, appName);
                Toast.makeText(App.getInstance(), "apk下载失败.....", Toast.LENGTH_SHORT).show();
            } else if (status == DownloadManager.STATUS_RUNNING) {
                Toast.makeText(App.getInstance(), "通知栏中正在下载...", Toast.LENGTH_SHORT).show();
            }
        } else { //第一次下载
            start(context, url, title, appName);
        }
    }


    private  void start(Context context, String url, String title, String appName) {
        Toast.makeText(App.getInstance(),"在通知栏中查看下载进度",Toast.LENGTH_LONG).show();
        long id = FileDownloadManager.getInstance(context).startDownload(url, title, "下载完成后自动安装", appName);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor= sp.edit();
        editor.putLong(DownloadManager.EXTRA_DOWNLOAD_ID, id);
        editor.apply();
    }



    private void startInstall(Context context, Uri uri) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(install);
    }

    /**
     * 获取apk程序信息[packageName,versionName...]
     *
     * @param context Context
     * @param path    apk path
     */
    private PackageInfo getApkInfo(Context context, String path) {
        PackageInfo info = context.getPackageManager().getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            return info;
        }
        return null;
    }


    /**
     * 下载的apk和当前程序版本比较
     *
     * @param apkInfo apk file's packageInfo
     * @param context Context
     * @return 如果当前应用版本小于apk的版本则返回true
     */
    private  boolean compare(PackageInfo apkInfo, Context context) {
        if (apkInfo == null) {
            return false;
        }
        String localPackage = context.getPackageName();
        if (apkInfo.packageName.equals(localPackage)) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(localPackage, 0);
                if (apkInfo.versionCode > packageInfo.versionCode) {
                    return true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
