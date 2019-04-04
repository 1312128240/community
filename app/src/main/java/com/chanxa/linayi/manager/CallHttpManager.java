
package com.chanxa.linayi.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.chanxa.linayi.HttpClient.api.ApiResponse;
import com.chanxa.linayi.Interface.RequestFailListener;
import com.chanxa.linayi.Interface.RequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class CallHttpManager<T extends ApiResponse> extends BaseHttpManager {

    public static final String TAG = "request";
    //映射外网地址
    //    public static final String BASE_URL = "http://120.77.153.55:8080/linayi/";
     public static final String BASE_URL_IMAGE = "http://47.106.159.93:8082/linayi/images/";

       //本地地址
    //public static final String BASE_URL = "http://www.laykj.cn/wherebuyAPI/";
     public static final String BASE_URL = "http://192.168.0.6:8080/wherebuyAPI/";
    // public static final String BASE_URL="http://192.168.0.134:8080/linayi/";


    public static CallHttpManager mInstance;

    private Handler mDelivery;

    public CallHttpManager() {
        super();
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public static synchronized CallHttpManager getInstance() {
        if (mInstance == null) {
            mInstance = initInstance();
        }
        return mInstance;
    }

    public static CallHttpManager initInstance() {
        if (mInstance == null) {
            mInstance = new CallHttpManager();
        }
        return mInstance;
    }

    /**
     * 针对于key获取方法名
     * */
    private String getKeyName() {
        StackTraceElement[] temp = Thread.currentThread().getStackTrace();
        StackTraceElement a = temp[5];
        Log.d("method-----", a.getClassName() + ":" + a.getMethodName());
        return a.getMethodName();
    }

    /**
     * 请求ObjectPost
     * @param param 传入请求Map
     * @param tClass 传入返回对象
     * @param requestListener 传入回调接口
     * */
    public void postMapObject(Context context, String url, Map<String, Object> param, String sysParameter, Class<T> tClass, RequestListener requestListener, RequestFailListener requestFailListener) {
        String keyName = getKeyName();
        Map<String, Object> root = new HashMap<>();
        root.put(keyName, param);
        String json = "{\"SEARCH_BOOKS\":{\"accessToken\":\"LdVkkQaU+HkB7M4BkUE9hFivdmCkIKlLPXDN4xHcybU=\",\"code\":\"10\",\"pageSize\":12,\"currentPage\":1}}";
        postData(url, keyName, toJson(root), sysParameter, tClass, requestListener, requestFailListener);
    }

    /**
     * 请求ObjectPost
     * @param param 传入请求Map
     * @param tClass 传入返回对象
     * @param requestListener 传入回调接口
     * */
    public void postMapObject(String url, Object param, String TOKEN, Class<T> tClass, RequestListener requestListener, RequestFailListener requestFailListener) {
        String keyName = getKeyName();
        //Map<String, Object> root = new HashMap<>();
        //root.put(keyName, param);
        String json = "{\"SEARCH_BOOKS\":{\"accessToken\":\"LdVkkQaU+HkB7M4BkUE9hFivdmCkIKlLPXDN4xHcybU=\",\"code\":\"10\",\"pageSize\":12,\"currentPage\":1}}";
        postData(url, keyName, toJson(param), TOKEN, tClass, requestListener, requestFailListener);
    }

    /**
     * 请求ObjectPost
     * @param param 传入请求Map
     * */
    public ApiResponse postMapObject(String url, Map<String, Object> param, String sysParameter, Class<T> tClass) throws Exception {
        String keyName = getKeyName();
        Map<String, Object> root = new HashMap<>();
        root.put(keyName, param);
        return postDataEn(url, keyName, toJson(root), sysParameter, tClass);
    }

    /**
     * 请求ObjectPost
     * @param tClass 传入返回对象
     * @param requestListener 传入回调接口
     * */
    public void postFileObject(String url, ArrayList<File> fileList, String sysParameter, Class<T> tClass, RequestListener requestListener, RequestFailListener requestFailListener) {
        String keyName = getKeyName();
        postFileData(url, keyName, fileList, sysParameter, tClass, requestListener, requestFailListener);
    }


    /**
     * 请求ObjectPost
     * @param object 传入请求对象
     * @param tClass 传入返回对象
     * @param requestListener 传入回调接口
     * */
    public void postObject(String url, Object object, String sysParameter, Class<T> tClass, RequestListener requestListener, RequestFailListener requestFailListener) {
        postData(url, getKeyName(), toJson(object), sysParameter, tClass, requestListener, requestFailListener);
    }

    /**
     * post请求数据
     *
     * @param requestListener
     */
    private void postFileData(String url, final String jsonKey, ArrayList<File> fileList, String sysParameter, final Class<T> tClass, final RequestListener requestListener, final RequestFailListener requestFailListener) {
        String baseUrl = getAbsoluteUrl(url + jsonKey);
        Log.d(TAG, "请求参数: key--{" + baseUrl + "}--param--" );
        postFile(baseUrl,fileList, sysParameter, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.d(TAG, "请求结果:" + result);
                    JSONObject json = new JSONObject(result);
                    JSONObject jsonObject = json.optJSONObject(jsonKey);
                    final T entity = new Gson().fromJson(jsonObject.toString(), tClass);

                    if (entity == null) {
                        onHandlerFail(entity, requestListener);
                        return;
                    }

                    if (entity.isSuccess()) {
                        onHandlerComplete(entity, requestListener);
                    } else {
                        onHandlerFail(entity, requestListener);
                        requestFailListener.onRequsetfail(entity);

                        if (entity.isInvalid()) {

                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    onHandlerFail(null, requestListener);
                }

            }

            @Override
            public void onFailure(Call call, IOException e) {
                onHandlerFail(null, requestListener);
            }
        });
    }

    /**
     * 转json
     * @param object
     * */
    private String toJson(Object object) {
        return new Gson().toJson(object);
    }

    /**
     * post请求数据
     *
     * @param json
     * @param requestListener
     */
    private void postData(String url, final String jsonKey, String json, String TOKEN, final Class<T> tClass, final RequestListener requestListener, final RequestFailListener requestFailListener) {
//        if (!NetUtils.isNetwork(mContext, isShowProgressDialog)) {
//            return;
//        }

        //String baseUrl = getAbsoluteUrl(url + jsonKey);
        String baseUrl = getAbsoluteUrl(url);
        Log.d(TAG, "请求参数: key--{" + baseUrl + "}--param--" + json);
        post(baseUrl, json, TOKEN, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.d(TAG, "请求结果:" + result);
                    JSONObject json = new JSONObject(result);
                    //JSONObject jsonObject = json.optJSONObject(jsonKey);
                    //final T entity = new Gson().fromJson(jsonObject.toString(), tClass);
                    final T entity = new Gson().fromJson(json.toString(), tClass);
//                    if (entity != null) {
//                        Log.d(TAG, "转化结果:"+entity.getRespCode());
//                        Log.d(TAG, "转化结果:"+entity.isSuccess2());
//                    }else {
//                        Log.d(TAG, "转化失败:" + result);
//                    }

                    if (entity == null) {
                        //onHandlerFail(entity, requestListener);
                        requestListener.onFailure(null);
                        return;
                    }
                    if (entity.isSuccess2()) {
                        //onHandlerComplete(entity, requestListener);
                        requestListener.onComplete(entity);
                    } else {
                        //onHandlerFail(entity, requestListener);
                        //requestFailListener.onRequsetfail(entity);
                        requestListener.onFailure(null);
                        if(entity.isInvalid()) {
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //onHandlerFail(null, requestListener);
                    requestListener.onFailure(null);
                }

            }

            @Override
            public void onFailure(Call call, IOException e) {
                onHandlerFail(null, requestListener);
            }
        });
    }


    /**
     * post请求数据
     *
     * @param json
     */
    private ApiResponse postDataEn(String url, final String jsonKey, String json, String sysParameter, final Class<T> tClass) throws Exception {

//        if (!NetUtils.isNetwork(mContext, isShowProgressDialog)) {
//            return;
//        }
        String baseUrl = getAbsoluteUrl(url + jsonKey);
        String result = postEn(baseUrl, json, sysParameter);
        JSONObject jsonOb = new JSONObject(result);
        JSONObject jsonObject = jsonOb.optJSONObject(jsonKey);
        final T entity = new Gson().fromJson(jsonObject.toString(), tClass);
        Log.d(TAG, "请求参数: key--{" + baseUrl + "}--param--" + json);
        Log.d(TAG, "请求结果:" + result);
        if (entity != null) {
            entity.setRequest(jsonObject.toString());
        }
        return entity;
    }

    /**
     * post请求数据
     *
     * @param json
     * @param requestListener
     */
    private void postDataList(final String jsonKey, String json, String sysParameter, final RequestListener requestListener) {

//        if (!NetUtils.isNetwork(mContext, isShowProgressDialog)) {
//            return;
//        }
        Log.d(TAG, "Incoming data: key--{" + jsonKey + "}--param--" + json);
        post(getAbsoluteUrl(jsonKey), json, sysParameter, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.d(TAG, "Request to return data:" + result);
                    JSONObject json = new JSONObject(result);
                    JSONObject jsonObject = json.optJSONObject(jsonKey);
                    Type type = new TypeToken<List<T>>() {
                    }.getType();
                    final T entity = new Gson().fromJson(jsonObject.toString(), type);
                    if (entity == null) {
                        onHandlerFail(entity, requestListener);
                        return;
                    }

                    if (entity.isSuccess()) {
                        onHandlerComplete((T) entity.getRows(), requestListener);
                    } else {
                        onHandlerFail(entity, requestListener);
                        if (entity.isInvalid()) {

                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    onHandlerFail(null, requestListener);
                }

            }

            @Override
            public void onFailure(Call call, IOException e) {
                onHandlerFail(null, requestListener);
            }
        });
    }


    public void onHandlerComplete(final T entity, final RequestListener requestListener) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                requestListener.onComplete(entity);
            }
        });
    }

    public void onHandlerFail(final T entity, final RequestListener requestListener) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                requestListener.onFailure(entity);
            }
        });
    }


    public void getData(String url, final String jsonKey, String json, final RequestListener requestListener) {
        getEnqueue(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onHandlerFail(null, requestListener);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();

                    Log.e("Request to return data:", result);
                    JSONObject json = new JSONObject(result);
                    JSONObject jsonObject = json.optJSONObject(jsonKey);
                    final ApiResponse entity = new Gson().fromJson(jsonObject.toString(), ApiResponse.class);
//                    if (entity.isSuccess()) {
//                        onHandlerComplete(entity, requestListener);
//                    } else {
//                        onHandlerFail(entity, requestListener);
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    onHandlerFail(null, requestListener);
                }
            }
        });

    }

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public String uploadImg(String imageUrl, String token, String suffix) throws IOException {

        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File f = new File(imageUrl);
        if (f != null) {
            builder.addFormDataPart("base64", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
        }
        //添加其它信息
        builder.addFormDataPart("accessToken", token);
        builder.addFormDataPart("suffix", suffix);
//        builder.addFormDataPart("mapY",SharedInfoUtils.getLatitude());
//        builder.addFormDataPart("name",SharedInfoUtils.getUserName());


        MultipartBody requestBody = builder.build();
        //构建请求
        Request request = new Request.Builder()
                .url(BASE_URL_IMAGE)//地址
                .post(requestBody)//添加请求体
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();

    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
