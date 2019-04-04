package com.chanxa.autoupdatelibrary.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.chanxa.autoupdatelibrary.R;
import com.chanxa.autoupdatelibrary.model.UpdateEntity;
import com.chanxa.autoupdatelibrary.widget.CommonProgressDialog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import static android.os.Build.VERSION_CODES.M;


public class AutoUpDateUtils {
    //广播接受者
    private static MyReceiver receiver;
    private static InstallReceiver installReceiver;
    private static AutoUpDateUtils autoUpDateUtils;

    //定义一个展示下载进度的进度条
    private static CommonProgressDialog progressDialog;

    private static Context mContext;

    //检查更新的url
    private static String checkUrl;

    //是否开启日志输出
    private static boolean showLog = true;
    //设置请求方式
    private static int requestMethod = Builder.METHOD_GET;

    //私有化构造方法
    private AutoUpDateUtils() {

    }

    /**
     * 检查更新
     */
    public void check() {
        if (TextUtils.isEmpty(checkUrl)) {
            throw new RuntimeException("checkUrl is null. You must call init before using the cretin checking library.");
        } else {
            new DownDataAsyncTask().execute();
        }
    }

    /**
     * 初始化url
     * @param builder
     */
    public static void init(Builder builder) {
        checkUrl = builder.baseUrl;
        showLog = builder.showLog;
        requestMethod = builder.requestMethod;
    }

    /**
     * getInstance()
     *
     * @param context
     * @return
     */
    public static AutoUpDateUtils getInstance(Context context) {
        mContext = context;
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.MY_RECEIVER");
        //注册
        context.registerReceiver(receiver, filter);

        installReceiver = new InstallReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.MY_INSTALL_APK");
        context.registerReceiver(installReceiver, intentFilter);

        requestPermission(null);
        if (autoUpDateUtils == null) {
            autoUpDateUtils = new AutoUpDateUtils();
        }
        return autoUpDateUtils;
    }


    /**
     * 取消广播的注册
     */
    public void destroy() {
        try {
            //不要忘了这一步
            if (mContext != null && intent != null)
                mContext.stopService(intent);
            if (mContext != null && receiver != null)
                mContext.unregisterReceiver(receiver);
            if (mContext != null && installReceiver != null) {
                mContext.unregisterReceiver(installReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步任务下载数据
     */
    class DownDataAsyncTask extends AsyncTask<String, Void, UpdateEntity> {

        @Override
        protected UpdateEntity doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient();
                //获取请求对象
                Request request = new Request.Builder().url(checkUrl).build();
                //获取响应体
                ResponseBody body = client.newCall(request).execute().body();
                String result = body.string();
                if (showLog) {
                    if (TextUtils.isEmpty(result.toString())) {
                        Log.e("请求版本更新", "自动更新library返回的数据为空，" +
                                "请检查请求方法是否设置正确，默认为post请求，再检查地址是否输入有误");
                    } else {
                        Log.e("请求版本更新", "自动更新library返回的数据：" + result);
                    }
                }
                return new Gson().fromJson((new JSONObject(result.toString())).optString("android"), UpdateEntity.class);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                throw new RuntimeException("json解析错误，" +
                        "请按照library中的UpdateEntity所需参数返回数据，json必须包含UpdateEntity所需全部字段");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(UpdateEntity entity) {
            if (entity != null) {
                if (entity.versionCode > AppUtils.getVersionCode(mContext)) {
                    showUpdateDialog(entity, false);
                }
            } else {
                Toast.makeText(mContext, mContext.getString(R.string.update_network_error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 显示更新对话框
     *
     * @param entity
     */
    private void showUpdateDialog(final UpdateEntity entity, boolean isForceUpdate) {
        //获取版本更新内容
        String updateLog = entity.update_content;
        if (TextUtils.isEmpty(updateLog))
            updateLog = mContext.getString(R.string.update_new_version_tips);
        //获取版本号
        String versionName = entity.versionName;
        if (TextUtils.isEmpty(versionName)) {
            versionName = "1.1";
        }

        String title = mContext.getString(R.string.update_find_new_version) + "V" + versionName;

        DialogUtils.showIsOkDialog(mContext, mContext.getString(R.string.update_now), mContext.getString(R.string.update_next_time), title, updateLog, isForceUpdate, new DialogListener() {
            @Override
            public void onComplete() {
                startUpdate(entity);
            }

            @Override
            public void onFail() {

            }
        });
    }

    private static int PERMISSON_REQUEST_CODE = 2;

    @TargetApi(M)
    private static void requestPermission(final UpdateEntity entity) {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 第一次请求权限时，用户如果拒绝，下一次请求shouldShowRequestPermissionRationale()返回true
            // 向用户解释为什么需要这个权限
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(mContext)
                        .setMessage(mContext.getString(R.string.update_apply_storage_rights))
                        .setPositiveButton(mContext.getString(R.string.update_btn_confirm), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //申请相机权限
                                ActivityCompat.requestPermissions((Activity) mContext,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSON_REQUEST_CODE);
                            }
                        })
                        .show();
            } else {
                //申请相机权限
                ActivityCompat.requestPermissions((Activity) mContext,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSON_REQUEST_CODE);
            }
        } else {
            if (entity != null) {
                if (!TextUtils.isEmpty(entity.download_url)) {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        try {
                            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                            final String fileName = filePath + "/" + AppUtils.getPackgeName(mContext) + "-v" + entity.versionName + ".apk";
                            final File file = new File(fileName);
                            //如果不存在
                            if (!file.exists()) {
                                //检测当前网络状态
                                if (!NetWorkUtils.getCurrentNetType(mContext).equals("wifi")) {
                                    DialogUtils.showIsOkDialog(mContext, mContext.getString(R.string.update_btn_confirm), mContext.getString(R.string.update_btn_cancel), mContext.getString(R.string.update_tips), mContext.getString(R.string.update_not_wifi_tips), new DialogListener() {
                                        @Override
                                        public void onComplete() {
                                            createFileAndDownload(file, entity.download_url, entity.versionName, Long.parseLong(entity.size));
                                        }

                                        @Override
                                        public void onFail() {

                                        }
                                    });
                                } else {
                                    createFileAndDownload(file, entity.download_url, entity.versionName, Long.parseLong(entity.size));
                                }
                            } else {
                                if (file.length() == Long.parseLong(entity.size)) {
                                    AppUtils.installApkFile(mContext, file, mContext.getString(R.string.app_name), entity.versionName);
                                    return;
                                } else {
                                    try {
                                        file.delete();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (!NetWorkUtils.getCurrentNetType(mContext).equals("wifi")) {
                                        DialogUtils.showIsOkDialog(mContext, mContext.getString(R.string.update_btn_confirm), mContext.getString(R.string.update_btn_cancel), mContext.getString(R.string.update_tips), mContext.getString(R.string.update_not_wifi_tips), new DialogListener() {
                                            @Override
                                            public void onComplete() {
                                                createFileAndDownload(file, entity.download_url, entity.versionName, Long.parseLong(entity.size));
                                            }

                                            @Override
                                            public void onFail() {

                                            }
                                        });
                                    } else {
                                        createFileAndDownload(file, entity.download_url, entity.versionName, Long.parseLong(entity.size));
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(mContext, R.string.update_no_sd_card, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, R.string.update_download_url_empty, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private static Intent intent;

    //创建文件并下载文件
    private static void createFileAndDownload(File file, String downurl, String versionName, long apkSize) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            if (!file.createNewFile()) {
                Toast.makeText(mContext, R.string.update_file_create_fail, Toast.LENGTH_SHORT).show();
            } else {
                //文件创建成功
                intent = new Intent(mContext, DownloadService.class);
                intent.putExtra("downUrl", downurl);
                intent.putExtra("versionName", versionName);
                intent.putExtra("apkSize", apkSize);
                mContext.startService(intent);

                //显示dialog
                progressDialog = new CommonProgressDialog(mContext);
                //设置进度条对话框为水平方向
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                //进度最大值
                progressDialog.setMax(100);
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始更新操作
     */
    public void startUpdate(UpdateEntity data) {
        requestPermission(data);
    }

    /**
     * 广播接收器
     *
     * @author user
     */
    private static class MyReceiver extends DownloadReceiver {
        @Override
        protected void downloadComplete() {
            try {
                if (mContext != null && intent != null) {
                    mContext.stopService(intent);
                }
                if (mContext != null && receiver != null) {
                    mContext.unregisterReceiver(receiver);
                }
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void downloading(int progress) {
            if (progressDialog != null) {
                progressDialog.setProgress(progress);
            }
        }

        @Override
        protected void downloadFail(String e) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            Toast.makeText(mContext, R.string.update_download_fail, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 安装apk广播
     */
    private static class InstallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if ("android.intent.action.MY_INSTALL_APK".equals(intent.getAction())) {
                Bundle bundle = intent.getExtras();
                String path = bundle.getString("path");
                final String versionName = bundle.getString("versionName");
                final File file = new File(path);
                if (file.exists()) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mContext.unregisterReceiver(installReceiver);
                            AppUtils.installApkFile(mContext, file, mContext.getString(R.string.app_name), versionName);
                        }
                    });
                }
            }

        }
    }

    //建造者模式
    public static final class Builder {
        private String baseUrl;
        //POST方法
        public static final int METHOD_POST = 3;
        //GET方法
        public static final int METHOD_GET = 4;
        //显示log日志
        private boolean showLog;
        //设置请求方式
        private int requestMethod = METHOD_POST;

        public final AutoUpDateUtils.Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public final AutoUpDateUtils.Builder showLog(boolean showLog) {
            this.showLog = showLog;
            return this;
        }

        public final AutoUpDateUtils.Builder setRequestMethod(int requestMethod) {
            this.requestMethod = requestMethod;
            return this;
        }

        public final Builder build() {
            return this;
        }
    }


}
