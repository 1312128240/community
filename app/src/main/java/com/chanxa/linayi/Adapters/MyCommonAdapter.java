package com.chanxa.linayi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2019/3/29.
 */

public abstract class MyCommonAdapter<T> extends MultiItemTypeAdapter<T>{
    protected Context mContext;
    private int mLayoutId;
    private List<T> mDatas;
    private LayoutInflater mInflater;

    public MyCommonAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType( T item, int position)
            {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position)
            {
              MyCommonAdapter.this.convert(holder, t, position);
            }
        });
    }


    /**
     * 适配器添加数据
     * @param lists   要添加的数据
     * @param isClean 是否清除原有数据
     */
    public void add(List<T>  lists,boolean isClean){
        if(isClean){
            mDatas.clear();
        }
        mDatas.addAll(lists);
        notifyDataSetChanged();
    }



    protected abstract void convert(ViewHolder holder, T t, int position);




}
