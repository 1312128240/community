package com.chanxa.linayi.HttpClient.api;

import com.chanxa.linayi.Interface.RequestCallBack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @description: 自定义enqueue(Callback<T> callback)的Callback
 * @author: miao
 * @time: 2018-09-26
 */
public class ApiCallBack<T> implements Callback<T>{

    private RequestCallBack<T> requestCallBack;

    public ApiCallBack(RequestCallBack<T> requestCallBack){
        this.requestCallBack = requestCallBack;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(requestCallBack == null){
            return;
        }
        //通过返回的code，判断请求数据是否正常
        if(response.body() != null){
            if(((ApiResponse)response.body()).isSuccess2()){
                requestCallBack.onSuccess(response.body());
            }else {
                requestCallBack.onError(((ApiResponse)response.body()).getErrorMsg());
            }
        }else {
            requestCallBack.onFailure();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if(requestCallBack != null){
            requestCallBack.onFailure();
        }
    }
}
