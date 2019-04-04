package com.chanxa.linayi.Repository;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.chanxa.linayi.App;
import com.chanxa.linayi.HttpClient.api.ApiResponse;
import com.chanxa.linayi.Interface.RequestFailListener;
import com.chanxa.linayi.Interface.RequestListener;
import com.chanxa.linayi.manager.CallHttpManager;
import com.chanxa.linayi.manager.LocalManager;
import com.chanxa.linayi.tools.AppUtils;
import com.chanxa.linayi.tools.SPUtils;
import com.chanxa.linayi.tools.ToastUtil;
import com.chanxa.linayi.uis.login.LoginActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;


/**
 * Created by user1 on 2017/1/5.
 */

public class BaseRepository {

    protected CallHttpManager http;
    protected LocalManager local;
    protected Context context;
    private String url;

    public BaseRepository() {
        http = CallHttpManager.getInstance();
        local = LocalManager.getInstance();
//        http.setAppLanguageCode(SPUtils.getLanguageCode(ChookrApplication.getInstance()));
    }

    public BaseRepository(Context context) {
        this.context = context;
        http = CallHttpManager.getInstance();
        local = LocalManager.getInstance();
//        http.setAppLanguageCode(SPUtils.getLanguageCode(ChookrApplication.getInstance()));
    }

    public void setUrl(String url) {
        this.url = url;
    }

   public void post(Map<String, Object> param, Class<?> tClass, final RequestListener requestListener) {

        if (App.getInstance().getIsLocal()) {
            //成功
            requestListener.onComplete(local.getLocalData(context, tClass));

        } else {
            //失败
            AppUtils.isNetwork(context, true);

            String TOKEN = (String) SPUtils.get(context,SPUtils.ACCESSTOKEN,"");
            http.postMapObject(url, param, TOKEN, tClass, requestListener, new RequestFailListener() {
                @Override
                public void onRequsetfail(ApiResponse result) {
                    //显示后台的错误信息
                    mHandler.obtainMessage(1, result).sendToTarget();
                }
            });
            
        }

    }

    public void post(Object param, Class<?> tClass, RequestListener requestListener) {
        if (App.getInstance().getIsLocal()) {
            //TODO 调用本地
            requestListener.onComplete(local.getLocalData(context, tClass));
        } else {
            http.postMapObject(url, param, AppUtils.getSystemInfo(context), tClass, requestListener, new RequestFailListener() {
                @Override
                public void onRequsetfail(ApiResponse result) {
                    //显示后台的错误信息
                    mHandler.obtainMessage(1, result).sendToTarget();
                }
            });
        }
    }

    public void postFile(ArrayList<File> fileList, Class<?> tClass, RequestListener requestListener) {
        if (App.getInstance().getIsLocal()) {
            //调用本地
            requestListener.onComplete(local.getLocalData(context, tClass));
        } else {
            http.postFileObject(url, fileList, AppUtils.getSystemInfo(context), tClass, requestListener, new RequestFailListener() {
                @Override
                public void onRequsetfail(ApiResponse result) {
                    //显示后台的错误信息
                    mHandler.obtainMessage(1, result).sendToTarget();
                }
            });
        }
    }

    public ApiResponse post(Map<String, Object> param, Class<?> tClass) throws Exception {
        ApiResponse entity = http.postMapObject(url, param, AppUtils.getSystemInfo(context), tClass);
        if (!entity.isSuccess()) {
            mHandler.obtainMessage(1, entity).sendToTarget();
            return null;
        }
        return entity;
    }


 Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                ApiResponse apiResponse = (ApiResponse) msg.obj;
                if (apiResponse != null && context != null) {
                    //如果是异地登录
                    if (apiResponse.getErrCode() == ApiResponse.ERROR_CODE_INVALID
                            || apiResponse.getErrCode() == ApiResponse.ERROR_CODE_TOKEN) {

                    } else {
                        if (!apiResponse.getErrMsg().endsWith("登陆失效！")) {

                            if (apiResponse.getErrCode() == 2008){

                            }else {
                                ToastUtil.showShort(context, apiResponse.getErrMsg());
                            }
                            Log.e("BaseRepository", "handleMessage: " + apiResponse.getErrMsg());
                        } else {
                            ToastUtil.showShort(context, "登陆失效！请重新登录");
//                            记住未登录
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    SPUtils.put(context, SPUtils.IS_LOGIN, false);
                                    //记录是退出登录
                                    SPUtils.put(context, SPUtils.IS_LOGIN_OUT, true);
                                    Intent intent = new Intent(context, LoginActivity.class);
                                    context.startActivity(intent);
                                    for (int i = 0; i < App.getInstance().store.size(); i++) {
                                        Activity activity = App.getInstance().store.get(i);
                                        if (activity instanceof LoginActivity){

                                        }else {
                                            activity.finish();
                                        }
                                    }
                                }
                            }, 500);
                        }
                    }
                }
            }

        }
    };

}
