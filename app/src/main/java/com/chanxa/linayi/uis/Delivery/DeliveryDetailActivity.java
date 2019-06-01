package com.chanxa.linayi.uis.Delivery;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chanxa.linayi.Adapters.MyCommonAdapter;
import com.chanxa.linayi.HttpClient.OkhttpUtil;
import com.chanxa.linayi.HttpClient.ResultCallback;
import com.chanxa.linayi.R;
import com.chanxa.linayi.bean.BaseStringBean;
import com.chanxa.linayi.bean.DeliveryDetailsBean;
import com.chanxa.linayi.tools.DateTools;
import com.chanxa.linayi.tools.PermissionUtil;
import com.chanxa.linayi.tools.ScreenUtils;
import com.chanxa.linayi.uis.BaseActivity;
import com.chanxa.linayi.uis.BrowseImageActivity;
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

public class DeliveryDetailActivity extends BaseActivity{

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
  //  private int REQUEST_TAKE_PHOTO_PERMISSION = 1;
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
        tv_title.setText("配送详情");
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
            protected void convert(ViewHolder holder, final DeliveryDetailsBean.DataBean.GoodsSkuListBean goodsSkuListBean, int position) {
                //图片
              final   ImageView iv=holder.getView(R.id.iv_goods);
                Glide.with(mContext).load(goodsSkuListBean.getImage()).error(R.drawable.default_error).into(iv);
               Log.e("配送详情图片",goodsSkuListBean.getImage()+"");
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(DeliveryDetailActivity.this,BrowseImageActivity.class);
                        intent.putExtra("imgUrl",goodsSkuListBean.getImage());
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(DeliveryDetailActivity.this,iv ,"share");
                        startActivity(intent,options.toBundle());
                    }
                });

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
                     showToast("请先选择封箱图片",0);
                 }else{
                     confirmBox();
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
         if(Build.VERSION.SDK_INT >= 23){
             boolean b=PermissionUtil.hasPermissons(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA});
             if(!b){
                 PermissionUtil.requestPerssions(this,101,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA});
             }else {
                 takePhoto();
             }
         }else {
             takePhoto();
         }
     }


   @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode ==101) {
            int flag=0;
            for (int i = 0; i <grantResults.length ; i++) {
               if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                   flag++;
               };
            }

            if(flag==grantResults.length){
                takePhoto();
            }else {
                showToast("拍照或查看相册权限被拒绝,请前往设置中开启",0);
            }
        }
    }

    /**
     * 选择相册图片弹窗
     */
    private void takePhoto(){
        popWindow = new ChoosePicturePopWindow(this);
        boolean b=ScreenUtils.checkDeviceHasNavigationBar(this);
        if(!b){
            int NavigationBarHeight=ScreenUtils.getNavigationBarHeight(this);
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
                                showToast(bean.getErrorMsg(),0);
                            }
                        }
                    }
                });
    }


    private void initHeader(DeliveryDetailsBean bean) {
        if(bean!=null&&bean.getData()!=null){
            //下单间
            String createTime = DateTools.formatDateNoYear(bean.getData().getCreateTime()+"");
            tvTime.setText("下单时间: "+createTime);
            //订单编号
            tvOrdersId.setText("订单编号: "+bean.getData().getOrdersId());
            //收货人
            tvBuyer.setText("收  货  人: "+bean.getData().getReceiverName());
            //配送地址
            tvAddress.setText(bean.getData().getAddress());
            //配送箱号
            tvNum.setText("配送箱号: "+bean.getData().getBoxNo());
            //数量
            tvGoodsNum.setText("数量: "+bean.getData().getQuantity());
        }

    }


    @OnClick(R.id.tv_actionbar_back)
    public void back(){
        onBackPressed();
    }

    private void confirmBox() {

       OkhttpUtil
               .getmIntance()
               .seTag(this)
               .UploadFile("delivery/deliveryTask/sealBox.do", ordersId+"",boxNo, uriFile, new ResultCallback<BaseStringBean>() {
                   @Override
                   public void onFailure(Call call, IOException e) {
                       Log.e("上传图片失败",e.toString());
                   }

                   @Override
                   public void onResponse(Call call, BaseStringBean response) {
                          if("S".equals(response.getRespCode())){
                              showToast(response.getData(),0);
                              onBackPressed();
                          }else {
                              if(response.getErrorMsg().contains("accessToken失效")){
                                  showLogOutDialog();
                              }else {
                                  showToast(response.getErrorMsg(),0);
                              }
                          }
                   }
               });

    }


    class PictureReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction()!=null){
                if("Pic".equals(intent.getAction())){
                    String uri=intent.getStringExtra("uri");
                    if(uri!=null){
                        Log.e("选取的图片",uri);
                        uriFile=new File(uri);
                    }else {
                        showToast("图片路径不存在",0);
                    }
                }
            }
        }
    }
}
