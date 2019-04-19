package com.chanxa.linayi.HttpClient.api;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @description: 创建Retrofit实例，提供创建接口Service的窗口
 * @author: miao
 * @time: 2018-09-25
 */
public class ApiRequest {

    //请求HOST
  // public final static String REQUEST_HOST = "http://www.laykj.cn/wherebuyAPI/";      //外网
    public final static String REQUEST_HOST="http://192.168.0.6:8080/wherebuyAPI/";     //2019/3/27内网
 //  public static final String REQUEST_HOST="http://192.168.0.134:8080/linayi/";  //高雄内网
    private Retrofit retrofit;
    private static ApiRequest apiRequestInstance;

    /**
     * 获取ApiRequest单例
     */
    public static ApiRequest getApiRequestInstance(){
        if(apiRequestInstance == null){
            apiRequestInstance = new ApiRequest();
        }
        return apiRequestInstance;
    }

    /**
     * 构造，创建Retrofit
     */
    private ApiRequest(){
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(logInterceptor)
                .addInterceptor(new RequestInterceptor())
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(REQUEST_HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 创建APIService实例
     */
    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}
