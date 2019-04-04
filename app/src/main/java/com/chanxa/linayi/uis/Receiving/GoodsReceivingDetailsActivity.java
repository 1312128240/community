package com.chanxa.linayi.uis.Receiving;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chanxa.linayi.App;
import com.chanxa.linayi.HttpClient.api.GetGoodsReceivingDetails;
import com.chanxa.linayi.HttpClient.okhttp.OkhttpUtil;
import com.chanxa.linayi.HttpClient.okhttp.ResultCallback;
import com.chanxa.linayi.Interface.RequestCallBack;
import com.chanxa.linayi.R;
import com.chanxa.linayi.bean.MyBean.BaseStringBean;
import com.chanxa.linayi.model.ReceivingDetails;
import com.chanxa.linayi.tools.AppUtils;
import com.chanxa.linayi.tools.ToastUtil;
import com.chanxa.linayi.uis.BaseActivity;
import com.chanxa.linayi.views.imagebrowse.widget.PhotoView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;


public class GoodsReceivingDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_check_completed)
    TextView tvCheckCompleted;
    @BindView(R.id.tv_task_id)
    TextView tvTaskId;
    @BindView(R.id.tv_supermarket_name)
    TextView tvSupermarketName;
    @BindView(R.id.tv_order_id)
    TextView tvOrderId;
    @BindView(R.id.tv_procurement_Name)
    TextView tvProcurementName;
    @BindView(R.id.tv_goods_num)
    TextView tvGoodsNum;
    @BindView(R.id.tv_service_charge)
    TextView tvServiceCharge;
    @BindView(R.id.tv_purchase_quantity)
    TextView tvPurchaseQuantity;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.iv_goods)
    PhotoView ivGoods;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;

    private int procurementTaskId;

    private Context mContext;
    private AlertDialog.Builder dialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_receiving_details;
    }

    @Override
    public void initView() {
        mContext=this;
        tv_title.setText("收货详情");
        procurementTaskId = getIntent().getIntExtra("procurementTaskId",0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }


    private void getData() {
        GetGoodsReceivingDetails goodsReceivingDetails = new GetGoodsReceivingDetails();
        goodsReceivingDetails.getResponse(new RequestCallBack<ReceivingDetails>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(ReceivingDetails response) {
                //图片
                String imaUrl=response.getData().getImage();
                if(imaUrl!=null){
                    Glide.with(mContext).load(imaUrl).error(R.drawable.default_error).into(ivGoods);
                }

                tvTaskId.setText("任务编号："+response.getData().getProcurementTaskId());
                tvSupermarketName.setText("采买超市："+response.getData().getSupermarketName());
                tvOrderId.setText("订单编号："+response.getData().getOrdersId());
                tvProcurementName.setText("采买员："+response.getData().getBuyUserName());
                tvGoodsNum.setText("数量："+response.getData().getQuantity());
                tvPurchaseQuantity.setText("实际采买数量："+response.getData().getActualQuantity());
                tvServiceCharge.setText("");
                try {
                    String money = AppUtils.changeF2Y(response.getData().getTotalPrice());
                    tvTotal.setText("金额总计："+money);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    String money2 = AppUtils.changeF2Y(response.getData().getPrice());
                    tvMoney.setText("单价："+money2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //商品名称
                tvGoodsName.setText(response.getData().getGoodsSkuName());
            }

            @Override
            public void onError(String err_msg) {
                showToast("获取失败！");
            }

            @Override
            public void onFailure() {
                showToast("网络错误！");
            }
        },procurementTaskId);
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back(){
        onBackPressed();
    }


    @OnClick(R.id.tv_check_completed)
    public void onViewClicked() {
        //创建dialog构造器
        dialog = new AlertDialog.Builder(this);
        //设置title
        dialog.setTitle("提示");
        //设置icon
        dialog.setIcon(R.mipmap.logo);
        //设置内容
        dialog.setMessage("确认收货完成");
        //设置按钮
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        inspectionComplete();
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


    private void inspectionComplete() {

        Map<String,String> map=new HashMap<>();
        map.put("procurementTaskId",procurementTaskId+"");

        OkhttpUtil
                .getmIntance()
                .seTag(this)
                .PostAsync("procurement/procurement/inspectionComplete.do", map, new ResultCallback<BaseStringBean>() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, BaseStringBean bean) {
                        if("S".equals(bean.getRespCode())){
                            ToastUtil.showShort(App.getInstance(),"验货成功");
                            finish();
                        }else {
                            if(bean.getErrorMsg().contains("accessToken失效")){
                                showLogOutDialog();
                            }else {
                                ToastUtil.showShort(GoodsReceivingDetailsActivity.this,bean.getErrorMsg());
                            }
                        }
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog!=null){
            dialog=null;
        }
    }
}
