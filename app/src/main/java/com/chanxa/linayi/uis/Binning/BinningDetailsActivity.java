package com.chanxa.linayi.uis.Binning;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.chanxa.linayi.Adapters.MyCommonAdapter;

import com.chanxa.linayi.HttpClient.OkhttpUtil;
import com.chanxa.linayi.HttpClient.ResultCallback;
import com.chanxa.linayi.R;
import com.chanxa.linayi.bean.BaseStringBean;
import com.chanxa.linayi.bean.BinningDetailsBean;
import com.chanxa.linayi.tools.DateTools;
import com.chanxa.linayi.tools.FormatUtils;
import com.chanxa.linayi.uis.BaseActivity;
import com.chanxa.linayi.uis.BrowseImageActivity;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;


public class BinningDetailsActivity extends BaseActivity {

   @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.recy_binning_details)
    RecyclerView recyclerView;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_ordersId)
    TextView tv_orderId;
    @BindView(R.id.tv_buyer)
    TextView tv_buyer;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.et_box_num)
    EditText et_num;

    private List<BinningDetailsBean.DataBean> templist=new ArrayList<>();
    private MyCommonAdapter<BinningDetailsBean.DataBean> adapter;
    private int orderId;
    private Context mContext;


    @Override
    public int getLayoutId() {
        return R.layout.activity_binning_details;
    }

    @Override
    public void initView() {
        mContext=this;
        orderId = getIntent().getIntExtra("orderID",-1);
        tv_title.setText("装箱详情");
        initRecy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Refresh();
    }

    private void Refresh() {
        final Map<String,String> map=new HashMap<>();
        map.put("ordersId",orderId+"");
        OkhttpUtil
                .getmIntance()
                .seTag(this)
                .PostAsync("procurement/procurement/boxingDetails.do", map, new ResultCallback<BinningDetailsBean>() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, BinningDetailsBean bean) {
                        if("S".equals(bean.getRespCode())){
                            templist=bean.getData();
                            adapter.add(templist,true);
                            initHeader(templist);
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

    private void initHeader(List<BinningDetailsBean.DataBean> templist) {
        if(templist.size()>0){
            BinningDetailsBean.DataBean dataBean=templist.get(0);
            //下单时间
            String createTime = DateTools.formatDateNoYear(dataBean.getOrderCreateTime() + "");
            tv_time.setText("下单时间: "+createTime);
            //订单编号
            tv_orderId.setText("订单编号: "+dataBean.getOrdersId());
            //收货人
            tv_buyer.setText("收  货  人: "+dataBean.getReceiverName());
            //配送地址
            tv_address.setText(dataBean.getReceiverAddress());
        }

    }

    private void initRecy() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<BinningDetailsBean.DataBean> list=new ArrayList<>();
        adapter = new MyCommonAdapter<BinningDetailsBean.DataBean>(this,R.layout.item_goods_binning_details_list,list) {
            @Override
            protected void convert(ViewHolder holder,final BinningDetailsBean.DataBean dataBean, int position) {
                //图片
                final ImageView iv=holder.getView(R.id.iv_goods);
                Glide.with(mContext).load(dataBean.getGoodsImage()).error(R.drawable.default_error).into(iv);

                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(BinningDetailsActivity.this,BrowseImageActivity.class);
                        intent.putExtra("imgUrl",dataBean.getGoodsImage());
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(BinningDetailsActivity.this,iv ,"share");
                        startActivity(intent,options.toBundle());
                    }
                });

                //名字
                holder.setText(R.id.tv_goods_name,dataBean.getGoodsSkuName());
                //数量
                holder.setText(R.id.tv_num,"x"+dataBean.getProcureQuantity());
                //价格
                holder.setText(R.id.tv_money,"单价 : ¥"+FormatUtils.format(dataBean.getPrice()+""));
            }
        };
        recyclerView.setAdapter(adapter);
    }


    /**
     * 确认装箱
     */
    @OnClick(R.id.tv_confirm)
    public void confirm(){
        String num=et_num.getText().toString();

        if(TextUtils.isEmpty(num)){
            showToast("请输入箱号",0);
            return;
        }

      Map<String ,String> map=new HashMap<>();
      map.put("procurementTaskList",ToJson(num));

      OkhttpUtil
              .getmIntance()
              .seTag(this)
              .PostAsync("delivery/deliveryBox/boxing.do", map, new ResultCallback<BaseStringBean>() {
                  @Override
                  public void onFailure(Call call, IOException e) {

                  }

                  @Override
                  public void onResponse(Call call, BaseStringBean bean) {
                      if("S".equals(bean.getRespCode())){
                          showToast("装箱成功",0);
                          finish();
                      }else {
                          showToast("装箱失败",0);
                      }
                  }
              });

    }

    public String ToJson(String num){
        JSONArray jsonArray = new JSONArray();
        JSONObject tmpObj = null;
        BinningDetailsBean.DataBean bean;
        for(int i = 0; i <templist.size(); i++) {
            bean = templist.get(i);
            tmpObj = new JSONObject();
            try {
                tmpObj.put("communityId",bean.getCommunityId());
                tmpObj.put("ordersId",bean.getOrdersId());
                tmpObj.put("procurementTaskId",bean.getProcurementTaskId());
                tmpObj.put("actualQuantity",bean.getActualQuantity());
                tmpObj.put("boxNo",num);
                jsonArray.put(tmpObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  jsonArray.toString();
    }


    @OnClick(R.id.tv_actionbar_back)
    public void back(){
        onBackPressed();
    }



}
