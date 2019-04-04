package com.chanxa.linayi.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/05/03.
 */
public class BaseHttpManager {

    public static final String TOKEN = "";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    protected OkHttpClient mClient = null;

    private String mAppLanguageCode = "";

    public void setAppLanguageCode(String appLanguageCode) {
        mAppLanguageCode = appLanguageCode;
    }


    public BaseHttpManager() {
        mClient = new OkHttpClient();
    }

    protected void post(String url, String json, String TOKEN, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);

        /**
         * 打印请求内容
         */
        System.out.println("mzzzzzzzz BaseHttpManager : url = "+url);
        System.out.println("mzzzzzzzz BaseHttpManager : TOKEN = "+TOKEN);
        System.out.println("mzzzzzzzz BaseHttpManager : body = "+json);


        Request request = new Request.Builder()
                .url(url)
                .addHeader("accessToken", TOKEN)
                //.addHeader("language", mAppLanguageCode)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                //.addHeader("agreement_params", systemParameter)
                .addHeader("Content-Length", String.valueOf(json.getBytes().length))
                .post(body)
                .build();
        mClient.newCall(request).enqueue(callback);
    }

    public String postEn(String url, String json, String systemParameter) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("TOKEN", TOKEN)
                .addHeader("language", mAppLanguageCode)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("agreement_params", systemParameter)
                .addHeader("Content-Length", String.valueOf(json.getBytes().length))
                .post(body)
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 上传文件
     * @param url
     * @param fileList
     * @param systemParameter
     * @param callback
     */
    protected void postFile(String url, ArrayList<File> fileList, String systemParameter, Callback callback) {
        try {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
//            builder.addFormDataPart(jsonKey, jsonObject2.toString());
            if (fileList != null) {
                for (File file : fileList) {
                    RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                    String fileName = file.getName();
                    builder.addFormDataPart("file", fileName, fileBody);
                }
            }
            RequestBody body = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("TOKEN", TOKEN)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .addHeader("agreement_params", systemParameter)
//                    .addHeader("Content-Length", String.valueOf(json.getBytes().length))
                    .post(body)
                    .build();
            mClient.newCall(request).enqueue(callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }

    protected void getEnqueue(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        mClient.newCall(request).enqueue(callback);
    }
}
