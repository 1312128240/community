package com.chanxa.linayi.uis.Order;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chanxa.linayi.Adapters.MyCommonAdapter;
import com.chanxa.linayi.HttpClient.OkhttpUtil;
import com.chanxa.linayi.HttpClient.ResultCallback;
import com.chanxa.linayi.R;
import com.chanxa.linayi.bean.OrderDetails;
import com.chanxa.linayi.uis.BaseActivity;
import com.chanxa.linayi.uis.BrowseImageActivity;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class OrderDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_ordersId)
    TextView tv_orderId;
    @BindView(R.id.tv_buyer)
    TextView tv_buyer;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_totalPrice)
    TextView tv_price;
    @BindView(R.id.recy_order_details)
    RecyclerView recyclerView;
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    private int ordersId;
    private MyCommonAdapter<OrderDetails.DataBean.ShoppingCarListBean> adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_details;
    }

    @Override
    public void initView() {
        ordersId = getIntent().getIntExtra("ordersId",-1);
        tv_title.setText("订单详情");

        initRecy();
    }


    @Override
    protected void onResume() {
        super.onResume();

         getData();
    }

    private void getData() {
        Map<String,String> map=new HashMap<>();
        map.put("communityName","community");
        map.put("ordersId",ordersId+"");

        OkhttpUtil
                .getmIntance()
                .seTag(this)
                .PostAsync("goods/order/getOrderDetails.do", map, new ResultCallback<OrderDetails>() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, OrderDetails response) {
                        if("S".equals(response.getRespCode())){
                            initHeader(response);
                            List<OrderDetails.DataBean.ShoppingCarListBean> lists=response.getData().getShoppingCarList();
                            adapter.add(lists,true);
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

    /**
     * 初始化头部
     * @param orderDetails
     */
    private void initHeader(OrderDetails orderDetails) {
        if (orderDetails.getData().getCreateDateStr() != null) {
            tv_time.setText("下单时间：" + orderDetails.getData().getCreateDateStr());
        }
        if (orderDetails.getData().getReceiverName() != null) {
            tv_buyer.setText("收  货  人：" + orderDetails.getData().getReceiverName());
        }
        if (orderDetails.getData().getAddress() != null) {
            tv_address.setText(orderDetails.getData().getAddress());
        }
        if (orderDetails.getData().getTotalPrice() != null) {
            tv_price.setText("订单金额：¥" + orderDetails.getData().getTotalPrice());
        }
        if (orderDetails.getData().getOrdersId() != null) {
            tv_orderId.setText("订单编号：" + orderDetails.getData().getOrdersId());
        }
    }

    /**
     * 列表
     */
    public void  initRecy(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<OrderDetails.DataBean.ShoppingCarListBean> beanList=new ArrayList<>();
        adapter = new MyCommonAdapter<OrderDetails.DataBean.ShoppingCarListBean>(this,R.layout.item_goods_binning_details_list,beanList) {
            @Override
            protected void convert(ViewHolder holder, final OrderDetails.DataBean.ShoppingCarListBean bean, int position) {
                //图片
                final ImageView photoView=holder.getView(R.id.iv_goods);
                if(bean.getGoodsSkuImage()!=null){
                    Glide.with(mContext).load(bean.getGoodsSkuImage()).error(R.drawable.default_error).into(photoView);
                }
                photoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(OrderDetailsActivity.this,BrowseImageActivity.class);
                        intent.putExtra("imgUrl",bean.getGoodsSkuImage());
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(OrderDetailsActivity.this,photoView ,"share");
                        startActivity(intent,options.toBundle());
                    }
                });

               //商品名字
                holder.setText(R.id.tv_goods_name, bean.getGoodsName());
                //价格
                holder.setText(R.id.tv_money, "单价: ¥"+bean.getMinPrice());
                //数量
                holder.setText(R.id.tv_num,"x"+bean.getQuantity());
            }
        };
        recyclerView.setAdapter(adapter);
    }


    @OnClick(R.id.tv_actionbar_back)
    public void back(){
        onBackPressed();
    }


}
