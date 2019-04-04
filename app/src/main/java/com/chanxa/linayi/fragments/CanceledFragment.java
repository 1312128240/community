package com.chanxa.linayi.fragments;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.chanxa.linayi.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;


public class CanceledFragment extends BaseFragments implements OnRefreshLoadMoreListener{

     @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recy_cancel)
    RecyclerView recyclerView;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_canceled;
    }

    @Override
    public void initData() {
            refreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }
}
