package com.chanxa.linayi.uis.Binning;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chanxa.linayi.Adapters.MyCommonAdapter;
import com.chanxa.linayi.HttpClient.okhttp.OkhttpUtil;
import com.chanxa.linayi.HttpClient.okhttp.ResultCallback;
import com.chanxa.linayi.R;
import com.chanxa.linayi.bean.MyBean.BaseStringBean;
import com.chanxa.linayi.bean.MyBean.BinningDetailsBean;
import com.chanxa.linayi.tools.AppUtils;
import com.chanxa.linayi.tools.ToastUtil;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


public class BinningDetailsActivity extends AppCompatActivity{

   @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.recy_binning_details)
    RecyclerView recyclerView;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_second_time)
    TextView tv_second_time;
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



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binning_details);
        if (Build.VERSION.SDK_INT >=21){
            getWindow().setStatusBarColor(Color.parseColor("#f6382f"));
        }
        ButterKnife.bind(this);
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
        Map<String,String> map=new HashMap<>();
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
                                ToastUtil.showShort(BinningDetailsActivity.this,"accessToken失效,请退出重新登录");
                            }else {
                                ToastUtil.showShort(BinningDetailsActivity.this,bean.getErrorMsg());
                            }
                        }
                    }
                });
    }

    private void initHeader(List<BinningDetailsBean.DataBean> templist) {
        if(templist.size()>0){
            BinningDetailsBean.DataBean dataBean=templist.get(0);
            //下单时间
            String createTime = AppUtils.formatDateNoYear(dataBean.getCreateTime() + "");
            tv_time.setText("下单时间: "+createTime);
            //剩余时间
            String updateTime=AppUtils.formatDateNoYear(dataBean.getCreateTime() + "");
            tv_second_time.setText("剩余时间: "+updateTime);
            //订单编号
            tv_orderId.setText("订单编号: "+dataBean.getOrdersId());
            //收货人
            tv_buyer.setText("收货人: "+dataBean.getReceiverName());
            //配送地址
            tv_address.setText("收货地址: "+dataBean.getReceiverAddress());
        }

    }

    private void initRecy() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<BinningDetailsBean.DataBean> list=new ArrayList<>();
        adapter = new MyCommonAdapter<BinningDetailsBean.DataBean>(this,R.layout.item_goods_binning_details_list,list) {
            @Override
            protected void convert(ViewHolder holder, BinningDetailsBean.DataBean dataBean, int position) {
                //图片
                ImageView iv=holder.getView(R.id.iv_goods);
                Glide.with(mContext).load(dataBean.getGoodsImage()).error(R.drawable.default_error).into(iv);
                //名字
                holder.setText(R.id.tv_goods_name,dataBean.getGoodsSkuName());
                //数量
                holder.setText(R.id.tv_num,"x"+dataBean.getQuantity());
                //价格
                holder.setText(R.id.tv_money,"¥"+dataBean.getPrice());
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
            ToastUtil.showShort(this,"请输入箱号");
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
                          ToastUtil.showShort(BinningDetailsActivity.this,"装箱成功");
                          finish();
                      }else {
                          ToastUtil.showShort(BinningDetailsActivity.this,"装箱失败");
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
                tmpObj.put("boxNo",Integer.valueOf(num));
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
