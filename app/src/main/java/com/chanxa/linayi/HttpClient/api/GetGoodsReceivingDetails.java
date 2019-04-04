package com.chanxa.linayi.HttpClient.api;



import com.chanxa.linayi.Interface.RequestCallBack;
import com.chanxa.linayi.model.BaseResponse;
import com.chanxa.linayi.model.ReceivingDetails;

import retrofit2.Call;

/**
 * @description: 获取收货列表
 * @author: miao
 * @time: 02-28
 */
public class GetGoodsReceivingDetails {

    public void getResponse(RequestCallBack<ReceivingDetails> requestCallBack, int procurementTaskId){
        //创建APIService实例
        ApiService.getReceivingDetails httpTestService =
                ApiRequest.getApiRequestInstance().create(ApiService.getReceivingDetails.class);
        //创建json body
        BaseResponse receivingDetailsBody = new BaseResponse();
        receivingDetailsBody.setProcurementTaskId(procurementTaskId);
        //创建请求，请求执行
        Call<ReceivingDetails> call = httpTestService.call(receivingDetailsBody);
        call.enqueue(new ApiCallBack<>(requestCallBack));
    }
}
