package com.chanxa.linayi.uis.Task;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chanxa.linayi.HttpClient.OkhttpUtil;
import com.chanxa.linayi.HttpClient.ResultCallback;
import com.chanxa.linayi.R;
import com.chanxa.linayi.bean.TaskDetailsBean;
import com.chanxa.linayi.tools.DateTools;
import com.chanxa.linayi.uis.BaseActivity;
import com.chanxa.linayi.uis.BrowseImageActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;


public class TaskProcurementkDetailActivity extends BaseActivity{

    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_ordersId)
    TextView tv_orderId;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_totalPrice)
    TextView tv_totalPrice;
    @BindView(R.id.iv_goods)
    ImageView iv_goods;
    @BindView(R.id.tv_goods_name)
    TextView tv_goods_name;
    @BindView(R.id.tv_goods_price)
    TextView tv_price;
    @BindView(R.id.tv_goods_number)
    TextView tv_goods_number;
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    private int taskId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_procurement_task_detail;
    }

    @Override
    public void initView() {
        taskId = getIntent().getIntExtra("taskId",-1);
        tv_title.setText("采买任务详情");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        Map<String,String> map=new HashMap<>();
        map.put("procurementTaskId",taskId+"");

        OkhttpUtil
                .getmIntance()
                .seTag(this)
                .PostAsync("procurement/procurement/getProcurement.do", map, new ResultCallback<TaskDetailsBean>() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, TaskDetailsBean bean) {
                        if("S".equals(bean.getRespCode())){
                            initUi(bean.getData());
                        }else {
                            if(bean.getErrorMsg().contains("accessToken失效")){
                                showLogOutDialog();
                            }else {
                                showToast(bean.getErrorMsg(),0);
                            }
                        }
                    }
                });

    }

    private void initUi(final TaskDetailsBean.DataBean dataBean) {
         //下单时间
        String time = DateTools.formatDateNoYear(dataBean.getCreateTime()+"");
        tv_time.setText("下单时间: "+time);
        //订单金额
        tv_totalPrice.setText("订单金额: ¥" +dataBean.getTotalPrice());
        //订单编号
        tv_orderId.setText("订单编号: "+dataBean.getOrdersId());
        //配送地址
        tv_address.setText(dataBean.getCommunityName());
        //图片
        Glide.with(this).load(dataBean.getImage()).error(R.drawable.default_error).into(iv_goods);

        Log.e("图片路径",dataBean.getImage()+"");
        iv_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TaskProcurementkDetailActivity.this,BrowseImageActivity.class);
                intent.putExtra("imgUrl",dataBean.getImage());
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(TaskProcurementkDetailActivity.this,iv_goods ,"share");
                startActivity(intent,options.toBundle());
            }
        });

        //名字
        tv_goods_name.setText(dataBean.getGoodsSkuName());
        //数量
        tv_goods_number.setText("x"+dataBean.getQuantity());
         //价格
        double p=(double) dataBean.getPrice()/100;
        tv_price.setText("单价: ¥"+p);
    }


    @OnClick(R.id.tv_actionbar_back)
    public void back(){
        onBackPressed();
    }

}
