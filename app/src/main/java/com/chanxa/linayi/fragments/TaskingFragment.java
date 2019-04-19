package com.chanxa.linayi.fragments;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chanxa.linayi.Adapters.MyCommonAdapter;
import com.chanxa.linayi.HttpClient.okhttp.OkhttpUtil;
import com.chanxa.linayi.HttpClient.okhttp.ResultCallback;
import com.chanxa.linayi.R;
import com.chanxa.linayi.bean.MyBean.ProcurementBean;
import com.chanxa.linayi.bean.MyBean.TaskDeliveryBean;
import com.chanxa.linayi.tools.ToastUtil;
import com.chanxa.linayi.uis.Task.TaskDeliveryDetailsActivity;
import com.chanxa.linayi.uis.Task.TaskHomeActivity;
import com.chanxa.linayi.uis.Task.TaskProcurementkDetailActivity;
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


public class TaskingFragment extends BaseFragments {

    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.tv_tasking_number)
    TextView tv_tasking_number;
    @BindView(R.id.tv_tasking_person)
    TextView tv_tasking_person;
    @BindView(R.id.recy_tasking_buy)
    RecyclerView recyclerView_buy;
    @BindView(R.id.recy_tasking_dispatching)
    RecyclerView recyclerView_dispatching;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int currentPage = 1;
    private int totalPageBuy;
    private int currentPage2 = 1;
    private int totalPageDispatching;
    private int tabIsCheck;
    public MyCommonAdapter<ProcurementBean.DataBeanX.DataBean> adapter1;
    private MyCommonAdapter<TaskDeliveryBean.DataBeanX.DataBean> adapter2;
    private TaskHomeActivity taskHomeActivity;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_tasking;
    }



    @Override
    public void initData() {

        Listener();


        initRecy();


        RefreshDelivery(true);


        RefreshProcurement(true);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        taskHomeActivity = (TaskHomeActivity) activity;
    }


    private void initRecy() {
        recyclerView_buy.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_dispatching.setLayoutManager(new LinearLayoutManager(getContext()));
        //采买任务
        List<ProcurementBean.DataBeanX.DataBean> lists1 = new ArrayList<>();
        adapter1 = new MyCommonAdapter<ProcurementBean.DataBeanX.DataBean>(getContext(), R.layout.item_delivery_list, lists1) {
            @Override
            protected void convert(ViewHolder holder, final ProcurementBean.DataBeanX.DataBean bean, int position) {
                //任务编号
                holder.setText(R.id.tv1t, bean.getProcurementTaskId()+"");
                //采买员
                holder.setText(R.id.tv2t, bean.getBuyUserName());
                //金额
                double p=(double)bean.getPrice()/100;
                holder.setText(R.id.tv3t,p+"");
                //查看详情
                holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, TaskProcurementkDetailActivity.class);
                        intent.putExtra("taskId", bean.getProcurementTaskId());
                        mContext.startActivity(intent);
                    }
                });
            }
        };

        recyclerView_buy.setAdapter(adapter1);

        //配送任务
        List<TaskDeliveryBean.DataBeanX.DataBean> lists2 = new ArrayList<>();
        adapter2 = new MyCommonAdapter<TaskDeliveryBean.DataBeanX.DataBean>(getContext(), R.layout.item_delivery_list, lists2) {
            @Override
            protected void convert(ViewHolder holder, final TaskDeliveryBean.DataBeanX.DataBean bean, int position) {
                //订单编号
                holder.setText(R.id.tv1t, bean.getOrdersId() + "");
                //配送员
                holder.setText(R.id.tv2t, bean.getBuyUserName());
                //金额
                double p=(double)bean.getAmount()/100;
                holder.setText(R.id.tv3t, p+"");

                holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, TaskDeliveryDetailsActivity.class);
                        intent.putExtra("ordersId", bean.getOrdersId());
                        mContext.startActivity(intent);
                    }
                });
            }
        };
        recyclerView_dispatching.setAdapter(adapter2);
    }

    /**
     * 进行中的采买
     */
    private void RefreshProcurement(final boolean isRefresh) {
        Map<String, String> map = new HashMap<>();
        map.put("procureStatus", "PROCURING");
        map.put("currentPage", currentPage + "");
        map.put("pageSize", 10 + "");
        OkhttpUtil
                .getmIntance()
                .PostAsync("procurement/procurement/getProcurementList.do", map, new ResultCallback<ProcurementBean>() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        ToastUtil.showShort(getContext(),e.toString());
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                    }

                    @Override
                    public void onResponse(Call call, ProcurementBean bean) {
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                        if("S".equals(bean.getRespCode())){
                            totalPageBuy = bean.getData().getTotalPage();
                            final List<ProcurementBean.DataBeanX.DataBean> tempLists = bean.getData().getData();
                            adapter1.add(tempLists,isRefresh);
                        }else {
                            if(bean.getErrorMsg().contains("accessToken失效")){
                                taskHomeActivity.showLogOutDialog();
                            }else {
                                ToastUtil.showShort(getContext(),bean.getErrorMsg());
                            }
                        }
                    }
                });
    }


    /**
     * 进行中的配送
     */
    public void RefreshDelivery(final boolean isRefresh) {
        Map<String, String> map = new HashMap<>();
        map.put("communityStatus", "IN_PROGRESS");
        map.put("currentPage", currentPage2 + "");
        OkhttpUtil
                .getmIntance()
                .seTag(this)
                .PostAsync("delivery/deliveryList.do", map, new ResultCallback<TaskDeliveryBean>() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                    }

                    @Override
                    public void onResponse(Call call, TaskDeliveryBean bean) {
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                        if("S".equals(bean.getRespCode())){
                            totalPageDispatching = bean.getData().getTotalPage();
                            final List<TaskDeliveryBean.DataBeanX.DataBean> tempLists = bean.getData().getData();
                            adapter2.add(tempLists,isRefresh);
                        }else {
                            if(bean.getErrorMsg().contains("accessToken失效")){
                                taskHomeActivity.showLogOutDialog();
                            }else {
                                ToastUtil.showShort(getContext(),bean.getErrorMsg());
                            }
                        }
                    }
                });
    }


    private void Listener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {

                    tabIsCheck = 0;

                    tv_tasking_number.setText("任务编号");
                    tv_tasking_person.setText("采买员");
                    recyclerView_buy.setVisibility(View.VISIBLE);
                    recyclerView_dispatching.setVisibility(View.GONE);

                } else if (position == 1) {

                    tabIsCheck = 1;

                    tv_tasking_number.setText("订单编号");
                    tv_tasking_person.setText("配送员");

                    recyclerView_buy.setVisibility(View.GONE);
                    recyclerView_dispatching.setVisibility(View.VISIBLE);


                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        //刷新或加载更多
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                switch (tabIsCheck) {
                    case 0:
                        if (currentPage < totalPageBuy) {
                            currentPage++;
                            RefreshProcurement(false);
                        } else {
                            ToastUtil.showShort(getContext(), "全部加载完毕");
                            refreshLayout.finishLoadMore();
                        }

                        break;
                    case 1:
                        if (currentPage2 < totalPageDispatching) {
                            currentPage++;
                            RefreshDelivery(false);
                        } else {
                            ToastUtil.showShort(getContext(), "全部加载完毕");
                            refreshLayout.finishLoadMore();
                        }

                        break;
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                switch (tabIsCheck) {
                    case 0:
                        currentPage = 1;
                        RefreshProcurement(true);
                        break;
                    case 1:
                        currentPage2 = 1;
                        RefreshDelivery(true);
                        break;
                }
            }
        });

    }



}
