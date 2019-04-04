package com.chanxa.linayi.fragments;


import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chanxa.linayi.Adapters.MyCommonAdapter;
import com.chanxa.linayi.HttpClient.okhttp.OkhttpUtil;
import com.chanxa.linayi.HttpClient.okhttp.ResultCallback;
import com.chanxa.linayi.R;
import com.chanxa.linayi.bean.MyBean.AllOrderBean;
import com.chanxa.linayi.tools.ToastUtil;
import com.chanxa.linayi.uis.Order.OrderDetailNewActivity;
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


public class AllOrderFragment extends BaseFragments implements OnRefreshLoadMoreListener{


    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshlayout;
    @BindView(R.id.recy_all_order)
    RecyclerView recyclerview;

    private int currentPage=1;
    private int totalPage;
    private OrderStatusActivity orderStatusActivity;
    private MyCommonAdapter<AllOrderBean.DataBeanX.DataBean> adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_all_order;
    }

    @Override
    public void initData() {
         refreshlayout.setOnRefreshLoadMoreListener(this);

         initRecy();
         Refresh(true);
    }






    private void initRecy() {
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        List<AllOrderBean.DataBeanX.DataBean> lists=new ArrayList<>();
        adapter = new MyCommonAdapter<AllOrderBean.DataBeanX.DataBean>(getContext(),R.layout.item_order_list,lists) {
            @Override
            protected void convert(ViewHolder holder, final AllOrderBean.DataBeanX.DataBean dataBean, int position) {
                   //订单编号
                holder.setText(R.id.tv1t,dataBean.getOrdersId()+"");
                //顾客名字
                holder.setText(R.id.tv2t,dataBean.getReceiverName());
                //订单金额
                double p1=(double)dataBean.getAmount()/100;
                holder.setText(R.id.tv3t,p1+"");

                //去详情
                holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getActivity(), OrderDetailNewActivity.class);
                        intent.putExtra("ordersId",dataBean.getOrdersId());
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerview.setAdapter(adapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        orderStatusActivity = (OrderStatusActivity) activity;
    }



    private void Refresh(final boolean isRefresh) {

        Map<String,String> map=new HashMap<>();
        map.put("communityStatus","ALL");
        map.put("currentPage",currentPage+"");
        map.put("pageSize",10+"");
        OkhttpUtil
                .getmIntance()
                .seTag(this)
                .PostAsync("goods/order/getProcureOrderList.do", map, new ResultCallback<AllOrderBean>() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        refreshlayout.finishLoadMore();
                        refreshlayout.finishRefresh();
                        ToastUtil.showShort(getContext(),e.toString());
                    }

                    @Override
                    public void onResponse(Call call, AllOrderBean bean) {
                        refreshlayout.finishLoadMore();
                        refreshlayout.finishRefresh();

                        if("S".equals(bean.getRespCode())){
                            totalPage=bean.getData().getTotalPage();
                            List<AllOrderBean.DataBeanX.DataBean> tempList=bean.getData().getData();
                            adapter.add(tempList,isRefresh);
                        }else {
                            if(bean.getErrorMsg().contains("accessToken失效")){
                                orderStatusActivity.showLogOutDialog();
                            }else {
                                ToastUtil.showShort(getContext(),bean.getErrorMsg());
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
                       ToastUtil.showShort(getContext(),"全部加载完毕");
                   }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
           currentPage=1;
           Refresh(true);
    }
}
