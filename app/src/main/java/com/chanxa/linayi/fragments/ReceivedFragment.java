package com.chanxa.linayi.fragments;


import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chanxa.linayi.Adapters.MyCommonAdapter;
import com.chanxa.linayi.HttpClient.OkhttpUtil;
import com.chanxa.linayi.HttpClient.ResultCallback;
import com.chanxa.linayi.R;

import com.chanxa.linayi.bean.AllOrderBean;
import com.chanxa.linayi.uis.Order.OrderDetailsActivity;
import com.chanxa.linayi.uis.Order.OrderStatusActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;


public class ReceivedFragment extends BaseFragments implements OnRefreshLoadMoreListener{

     @BindView(R.id.refreshLayout)
    SmartRefreshLayout  refreshlayout;
    @BindView(R.id.recy_received)
    RecyclerView recyclerView;
    @BindView(R.id.emptylayout)
    View emptyView;


    private int currentPage=1;
    private int totalPage;
    private MyCommonAdapter<AllOrderBean.DataBeanX.DataBean> adapter;
    private OrderStatusActivity orderStatusActivity;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_received;
    }

    @Override
    public void initData() {

        refreshlayout.setOnRefreshLoadMoreListener(this);

         initRecy();

         Refresh(true);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        orderStatusActivity = (OrderStatusActivity) activity;
    }

    private void initRecy() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<AllOrderBean.DataBeanX.DataBean> lists=new ArrayList<>();
        adapter = new MyCommonAdapter<AllOrderBean.DataBeanX.DataBean>(getContext(),R.layout.item_order_list,lists) {
            @Override
            protected void convert(ViewHolder holder,final AllOrderBean.DataBeanX.DataBean dataBean, int position) {
                //订单编号
                holder.setText(R.id.tv1t,dataBean.getOrdersId()+"");
                //顾客名字
                holder.setText(R.id.tv2t,dataBean.getReceiverName());
                //金额
                double p1=(double)dataBean.getAmount()/100;
                holder.setText(R.id.tv3t,p1+"");
                //去详情
                holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getActivity(), OrderDetailsActivity.class);
                        intent.putExtra("ordersId",dataBean.getOrdersId());
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void Refresh(final boolean isRefresh) {
        Map<String,String> map=new HashMap<>();
        map.put("communityStatus", "DELIVER_FINISHED");
        map.put("communityName","community");
        map.put("currentPage", currentPage + "");
        map.put("pageSize",10+"");

        OkhttpUtil
                .getmIntance()
                .seTag(this)
                .PostAsync("goods/order/getProcureOrderList.do", map, new ResultCallback<AllOrderBean>() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        refreshlayout.finishRefresh();
                        refreshlayout.finishLoadMore();
                    }

                    @Override
                    public void onResponse(Call call, AllOrderBean bean) {
                        refreshlayout.finishRefresh();
                        refreshlayout.finishLoadMore();

                        if("S".equals(bean.getRespCode())){
                            totalPage=bean.getData().getTotalPage();
                            final List<AllOrderBean.DataBeanX.DataBean> tempList=bean.getData().getData();
                            adapter.add(tempList,isRefresh);

                            if(isRefresh){
                                if(tempList.size()==0){
                                    refreshlayout.setVisibility(View.GONE);
                                    emptyView.setVisibility(View.VISIBLE);
                                }else {
                                    refreshlayout.setVisibility(View.VISIBLE);
                                    emptyView.setVisibility(View.GONE);
                                }
                            }

                        }else {
                            if(bean.getErrorMsg().contains("accessToken失效")){
                                orderStatusActivity.showLogOutDialog();
                            }else {
                                showToast(bean.getErrorMsg(),0);
                            }
                        }
                    }
                });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
         if(currentPage<totalPage){
             currentPage++;
             Refresh(false);
         }else {
             refreshlayout.finishLoadMore();
             showToast("全部加载完毕",0);
         }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
             currentPage=1;
              Refresh(true);
    }
}
