package com.chanxa.linayi.uis.Receiving;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chanxa.linayi.Adapters.MyCommonAdapter;
import com.chanxa.linayi.App;
import com.chanxa.linayi.HttpClient.okhttp.OkhttpUtil;
import com.chanxa.linayi.HttpClient.okhttp.ResultCallback;
import com.chanxa.linayi.R;
import com.chanxa.linayi.bean.MyBean.BaseStringBean;
import com.chanxa.linayi.bean.MyBean.ReceivBean;
import com.chanxa.linayi.tools.FormatUtils;
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

public class ReceivActivity extends BaseActivity implements OnRefreshLoadMoreListener {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.recy_receiv)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshlayout;

    private int currentPage=1;
    private int totalPage=0;
    private MyCommonAdapter<ReceivBean.DataBeanX.DataBean> adapter;
    private AlertDialog.Builder dialog;


    @Override
    public int getLayoutId() {
        return R.layout.activity_receiv;
    }

    @Override
    public void initView() {
     tv_title.setText("收货详情");
     refreshlayout.setOnLoadMoreListener(this);
     initRecy();
 }

    private void initRecy() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        List<ReceivBean.DataBeanX.DataBean> beanList=new ArrayList<>();
        adapter = new MyCommonAdapter<ReceivBean.DataBeanX.DataBean>(this,R.layout.new_receiv_item,beanList) {
            @Override
            protected void convert(ViewHolder holder, final ReceivBean.DataBeanX.DataBean dataBean, int position) {
                //数量
                holder.setText(R.id.tv_receiv_number,"数量: "+dataBean.getQuantity());
                //实际采买数量
                holder.setText(R.id.tv_practical_number,"实际采买数量: "+dataBean.getActualQuantity());
                //金额总计
                String totalPrice=FormatUtils.format(dataBean.getTotalPrice());
                holder.setText(R.id.tv_total_price,"金额总计: "+totalPrice);
                //图片
                ImageView iv=holder.getView(R.id.iv_receiv);
                Glide.with(mContext).load(dataBean.getGoodsImage()).error(R.drawable.default_error).into(iv);
               //商品名字
                holder.setText(R.id.tv_receiv_goods_name,dataBean.getFullName());
                //单价
               String price= FormatUtils.format(dataBean.getPrice()+"");
               holder.setText(R.id.tv_unit_price,"单价: "+price+"元");
               //确定收货
                holder.getView(R.id.btn_sure_receiv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMyDialog(dataBean.getProcurementTaskIdList());
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Refresh(true);
    }


    public void showMyDialog(final String taskId) {
        //创建dialog构造器
        dialog = new AlertDialog.Builder(this);
        //设置title
        dialog.setTitle("提示");
        //设置icon
        dialog.setIcon(R.mipmap.logo);
        //设置内容
        dialog.setMessage("确认收货完成");
        //设置按钮
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //确认收货
                inspectionComplete(taskId);
            }
        });
        dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        //创建并显示
        dialog.create().show();
    }

    //确认收货
    private void inspectionComplete(String taskId) {

        Map<String,String> map=new HashMap<>();
        map.put("procurementTaskIdList",taskId);

        OkhttpUtil
                .getmIntance()
                .seTag(this)
                .PostAsync("procurement/procurement/inspectionComplete.do", map, new ResultCallback<BaseStringBean>() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, BaseStringBean bean) {
                        if("S".equals(bean.getRespCode())){
                            ToastUtil.showShort(App.getInstance(),"验货成功");
                            Refresh(true);
                        }else {
                            if(bean.getErrorMsg().contains("accessToken失效")){
                                showLogOutDialog();
                            }else {
                                ToastUtil.showShort(ReceivActivity.this,bean.getErrorMsg());
                            }
                        }
                    }
                });
    }

    private void Refresh(final boolean isRefresh) {
        Map<String,String> map=new HashMap<>();
        map.put("currentPage",currentPage+"");
        map.put("pageSize","10");
        OkhttpUtil
                .getmIntance()
                .seTag(this)
                .PostAsync("procurement/procurement/getProcurementTask.do", map, new ResultCallback<ReceivBean>() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        refreshlayout.finishRefresh();
                        refreshlayout.finishLoadMore();
                    }

                    @Override
                    public void onResponse(Call call, ReceivBean bean) {
                        refreshlayout.finishRefresh();
                        refreshlayout.finishLoadMore();
                        if("S".equals(bean.getRespCode())){
                            totalPage=bean.getData().getTotalPage();
                            final List<ReceivBean.DataBeanX.DataBean> tempList=bean.getData().getData();
                            adapter.add(tempList,isRefresh);
                        }else {
                            if(bean.getErrorMsg().contains("accessToken失效")){
                                showLogOutDialog();
                            }else {
                                ToastUtil.showShort(ReceivActivity.this,bean.getErrorMsg());
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
            ToastUtil.showShort(this,"全部加载完毕");
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog!=null){
            dialog=null;
        }
    }
}
