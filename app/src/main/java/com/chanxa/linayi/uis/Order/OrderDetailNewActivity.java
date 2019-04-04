package com.chanxa.linayi.uis.Order;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chanxa.linayi.Adapters.OrderDetailsGoodsItemAdapter;
import com.chanxa.linayi.HttpClient.api.GetOrderDetails;
import com.chanxa.linayi.Interface.RequestCallBack;
import com.chanxa.linayi.R;
import com.chanxa.linayi.model.OrderDetails;
import com.chanxa.linayi.uis.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class OrderDetailNewActivity extends BaseActivity {

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_buyer)
    TextView tvBuyer;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_totalPrice)
    TextView tvTotalPrice;
    @BindView(R.id.tv_ordersId)
    TextView tvOrdersId;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private int ordersId;
    private OrderDetailsGoodsItemAdapter orderDetailsGoodsItemAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail_new;
    }

    @Override
    public void initView() {
        ordersId=getIntent().getIntExtra("ordersId",-1);
        initRecy();
    }


    @Override
    public void initData() {
        super.initData();
        getOrderDetails();
    }

    private void getOrderDetails() {
        GetOrderDetails getOrderDetails = new GetOrderDetails();
        getOrderDetails.get(new RequestCallBack<OrderDetails>() {
            @Override
            public void onSuccess(OrderDetails response) {
                if (orderDetailsGoodsItemAdapter != null && response.getData() != null) {
                    orderDetailsGoodsItemAdapter.setNewData(response.getData().getShoppingCarList());
                    setOrderInfo(response);
                } else {
                    showToast("无商品信息");
                }
            }

            @Override
            public void onError(String err_msg) {
                showToast("查询失败");
            }

            @Override
            public void onFailure() {
                showToast("查询出错");
            }
        },ordersId+"");
    }

    @SuppressLint("SetTextI18n")
    private void setOrderInfo(OrderDetails orderDetails) {
        if (orderDetails.getData().getCreateDateStr() != null) {
            tvTime.setText("下单时间：" + orderDetails.getData().getCreateDateStr());
        }
        if (orderDetails.getData().getReceiverName() != null) {
            tvBuyer.setText("收货人：" + orderDetails.getData().getReceiverName());
        }
        if (orderDetails.getData().getAddress() != null) {
            tvAddress.setText("收货地址：" + orderDetails.getData().getAddress());
        }
        if (orderDetails.getData().getTotalPrice() != null) {
            tvTotalPrice.setText("订单金额：" + orderDetails.getData().getTotalPrice());
        }
        if (orderDetails.getData().getOrdersId() != null) {
            tvOrdersId.setText("订单编号：" + orderDetails.getData().getOrdersId());
        }
    }


    /**
     *  设置RecyclerView
     */
    public void initRecy(){
        orderDetailsGoodsItemAdapter = new OrderDetailsGoodsItemAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(orderDetailsGoodsItemAdapter);
    }

    @OnClick({R.id.iv_back, R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                finish();
                break;
        }
    }

}
