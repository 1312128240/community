package com.chanxa.linayi.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chanxa.linayi.Interface.javassist.BusRegister;
import com.chanxa.linayi.Interface.javassist.BusUnRegister;
import com.chanxa.linayi.R;
import com.chanxa.linayi.model.OrderDetails;


/**
 * @description: 订单详情，商品列表适配器
 * @author: miao
 * @time: 03-05
 */
public class OrderDetailsGoodsItemAdapter extends BaseQuickAdapter<OrderDetails.DataBean.ShoppingCarListBean> {

    public static final String TAG = "GoodsBinningDetailsAdapter";

    public OrderDetailsGoodsItemAdapter(Context context) {
        super(context, R.layout.item_goods_binning_details_list, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final OrderDetails.DataBean.ShoppingCarListBean bean) {
        if(!TextUtils.isEmpty(bean.getGoodsSkuImage())){
            Glide.with(mContext).load(bean.getGoodsSkuImage()).into((ImageView) baseViewHolder.getView(R.id.iv_goods));
        }

        if(!TextUtils.isEmpty(bean.getGoodsName())){
            baseViewHolder.setText(R.id.tv_goods_name, bean.getGoodsName());
        }
        if(!TextUtils.isEmpty(bean.getHeJiPrice())){
            baseViewHolder.setText(R.id.tv_money, bean.getHeJiPrice());
        }
        if(!TextUtils.isEmpty(bean.getQuantity())){
            baseViewHolder.setText(R.id.tv_num, bean.getQuantity());
        }
    }

    @BusRegister
    public void onClickStart(){

    }

    @BusUnRegister
    public void onClickFinish(){

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        onClickStart();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        onClickFinish();
    }
}
