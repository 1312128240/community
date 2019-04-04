package com.chanxa.linayi.manager;

import android.content.Context;
import android.util.Log;

import com.chanxa.linayi.HttpClient.api.ApiResponse;
import com.google.gson.Gson;

import org.apache.http.util.EncodingUtils;
import org.json.JSONObject;

import java.io.InputStream;


/**
 * Created by user1 on 2017/1/14.
 */

public class LocalManager<T extends ApiResponse> {


    public static final String INDEX = "json/";
    private static final String TAG = LocalManager.class.getSimpleName();

    public static LocalManager mInstance;


    public LocalManager() {
        super();
    }

    public static synchronized LocalManager getInstance() {
        if (mInstance == null) {
            mInstance = initInstance();
        }
        return mInstance;
    }

    public static LocalManager initInstance() {
        if (mInstance == null) {
            mInstance = new LocalManager();
        }
        return mInstance;
    }

    public T getLocalData(Context context, Class<T> tClass){
        String keyName = getKeyName();
        String result = readFile(context, keyName).replace("\r\n", "");
        Log.d(TAG, "Request to return data:" + result);
        JSONObject json = null;
        T entity = null;
        try {
            json = new JSONObject(result);
            JSONObject jsonObject = json.optJSONObject(keyName);
            entity = new Gson().fromJson(jsonObject.toString(), tClass);
        } catch (Exception e) {
            e.printStackTrace();
            return entity;
        }
        return entity;
    }

    private String readFile(Context context, String keyName){
        String res = null;
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(INDEX + keyName + ".json");
            int length = inputStream.available();
            byte [] buffer = new byte[length];

            inputStream.read(buffer);

            res = EncodingUtils.getString(buffer, "UTF-8");

            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return res;
        }
        return res;
    }

    /**
     * 针对于key获取方法名
     * */
    private String getKeyName(){
        StackTraceElement[] temp=Thread.currentThread().getStackTrace();
        StackTraceElement a = temp[5];
        Log.d("method-----", a.getClassName()+":"+a.getMethodName());
        return a.getMethodName();
    }
}
