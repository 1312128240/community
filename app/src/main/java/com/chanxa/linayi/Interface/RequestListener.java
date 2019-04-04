
package com.chanxa.linayi.Interface;


import com.chanxa.linayi.HttpClient.api.ApiResponse;

public interface RequestListener<T> {

    void onComplete(T result);

    void onFailure(ApiResponse result);
}