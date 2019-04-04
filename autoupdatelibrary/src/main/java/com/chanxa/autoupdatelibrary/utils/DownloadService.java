package com.chanxa.autoupdatelibrary.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.Toast;

import com.chanxa.autoupdatelibrary.R;

import java.io.File;

import okhttp3.Request;


public class DownloadService extends Service {

    private Context mContext;
    private String downUrl;
    private String versionName;
    private long apkSize;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        downUrl = intent.getStringExtra("downUrl");
        versionName = intent.getStringExtra("versionName");
        apkSize = intent.getLongExtra("apkSize", 0);
        if (!TextUtils.isEmpty(downUrl)) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                final String fileName = filePath + "/" + AppUtils.getPackgeName(this) + "-v" + versionName + ".apk";
                //下载apk
                DownloadManager.downloadApk(downUrl, fileName, new DownloadManager.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        //发送特定action的广播,下载中出错io
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.MY_RECEIVER");
                        intent.putExtra("type", "err");
                        intent.putExtra("err", e.toString());
                        sendBroadcast(intent);
                    }

                    @Override
                    public void onResponse(Object response) {

                        try {
                            File file = new File(response.toString());
                            if (file != null && file.length() == apkSize) {
                                //发送广播通知安装
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.MY_INSTALL_APK");
                                intent.putExtra("path", response.toString());
                                intent.putExtra("versionName", versionName);
                                sendBroadcast(intent);
                            } else {
                                Toast.makeText(mContext, R.string.update_network_error_download_fail, Toast.LENGTH_SHORT).show();
                            }
                            stopSelf(); //停止服务

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onProgress(double total, double current) {
                        //发送特定action的广播,更新下载进度条
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.MY_RECEIVER");
                        intent.putExtra("type", "doing");
                        intent.putExtra("progress", (int) ((current * 100) / total));
                        sendBroadcast(intent);
                    }

                });
            } else {
                Toast.makeText(mContext, R.string.update_no_sd_card, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, R.string.update_download_url_empty, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DownloadManager.cancleDown();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}  