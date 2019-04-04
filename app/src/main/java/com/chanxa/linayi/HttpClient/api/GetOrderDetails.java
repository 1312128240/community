package com.chanxa.linayi.HttpClient.api;


import com.chanxa.linayi.Interface.RequestCallBack;
import com.chanxa.linayi.bean.MyBean.BaseStringBean;
import com.chanxa.linayi.model.OrderDetails;


import retrofit2.Call;

/**
 * @description: 获取订单详情
 * @author: miao
 * @time: 03-05
 */
public class GetOrderDetails {

    public void get(RequestCallBack<OrderDetails> requestCallBack, String ordersId){
        //创建APIService实例
        ApiService.getOrderDetails httpTestService =
                ApiRequest.getApiRequestInstance().create(ApiService.getOrderDetails.class);
        //创建json body
        BaseStringBean detailsBody = new BaseStringBean();
        detailsBody.setOrdersId(ordersId);
        //创建请求，请求执行
        Call<OrderDetails> call = httpTestService.call(detailsBody);
        call.enqueue(new ApiCallBack<>(requestCallBack));
    }
}
