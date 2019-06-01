package com.chanxa.linayi.HttpClient;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.chanxa.linayi.App;
import com.chanxa.linayi.tools.SPUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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

    //请求HOST
     public final static String REQUEST_HOST = "http://www.laykj.cn/wherebuyAPI/";  //外网
   // public final static String REQUEST_HOST="http://192.168.0.6:8080/wherebuyAPI/";       //2019/3/27内网

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


    public OkhttpUtil seTag(Object tag){
        this.tag=tag;
        return mIntance;
    }



    /**
     * POST异步,json传递参数
     */
    public void PostAsync(String url, Map<String,String> map, final ResultCallback resultCallback ){
        final String jsonMap=new Gson().toJson(map);
        MediaType type=MediaType.parse("application/json; charset=utf-8");
        RequestBody body=RequestBody.create(type,jsonMap);

        final Request request=new Request.Builder()
                .url(REQUEST_HOST+url)
                .addHeader("Content-Type","application/json; charset=utf-8")
                .addHeader("accessToken", SPUtils.getAccessToken(App.getInstance()))
                .post(body)
                .tag(tag)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull final Call call,  @NonNull final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                      resultCallback.onFailure(call,e);
                    }
                });
            }

            @Override
            public void onResponse(@NonNull final Call call,@NonNull Response response) throws IOException {
                   if(response.code()==200&&response.body()!=null){
                           final String jsonstr=response.body().string();
                           mHandler.post(new Runnable() {
                               @Override
                               public void run() {
                                   if(!TextUtils.isEmpty(jsonstr)&&jsonstr!=null){
                                       resultCallback.onResponse(call, gson.fromJson(jsonstr,resultCallback.mType));
                                   }
                               }
                           });

                   }
            }
        });
    }


    /*
     *GET 异步请求,map拼接参数
     */
    public void GetAsyncData(String url, Map<String,String> paramMap, final ResultCallback resultCallback){
        //拼接参数
         StringBuilder buffer=new StringBuilder();
        if (paramMap != null && !paramMap.isEmpty()) {
            Iterator<Map.Entry<String, String>> iterator = paramMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                buffer.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
            buffer.toString();
        }
        Log.e("Get请求完整路径",REQUEST_HOST+url+buffer);

        Request request=new Request.Builder()
                .url(REQUEST_HOST+url+buffer)
                .get()
                .build();

        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull final Call call,@NonNull final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        resultCallback.onFailure(call,e);
                    }
                });
            }

            @Override
            public void onResponse(@NonNull final Call call,@NonNull final Response response) throws IOException {
                if(response.code()==200&&response.body()!=null){
                        final String jsonstr=response.body().string();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(!TextUtils.isEmpty(jsonstr)&&jsonstr!=null){
                                    resultCallback.onResponse(call, gson.fromJson(jsonstr,resultCallback.mType));
                                }

                            }
                        });


                }
            }
        });

    }


    /**
     * 上传文件
     */
    public void UploadFile(String url,String ordersId,String boxNo,File uriFile, final ResultCallback resultCallback){
        //创建RequestBody
        RequestBody fileBody=RequestBody.create(MediaType.parse("image/*"),uriFile);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("ordersId",ordersId+"")
                .addFormDataPart("boxNo",boxNo)
                .addFormDataPart("file", uriFile.getName(), fileBody)
                .build();

        final Request request = new Request.Builder()
                .url(REQUEST_HOST+url)
                .addHeader("Content-Type","application/json; charset=utf-8")
                .addHeader("accessToken", SPUtils.getAccessToken(App.getInstance()))
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull final Call call,@NonNull final IOException e) {
                 mHandler.post(new Runnable() {
                     @Override
                     public void run() {
                            resultCallback.onFailure(call,e);
                     }
                 });
            }

            @Override
            public void onResponse(@NonNull final Call call, @NonNull Response response) throws IOException {
                if(response.code()==200&&response.body()!=null){
                    final String jsonstr=response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(!TextUtils.isEmpty(jsonstr)&&jsonstr!=null){
                                resultCallback.onResponse(call, gson.fromJson(jsonstr,resultCallback.mType));
                            }
                        }
                    });
                }
            }
        });
    }




    /**
     * 取消网络请求
     * @param tag
     */
    public void cancelTag(Object tag) {
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
