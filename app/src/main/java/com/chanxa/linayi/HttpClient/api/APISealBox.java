package com.chanxa.linayi.HttpClient.api;


import com.chanxa.linayi.Interface.RequestCallBack;
import com.chanxa.linayi.model.SealBox;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;


/**
 * @description: 拍照封箱 POST文件/表单上传 需要用post multipart/form-data 方式上传
 * @author: miao
 * @time: 02-28
 */
public class APISealBox {

    public void start(RequestCallBack<SealBox> requestCallBack, File file, int ordersId, String boxNo){
        //创建APIService实例
        ApiService.sealBox apiService =
                ApiRequest.getApiRequestInstance().create(ApiService.sealBox.class);
        // 创建RequestBody，传入参数："multipart/form-data"，boxNo
        RequestBody requestBoxNo = RequestBody.create(MediaType.parse("multipart/form-data"), boxNo+"");
        // 创建RequestBody，传入参数："multipart/form-data"，ordersId
        RequestBody requestOrdersId = RequestBody.create(MediaType.parse("multipart/form-data"), ordersId+"");
        // 创建RequestBody，传入参数："multipart/form-data"，File
        RequestBody requestImgFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // 创建MultipartBody.Part，用于封装文件数据
        MultipartBody.Part requestImgPart = MultipartBody.Part.createFormData("file", file.getName(), requestImgFile);
        // 发起网络请求，上传图片我二进制数据
        Call<SealBox> call = apiService.call(requestOrdersId,requestBoxNo,requestImgPart);
        call.enqueue(new ApiCallBack<>(requestCallBack));
    }
}
