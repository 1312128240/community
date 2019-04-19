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
import com.google.gson.Gson;
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


public class TaskOverFragment extends BaseFragments{
   @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.tv_taskover_number)
    TextView tv_taskover_number;
    @BindView(R.id.tv_taskover_person)
    TextView tv_taskover_person;
    @BindView(R.id.recy_taskover_buy)
    RecyclerView recyclerView_buy;
    @BindView(R.id.recy_taskover_dispatching)
    RecyclerView recyclerView_dispatching;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    private int currentPage = 1;
    private int totalPageBuy;

    private int currentPage2 = 1;
    private int totalPageDelivery;

    private MyCommonAdapter<ProcurementBean.DataBeanX.DataBean> adapter1;
    private MyCommonAdapter<TaskDeliveryBean.DataBeanX.DataBean> adapter2;
    private int selectIndex;
    private TaskHomeActivity taskHomeActivity;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_task_over;
    }



    @Override
    public void initData() {
         Listener();
         initRecy();

        RefreshProcurement(true);
        RefreshDelivery(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        taskHomeActivity = (TaskHomeActivity) activity;
    }

    private void initRecy() {
        //-----------------已完成采购------------------------
        recyclerView_buy.setLayoutManager(new LinearLayoutManager(getContext()));
        List<ProcurementBean.DataBeanX.DataBean> list1=new ArrayList<>();

        adapter1=new MyCommonAdapter<ProcurementBean.DataBeanX.DataBean>(getContext(),R.layout.item_delivery_list,list1) {
            @Override
            protected void convert(ViewHolder holder,final ProcurementBean.DataBeanX.DataBean bean, int position) {
                //任务编号
                holder.setText(R.id.tv1t,bean.getProcurementTaskId()+"");
                //采买员
                holder.setText(R.id.tv2t,bean.getBuyUserName());
                //金额
                double p=(double)bean.getPrice()/100;
                holder.setText(R.id.tv3t,p+"");
                //查看详情
                holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(mContext, TaskProcurementkDetailActivity.class);
                        intent.putExtra("taskId",bean.getProcurementTaskId());
                        mContext.startActivity(intent);
                    }
                });
            }
        };

        recyclerView_buy.setAdapter(adapter1);

       //-----------------已完成配送任务------------------------
        recyclerView_dispatching.setLayoutManager(new LinearLayoutManager(getContext()));
        List<TaskDeliveryBean.DataBeanX.DataBean> list2=new ArrayList<>();
        adapter2=new MyCommonAdapter<TaskDeliveryBean.DataBeanX.DataBean>(getContext(),R.layout.item_delivery_list,list2) {
            @Override
            protected void convert(ViewHolder holder, final TaskDeliveryBean.DataBeanX.DataBean bean, int position) {
                //任务编号
                holder.setText(R.id.tv1t,bean.getOrdersId()+"");
                //采买员
                holder.setText(R.id.tv2t,bean.getDelivererName());
                //金额
                double p=(double)bean.getAmount()/100;
                holder.setText(R.id.tv3t,p+"");

                holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(mContext, TaskDeliveryDetailsActivity.class);
                        intent.putExtra("ordersId",bean.getOrdersId());
                        mContext.startActivity(intent);
                    }
                });
            }
        };

        recyclerView_dispatching.setAdapter(adapter2);

    }

    /**
     * 己完成的采购
     */
    private void RefreshProcurement(final boolean isRefresh) {
        Map<String,String> map=new HashMap<>();
        map.put("procureStatus","FINISHED");
        map.put("currentPage",currentPage+"");
        map.put("pageSize",10+"");

        OkhttpUtil
                .getmIntance()
                .seTag(this)
                .PostAsync("procurement/procurement/getProcurementList.do", map, new ResultCallback<ProcurementBean>() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                    }

                    @Override
                    public void onResponse(Call call, ProcurementBean bean) {
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                        if("S".equals(bean.getRespCode())){
                            totalPageBuy= bean.getData().getTotalPage();
                            final List<ProcurementBean.DataBeanX.DataBean> tempList=bean.getData().getData();
                            adapter1.add(tempList,isRefresh);
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
     * 己完成配送
     * @param isRefresh
     */
    private void RefreshDelivery(final boolean isRefresh){
        Map<String,String> map=new HashMap<>();
        map.put("communityStatus","DELIVER_FINISHED");
        map.put("currentPage",currentPage2+"");
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
                            totalPageDelivery = bean.getData().getTotalPage();
                            final List<TaskDeliveryBean.DataBeanX.DataBean> tempList=bean.getData().getData();
                            adapter2.add(tempList,isRefresh);
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
                int position=tab.getPosition();
                if(position==0){

                    selectIndex = 0;

                    tv_taskover_number.setText("任务编号");
                    tv_taskover_person.setText("采买员");

                    recyclerView_buy.setVisibility(View.VISIBLE);
                    recyclerView_dispatching.setVisibility(View.GONE);


                }else if(position==1){
                    selectIndex = 1;

                    tv_taskover_number.setText("订单编号");
                    tv_taskover_person.setText("配送员");

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

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                switch (selectIndex){
                    case 0:
                        if(currentPage<totalPageBuy){
                            currentPage++;
                            RefreshProcurement(false);
                        }else {
                            ToastUtil.showShort(getContext(),"全部加载完毕");
                            refreshLayout.finishLoadMore();
                        }
                        break;
                    case 1:
                        if(currentPage2<totalPageDelivery){
                            currentPage2++;
                            RefreshDelivery(false);
                        }else {
                            ToastUtil.showShort(getContext(),"全部加载完毕");
                            refreshLayout.finishLoadMore();
                        }
                        break;
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                  switch (selectIndex){
                      case 0:
                          currentPage=1;
                          RefreshProcurement(true);
                          break;
                      case 1:
                          currentPage2=1;
                          RefreshDelivery(true);
                          break;
                  }
            }
        });

    }



}
