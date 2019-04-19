package com.chanxa.linayi.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chanxa.linayi.R;
import com.chanxa.linayi.uis.Binning.BinningActivity;
import com.chanxa.linayi.uis.Delivery.DeliveryActivity;
import com.chanxa.linayi.uis.Order.OrderStatusActivity;
import com.chanxa.linayi.uis.Personal.SettingActivity;
import com.chanxa.linayi.uis.Receiving.ReceivActivity;
import com.chanxa.linayi.uis.Receiving.ReceivingActivity;
import com.chanxa.linayi.uis.Task.TaskHomeActivity;


/**
 * Created by chanxa on 2017/11/2.
 */

public class HomeRcvAdapter extends RecyclerView.Adapter<HomeRcvAdapter.MyViewHolder> {

    private Context mContext;


    private int[] names ={R.string.home_order, R.string.home_task, R.string.home_goods_receipt,
            R.string.home_packing, R.string.home_goods_send, R.string.home_account_number,
    };

    private int [] drawableArrays={
            R.drawable.icon_home_order,
            R.drawable.icon_home_task,
            R.drawable.icon_home_goods_receiving,
            R.drawable.icon_home_packing,
            R.drawable.icon_home_goods_receipt,
            R.drawable.icon_home_account_number,
    };


    public HomeRcvAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view =LayoutInflater.from(mContext).inflate(R.layout.item_home,parent,false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
       holder.tv.setText(names[position]);
       holder.iv.setImageResource(drawableArrays[position]);

       holder.linearLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               switch (position) {
                   case 0:
                     //  mContext.startActivity(new android.content.Intent(mContext,OrderActivity.class));
                     mContext.startActivity(new Intent(mContext, OrderStatusActivity.class));
                       break;
                   case 1:
                       //  mContext.startActivity(new android.content.Intent(mContext, TaskActivity.class));
                       mContext.startActivity(new Intent(mContext, TaskHomeActivity.class));
                       break;
                   case 2:
                     //  mContext.startActivity(new Intent(mContext, ReceivingActivity.class));
                       mContext.startActivity(new Intent(mContext,ReceivActivity.class));
                       break;
                   case 3: //装箱
                       mContext.startActivity(new Intent(mContext, BinningActivity.class));
                       break;
                  case 4: //配送
                      mContext.startActivity(new Intent(mContext, DeliveryActivity.class));
                      break;
                  case 5:
                       mContext.startActivity(new Intent(mContext, SettingActivity.class));
                       break;
               }
           }
       });

    }



    @Override
    public int getItemCount() {
        return names.length;
    }


    static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv;
        private final ImageView iv;
        private final LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_name);
            iv = itemView.findViewById(R.id.iv_icon);
            linearLayout = itemView.findViewById(R.id.list_item);
        }
    }

}
