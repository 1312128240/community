package com.chanxa.linayi.uis.Delivery;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chanxa.linayi.Adapters.MyCommonAdapter;
import com.chanxa.linayi.HttpClient.okhttp.OkhttpUtil;
import com.chanxa.linayi.HttpClient.okhttp.ResultCallback;
import com.chanxa.linayi.R;
import com.chanxa.linayi.bean.MyBean.DeliveryListBean;
import com.chanxa.linayi.tools.AppUtils;
import com.chanxa.linayi.tools.ToastUtil;
import com.chanxa.linayi.uis.BaseActivity;
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
import butterknife.OnClick;
import okhttp3.Call;


public class DeliveryActivity extends BaseActivity implements OnRefreshLoadMoreListener{

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.mRecyclerView)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int currentPage=1;
    private MyCommonAdapter<DeliveryListBean.DataBeanX.DataBean> adpater;
    private int totalPage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_delivery;
    }

    @Override
    public void initView() {
         tv_title.setText("配送");
         refreshLayout.setOnRefreshLoadMoreListener(this);
         initRecy();
    }


    @Override
    protected void onResume() {
        super.onResume();
        currentPage=1;
        Refresh(true);
    }

    private void Refresh(final boolean isRefresh) {
        Map<String,String> map=new HashMap<>();
        map.put("currentPage",currentPage+"");
        map.put("pageSize",10+"");
        OkhttpUtil
                .getmIntance()
                .seTag(this)
                .PostAsync("delivery/deliveryTask/getAllDeliveryTask.do", map, new ResultCallback<DeliveryListBean>() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();
                    }

                    @Override
                    public void onResponse(Call call, DeliveryListBean bean) {
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();
                        if("S".equals(bean.getRespCode())){
                            List<DeliveryListBean.DataBeanX.DataBean> tempList=bean.getData().getData();
                            totalPage = bean.getData().getTotalPage();
                            adpater.add(tempList,isRefresh);
                        }else {
                            if(bean.getErrorMsg().contains("accessToken失效")){
                                showLogOutDialog();
                            }else {
                                ToastUtil.showShort(DeliveryActivity.this,bean.getErrorMsg());
                            }
                        }
                    }
                });
    }

    private void initRecy() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        List<DeliveryListBean.DataBeanX.DataBean> lists=new ArrayList<>();
        adpater = new MyCommonAdapter<DeliveryListBean.DataBeanX.DataBean>(this,R.layout.item_send_list,lists) {
            @Override
            protected void convert(ViewHolder holder, final DeliveryListBean.DataBeanX.DataBean deliveryListBean, int position) {
                //装箱时间
                String createTime = AppUtils.formatDateNoYear(deliveryListBean.getBoxTime() + "");
                holder.setText(R.id.tv1,createTime);
                //顾客名字
                holder.setText(R.id.tv2,deliveryListBean.getReceiverName());
                //数量
                holder.setText(R.id.tv3,deliveryListBean.getQuantity()+"");
                //箱号
                holder.setText(R.id.tv4,deliveryListBean.getBoxNo());
                //去详情
                holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(mContext,DeliveryDetailActivity.class);
                        intent.putExtra("boxNo",deliveryListBean.getBoxNo());
                        intent.putExtra("ordersId",deliveryListBean.getOrdersId());
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerview.setAdapter(adpater);

    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
              if(currentPage<totalPage){
                  currentPage++;
                  Refresh(false);
              }else {
                  ToastUtil.showShort(this,"全部加载完毕");
                  refreshLayout.finishLoadMore();
              }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
              currentPage=1;
              Refresh(true);
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back(){
        onBackPressed();
    }
}
