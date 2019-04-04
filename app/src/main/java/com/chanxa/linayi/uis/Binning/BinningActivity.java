package com.chanxa.linayi.uis.Binning;

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
import com.chanxa.linayi.bean.MyBean.BinningBean;
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


public class BinningActivity extends BaseActivity implements OnRefreshLoadMoreListener{

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.recy_binning)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int currentPage=1;
    private MyCommonAdapter<BinningBean.DataBeanX.DataBean> adapter;
    private int totalPage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_binning;
    }

    @Override
    public void initView() {
         tv_title.setText("装箱");
         refreshLayout.setOnRefreshLoadMoreListener(this);
         initRecy();
    }

    private void initRecy() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<BinningBean.DataBeanX.DataBean> beanList=new ArrayList<>();
        adapter = new MyCommonAdapter<BinningBean.DataBeanX.DataBean>(this,R.layout.item_task_list,beanList) {
            @Override
            protected void convert(ViewHolder holder, final BinningBean.DataBeanX.DataBean dataBean, int position) {
                //收货人名字
                holder.setText(R.id.tv1t,dataBean.getReceiverName());
                //数量
                holder.setText(R.id.tv2t,dataBean.getQuantity()+"");
                //小区名称
                holder.setText(R.id.tv3t,dataBean.getAddressTwo());

                //查看详情
                holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(BinningActivity.this,BinningDetailsActivity.class);
                        intent.putExtra("orderID",dataBean.getOrdersId());
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
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
        OkhttpUtil
                .getmIntance()
                .seTag(this)
                .PostAsync("goods/order/boxingOrder.do", map, new ResultCallback<BinningBean>() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();
                    }

                    @Override
                    public void onResponse(Call call, BinningBean bean) {
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();

                        if("S".equals(bean.getRespCode())){
                            totalPage = bean.getData().getTotalPage();
                            List<BinningBean.DataBeanX.DataBean> tempList=bean.getData().getData();
                            adapter.add(tempList,isRefresh);
                        }else{
                            if(bean.getErrorMsg().contains("accessToken失效")){
                                showLogOutDialog();
                            }else {
                                ToastUtil.showShort(BinningActivity.this,bean.getErrorMsg());
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
              refreshLayout.finishLoadMore();
              ToastUtil.showShort(this,"全部加毕");
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
