package com.chanxa.linayi.uis.Delivery;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chanxa.linayi.Adapters.MyCommonAdapter;
import com.chanxa.linayi.HttpClient.api.APISealBox;
import com.chanxa.linayi.HttpClient.okhttp.OkhttpUtil;
import com.chanxa.linayi.HttpClient.okhttp.ResultCallback;
import com.chanxa.linayi.Interface.RequestCallBack;
import com.chanxa.linayi.R;
import com.chanxa.linayi.bean.MyBean.DeliveryDetailsBean;
import com.chanxa.linayi.model.GoodsSendDetails;
import com.chanxa.linayi.model.SealBox;
import com.chanxa.linayi.tools.AppUtils;
import com.chanxa.linayi.tools.ScreenUtils;
import com.chanxa.linayi.tools.ToastUtil;
import com.chanxa.linayi.uis.BaseActivity;
import com.chanxa.linayi.views.PopWindow.ChoosePicturePopWindow;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class DeliveryDetailActivity extends BaseActivity {

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_buyer)
    TextView tvBuyer;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_second_time)
    TextView tvSecondTime;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_goods_num)
    TextView tvGoodsNum;
    @BindView(R.id.tv_ordersId)
    TextView tvOrdersId;
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.parent)
    View parent;
    private String boxNo;
    private int ordersId = 0;
    private int REQUEST_TAKE_PHOTO_PERMISSION = 1;
    private MyCommonAdapter<DeliveryDetailsBean.DataBean.GoodsSkuListBean> adapter;
    private ChoosePicturePopWindow popWindow;
    private File uriFile;
    private PictureReceiver myReceiver;
    private AlertDialog.Builder dialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_send_detail;
    }

    @Override
    public void initView() {
        tv_title.setText("新配送详情");
        boxNo = getIntent().getStringExtra("boxNo");
        ordersId = getIntent().getIntExtra("ordersId", 0);
        initRecy();
    }

    @Override
    public void initData() {
        super.initData();
        Refresh();

        initReceiver();
    }

    private void initReceiver() {
        myReceiver = new PictureReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("Pic");
        registerReceiver(myReceiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(myReceiver!=null){
            unregisterReceiver(myReceiver);
        }
        if(dialog!=null){
            dialog=null;
        }
    }

    private void initRecy() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<DeliveryDetailsBean.DataBean.GoodsSkuListBean> lists=new ArrayList<>();
        adapter = new MyCommonAdapter<DeliveryDetailsBean.DataBean.GoodsSkuListBean>(this,R.layout.item_goods_send_details_list,lists) {
            @Override
            protected void convert(ViewHolder holder, DeliveryDetailsBean.DataBean.GoodsSkuListBean goodsSkuListBean, int position) {
                //图片
                ImageView iv=holder.getView(R.id.iv_goods);
                Glide.with(mContext).load(goodsSkuListBean.getImage()).error(R.drawable.default_error).into(iv);
                //名字
                holder.setText(R.id.tv_goods_name,goodsSkuListBean.getFullName());
                //数量
                holder.setText(R.id.tv_money,"x"+goodsSkuListBean.getQuantity());
            }
        };

        mRecyclerView.setAdapter(adapter);
    }


    @OnClick(R.id.tv_confirm)
     public void confirm(){
        dialog = new AlertDialog.Builder(this);
         dialog.setTitle("提示");
         dialog.setIcon(R.mipmap.logo);
         dialog.setMessage("确认收货完成");
         dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();
                 if(uriFile == null){
                     showToast("请先选择封箱图片");
                 }else{
                     confirmBox();
                    // Log.e("自己发的广播选择图片",uriFile.toString());
                 }
             }
         });
         dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 dialogInterface.dismiss();
             }
         });
         //创建并显示
         dialog.create().show();

     }

     @OnClick(R.id.iv_take_photo)
     public void take_photo(){
         check();
     }

     @OnClick(R.id.tv_actionbar_back)
     public void back(){
         onBackPressed();
     }

    private void confirmBox() {
        APISealBox apiSealBox = new APISealBox();
        apiSealBox.start(new RequestCallBack<SealBox>() {
            @Override
            public void onSuccess(SealBox response) {
                showToast(response.getData());
              //  showToast("封箱成功");
                finish();
            }

            @Override
            public void onError(String err_msg) {
                showToast(err_msg);
            }

            @Override
            public void onFailure() {
                showToast("请求错误，失败");
            }
        },uriFile,ordersId,boxNo);
    }

    private void check() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_TAKE_PHOTO_PERMISSION);
        } else {
            takePhoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_TAKE_PHOTO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                showToast("拍照权限或查看相册权限被拒绝");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void takePhoto(){

      popWindow = new ChoosePicturePopWindow(this);

        int NavigationBarHeight=ScreenUtils.getNavigationBarHeight(this);
        boolean b=ScreenUtils.checkDeviceHasNavigationBar(this);
        if(!b){
            popWindow.showAtLocation(parent, Gravity.BOTTOM , 0,NavigationBarHeight);
        }else {
            popWindow.showAtLocation(parent, Gravity.BOTTOM , 0,0);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        popWindow.setActivityResult(requestCode, resultCode, data);
    }


    private void Refresh() {
        Map<String,String> map=new HashMap<>();
        map.put("boxNo",boxNo);
        map.put("ordersId",ordersId+"");
        OkhttpUtil
                .getmIntance()
                .seTag(this)
                .PostAsync("delivery/deliveryTask/toViewDeliveryTask.do", map, new ResultCallback<DeliveryDetailsBean>() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, DeliveryDetailsBean bean) {
                        if("S".equals(bean.getRespCode())){
                            initHeader(bean);
                            List<DeliveryDetailsBean.DataBean.GoodsSkuListBean> tempList=bean.getData().getGoodsSkuList();
                            adapter.add(tempList,true);
                        }else {
                            if(bean.getErrorMsg().contains("accessToken失效")){
                                showLogOutDialog();
                            }else {
                                ToastUtil.showShort(DeliveryDetailActivity.this,bean.getErrorMsg());
                            }
                        }
                    }
                });
    }


    private void initHeader(DeliveryDetailsBean bean) {
        if(bean!=null&&bean.getData()!=null){
            //下单间
            String createTime = AppUtils.formatDateNoYear(bean.getData().getCreateTime()+"");
            tvTime.setText("下单时间: "+createTime);
            //订单编号
            tvOrdersId.setText("订单编号: "+bean.getData().getOrdersId());
            //收货人
            tvBuyer.setText("收货人: "+bean.getData().getReceiverName());
            //配送地址
            tvAddress.setText("配送地址: "+bean.getData().getAddress());
            //配送箱号
            tvNum.setText("配送箱号: "+bean.getData().getBoxNo());
            //数量
            tvGoodsNum.setText("数量: "+bean.getData().getQuantity());
        }

    }

    @SuppressLint("SetTextI18n")
    private void setSendInfo(GoodsSendDetails sendDetails) {
        if (sendDetails.getData().getCreateTime() != null) {
            String time = AppUtils.formatDateNoYear(sendDetails.getData().getCreateTime());
            tvTime.setText("下单时间：" + time);
        }
        if (sendDetails.getData().getMobile() != null && sendDetails.getData().getReceiverName() != null) {
            tvBuyer.setText("收货人： "+sendDetails.getData().getReceiverName()+" " + sendDetails.getData().getMobile());
        }
        if (sendDetails.getData().getAddress() != null) {
            tvAddress.setText("收货地址：" + sendDetails.getData().getAddress());
        }
        if (sendDetails.getData().getBoxNo() != null) {
            tvNum.setText("配送箱号：" + sendDetails.getData().getBoxNo());
        }
        if (sendDetails.getData().getQuantity() != null) {
            tvGoodsNum.setText("数量：" + sendDetails.getData().getQuantity());
        }
        if (sendDetails.getData().getOrdersId() != null) {
            tvOrdersId.setText("订单编号：" + sendDetails.getData().getOrdersId());
        }
    }


    class PictureReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent!=null){
                if("Pic".equals(intent.getAction())){
                   String uri=intent.getStringExtra("uri");
                    if(uri!=null){
                        uriFile=new File(uri);
                    }else {
                        ToastUtil.showShort(DeliveryDetailActivity.this,"图片路径不存在");
                    }
                }
            }
        }
    }
}
