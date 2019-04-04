package com.chanxa.linayi.uis.Task;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chanxa.linayi.HttpClient.okhttp.OkhttpUtil;
import com.chanxa.linayi.HttpClient.okhttp.ResultCallback;
import com.chanxa.linayi.R;
import com.chanxa.linayi.bean.MyBean.TaskDeliveryDetailsBean;
import com.chanxa.linayi.tools.AppUtils;
import com.chanxa.linayi.tools.FormatUtils;
import com.chanxa.linayi.tools.ToastUtil;
import com.chanxa.linayi.uis.BaseActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;


public class TaskDeliveryDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tv_time)
    TextView tv_titme;
    @BindView(R.id.tv_ordersId)
    TextView tv_orderId;
    @BindView(R.id.tv_buyer)
    TextView tv_buyer;
    @BindView(R.id.tv_address)
    TextView ttv_address;
    @BindView(R.id.tv_totalPrice)
    TextView tv_price;
    @BindView(R.id.recy_delivery_details)
    RecyclerView recyclerView;
    private int orderId;


    @Override
    public int getLayoutId() {
        return R.layout.activity_task_delivery_details;
    }

    @Override
    public void initView() {
        orderId = getIntent().getIntExtra("ordersId",-1);
        tv_title.setText("配送任务详情");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Refresh();
    }

    private void Refresh() {
        Map<String, String> map = new HashMap<>();
        map.put("ordersId", orderId+"");
        OkhttpUtil
                .getmIntance()
                .seTag(this)
                .PostAsync("delivery/getDeliveryTaskDetails.do", map, new ResultCallback<TaskDeliveryDetailsBean>() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        ToastUtil.showShort(TaskDeliveryDetailsActivity.this,e.toString());
                    }

                    @Override
                    public void onResponse(Call call, TaskDeliveryDetailsBean bean) {
                        if("S".equals(bean.getRespCode())){
                            initUi(bean.getData());
                        }else {
                            if(bean.getErrorMsg().contains("accessToken失效")){
                                showLogOutDialog();
                            }else {
                                ToastUtil.showShort(TaskDeliveryDetailsActivity.this,bean.getErrorMsg());
                            }
                        }
                    }
                });

    }

    public void initUi(TaskDeliveryDetailsBean.DataBean dataBean) {
        //下单时间
        String time = AppUtils.formatDateNoYear(dataBean.getCreateTime() + "");
        tv_titme.setText("下单时间: " + time);
        //订单金额
        double p=(double)dataBean.getAmount()/100;
        tv_price.setText("订单金额: " + p);
        //订单编号
        tv_orderId.setText("订单编号: " + dataBean.getOrdersId());
        //配送地址
        tv_buyer.setText("收货人: " + dataBean.getReceiverName());
        //商品列表
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<TaskDeliveryDetailsBean.DataBean.ShoppingCarListBean> adapter =
                new CommonAdapter<TaskDeliveryDetailsBean.DataBean.ShoppingCarListBean>(this,R.layout.item_goods_binning_details_list,dataBean.getShoppingCarList()) {
            @Override
            protected void convert(ViewHolder holder, TaskDeliveryDetailsBean.DataBean.ShoppingCarListBean shoppingCarListBean, int position) {
                //图片
                ImageView iv=holder.getView(R.id.iv_goods);
                Glide.with(mContext).load(shoppingCarListBean.getGoodsSkuImage()).error(R.drawable.default_error).into(iv);
                //名字
                holder.setText(R.id.tv_goods_name,shoppingCarListBean.getGoodsName());
                //价格
                String p= FormatUtils.format(shoppingCarListBean.getHeJiPrice());
                holder.setText(R.id.tv_money,"¥"+p);
                //数量
                holder.setText(R.id.tv_num,"x"+shoppingCarListBean.getQuantity());
            }
        };

        recyclerView.setAdapter(adapter);

    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }
}