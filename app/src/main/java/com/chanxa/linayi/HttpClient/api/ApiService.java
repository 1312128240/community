package com.chanxa.linayi.HttpClient.api;

import com.chanxa.linayi.bean.MyBean.BaseStringBean;
import com.chanxa.linayi.bean.MyBean.LoginBean;
import com.chanxa.linayi.bean.OrderBeanNew;
import com.chanxa.linayi.bean.OrderLisBody;
import com.chanxa.linayi.model.BaseResponse;
import com.chanxa.linayi.model.Binning;
import com.chanxa.linayi.model.BinningBody;
import com.chanxa.linayi.model.DeliveryDetails;
import com.chanxa.linayi.model.DeliveryList;
import com.chanxa.linayi.model.GoodsBinningDetails;
import com.chanxa.linayi.model.GoodsBinningList;
import com.chanxa.linayi.model.GoodsSendDetails;
import com.chanxa.linayi.model.GoodsSendList;
import com.chanxa.linayi.model.InspectionComplete;
import com.chanxa.linayi.model.OrderDetails;
import com.chanxa.linayi.model.OrderList;
import com.chanxa.linayi.model.ProcurementList;
import com.chanxa.linayi.model.ReceivingDetails;
import com.chanxa.linayi.model.SealBox;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


/**
 * @description: ApiService 接口定义
 * @author: miao
 * @time: 2018-09-25
 */
public class ApiService {

  /*  //无参数示例
    public interface HttpTestService {
        @GET("banner/json")
        Call<Test1> getJSON();
    }*/

    //有参数示例
    public interface HttpTestService2 {
        @GET("article/list/{page}/json")
        Call<ResponseBody> getJSON(@Path("page") int page);
    }

    //POST有参数示例
    public interface HttpTestService3 {
        @FormUrlEncoded
        @POST("HomePage/CallSend")
        Call<BaseResponse> call(
                @Field("phone") String phone,
                @Field("loginName") String loginName,
                @Field("timestamp") String timestamp,
                @Field("sign") String sign,
                @Field("versionCode") String versionCode,
                @Field("deviceType") String deviceType);
    }


    /**
     * 收货
     */
    //获取收货列表
    public interface getGoodsReceivingList {
        @POST("procurement/procurement/getProcurementTask.do")
        Call<OrderBeanNew> call(@Body BaseResponse body);
    }
    //获取收货详情
    public interface getReceivingDetails {
        @POST("procurement/procurement/receivingDetails.do")
        Call<ReceivingDetails> call(@Body BaseResponse body);
    }
    //确认收货，验货完成
    public interface inspectionComplete {
        @POST("procurement/procurement/inspectionComplete.do")
        Call<InspectionComplete> call(@Body BaseResponse body);
    }

    /**
     * 装箱
     */
    //获取装箱列表
    public interface getGoodsBinningList {
        @POST("goods/order/boxingOrder.do")
        Call<GoodsBinningList> call(@Body BaseResponse body);
    }
    //获取装箱详情
    public interface getGoodsBinningDetails {
        @POST("procurement/procurement/boxingDetails.do")
        Call<GoodsBinningDetails> call(@Body BaseResponse body);
    }
    //装箱（箱号需要手动输入）
    public interface binning {
        @POST("delivery/deliveryBox/boxing.do")
        Call<Binning> call(@Body BinningBody body);
    }

    /**
     * 配送
     */
    //获取配送列表
    public interface getGoodsSendList {
        @POST("delivery/deliveryTask/getAllDeliveryTask.do")
        Call<GoodsSendList> call(@Body BaseResponse body);
    }
    //获取配送详情
    public interface getGoodsSendDetails {
        @POST("delivery/deliveryTask/toViewDeliveryTask.do")
        Call<GoodsSendDetails> call(@Body BaseResponse body);
    }
    //确认封箱
    public interface sealBox {
        @Multipart
        @POST("delivery/deliveryTask/sealBox.do")
        Call<SealBox> call(@Part("ordersId") RequestBody ordersId,
                           @Part("boxNo") RequestBody boxNo,
                           @Part MultipartBody.Part request_img_part);
    }

    /**
     * 任务空间
     */
    //获取配送任务列表（进行中、已完成）
    public interface getDeliveryList {
        @POST("delivery/deliveryList.do")
        Call<DeliveryList> call(@Body BaseResponse body);
    }

    //获取采买任务列表（进行中、已完成）
    public interface getProcurementList {
        @POST("procurement/procurement/getProcurementList.do")
        Call<ProcurementList> call(@Body BaseResponse body);
    }

    //获取配送任务详情
    public interface getDeliveryTaskDetails {
        @POST("delivery/getDeliveryTaskDetails.do")
        Call<DeliveryDetails> call(@Body BaseStringBean body);
    }

    /**
     * 订单列表
     */
    //获取订单列表（全部:ALL、采买中:PROCURING、送货中:DELIVERING、已收货:DELIVER_FINISHED、已取消:CANCELED）
    public interface getOrderList {
        @POST("goods/order/getOrderList.do")
        Call<OrderList> call(@Body OrderLisBody body);
    }
    //获取订单详情
    public interface getOrderDetails {
        @POST("goods/order/getOrderDetails.do")
        Call<OrderDetails> call(@Body BaseStringBean body);
    }



    /**
     * 登录
     */
    public interface Login{
        @POST("account/account/communityLogin.do")
        Call<LoginBean> call(@Body BaseStringBean body);
    }
}
