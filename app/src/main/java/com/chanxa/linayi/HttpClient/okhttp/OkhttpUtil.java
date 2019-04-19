package com.chanxa.linayi.HttpClient.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.chanxa.linayi.App;
import com.chanxa.linayi.HttpClient.api.ApiRequest;
import com.chanxa.linayi.tools.SPUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2019/4/4.
 */

public class OkhttpUtil {


    public static volatile OkhttpUtil mIntance=null;
    private final Handler mHandler;
    private final OkHttpClient okHttpClient;
    private Object tag;
    private final Gson gson;

    public static OkhttpUtil getmIntance(){
        if(mIntance==null){
            synchronized (OkhttpUtil.class){
                if(mIntance==null){
                    mIntance=new OkhttpUtil();
                }
            }
        }
        return mIntance;
    }

    public OkhttpUtil(){
        okHttpClient = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
        gson = new Gson();
    }


    /**
     * post异步,json传递参数
     */

    public void PostAsync(String url, Map<String,String> map, final ResultCallback resultCallback ){

        String jsonMap=new Gson().toJson(map);
        MediaType type=MediaType.parse("application/json; charset=utf-8");
        RequestBody body=RequestBody.create(type,jsonMap);

        final Request request=new Request.Builder()
                .url(ApiRequest.REQUEST_HOST+url)
                .addHeader("Content-Type","application/json; charset=utf-8")
                .addHeader("accessToken", SPUtils.getAccessToken(App.getInstance()))
                .post(body)
                .tag(tag)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                      resultCallback.onFailure(call,e);
                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {

                   if(response.code()==200){
                       final String jsonstr=response.body().string();
                       ;
                       mHandler.post(new Runnable() {
                           @Override
                           public void run() {
                               resultCallback.onResponse(call, gson.fromJson(jsonstr,resultCallback.mType));
                           }
                       });
                   }
            }
        });

    }

    public OkhttpUtil seTag(Object tag){
        Log.e("设置的tag",tag+"");
        this.tag=tag;
        return mIntance;
    }


    public void cancelTag(Object tag) {
        Log.e("取消的tag是",tag+"");
        for (Call call : okHttpClient.dispatcher().queuedCalls()) {

            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
        for (Call call : okHttpClient.dispatcher().runningCalls()) {

            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
    }


}
