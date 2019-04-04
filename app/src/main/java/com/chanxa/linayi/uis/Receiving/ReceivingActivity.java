package com.chanxa.linayi.uis.Receiving;

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
import com.chanxa.linayi.bean.MyBean.ReceivingBean;
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


public class ReceivingActivity extends BaseActivity implements OnRefreshLoadMoreListener {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;

     @BindView(R.id.refreshLayout)
     SmartRefreshLayout refreshlayout;

    @BindView(R.id.recy_receiving)
    RecyclerView recyclerview;

    private int currentPage=1;
    private int totalPage;
    private MyCommonAdapter<ReceivingBean.DataBeanX.DataBean> adapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_receiving;
    }

    @Override
    public void initView() {
         refreshlayout.setOnRefreshLoadMoreListener(this);
         tv_title.setText("收货");
         initRecy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage=1;
        Refresh(true);
    }

    private void initRecy() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        List<ReceivingBean.DataBeanX.DataBean> lists; lists = new ArrayList<>();
        adapter = new MyCommonAdapter<ReceivingBean.DataBeanX.DataBean>(this,R.layout.item_send_list,lists) {
            @Override
            protected void convert(ViewHolder holder, final ReceivingBean.DataBeanX.DataBean dataBean, int position) {
                //网点名称
                holder.setText(R.id.tv1,dataBean.getSupermarketName());
                //商品数量
                holder.setText(R.id.tv2,dataBean.getQuantity()+"");
                //采买金额
                double p=(double)dataBean.getPrice()/100;
                holder.setText(R.id.tv3,p+"");
                //采买时间
                String createTime = AppUtils.formatDateNoYear(dataBean.getCreateTime() + "");
                holder.setText(R.id.tv4,createTime);
                //去详情
                holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ReceivingActivity.this,GoodsReceivingDetailsActivity.class);
                        intent.putExtra("procurementTaskId",dataBean.getProcurementTaskId());
                        startActivity(intent);
                    }
                });
            }
        };

        recyclerview.setAdapter(adapter);
    }

    private void Refresh(final boolean isRefresh) {
        Map<String,String> map=new HashMap<>();
        map.put("currentPage",currentPage+"");
        map.put("pageSize",15+"");

        OkhttpUtil
                .getmIntance()
                .seTag(this)
                .PostAsync("procurement/procurement/getProcurementTask.do", map, new ResultCallback<ReceivingBean>() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        refreshlayout.finishRefresh();
                        refreshlayout.finishLoadMore();
                    }

                    @Override
                    public void onResponse(Call call, ReceivingBean bean) {
                        refreshlayout.finishRefresh();
                        refreshlayout.finishLoadMore();
                        if("S".equals(bean.getRespCode())){
                            totalPage=bean.getData().getTotalPage();
                            final List<ReceivingBean.DataBeanX.DataBean> tempList=bean.getData().getData();
                            adapter.add(tempList,isRefresh);
                        }else {
                            if(bean.getErrorMsg().contains("accessToken失效")){
                                showLogOutDialog();
                            }else {
                                ToastUtil.showShort(ReceivingActivity.this,bean.getErrorMsg());
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
            ToastUtil.showShort(this,"加载完毕");
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
